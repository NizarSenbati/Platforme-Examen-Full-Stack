import { Exam } from "./exam";
import { Ressource } from "./ressource";
import { User } from "./user";

export interface Modules {
    id: number,
    nom: string,
    annee: number,
    session: string,
    professeur: User | null,
    etudiants: User[]
    exam: Exam | null,
    ressources: Ressource[]
}
