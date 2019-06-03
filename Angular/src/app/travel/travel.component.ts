import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Travel } from '../entities/travel';
import { Url } from '../http/url';
import { Office } from '../entities/office';
import { Organiser } from '../entities/organiser';
import { Router } from '@angular/router';
import {forEach} from "@angular/router/src/utils/collection";
import {printLine} from "tslint/lib/verify/lines";
import {UserService} from "../services/user.service";

@Component({
    selector: 'app-travel',
    templateUrl: './travel.component.html',
    styleUrls: ['./travel.component.scss']
})
export class TravelComponent implements OnInit {
    items: Array<Travel> = [];
    selectedTravels: Array<Travel> = [];
    public displayedColumns: string[] = ['startDate', 'endDate', 'price', 'departureOffice', 'arrivalOffice', 'organiser', 'info'];
    constructor(private http: HttpClient, private userService: UserService, private router: Router) { }
  ngOnInit() {
    if (this.userService.user)
      this.loadTable();
    else
      this.router.navigate(['/login']);
  }
  loadTable() {
        this.http.get(Url.get('travel/get/all'))
            .subscribe((travels: Array<Travel>) => {
                this.items = travels;
                this.items.forEach(item => {
                    item.isSelected = false;
                    this.http.get(Url.get('travel/get/getDepartureOfficeByTravelId/' + item.id))
                        .subscribe((office: Office) => {
                            item.departureOffice = office.name;
                        });
                    this.http.get(Url.get('travel/get/getArrivalOfficeByTravelId/' + item.id))
                        .subscribe((office: Office) => {
                            item.arrivalOffice = office.name;
                        });
                    this.http.get(Url.get('travel/get/getOrganiserByTravelId/' + item.id))
                        .subscribe((organiser: Organiser) => {
                            item.organiser = organiser.firstName.toString() + ' ' + organiser.lastName.toString();
                        });
                });
            });
    }

    goToDetails(id: number) {
        this.router.navigate(['/travel', id]);
    }
    merge() {
      this.selectedTravels = this.items.filter(item => item.isSelected === true);
      this.http.put(
        Url.get('travel/merge'),
        {baseTravelId: this.selectedTravels[0].id, travels: this.selectedTravels.splice (1, 1).map(item => item.id)}
        ).subscribe(response => {
          if (response == null) {alert('Offices does not match'); }
      else {this.loadTable(), error1 => alert('Dates does not match the rules\n ' +
        'Rules:\n 1 dates should be similar (+- 1 day)\n 2 destination should be the same'); }
         });
    }
}

