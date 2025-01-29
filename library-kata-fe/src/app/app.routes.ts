import { Routes } from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {HomeComponent} from './pages/home/home.component';
import {loggedInGuard} from './guards/loggedInGuard';

export const routes: Routes = [
  {
    path: '', component: HomeComponent, pathMatch: 'full', canActivate: [loggedInGuard]
  },
  {
    path: 'login', component: LoginComponent
  }
];
