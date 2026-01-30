import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgApexchartsModule, ChartComponent } from 'ng-apexcharts';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { lucideTrendingUp } from '@ng-icons/lucide';
import { BackendService } from '../../services/backend.service';
import { SalesSummary, ChartSeries } from '../../models/dashboard.models';
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexDataLabels,
  ApexStroke,
  ApexGrid,
  ApexTooltip,
  ApexFill,
  ApexYAxis,
  ApexLegend
} from 'ng-apexcharts';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  yaxis: ApexYAxis;
  dataLabels: ApexDataLabels;
  stroke: ApexStroke;
  grid: ApexGrid;
  tooltip: ApexTooltip;
  fill: ApexFill;
  colors: string[];
  legend: ApexLegend;
};

@Component({
  selector: 'app-sales-chart',
  standalone: true,
  imports: [CommonModule, NgApexchartsModule, NgIconComponent],
  viewProviders: [provideIcons({ lucideTrendingUp })],
  template: `
    <div class="rounded-xl border border-border bg-card h-full flex flex-col">
      <div class="p-6 pb-4">
        <div class="flex items-center justify-between">
          <div>
            <h3 class="text-lg font-semibold text-foreground">Total Visitors</h3>
            <p class="text-sm text-muted-foreground">{{ getSubtitle() }}</p>
          </div>
          <!-- Button Group / Tabs for time range selection (shadcn style) -->
          <div class="inline-flex items-center gap-1 rounded-lg bg-secondary p-1">
            <button 
              *ngFor="let option of timeRangeOptions"
              (click)="onTimeRangeChange(option.value)"
              class="px-3 py-1.5 text-sm font-medium rounded-md transition-all"
              [class]="selectedTimeRange === option.value 
                ? 'bg-card text-foreground' 
                : 'text-muted-foreground hover:text-foreground'">
              {{ option.label }}
            </button>
          </div>
        </div>
      </div>
      
      <!-- Sales Summary Cards -->
      <div class="grid grid-cols-3 gap-4 px-6 pb-4">
        <div class="rounded-lg bg-muted/50 p-4">
          <p class="text-xs text-muted-foreground">Total Sales</p>
          <p class="text-xl font-bold text-foreground">{{ salesSummary.totalSales }}</p>
          <p class="text-xs text-emerald-500 flex items-center gap-1 mt-1">
            <ng-icon name="lucideTrendingUp" class="h-3 w-3" />
            +12.5%
          </p>
        </div>
        <div class="rounded-lg bg-muted/50 p-4">
          <p class="text-xs text-muted-foreground">This Month</p>
          <p class="text-xl font-bold text-foreground">{{ salesSummary.thisMonth }}</p>
          <p class="text-xs text-emerald-500 flex items-center gap-1 mt-1">
            <ng-icon name="lucideTrendingUp" class="h-3 w-3" />
            +8.2%
          </p>
        </div>
        <div class="rounded-lg bg-muted/50 p-4">
          <p class="text-xs text-muted-foreground">This Week</p>
          <p class="text-xl font-bold text-foreground">{{ salesSummary.thisWeek }}</p>
          <p class="text-xs text-emerald-500 flex items-center gap-1 mt-1">
            <ng-icon name="lucideTrendingUp" class="h-3 w-3" />
            +4.5%
          </p>
        </div>
      </div>

      <!-- Chart -->
      <div class="px-6 pb-6 flex-1 min-h-0">
        <apx-chart
          #chart
          [series]="chartOptions.series"
          [chart]="chartOptions.chart"
          [xaxis]="chartOptions.xaxis"
          [yaxis]="chartOptions.yaxis"
          [dataLabels]="chartOptions.dataLabels"
          [stroke]="chartOptions.stroke"
          [grid]="chartOptions.grid"
          [tooltip]="chartOptions.tooltip"
          [fill]="chartOptions.fill"
          [colors]="chartOptions.colors"
          [legend]="chartOptions.legend">
        </apx-chart>
      </div>
    </div>
  `
})
export class SalesChartComponent implements OnInit {
  @ViewChild('chart') chart!: ChartComponent;
  
  salesSummary: SalesSummary = { totalSales: '', thisMonth: '', thisWeek: '' };
  chartOptions: ChartOptions;
  
  // Time range options for the button group
  timeRangeOptions = [
    { label: 'Last 3 months', value: '3m' },
    { label: 'Last 30 days', value: '30d' },
    { label: 'Last 7 days', value: '7d' }
  ];
  selectedTimeRange = '3m';

  constructor(private backendService: BackendService) {
    this.chartOptions = this.getDefaultChartOptions();
  }

  ngOnInit(): void {
    this.salesSummary = this.backendService.getSalesSummary();
    this.loadChartData();
  }

  /**
   * Handle time range change - update chart with smooth animation
   */
  onTimeRangeChange(range: string): void {
    if (this.selectedTimeRange === range) return;
    
    this.selectedTimeRange = range;
    
    const chartData = this.backendService.getChartData(this.selectedTimeRange);
    const categories = this.getXAxisCategories();
    
    // Use ApexCharts API for smooth transition
    if (this.chart) {
      // First update the x-axis categories
      this.chart.updateOptions({
        xaxis: {
          categories: categories,
          axisBorder: { show: false },
          axisTicks: { show: false },
          labels: {
            style: { colors: 'hsl(240 5% 64.9%)' }
          }
        }
      }, false, true);
      
      // Then update the series data with animation
      this.chart.updateSeries(chartData, true);
    }
  }

  /**
   * Initial chart data load
   */
  private loadChartData(): void {
    const chartData = this.backendService.getChartData(this.selectedTimeRange);
    this.chartOptions = {
      ...this.chartOptions,
      series: chartData,
      xaxis: {
        ...this.chartOptions.xaxis,
        categories: this.getXAxisCategories()
      }
    };
  }

  /**
   * Get X-axis categories based on selected time range
   */
  private getXAxisCategories(): string[] {
    switch (this.selectedTimeRange) {
      case '7d':
        return ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
      case '30d':
        return ['Week 1', 'Week 2', 'Week 3', 'Week 4'];
      case '3m':
      default:
        return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'];
    }
  }

  /**
   * Get subtitle based on selected time range
   */
  getSubtitle(): string {
    switch (this.selectedTimeRange) {
      case '7d':
        return 'Total for the last 7 days';
      case '30d':
        return 'Total for the last 30 days';
      case '3m':
      default:
        return 'Total for the last 3 months';
    }
  }

  private getDefaultChartOptions(): ChartOptions {
    return {
      series: [],
      chart: {
        type: 'area',
        height: 280,
        fontFamily: 'inherit',
        toolbar: { show: false },
        background: 'transparent',
        foreColor: 'hsl(240 5% 64.9%)',
        animations: {
          enabled: true,
          speed: 500,
          animateGradually: {
            enabled: true,
            delay: 50
          },
          dynamicAnimation: {
            enabled: true,
            speed: 500
          }
        },
        redrawOnParentResize: true
      },
      dataLabels: { enabled: false },
      stroke: { 
        curve: 'smooth', 
        width: 2
      },
      colors: ['rgba(255,255,255,0.9)', 'rgba(255,255,255,0.4)'],
      fill: {
        type: 'gradient',
        gradient: {
          shadeIntensity: 1,
          opacityFrom: 0.2,
          opacityTo: 0.05,
          stops: [0, 90, 100]
        }
      },
      grid: { 
        borderColor: 'hsl(240 3.7% 15.9%)',
        strokeDashArray: 0,
        xaxis: { lines: { show: false } },
        yaxis: { lines: { show: true } },
        padding: {
          top: 0,
          right: 10,
          bottom: 0,
          left: 15
        }
      },
      xaxis: {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
        axisBorder: { show: false },
        axisTicks: { show: false },
        labels: {
          style: { colors: 'hsl(240 5% 64.9%)' }
        }
      },
      yaxis: {
        labels: {
          style: { colors: 'hsl(240 5% 64.9%)' }
        }
      },
      tooltip: { 
        theme: 'dark',
        x: { show: true },
        shared: true,
        intersect: false
      },
      legend: {
        show: true,
        position: 'bottom',
        horizontalAlign: 'center',
        labels: {
          colors: 'hsl(240 5% 64.9%)'
        },
        markers: {
          offsetX: -4
        },
        itemMargin: {
          horizontal: 16,
          vertical: 8
        }
      }
    };
  }
}
