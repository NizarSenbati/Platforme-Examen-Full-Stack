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
  url: String = 'http://localhost:3000/api/modules';



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
