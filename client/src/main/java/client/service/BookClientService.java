package client.service;

import client.dto.BookDTO;
import com.book.*;
import com.google.protobuf.Descriptors;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class BookClientService {

    @GrpcClient("grpc-client-service")
    BookServiceGrpc.BookServiceBlockingStub synchronousClient;

    @GrpcClient("grpc-client-service")
    BookServiceGrpc.BookServiceStub asynchronousClient;

    public Map<Descriptors.FieldDescriptor, Object> getBook(UUID bookId) {
        BookId bookRequest = BookId.newBuilder().setId(String.valueOf(bookId)).build();
        Book bookResponse = synchronousClient.getBook(bookRequest);
        return bookResponse.getAllFields();
        /*
        Or simplify it to:
         BookId bookRequest = BookId.newBuilder().setId(String.valueOf(bookId)).build();
        return synchronousClient.getBook(bookRequest).getAllFields();
        */

    }

    public Map<Descriptors.FieldDescriptor, Object> createBook(BookDTO dto) {
        Book bookRequest = Book.newBuilder()
                .setId((dto.getId().toString()))
                .setAuthor(dto.getAuthor())
                .setIsbn(dto.getIsbn())
                .setTitle(dto.getTitle())
                .setQuantity(dto.getQuantity())
                .build();
        BookId bookResponse = synchronousClient.createBook(bookRequest);
        return bookResponse.getAllFields();
    }

    public Map<Descriptors.FieldDescriptor, Object> deleteBook(UUID bookId) {
        BookId bookRequest = BookId.newBuilder().setId(String.valueOf(bookId)).build();
        DeleteBookResponse bookResponse = synchronousClient.deleteBook(bookRequest);
        return bookResponse.getAllFields();
    }

    public Map<Descriptors.FieldDescriptor, Object> updateBook(BookDTO dto) {
        Book bookRequest = Book.newBuilder()
                .setId((dto.getId().toString()))
                .setAuthor(dto.getAuthor())
                .setIsbn(dto.getIsbn())
                .setTitle(dto.getTitle())
                .setQuantity(dto.getQuantity())
                .build();
        Book bookResponse = synchronousClient.updateBook(bookRequest);
        return bookResponse.getAllFields();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getAllBooks() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ListBooksRequest listBooksRequest = ListBooksRequest.newBuilder().build();
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>() {
        };
        asynchronousClient.getAllBooks(listBooksRequest, new StreamObserver<Book>() {
            @Override
            public void onNext(Book value) {
                response.add(value.getAllFields());
            }

            @Override
            public void onError(Throwable t) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthorName(String author) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AuthorName authorRequest = AuthorName.newBuilder().setName(author).build();
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>() {
        };
        asynchronousClient.getAllBooksByAuthor(authorRequest, new StreamObserver<Book>() {
            @Override
            public void onNext(Book value) {
                response.add(value.getAllFields());
            }

            @Override
            public void onError(Throwable t) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }
}
