import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { NavigationService } from 'src/app/services/navigation.service';

@Component({
  selector: 'app-sidenav-list',
  templateUrl: './sidenav-list.component.html',
  styleUrls: ['./sidenav-list.component.css']
})
export class SidenavListComponent implements OnInit {
  @Output() sidenavClose = new EventEmitter();

  constructor(private userService: UserService, private navigationService: NavigationService) { }

  ngOnInit() {
  }

  public onNavigationItemSelected(title: string) {
    this.navigationService.currentPageTitle = title;
    this.sidenavClose.emit();
  }

  onLogoutClick() {
    this.userService.logout();
  }

}
