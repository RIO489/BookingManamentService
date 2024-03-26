package server.service;


import com.book.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.BookEntity;
import server.exception.ServerException;
import server.mapper.BookMapper;
import server.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@GrpcService
public class BookServerService extends BookServiceGrpc.BookServiceImplBase {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServerService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public void getBook(BookId request, StreamObserver<Book> responseObserver) {
        try {
            UUID bookUuid = UUID.fromString(request.getId()); // Assuming the ID sent through gRPC is a valid UUID
            //  Optional<BookEntity> bookOptional = bookRepository.findById(bookUuid); // Method from your repository
            BookEntity book = bookRepository.findById(bookUuid).orElseThrow(() -> new ServerException("Can`t find Book by this ID: " + bookUuid));
            // Convert your entity to gRPC book object
            com.book.Book grpcBook = com.book.Book.newBuilder()
                    .setId(book.getId().toString())
                    .setAuthor(book.getAuthor())
                    .setIsbn(book.getIsbn())
                    .setTitle(book.getTitle())
                    .setQuantity(book.getQuantity())
                    .build();

            //    responseObserver.onError(new StatusException(Status.NOT_FOUND));
            responseObserver.onNext(grpcBook);
            responseObserver.onCompleted();
        } catch (ServerException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMassage())
                    .asException());
        }
    }

    @Override
    public void deleteBook(BookId request, StreamObserver<DeleteBookResponse> responseObserver) {
        try {
            UUID bookUuid = UUID.fromString(request.getId());
            BookEntity book = bookRepository.findById(bookUuid).orElseThrow(() -> new ServerException("Can`t find Book by this ID: " + bookUuid));
            bookRepository.delete(book);

            Book grpcBook = bookMapper.toGRPC(book);
            DeleteBookResponse deleteBookResponse = DeleteBookResponse.newBuilder()
                    .setMessage("Book successful deleted!")
                    .setBook(grpcBook)
                    .build();

            responseObserver.onNext(deleteBookResponse);
            responseObserver.onCompleted();
        } catch (ServerException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMassage())
                    .asException());
        }
    }

    @Override
    public void updateBook(Book request, StreamObserver<Book> responseObserver) {
        try {
            UUID bookUuid = UUID.fromString(request.getId());

            // Find the book by ID and update it, throw an exception if not found
            BookEntity book = bookRepository.findById(bookUuid)
                    .orElseThrow(() -> new ServerException("Can't find Book by this ID: " + request.getId()));

            // Perform the update
            book.setAuthor(request.getAuthor());
            book.setTitle(request.getTitle());
            book.setIsbn(request.getIsbn());
            book.setQuantity(request.getQuantity());

            // Save the updated entity to the database
            bookRepository.save(book);

            // Convert to gRPC object and respond
            Book respond = bookMapper.toGRPC(book);
            responseObserver.onNext(respond);
            responseObserver.onCompleted();

            /* Or simply do this instead,but without checking in db if book with this UUID existing, but it can be worse method to use so not recommended

            BookEntity entityToUpdate = bookMapper.toEntity(request);
            bookRepository.save(entityToUpdate); // Save the updated entity to the database
             BookId updatedBookId = BookId.newBuilder().setId(entityToUpdate.getId().toString()).build();
            */

            responseObserver.onNext(respond);
            responseObserver.onCompleted();
        } catch (ServerException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMassage())
                    .asException());
        }
    }

    @Override
    public void getAllBooks(ListBooksRequest request, StreamObserver<Book> responseObserver) {
        List<BookEntity> bookEntities = bookRepository.findAll(); // Retrieve all books from the database
        List<Book> books = bookEntities.stream().map(bookMapper::toGRPC).toList();
        books.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllBooksByAuthor(AuthorName request, StreamObserver<Book> responseObserver) {
        List<BookEntity> bookEntities = bookRepository.findByAuthor(request.getName());// Retrieve all books with one author from the database
        List<Book> books = bookEntities.stream().map(bookMapper::toGRPC).toList();
        books.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void createBook(Book request, StreamObserver<BookId> responseObserver) {
        //First we need to check if this book already existing in db. We will do it by comparing title and author. If we found one - throw new ServerException
        Optional<BookEntity> existingBook = bookRepository.findByTitle(request.getTitle());

        //Double checking if both parameters such Title for book and author of book are already in the db
        // Because there is no way Author could have 2 same book title with different ideas.....right???

        if (existingBook.isPresent() && existingBook.get().getAuthor().equals(request.getAuthor())) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription("Book with this title " + existingBook.get().getTitle() + " already existing")
                            .withCause(new ServerException("This book is already in our library"))
                            .asRuntimeException());
        }
        //If nothing was found,proceed to saving book into db
        // Convert the gRPC Book object directly to BookEntity and save it to db
        BookEntity newBookEntity = bookMapper.toEntity(request); // Using mapper to convert Book into BookEntity
        BookEntity savedEntity = bookRepository.save(newBookEntity); // Save the new entity to the database
        BookId newBookId = BookId.newBuilder().setId(savedEntity.getId().toString()).build();
        responseObserver.onNext(newBookId);
        responseObserver.onCompleted();

    }
}