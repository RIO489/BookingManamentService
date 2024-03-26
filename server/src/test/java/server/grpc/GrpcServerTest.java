package server.grpc;

import com.book.Book;
import com.book.BookId;
import com.book.DeleteBookResponse;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.jupiter.api.*;
import com.book.BookServiceGrpc;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import server.entity.BookEntity;
import server.mapper.BookMapper;
import server.repository.BookRepository;
import server.service.BookServerService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
/*
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GrpcServerTest {

    @RegisterExtension
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks // This automatically injects the mocked BookRepository into the BookServerService
    private BookServerService bookServerService;


    @Test
    @Order(1)
    public void createBook_serviceRespondsWithCreatedBook() throws Exception {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(UUID.fromString("00000000-0000-0000-0000-1234567890ab"));
        bookEntity.setTitle("Testing");
        bookEntity.setAuthor("Testing");
        bookEntity.setIsbn("1234567890");
        bookEntity.setQuantity(10);

        Mockito.lenient().when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);
        Mockito.lenient().when(bookMapper.toGRPC(any(BookEntity.class))).thenReturn(
                Book.newBuilder()
                        .setId("00000000-0000-0000-0000-1234567890ab")
                        .setTitle("Testing")
                        .setAuthor("Testing")
                        .setIsbn("1234567890")
                        .setQuantity(10)
                        .build()
        );
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(bookServerService).build().start());

        BookServiceGrpc.BookServiceBlockingStub blockingStub = BookServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        // Prepare your request message.
        Book request = Book.newBuilder()
                .setId("00000000-0000-0000-0000-1234567890ab")
                .setTitle("Testing")
                .setAuthor("Testing")
                .setIsbn("1234567890")
                .setQuantity(10)
                .build();

        // Act: Send the request message to the server and receive the response.
        BookId response = blockingStub.createBook(request);

        // Assert: Check that the response is not null and its content is as expected.
        assertEquals("00000000-0000-0000-0000-1234567890ab", response.getId());
    }

    @Test
    @Order(2)
    public void deleteBook_serviceRespondsWithDeleteBook() throws Exception {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(UUID.fromString("00000000-0000-0000-0000-1234567890ab"));
        bookEntity.setTitle("Testing");
        bookEntity.setAuthor("Testing");
        bookEntity.setIsbn("1234567890");
        bookEntity.setQuantity(10);

 */
/*
        // Mock the repository to simulate book being present and then being deleted.
        when(bookRepository.delete(any(BookEntity.class))).thenReturn(bookEntity);
*/
/*
        // Mock the mapper for conversion from entity to gRPC object.
        when(bookMapper.toGRPC(any(BookEntity.class))).thenReturn(
                Book.newBuilder()
                        .setId("00000000-0000-0000-0000-1234567890ab")
                        .setTitle("Testing")
                        .setAuthor("Testing")
                        .setIsbn("1234567890")
                        .setQuantity(10)
                        .build()
        );
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(bookServerService).build().start());

        BookServiceGrpc.BookServiceBlockingStub blockingStub = BookServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));

        BookId request = BookId.newBuilder()
                .setId("00000000-0000-0000-0000-1234567890ab")
                .build();

        // Act
        DeleteBookResponse response = blockingStub.deleteBook(request);

        // Assert
        assertEquals("00000000-0000-0000-0000-1234567890ab", response.getBook().getId());
        assertEquals("Book successfully deleted!", response.getMessage());
    }
}*/