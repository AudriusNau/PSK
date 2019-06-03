import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Employee } from '../entities/employee';
import { HttpClient } from '@angular/common/http';
import { Url } from '../http/url';

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

    public displayedColumns: string[] = ['firstName', 'lastName', 'userName', 'role', 'actions'];
    users: Array<Employee> = [];

    constructor(private http: HttpClient, private userService: UserService) { }

    ngOnInit() {
        this.loadUsers()
    }

    loadUsers() {
        this.http.get(Url.get('employee/get/all'))
            .subscribe((users: Array<Employee>) => {
                this.users = users;
            });
    }

    onRoleChange(role: string, id: number) {
        this.http.get(Url.get('employee/get/' + id))
            .subscribe((user: Employee) => {
                this.loadUsers();
            });
    }

}
