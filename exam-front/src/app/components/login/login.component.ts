import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthenticationRequest } from '../../models/authentication-request';
import { AuthenticationResponse } from '../../models/authentication-response';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private router: Router,
    private authService: AuthService,
    private fb: FormBuilder
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      mdp: ['', Validators.required]
    });
  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  async onSubmit() {
    if (this.loginForm.valid) {
      const login: AuthenticationRequest = {email: this.loginForm.value.email, password: this.loginForm.value.mdp};
      try {
        const tokenObj: AuthenticationResponse = await this.authService.logIn(login);
        
        if (tokenObj.token.length > 0) {
          await this.router.navigate(['/home']);
        } else {
          console.log('Login failed');
        }
      } catch (error) {
        console.error('Submit error:', error);
      }
    }
  }

}
