import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-prompt-form',
  standalone: true,
  imports: [CommonModule, FormsModule], // Do not import HttpClientModule here
  templateUrl: './prompt-form.component.html',
  styleUrls: ['./prompt-form.component.scss']
})
export class PromptFormComponent {
  besoin: string = '';
  maxTokens: number = 500;
  userCaseResponse: string = 'No data yet.';
  isLoading: boolean = false;

  constructor() {}

  onSubmit() {
    if (!this.besoin.trim()) {
      this.userCaseResponse = 'Error: Besoin cannot be empty.';
      return;
    }

    this.userCaseResponse = 'Form submitted successfully!';
  }
}
