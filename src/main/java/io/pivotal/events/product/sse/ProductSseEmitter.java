package io.pivotal.events.product.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component("productUpdateMessageListener")
@Slf4j
public class ProductSseEmitter implements MessageListener {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(error -> emitters.remove(emitter));
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        String messagePattern = new String(pattern);
        String messageBody = new String(message.getBody());
        log.info("Publishing event to emitters {} for {}", emitters.size(), messagePattern);
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(messageBody, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                log.info("Publish event error handled. {} emitters remaining.", emitters.size());
            }
        }
    }
}
