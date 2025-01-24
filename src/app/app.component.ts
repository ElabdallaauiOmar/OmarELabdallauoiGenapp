import { Component, Provider } from '@angular/core';
import { PromptFormComponent } from './components/prompt-form/prompt-form.component';
import { UserCasesComponent } from './components/user-cases/user-cases.component';
import { CommonModule } from '@angular/common';
import { provideHttpClient, withFetch } from '@angular/common/http'; // New HttpClient approach for Angular 18
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    PromptFormComponent,
    UserCasesComponent,
    CommonModule
  ],

  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {}
