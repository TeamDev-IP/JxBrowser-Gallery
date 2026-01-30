export interface TopCard {
  bgcolor: string;
  icon: string;
  title: string;
  subtitle: string;
  trendDirection: boolean;
  trendValue: string;
  trendDescription: string;
}

export interface Feed {
  bgClass: string;
  icon: string;
  task: string;
  time: string;
}

export interface Project {
  image: string;
  name: string;
  email: string;
  project: string;
  status: 'success' | 'warning' | 'danger' | 'info';
  weeks: number;
  budget: string;
}

export interface SalesSummary {
  totalSales: string;
  thisMonth: string;
  thisWeek: string;
}

export interface ChartSeries {
  name: string;
  data: number[];
}
