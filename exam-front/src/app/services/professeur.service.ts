import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { UserService } from './user.service';
import { Modules } from '../models/modules';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ProfesseurService {
  url = 'http://localhost:3000/api/professeurs';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private userService: UserService
  ) { }
  
  async getModules(){
    if(this.userService.user.role === "PROFESSEUR"){
      return this.http.get<Modules[]>(`${this.url}/${this.userService.user.id}/modules`, {headers: this.authService.headers}).subscribe(modules => {
        if(modules){
          this.userService.user.modules = modules;
          return modules;
        }
        throw new Error("Erreur lors de la récupération des modules");
      }
      );
    }
    return [];
  }

}
