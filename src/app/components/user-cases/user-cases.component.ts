import { Component, OnInit } from '@angular/core';
import { UsercaseService } from '../../services/usercase.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // For ngModel binding
import { Router } from '@angular/router'; // Import Router for navigation

@Component({
  selector: 'app-user-cases',
  standalone: true,
  imports: [CommonModule, FormsModule], // Import FormsModule for ngModel
  templateUrl: './user-cases.component.html',
  styleUrls: ['./user-cases.component.css']
})
export class UserCasesComponent implements OnInit {
  userCases: any[] = []; // Array to hold user cases
  error: string | null = null; // Error message, if any
  isLoading: boolean = false; // Loading state to manage UI during request
  besoin: string = ''; // To bind the input for 'besoin'
  maxTokens: number = 500; // Default maxTokens

  constructor(private usercaseService: UsercaseService, private router: Router) {} // Inject Router

  ngOnInit(): void {
    // Optionally, you can load an initial user case on component initialization.
  }

  // Handle form submission and fetch new user case
  onSubmit(): void {
    this.userCases = [];
    this.error = null;
    this.isLoading = true;

    if (this.besoin.trim() === '') {
      this.error = 'Besoin is required!';
      this.isLoading = false;
      return;
    }

    this.usercaseService.generateUserCases(this.besoin, this.maxTokens).subscribe({
      next: (response) => {
        this.isLoading = false;
        if (response && response.id && response.content) {
          this.userCases = [response];
        } else {
          this.error = 'Unexpected response format';
        }
      },
      error: (err) => {
        this.isLoading = false;
        console.error(err);
        this.error = 'Failed to load user cases. Please try again.';
      }
    });
  }

  // Navigate to user-case-split page
  navigateToUserCaseSplit(): void {
    this.router.navigate(['/user-case-split']);
  }
}
