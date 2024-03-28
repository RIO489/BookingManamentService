package client.runner;

import client.dto.BookDTO;
import client.exception.ClientException;
import client.service.BookClientService;
import com.google.protobuf.Descriptors;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ClientRunner implements ApplicationRunner {

    private final BookClientService bookClientService;

    @Autowired
    public ClientRunner(BookClientService bookClientService) {
        this.bookClientService = bookClientService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       // getBook("123e4567-e89b-12d3-a456-426614174000");//First book in db

        createBook();
        //Now trying to retrieve every book
        System.out.println("\n Trying to retrieve all books \n");

        getAllBooks();
        //Attempt to create a book in db
        System.out.println("\n Attempt to create a book \n");
    }

    public void getBook(String bookId) throws ClientException {
        UUID uuid = UUID.fromString(bookId);
        //Making call to server to receive a book with UUID above
        Map<Descriptors.FieldDescriptor, Object> book;

        //Checking if book is not Empty or null and if no StatusRuntimeException was thrown
        try {
            book = bookClientService.getBook(uuid);
            if (book == null || book.isEmpty()) {
                throw new ClientException("The book information is unavailable.");
            }
        } catch (StatusRuntimeException e) {
            System.err.println("gRPC service error: " + e.getStatus().getDescription());
            throw new ClientException("Service unavailable: " + e.getStatus().getCode());
        } catch (ClientException e) {
            System.err.println("Client error: " + e.getMessage());
            throw e; // Rethrow the exception or perform additional error handling
        }
        //If book retrieved successful,print the result
        System.out.println("Retrieved book: " + book);
    }

    public void getAllBooks() throws ClientException, InterruptedException {

        List<Map<Descriptors.FieldDescriptor, Object>> books = bookClientService.getAllBooks();
        try {
            //Checking if list of books is empty ,throw custom ClientException. If not,print all books in variable named "books"
            if (!books.isEmpty()) {
                books.forEach(System.out::println);
            } else
                throw new ClientException("The books list is empty or unavailable.");
        } catch (StatusRuntimeException e) {
            System.err.println("gRPC service error: " + e.getStatus().getDescription());
            throw new ClientException("Service unavailable: " + e.getStatus().getCode());
        } catch (ClientException e) {
            System.err.println("Client error: " + e.getMessage());
            throw e;
        }
    }

    public void createBook() throws ClientException {
        BookDTO dto = new BookDTO(UUID.randomUUID().toString(), "Arsen", "Genius", "123", 5);

        Map<Descriptors.FieldDescriptor, Object> book ;

        try {
            book = bookClientService.createBook(dto);
            if (book == null || book.isEmpty()) {
                throw new ClientException("The book information is unavailable.");
            }
        //If book retrieved successful,print the result
        System.out.println("Created book: " + book);
        } catch (StatusRuntimeException e) {
            System.err.println("gRPC service error: " + e.getStatus().getDescription());
        } catch (ClientException e) {
            System.err.println("Client error: " + e.getMessage());
            throw e; // Rethrow the exception or perform additional error handling
        }
    }
}

