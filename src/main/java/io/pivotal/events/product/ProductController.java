package io.pivotal.events.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/api/sales")
    public List<ProductRecord> getProductsOnSale() {
        return productService.getProductsOnSale();
    }

    @PostMapping("/api/products")
    public ProductRecord getProductsOnSale(@RequestBody NewProductRecord productRecord) {
        return productService.createProduct(productRecord);
    }
}
