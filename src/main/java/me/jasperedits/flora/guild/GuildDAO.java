package me.jasperedits.flora.guild;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.client.model.Filters;
import lombok.experimental.UtilityClass;
import me.jasperedits.flora.FloraBot;
import me.jasperedits.flora.docs.db.impl.Guild;
import org.jetbrains.annotations.NotNull;
import org.mongojack.JacksonMongoCollection;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class GuildDAO {

    private final JacksonMongoCollection<Guild> guildCollection = FloraBot.getInstance().getDatabaseManager().getCollection("guilds", Guild.class);

    private final LoadingCache<String, Guild> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<>() {
                        @Override
                        public Guild load(@NotNull String id) {
                            return findGuild(id).orElseGet(() -> new Guild(id));
                        }
                    });

    public void updateGuild(Guild guild) {
        guildCollection.save(guild);
    }

    public Optional<Guild> findGuild(String id) {
        return Optional.ofNullable(guildCollection.find()
                .filter(Filters.eq("guildId", id)).first());
    }

    public Guild getGuild(String id) throws ExecutionException {
        return cache.get(id);
    }

}
