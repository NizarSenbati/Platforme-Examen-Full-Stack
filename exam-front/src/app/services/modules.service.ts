import { Injectable } from '@angular/core';
import { Modules } from '../models/modules';
import { UserService } from './user.service';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { firstValueFrom } from 'rxjs';
import { Exams } from '../models/exams';

@Injectable({
  providedIn: 'root'
})
export class ModulesService {
  url: string = 'http://127.0.0.1:8080//api/modules';



  constructor(
    private userService: UserService,
    private http: HttpClient
  ) { }

  async chargerEtudiants(id: number): Promise<User[]> {
    return firstValueFrom(
      this.http.get<User[]>(`${this.url}/${id}/etudiants`, { headers: this.userService.headers })
    ).then(etudiants => {
      if (etudiants) {
        return etudiants;
      } else {
        throw new Error("Erreur lors du chargement des étudiants");
      }
    }).catch(error => {
      throw error;
    });
  }

  async chargerExam(id: number): Promise<any> {
    try{
      let exam = await firstValueFrom(
        this.http.get<Exams>(`${this.url}/${id}/exams`, { headers: this.userService.headers })
      )
      if(exam)
        return exam;
      else throw new Error("erreur lors du chargement de l'exam pour le module d'id : "+ id);
    }catch (e){
      console.error("error: ", e);
      throw e;
    }
  }

  async creerModule(module: Modules): Promise<Modules> {
    return firstValueFrom(
      this.http.post<Modules>(this.url, module, { headers: this.userService.headers })
    ).then(module => {
      if (module) {
        return module;
      } else {
        throw new Error("Erreur lors de la création du module");
      }
    }).catch(error => {
      throw error;
    });
  }

  async modifierModule(module: Modules): Promise<Modules> {
    return firstValueFrom(
      this.http.put<Modules>(`${this.url}/${module.id}`, module, { headers: this.userService.headers })
    ).then(module => {
      if (module) {
        return module;
      } else {
        throw new Error("Erreur lors de la modification du module");
      }
    }).catch(error => {
      throw error;
    });
  }

  async supprimerModule(id: number): Promise<boolean> {
    return firstValueFrom(
      this.http.delete<boolean>(`${this.url}/${id}`, { headers: this.userService.headers })
    ).then(result => {
      return result;
    }).catch(error => {
      throw error;
    });
  }
  
  empty(): Modules{
    let module: Modules = {
      id: 404,
      nom: '',
      annee: 404,
      session: 'vide',
      professeur: null,
      etudiants: [],
      exam: null
    }
    return module;
  }
}
