package com.example.app_securety.controller;

import com.example.app_securety.entity.Product;
import com.example.app_securety.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //MANAGER,DIRECTOR
    @PreAuthorize(value = "hasAuthority('READ_ALL_PRODUCT')")
    @GetMapping
    public HttpEntity<?> getProduct(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    //DIRECTOR
    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody Product product){
        Product save = productRepository.save(product);
        return ResponseEntity.ok(save);
    }

    //DIRECTOR
    @PreAuthorize(value = "hasAuthority('EDIT_PRODUCT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable Integer id,@RequestBody Product product){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product product1 = optionalProduct.get();
            product1.setName(product.getName());
            Product save = productRepository.save(product1);
        return ResponseEntity.ok(save);

        }
        return ResponseEntity.notFound().build();
    }

    //DIRECTOR
    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping({"/{id}"})
    public HttpEntity<?> deleteProduct(@PathVariable Integer id){
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //USER,MANAGER,DIRECTOR
    @PreAuthorize(value = "hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/{id}")
    public HttpEntity<?> getProductById(@PathVariable Integer id){
        Optional<Product> productOptional = productRepository.findById(id);
        return ResponseEntity.status(productOptional.isPresent()?200:404).body(productOptional.orElse(null));
    }



}
