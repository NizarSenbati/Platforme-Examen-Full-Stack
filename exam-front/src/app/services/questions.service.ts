import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Question } from '../models/questions';
import { AuthService } from './auth.service';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuestionsService {
  url: string = 'http://localhost:3000/questions';


  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  async chargerQuestions(): Promise<Question[]>{
    try{
      let questions: Question[] = await firstValueFrom(
        this.http.get<Question[]>(`${this.url}/questions`, {headers: this.authService.headers})
      );
      if(questions){
        return questions;
      }
      throw new Error("Erreur lors de la récupération des questions");
    }
    catch(error){
      console.error(error);
      return [];
    }
  }

  async ajouterQuestion(question: Question): Promise<Question>{
    try{
      let reponse = await firstValueFrom(
        this.http.post<Question>(`${this.url}/add`, question, {headers: this.authService.headers})
      );
      if(reponse){
        return reponse;
      }
      throw new Error("Erreur lors de l'ajout de la question");
    }
    catch(error){
      console.error(error);
      return this.empty();
    }
  }

  async modifierQuestion(question: Question): Promise<Question>{
    try{
      let reponse = await firstValueFrom(
        this.http.put<Question>(`${this.url}/${question.id}`, question, {headers: this.authService.headers})
      );
      if(reponse){
        return reponse;
      }
      throw new Error("Erreur lors de la modification de la question");
    }
    catch(error){
      console.error(error);
      return this.empty();
    }
  }

  async supprimerQuestion(id: number): Promise<boolean>{
    try{
      let reponse = await firstValueFrom(
        this.http.delete<boolean>(`${this.url}/${id}`, {headers: this.authService.headers})
      );
      if(reponse){
        return reponse;
      }
      throw new Error("Erreur lors de la suppression de la question");
    }
    catch(error){
      console.error(error);
      return false;
    }
  }

  async getQuestionBySujet(sujet: string): Promise<Question[]>{
    try{
      let questions: Question[] = await firstValueFrom(
        this.http.get<Question[]>(`${this.url}/sujet/${sujet}`, {headers: this.authService.headers})
      );
      if(questions){
        return questions;
      }
      throw new Error("Erreur lors de la récupération des questions");
    }
    catch(error){
      console.error(error);
      return [];
    }
  }

  empty(): Question{
    let question: Question = {
      id: 404,
      question: "vide?",
      difficulte: "vide",
      sujet: "general knowledge",
      rep1: "vide1",
      rep2: "vide2",
      rep3: "vide3",
      rep4: "vide3",
      indice: 404
    }
    return question;
  }

}
