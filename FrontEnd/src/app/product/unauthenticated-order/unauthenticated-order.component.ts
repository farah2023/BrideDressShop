import { Component } from '@angular/core';
import { Address } from '../../auth/model/auth.model';
import { CartService } from '../cart.service';
import { OrdersService } from '../../seller/orders.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { OrderRequest } from '../../seller/model/order.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-unauthenticated-order',
  templateUrl: './unauthenticated-order.component.html',
  styleUrl: './unauthenticated-order.component.css'
})
export class UnauthenticatedOrderComponent {
  address: Address = {
    street: '',
    streetNumber: '',
    city: '',
    country: '',
    postalCode: ''
  };
  name: string = '';
  surname: string = '';
  phoneNumber: string = '';
  email: string = '';
  addressConfirmed: boolean = false;

  constructor(
    private cartService: CartService,
    private orderService: OrdersService,
    private router: Router
  ) { }

  onAddressSubmitted(address: Address) {
    this.address = address;
    this.addressConfirmed = true;
    Swal.fire('Success', 'Address successfully confirmed!', 'success');
  }

  submitOrder() {
    const cart = this.cartService.getCart();

    if (this.name && this.surname && this.phoneNumber && this.email && this.addressConfirmed) {
      const orderRequest: OrderRequest = {
        orderItems: cart.items.map(item => ({
          product: item.product,
          quantity: item.quantity,
          price: item.product.price * item.quantity
        })),
        address: this.address
      };

      this.orderService.createOrder(orderRequest).subscribe(
        response => {
          Swal.fire('Success', 'Order successfully created!', 'success');
          this.cartService.clearCart();
          this.router.navigate(['/']);
        },
        error => {
          Swal.fire('Error', 'There was an error while creating the order.', 'error');
        }
      );
    } else {
      Swal.fire('Error', 'Please enter all information and confirm the address.', 'error');
    }
  }
}