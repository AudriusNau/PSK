import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { User } from '../entities/user';

@Injectable()
export class AuthenticateComponent {
  private loggedIn = new BehaviorSubject<boolean>(false);

  get isLoggedIn() {
    return this.loggedIn.asObservable();
  }

  constructor(private router: Router) {}

  login(user: User) {
    if (user !== {} as User) {
      this.loggedIn.next(true);
      this.router.navigate(['./home/']);
    } else {
      console.log('Incorect username or password');
    }
  }

  logout() {
    this.loggedIn.next(false);
    this.router.navigate(['/login']);
  }
}
