package server.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ServerException extends RuntimeException{

    private int code;
    private String massage;

    public ServerException(String massage) {
        this.code = 400;
        this.massage = massage;
    }

}
