import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Accommodation } from '../entities/accommodation';
import { Url } from '../http/url';
import { Office } from '../entities/office';

@Component({
    selector: 'app-accommodation',
    templateUrl: './accommodation.component.html',
    styleUrls: ['./accommodation.component.scss']
})
export class AccommodationComponent implements OnInit {

    items: Array<Accommodation> = []
    public displayedColumns: string[] = ['name', 'type', 'office'];

    constructor(private http: HttpClient) { }

    ngOnInit() {
        this.http.get(Url.get("accommodation/get/all"))
            .subscribe((accommodations: Array<Accommodation>) => {
                this.items = accommodations;
                this.items.forEach(item => {
                    this.http.get(Url.get("accommodation/get/getOfficeByAccommodationId/" + item.id))
                        .subscribe((office: Office) => {
                            item.office = office.name;
                        })
                })
            })
    }

}
