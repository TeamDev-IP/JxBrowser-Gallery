import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './components/layout/sidebar.component';
import { TopbarComponent } from './components/layout/topbar.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    SidebarComponent,
    TopbarComponent,
    DashboardComponent
  ],
  template: `
    <div class="flex h-screen bg-background">
      <!-- Sidebar -->
      <app-sidebar></app-sidebar>

      <!-- Main Content Area -->
      <div class="flex flex-1 flex-col overflow-hidden">
        <!-- Top Bar -->
        <app-topbar></app-topbar>

        <!-- Main Content -->
        <main class="flex-1 overflow-y-auto p-6">
          <app-dashboard></app-dashboard>
        </main>
      </div>
    </div>
  `
})
export class AppComponent {
  title = 'Angular + JxBrowser Dashboard';
}
