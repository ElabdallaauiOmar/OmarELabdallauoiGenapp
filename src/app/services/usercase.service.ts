import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsercaseService {
  constructor(private http: HttpClient) {}

  // Send the POST request to generate user cases
  generateUserCases(besoin: string, maxTokens: number): Observable<any> {
    const data = {
      besoin: besoin,
      maxTokens: maxTokens
    };

    return this.http.post<any>('http://localhost:8080/api/huggingface/generate-user-cases', data);
  }

  splitUserCase(userCaseId: number): Observable<any> {
    const apiUrl = `http://localhost:8080/api/user-cases/split/${userCaseId}`;
    return this.http.post<any>(apiUrl, {});
  }
}
