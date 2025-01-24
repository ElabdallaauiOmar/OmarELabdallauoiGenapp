import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCasesComponent } from './user-cases.component';

describe('UserCasesComponent', () => {
  let component: UserCasesComponent;
  let fixture: ComponentFixture<UserCasesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserCasesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserCasesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
