import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EmployeeTravel } from 'src/app/entities/employeeTravel';
import { HttpClient } from '@angular/common/http';
import { Url } from 'src/app/http/url';
import { Accommodation } from 'src/app/entities/accommodation';

@Component({
  selector: 'app-edit-traveler-dialog',
  templateUrl: './edit-traveler-dialog.component.html',
  styleUrls: ['./edit-traveler-dialog.component.scss']
})
export class EditTravelerDialogComponent {
  employeeTravel: EmployeeTravel;

  constructor(
    private http: HttpClient,
    public dialogRef: MatDialogRef<EditTravelerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: EmployeeTravel) {
    this.employeeTravel = data;
    if (this.employeeTravel.room) {
      this.http.get(Url.get("room/get/getAccommodationByRoomId/" + this.employeeTravel.room.id))
        .subscribe((accommodation: Accommodation) => this.employeeTravel.accomodation = accommodation)
    }
  }

  onChangeClick() {
    this.http.put(Url.get("employeeTravel/changeAccommodation/" + this.employeeTravel.id), {})
      .subscribe((newEmployeeTravel: EmployeeTravel) => {
        this.employeeTravel.room = newEmployeeTravel.room;
        console.log(newEmployeeTravel.room);
        if (this.employeeTravel.room) {
          this.http.get(Url.get("room/get/getAccommodationByRoomId/" + this.employeeTravel.room.id))
            .subscribe((accommodation: Accommodation) => this.employeeTravel.accomodation = accommodation)
        }
      })
  }

}
