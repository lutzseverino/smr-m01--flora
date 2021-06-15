package me.jasperedits.buttons;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import me.jasperedits.commands.CommandInformation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class ExecutorCache {

    private final Cache<String, CommandInformation> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(3, TimeUnit.DAYS)
            .build();

    public CommandInformation get(String id) throws ExecutionException {
        return cache.getIfPresent(id);
    }

    public void set(String id, CommandInformation information) {
        cache.put(id, information);
    }
}
