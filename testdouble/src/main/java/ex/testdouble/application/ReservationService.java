package ex.testdouble.application;

import ex.testdouble.dao.BookRepository;
import ex.testdouble.dao.MemberRepository;
import ex.testdouble.dao.ReservationRepository;
import ex.testdouble.domain.AvailabilityStatus;
import ex.testdouble.domain.Book;
import ex.testdouble.domain.Member;
import ex.testdouble.domain.Reservation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Long createReservation(Long memberId, Long bookId) {
        // 1. 사용자 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 2. 책 조회
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // 3. 책 대여 가능 여부 확인
        if (book.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available");
        }

        // 4. 예약 정보 생성
        Reservation reservation = Reservation.builder()
                .member(member)
                .book(book)
                .build();

        // 5. 책 상태 변경
        book.changeAvailability(AvailabilityStatus.RESERVED);

        // 6. 예약 정보 저장
        reservationRepository.save(reservation);

        return reservation.getId();
    }
}
