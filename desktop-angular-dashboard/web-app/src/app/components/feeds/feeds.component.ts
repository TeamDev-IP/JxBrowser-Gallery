import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { 
  lucideBell, 
  lucideServer, 
  lucideShoppingBag, 
  lucideUserPlus,
  lucideShieldCheck,
  lucideCircle
} from '@ng-icons/lucide';
import { Feed } from '../../models/dashboard.models';
import { BackendService } from '../../services/backend.service';

@Component({
  selector: 'app-feeds',
  standalone: true,
  imports: [CommonModule, NgIconComponent],
  viewProviders: [provideIcons({ 
    lucideBell, 
    lucideServer, 
    lucideShoppingBag, 
    lucideUserPlus,
    lucideShieldCheck,
    lucideCircle
  })],
  template: `
    <div class="rounded-xl border border-border bg-card h-full flex flex-col">
      <div class="p-6 pb-4 shrink-0">
        <h3 class="text-lg font-semibold text-foreground">Recent Activity</h3>
        <p class="text-sm text-muted-foreground">Latest system events</p>
      </div>
      <div class="px-6 pb-6 flex-1 overflow-y-auto">
        <div class="space-y-0">
          <div *ngFor="let feed of feeds; let last = last" 
               class="flex items-start gap-3 py-3"
               [class.border-b]="!last"
               [class.border-border]="!last">
            <!-- Simple dot indicator -->
            <div class="mt-1.5 h-2 w-2 shrink-0 rounded-full" 
                 [ngClass]="getDotClass(feed.bgClass)"></div>
            
            <!-- Content -->
            <div class="flex-1 min-w-0 space-y-1">
              <p class="text-sm text-foreground leading-tight">{{ feed.task }}</p>
              <p class="text-xs text-muted-foreground">{{ feed.time }}</p>
            </div>
            
            <!-- Icon (subtle) -->
            <ng-icon [name]="getIconName(feed.icon)" 
                     class="h-4 w-4 text-muted-foreground/50 shrink-0" />
          </div>
        </div>
      </div>
    </div>
  `
})
export class FeedsComponent implements OnInit {
  feeds: Feed[] = [];

  constructor(private backendService: BackendService) {}

  ngOnInit(): void {
    this.feeds = this.backendService.getFeeds();
  }

  getIconName(icon: string): string {
    const iconMap: Record<string, string> = {
      'bi bi-bell': 'lucideBell',
      'bi bi-hdd': 'lucideServer',
      'bi bi-bag-check': 'lucideShoppingBag',
      'bi bi-person': 'lucideUserPlus',
      'bi bi-shield-check': 'lucideShieldCheck'
    };
    return iconMap[icon] || 'lucideBell';
  }

  getDotClass(bgClass: string): string {
    // Use subtle, muted colors that match the dark theme
    const dotMap: Record<string, string> = {
      'bg-info': 'bg-blue-400/60',
      'bg-success': 'bg-emerald-400/60',
      'bg-warning': 'bg-amber-400/60',
      'bg-danger': 'bg-rose-400/60',
      'bg-primary': 'bg-violet-400/60'
    };
    return dotMap[bgClass] || 'bg-muted-foreground/60';
  }
}
