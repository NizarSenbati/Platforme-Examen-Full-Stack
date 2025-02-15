export interface Question {
    id: number;
    question: string;
    difficulte: string;
    sujet: string;
    rep1: string;
    rep2: string;
    rep3: string;
    rep4: string;
    indice: number;
}

const exampleQuestion: Question = {
    id: 1,
    question: "Quel pigeon est connut par Franschesco?",
    difficulte: "facile",
    sujet: "general knowledge",
    rep1: "Attique",
    rep2: "naVas",
    rep3: "lamana",
    rep4: "Jolikha",
    indice: 2
};
