import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialogRef } from '@angular/material';

@Component({
    selector: 'app-add-user-dialog',
    templateUrl: './add-user-dialog.component.html',
    styleUrls: ['./add-user-dialog.component.scss']
})
export class AddUserDialogComponent implements OnInit {

    firstName = "";
    lastName = "";
    userName = "";
    password = "";
    role = "Employee";

    constructor(
        private http: HttpClient,
        public dialogRef: MatDialogRef<AddUserDialogComponent>, ) { }

    ngOnInit() {
    }

}
