package client.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientException extends RuntimeException {
    private int code;
    private String massage;

    public ClientException(String massage) {
        this.code = 400;
        this.massage = massage;
    }

}
