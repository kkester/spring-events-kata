package io.pivotal.events.util;

import lombok.SneakyThrows;

import java.util.concurrent.Future;

public class FutureUtil {
    private FutureUtil() {
    }

    @SneakyThrows
    public static <T> T get(Future<T> future) {
        return future.get();
    }
}
