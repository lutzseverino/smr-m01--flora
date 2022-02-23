package com.jasperls.flora.guild;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jasperls.flora.database.DatabaseCollection;
import com.jasperls.flora.database.DatabaseManager;
import com.jasperls.flora.docs.impl.Guild;
import lombok.NonNull;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuildRepositoryImpl implements GuildRepository {

    private final DatabaseCollection<Guild> guildCollection;
    private final LoadingCache<String, Guild> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<>() {
                        @Override
                        public @NonNull Guild load(@NonNull String id) {
                            return guildCollection.get(id).orElseGet(() -> new Guild(id));
                        }
                    });

    @Inject public GuildRepositoryImpl(DatabaseManager databaseManager) {
        this.guildCollection = databaseManager.getCollection(Guild.class);
    }

    public void update(Guild guild) {
        this.guildCollection.update(guild);
    }

    @Override
    public Guild get(String id) {
        try {
            return cache.get(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

}
