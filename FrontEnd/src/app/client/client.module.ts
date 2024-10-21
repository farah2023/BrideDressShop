import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientProfileComponent } from './client-profile/client-profile.component';
import { MaterialModule } from '../../material/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';


@NgModule({
  declarations: [
    ClientProfileComponent,
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
export class ClientModule { }
