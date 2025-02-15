import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SideBarComponent } from "./components/side-bar/side-bar.component";
import { TopBarComponent } from "./components/top-bar/top-bar.component";
import { NgIf } from '@angular/common';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SideBarComponent, TopBarComponent, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass'
})
export class AppComponent {
  title = 'exam-front';
  login: boolean = false; //true lorsqu'on a une session
  newAcc: boolean = false;

  constructor(public authService: AuthService){ }

  ngOnInit(){
    this.login = false;
  }

  toNewAcc(nbr: number){
    if(nbr == 1)
      this.newAcc = true;
  }
}
