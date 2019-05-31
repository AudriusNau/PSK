import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CalendarComponent } from './calendar.component';
import { SelectAutocompleteModule } from 'mat-select-autocomplete';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
// import { FlatpickrModule } from 'angularx-flatpickr';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { MaterialModule } from '../material/material.module';
import { SetDateDialogComponent } from './set-date-dialog/set-date-dialog.component';
import { MatDialogModule, MAT_DIALOG_DEFAULT_OPTIONS } from '@angular/material';

@NgModule({
  declarations: [
    CalendarComponent,
    SetDateDialogComponent
  ],
  providers: [
    MatDialogModule,
    { provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: { hasBackdrop: false } },
  ],
  imports: [
    CommonModule,
    FormsModule,
    NgbModalModule,
    BrowserModule,
    BrowserAnimationsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    }),
    SelectAutocompleteModule,
    MaterialModule,
  ],
  bootstrap: [CalendarComponent]
})
export class AppCalendarModule { }
