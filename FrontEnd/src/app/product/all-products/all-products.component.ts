import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../model/product.model';
import { CartService } from '../cart.service';
import { AuthService } from '../../auth/auth.service';
import { CurrentUser } from '../../auth/model/auth.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EditProductComponent } from '../edit-product/edit-product.component';
import { ConfirmDeleteComponent } from '../confirm-delete/confirm-delete.component';
import { Category } from '../model/category.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-all-products',
  templateUrl: './all-products.component.html',
  styleUrl: './all-products.component.css'
})
export class AllProductsComponent implements OnInit {
  products: Product[] = [];
  currentPage = 0;
  totalPages = 0;
  pageSize = 10;
  public currentUser: CurrentUser | undefined;
  categories: Category[] = [];
  searchTerm: string = '';
  selectedCategory: string = '';
  filteredProducts: Product[] = [];

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private authService: AuthService,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.loadProducts();
    this.loadCategories();
    this.authService.currentUser$.subscribe((user) => {
      if (user) {
        console.log("User(navbar): ", user.email, " Role: ", user.role);
        this.currentUser = user;
      }
    });
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (data: Product[]) => {
        this.products = data;
        this.filteredProducts = data;
      },
      error: () => {
        Swal.fire({
          title: 'Error',
          text: 'Error loading products. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  loadCategories(): void {
    this.productService.getAllCategories().subscribe({
      next: (data: Category[]) => {
        this.categories = data;
      },
      error: () => {
        Swal.fire({
          title: 'Error',
          text: 'Error loading categories. Please try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  filterProducts(): void {
    this.filteredProducts = this.products.filter(product => {
      const matchesName = this.searchTerm
        ? product.name.toLowerCase().includes(this.searchTerm.toLowerCase())
        : true;
      const matchesCategory = this.selectedCategory
        ? product.category?.name === this.selectedCategory
        : true;

      return matchesName && matchesCategory;
    });
  }

  addToCart(product: Product) {
    this.cartService.addToCart(product);

    Swal.fire({
      position: 'center',
      icon: 'success',
      title: 'Added to Cart',
      text: `${product.name} has been added to your shopping cart`,
      showConfirmButton: false,
      timer: 1500,
      customClass: {
        popup: 'animate__animated animate__fadeInDown'
      },
      backdrop: `
        rgba(0,0,123,0.1)
        left top
        no-repeat
      `
    });
  }

  openEditModal(product: Product) {
    const modalRef = this.modalService.open(EditProductComponent);
    modalRef.componentInstance.product = { ...product };

    modalRef.result.then((result) => {
      if (result === 'Updated') {
        this.loadProducts();
        Swal.fire({
          title: 'Success',
          text: 'Product successfully updated',
          icon: 'success',
          confirmButtonText: 'OK',
          timer: 1500,
          position: 'top-end',
          showConfirmButton: false,
          toast: true
        });
      }
    }).catch((error) => {
      console.log('Modal dismissed');
    });
  }

  deleteProduct(productId: number, productName: string) {
    Swal.fire({
      title: 'Are you sure?',
      text: `Do you want to delete ${productName}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6'
    }).then((result) => {
      if (result.isConfirmed) {
        this.productService.deleteProduct(productId).subscribe({
          next: () => {
            Swal.fire({
              title: 'Deleted!',
              text: 'Product has been deleted successfully',
              icon: 'success',
              timer: 1500,
              position: 'top-end',
              showConfirmButton: false,
              toast: true
            });
            this.loadProducts();
          },
          error: () => {
            Swal.fire({
              title: 'Error',
              text: 'Error occurred while deleting the product',
              icon: 'error',
              confirmButtonText: 'OK'
            });
          }
        });
      }
    });
  }
}