package me.jasperedits.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.experimental.UtilityClass;
import me.jasperedits.manager.InteractivePaginator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class PaginatorClassifierCache {

    private final Cache<Long, InteractivePaginator> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    public InteractivePaginator get(Long id) throws ExecutionException {
        return cache.getIfPresent(id);
    }

    public void set(Long id, InteractivePaginator paginator) {
        cache.put(id, paginator);
    }
}
