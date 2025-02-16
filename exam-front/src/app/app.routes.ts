import { LoginComponent } from './components/login/login.component';
import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { authGuard } from './guards/auth.guard';
import { loginGuard } from './guards/login.guard';
import { ExamenComponent } from './components/examen/examen.component';
import { ModuleComponent } from './components/module/module.component';

export const routes: Routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {path: 'login', component: LoginComponent, title: 'Login', canActivate: [loginGuard]},
    {path: 'home', component: HomeComponent, title: 'Home', canActivate: [authGuard]},
    {path: 'examen', component: ExamenComponent, title: 'Examen', canActivate: [authGuard]},
    {path: 'module', component: ModuleComponent, title: 'Module', canActivate: [authGuard]},
];
