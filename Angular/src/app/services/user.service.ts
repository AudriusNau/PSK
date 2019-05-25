import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../entities/user';

@Injectable()
export class UserService {

    private _user: User;
    get user(): User {
        return this._user;
    }

    private _userSource = new BehaviorSubject(this._user);
    userChanged = this._userSource.asObservable();

    changeUser(user: User) {
        this._user = user;
        this._userSource.next(user);
    }

}