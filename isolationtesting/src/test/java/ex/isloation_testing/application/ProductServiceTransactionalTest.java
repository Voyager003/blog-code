package ex.isloation_testing.application;

import ex.isloation_testing.dao.ProductRepository;
import ex.isloation_testing.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductServiceTransactionalTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product createProduct(String name, int quantity) {
        return Product.builder()
                .name(name)
                .quantity(quantity)
                .build();
    }

    @Test
    public void testCreateProduct() {
        Product product1 = createProduct("Product1", 10);
        Product product2 = createProduct("Product2", 20);
        productRepository.save(product1);
        productRepository.save(product2);

        String name = "New Product";
        int quantity = 5;

        Product createdProduct = productService.createProduct(name, quantity);
        List<Product> allProducts = productRepository.findAll();

        assertThat(createdProduct.getQuantity()).isEqualTo(quantity);
        assertThat(allProducts).hasSize(3);
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = createProduct("Product1", 10);
        Product product2 = createProduct("Product2", 20);
        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products = productService.getAllProducts();

        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).containsExactlyInAnyOrder("Product1", "Product2");
    }
}
