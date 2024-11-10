package ex.isloation_testing.dao;

import ex.isloation_testing.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

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

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(2);
        assertThat(allProducts).extracting(Product::getName).contains("Product1", "Product2");
    }

    @Test
    public void testFindProductById() {
        Product product = createProduct("Product1", 10);
        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Product1");
        assertThat(foundProduct.get().getQuantity()).isEqualTo(10);
    }

    @Test
    public void testDeleteProduct() {
        Product product = createProduct("Product1", 10);
        productRepository.save(product);

        productRepository.deleteById(product.getId());

        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertThat(foundProduct).isNotPresent();
    }
}