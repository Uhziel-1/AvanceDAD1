// token.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  //const token = localStorage.getItem('access_token');
  const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVaHppZWwiLCJpZCI6MSwiaWF0IjoxNzUwMzM5MDk4LCJleHAiOjE3NTAzNDI2OTh9.oItF8kR_pBJwdoOsurbvruau-MijC7WbvL4wcWGaGmI"

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
