import { Injectable } from '@angular/core';
import { TopCard, Feed, Project, SalesSummary, ChartSeries } from '../models/dashboard.models';

declare global {
  interface Window {
    backend?: {
      getTopCards(): string;
      getFeeds(): string;
      getProjects(): string;
      getSalesSummary(): string;
      getChartData(timeRange: string): string;
    };
  }
}

/**
 * Handles communication with the Java backend via JxBrowser's JS-Java Bridge.
 */
@Injectable({
  providedIn: 'root'
})
export class BackendService {

  getTopCards(): TopCard[] {
    if (window.backend) {
      return JSON.parse(window.backend.getTopCards());
    }
    return [];
  }

  getFeeds(): Feed[] {
    if (window.backend) {
      return JSON.parse(window.backend.getFeeds());
    }
    return [];
  }

  getProjects(): Project[] {
    if (window.backend) {
      return JSON.parse(window.backend.getProjects());
    }
    return [];
  }

  getSalesSummary(): SalesSummary {
    if (window.backend) {
      return JSON.parse(window.backend.getSalesSummary());
    }
    return { totalSales: '$0', thisMonth: '$0', thisWeek: '$0' };
  }

  getChartData(timeRange: string = '3m'): ChartSeries[] {
    if (window.backend) {
      return JSON.parse(window.backend.getChartData(timeRange));
    }
    return [];
  }
}
