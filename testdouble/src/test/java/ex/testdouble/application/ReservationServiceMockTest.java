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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceMockTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("예약이 끝나면 book의 상태로 RESERVED가 기대된다")
    void createReservation_Successful() {
        // given
        Member mockMember = Member.builder()
                .name("rome")
                .email("rome@gmail.com")
                .build();

        Book mockBook = Mockito.spy(Book.builder()
                .title("소년이 온다")
                .author("한강")
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .build());


        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(mockMember));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));
        Mockito.when(mockBook.getAvailabilityStatus()).thenReturn(AvailabilityStatus.AVAILABLE);

        // when
        Long reservationId = reservationService.createReservation(1L, 1L);

        // then
        Mockito.verify(mockBook).changeAvailability(AvailabilityStatus.RESERVED);
    }

    @Test
    @DisplayName("없는 회원 ID로 예약 시 IllegalArgumentException 발생")
    void createReservation_MemberNotFound() {
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(1L, 1L));
    }
}