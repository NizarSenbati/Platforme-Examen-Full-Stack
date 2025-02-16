import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { HttpClient } from '@angular/common/http';
import { Modules } from '../models/modules';
import { firstValueFrom } from 'rxjs';
import { AuthService } from './auth.service';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class EtudiantService {
  url: string = 'http://127.0.0.1:8080/api/etudiants';

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

  async chargerNotes(id: number): Promise<{module: string, note: number}[]>{
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

  async creerEtudiant(etudiant: User): Promise<User> {
    try {
      const user = await firstValueFrom(
        this.http.post<User>(this.url, etudiant, { headers: this.authService.headers })
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

  async modifierEtudiant(etudiant: User): Promise<User> {
    try {
      const user = await firstValueFrom(
        this.http.put<User>(`${this.url}/${etudiant.id}`, etudiant, { headers: this.authService.headers })
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

  async supprimerEtudiant(id: number): Promise<void> {
    try {
      await firstValueFrom(
        this.http.delete(`${this.url}/${id}`, { headers: this.authService.headers })
      );
    } catch (error) {
      console.error(error);
      throw error;
    }
  }

}
