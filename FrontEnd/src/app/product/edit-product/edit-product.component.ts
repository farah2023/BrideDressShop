import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../model/product.model';
import { Category } from '../model/category.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductService } from '../product.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrl: './edit-product.component.css'
})
export class EditProductComponent implements OnInit {
  @Input() product!: Product;
  categories: Category[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    // Load categories when component initializes
    this.loadCategories();
  }

  private loadCategories(): void {
    this.productService.getAllCategories().subscribe({
      next: (data) => {
        this.categories = data;
      },
      error: () => {
        Swal.fire({
          title: 'Error',
          text: 'Unable to load categories. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  onSubmit(): void {
    // Show loading state
    Swal.fire({
      title: 'Updating Product',
      text: 'Please wait while we process your changes...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });

    this.productService.updateProduct(this.product.id!, this.product).subscribe({
      next: (response) => {
        Swal.fire({
          title: 'Success!',
          text: 'Product has been successfully updated',
          icon: 'success',
          timer: 1500,
          showConfirmButton: false
        }).then(() => {
          this.activeModal.close('Updated');
        });
      },
      error: (error) => {
        console.error('Error updating product:', error);
        Swal.fire({
          title: 'Error',
          text: 'Failed to update product. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }
}