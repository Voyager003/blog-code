package ex.testdouble.application;

import ex.testdouble.dao.BookRepository;
import ex.testdouble.dao.MemberRepository;
import ex.testdouble.dao.ReservationRepository;
import ex.testdouble.domain.AvailabilityStatus;
import ex.testdouble.domain.Book;
import ex.testdouble.domain.Member;
import ex.testdouble.domain.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("도서 대여 후 id검증")
    @Transactional
    void createReservationTest() {
        // given
        Member member = Member.builder()
                .name("rome")
                .email("rome@gmail.com")
                .build();
        memberRepository.save(member);

        Book book = Book.builder()
                .title("Book")
                .author("Author")
                .availabilityStatus(AvailabilityStatus.RESERVED)
                .build();
        bookRepository.save(book);

        // when
        Long reservationId = reservationService.createReservation(member.getId(), book.getId());

        // then
        Reservation foundReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AssertionError("Reservation not found"));

        assertAll(
                () -> assertNotNull(foundReservation, "Reservation should not be null"),
                () -> assertEquals(member.getId(), foundReservation.getMember().getId(), "Member ID should match"),
                () -> assertEquals(book.getId(), foundReservation.getBook().getId(), "Book ID should match"),
                () -> assertEquals(AvailabilityStatus.RESERVED, foundReservation.getBook().getAvailabilityStatus(), "Book should be in RESERVED status")
        );

    }
}