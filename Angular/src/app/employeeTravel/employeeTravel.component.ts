import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Url} from "../http/url";
import {EmployeeTravel} from "../entities/employeeTravel";
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-employee',
  templateUrl: './employeeTravel.component.html',
  styleUrls: ['./employeeTravel.component.scss']
})
export class EmployeeTravelComponent implements OnInit {
  id: number;
  private subscription: Subscription;

  items: Array<EmployeeTravel> = [];
  public displayedColumns: string[] = ['firstName', 'lastName', 'date', 'room', 'flight', 'carRent', 'status' ];
  constructor(private http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.subscription = this.route.params.subscribe(params => {
      this.id = params.id;

      this.http.get(Url.get('employeeTravel/get/travelId/' + this.id))
      .subscribe((employees: Array<EmployeeTravel>) => {
        this.items = employees;
        this.items.forEach(item => {

          if (item.flight.need === 2){// if flight needed
            item.flight.info = item.flight.need.toString().replace('2', 'reserved ') + ' ' + item.flight.info;
          }else if (item.flight.need === 1){
            item.flight.info = item.flight.need.toString().replace('1', 'not reserved ') + ' ' + item.flight.info;
          }else item.flight.info = "no flight needed" + item.flight.info ;

          if (item.carRent.need === 2){// if car needed
            item.carRent.info = item.carRent.need.toString().replace('2', 'reserved ') + ' ' + item.carRent.info;
          }else if (item.carRent.need === 1){
            item.carRent.info = item.carRent.need.toString().replace('1', 'not reserved ') + ' ' + item.carRent.info;
          }else item.carRent.info = "no car rent needed" + item.carRent.info ;

          item.status = item.status.toString().replace('false', 'Pending');
          item.status = item.status.toString().replace('true', 'Approved');
        })
      });
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
