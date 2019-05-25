import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Url} from "../http/url";
import {EmployeeTravel} from "../entities/employeeTravel";

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit {

  items: Array<EmployeeTravel> = [];
  public displayedColumns: string[] = ['firstName', 'lastName', 'date', 'room' ];
  constructor(private http: HttpClient) { }
  ngOnInit() {
    this.http.get(Url.get('employeeTravel/get/travelId/' + 1))
      .subscribe((employees: Array<EmployeeTravel>) => {
        this.items = employees;
        });
  }
}
