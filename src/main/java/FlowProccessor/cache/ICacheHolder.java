package FlowProccessor.cache;

import FlowProccessor.model.impl.BotFlow;

import java.util.List;

public interface ICacheHolder {

    void remove(String key);
    void put(String key, List<BotFlow> flows);
    List<BotFlow> get(String key);
}
