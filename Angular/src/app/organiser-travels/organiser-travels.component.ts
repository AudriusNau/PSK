import { Component, OnInit } from '@angular/core';
import { Travel } from '../entities/travel';
import { Url } from '../http/url';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Office } from '../entities/office';
import { MatTableDataSource, MatDialogConfig, MatDialog } from '@angular/material';
import { NewTravelDialogComponent } from './new-travel-dialog/new-travel-dialog.component';
import { UserService } from '../services/user.service';

@Component({
    selector: 'app-organiser-travels',
    templateUrl: './organiser-travels.component.html',
    styleUrls: ['./organiser-travels.component.scss']
})

export class OrganiserTravelsComponent implements OnInit {

    travels: Array<Travel> = [];
    public displayedColumns: string[] = ['date', 'departureOffice', 'arrivalOffice', 'cancel'];
    
    constructor(private http: HttpClient, private dialog: MatDialog, private userService: UserService, private router: Router) { }

    ngOnInit() {
        if (this.userService.user)
            this.loadTable();
        else
            this.router.navigate(['/login']);
    }

    loadTable() {
        this.http.get(Url.get('travel/get/getByOrganiserId/' + this.userService.user.id))
            .subscribe((travels: Array<Travel>) => {
                this.travels = travels;
                this.travels.forEach(item => {
                    item.date = item.date.replace(/_/g, " - ");
                    this.http.get(Url.get('travel/get/getDepartureOfficeByTravelId/' + item.id))
                        .subscribe((office: Office) => {
                            item.departureOffice = office.name;
                        });
                    this.http.get(Url.get('travel/get/getArrivalOfficeByTravelId/' + item.id))
                        .subscribe((office: Office) => {
                            item.arrivalOffice = office.name;
                        });
                });
            });
    }

    onCreateClick() {
        const config = new MatDialogConfig();
        config.data = this.userService.user.id;
        this.dialog.open(NewTravelDialogComponent, config)
            .afterClosed().subscribe((result) => {
                if (result == true)
                    this.loadTable();
            })
    }

    onCancelCLick(id: number) {
        this.http.delete(Url.get("travel/delete/" + id))
            .subscribe(() => this.loadTable());
    }
}
