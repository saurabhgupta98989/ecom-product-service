package com.nagp.products.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public Product create(@RequestBody Product product){
        return service.create(product);
    }

    @PatchMapping("/{id}")
    public Product patch(
            @PathVariable String id,
            @RequestBody Map<String,Object> updates){
        return service.patch(id,updates);
    }


    @GetMapping("/{id}")
    public Product getById(@PathVariable String id){
        return service.getById(id);
    }

    @GetMapping
    public Page<Product> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ){
        return service.getAll(page,size,sortBy);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }
}
