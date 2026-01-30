import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { 
  lucideLayoutDashboard, 
  lucideChartLine, 
  lucideUsers, 
  lucideFolderKanban,
  lucideSettings,
  lucideSearch,
  lucideCircleHelp,
  lucideChevronDown,
  lucideDatabase,
  lucideFileText,
  lucidePenTool
} from '@ng-icons/lucide';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, NgIconComponent],
  viewProviders: [provideIcons({ 
    lucideLayoutDashboard, 
    lucideChartLine, 
    lucideUsers, 
    lucideFolderKanban,
    lucideSettings,
    lucideSearch,
    lucideCircleHelp,
    lucideChevronDown,
    lucideDatabase,
    lucideFileText,
    lucidePenTool
  })],
  template: `
    <div class="flex h-screen w-64 flex-col border-r border-border bg-card">
      <!-- Logo -->
      <div class="flex h-14 items-center border-b border-border px-4">
        <div class="flex items-center gap-2">
          <div class="flex h-8 w-8 items-center justify-center rounded-lg bg-primary text-primary-foreground">
            <span class="text-sm font-bold">JX</span>
          </div>
          <span class="font-semibold text-foreground">JxBrowser</span>
        </div>
      </div>

      <!-- Navigation -->
      <div class="flex-1 overflow-y-auto py-4">
        <!-- Home Section -->
        <div class="px-3 py-2">
          <h2 class="mb-2 px-2 text-xs font-semibold tracking-tight text-muted-foreground">
            Home
          </h2>
          <div class="space-y-1">
            <a class="flex items-center gap-3 rounded-lg bg-accent px-3 py-2 text-accent-foreground transition-colors">
              <ng-icon name="lucideLayoutDashboard" class="h-4 w-4" />
              <span class="text-sm">Dashboard</span>
            </a>
            <a class="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground hover:bg-accent hover:text-accent-foreground transition-colors cursor-pointer">
              <ng-icon name="lucideChartLine" class="h-4 w-4" />
              <span class="text-sm">Analytics</span>
            </a>
            <a class="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground hover:bg-accent hover:text-accent-foreground transition-colors cursor-pointer">
              <ng-icon name="lucideFolderKanban" class="h-4 w-4" />
              <span class="text-sm">Projects</span>
            </a>
            <a class="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground hover:bg-accent hover:text-accent-foreground transition-colors cursor-pointer">
              <ng-icon name="lucideUsers" class="h-4 w-4" />
              <span class="text-sm">Team</span>
            </a>
          </div>
        </div>

        <!-- Documents Section -->
        <div class="px-3 py-2">
          <h2 class="mb-2 px-2 text-xs font-semibold tracking-tight text-muted-foreground">
            Documents
          </h2>
          <div class="space-y-1">
            <a class="flex items-center justify-between rounded-lg px-3 py-2 text-muted-foreground hover:bg-accent hover:text-accent-foreground transition-colors cursor-pointer">
              <div class="flex items-center gap-3">
                <ng-icon name="lucideDatabase" class="h-4 w-4" />
                <span class="text-sm">Data Library</span>
              </div>
              <ng-icon name="lucideChevronDown" class="h-4 w-4" />
            </a>
            <a class="flex items-center justify-between rounded-lg px-3 py-2 text-muted-foreground hover:bg-accent hover:text-accent-foreground transition-colors cursor-pointer">
              <div class="flex items-center gap-3">
                <ng-icon name="lucideFileText" class="h-4 w-4" />
                <span class="text-sm">Reports</span>
              </div>
              <ng-icon name="lucideChevronDown" class="h-4 w-4" />
            </a>
          </div>
        </div>
      </div>

      <!-- Footer -->
      <div class="border-t border-border p-3">
        <div class="space-y-1">
          <a class="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground hover:bg-accent hover:text-accent-foreground transition-colors cursor-pointer">
            <ng-icon name="lucideSettings" class="h-4 w-4" />
            <span class="text-sm">Settings</span>
          </a>
          <a class="flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground hover:bg-accent hover:text-accent-foreground transition-colors cursor-pointer">
            <ng-icon name="lucideCircleHelp" class="h-4 w-4" />
            <span class="text-sm">Get Help</span>
          </a>
        </div>
      </div>
    </div>
  `
})
export class SidebarComponent {}
