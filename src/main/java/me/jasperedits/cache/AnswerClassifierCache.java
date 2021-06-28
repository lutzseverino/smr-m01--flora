package me.jasperedits.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import me.jasperedits.command.CommandInformation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class AnswerClassifierCache {

    private final Cache<Long, CommandInformation> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.DAYS)
            .build();

    public CommandInformation get(Long id) throws ExecutionException {
        return cache.getIfPresent(id);
    }

    public void set(Long id, CommandInformation information) {
        cache.put(id, information);
    }
}
