package com.ReactiveMongoCrud.controller;


import com.ReactiveMongoCrud.dto.ProductDto;
import com.ReactiveMongoCrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
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

    // GET PRODUCT IN RANGE
    @RequestMapping(value = "/product-in-range", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductDto> getProductInRange(@RequestParam("min") double min, @RequestParam("max") double max){
        return service.getProductsInPriceRange(min,max);
    }

    // SAVE A PRODUCT
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono){
        return service.saveProduct(productDtoMono);
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
