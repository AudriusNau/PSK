import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccommodationComponent } from './accommodation/accommodation.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './Login/login.component';
import {TravelComponent} from './travel/travel.component';
import { OrganiserTravelsComponent } from './organiser-travels/organiser-travels.component';
import {EmployeeTravelComponent} from './employeeTravel/employeeTravel.component';
import {EmployeeTripsComponent} from './employee-trips/employee-trips.component';
import { CalendarComponent } from './calendar/calendar.component';
import { UsersComponent } from './users/users.component';

const routes: Routes = [
    { path: 'calendar', component: CalendarComponent},
    { path: 'login', component: LoginComponent},
    { path: 'accommodation', component: AccommodationComponent},
    { path: 'accommodation', component: AccommodationComponent},
    { path: 'travel', component: TravelComponent},
    { path: 'organised', component: OrganiserTravelsComponent},
    { path: 'myTrips', component: EmployeeTripsComponent},
    { path: 'home', component: HomeComponent},
    { path: 'travel/:id', component: EmployeeTravelComponent},
    { path: 'users', component: UsersComponent},
    { path: '', redirectTo: '/home', pathMatch: 'full' }

  ];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
