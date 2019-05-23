import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Accommodation } from '../entities/accommodation';
import { Url } from '../http/url';

@Component({
    selector: 'app-accommodation',
    templateUrl: './accommodation.component.html',
    styleUrls: ['./accommodation.component.scss']
})
export class AccommodationComponent implements OnInit {

    items: Array<Accommodation> = []

    constructor(private http: HttpClient) { }

    ngOnInit() {
        this.http.get(Url.get("accommodation/get/all"))
            .subscribe((response) => {
                this.items = response as Array<Accommodation>;
            })
    }

}
