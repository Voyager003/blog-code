package ex.isloation_testing.application;

import ex.isloation_testing.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductServiceSqlTest {

    @Autowired
    private ProductService productService;

    @Test
    @Sql(value = "/product/ddl.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/product/truncate.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("전체 상품 목록을 조회한다")
    void getAllProducts() {
        List<Product> products = productService.getAllProducts();

        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("테스트 상품1");
        assertThat(products.get(0).getQuantity()).isEqualTo(10);
        assertThat(products.get(1).getName()).isEqualTo("테스트 상품2");
        assertThat(products.get(1).getQuantity()).isEqualTo(20);
    }

    @Test
    @Sql(value = "/product/ddl.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/product/truncate.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("ID로 특정 상품을 조회한다")
    void getProductById() {
        // when
        Product product = productService.getProductById(1L);

        // then
        assertThat(product.getName()).isEqualTo("테스트 상품1");
        assertThat(product.getQuantity()).isEqualTo(10);
    }

    @Test
    @Sql(value = "/product/ddl.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/product/truncate.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("존재하지 않는 상품 ID로 조회시 예외가 발생한다")
    void getProductById_NotFound() {
        assertThatThrownBy(() -> productService.getProductById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product not found with id: 999");
    }
}
