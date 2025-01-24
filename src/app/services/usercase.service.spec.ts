import { TestBed } from '@angular/core/testing';

import { UsercaseService } from './usercase.service';

describe('UsercaseService', () => {
  let service: UsercaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsercaseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
