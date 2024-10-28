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

  }

}