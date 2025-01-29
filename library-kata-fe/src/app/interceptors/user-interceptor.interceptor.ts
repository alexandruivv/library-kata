import {
  HttpEvent,
  HttpHandlerFn,
  HttpRequest
} from '@angular/common/http';
import {inject} from '@angular/core';
import {UserService} from '../services/user.service';
import {Observable} from 'rxjs';


export function tokenInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const userService = inject(UserService);

  const userId = userService.getUserId();
  if (userId) {
    const cloned = req.clone({
      headers: req.headers.set('User-Id', userId)
    });
    return next(cloned);
  }
  return next(req);
}



