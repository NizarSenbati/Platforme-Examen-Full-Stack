import { Injectable } from '@angular/core';
import { Exam } from '../models/exam';
import { Question } from '../models/questions';
import { QuestionsService } from './questions.service';
import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExamService {
  url = 'http://127.0.0.1:8080/api/exams';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private questionService: QuestionsService
  ) { }

  async generateExam(exam: Exam, difficulte: string, sujet: string, nbrQuestion: number): Promise<Exam> {
    let questions: Question[] = await this.questionService.getQuestionBySujet(sujet);
    if(questions.length < nbrQuestion){
      exam.questions = questions;
      console.log("Pas assez de questions pour générer l'examen");
      return exam;
    }

    if(difficulte !== 'tous'){
      questions = questions.filter(question => question.difficulte === difficulte);
    }

    exam.questions = questions.slice(0, nbrQuestion);
    return exam;
  }

  async addQuestionsToExam(exam: Exam, questionIds: number[]): Promise<Exam> {
    try{
      exam = await firstValueFrom(
        this.http.post<Exam>(
          `${this.url}/${exam.id}/questions`, questionIds, {headers: this.authService.headers}
        )
      );
      if(exam){
        return exam;
      }
    throw new Error("Erreur lors de l'ajout des questions à l'examen");
    }
    catch(error){
      console.error(error);
      return exam;
    }
  }

  async getNotes(examId: number): Promise<{nom: string, note: number}[]> {
    try{
      let notes: {nom: string, note: number}[] = await firstValueFrom(
        this.http.get<{nom: string, note: number}[]>(`${this.url}/${examId}/notes`, {headers: this.authService.headers})
      );
      return notes;
    }
    catch(error){
      console.error(error);
      return [];
    }
  }

  empty(): Exam{
    return {
      id: 0,
      titre: '',
      duree: 0,
      notes: [],
      questions: []
    };
  }

}
