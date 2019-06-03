import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../entities/user';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class UserService {

    constructor(private cookieService: CookieService) {}
    // private _user: User;
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

    // private _userSource = new BehaviorSubject(this._user);
    // userChanged = this._userSource.asObservable();

    changeUser(user: User) {
        // this._user = user;
        this.cookieService.set("user-id", user.id.toString());
        this.cookieService.set("user-firstname", user.firstName);
        this.cookieService.set("user-lastname", user.lastName);
        this.cookieService.set("user-username", user.username);
        this.cookieService.set("user-role", user.role);
        // this._userSource.next(user);
    }

    logout() {
        this.cookieService.delete("user-id");
        this.cookieService.delete("user-firstname");
        this.cookieService.delete("user-lastname");
        this.cookieService.delete("user-username");
        this.cookieService.delete("user-role");
    }

}