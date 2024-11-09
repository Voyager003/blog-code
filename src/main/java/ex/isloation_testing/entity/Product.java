package ex.isloation_testing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int quantity;

    @Builder
    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
