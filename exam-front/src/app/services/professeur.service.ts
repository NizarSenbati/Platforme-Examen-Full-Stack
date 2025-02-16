import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { UserService } from './user.service';
import { Modules } from '../models/modules';
import { AuthService } from './auth.service';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfesseurService {
  url = 'http://127.0.0.1:8080/api/professeurs';

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

  async creerProfesseur(professeur: User) {
      try {
        const user = await firstValueFrom(
          this.http.post<User>(this.url, professeur, { headers: this.authService.headers })
        );
        if (user) {
          return user;
        } else {
          throw new Error("Erreur lors de la création de l'étudiant");
        }
      } catch (error) {
        console.error(error);
        throw error;
      }
  }

  async modifierProfesseur(professeur: User) {
    try {
      const user = await firstValueFrom(
        this.http.put<User>(`${this.url}/${professeur.id}`, professeur, { headers: this.authService.headers })
      );
      if (user) {
        return user;
      } else {
        throw new Error("Erreur lors de la modification de l'étudiant");
      }
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async supprimerProfesseur(id: number) {
    try {
      const user = await firstValueFrom(
        this.http.delete<User>(`${this.url}/${id}`, { headers: this.authService.headers })
      );
      if (user) {
        return user;
      } else {
        throw new Error("Erreur lors de la suppression de l'étudiant");
      }
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  empty(): User {
    let user: User = {
      id: 404,
      firstName: '',
      lastName: '',
      email: '',
      role: '',
      modules: []
    }
    return user;
  }

}
