import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Employee } from '../entities/employee';
import { HttpClient } from '@angular/common/http';
import { Url } from '../http/url';
import { Travel } from '../entities/travel';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { ReasignTravelsDialogComponent } from './reasign-travels-dialog/reasign-travels-dialog.component';
import { AddUserDialogComponent } from './add-user-dialog/add-user-dialog.component';

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

    public displayedColumns: string[] = ['firstName', 'lastName', 'userName', 'role', 'actions'];
    users: Array<Employee> = [];

    constructor(private http: HttpClient, private userService: UserService, private dialog: MatDialog) { }

    ngOnInit() {
        this.loadUsers()
    }

    loadUsers() {
        this.http.get(Url.get('employee/get/all'))
            .subscribe((users: Array<Employee>) => {
                this.users = users;
            });
    }

    onRoleChange(newRole: string, id: number) {
        this.http.get(Url.get('employee/get/' + id))
            .subscribe((user: Employee) => {
                if(newRole == "Admin" || newRole == "Organiser") {
                    this.http.put(Url.get('employee/put/' + id), {role: newRole})
                        .subscribe(() => this.loadUsers());
                }
                this.http.get(Url.get("travel/get/getByOrganiserId/" + user.id))
                    .subscribe((travels: Array<Travel>) => {
                        if(travels.length == 0) {
                            this.http.put(Url.get('employee/put/' + id), {role: newRole})
                                .subscribe(() => this.loadUsers());
                        } else {
                            const config = new MatDialogConfig();
                            config.data = travels;
                            this.dialog.open(ReasignTravelsDialogComponent, config)
                                // .afterClosed().subscribe((result) => {
                                //     if (result == true)
                                //         this.loadTable();
                                // })

                            this.loadUsers()
                        }
                    })
            });
    }

    onAddCLick() {
        this.dialog.open(AddUserDialogComponent)
            .afterClosed().subscribe((reload: boolean) => {
                if (reload)
                    this.loadUsers();
            })
    }

    onRemoveClick(user: Employee) {
        this.http.delete(Url.get("employee/delete/" + user.id))
            .subscribe(() => this.loadUsers());
    }
}
