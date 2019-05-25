import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccommodationComponent } from './accommodation/accommodation.component';
import { HttpClientModule } from '@angular/common/http';

import { LayoutComponent } from './layout/layout.component';
import { MaterialModule } from './material/material.module';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './navigation/header/header.component';
import { SidenavListComponent } from './navigation/sidenav-list/sidenav-list.component';
import {TravelComponent} from './travel/travel.component';
import { EmployeeComponent } from './employee/employee.component';
import { AccommodationDialogComponent } from './accommodation/accommodation-dialog/accommodation-dialog.component';
import { MAT_DIALOG_DEFAULT_OPTIONS, MatDialog, MatDialogModule } from '@angular/material';

@NgModule({
    declarations: [
        AppComponent,
        LayoutComponent,
        HomeComponent,
        HeaderComponent,
        SidenavListComponent,
        AccommodationComponent,
        TravelComponent,
        EmployeeComponent
        TravelComponent,
        AccommodationDialogComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MaterialModule,
        FlexLayoutModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule
    ],
    providers: [
        MatDialogModule,
        {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}
    ],
    bootstrap: [AppComponent],
    entryComponents: [AccommodationDialogComponent]
})
export class AppModule { }
