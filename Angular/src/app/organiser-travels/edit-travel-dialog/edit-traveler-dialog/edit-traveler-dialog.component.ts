import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EmployeeTravel } from 'src/app/entities/employeeTravel';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-edit-traveler-dialog',
  templateUrl: './edit-traveler-dialog.component.html',
  styleUrls: ['./edit-traveler-dialog.component.scss']
})
export class EditTravelerDialogComponent implements OnInit {

  constructor(
    private http: HttpClient,
    public dialogRef: MatDialogRef<EditTravelerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: EmployeeTravel) { }

  ngOnInit() {
  }

  

}
