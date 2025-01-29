import {inject, Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/user';
import {tap} from 'rxjs';
import {Router} from '@angular/router';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private USER = 'user';
  currentUser = signal<User | null>(null);
  router = inject(Router);
  http: HttpClient = inject(HttpClient);
  apiUrl = environment.baseUrl;

  constructor() {
    const user = this.getUser();
    if (user) {
      this.currentUser.set(user);
    }
  }

  login(username: string, password: string) {
    return this.http.post(`${this.apiUrl}/users/login`, {
      username: username,
      password: password
    }).pipe(tap((resData: any) => {
      this.storeUser(resData);
      this.currentUser.set(resData);
    }))
  }

  storeUser(user: User) {
    localStorage.setItem(this.USER, JSON.stringify(user));
  }

  getUserId(): string | null {
    const user: any = localStorage.getItem(this.USER);
    if (!user) {
      return null;
    }
    return JSON.parse(user).id;
  }

  getUser(): User | null {
    const user: any = localStorage.getItem(this.USER);
    if (!user) {
      return null;
    }
    return JSON.parse(user);
  }

  logout() {
    localStorage.clear();
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

}
