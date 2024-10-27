import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../model/product.model';
import { Category } from '../model/category.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductService } from '../product.service';
import Swal from 'sweetalert2'; // Importing SweetAlert for notifications

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css'] // Fixed typo from styleUrl to styleUrls
})
export class EditProductComponent implements OnInit {
  @Input() product!: Product;
  categories: Category[] = [];

  constructor(public activeModal: NgbActiveModal, private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getAllCategories().subscribe(data => {
      this.categories = data;
    });
  }

  onSubmit(): void {
    this.productService.updateProduct(this.product.id!, this.product).subscribe(response => {
      Swal.fire({
        icon: 'success',
        title: 'Success',
        text: 'Product has been updated successfully.',
        confirmButtonText: 'OK'
      });
      this.activeModal.close('Updated');
    }, error => {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'An error occurred while updating the product.',
        confirmButtonText: 'OK'
      });
    });
  }
}