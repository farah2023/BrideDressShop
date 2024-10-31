import { Component, OnInit } from '@angular/core';
import { Order, OrderStatus } from '../../seller/model/order.model';
import { OrdersService } from '../../seller/orders.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-client-orders',
  templateUrl: './client-orders.component.html',
  styleUrl: './client-orders.component.css'
})
export class ClientOrdersComponent implements OnInit {
  orders: Order[] = [];
  filteredOrders: Order[] = [];
  selectedStatus: string = '';
  productFilter: string = '';

  orderStatuses = Object.values(OrderStatus);

  // English translations for order statuses
  statusTranslations: { [key in OrderStatus]: string } = {
    [OrderStatus.CREATED]: 'Created',
    [OrderStatus.CANCELLED]: 'Cancelled',
    [OrderStatus.APPROVED]: 'Approved',
    [OrderStatus.COLLECTED_FOR_DELIVERY]: 'Out for Delivery',
    [OrderStatus.DELIVERY_COMPLETED_SUCCESSFULLY]: 'Delivery Successful',
    [OrderStatus.DELIVERY_COMPLETED_UNSUCCESSFULLY]: 'Delivery Failed',
    [OrderStatus.REJECTED]: 'Rejected'
  };

  constructor(private orderService: OrdersService) { }

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    // Show loading state
    Swal.fire({
      title: 'Loading Orders',
      text: 'Please wait...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });

    this.orderService.getClientsOrders().subscribe({
      next: (data) => {
        this.orders = data;
        this.applyFilters();
        Swal.close();
      },
      error: (error) => {
        console.error('Error loading orders:', error);
        Swal.fire({
          title: 'Error',
          text: 'Failed to load orders. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  applyFilters(): void {
    this.filteredOrders = this.orders.filter(order => {
      const matchesStatus = !this.selectedStatus || order.status === this.getStatusKey(this.selectedStatus);
      const matchesProduct = !this.productFilter || order.orderItems.some(item =>
        item.product.name.toLowerCase().includes(this.productFilter.toLowerCase())
      );

      return matchesStatus && matchesProduct;
    });
  }

  cancelOrder(orderId: number): void {
    // Show confirmation dialog
    Swal.fire({
      title: 'Cancel Order',
      text: 'Are you sure you want to cancel this order? This action cannot be undone.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, cancel order',
      cancelButtonText: 'No, keep order',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        // Show loading state
        Swal.fire({
          title: 'Cancelling Order',
          text: 'Please wait...',
          allowOutsideClick: false,
          didOpen: () => {
            Swal.showLoading();
          }
        });

        this.orderService.cancelOrder(orderId).subscribe({
          next: (response) => {
            console.log('Order cancelled', response);

            // Show success message with penalty points notification
            Swal.fire({
              title: 'Order Cancelled',
              html: '<p>Your order has been successfully cancelled.</p>' +
                '<p class="text-danger mt-2">Note: 5 penalty points have been added to your account.</p>',
              icon: 'success',
              confirmButtonText: 'OK',
              confirmButtonColor: '#28a745'
            }).then(() => {
              this.loadOrders(); // Reload orders after confirmation
            });
          },
          error: (error) => {
            console.error('Error cancelling order:', error);
            Swal.fire({
              title: 'Error',
              text: 'Failed to cancel the order. Please try again.',
              icon: 'error',
              confirmButtonText: 'OK'
            });
          }
        });
      }
    });
  }

  getStatusKey(value: string): OrderStatus | undefined {
    return Object.keys(this.statusTranslations).find(
      (key) => this.statusTranslations[key as keyof typeof this.statusTranslations] === value
    ) as OrderStatus | undefined;
  }

  ngOnChanges(): void {
    this.applyFilters();
  }
}