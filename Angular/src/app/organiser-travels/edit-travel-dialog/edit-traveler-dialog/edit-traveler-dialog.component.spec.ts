import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTravelerDialogComponent } from './edit-traveler-dialog.component';

describe('EditTravelerDialogComponent', () => {
  let component: EditTravelerDialogComponent;
  let fixture: ComponentFixture<EditTravelerDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditTravelerDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTravelerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
