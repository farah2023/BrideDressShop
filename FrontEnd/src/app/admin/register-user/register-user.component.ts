import { Component } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { UserRegistration } from '../../auth/model/auth.model';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
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

  constructor(
    private authService: AuthService,
    private toastr: ToastrService
  ) { }

  onSubmit() {
    this.authService.registerUser(this.user).subscribe(
      response => {
        console.log('User successfully registered', response);
        this.toastr.success('User successfully registered!', 'Success', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right',
          closeButton: true,
          progressBar: true,
        });
      },
      error => {
        console.error('Error registering user', error);
        this.toastr.error('Error while registering user. Please try again.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right',
          closeButton: true,
          progressBar: true,
        });
      }
    );
  }
}
