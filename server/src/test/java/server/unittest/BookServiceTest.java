package server.unittest;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import com.book.Book;
import com.book.DeleteBookResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.entity.BookEntity;
import server.mapper.BookMapper;
import server.repository.BookRepository;
import server.service.BookServerService;
import com.book.BookId;
import io.grpc.stub.StreamObserver;
import server.exception.ServerException;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServerService bookService;
    @Mock
    private StreamObserver<BookId> responseObserverBookId;
    @Mock
    private StreamObserver<Book> responseObserverBook;
    @Mock
    private StreamObserver<DeleteBookResponse> responseObserverDeleteBookResponse;

    @Test
    @Order(1)
    public void createBookSuccessfully() {
        // Arrange
        Book request = Book.newBuilder()
                .setId("5844ae0c-6d0b-49d3-8f50-70582c31a7a2")
                .setTitle("The Great Book")
                .setAuthor("Famous Author")
                .build();
        // UUID newBookId = UUID.randomUUID(); // Generate a new UUID for the book entity
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(UUID.fromString("5844ae0c-6d0b-49d3-8f50-70582c31a7a2")); // Set the ID on the book entity

        when(bookMapper.toEntity(request)).thenReturn(bookEntity);
        when(bookRepository.findByTitle(request.getTitle())).thenReturn(Optional.empty());
        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        // Act
        bookService.createBook(request, responseObserverBookId);

        // Assert
        verify(responseObserverBookId).onNext(any(BookId.class));
        verify(responseObserverBookId).onCompleted();
        verify(responseObserverBookId, never()).onError(any(Throwable.class));
    }

    @Test
    @Order(2)
    public void getBookSuccessfully() throws ServerException {
        // Arrange
        UUID uuid = UUID.fromString("5844ae0c-6d0b-49d3-8f50-70582c31a7a2");
        BookEntity bookEntity = new BookEntity(uuid, "The Great Book", "Famous Author", "isbn", 10);
        when(bookRepository.findById(uuid)).thenReturn(Optional.of(bookEntity));
        BookId request = BookId.newBuilder().setId(uuid.toString()).build();

        // Act
        bookService.getBook(request, responseObserverBook);

        // Assert
        verify(responseObserverBook).onNext(any(Book.class)); // BookId.class should match the mocked type
        verify(responseObserverBook).onCompleted();
        verify(responseObserverBook, never()).onError(any(Throwable.class));
    }

    @Test
    void deleteBookSuccessfully() {
        // Arrange
        UUID bookUuid = UUID.fromString("5844ae0c-6d0b-49d3-8f50-70582c31a7a2");
        BookId request = BookId.newBuilder().setId(bookUuid.toString()).build();
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookUuid);

        Book grpcBook = Book.newBuilder()
                .setId(bookUuid.toString())
                .build();


        when(bookRepository.findById(bookUuid)).thenReturn(Optional.of(bookEntity));
        doNothing().when(bookRepository).delete(bookEntity);
        when(bookMapper.toGRPC(bookEntity)).thenReturn(grpcBook);


        // Act
        bookService.deleteBook(request, responseObserverDeleteBookResponse);

        // Assert
        verify(bookRepository).findById(bookUuid);
        verify(bookRepository).delete(bookEntity);
        verify(responseObserverDeleteBookResponse).onNext(any(DeleteBookResponse.class));
        verify(responseObserverDeleteBookResponse).onCompleted();
        verify(responseObserverDeleteBookResponse, never()).onError(any(Throwable.class));
    }
}