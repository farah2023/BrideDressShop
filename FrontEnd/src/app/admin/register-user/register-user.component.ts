import { Component } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { UserRegistration } from '../../auth/model/auth.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrl: './register-user.component.css'
})
export class RegisterUserComponent {
  user: UserRegistration = {
    email: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    password: '',
    street: '',
    streetNumber: '',
    city: '',
    country: '',
    postalCode: '',
    isSenior: false,
    userType: ''
  };

  constructor(private authService: AuthService) { }

  onSubmit() {
    this.authService.registerUser(this.user).subscribe(
      response => {
        console.log('User successfully registered', response);
        Swal.fire({
          title: 'Success',
          text: 'User successfully registered!',
          icon: 'success',
          confirmButtonText: 'OK'
        });
      },
      error => {
        console.error('Error registering user', error);
        Swal.fire({
          title: 'Error',
          text: 'Error while registering user. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    );
  }
}