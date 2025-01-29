import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {UserService} from '../services/user.service';

export const loggedInGuard: CanActivateFn = (route, state) => {
  const userService = inject(UserService);
  const router = inject(Router);

  if (!!userService.getUserId()) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
