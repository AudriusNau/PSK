import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Url} from "../http/url";
import {EmployeeTravel} from "../entities/employeeTravel";
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit {
  id: number;
  private subscription: Subscription;

  items: Array<EmployeeTravel> = [];
  public displayedColumns: string[] = ['firstName', 'lastName', 'date', 'room' ];
  constructor(private http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(params => {
      this.id = params.id;

      this.http.get(Url.get('employeeTravel/get/travelId/' + this.id))
      .subscribe((employees: Array<EmployeeTravel>) => {
        this.items = employees;
      });
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
