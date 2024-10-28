import { Component, OnInit } from '@angular/core';
import { Address, User } from '../../auth/model/auth.model';
import { AuthService } from '../../auth/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrl: './client-profile.component.css'
})
export class ClientProfileComponent implements OnInit {
  user: User | undefined;
  isEditing = false;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.getUser().subscribe({
      next: (user: User) => {
        this.user = user;
      },
      error: (error) => {
        Swal.fire({
          title: 'Error',
          text: 'Error loading user data.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  toggleEdit(): void {
    this.isEditing = true;
  }

  saveChanges(): void {
    if (this.user) {
      this.authService.updateUser(this.user).subscribe({
        next: () => {
          this.isEditing = false;
          Swal.fire({
            title: 'Success',
            text: 'Data successfully updated!',
            icon: 'success',
            confirmButtonText: 'OK'
          });
        },
        error: () => {
          Swal.fire({
            title: 'Error',
            text: 'Error occurred while saving data.',
            icon: 'error',
            confirmButtonText: 'OK'
          });
        }
      });
    } else {
      Swal.fire({
        title: 'Error',
        text: 'User data not loaded.',
        icon: 'error',
        confirmButtonText: 'OK'
      });
    }
  }

  cancelEdit(): void {
    Swal.fire({
      title: 'Confirm',
      text: 'Are you sure you want to cancel? All changes will be lost.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, cancel',
      cancelButtonText: 'No, keep editing'
    }).then((result) => {
      if (result.isConfirmed) {
        this.isEditing = false;
        this.ngOnInit();
      }
    });
  }
}