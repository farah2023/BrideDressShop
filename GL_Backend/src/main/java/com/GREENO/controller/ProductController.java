package com.GREENO.controller;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/controller/ProductController.java
import com.agropharm.Entities.Product;
import com.agropharm.dto.CategoryDTO;
import com.agropharm.dto.ProductCreationDTO;
import com.agropharm.dto.ProductDTO;
import com.agropharm.mapper.DTOUtils;
import com.agropharm.service.CategoryService;
import com.agropharm.service.NotificationService;
import com.agropharm.service.ProductService;
=======
import com.GREENO.domain.Product;
import com.GREENO.dto.CategoryDTO;
import com.GREENO.dto.ProductCreationDTO;
import com.GREENO.dto.ProductDTO;
import com.GREENO.mapper.DTOUtils;
import com.GREENO.service.CategoryService;
import com.GREENO.service.ProductService;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/controller/ProductController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/all")
    public ResponseEntity<Set<ProductDTO>> getAll() {
        Set<ProductDTO> productDTOS = (Set<ProductDTO>) new DTOUtils().convertToDtos(productService.getAll(), new ProductDTO());
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<Set<CategoryDTO>> getAllCategories() {
        Set<CategoryDTO> categoryDTOS = (Set<CategoryDTO>) new DTOUtils().convertToDtos(categoryService.getAll(), new CategoryDTO());
        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody ProductCreationDTO productCreationDTO) {
        try {
            Product createdProduct = productService.createProduct(productCreationDTO);
<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/controller/ProductController.java
            String content = "Novi proizvod je dodat.";
            notificationService.createNotificationForAllUsersByRole("Novi proizvod", content, "SELLER");
=======
            String content = "New product has been added.";

>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/controller/ProductController.java

        }catch (Exception e){
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"You have successfully added new product\"}");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productUpdateDTO) {
        try {
            Product updatedProduct = productService.updateProduct(id, productUpdateDTO);
<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/controller/ProductController.java
            String content = "Proizvod " + productUpdateDTO.name + " je izmenjen.";
            notificationService.createNotificationForAllUsersByRole("Proizvod izmenjen", content, "SELLER");
=======
            String content = "Product " + productUpdateDTO.name + " has been modified.";

>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/controller/ProductController.java

        }catch (Exception e){
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"You have successfully updated product\"}");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/controller/ProductController.java
            String content = "Proizvod sa brojem " + id + " je obrisan.";
            notificationService.createNotificationForAllUsersByRole("Proizvod obrisan", content, "SELLER");
=======
            String content = "Product with number " + id + " has been deleted.";
          
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/controller/ProductController.java

        }catch (Exception e){
            return ResponseEntity.badRequest().body("{\"message\":\"" + e.getMessage() + "\"}");
        }
        return ResponseEntity.ok().body("{\"message\": \"You have successfully deleted product\"}");

    }
}
