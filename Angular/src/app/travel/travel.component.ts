import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Travel } from '../entities/travel';
import { Url } from '../http/url';
import { Office } from '../entities/office';
import { Organiser } from '../entities/organiser';
import { Router } from '@angular/router';
import {forEach} from "@angular/router/src/utils/collection";
import {printLine} from "tslint/lib/verify/lines";

@Component({
    selector: 'app-travel',
    templateUrl: './travel.component.html',
    styleUrls: ['./travel.component.scss']
})
export class TravelComponent implements OnInit {
    items: Array<Travel> = [];
    selectedTravels: Array<Travel> = [];
    public displayedColumns: string[] = ['date', 'price', 'departureOffice', 'arrivalOffice', 'organiser', 'info'];
    constructor(private http: HttpClient, private router: Router) { }
    ngOnInit() {
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
        Url.get('employeeTravel/merge'),
        {baseTravelId: this.items[0].id, travels: this.items.map(item => item.id)}
        ).subscribe();
    }
}

