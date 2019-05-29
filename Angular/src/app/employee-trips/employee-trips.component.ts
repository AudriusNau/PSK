import { Component, OnInit } from '@angular/core';
import {Travel} from "../entities/travel";
import {HttpClient} from "@angular/common/http";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {Url} from "../http/url";
import {Office} from "../entities/office";
import {NewTravelDialogComponent} from "../organiser-travels/new-travel-dialog/new-travel-dialog.component";
import {EmployeeTravel} from "../entities/employeeTravel";

@Component({
  selector: 'app-employee-trips',
  templateUrl: './employee-trips.component.html',
  styleUrls: ['./employee-trips.component.scss']
})
export class EmployeeTripsComponent implements OnInit {

  employeeTravel: Array<EmployeeTravel> = [];
  approvedTrips: Array<EmployeeTravel> = [];
  public displayedColumns1: string[] = ['firstName', 'lastName', 'date',
    'departureOffice', 'arrivalOffice', 'decline' , 'approve'];
  public displayedColumns2: string[] = ['firstName', 'lastName', 'date', 'room', 'flight', 'carRent',
    'departureOffice', 'arrivalOffice'];
  constructor(private http: HttpClient, private dialog: MatDialog, private userService: UserService, private router: Router) { }

  ngOnInit() {
    if (this.userService.user)
      this.loadTable();
    else
      this.router.navigate(['/login']);
  }

  loadTable() {
    this.http.get(Url.get('employeeTravel/get/getPendingTravelsForEmployee/' + this.userService.user.id))
      .subscribe((employeeTravel: Array<EmployeeTravel>) => {
        this.employeeTravel = employeeTravel;
        this.employeeTravel.forEach(item => {
          item.travel.startDate = item.travel.startDate.replace(/_/g, " - ");
          this.http.get(Url.get('travel/get/getDepartureOfficeByTravelId/' + item.travel.id))
            .subscribe((office: Office) => {
              item.travel.departureOffice = office.name;
            });
          this.http.get(Url.get('travel/get/getArrivalOfficeByTravelId/' + item.travel.id))
            .subscribe((office: Office) => {
              item.travel.arrivalOffice = office.name;
            });
        });
      });

    this.http.get(Url.get('employeeTravel/get/getAcceptedTravelsForEmployee/' + this.userService.user.id))
      .subscribe((employeeTravel: Array<EmployeeTravel>) => {
        this.approvedTrips = employeeTravel;
        this.approvedTrips.forEach(item => {
          item.travel.startDate = item.travel.startDate.replace(/_/g, " - ");
          this.http.get(Url.get('travel/get/getDepartureOfficeByTravelId/' + item.travel.id))
            .subscribe((office: Office) => {
              item.travel.departureOffice = office.name;
            });
          this.http.get(Url.get('travel/get/getArrivalOfficeByTravelId/' + item.travel.id))
            .subscribe((office: Office) => {
              item.travel.arrivalOffice = office.name;
            });
        });
      });
  }
  onDeclineCLick(id: number) {
    this.http.delete(Url.get("employeeTravel/delete/" + id))
      .subscribe(() => this.loadTable());
  }
  onApproveCLick(id: number) {
    this.http.put(Url.get("employeeTravel/accept/" + id), {})
      .subscribe(() => this.loadTable());
  }
}
