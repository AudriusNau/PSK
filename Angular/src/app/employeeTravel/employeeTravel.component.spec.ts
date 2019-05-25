import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeTravelComponent } from './employeeTravel.component';

describe('EmployeeTravelComponent', () => {
  let component: EmployeeTravelComponent;
  let fixture: ComponentFixture<EmployeeTravelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmployeeTravelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeeTravelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
