import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { CalendarDateOption } from '../calendar.component';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-set-date-dialog',
    templateUrl: './set-date-dialog.component.html',
    styleUrls: ['./set-date-dialog.component.scss']
})
export class SetDateDialogComponent {

    date: string;

    constructor(
        public dialogRef: MatDialogRef<SetDateDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private userService: UserService
    ) {
        this.date = data;
    }

    onStartClick(): void {
        this.dialogRef.close(CalendarDateOption.startDate);
    }

    onEndClick(): void {
        this.dialogRef.close(CalendarDateOption.endDate);
    }

    onToggleClick(): void {
        this.dialogRef.close(CalendarDateOption.toggleAvailability);
    }
}
