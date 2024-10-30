package ex.testdouble.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime reservationDate;
    private LocalDateTime returnDate;

    @Builder
    public Reservation(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.reservationDate = LocalDateTime.now();
        this.returnDate = LocalDateTime.now().plusDays(14);
    }
}
