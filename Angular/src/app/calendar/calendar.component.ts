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
    isSameDay,
    isSameMonth,
    addHours
} from 'date-fns';
import { EmployeeCalendar } from '../entities/employeeCalender';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { NewTravelDialogComponent } from '../organiser-travels/new-travel-dialog/new-travel-dialog.component';
import { UserService } from '../services/user.service';
import { Subject } from 'rxjs';

interface Option {
    display: string,
    value: number
}

// class AppCalendarEvent implements CalendarEvent {
//     start: Date,

// }
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
    events: CalendarEvent[] = [
        {
            start: subDays(startOfDay(new Date()), 1),
            end: addDays(new Date(), 1),
            title: 'A 3 day event',
            color: colors.red,
            allDay: true,
            resizable: {
                beforeStart: true,
                afterEnd: true
            },
            draggable: true
        },
        {
            start: subDays(endOfMonth(new Date()), 3),
            end: addDays(endOfMonth(new Date()), 3),
            title: 'A long event that spans 2 months',
            color: colors.blue,
            allDay: true
        }
    ];

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
        this.events[0] = { start: new Date, title: "Busy" };
        this.events[0].allDay = true;
        this.events[0].color = {
            primary: '#ad2121',
            secondary: '#FAE3E3'
        };

        this.http.get(Url.get("employee/get/all"))
            .subscribe((employees: Array<Employee>) => {
                this.options = employees.map((employee) => {
                    this.employeeId.set(employee.id, employee.username);
                    return { display: employee.username, value: employee.id };
                });
            })

    }

    getSelectedOptions(selected) {
        this.selected = selected;
        console.log("being called");
        this.events = [];

        this.selected.forEach((id) => {
            this.http.get(Url.get("employeeCalendar/get/employeeId/" + id))
                .subscribe((calendars: Array<EmployeeCalendar>) => {
                    calendars.forEach((employeeCalendar) => {
                        let calendar = employeeCalendar.calendar;
                        if (calendar) {
                            let dateParts: string[] = calendar.date.split("_");
                            let date = new Date();
                            date.setFullYear(+dateParts[0], +dateParts[1] - 1, +dateParts[2]);
        
                            let event: CalendarEvent = {title: this.employeeId.get(id), start: date};
                            event.allDay = true;
                            event.color = colors.red;
                            this.events.push(event);
                        }
                    });
                    this.refresh.next();
                })
        })
    }
    
    dateClicked(date: any) {
        if (!this.startDate) {
            this.startDate = date.date;
            return;
        }

        if (this.startDate < date.date) {
            this.endDate = date.date;
        }

        this.endDate = this.startDate;
        this.startDate = date.date;
    }

    get startDateStr(): string {
        return this.convertToString(this.startDate);
    }

    get endDateStr(): string {
        return this.convertToString(this.endDate);
    }

    private convertToString(date: any) : string {
        if (!date) return "";
        let month = date.getMonth() + 1;
        let day = date.getDate();
        return date.getFullYear() + "_" + (month > 9 ? month : "0" + month) + "_" + (day > 9 ? day : "0" + day);
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

    get isCreateEnabled(): boolean {
        if (!this.startDate)
            return false;
        if (!this.endDate)
            return false;
        return true;
    }

}
