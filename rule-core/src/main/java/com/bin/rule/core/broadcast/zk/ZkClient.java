package com.bin.rule.core.broadcast.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * zk客户端
 *
 * @author bin
 * @version 1.0 2018/5/10
 */
public abstract class ZkClient implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(ZkClient.class);

    private String zkServer;

    private volatile ZooKeeper zookeeper; // 单例

    void setZkServer(String zkServer) {
        this.zkServer = zkServer;
    }

    /**
     * 获取zk连接
     *
     * @return zk连接
     */
    ZooKeeper getClient() {
        if (zookeeper == null) {
            synchronized (ZkClient.class) {
                if (zookeeper == null) {
                    try {
                        zookeeper = new ZooKeeper(zkServer, 10000, this);
                    } catch (IOException e) {
                        logger.error("create zookeeper -> {}", e.getMessage());
                    }
                }
            }
        }
        return zookeeper;
    }

    /**
     * 关闭zk连接
     */
    void close() {
        if (zookeeper != null) {
            try {
                zookeeper.close();
                zookeeper = null;
            } catch (InterruptedException e) {
                logger.error("close zookeeper -> {}", e.getMessage());
            }
        }
    }

    /**
     * 创建节点
     *
     * @return zk连接
     */
    private Stat existsOrCreate(String path) throws KeeperException, InterruptedException {
        if (path == null || path.length() < 1) {
            return null;
        }

        Stat stat = zookeeper.exists(path, false);
        if (stat != null) { // 节点已存在
            return stat;
        } else {
            // 不存在创建节点
            String parentPath = path.substring(0, path.lastIndexOf("/"));
            if (parentPath.length() < path.length()) {
                existsOrCreate(parentPath);
            }
            zookeeper.create(path, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("zk create path -> {}", path);
            return null;
        }
    }

    /**
     * 设置节点数据
     *
     * @param path 节点路径
     * @param data 数据包
     * @return Stat
     */
    Stat setData(String path, byte[] data) {
        try {
            getClient();
            Stat stat = existsOrCreate(path);
            return getClient().setData(path, data, stat != null ? stat.getVersion() : -1);
        } catch (Exception e) {
            logger.error("setData -> {}", e.getMessage());
        }
        return null;
    }

    boolean watchTopic(String path) {
        try {
            Stat stat = getClient().exists(path, true);
            if (stat == null) {
                existsOrCreate(path);
                stat = getClient().exists(path, true);
            }
            boolean ret = stat != null;
            logger.info("zk watchTopic -> {}, path:{}", ret, path);
            return ret;
        } catch (KeeperException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public abstract void process(WatchedEvent watchedEvent);
}
