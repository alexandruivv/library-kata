import {Component, inject} from '@angular/core';
import {UserService} from '../../services/user.service';
import {NgClass} from '@angular/common';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-header',
  imports: [
    NgClass,
    MatButton
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
  standalone: true
})
export class HeaderComponent {
  userService = inject(UserService);

  get user() {
    return this.userService.currentUser;
  }

  onLogout() {
    this.userService.logout();
  }
}
