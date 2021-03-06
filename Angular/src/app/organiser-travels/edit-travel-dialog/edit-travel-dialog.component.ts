import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogConfig, MatDialog } from '@angular/material';
import { Travel } from 'src/app/entities/travel';
import { EmployeeTravel } from 'src/app/entities/employeeTravel';
import { Url } from 'src/app/http/url';
import { NewTravelerDialogComponent } from './new-traveler-dialog/new-traveler-dialog.component';
import { EditTravelerDialogComponent } from './edit-traveler-dialog/edit-traveler-dialog.component';

@Component({
    selector: 'app-edit-travel-dialog',
    templateUrl: './edit-travel-dialog.component.html',
    styleUrls: ['./edit-travel-dialog.component.scss']
})
export class EditTravelDialogComponent implements OnInit {

    public displayedColumns: string[] = ['status', 'firstName', 'lastName', 'actions'];
    travelers: Array<EmployeeTravel>;

    constructor(
        private http: HttpClient,
        public dialogRef: MatDialogRef<EditTravelDialogComponent>,
        @Inject(MAT_DIALOG_DATA) private data: Travel,
        private dialog: MatDialog
    ) { }

    ngOnInit() {
        this.loadTravelers();
    }

    loadTravelers() {
        this.http.get(Url.get('employeeTravel/get/travelId/' + this.data.id))
            .subscribe((travelers: Array<EmployeeTravel>) => {
                this.travelers = travelers;
            });
    }

    onAddCLick() {
        const config = new MatDialogConfig();
        config.data = this.data;
        this.dialog.open(NewTravelerDialogComponent, config)
            .afterClosed().subscribe((reload: boolean) => {
                if (reload)
                    this.loadTravelers();
            })
    }

    onEditClick(traveler: EmployeeTravel) {
        const config = new MatDialogConfig();
        config.data = traveler;
        this.dialog.open(EditTravelerDialogComponent, config);
    }

    onRemoveClick(traveler: EmployeeTravel) {
        this.http.delete(Url.get("employeeTravel/delete/" + traveler.id))
            .subscribe(() => this.loadTravelers());
    }

    onCloseClick() {
        this.dialogRef.close();
    }

}
