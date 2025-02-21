import { Component } from '@angular/core';
import { Question } from '../../models/questions';
import { Exam } from '../../models/exam';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ExamService } from '../../services/exam.service';
import { QuestionsService } from '../../services/questions.service';

@Component({
  selector: 'app-examen',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './examen.component.html',
  styleUrl: './examen.component.scss'
})
export class ExamenComponent {
  exam: Exam = this.examService.empty();


  ListQuestions: Question[] = [
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
  ];

  filteredQuestions: Question[] = [];
  subjects: string[] = [];
  difficulties: string[] = ['facile', 'moyen', 'difficile'];
  
  selectedSubject: string = '';
  selectedDifficulty: string = '';
  showNewQuestionForm: boolean = false;

  constructor(
    private examService: ExamService,
    private questionService: QuestionsService
  ) {}

  async ngOnInit() {
    this.filteredQuestions = [...this.ListQuestions];
    this.subjects = [...new Set(this.ListQuestions.map(q => q.sujet))];
    await this.loadQuestions();
  }

  async loadQuestions() {
    this.ListQuestions = await this.questionService.chargerQuestions();
    this.subjects = await this.questionService.chargerSujets();
    console.log(this.ListQuestions);
    console.log(this.subjects);
  }




  filterQuestions() {
    this.filteredQuestions = this.ListQuestions.filter(question => {
      const matchesSubject = !this.selectedSubject || question.sujet === this.selectedSubject;
      const matchesDifficulty = !this.selectedDifficulty || question.difficulte === this.selectedDifficulty;
      return matchesSubject && matchesDifficulty;
    });
  }

  addQuestion(question: Question) {
    if (!this.exam.questions.some(q => q.id === question.id)) {
      this.exam.questions.push({ ...question });
    }
  }

  removeQuestion(questionId: number) {
    this.exam.questions = this.exam.questions.filter(q => q.id !== questionId);
  }

  createExam() {
    // Implement API call to save exam
    console.log('Creating exam:', this.exam);
  }

  toggleNewQuestionForm() {
    this.showNewQuestionForm = !this.showNewQuestionForm;
  }

  isQuestionAdded(questionId: number): boolean {
    return this.exam.questions.some(q => q.id === questionId);
  }

}
