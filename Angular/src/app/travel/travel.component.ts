import {Component, OnInit, ViewChild} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Travel } from '../entities/travel';
import { Url } from '../http/url';
import { Office } from '../entities/office';
import {Organiser} from "../entities/organiser";
import {MatPaginator, MatTableDataSource} from '@angular/material';

@Component({
    selector: 'app-travel',
    templateUrl: './travel.component.html',
    styleUrls: ['./travel.component.scss']
})
export class TravelComponent implements OnInit {

    items: Array<Travel> = [];
    public displayedColumns: string[] = ['date', 'price', 'departureOffice', 'arrivalOffice', 'organiser' ];
    dataSource: MatTableDataSource<Travel>;
    constructor(private http: HttpClient) { }
    @ViewChild(MatPaginator) paginator: MatPaginator;
    ngOnInit() {
      this.http.get(Url.get('travel/get/all'))
            .subscribe((travels: Array<Travel>) => {
                this.items = travels;
                this.items.forEach(item => {
                    this.http.get(Url.get('travel/get/getDepartureOfficeByTravelId/' + item.id))
                        .subscribe((office: Office) => {
                            item.departureOffice = office.name;
                        });
                });
                this.items.forEach(item => {
                  this.http.get(Url.get('travel/get/getArrivalOfficeByTravelId/' + item.id))
                    .subscribe((office: Office) => {
                      item.arrivalOffice = office.name;
                    });
              });
                this.items.forEach(item => {
                  this.http.get(Url.get('travel/get/getOrganiserByTravelId/' + item.id))
                    .subscribe((organiser: Organiser) => {
                      item.organiser = organiser.firstName.toString() + ' ' + organiser.lastName.toString();
                    });
              });
            });
    }

}

