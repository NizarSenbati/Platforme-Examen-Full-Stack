import { Question } from "./questions";

export interface Exam {
    id: number;
    titre: string;
    duree: number;
    seuil: number;
    questions: Question[];
}
