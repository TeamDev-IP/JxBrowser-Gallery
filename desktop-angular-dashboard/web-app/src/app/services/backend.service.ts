import { Injectable } from '@angular/core';
import { TopCard, Feed, Project, SalesSummary, ChartSeries } from '../models/dashboard.models';

declare global {
  interface Window {
    backend: {
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
    return JSON.parse(window.backend.getTopCards());
  }

  getFeeds(): Feed[] {
    return JSON.parse(window.backend.getFeeds());
  }

  getProjects(): Project[] {
    return JSON.parse(window.backend.getProjects());
  }

  getSalesSummary(): SalesSummary {
    return JSON.parse(window.backend.getSalesSummary());
  }

  getChartData(timeRange: string = '3m'): ChartSeries[] {
    return JSON.parse(window.backend.getChartData(timeRange));
  }
}
