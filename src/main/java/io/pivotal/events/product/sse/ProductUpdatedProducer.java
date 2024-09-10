package io.pivotal.events.product.sse;

import io.pivotal.events.product.ProductRecord;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductUpdatedProducer {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

    @SneakyThrows
    public void publish(ProductRecord productRecord) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), productRecord);
    }
}
