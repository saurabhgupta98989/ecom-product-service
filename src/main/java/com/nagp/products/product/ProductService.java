package com.nagp.products.product;

import com.nagp.products.events.ProductCreatedEvent;
import com.nagp.products.events.ProductUpdatedEvent;
import com.nagp.products.publishers.SnsPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final SnsPublisher publisher;

    // ---------- CREATE ----------
    public Product create(Product product){
        Product saved = repo.save(product);
        publisher.publish("PRODUCT_CREATED",ProductCreatedEvent.builder()
                .id(saved.getId())
                .name(saved.getName())
                .description(saved.getDescription())
                .tags((saved.getTags()))
                .brand(saved.getBrand())
                .category(saved.getCategory())
                .audience(saved.getAudience())
                .colors(saved.getColors())
                .sizes(saved.getSizes())
                .price(saved.getPrice())
                .images(saved.getImages())
                .inventory(saved.getInventory())
                .build());
        return saved;
    }

    // ---------- PATCH ----------
    public Product patch(String id, Map<String,Object> updates){

        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        updates.forEach((key,value)->{
            Field field = ReflectionUtils.findField(Product.class,key);
            if(field!=null){
                field.setAccessible(true);
                ReflectionUtils.setField(field,product,value);
            }
        });

        Product saved = repo.save(product);

//        publisher.publish(ProductUpdatedEvent.builder()
//                .id(id)
//                .updatedFields(updates)
//                .build());

        return saved;
    }

    // ---------- DELETE ----------
    public void delete(String id){

        repo.deleteById(id);

//        publisher.publish(ProductDeletedEvent.builder()
//                .id(id)
//                .build());
    }

    public Product getById(String id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Page<Product> getAll(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).descending()
        );
        return repo.findAll(pageable);
    }
}
