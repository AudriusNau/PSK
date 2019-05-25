import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Accommodation } from '../entities/accommodation';
import { Url } from '../http/url';
import { Office } from '../entities/office';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { AccommodationDialogComponent } from './accommodation-dialog/accommodation-dialog.component';

@Component({
    selector: 'app-accommodation',
    templateUrl: './accommodation.component.html',
    styleUrls: ['./accommodation.component.scss']
})
export class AccommodationComponent implements OnInit {

    items: Array<Accommodation> = []
    public displayedColumns: string[] = ['name', 'type', 'office'];

    constructor(private http: HttpClient, private dialog: MatDialog) { }

    ngOnInit() {
        this.http.get(Url.get("accommodation/get/all"))
            .subscribe((accommodations: Array<Accommodation>) => {
                this.items = accommodations;
                this.items.forEach(item => {
                    this.http.get(Url.get("accommodation/get/getOfficeByAccommodationId/" + item.id))
                        .subscribe((office: Office) => {
                            item.office = office.name;
                        })
                })
            })
    }

    openDialog(accommodation: Accommodation): void {
        const config = new MatDialogConfig();
        config.data = accommodation;
        config.width = '250px'

        // const dialogRef = this.dialog.open(AccommodationDialogComponent, config);
        this.dialog.open(AccommodationDialogComponent, config);
    }

}
