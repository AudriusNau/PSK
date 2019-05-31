import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetDateDialogComponent } from './set-date-dialog.component';

describe('SetDateDialogComponent', () => {
  let component: SetDateDialogComponent;
  let fixture: ComponentFixture<SetDateDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetDateDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetDateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
