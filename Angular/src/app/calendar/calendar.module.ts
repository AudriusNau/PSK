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

@NgModule({
  declarations: [
    CalendarComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    NgbModalModule,
    BrowserModule,
    BrowserAnimationsModule,
    // FlatpickrModule.forRoot(),
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    }),
    SelectAutocompleteModule,
  ],
  bootstrap: [CalendarComponent]
})
export class AppCalendarModule { }
