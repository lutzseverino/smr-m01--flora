package me.jasperedits.flora.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import me.jasperedits.flora.command.ExecutionData;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class AnswerClassifierCache {

    private final Cache<Long, ExecutionData> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.DAYS)
            .build();

    public ExecutionData get(Long id) throws ExecutionException {
        return cache.getIfPresent(id);
    }

    public void set(Long id, ExecutionData information) {
        cache.put(id, information);
    }
}
