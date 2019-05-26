import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccommodationComponent } from './accommodation/accommodation.component';
import { HttpClientModule } from '@angular/common/http';

import { LayoutComponent } from './layout/layout.component';
import { MaterialModule } from './material/material.module';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './navigation/header/header.component';
import { SidenavListComponent } from './navigation/sidenav-list/sidenav-list.component';
import { LoginComponent } from './Login/login.component';
import {TravelComponent} from './travel/travel.component';
import { EmployeeTravelComponent } from './employeeTravel/employeeTravel.component';
import { AccommodationDialogComponent } from './accommodation/accommodation-dialog/accommodation-dialog.component';
import { MAT_DIALOG_DEFAULT_OPTIONS, MatDialog, MatDialogModule } from '@angular/material';
import { OrganiserTravelsComponent } from './organiser-travels/organiser-travels.component';
import { NewTravelDialogComponent } from './organiser-travels/new-travel-dialog/new-travel-dialog.component';
import { UserService } from './services/user.service';
import { CookieService } from 'ngx-cookie-service';
import { EditTravelDialogComponent } from './organiser-travels/edit-travel-dialog/edit-travel-dialog.component';
import { NewTravelerDialogComponent } from './organiser-travels/edit-travel-dialog/new-traveler-dialog/new-traveler-dialog.component';

@NgModule({
    declarations: [
        AppComponent,
        LayoutComponent,
        HomeComponent,
        HeaderComponent,
        SidenavListComponent,
        AccommodationComponent,
        LoginComponent,
        TravelComponent,
        TravelComponent,
        AccommodationDialogComponent,
        OrganiserTravelsComponent,
        NewTravelDialogComponent,
        EmployeeTravelComponent,
        AccommodationDialogComponent,
        EditTravelDialogComponent,
        NewTravelerDialogComponent,
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MaterialModule,
        FlexLayoutModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    providers: [
        CookieService,
        UserService,
        MatDialogModule,
        {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}
    ],
    bootstrap: [AppComponent],
    entryComponents: [
        AccommodationDialogComponent,
        NewTravelDialogComponent,
        EditTravelDialogComponent,
        NewTravelerDialogComponent,
    ]
})
export class AppModule { }
