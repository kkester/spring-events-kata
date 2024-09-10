package io.pivotal.events.product.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.events.product.ProductRecord;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import reactor.util.annotation.NonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductMessageListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ProductSseEmitter productSseEmitter;

    @Override
    @SneakyThrows
    public void onMessage(@NonNull Message message, byte[] pattern) {
        log.info("Received message: {} from channel: {}", message, new String(message.getChannel()));
        ProductRecord productRecord = objectMapper.readValue(message.getBody(), ProductRecord.class);
        productSseEmitter.emitProductUpdated(productRecord);
    }
}
