import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopCardsComponent } from '../../components/top-cards/top-cards.component';
import { SalesChartComponent } from '../../components/sales-chart/sales-chart.component';
import { FeedsComponent } from '../../components/feeds/feeds.component';
import { ProjectsTableComponent } from '../../components/projects-table/projects-table.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    TopCardsComponent,
    SalesChartComponent,
    FeedsComponent,
    ProjectsTableComponent
  ],
  template: `
    <div class="space-y-6">
      <!-- Top Statistics Cards -->
      <app-top-cards></app-top-cards>

      <!-- Charts and Feeds Row -->
      <div class="grid gap-6 lg:grid-cols-3 items-stretch">
        <div class="lg:col-span-2">
          <app-sales-chart class="block h-full [&>div]:h-full"></app-sales-chart>
        </div>
        <div>
          <app-feeds class="block h-full [&>div]:h-full"></app-feeds>
        </div>
      </div>

      <!-- Projects Table -->
      <div class="mt-6">
        <app-projects-table></app-projects-table>
      </div>
    </div>
  `
})
export class DashboardComponent {}
