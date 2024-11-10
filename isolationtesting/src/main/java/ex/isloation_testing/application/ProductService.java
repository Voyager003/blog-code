package ex.isloation_testing.application;

import ex.isloation_testing.dao.ProductRepository;
import ex.isloation_testing.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    @Transactional
    public Product createProduct(String name, int quantity) {
        Product product = Product.builder()
                .name(name)
                .quantity(quantity)
                .build();
        return productRepository.save(product);
    }
}
