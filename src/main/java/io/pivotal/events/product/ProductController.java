package io.pivotal.events.product;

import io.pivotal.events.product.sse.ProductSseEmitter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductSseEmitter productSseEmitter;

    @GetMapping("/api/sales")
    public List<ProductRecord> getProductsOnSale() {
        return productService.getProductsOnSale();
    }

    @PostMapping("/api/products")
    public ProductRecord getProductsOnSale(@RequestBody NewProductRecord productRecord) {
        return productService.createProduct(productRecord);
    }

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        // Add the emitter to a list of subscribers or handle it in another way
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        productSseEmitter.addEmitter(sseEmitter);
        return sseEmitter;
    }
}
