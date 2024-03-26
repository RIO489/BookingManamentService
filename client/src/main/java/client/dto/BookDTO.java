package client.dto;

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

    private String id;
    private String title;
    private String author;
    private String isbn;
    private Integer quantity;

}