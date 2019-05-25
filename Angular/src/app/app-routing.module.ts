import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccommodationComponent } from './accommodation/accommodation.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './Login/login.component';

const routes: Routes = [
    { path: 'login', component: LoginComponent},
    { path: 'accomodation', component: AccommodationComponent},
    { path: 'home', component: HomeComponent},
    { path: '', redirectTo: '/home', pathMatch: 'full' }

  ];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
