import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { HttpClient } from '@angular/common/http';
import { Modules } from '../models/modules';
import { firstValueFrom } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class EtudiantService {
  url: String = 'http://localhost:3000/api/etudiants';

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private http: HttpClient
  ) { }

  async getModules(): Promise<Modules[]> {
    if (this.userService.user.role !== "ETUDIANT") {
      return [];
    }
  
    try {
      const modules = await firstValueFrom(
        this.http.get<Modules[]>(
          `${this.url}/${this.userService.user.id}/modules`,
          { headers: this.authService.headers }
        )
      );
  
      if (modules) {
        this.userService.user.modules = modules;
        return modules;
      } else {
        throw new Error("Erreur lors de la récupération des modules");
      }
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

  async chargerNotes(id: number){
    try{
      let notes = await firstValueFrom(
        this.http.get<{module: string, note: number}[]>(`${this.url}/${id}/notes`, {headers: this.authService.headers})
      )
      if(notes){
        return notes;
      }
      throw new Error("erreur lors de l'obtention des notes pour l'etudiant d'id: "+ id);
    }
    catch(error){
      console.error(error);
      return [];
    }
  }

}
