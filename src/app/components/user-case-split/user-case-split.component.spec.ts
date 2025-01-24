import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCaseSplitComponent } from './user-case-split.component';

describe('UserCaseSplitComponent', () => {
  let component: UserCaseSplitComponent;
  let fixture: ComponentFixture<UserCaseSplitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserCaseSplitComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserCaseSplitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
