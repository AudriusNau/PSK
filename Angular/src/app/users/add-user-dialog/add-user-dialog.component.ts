import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialogRef } from '@angular/material';
import { Employee } from 'src/app/entities/employee';
import { Url } from 'src/app/http/url';

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

    onSaveClick() {
        let user: Employee = {
            firstName: this.firstName,
            lastName: this.lastName,
            username: this.userName,
            role: this.role,
            password: this.password
        }
        this.http.post(Url.get("employee/post"), user)
            .subscribe(() => this.dialogRef.close(true));
    }

    onCloseClick() {
        this.dialogRef.close(false);
    }

}
