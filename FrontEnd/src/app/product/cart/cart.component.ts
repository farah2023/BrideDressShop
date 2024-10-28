import { Component, OnInit } from '@angular/core';
import { Cart } from '../model/cart.model';
import { CartService } from '../cart.service';
import { Address, User } from '../../auth/model/auth.model';
import { OrdersService } from '../../seller/orders.service';
import { OrderRequest } from '../../seller/model/order.model';
import { AuthService } from '../../auth/auth.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {
  cart: Cart = new Cart();
  cartTotal: number = 0;
  showAddressForm: boolean = false;
  useExistingAddress: boolean = false;
  userAddress?: Address;
  isAuthenticated: boolean = false;

  constructor(
    private cartService: CartService,
    private orderService: OrdersService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.cart = this.cartService.getCart();
    this.calculateTotal();
    this.isAuthenticated = this.authService.isAuthenticated();

    if (this.isAuthenticated) {
      this.fetchUserAddress();
    }
    console.log(this.cart);
  }

  fetchUserAddress() {
    this.authService.getUser().subscribe({
      next: (user: User) => {
        this.userAddress = user.address;
      },
      error: (error) => {
        console.error('Error fetching user address:', error);
        Swal.fire({
          title: 'Error',
          text: 'Failed to load your address information. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  removeFromCart(productId: number | undefined) {
    if (productId !== undefined) {
      Swal.fire({
        title: 'Remove Item',
        text: 'Are you sure you want to remove this item from your cart?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, remove it',
        cancelButtonText: 'No, keep it',
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6'
      }).then((result) => {
        if (result.isConfirmed) {
          this.cartService.removeFromCart(productId);
          this.calculateTotal();
          Swal.fire({
            position: 'center',
            icon: 'success',
            title: 'Item Removed',
            text: 'The item has been removed from your cart',
            showConfirmButton: false,
            timer: 1500
          });
        }
      });
    } else {
      console.error('Product id is undefined');
    }
  }

  increaseQuantity(item: any) {
    item.quantity++;
    this.cartService.saveCart();
    this.calculateTotal();
  }

  decreaseQuantity(item: any) {
    if (item.quantity > 1) {
      item.quantity--;
      this.cartService.saveCart();
      this.calculateTotal();
    }
  }

  calculateTotal() {
    this.cartTotal = this.cartService.getTotal();
  }

  createOrder() {
    if (this.isAuthenticated) {
      if (this.userAddress) {
        this.useExistingAddress = true;
      } else {
        this.showAddressForm = true;
      }
    } else {
      window.location.href = '/unauthenticated-order';
    }
  }

  confirmUseExistingAddress() {
    if (this.userAddress) {
      // Show loading state
      Swal.fire({
        title: 'Creating Order',
        text: 'Please wait while we process your order...',
        allowOutsideClick: false,
        didOpen: () => {
          Swal.showLoading();
        }
      });

      const orderRequest: OrderRequest = {
        orderItems: this.cart.items.map(item => ({
          product: item.product,
          quantity: item.quantity,
          price: item.product.price * item.quantity
        })),
        address: this.userAddress
      };

      this.orderService.createOrder(orderRequest).subscribe({
        next: (response) => {
          this.cartService.clearCart();
          this.cartTotal = 0;
          this.useExistingAddress = false;

          Swal.fire({
            title: 'Order Successful!',
            text: 'Your order has been successfully created',
            icon: 'success',
            confirmButtonText: 'Continue Shopping',
            confirmButtonColor: '#28a745'
          });
        },
        error: (error) => {
          console.error('Error creating order:', error);
          Swal.fire({
            title: 'Error',
            text: 'Failed to create your order. Please try again.',
            icon: 'error',
            confirmButtonText: 'OK'
          });
        }
      });
    }
  }

  onAddressSubmitted(address: Address) {
    // Show loading state
    Swal.fire({
      title: 'Creating Order',
      text: 'Please wait while we process your order...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });

    const orderRequest: OrderRequest = {
      orderItems: this.cart.items.map(item => ({
        product: item.product,
        quantity: item.quantity,
        price: item.product.price * item.quantity
      })),
      address: address
    };

    this.orderService.createOrder(orderRequest).subscribe({
      next: (response) => {
        this.cartService.clearCart();
        this.showAddressForm = false;
        this.cartTotal = 0;

        Swal.fire({
          title: 'Order Successful!',
          text: 'Your order has been successfully created',
          icon: 'success',
          confirmButtonText: 'Continue Shopping',
          confirmButtonColor: '#28a745'
        });
      },
      error: (error) => {
        console.error('Error creating order:', error);
        Swal.fire({
          title: 'Error',
          text: 'Failed to create your order. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  declineExistingAddress() {
    this.useExistingAddress = false;
    this.showAddressForm = true;
  }
}