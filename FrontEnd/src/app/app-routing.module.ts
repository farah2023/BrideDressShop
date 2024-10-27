import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './common/home/home.component';
import { AllProductsComponent } from './product/all-products/all-products.component';
import { LoginComponent } from './auth/login/login.component';
import { ClientProfileComponent } from './client/client-profile/client-profile.component';
import { AdminProfileComponent } from './admin/admin-profile/admin-profile.component';
import { AuthGuard } from './auth/guard/auth.guard';
import { CartComponent } from './product/cart/cart.component';
import { CreateProductComponent } from './product/create-product/create-product.component';
import { RegisterClientComponent } from './auth/register-client/register-client.component';
import { AllUsersComponent } from './admin/all-users/all-users.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'all-products', component: AllProductsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ClientProfileComponent },
  { path: 'cart', component: CartComponent },
  { path: 'create-product', component: CreateProductComponent },
  { path: 'register-client', component: RegisterClientComponent },
  { path: 'all-users', component: AllUsersComponent },






];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
