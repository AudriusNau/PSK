import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Travel } from '../entities/travel';
import { Url } from '../http/url';
import { Office } from '../entities/office';
import { Organiser } from '../entities/organiser';
import { Router } from '@angular/router';
import {UserService} from "../services/user.service";
import {Accommodation} from "../entities/accommodation";
import {MatDialog, MatDialogConfig} from "@angular/material";
import {AccommodationDialogComponent} from "../accommodation/accommodation-dialog/accommodation-dialog.component";
import {MergeTravelDialogComponent} from "./merge-travel-dialog/merge-travel-dialog.component";

@Component({
    selector: 'app-travel',
    templateUrl: './travel.component.html',
    styleUrls: ['./travel.component.scss']
})
export class TravelComponent implements OnInit {
    items: Array<Travel> = [];
    selectedTravels: Array<Travel> = [];
  private rules: string = String('Rules:\n 1 dates should be similar (+- 1 day)\n 2 destination should be the same\n');
    public displayedColumns: string[] = ['startDate', 'endDate', 'price', 'departureOffice', 'arrivalOffice', 'organiser', 'info'];
    constructor(private http: HttpClient, private userService: UserService, private router: Router, private dialog: MatDialog) { }
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
    openDialog(): void {
      this.selectedTravels = this.items.filter(item => item.isSelected === true);
      const config = new MatDialogConfig();
      config.data = this.selectedTravels;
      this.dialog.open(MergeTravelDialogComponent, config)
        .afterClosed().subscribe(result => {
          if (result == false) {alert(' Does not match the rules\n' + this.rules); }
          else this.loadTable();
      });

  }
}

