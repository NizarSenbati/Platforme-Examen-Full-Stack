import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './user.service';
import { AuthenticationRequest } from '../models/authentication-request';
import { AuthenticationResponse } from '../models/authentication-response';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnInit{
  token: String = '';
  url: string = 'http://127.0.0.1:8080/api';
  headers!: HttpHeaders;

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private router: Router
  ) 
  {
    this.checkSatut();
  }

  ngOnInit(){
    this.logout();
  }
  
  loadHeaders(){
    this.token = localStorage.getItem("token") || '';
    if (this.token === '') {
      throwError(() => new Error("No token found"));
    }

    this.headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
  }

  async registerEtd(newUser: AuthenticationRequest): Promise<AuthenticationResponse> {
    try {
      return new Promise((resolve) => {
        this.http.post<AuthenticationResponse>(`${this.url}/auth/register/etudiant`, newUser)
        .subscribe( (jwt) => {
          if(jwt){
            resolve(jwt);
          }
          else{
            throw new Error("Erreur lors de la création de l'utilisateur");
          }
        });
      });
    }catch(error){
      console.error(error);
      throw error;
    }
  }

  async logIn(login: AuthenticationRequest): Promise<AuthenticationResponse> {
      try{
        return new Promise((resolve)=>{
          console.log("login: ", login);
          this.http.post<AuthenticationResponse>(`${this.url}/auth/authentication`, login).subscribe( token =>{
            if(token){
              localStorage.setItem("token", token.token);
              this.loadHeaders();
              this.userService.headers = this.headers;
              this.userService.loadUser();
              resolve(token);
            }
            else
              throw new Error("erreur lors de l'authentification");
          });
        });
      }
      catch(error){
        console.log("error lors de l'authentification: ", error);
        throw error;
      } 
    }

  isLoggedIn(): boolean {
    if(localStorage.getItem('token')){
      return true;
    }
    return false;
  }

  checkSatut() {
    const token = localStorage.getItem('token');
    if (token) {
      this.token = JSON.parse(token as string);
    }
  }

  logout() {
    this.token = '';
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

}

