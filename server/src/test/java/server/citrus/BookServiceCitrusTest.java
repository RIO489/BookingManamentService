package server.citrus;
/*
import org.citrusframework.annotations.CitrusTest;
import org.citrusframework.message.MessageType;
import org.citrusframework.testng.TestNGCitrusSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {CitrusTestConfig.class})
public class BookServiceCitrusTest extends TestNGCitrusSupport {

    @Autowired
    @Qualifier("bookGrpcClient")
    private GrpcClient bookGrpcClient;

    @Test
    @CitrusTest
    public void testCreateBook() {
        run(grpc().client(bookGrpcClient)
                .send()
                .message()
                .type(MessageType.PLAINTEXT)
                .body("{"
                        + "\"id\": \"\","
                        + "\"title\": \"Citrus in Action\","
                        + "\"author\": \"Consol\","
                        + "\"isbn\": \"1234567890\","
                        + "\"quantity\": \"1\""
                        + "}"));

        run(grpc().client(bookGrpcClient)
                .receive()
                .response()
                .message()
                .type(MessageType.PLAINTEXT)
                .body("{"
                        + "\"success\": true,"
                        + "\"message\": \"Book successfully created\""
                        + "}"));
    }
}*/