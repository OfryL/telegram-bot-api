package poc.cache;

import FlowProccessor.cache.ICacheHolder;
import FlowProccessor.model.impl.BotFlow;
import com.google.common.cache.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Cache implements ICacheHolder {

    private LoadingCache<String, List<BotFlow>> cache;

    public Cache(RemovalListener<String, List<BotFlow>> listener) {

        CacheLoader<String, List<BotFlow>> loader = new CacheLoader<String, List<BotFlow>>() {
            @Override
            public List<BotFlow> load(String key) {
                return new ArrayList<>();
            }
        };

        cache = CacheBuilder.newBuilder()
                .removalListener(listener)
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .build(loader);

    }

    @Override
    public void remove(String key) {
        cache.invalidate(key);
    }

    @Override
    public void put(String key, List<BotFlow> flows) {
        cache.put(key, flows);
    }

    @Override
    public List<BotFlow> get(String key) {
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
