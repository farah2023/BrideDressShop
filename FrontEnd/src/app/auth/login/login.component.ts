import { Component, ViewEncapsulation } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { AuthenticationResponse, Login } from '../model/auth.model';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  encapsulation: ViewEncapsulation.Emulated
})
export class LoginComponent {
  public loginForm: FormGroup;

  public notEmptyString: ValidatorFn = (control: AbstractControl): { [key: string]: any } | null => {
    const value = control.value;
    if (value === null || value === undefined || value === '') {
      return { 'notEmptyString': true };
    }
    return null;
  };

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = new FormGroup({
      id: new FormControl(-1, [Validators.required]),
      email: new FormControl('', [
        Validators.required,
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      password: new FormControl('', [Validators.required, this.notEmptyString])
    });
  }

  login(): void {
    if (this.loginForm.invalid) {
      this.showErrorPopup('Please fill in all required fields correctly.');
      return;
    }

    const login: Login = {
      email: this.loginForm.value.email,
      password: this.loginForm.value.password,
    };

    Swal.fire({
      title: 'Logging in...',
      didOpen: () => {
        Swal.showLoading();
      }
    });

    this.authService.login(login).subscribe({
      next: (response: AuthenticationResponse) => {
        Swal.fire({
          icon: 'success',
          title: 'Login Successful!',
          showConfirmButton: false,
          timer: 500
        }).then(() => {
          this.router.navigate(['/']);
        });
      },
      error: (err: HttpErrorResponse) => {
        let errorMessage = 'An unexpected error occurred.';
        let title = 'Login Failed';

        if (err.status === 401) {
          errorMessage = 'Invalid email or password.';
        } else if (err.status === 403) {
          title = 'Account Disabled';
          errorMessage = 'Your account has been disabled. Please contact support.';
        } else if (err.status === 0) {
          errorMessage = 'Unable to connect to server. Please check your connection.';
        }

        Swal.fire({
          icon: 'error',
          title: title,
          text: errorMessage,
          confirmButtonColor: '#3085d6',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  // Method to show error pop-up for form validation
  private showErrorPopup(message: string): void {
    Swal.fire({
      icon: 'warning',
      title: 'Validation Error',
      text: message,
      confirmButtonColor: '#3085d6',
      confirmButtonText: 'OK'
    });
  }
}