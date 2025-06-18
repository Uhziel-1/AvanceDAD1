// token.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  //const token = localStorage.getItem('access_token');
  const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVaHppZWwiLCJpZCI6MSwiaWF0IjoxNzUwMjU0NjQ1LCJleHAiOjE3NTAyNTgyNDV9.zQ7VZDg_YuDYJT_Z_u7JxgWIuybBg-wkGsLjIlktHkA"

  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(authReq);
  }

  return next(req);
};
