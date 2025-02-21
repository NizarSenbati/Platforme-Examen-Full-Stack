import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Modules } from '../../models/modules';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-side-bar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.scss'
})
export class SideBarComponent {
  current: number = 0;
  etat: string = 'selected';
  lastone: number = 0;
  modules: {"module": Modules, "etat": string}[] = [];

  constructor(private router: Router, private authService: AuthService, private userService: UserService) { }

  async ngOnInit() {
      this.findCurrent();
      await this.chargerModules();
  }

  async chargerModules(){
    // const modules = await this.userService.chargerModules();
    const modules = (await this.userService.loadUser()).modules;
    modules.forEach(module => {
      this.modules.push({ "module": module, etat: 'button' });
    });
  }

  findCurrent(){
    this.router.events.subscribe(event =>{
      if(event instanceof NavigationEnd){
        const nom_current: string = event.urlAfterRedirects;
        switch (nom_current){
          case '/dashboard':
            this.current = 0;
            break;
          case '/transaction':
            this.current = 1;
            break;
          case '/compte':
            this.current = 2;
            break;
          case '/profil':
            this.current = 5;
            break;
          case '/aide':
            this.current = 4;
            break;
        }
        if(nom_current.includes('/dashboard')){
          this.current = 0;
        }
        // this.changeCurrent(this.current);
      }
    });
  }

  openHome(){
    this.modules.forEach(m => {
      m.etat = 'button';
    });
    this.etat = 'selected';
    this.router.navigate(['/home']);
  }

  openModule(module: {"module": Modules, "etat": string}){
    this.modules.forEach(m => {
      m.etat = 'button';
    });
    this.etat = 'button';
    module.etat = 'selected';
    this.router.navigate([`/module/${module.module.id}`]);
  }

  logout(){
    this.authService.logout();
  }
}
