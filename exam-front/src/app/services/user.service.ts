import { Injectable, OnInit } from '@angular/core';
import { User } from '../models/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  url: string = 'http://127.0.0.1:8080/api';
  user: User = this.empty();
  token: string = '';
  headers!: HttpHeaders;

  constructor(
    private http: HttpClient
  ) { }

  loadUser(): void{
    this.http.get<User>(`${this.url}/auth/data`, {headers: this.headers})
      .subscribe(user => {
        if(user){
          this.user = user;
        }
        throw new Error("Error loading user");
      });
  }

  addUser(newUser: User): Observable<User>{
    return this.http.post<User>(`${this.url}/${newUser.role === "ETUDIANT" ? 'etudiant' : 'professeur'}/add`, newUser, {headers: this.headers })
      .pipe(
    
        catchError(error => {
          console.error("Error adding user:", error);
          return throwError(() => new Error("Error adding user"));
        })
      );
  }

  async modUser(newUser: User): Promise<User>{
    return new Promise(resolve =>{ this.http.put<User>(`${this.url}/${newUser.role == "ETUDIANT" ? 'etudiant' : 'professeur'}/${newUser.id}`, newUser, {headers: this.headers })
      .subscribe(user => {
        if(user){
          return resolve(user);
        }
        throw new Error("Erreur lors de la modification de l'utilisateur");
      });
    });
  }

  async delUser(id: number, role: string): Promise<User>{
    return new Promise(resolve =>{ this.http.delete<User>(`${this.url}/${role == "ETUDIANT" ? 'etudiant' : 'professeur'}/${id}`, {headers: this.headers })
      .subscribe(user => {
        if(user){
          return resolve(user);
        }
        throw new Error("Erreur lors de la suppression de l'utilisateur");
      });
    });
  }

  empty(): User{
    let user: User = {
      id: 404,
      email: "vide",
      firstName: "vide",
      lastName: "vide",
      role: "vide",
      modules: []
    }
    return user;
  }

}

