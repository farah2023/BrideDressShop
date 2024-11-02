import { Component, OnInit } from '@angular/core';
import { User, UserWRole } from '../../auth/model/auth.model';
import { AuthService } from '../../auth/auth.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrl: './all-users.component.css'
})
export class AllUsersComponent implements OnInit {
  users: UserWRole[] = [];
  clients: UserWRole[] = [];
  workers: UserWRole[] = [];

  // Role translations in English
  roleTranslations: any = {
    'ADMIN': 'Administrator',
    'DELIVERER': 'Delivery Person',
    'SELLER': 'Sales Representative',
    'CLIENT': 'Customer'
  };

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    // Show loading state
    Swal.fire({
      title: 'Loading Users',
      text: 'Please wait...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });

    this.authService.getAllUsers().subscribe({
      next: (data: UserWRole[]) => {
        this.users = data;
        this.splitUsersByRole();
        Swal.close();
      },
      error: (error) => {
        console.error('Error loading users:', error);
        Swal.fire({
          title: 'Error',
          text: 'Failed to load users. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  splitUsersByRole() {
    this.clients = this.users.filter(user => user.role.name === 'CLIENT');
    this.workers = this.users.filter(user => user.role.name !== 'CLIENT');
  }

  deleteUser(userId: number) {
    // Initial confirmation dialog
    Swal.fire({
      title: 'Confirm Deletion',
      text: 'Are you sure you want to delete this user? This action cannot be undone.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete user',
      cancelButtonText: 'Cancel',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        // Show loading state
        Swal.fire({
          title: 'Deleting User',
          text: 'Please wait...',
          allowOutsideClick: false,
          didOpen: () => {
            Swal.showLoading();
          }
        });

        this.authService.deleteUser(userId).subscribe({
          next: () => {
            // Success popup after deletion
            Swal.fire({
              position: 'center',
              icon: 'success',
              title: 'User Deleted Successfully',
              text: 'The user has been removed from the system',
              showConfirmButton: true,
              confirmButtonText: 'OK',
              confirmButtonColor: '#28a745'
            }).then(() => {
              this.loadUsers(); // Reload the users list after confirmation
            });
          },
          error: (error) => {
            console.error('Error deleting user:', error);
            Swal.fire({
              title: 'Error',
              text: 'Failed to delete user. Please try again.',
              icon: 'error',
              confirmButtonText: 'OK',
              confirmButtonColor: '#d33'
            });
          }
        });
      }
    });
  }
  enableUser(userId: number, isEnabled: boolean) {
    // Show loading state
    Swal.fire({
      title: isEnabled ? 'Enabling User' : 'Disabling User',
      text: 'Please wait...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });

    this.authService.enableDisableUser(userId, isEnabled).subscribe({
      next: (response) => {
        console.log('Response:', response);
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: `User ${isEnabled ? 'Enabled' : 'Disabled'} Successfully`,
          showConfirmButton: true,
          confirmButtonText: 'OK',
          confirmButtonColor: '#28a745'
        }).then(() => {
          this.loadUsers(); // Reload the users list after confirmation
        });
      },
      error: (error) => {
        console.error('Error updating user status:', error);
        Swal.fire({
          title: 'Error',
          text: 'Failed to update user status. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK',
          confirmButtonColor: '#d33'
        });
      }
    });

  }

}
