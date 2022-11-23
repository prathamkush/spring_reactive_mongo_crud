package com.ReactiveMongoCrud;

import com.ReactiveMongoCrud.controller.ProductController;
import com.ReactiveMongoCrud.dto.ProductDto;
import com.ReactiveMongoCrud.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class ReactiveMongoCrudApplicationTests {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService service;



    @Test
    public void saveProductTest(){

        ProductDto productDto = new ProductDto("102", "Laptop", "PC & Accessories", 1, 100000);
        // request body
        Mono<ProductDto> productDtoMono = Mono.just(productDto);

        when(service.saveProduct(productDtoMono)).thenReturn(productDtoMono);

        EntityExchangeResult<ProductDto> result = webTestClient.post().uri("/products/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(productDtoMono), ProductDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class)
                .returnResult();

        System.out.println(result);
    }

    // SEEING THE actual response got
    @Test
    public void getProductsTests(){
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add( new ProductDto("102", "Laptop","PC & Accessories" ,1, 100000));
        productDtoList.add( new ProductDto("103", "Battery", "PC & Accessories", 4, 1000));

        Flux<ProductDto> productDtoFlux = Flux.just(productDtoList.get(0), productDtoList.get(1));

        when(service.getProducts()).thenReturn(productDtoFlux);

        FluxExchangeResult<ProductDto> responseBody = webTestClient.get().uri("/products/")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class);

        System.out.println(responseBody);



    }

    //  verifying actual response body
    @Test
    public void getProductsTests1(){
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add( new ProductDto("102", "Laptop","PC & Accessories" ,1, 100000));
        productDtoList.add( new ProductDto("103", "Battery", "PC & Accessories", 4, 1000));


        Flux<ProductDto> productDtoFlux = Flux.just(productDtoList.get(0), productDtoList.get(1));

        when(service.getProducts()).thenReturn(productDtoFlux);

        Flux<ProductDto> responseBody = webTestClient.get().uri("/products/")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                        .expectSubscription()
                                .expectNext(productDtoList.get(0))
                                        .expectNext(productDtoList.get(1))
                                                .verifyComplete();
        System.out.println(responseBody);

    }

    @Test
    public void getProductByIdTest(){
        ProductDto productDto = new ProductDto("102", "Laptop", "PC & Accessories", 1, 100000);
        Mono<ProductDto> productDtoMono = Mono.just(productDto);

        when(service.getProduct(any())).thenReturn(productDtoMono);

        Flux<ProductDto> responseBody = webTestClient.get().uri("/products/102")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();


        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getName().equals("Laptop"))
                .verifyComplete();

    }
    @Test
    public void getProductByIdTest1(){
        ProductDto productDto = new ProductDto("102", "Laptop", "PC & Accessories", 1, 100000);
        Mono<ProductDto> productDtoMono = Mono.just(productDto);

        when(service.getProduct(any())).thenReturn(productDtoMono);

        EntityExchangeResult<ProductDto> result = webTestClient.get().uri("/products/102")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class)
//                .value(productDto1 -> productDto1.getName(), equalTo("Laptop"))
                .value(productDto1 -> productDto1.equals(productDto))
                .returnResult();

        System.out.println(result);

    }

    @Test
    public void updateProductTest(){

        ProductDto productDto = new ProductDto("102", "Laptop", "PC & Accessories", 1, 100000);
        Mono<ProductDto> productDtoMono = Mono.just(productDto);

        when(service.updateProduct(productDtoMono,"102")).thenReturn(productDtoMono);

        webTestClient.put().uri("/products/update/102")
                .body(Mono.just(productDtoMono), ProductDto.class)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void deleteProductTest(){
        given(service.deleteProduct(any())).willReturn(Mono.empty());

        webTestClient.delete().uri("/products/delete/102")
                .exchange()
                .expectStatus().isOk();


    }













}
