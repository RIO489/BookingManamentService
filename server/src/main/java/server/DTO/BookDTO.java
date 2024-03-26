package server.DTO;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookDTO {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private Integer quantity;

}