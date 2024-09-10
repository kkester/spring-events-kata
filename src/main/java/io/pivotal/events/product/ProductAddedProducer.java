package io.pivotal.events.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductAddedProducer {
    private final RedisTemplate<String, String> redisTemplate;
    private final ChannelTopic channelTopic;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void publish(ProductRecord productRecord) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), objectMapper.writeValueAsString(productRecord));
    }
}
