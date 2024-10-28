import { Component, OnInit } from '@angular/core';
import { Order, OrderStatus } from '../model/order.model';
import { OrdersService } from '../orders.service';
import { ToastrService } from 'ngx-toastr';
import { ReportService } from '../../common/report.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];
  filteredOrders: Order[] = [];
  selectedStatus: string = '';
  productFilter: string = '';

  orderStatuses = Object.values(OrderStatus);

  constructor(
    private orderService: OrdersService,
    private reportService: ReportService
  ) { }

  ngOnInit(): void {
    this.orderService.getAllOrders().subscribe(data => {
      this.orders = data;
      this.applyFilters();
    });
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

  applyFilters(): void {
    this.filteredOrders = this.orders.filter(order => {
      const matchesStatus = !this.selectedStatus || order.status === this.getStatusKey(this.selectedStatus);
      const matchesProduct = !this.productFilter || order.orderItems.some(item =>
        item.product.name.toLowerCase().includes(this.productFilter.toLowerCase())
      );

      return matchesStatus && matchesProduct;
    });
  }

  approveOrder(orderId: number): void {
    Swal.fire({
      title: 'Approve Order',
      text: 'Are you sure you want to approve this order?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Yes, approve it!',
      cancelButtonText: 'No, cancel',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.isConfirmed) {
        this.orderService.approveOrder(orderId).subscribe(
          response => {
            Swal.fire({
              icon: 'success',
              title: 'Approved!',
              text: 'Order has been successfully approved!'
            });
            this.loadOrders();
          },
          error => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'An error occurred while approving the order.'
            });
          }
        );
      }
    });
  }

  rejectOrder(orderId: number): void {
    Swal.fire({
      title: 'Reject Order',
      text: 'Are you sure you want to reject this order?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, reject it!',
      cancelButtonText: 'No, cancel',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        this.orderService.rejectOrder(orderId).subscribe(
          response => {
            Swal.fire({
              icon: 'success',
              title: 'Rejected!',
              text: 'Order has been successfully rejected!'
            });
            this.loadOrders();
          },
          error => {
            Swal.fire({
              icon: 'error',
              title: 'Error',
              text: 'An error occurred while rejecting the order.'
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

  loadOrders(): void {
    this.orderService.getAllOrders().subscribe(data => {
      this.orders = data;
      this.applyFilters();
    });
  }

  ngOnChanges(): void {
    this.applyFilters();
  }

  downloadReport(): void {
    // this.reportService.downloadOrdersReport();
  }
}