import {Component, inject, OnInit} from '@angular/core';
import {BooksService} from '../../services/books.service';
import {UserService} from '../../services/user.service';
import {HeaderComponent} from '../../components/header/header.component';
import {AdminHomeComponent} from '../../components/admin-home/admin-home.component';
import {UserHomeComponent} from '../../components/user-home/user-home.component';

@Component({
  selector: 'app-home',
  imports: [
    HeaderComponent,
    AdminHomeComponent,
    UserHomeComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true
})
export class HomeComponent {
  userService = inject(UserService);

  get user() {
    return this.userService.currentUser;
  }
}
