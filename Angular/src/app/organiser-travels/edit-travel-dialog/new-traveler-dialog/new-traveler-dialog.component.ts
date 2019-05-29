import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Travel } from 'src/app/entities/travel';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Url } from 'src/app/http/url';
import { Employee } from 'src/app/entities/employee';
import { CarRent } from 'src/app/entities/carRent';
import { Flight } from 'src/app/entities/flight';
import { EmployeeTravelDTO } from 'src/app/entities/employeeTravelDTO';
import { EmployeeCalendar } from 'src/app/entities/employeeCalender';

@Component({
    selector: 'app-new-traveler-dialog',
    templateUrl: './new-traveler-dialog.component.html',
    styleUrls: ['./new-traveler-dialog.component.scss']
})
export class NewTravelerDialogComponent implements OnInit {

    constructor(
        private http: HttpClient,
        public dialogRef: MatDialogRef<NewTravelerDialogComponent>,
        @Inject(MAT_DIALOG_DATA) private data: Travel
    ) { }

    myControl = new FormControl();
    options: string[] = [];
    employeeId: Map<string, number> = new Map();
    filteredOptions: Observable<String[]>;

    username: string;
    ticketsNeeded: boolean = false;
    carNeeded: boolean = false;
    showAvailability = false;
    isAvailable = false;

    ngOnInit() {
        this.http.get(Url.get("employee/get/all"))
            .subscribe((employees: Array<Employee>) => {
                this.options = employees.map((employee) => {
                    this.employeeId.set(employee.username, employee.id);
                    return employee.username;
                });
            })

        this.filteredOptions = this.myControl.valueChanges.pipe(
            startWith(''),
            map(value => this._filter(value))
        );
    }

    private _filter(value: string): String[] {
        const filterValue = value.toLowerCase();

        return this.options.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
    }

    onSaveClick() {
        this.http.post(Url.get("carRent/post"), {need: this.carNeeded ? 1 : 0, info: "Car info"})
            .subscribe((carRent: CarRent) => {
                this.http.post(Url.get("flight/post"), {need: this.ticketsNeeded ? 1 : 0, info: "Flight info"})
                    .subscribe((flight: Flight) => {
                        let employeeTravel: EmployeeTravelDTO = {
                            carRentId: carRent.id,
                            employeeId: this.employeeId.get(this.username),
                            flightId: flight.id,
                            travelId: this.data.id
                        };
                        this.http.post(Url.get("employeeTravel/post"), employeeTravel).subscribe(() => {
                            this.dialogRef.close(true);
                        });
                    })
            })
    }

    checkAvailability() {
        let id = this.employeeId.get(this.username);
        if (!id) {
            this.showAvailability = false;
            return;
        }

        this.http.get(Url.get("employeeCalendar/get/employeeId/" + id))
            .subscribe((dates: Array<any>) => {
                this.isAvailable = true;
                dates.forEach(date => {
                    if (date.calendar.date == this.data.startDate) {
                        this.isAvailable = false;
                    }
                });
                this.showAvailability = true;
            });
    }

}
