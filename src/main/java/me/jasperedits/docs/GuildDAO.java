package me.jasperedits.docs;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import me.jasperedits.FloraBot;
import me.jasperedits.docs.impl.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuildDAO {
    private static MongoCollection<Guild> guildCollection = FloraBot.databaseManager.getCollection("guilds", Guild.class);
    private static LoadingCache<String, Guild> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<>() {
                        @Override
                        public Guild load(@NotNull String id) {
                            return findGuild(id).orElseThrow();
                        }
                    });

    public static void updateGuild(Guild guild) {
        guildCollection.insertOne(guild);
        cache.refresh(guild.getGuildId());
    }

    public static Optional<Guild> findGuild(String id) {
        return Optional.ofNullable(guildCollection.find()
                .filter(Filters.eq("guildId", id)).first());
    }

    public static Guild getGuild(String id) throws ExecutionException {
        return cache.get(id);
    }
}
