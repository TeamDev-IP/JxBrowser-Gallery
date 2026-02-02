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
