package com.bin.rule.core.broadcast.zk;

import com.bin.rule.core.HandlerFactory;
import com.bin.rule.core.broadcast.BroadcastMessage;
import com.bin.rule.core.broadcast.Broadcaster;
import com.bin.rule.core.broadcast.BroadcasterEnum;
import com.bin.rule.core.config.RuleConfig;
import com.bin.rule.core.serializer.SerializerFactory;
import com.bin.rule.core.util.SpringBeanUtil;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zk广播类
 * @author bin
 * @version 1.0 2018/5/10
 * */
public class ZkBroadcaster extends ZkClient implements Broadcaster {

    private static final Logger logger = LoggerFactory.getLogger(ZkBroadcaster.class);

    private static final String RULE_BASE = "/rule";
    private static final String RULE_BROADCAST = RULE_BASE + "/broadcast";

    @Override
    public void init(RuleConfig ruleConfig) {
        setZkServer(ruleConfig.getZkConfig().getZkServer());
    }

    @Override
    public boolean produce(String ruleName, long version) {
        String topicPath = RULE_BROADCAST + "/" + ruleName;

        BroadcastMessage message = new BroadcastMessage();
        message.setRuleName(ruleName);
        message.setVersion(version);

        byte[] serialize = null;
        try {
            serialize = SerializerFactory.getSerializer().serialize(message);
        } catch (Exception e) {
            logger.error("produce -> {}", e.getMessage());
        }
        return setData(topicPath, serialize) != null;
    }

    @Override
    public boolean watch(String name) {
        String topic = RULE_BROADCAST + "/" + name;

        return watchTopic(topic);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.Expired) {
            super.close();
            super.getClient();
        }

        // node data changed
        if (event.getType() == Event.EventType.NodeDataChanged) {
            String path = event.getPath();

            try {
                super.getClient().exists(path, true);
            } catch (Exception e) {
                logger.error("zk process -> {}", e.getMessage());
            }


            byte[] resultData = null;
            try {
                resultData = super.getClient().getData(path, true, null);
            } catch (KeeperException | InterruptedException e) {
                logger.error("zk process -> {}", e.getMessage());
            }

            consume(resultData);
        }
    }

    @Override
    public void consume(byte[] data) {
        try {
            BroadcastMessage message = SerializerFactory.getSerializer().deSerialize(data, BroadcastMessage.class);
            HandlerFactory factory = (HandlerFactory) SpringBeanUtil.getBean(HandlerFactory.class);
            factory.reloadHandler(message.getRuleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return BroadcasterEnum.ZK.getBroadcaster();
    }

    private boolean watchTopic(String path) {
        try {
            Stat stat = super.getClient().exists(path, true);
            if (stat == null) {
                super.existsOrCreate(path);
                stat = super.getClient().exists(path, true);
            }
            boolean ret = stat != null;
            logger.info("zk watchTopic -> {}, path:{}", ret, path);
            return ret;
        } catch (KeeperException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }
}
