import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgIconComponent, provideIcons } from '@ng-icons/core';
import { lucideEllipsisVertical } from '@ng-icons/lucide';
import { Project } from '../../models/dashboard.models';
import { BackendService } from '../../services/backend.service';

@Component({
  selector: 'app-projects-table',
  standalone: true,
  imports: [CommonModule, NgIconComponent],
  viewProviders: [provideIcons({ lucideEllipsisVertical })],
  template: `
    <div class="rounded-xl border border-border bg-card">
      <div class="p-6 pb-4">
        <div class="flex items-center justify-between">
          <div>
            <h3 class="text-lg font-semibold text-foreground">Project Overview</h3>
            <p class="text-sm text-muted-foreground">Current active projects</p>
          </div>
          <button class="text-sm text-muted-foreground hover:text-foreground transition-colors">
            View All
          </button>
        </div>
      </div>
      
      <div class="px-6 pb-6">
        <div class="rounded-lg border border-border overflow-hidden">
          <table class="w-full">
            <thead>
              <tr class="border-b border-border bg-muted/30">
                <th class="h-10 px-4 text-left text-xs font-medium text-muted-foreground">Team Lead</th>
                <th class="h-10 px-4 text-left text-xs font-medium text-muted-foreground">Project</th>
                <th class="h-10 px-4 text-left text-xs font-medium text-muted-foreground">Status</th>
                <th class="h-10 px-4 text-left text-xs font-medium text-muted-foreground">Weeks</th>
                <th class="h-10 px-4 text-left text-xs font-medium text-muted-foreground">Budget</th>
                <th class="h-10 px-4 text-right text-xs font-medium text-muted-foreground"></th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let project of projects; let i = index" 
                  class="border-b border-border last:border-0 hover:bg-muted/30 transition-colors">
                <td class="p-4">
                  <div class="flex items-center gap-3">
                    <div class="flex h-8 w-8 items-center justify-center rounded-full bg-muted text-sm font-medium text-muted-foreground">
                      {{ project.name.charAt(0) }}
                    </div>
                    <div>
                      <p class="text-sm font-medium text-foreground">{{ project.name }}</p>
                      <p class="text-xs text-muted-foreground">{{ project.email }}</p>
                    </div>
                  </div>
                </td>
                <td class="p-4">
                  <span class="text-sm text-foreground">{{ project.project }}</span>
                </td>
                <td class="p-4">
                  <span class="inline-flex items-center gap-2 rounded-md border border-border px-2.5 py-1 text-xs font-medium text-foreground">
                    <span class="h-2 w-2 rounded-full" [ngClass]="getStatusDotClass(project.status)"></span>
                    {{ getStatusText(project.status) }}
                  </span>
                </td>
                <td class="p-4">
                  <span class="text-sm text-muted-foreground">{{ project.weeks }}</span>
                </td>
                <td class="p-4">
                  <span class="text-sm font-medium text-foreground">{{ project.budget }}</span>
                </td>
                <td class="p-4 text-right">
                  <button class="inline-flex h-8 w-8 items-center justify-center rounded-lg text-muted-foreground hover:bg-muted hover:text-foreground transition-colors">
                    <ng-icon name="lucideEllipsisVertical" class="h-4 w-4" />
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  `
})
export class ProjectsTableComponent implements OnInit {
  projects: Project[] = [];

  constructor(private backendService: BackendService) {}

  ngOnInit(): void {
    this.projects = this.backendService.getProjects();
  }

  getStatusDotClass(status: string): string {
    const statusMap: Record<string, string> = {
      'success': 'bg-emerald-500',
      'info': 'bg-muted-foreground animate-pulse',
      'warning': 'bg-muted-foreground animate-pulse',
      'danger': 'bg-rose-500'
    };
    return statusMap[status] || 'bg-muted-foreground';
  }

  getStatusText(status: string): string {
    const statusMap: Record<string, string> = {
      'success': 'Done',
      'info': 'In Process',
      'warning': 'Pending',
      'danger': 'Delayed'
    };
    return statusMap[status] || status;
  }
}
