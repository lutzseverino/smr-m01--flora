package me.jasperedits.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import me.jasperedits.command.CommandData;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class AnswerClassifierCache {

    private final Cache<Long, CommandData> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.DAYS)
            .build();

    public CommandData get(Long id) throws ExecutionException {
        return cache.getIfPresent(id);
    }

    public void set(Long id, CommandData information) {
        cache.put(id, information);
    }
}
