import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material';
import { Travel } from 'src/app/entities/travel';
import { HttpClient } from '@angular/common/http';

@Component({
    selector: 'app-reasign-travels-dialog',
    templateUrl: './reasign-travels-dialog.component.html',
    styleUrls: ['./reasign-travels-dialog.component.scss']
})
export class ReasignTravelsDialogComponent implements OnInit {

    public displayedColumns: string[] = ['startDate', 'endDate', 'departureOffice', 'arrivalOffice', 'organiser'];

    constructor(
        private http: HttpClient,
        public dialogRef: MatDialogRef<ReasignTravelsDialogComponent>,
        @Inject(MAT_DIALOG_DATA) private data: Array<Travel>,
        private dialog: MatDialog) { }

    ngOnInit() {
        
    }

}
