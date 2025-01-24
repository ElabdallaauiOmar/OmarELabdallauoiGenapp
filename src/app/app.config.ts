import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withFetch } from '@angular/common/http'; // Import the necessary HTTP features

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), // Enables event coalescing in zone change detection
    provideRouter(routes), // Provides the router for navigation
    provideHttpClient(withFetch()) // Adds HTTP client with the Fetch API
  ]
};
