import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccommodationComponent } from './accommodation/accommodation.component';
import { HomeComponent } from './home/home.component';
import {TravelComponent} from './travel/travel.component';
import {EmployeeComponent} from './employee/employee.component';

const routes: Routes = [
    { path: 'accommodation', component: AccommodationComponent},
    { path: 'travel', component: TravelComponent},
    { path: 'home', component: HomeComponent},
    { path: 'employee', component: EmployeeComponent},
    { path: '', redirectTo: '/home', pathMatch: 'full' }
  ];
@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
