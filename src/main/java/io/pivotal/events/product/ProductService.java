package io.pivotal.events.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductRecord getProductById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow();
        return productMapper.productEntityToProductRecord(productEntity);
    }

    public void saveProduct(ProductRecord productRecord) {
        ProductEntity productEntity = productMapper.productRecordToProductEntity(productRecord);
        productRepository.save(productEntity);
    }
}
