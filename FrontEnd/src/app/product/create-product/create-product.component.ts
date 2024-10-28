import { Component, OnInit } from '@angular/core';
import { ProductCreation } from '../model/product.model';
import { Category } from '../model/category.model';
import { ProductService } from '../product.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrl: './create-product.component.css'
})
export class CreateProductComponent implements OnInit {
  // Initialize product object with default values
  product: ProductCreation = {
    name: '',
    description: '',
    price: 0,
    supplies: 0,
    reserved: 0,
    categoryId: 0,
    imageUrl: ''
  };

  // Array to store available categories
  categories: Category[] = [];

  constructor(
    private productService: ProductService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  // Fetch all categories from the service
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

  // Handle form submission
  onSubmit(): void {
    // Display loading popup
    Swal.fire({
      title: 'Creating New Product',
      text: 'Please wait while we process your request...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
      }
    });

    this.productService.createProduct(this.product).subscribe({
      next: (response) => {
        console.log('Product successfully created', response);
        Swal.fire({
          title: 'Success!',
          text: 'Your product has been successfully created',
          icon: 'success',
          timer: 1500,
          showConfirmButton: false
        }).then(() => {
          this.router.navigate(['/all-products']);
        });
      },
      error: (error) => {
        console.error('Error while creating product', error);
        Swal.fire({
          title: 'Error',
          text: 'Unable to create product. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  // Validate form fields
  validateForm(): boolean {
    if (!this.product.name || !this.product.description || !this.product.price || !this.product.categoryId) {
      Swal.fire({
        title: 'Form Validation Error',
        text: 'Please complete all required fields',
        icon: 'warning',
        confirmButtonText: 'OK'
      });
      return false;
    }
    return true;
  }

  // Reset form with confirmation
  resetForm(): void {
    Swal.fire({
      title: 'Confirm Reset',
      text: 'Are you sure you want to clear all entered information?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, clear form',
      cancelButtonText: 'No, keep data'
    }).then((result) => {
      if (result.isConfirmed) {
        this.product = {
          name: '',
          description: '',
          price: 0,
          supplies: 0,
          reserved: 0,
          categoryId: 0,
          imageUrl: ''
        };
        Swal.fire({
          title: 'Form Cleared',
          text: 'All form fields have been reset',
          icon: 'success',
          timer: 1500,
          showConfirmButton: false
        });
      }
    });
  }
}