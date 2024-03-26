package server.mapper;

import com.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import server.DTO.BookDTO;
import server.entity.BookEntity;

import java.util.UUID;

//@Component
@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", expression = "java(entity.getId().toString())")
    @Mapping(target = "mergeFrom", ignore = true)
    @Mapping(target = "clearField", ignore = true)
    @Mapping(target = "clearOneof", ignore = true)
    @Mapping(target = "idBytes", ignore = true)
    @Mapping(target = "titleBytes", ignore = true)
    @Mapping(target = "authorBytes", ignore = true)
    @Mapping(target = "isbnBytes", ignore = true)
    @Mapping(target = "unknownFields", ignore = true)
    @Mapping(target = "mergeUnknownFields", ignore = true)
    @Mapping(target = "allFields", ignore = true)
    Book toGRPC(BookEntity entity);

    // Assuming your Book gRPC object has a builder pattern with the build method.
    // Custom method to generate UUID for BookEntity
    default UUID map(String id) {
        return UUID.fromString(id);
    }

    @Mapping(target = "id", expression = "java(map(grpc.getId()))")
    BookEntity toEntity(Book grpc);

    BookDTO toDTO(BookEntity entity);

    // Assuming the DTO has a constructor matching this signature or setters for each field.
    BookEntity toEntity(BookDTO dto);

}
