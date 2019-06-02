import { Component, OnInit } from '@angular/core';
import { CalendarEvent, EventColor } from 'calendar-utils';
import { HttpClient } from '@angular/common/http';
import { Url } from '../http/url';
import { Employee } from '../entities/employee';
import {
    startOfDay,
    endOfDay,
    subDays,
    addDays,
    endOfMonth,
} from 'date-fns';
import { EmployeeCalendar } from '../entities/employeeCalender';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { NewTravelDialogComponent } from '../organiser-travels/new-travel-dialog/new-travel-dialog.component';
import { UserService } from '../services/user.service';
import { Subject } from 'rxjs';
import { SetDateDialogComponent } from './set-date-dialog/set-date-dialog.component';

interface Option {
    display: string,
    value: number
}

export enum CalendarDateOption {
    startDate,
    endDate,
    toggleAvailability
}

const colors: any = {
    red: {
        primary: '#ad2121',
        secondary: '#FAE3E3'
    },
    blue: {
        primary: '#1e90ff',
        secondary: '#D1E8FF'
    },
    yellow: {
        primary: '#e3bc08',
        secondary: '#FDF1BA'
    }
};
@Component({
    selector: 'app-calendar',
    templateUrl: './calendar.component.html',
    styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {

    viewDate: Date = new Date();
    busyDates: EmployeeCalendar[] = [];
    personalEvents: CalendarEvent[] = [];
    busyEvents: CalendarEvent[] = [];
    tripEvents: CalendarEvent[] = [];
    get events(): CalendarEvent[] {
        return this.busyEvents.concat(this.tripEvents).concat(this.personalEvents);
    }

    options: Option[] = [];
    employeeId: Map<number, string> = new Map();

    selectedOptions: number[] = [];
    selected = this.selectedOptions;
    showError = false;
    errorMessage = '';
    view = "month";
    refresh: Subject<any> = new Subject();

    startDate: Date;
    endDate: Date;
    
    constructor(private http: HttpClient, private dialog: MatDialog, private userService: UserService) { }

    ngOnInit() {
        this.http.get(Url.get("employee/get/all"))
            .subscribe((employees: Array<Employee>) => {
                this.options = employees.map((employee) => {
                    this.employeeId.set(employee.id, employee.username);
                    return { display: employee.username, value: employee.id };
                });
            });

        this.loadPersonalEvents();
    }

    private loadPersonalEvents() {
        this.personalEvents = [];

        this.http.get(Url.get("employeeCalendar/get/employeeId/" + this.userService.user.id))
            .subscribe((empCalendars: EmployeeCalendar[]) => {
                this.busyDates = empCalendars;
                empCalendars.forEach(empCalendar => {
                    let date = this.convertToDate(empCalendar.calendar.date);

                    let event: CalendarEvent = {title: "Busy", start: date};
                    event.allDay = true;
                    event.color = colors.yellow;
                    this.personalEvents.push(event);
                });
                this.refresh.next();
            })
    }

    getSelectedOptions(selected) {
        this.selected = selected;
        this.busyEvents = [];

        this.selected.forEach((id) => {
            this.http.get(Url.get("employeeCalendar/get/employeeId/" + id))
                .subscribe((calendars: Array<EmployeeCalendar>) => {
                    calendars.forEach((employeeCalendar) => {
                        let calendar = employeeCalendar.calendar;
                        if (calendar) {
                            let date = this.convertToDate(calendar.date);
        
                            let event: CalendarEvent = {title: this.employeeId.get(id), start: date};
                            event.allDay = true;
                            event.color = colors.red;
                            this.busyEvents.push(event);
                        }
                    });
                    this.refresh.next();
                })
        })
    }
    
    dateClicked(date: any) {
        let config = new MatDialogConfig();
        let dateStr = this.convertToString(date.date);
        config.data = dateStr;

        this.dialog.open(SetDateDialogComponent, config)
            .afterClosed().subscribe((result) => {
                if (result == null)
                    return;

                if (result == CalendarDateOption.toggleAvailability) {
                    var dateFound = false;
                    this.busyDates.forEach(busyDate => {
                        if(busyDate.calendar.date == dateStr) {
                            dateFound = true;
                            this.http.delete(Url.get("employeeCalendar/delete/" + busyDate.id))
                                .subscribe(() => this.loadPersonalEvents());
                        }
                    });
                    if (!dateFound) {
                        this.http.post(
                            Url.get("employeeCalendar/post"),
                            {date: dateStr, employeeId: this.userService.user.id}
                        ).subscribe(() => this.loadPersonalEvents());
                    }
                    return;
                }

                this.tripEvents = [];
                if (result == CalendarDateOption.startDate)
                    this.startDate = date.date;
                else if (result == CalendarDateOption.endDate)
                    this.endDate = date.date;

                if (!this.endDate) {
                    let event: CalendarEvent = {title: "Trip", start: this.startDate};
                    event.allDay = true;
                    event.color = colors.blue;
                    this.tripEvents.push(event);
                }

                if (!this.startDate) {
                    let event: CalendarEvent = {title: "Trip", start: this.endDate};
                    event.allDay = true;
                    event.color = colors.blue;
                    this.tripEvents.push(event);
                }

                if (this.startDate && this.endDate) {
                    let event: CalendarEvent = {title: "Trip", start: this.startDate, end: this.endDate};
                    event.allDay = true;
                    event.color = colors.blue;
                    this.tripEvents.push(event);
                }
            })
    }

    get startDateStr(): string {
        return this.convertToString(this.startDate);
    }

    get endDateStr(): string {
        return this.convertToString(this.endDate);
    }

    private convertToString(date: any) : string {
        if (!date) return "not selected";
        let month = date.getMonth() + 1;
        let day = date.getDate();
        return date.getFullYear() + "_" + (month > 9 ? month : "0" + month) + "_" + (day > 9 ? day : "0" + day);
    }

    private convertToDate(dateStr: string): Date {
        let dateParts: string[] = dateStr.split("_");
        let date = new Date();
        date.setFullYear(+dateParts[0], +dateParts[1] - 1, +dateParts[2]);
        return date;
    }

    onCreateClick() {
        const config = new MatDialogConfig();
        config.data = {
            organiserId: this.userService.user.id,
            startDate: this.startDate,
            endDate: this.endDate
        };
        this.dialog.open(NewTravelDialogComponent, config);
    }

}
