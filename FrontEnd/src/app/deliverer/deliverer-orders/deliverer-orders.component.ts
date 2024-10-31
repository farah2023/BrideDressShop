import { Component, OnInit } from '@angular/core';
import { Order, OrderStatus } from '../../seller/model/order.model';
import { OrdersService } from '../../seller/orders.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-deliverer-orders',
  templateUrl: './deliverer-orders.component.html',
  styleUrl: './deliverer-orders.component.css'
})
export class DelivererOrdersComponent implements OnInit {
  orders: Order[] = [];
  filteredOrders: Order[] = [];
  selectedStatus: string = '';
  delivererFilter: string = '';
  addressFilter = '';
  dateSortOrder: string = 'asc';

  orderStatuses = Object.values(OrderStatus);

  constructor(private orderService: OrdersService) { }

  ngOnInit(): void {
    this.loadOrders();
  }

  statusTranslations: { [key in OrderStatus]: string } = {
    [OrderStatus.CREATED]: 'Created',
    [OrderStatus.CANCELLED]: 'Cancelled',
    [OrderStatus.APPROVED]: 'Approved',
    [OrderStatus.COLLECTED_FOR_DELIVERY]: 'Collected for Delivery',
    [OrderStatus.DELIVERY_COMPLETED_SUCCESSFULLY]: 'Delivery Completed - Successful',
    [OrderStatus.DELIVERY_COMPLETED_UNSUCCESSFULLY]: 'Delivery Completed - Unsuccessful',
    [OrderStatus.REJECTED]: 'Rejected'
  };

  private showLoadingSpinner(): void {
    Swal.fire({
      title: 'Processing...',
      html: '<div class="loading-spinner"></div>',
      allowOutsideClick: false,
      showConfirmButton: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });
  }


  private async showSuccessAnimation(message: string): Promise<void> {
    await Swal.fire({
      icon: 'success',
      title: 'Success!',
      text: message,
      showConfirmButton: false,
      timer: 1500,
      timerProgressBar: true,
      didOpen: () => {
        const popup = Swal.getPopup();
        popup?.classList.add('animate__animated', 'animate__bounceIn');
      }
    });
  }

  loadOrders(): void {
    this.showLoadingSpinner();
    this.orderService.getAllOrders().subscribe(
      data => {
        this.orders = data;
        this.applyFilters();
        Swal.close();
      },
      error => {
        Swal.fire({
          icon: 'error',
          title: 'Error Loading Orders',
          text: 'Unable to load orders. Please try again.',
          confirmButtonColor: '#3085d6'
        });
      }
    );
  }

  applyFilters(): void {
    this.filteredOrders = this.orders.filter(order => {
      const matchesStatus = !this.selectedStatus || order.status === this.getStatusKey(this.selectedStatus);
      const matchesDeliverer = !this.delivererFilter ||
        (order.deliverer && (order.deliverer.firstName.toLowerCase().includes(this.delivererFilter.toLowerCase()) ||
          order.deliverer.lastName.toLowerCase().includes(this.delivererFilter.toLowerCase())));
      const matchesAddress = !this.addressFilter ||
        `${order.address.street} ${order.address.city} ${order.address.country}`.toLowerCase().includes(this.addressFilter.toLowerCase());

      return matchesStatus && matchesDeliverer && matchesAddress;
    });
  }

  markOrderCollectedForDelivery(orderId: number): void {
    Swal.fire({
      title: 'Collect Order for Delivery',
      html: `
        <div class="custom-swal-content">
          <i class="fas fa-truck fa-3x" style="color: #3085d6; margin-bottom: 15px;"></i>
          <p>Are you ready to collect this order for delivery?</p>
          <small>This action indicates you're taking responsibility for delivering this order.</small>
        </div>
      `,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Yes, collect for delivery',
      cancelButtonText: 'No, not yet',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      reverseButtons: true,
      showClass: {
        popup: 'animate__animated animate__fadeInDown'
      }
    }).then((result) => {
      if (result.isConfirmed) {
        this.showLoadingSpinner();
        this.orderService.colectOrderForDelivery(orderId).subscribe(
          async response => {
            await this.showSuccessAnimation('Order collected!');

            // Show follow-up confirmation with details
            await Swal.fire({
              title: 'Order Collected Successfully',
              html: `
                <div class="success-details">
                  <i class="fas fa-check-circle fa-4x" style="color: #28a745; margin-bottom: 20px;"></i>
                  <h4>Next Steps:</h4>
                  <ul style="text-align: left; margin-top: 10px;">
                    <li>Ensure all items are present</li>
                    <li>Check delivery address</li>
                    <li>Plan your route</li>
                  </ul>
                </div>
              `,
              icon: 'success',
              confirmButtonText: 'Got it!',
              confirmButtonColor: '#28a745',
              showClass: {
                popup: 'animate__animated animate__fadeInUp'
              }
            });

            this.loadOrders();
          },
          error => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'Failed to update order status. Please try again.',
              confirmButtonColor: '#3085d6'
            });
          }
        );
      }
    });
  }
  markOrderAsSuccesfullyDelivered(orderId: number): void {
    Swal.fire({
      title: 'Confirm Successful Delivery',
      html: `
        <div class="custom-swal-content">
          <i class="fas fa-check-circle fa-3x" style="color: #28a745; margin-bottom: 15px;"></i>
          <p>Has this order been successfully delivered to the customer?</p>
          <small>Please confirm only if the customer has received their order.</small>
        </div>
      `,
      input: 'checkbox',
      inputPlaceholder: 'Customer signature received',
      icon: 'success',
      showCancelButton: true,
      confirmButtonText: 'Yes, delivered successfully',
      cancelButtonText: 'No, not yet',
      confirmButtonColor: '#28a745',
      cancelButtonColor: '#d33',
      reverseButtons: true,
      showClass: {
        popup: 'animate__animated animate__fadeInDown'
      },
      inputValidator: (result) => {
        return !result && 'You need to confirm receiving customer signature';
      }
    }).then(async (result) => {
      if (result.isConfirmed) {
        this.showLoadingSpinner();
        this.orderService.markOrderCompletedSuccessfully(orderId).subscribe(
          async response => {
            await this.showSuccessAnimation('Delivery confirmed!');

            // Show delivery summary
            await Swal.fire({
              title: 'Delivery Complete',
              html: `
                <div class="delivery-summary">
                  <i class="fas fa-clipboard-check fa-4x" style="color: #28a745; margin-bottom: 20px;"></i>
                  <h4>Delivery Summary:</h4>
                  <div style="text-align: left; margin-top: 15px;">
                    <p>✓ Order delivered</p>
                    <p>✓ Customer signature received</p>
                    <p>✓ Delivery time recorded</p>
                  </div>
                </div>
              `,
              icon: 'success',
              confirmButtonText: 'Complete',
              confirmButtonColor: '#28a745',
              showClass: {
                popup: 'animate__animated animate__fadeInUp'
              }
            });

            this.loadOrders();
          },
          error => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'Failed to update delivery status. Please try again.',
              confirmButtonColor: '#3085d6'
            });
          }
        );
      }
    });
  }
  markOrderAsUnuccesfullyDelivered(orderId: number): void {
    Swal.fire({
      title: 'Report Failed Delivery',
      html: `
        <div class="custom-swal-content">
          <i class="fas fa-exclamation-circle fa-3x" style="color: #dc3545; margin-bottom: 15px;"></i>
          <p>Are you reporting this delivery as unsuccessful?</p>
        </div>
      `,
      icon: 'warning',
      input: 'select',
      inputOptions: {
        'not_home': 'Customer not at home',
        'wrong_address': 'Wrong address',
        'refused': 'Customer refused delivery',
        'other': 'Other reason'
      },
      inputPlaceholder: 'Select reason for failed delivery',
      showCancelButton: true,
      confirmButtonText: 'Continue',
      cancelButtonText: 'Cancel',
      confirmButtonColor: '#dc3545',
      cancelButtonColor: '#6c757d',
      reverseButtons: true,
      inputValidator: (value) => {
        if (!value) {
          return 'Please select a reason';
        }
        return null;
      },
      showClass: {
        popup: 'animate__animated animate__fadeInDown'
      }
    }).then(async (result) => {
      if (result.isConfirmed) {
        // If "Other" was selected, show additional input popup
        if (result.value === 'other') {
          const detailsResult = await Swal.fire({
            title: 'Additional Details',
            input: 'textarea',
            inputLabel: 'Please provide more details about the failed delivery:',
            inputPlaceholder: 'Enter detailed reason...',
            inputValidator: (value) => {
              if (!value) {
                return 'Please provide details';
              }
              return null;
            },
            showCancelButton: true,
            confirmButtonColor: '#dc3545'
          });

          if (!detailsResult.isConfirmed) {
            return;
          }
        }

        this.showLoadingSpinner();
        this.orderService.markOrderCompletedUnsuccessfully(orderId).subscribe(
          async response => {
            await this.showSuccessAnimation('Status updated');

            // Show follow-up action items
            await Swal.fire({
              title: 'Delivery Attempt Recorded',
              html: `
                <div class="failed-delivery-summary">
                  <i class="fas fa-clipboard-list fa-4x" style="color: #dc3545; margin-bottom: 20px;"></i>
                  <h4>Next Steps:</h4>
                  <ul style="text-align: left; margin-top: 15px;">
                    <li>Customer will be notified</li>
                    <li>Delivery will be rescheduled</li>
                    <li>Package returned to warehouse</li>
                  </ul>
                </div>
              `,
              icon: 'info',
              confirmButtonText: 'Understood',
              confirmButtonColor: '#3085d6',
              showClass: {
                popup: 'animate__animated animate__fadeInUp'
              }
            });

            this.loadOrders();
          },
          error => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'Failed to update delivery status. Please try again.',
              confirmButtonColor: '#3085d6'
            });
          }
        );
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
    this.sortOrders();
  }

  sortOrders(): void {
    this.filteredOrders.sort((a, b) => {
      const dateA = new Date(a.date).getTime();
      const dateB = new Date(b.date).getTime();
      return this.dateSortOrder === 'asc' ? dateA - dateB : dateB - dateA;
    });
  }
}