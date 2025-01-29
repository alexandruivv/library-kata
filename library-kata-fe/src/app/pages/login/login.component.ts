import {Component, inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule, MatFormFieldModule, MatInputModule, MatButton, ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  standalone: true
})
export class LoginComponent {
  invalidCredentials = false;
  userService = inject(UserService);
  router = inject(Router);

  loginForm= new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  })

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    this.invalidCredentials = false;
    this.userService.login(this.loginForm.value.username!, this.loginForm.value.password!)
      .subscribe({
        next: resData => {
          this.router.navigate(['/']);
        },
        error: (err: HttpErrorResponse) => {
          if (err.status === 400 && err.error.code === '004') {
            this.invalidCredentials = true;
          }
        }
      })
  }
}
