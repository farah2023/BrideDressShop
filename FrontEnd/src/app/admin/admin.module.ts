import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { MaterialModule } from '../../material/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AllUsersComponent } from './all-users/all-users.component';


@NgModule({
  declarations: [
    AdminProfileComponent,
    AllUsersComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule

  ]
})
export class AdminModule { }
