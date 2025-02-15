import { Exams } from "./exams";
import { User } from "./user";

export interface Modules {
    id: number,
    nom: string,
    annee: number,
    session: string,
    professeur: User | null,
    etudiants: User[]
    exam: Exams | null
}
