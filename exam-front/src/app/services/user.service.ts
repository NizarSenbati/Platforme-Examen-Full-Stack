import { Injectable, OnInit } from '@angular/core';
import { User } from '../models/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, firstValueFrom, Observable, throwError } from 'rxjs';
import { Modules } from '../models/modules';

@Injectable({
  providedIn: 'root'
})
export class UserService implements OnInit {
  url: string = 'http://127.0.0.1:8080/api';
  user: User = this.empty();
  token: string = '';
  headers!: HttpHeaders;
  modules: Modules[] = [];

  constructor(
    private http: HttpClient
  ) { }

  ngOnInit(){
    this.loadUser();
  }

  async loadUser(){
    if(localStorage.getItem("user")){
      console.log("User loaded from local storage");
      this.user = JSON.parse(localStorage.getItem("user") || '{}');
    }
    else{
      let u = await this.getUser();
      if(u){
        this.user = u;
      }
    }
    return this.user;
  }

  async getUser(): Promise<User> {
    return firstValueFrom(
      this.http.get<User>(`${this.url}/auth/data`, { headers: this.headers })
    ).then(async user => {
      if (user) {
        this.user = user;
        user.modules = await this.chargerModules();
        this.user = user;
        console.log("User loaded:", user);
        localStorage.setItem("user", JSON.stringify(user));
        return user;
      }
      throw new Error("Error loading user");
    }).catch(error => {
      console.error(error);
      return this.empty();
    });
  }

async chargerModules(): Promise<Modules[]> {
    return firstValueFrom(
      this.http.get<Modules[]>(
        // `${this.url}/${this.user.role.toLowerCase()}s/${this.user.id}/modules`,
        `${this.url}/professeurs/${this.user.id}/modules`,
        { headers: this.headers }
      )
    ).then(modules => {
      if (modules) {
        console.log("Modules loaded:", modules);
        return modules;
      } else {
        throw new Error("Erreur lors du chargement des modules");
      }
    }).catch(error => {
      throw error;
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

