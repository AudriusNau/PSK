import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewTravelerDialogComponent } from './new-traveler-dialog.component';

describe('NewTravelerDialogComponent', () => {
  let component: NewTravelerDialogComponent;
  let fixture: ComponentFixture<NewTravelerDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewTravelerDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewTravelerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
