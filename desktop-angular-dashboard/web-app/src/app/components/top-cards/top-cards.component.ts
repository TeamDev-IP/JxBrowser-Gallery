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

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { 
  lucideTrendingUp,
  lucideTrendingDown
} from '@ng-icons/lucide';
import { TopCard } from '../../models/dashboard.models';
import { BackendService } from '../../services/backend.service';

@Component({
  selector: 'app-top-cards',
  standalone: true,
  imports: [CommonModule, NgIconComponent],
  viewProviders: [provideIcons({ 
    lucideTrendingUp,
    lucideTrendingDown
  })],
  template: `
    <div class="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
      <div *ngFor="let card of topCards" 
           class="rounded-xl border border-border bg-card p-6">
        <div class="flex flex-row items-center justify-between space-y-0 pb-2">
          <h3 class="text-sm font-medium text-muted-foreground">{{ card.subtitle }}</h3>
          <div class="flex items-center gap-1.5 rounded-lg bg-secondary/50 px-2.5 py-1 text-xs font-medium">
            <ng-icon 
              [name]="card.trendDirection ? 'lucideTrendingUp' : 'lucideTrendingDown'" 
              class="h-3 w-3"
              [ngClass]="card.trendDirection ? 'text-foreground' : 'text-foreground'" 
            />
            <span class="text-foreground">
              {{ card.trendValue }}
            </span>
          </div>
        </div>
        <div class="pt-2">
          <div class="text-2xl font-bold text-foreground">{{ card.title }}</div>
          <p class="text-xs text-muted-foreground mt-1 flex items-center gap-1">
            <span>{{ card.trendDescription }}</span>
            <ng-icon 
              [name]="card.trendDirection ? 'lucideTrendingUp' : 'lucideTrendingDown'" 
              class="h-3 w-3 text-muted-foreground" 
            />
          </p>
        </div>
      </div>
    </div>
  `
})
export class TopCardsComponent implements OnInit {
  topCards: TopCard[] = [];

  constructor(private backendService: BackendService) {}

  ngOnInit(): void {
    this.topCards = this.backendService.getTopCards();
  }
}
