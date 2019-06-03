import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../entities/user';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class UserService {

    constructor(private cookieService: CookieService) {}

    get user(): User {
        const user: User = {};
        user.id = Number.parseInt(this.cookieService.get("user-id"));
        user.firstName = this.cookieService.get("user-firstname");
        user.lastName = this.cookieService.get("user-lastname");
        user.username = this.cookieService.get("user-username");
        user.role = this.cookieService.get("user-role");
        return user;
    }

    get isLoggedIn(): boolean {
        return this.cookieService.get("user-id") ? true : false;
    }

    get isAdmin(): boolean {
        return this.cookieService.get("user-role") == "Admin";
    }

    get isOrganiser(): boolean {
        return this.isAdmin || this.cookieService.get("user-role") == "Organiser";
    }

    get firstName(): string {
        return this.cookieService.get("user-firstname");
    }

    get lastName(): string {
        return this.cookieService.get("user-lastname");
    }

    changeUser(user: User) {
        this.cookieService.set("user-id", user.id.toString());
        this.cookieService.set("user-firstname", user.firstName);
        this.cookieService.set("user-lastname", user.lastName);
        this.cookieService.set("user-username", user.username);
        this.cookieService.set("user-role", user.role);
    }

    logout() {
        this.cookieService.delete("user-id");
        this.cookieService.delete("user-firstname");
        this.cookieService.delete("user-lastname");
        this.cookieService.delete("user-username");
        this.cookieService.delete("user-role");
    }

}