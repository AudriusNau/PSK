import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeTripsComponent } from './employee-trips.component';

describe('EmployeeTripsComponent', () => {
  let component: EmployeeTripsComponent;
  let fixture: ComponentFixture<EmployeeTripsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeeTripsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeTripsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
