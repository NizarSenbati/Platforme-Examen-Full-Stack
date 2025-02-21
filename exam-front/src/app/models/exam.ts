import { Question } from "./questions";

export interface Exam {
    id: number;
    titre: string;
    duree: number;
    notes: number[];
    questions: Question[];
}
