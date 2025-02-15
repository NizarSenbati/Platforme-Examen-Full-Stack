import { Modules } from "./modules";

export interface User {
    id: number,
    email: string,
    firstName: string,
    lastName: string,
    role: string,
    modules: Modules[]
}
