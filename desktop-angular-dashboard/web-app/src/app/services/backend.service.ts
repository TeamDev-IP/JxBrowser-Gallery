/*
 * Copyright 2026, TeamDev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
