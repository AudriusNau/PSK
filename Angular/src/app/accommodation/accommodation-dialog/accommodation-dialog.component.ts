import { Component, OnInit, Input, Inject, Optional } from '@angular/core';
import { Accommodation } from 'src/app/entities/accommodation';
import { Room } from 'src/app/entities/room';
import { Url } from 'src/app/http/url';
import { HttpClient } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
    selector: 'app-accommodation-dialog',
    templateUrl: './accommodation-dialog.component.html',
    styleUrls: ['./accommodation-dialog.component.scss']
})
export class AccommodationDialogComponent implements OnInit {
    rooms: Array<Room>;

    constructor(
        private http: HttpClient,
        public dialogRef: MatDialogRef<AccommodationDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: Accommodation
    ) {}
    
    onCloseClick(): void {
        this.dialogRef.close();
    }

    ngOnInit() {
        this.http.get(Url.get("room/get/getByAccommodationId/" + this.data.id))
            .subscribe((rooms: Array<Room>) => {
                this.rooms = rooms;
            })
    }

}
