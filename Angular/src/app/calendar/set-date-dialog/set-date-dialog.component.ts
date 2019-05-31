import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
    selector: 'app-set-date-dialog',
    templateUrl: './set-date-dialog.component.html',
    styleUrls: ['./set-date-dialog.component.scss']
})
export class SetDateDialogComponent {

    date: string;

    constructor(
        public dialogRef: MatDialogRef<SetDateDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any
    ) {
        this.date = data;
    }

    onStartClick(): void {
        this.dialogRef.close(true);
    }

    onEndClick(): void {
        this.dialogRef.close(false);
    }
}
