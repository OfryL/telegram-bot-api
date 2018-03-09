package FlowProccessor.cache;

import FlowProccessor.model.impl.BotFlow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotDefaultCache implements ICacheHolder {

    private Map<String, List<BotFlow>> defaultCache;

    public BotDefaultCache() {

        defaultCache = new HashMap<>();
    }

    @Override
    public void remove(String key) {
        defaultCache.remove(key);
    }

    @Override
    public void put(String key, List<BotFlow> flows) {
        defaultCache.put(key,flows);
    }

    @Override
    public List<BotFlow> get(String key) {
        return defaultCache.get(key) != null ? defaultCache.get(key) : new ArrayList<>();
    }
}
