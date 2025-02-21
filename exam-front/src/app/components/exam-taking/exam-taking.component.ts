import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Question } from '../../models/questions';
import { Router } from '@angular/router';
import { Exam } from '../../models/exam';

@Component({
  selector: 'app-exam-taking',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './exam-taking.component.html',
  styleUrl: './exam-taking.component.scss'
})
export class ExamTakingComponent {
  exam: Exam = {
    id: 1,
    titre: "Examen de Fin de Module",
    duree: 120, // in minutes
    notes: [],
    questions: [
      {
        id: 1,
        question: "Quelle est la complexité temporelle de la recherche dans un arbre binaire de recherche équilibré ?",
        difficulte: "moyen",
        sujet: "structures de données",
        rep1: "O(1)",
        rep2: "O(n)",
        rep3: "O(log n)",
        rep4: "O(n^2)",
        indice: 3
      }
    ]
  };

  remainingTime: number = 0; // in seconds
  timerInterval: any;
  selectedAnswers: { [key: number]: number } = {};
  examSubmitted: boolean = false;

  constructor(private router: Router) {}

  ngOnInit() {
    this.initializeTimer();
  }

  ngOnDestroy() {
    this.clearTimer();
  }

  private initializeTimer() {
    // Convert minutes to seconds
    this.remainingTime = this.exam.duree * 60;
    this.startTimer();
  }

  private startTimer() {
    this.timerInterval = setInterval(() => {
      if (this.remainingTime > 0) {
        this.remainingTime--;
      } else {
        this.submitExam();
      }
    }, 1000);
  }

  private clearTimer() {
    if (this.timerInterval) {
      clearInterval(this.timerInterval);
    }
  }

  formatTime(seconds: number): string {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const remainingSeconds = seconds % 60;

    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}`;
  }

  selectAnswer(questionId: number, answerIndex: number) {
    this.selectedAnswers[questionId] = answerIndex;
  }

  isAnswerSelected(questionId: number, answerIndex: number): boolean {
    return this.selectedAnswers[questionId] === answerIndex;
  }

  getAnsweredQuestionsCount(): number {
    return Object.keys(this.selectedAnswers).length;
  }

  getTotalQuestionsCount(): number {
    return this.exam.questions.length;
  }

  submitExam() {
    this.examSubmitted = true;
    this.clearTimer();
    
    // Calculate score
    let score = 0;
    this.exam.questions.forEach((question: Question) => {
      if (this.selectedAnswers[question.id] === question.indice) {
        score++;
      }
    });

    // Here you would typically send the results to your backend
    console.log('Exam submitted with score:', score);
    // You could navigate to a results page here
    // this.router.navigate(['/exam-results'], { state: { score } });
  }
}
