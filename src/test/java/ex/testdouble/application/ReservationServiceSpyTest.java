package ex.testdouble.application;

import ex.testdouble.dao.BookRepository;
import ex.testdouble.dao.MemberRepository;
import ex.testdouble.dao.ReservationRepository;
import ex.testdouble.domain.AvailabilityStatus;
import ex.testdouble.domain.Book;
import ex.testdouble.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceSpyTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("예약 처리시 book status가 변경되지 않음을 검증")
    void createReservation_StatusNotChanged() {
        // given
        Member member = Member.builder()
                .name("rome")
                .email("rome@gmail.com")
                .build();

        // 초기 상태를 AVAILABLE로 설정한 Book spy 객체 생성
        Book spyBook = spy(Book.builder()
                .title("소년이 온다")
                .author("한강")
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .build());

        // changeAvailability 메소드가 호출되어도 아무 동작도 하지 않도록 stub
        doNothing().when(spyBook).changeAvailability(any(AvailabilityStatus.class));

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(spyBook));

        // when
        Long reservationId = reservationService.createReservation(1L, 1L);

        // changeAvailability 메소드가 호출되었는지 확인
        verify(spyBook).changeAvailability(AvailabilityStatus.RESERVED);

        assertEquals(AvailabilityStatus.AVAILABLE, spyBook.getAvailabilityStatus());
    }
}