package com.ReactiveMongoCrud.controller;


import com.ReactiveMongoCrud.dto.ProductDto;
import com.ReactiveMongoCrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService service;

    // GET ALL PRODUCTS
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductDto> getProducts(){
        return service.getProducts();
    }

    // GET PRODUCT BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductDto> getProductById(@PathVariable String id){
        return service.getProduct(id);
    }


    // GET PRODUCT BY NAME
    @RequestMapping(value = "/getProductByName/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductDto> getProductByName(@PathVariable("name") String name)
    {
        return service.getProductByName(name);
    }


    // GET PRODUCT BY CATEGORY (LIKE)
    @RequestMapping(value = "/getProductByCategory/{category}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductDto> getProductByCategory(@PathVariable("category") String category) {
        return service.getProductByCategory(category);
    }






    // GET PRODUCT IN RANGE PRICE
    @RequestMapping(value = "/product-in-range/{min}/{max}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductDto> getProductInRange(@PathVariable("min") double min,@PathVariable("max") double max){
        return service.getProductsInPriceRange(min,max);
    }

    // GET PRODUCT IN RANGE QTY
    @RequestMapping(value = "/product-in-range-qty/{min}/{max}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductDto> getProductInRangeQty(@PathVariable("min") int min,@PathVariable("max") int max){
        return service.getProductsInQtyRange(min,max);
    }

    // SAVE A PRODUCT
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono){
        return service.saveProduct(productDtoMono);
    }

    // SAVE ALL PRODUCTS
    @RequestMapping(value = "/saveAll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductDto> saveAllProduct(@RequestBody Flux<ProductDto> productDto)
    {
        return service.saveAll(productDto);
    }


    // UPDATE PRODUCT BY ID
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable("id") String id){
        return service.updateProduct(productDtoMono, id);
    }

    // DELETE PRODUCT BY ID
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> deleteProduct(@PathVariable String id){
        return service.deleteProduct(id);
    }

}
