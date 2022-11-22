package com.ReactiveMongoCrud.service;


import com.ReactiveMongoCrud.dto.ProductDto;
import com.ReactiveMongoCrud.entity.Product;
import com.ReactiveMongoCrud.repository.ProductRepository;
import com.ReactiveMongoCrud.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // GET ALL PRODUCTS
    public Flux<ProductDto> getProducts(){

        return repository.findAll().map(AppUtils::entityToDto);

    }

    // GET PRODUCT BY ID
    public Mono<ProductDto> getProduct(String id){
        return repository.findById(id).map(AppUtils::entityToDto);
    }

    // GET PRODUCTS IN PRICE RANGE
    public Flux<ProductDto> getProductsInPriceRange(double min, double max){

        return repository.findByPriceBetween(Range.closed(min,max));

    }

    // GET PRODUCTS IN QTY RANGE
    public Flux<ProductDto> getProductsInQtyRange(int min, int max){

        return repository.findByQtyBetween(Range.closed(min,max));

    }




    // SAVE PRODUCT
    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDto);

    }

    // UPDATE PRODUCT
    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id){

        return repository.findById(id)
                .flatMap(p -> productDtoMono.map(AppUtils::dtoToEntity)
                                            .doOnNext(e -> e.setId(id)))
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);
    }

    // DELETE Product
    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);
    }




}
