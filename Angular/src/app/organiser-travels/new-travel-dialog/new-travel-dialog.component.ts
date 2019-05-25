import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Url } from 'src/app/http/url';
import { Office } from 'src/app/entities/office';
import { Travel } from 'src/app/entities/travel';

@Component({
    selector: 'app-new-travel-dialog',
    templateUrl: './new-travel-dialog.component.html',
    styleUrls: ['./new-travel-dialog.component.scss']
})
export class NewTravelDialogComponent implements OnInit {
    offices: Array<Office> = [];
    departureOffice: number;
    arrivalOffice: number;
    price: number;
    date: Date;

    constructor(
        private http: HttpClient,
        public dialogRef: MatDialogRef<NewTravelDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: Number
    ) { }

    onCloseClick(): void {
        this.dialogRef.close();
    }

    onCreateClick() {
        const newTrip: any = {};

        let month = this.date.getMonth() + 1;
        let day = this.date.getDate();
        newTrip.date = this.date.getFullYear() + "_" + (month > 9 ? month : "0" + month) + "_" + (day > 9 ? day : "0" + day);
        
        newTrip.departureOfficeId = this.departureOffice;
        newTrip.arrivalOfficeId = this.arrivalOffice;
        newTrip.price = this.price;
        newTrip.organiserId = this.data;

        this.http.post(Url.get("travel/post"), newTrip)
            .subscribe(
                () => { this.dialogRef.close(true); },
                (error) => {
                    let a = 0;
                    a++;
                }
            );
    }

    ngOnInit() {
        this.http.get(Url.get("office/get/all"))
            .subscribe((offices: Array<Office>) => {
                this.offices = offices;
            })
    }

}
