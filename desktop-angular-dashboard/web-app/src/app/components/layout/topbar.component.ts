import { Component } from '@angular/core';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { lucideSearch, lucideBell, lucideUser } from '@ng-icons/lucide';

@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [NgIconComponent],
  viewProviders: [provideIcons({ lucideSearch, lucideBell, lucideUser })],
  template: `
    <div class="flex h-14 items-center justify-between border-b border-border bg-card px-6">
      <div>
        <h1 class="text-lg font-semibold text-foreground">Dashboard</h1>
      </div>
      
      <div class="flex items-center gap-4">
        <!-- Status Badge -->
        <div class="flex items-center gap-2 rounded-full px-3 py-1 text-xs bg-emerald-500/10 text-emerald-500">
          <span class="h-2 w-2 rounded-full bg-emerald-500"></span>
          JxBrowser Connected
        </div>

        <!-- Search -->
        <button class="flex h-9 w-9 items-center justify-center rounded-lg text-muted-foreground hover:bg-accent hover:text-accent-foreground transition-colors">
          <ng-icon name="lucideSearch" class="h-4 w-4" />
        </button>

        <!-- Notifications -->
        <button class="flex h-9 w-9 items-center justify-center rounded-lg text-muted-foreground hover:bg-accent hover:text-accent-foreground transition-colors">
          <ng-icon name="lucideBell" class="h-4 w-4" />
        </button>

        <!-- User Avatar -->
        <button class="flex h-8 w-8 items-center justify-center rounded-full bg-muted text-muted-foreground hover:bg-accent transition-colors">
          <ng-icon name="lucideUser" class="h-4 w-4" />
        </button>
      </div>
    </div>
  `
})
export class TopbarComponent {}
