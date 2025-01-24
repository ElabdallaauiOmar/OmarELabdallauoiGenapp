import { Component } from '@angular/core';
import { UsercaseService } from '../../services/usercase.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
 // Make sure the path is correct

 @Component({
  selector: 'app-user-case-split',
  standalone: true,
  templateUrl: './user-case-split.component.html',
  styleUrls: ['./user-case-split.component.css'],
  imports: [CommonModule, FormsModule],  // Add FormsModule here
})
export class UserCaseSplitComponent {
  userCaseId: number = 0;  // Initialize userCaseId
  result: any;
  errorMessage: string = '';  // Initialize error message

  constructor(private usercaseService: UsercaseService) {}

  onFetchUserCases() {
    if (!this.userCaseId) {
      this.errorMessage = 'Please enter a valid UserCase ID.';
      return;
    }

    this.usercaseService.splitUserCase(this.userCaseId).subscribe(
      (response) => {
        this.result = response;
        this.errorMessage;
      },
      (error) => {
        this.errorMessage = 'Error: Failed to fetch data. Please check the console for details.';
        console.error('Error:', error);
      }
    );
  }
  get selectedCases() {
    return this.result ? this.result.filter((caseItem: any) => caseItem.selected) : [];
  }

  
}