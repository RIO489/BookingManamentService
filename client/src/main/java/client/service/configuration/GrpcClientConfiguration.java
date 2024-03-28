package client.service.configuration;

import com.book.BookServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GrpcClientConfiguration {

    @Bean
    BookServiceGrpc.BookServiceBlockingStub bookServiceStub() {
        Channel Channel = ManagedChannelBuilder
                .forAddress("localhost",6565)
                .usePlaintext()
                .build();

        return BookServiceGrpc.newBlockingStub(Channel);
    }
}
