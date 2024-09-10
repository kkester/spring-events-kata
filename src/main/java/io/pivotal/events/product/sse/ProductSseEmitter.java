package io.pivotal.events.product.sse;

import io.pivotal.events.product.ProductRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class ProductSseEmitter {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(error -> emitters.remove(emitter));
    }

    public void emitProductUpdated(ProductRecord product) {
        log.info("Publishing event to emitters {}", emitters.size());
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(product, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                log.info("Publish event error handled. {} emitters remaining.", emitters.size());
            }
        }
    }
}
