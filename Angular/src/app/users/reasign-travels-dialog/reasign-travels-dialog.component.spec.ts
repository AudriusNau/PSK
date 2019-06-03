import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReasignTravelsDialogComponent } from './reasign-travels-dialog.component';

describe('ReasignTravelsDialogComponent', () => {
  let component: ReasignTravelsDialogComponent;
  let fixture: ComponentFixture<ReasignTravelsDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReasignTravelsDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReasignTravelsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
