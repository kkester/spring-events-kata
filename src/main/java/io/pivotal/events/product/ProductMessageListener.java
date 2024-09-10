package io.pivotal.events.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMessageListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ProductSseEmitter productSseEmitter;

    @Override
    @SneakyThrows
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String msg = new String(message.getBody());

        System.out.println("Received message: " + msg + " from channel: " + channel);
        ProductRecord productRecord = objectMapper.readValue(msg, ProductRecord.class);
        productSseEmitter.emitProductUpdated(productRecord);
    }
}
