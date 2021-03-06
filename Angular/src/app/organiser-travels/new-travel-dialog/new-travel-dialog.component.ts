import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Url } from 'src/app/http/url';
import { Office } from 'src/app/entities/office';
import { Travel } from 'src/app/entities/travel';
import { EmployeeTravel } from 'src/app/entities/employeeTravel';
import { Employee } from 'src/app/entities/employee';
import { EmployeeTravelDTO } from 'src/app/entities/employeeTravelDTO';
import { Flight } from 'src/app/entities/flight';
import { CarRent } from 'src/app/entities/carRent';

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
    startDate: Date;
    endDate: Date;

    public displayedColumns: string[] = ['firstName', 'lastName', 'flight', 'car'];
    travelers: Array<Employee> = [];

    constructor(
        private http: HttpClient,
        public dialogRef: MatDialogRef<NewTravelDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) { 
        this.startDate = this.data.startDate;
        this.endDate = this.data.endDate;
        if (this.data.travelers)
            this.travelers = this.data.travelers;
    }

    onCloseClick(): void {
        this.dialogRef.close();
    }

    onCreateClick() {
        const newTrip: any = {};

        newTrip.startDate = this.convertToString(this.startDate);
        newTrip.endDate = this.convertToString(this.endDate);
        
        newTrip.departureOfficeId = this.departureOffice;
        newTrip.arrivalOfficeId = this.arrivalOffice;
        newTrip.price = this.price;
        newTrip.organiserId = this.data.organiserId;

        this.http.post(Url.get("travel/post"), newTrip)
            .subscribe((newTravel: Travel) => {
                this.travelers.forEach((traveler) => {
                    this.http.post(Url.get("carRent/post"), {need: traveler.carNeeded ? 1 : 0, info: "Car info"})
                        .subscribe((carRent: CarRent) => {
                            this.http.post(Url.get("flight/post"), {need: traveler.ticketsNeeded ? 1 : 0, info: "Flight info"})
                                .subscribe((flight: Flight) => {
                                    let employeeTravel: EmployeeTravelDTO = {
                                        carRentId: carRent.id,
                                        employeeId: traveler.id,
                                        flightId: flight.id,
                                        travelId: newTravel.id
                                    };
                                    this.http.post(Url.get("employeeTravel/post"), employeeTravel).subscribe(() => {
                                        this.dialogRef.close(true);
                                    });
                                });
                        });
                });
                this.dialogRef.close(true);
                }
            );
    }

    private convertToString(date: Date) : string {
        let month = date.getMonth() + 1;
        let day = date.getDate();
        return date.getFullYear() + "_" + (month > 9 ? month : "0" + month) + "_" + (day > 9 ? day : "0" + day);
    }

    ngOnInit() {
        this.http.get(Url.get("office/get/all"))
            .subscribe((offices: Array<Office>) => {
                this.offices = offices;
            })
    }

}
