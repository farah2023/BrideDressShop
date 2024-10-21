import { Component, OnInit } from '@angular/core';
import { ProductCreation } from '../model/product.model';
import { Category } from '../model/category.model';
import { ProductService } from '../product.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2'; // Importing SweetAlert for notifications

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css'] // Fixed typo from styleUrl to styleUrls
})
export class CreateProductComponent implements OnInit {
  product: ProductCreation = {
    name: '',
    description: '',
    price: 0,
    supplies: 0,
    reserved: 0,
    categoryId: 0,
    imageUrl: ''
  };

  categories: Category[] = [];

  constructor(private productService: ProductService, private router: Router) { }

  ngOnInit(): void {
    this.productService.getAllCategories().subscribe(data => {
      this.categories = data;
    });
  }

  onSubmit(): void {
    this.productService.createProduct(this.product).subscribe(response => {
      console.log('Product created successfully', response);
      Swal.fire({
        icon: 'success',
        title: 'Success',
        text: 'Product has been created successfully.',
        confirmButtonText: 'OK'
      });
      this.router.navigate(['/all-products']);
    }, error => {
      console.error('Error creating product', error);
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'An error occurred while creating the product.',
        confirmButtonText: 'OK'
      });
    });
  }
}