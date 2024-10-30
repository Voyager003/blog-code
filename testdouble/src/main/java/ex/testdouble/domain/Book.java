package ex.testdouble.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;

    @OneToMany(mappedBy = "book")
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    public Book(String title, String author, AvailabilityStatus availabilityStatus) {
        this.title = title;
        this.author = author;
        this.availabilityStatus = AvailabilityStatus.AVAILABLE;
    }

    public void changeAvailability(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
