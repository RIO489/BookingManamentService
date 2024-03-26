package server.testcontainer;

import com.book.Book;
import com.book.BookId;
import com.book.DeleteBookResponse;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import server.service.BookServerService;

import java.util.concurrent.*;
/*


@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GrpcServiceTestcontainerTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("bookdb")
            .withUsername("postgres")
            .withPassword("Riorio489");

    @Autowired
    private BookServerService bookService;


    @Test
    @Order(1)
    public void testCreateBook() throws ExecutionException, InterruptedException, TimeoutException {
        Book request = Book.newBuilder()
                .setId("2506d2c5-5b4f-40f5-929d-7df29d2c63c5")
                .setTitle("Testing")
                .setAuthor("Testing")
                .setIsbn("1234567890")
                .setQuantity(10)
                .build();

        CompletableFuture<BookId> completableFuture = new CompletableFuture<>();

        bookService.createBook(request, new StreamObserver<BookId>() {
            @Override
            public void onNext(BookId value) {
                completableFuture.complete(value); // Complete the future with the response
            }

            @Override
            public void onError(Throwable t) {
                completableFuture.completeExceptionally(t); // Complete the future exceptionally in case of error
            }

            @Override
            public void onCompleted() {
                // Completion of the RPC call, handle if needed
            }
        });

        // Wait for the future to complete and assert
        BookId response = completableFuture.get(5, TimeUnit.SECONDS); // Wait for the response up to 5 seconds
        Assertions.assertNotNull(response.getId(), "The response ID should not be null");
    }

    @Test
    @Order(2)
    public void testUpdateBook() throws ExecutionException, InterruptedException, TimeoutException {
        // Act: Create a request for updating the book
        Book updateRequest = Book.newBuilder()
                .setId("2506d2c5-5b4f-40f5-929d-7df29d2c63c5")
                .setTitle("Updated Test Book")
                .setAuthor("Updated Test Author")
                .setIsbn("0987654321")
                .setQuantity(15)
                .build();

        // Prepare a CompletableFuture to capture the asynchronous response
        CompletableFuture<Book> responseFuture = new CompletableFuture<>();

        // Act: Call the updateBook method
        bookService.updateBook(updateRequest, new StreamObserver<Book>() {
            @Override
            public void onNext(Book value) {
                responseFuture.complete(value); // Capture the response
            }

            @Override
            public void onError(Throwable t) {
                responseFuture.completeExceptionally(t); // Capture the error
            }

            @Override
            public void onCompleted() {
                // Signal completion if needed
            }
        });

        // Assert: Check that the book has been updated
        Book updatedBook = responseFuture.get(5, TimeUnit.SECONDS); // Synchronously wait for the result
        Assertions.assertEquals("Updated Test Book", updatedBook.getTitle());
        Assertions.assertEquals("Updated Test Author", updatedBook.getAuthor());
        Assertions.assertEquals("0987654321", updatedBook.getIsbn());
        Assertions.assertEquals(15, updatedBook.getQuantity());
    }

    @Test
    @Order(3)
    public void testDeleteBook() throws ExecutionException, InterruptedException, TimeoutException {
        // Act: Create a request for updating the book
        BookId deleteRequest = BookId.newBuilder()
                .setId("2506d2c5-5b4f-40f5-929d-7df29d2c63c5") // Use the UUID generated in testCreateBook()
                .build();

        // Prepare a CompletableFuture to capture the asynchronous response
        CompletableFuture<DeleteBookResponse> responseFuture = new CompletableFuture<>();


        // Use CountDownLatch to await the asynchronous callback
        CountDownLatch latch = new CountDownLatch(1);

        // Act: Call the deleteBook method
        bookService.deleteBook(deleteRequest, new StreamObserver<DeleteBookResponse>() {

            @Override
            public void onNext(DeleteBookResponse value) {
                responseFuture.complete(value); // Capture the response
            }

            @Override
            public void onError(Throwable t) {
                responseFuture.completeExceptionally(t); // Capture the error
            }

            @Override
            public void onCompleted() {

            }
        });

        // Assert: Check that the book has been updated
        DeleteBookResponse deleteBookResponse = responseFuture.get(5, TimeUnit.SECONDS); // Synchronously wait for the result
        Assertions.assertEquals("Updated Test Book", deleteBookResponse.getBook().getTitle());
        Assertions.assertEquals("Book successful deleted!", deleteBookResponse.getMessage());
    }
}
*/