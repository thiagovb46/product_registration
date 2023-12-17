package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduto(@RequestBody @Valid ProductRecordDto productRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createOne(productRecordDto));
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAll(){
        List<ProductModel> products = productService.getAll();
        if(products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductModel> getOne(@PathVariable(value = "id") UUID id){
        var product = productService.getOne(id);
        if(product == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(product);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteOne(@PathVariable(value = "id") UUID id){
        boolean deleteResult = productService.deleteOne(id);
        if(deleteResult == false)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductModel> updateOne(@PathVariable (value = "id") UUID id,
                                                  @Valid @RequestBody ProductRecordDto productDto){
        var productModel = productService.updateOne(id, productDto);
        if(productModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(productModel);
    }
}
