import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { ModulesService } from '../../services/modules.service';
import { Modules } from '../../models/modules';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-module',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './module.component.html',
  styleUrl: './module.component.scss'
})
export class ModuleComponent implements OnInit {
  module: Modules = this.modulesService.empty();
  user = this.userService.empty();
  loading: boolean = true; // Add a loading flag
  isVisible = false;

  constructor(
    private router: Router,
    private userService: UserService,
    private modulesService: ModulesService
  ) { }

  async ngOnInit() {
    this.user = await this.userService.loadUser();
    await this.loadModule();
    this.loading = false; // Set loading to false when done
  }

  async loadModule(): Promise<Modules>{
    return new Promise(resolve => {
        this.router.events.subscribe(event =>{
        if(event instanceof NavigationEnd){
          let compo = event.urlAfterRedirects.slice(1);
          if(compo.includes('module')){
            let id = compo.split('/')[1];
            this.user.modules.forEach(module => {
              if(module.id == Number(id)){
                this.module = module;
                resolve(module);
              }
            });
          }
        }
      });
    });
  }

  async loadExam(){
    this.module.exam = await this.modulesService.chargerExam(this.module.id);
  }

  async loadRessources(){
    this.module.ressources = await this.modulesService.chargerRessources(this.module.id);
  }

  async loadEtudiants(){
    this.module.etudiants = await this.modulesService.chargerEtudiants(this.module.id);
  }

  toExam(){
    this.router.navigate(['/examen', this.module.id]);
  }

  toRessource(){
    this.router.navigate(['/ressources', this.module.id]);
  }

  showPopup() {
    this.isVisible = true;
  }

  closePopup() {
    this.isVisible = false;
  }

  takeExam() {
    this.router.navigate(['/passer-examen', this.module.id]);
  }

}
