import { Component, OnInit } from '@angular/core';
import { Address, User } from '../../auth/model/auth.model';
import { AuthService } from '../../auth/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.css'] // Fixed typo from styleUrl to styleUrls
})
export class ClientProfileComponent implements OnInit {
  user: User | undefined;
  isEditing = false;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.getUser().subscribe((user: User) => {
      this.user = user;
    });
  }

  toggleEdit(): void {
    this.isEditing = true;
  }

  saveChanges(): void {
    if (this.user) {
      this.authService.updateUser(this.user).subscribe(
        () => {
          this.isEditing = false;
          Swal.fire({
            icon: 'success',
            title: 'Success',
            text: 'Data has been successfully updated!',
            confirmButtonText: 'OK'
          });
        },
        (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'An error occurred while saving.',
            confirmButtonText: 'OK'
          });
        }
      );
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'User data could not be loaded.',
        confirmButtonText: 'OK'
      });
    }
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.ngOnInit();
  }
}