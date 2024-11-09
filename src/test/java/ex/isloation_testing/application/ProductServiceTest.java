package ex.isloation_testing.application;

import static org.junit.jupiter.api.Assertions.*;

import ex.isloation_testing.dao.ProductRepository;
import ex.isloation_testing.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        Product product1 = createProduct("Product1", 10);
        Product product2 = createProduct("Product2", 20);
        productRepository.save(product1);
        productRepository.save(product2);
        System.out.println("@BeforeEach executed");
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
        System.out.println("@AfterEach executed \n");
    }


    // 팩토리 메소드: Product 객체 생성
    private Product createProduct(String name, int quantity) {
        return Product.builder()
                .name(name)
                .quantity(quantity)
                .build();
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();

        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).containsExactlyInAnyOrder("Product1", "Product2");
        System.out.println("test1 executed");
    }

    @Test
    public void testCreateProduct() {
        String name = "New Product";
        int quantity = 5;

        Product createdProduct = productService.createProduct(name, quantity);

        assertThat(createdProduct.getId()).isNotNull();
        assertThat(createdProduct.getName()).isEqualTo(name);
        assertThat(createdProduct.getQuantity()).isEqualTo(quantity);

        List<Product> allProducts = productRepository.findAll();

        assertThat(allProducts).hasSize(3);
        System.out.println("test2 executed");
    }
}
