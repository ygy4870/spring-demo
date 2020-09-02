package com.ygy.study.zkdemo.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.*;

@Component
public class Register {
    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    @Value("${zookeeper.addr}")
    private String zookerAddress;

    private String DEFINE_NAME_SPACE = "EDU100_SV_DEFINE";

    private static String STR_BACKUP = "?backup=";
    private CuratorFramework client;

    private boolean connected = false;


    @PostConstruct
    public void connect() {
        int timeout = 5000;
        String zkAddr = getZkAddr();
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(zkAddr)
                .retryPolicy(new RetryNTimes(1, 1000))
                .namespace(DEFINE_NAME_SPACE)
                .sessionTimeoutMs(1000) // 会话超时
                .connectionTimeoutMs(timeout) // 链接超时
                ;

        client = builder.build();
        logger.info("begin connected, zkAddr:{}", zkAddr);
        client.start();
        connected = true;

    }

    public boolean isConnected() {
        return null != client && client.getZookeeperClient().isConnected();
    }

    public void disconnect() {
        if (connected && null != client) {
            client.close();
            connected = true;
        }
    }


    public void createZnode(String path, String value) throws Exception {
        this.getClient()
                .create()//创建Znode
                .creatingParentsIfNeeded()//如果是多级结点,这里声明如果需要,自动创建父节点
                .withMode(CreateMode.PERSISTENT)//声明结点类型
                .forPath(path,value.getBytes());//声明结点路径和值
    }

    public String createZnodeWithMode(String path, String value, CreateMode mode) throws Exception {
        return  this.getClient()
                .create()//创建Znode
                .creatingParentsIfNeeded()//如果是多级结点,这里声明如果需要,自动创建父节点
                .withMode(mode)//声明结点类型
                .forPath(path,value.getBytes());//声明结点路径和值
    }

    public void deleteZnode(String path) throws Exception {
        this.getClient()
                .delete()
                .deletingChildrenIfNeeded()//如果有子节点,会先自动删除子节点再删除本结点
                .forPath(path);
    }

    public void getZnodeData(String path) throws Exception {
        byte[] dataBytes = this.getClient().getData().forPath(path);
        System.out.println("结点值为:" +new String(dataBytes));
    }


    public void getZnodeData2(String path) throws Exception {
        Stat stat=new Stat();
        byte[] dataBytes = this.getClient().getData().storingStatIn(stat).forPath(path);
        System.out.println("结点值为:" +new String(dataBytes));
        System.out.print("目前版本为:"+stat.getVersion()+"创建时间为:"+stat.getCtime());
    }

    public void getZnodeChildren(String path) throws Exception {
        List<String> childrens = this.getClient().getChildren().forPath(path);
        System.out.println("子节点为:" + childrens);
    }


    public void setValue(String path,String value) throws Exception {
        Stat stat = this.getClient().checkExists().forPath(path);
        if (stat==null){
            System.out.println("Znode does not exists");
        }else {
            this.getClient()
                    .setData()
                    .withVersion(stat.getVersion())
                    .forPath(path,value.getBytes());
        }
    }

    /**
     * Cutator提供了三种完善而又灵活的监听机制
     * PathchildCache ~监听一个节点下子节点的创建、删除、更新
     * NodeCache ~监听一个节点的更新和创建事件(不包括删除)
     * TreeCache ~综合PatchChildCache和INodeCache的特性
     */

    public void addWatcherWithNodeCache(String path) throws Exception {
        final NodeCache nodeCache = new NodeCache(this.getClient(),path,false);
        NodeCacheListener nodeCacheListener=new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("事件路径:"+nodeCache.getCurrentData().getPath()+"发生数据变化,新数据为"+new String(nodeCache.getCurrentData().getData()));
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    public void addWatcherWithChildrenCache(String path) throws Exception {
        final PathChildrenCache childrenCache=new PathChildrenCache(this.getClient(),path,true);//缓存数据
        PathChildrenCacheListener pathChildrenCacheListener=new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("事件路径:"+pathChildrenCacheEvent.getData().getPath()+"事件类型"+pathChildrenCacheEvent.getType());
            }
        };
        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        childrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }

    public void addWatcherWithTreeCache(String path) throws Exception {
        TreeCache treeCache=new TreeCache(this.getClient(),path);
        TreeCacheListener treeCacheListener=new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                if (treeCacheEvent.getType().equals(NODE_ADDED)
                        || treeCacheEvent.getType().equals(NODE_UPDATED)) {

                } else if (treeCacheEvent.getType().equals(NODE_REMOVED)) {

                }
                System.out.println("事件路径:"+treeCacheEvent.getData().getPath()+"事件类型"+treeCacheEvent.getType()+"结点值为"+new String(treeCacheEvent.getData().getData()));

            }
        };
        treeCache.getListenable().addListener(treeCacheListener);
        treeCache.start();
    }





    public CuratorFramework getClient() {
        return client;
    }


    public String getZkAddr() {
        if (null == zookerAddress) {
            return null;
        }
        if (zookerAddress.indexOf("backup") < 0) {
            return getZkAddrWithoutBackup();
        } else {
            return getZkAddrWithBackup();
        }
    }
    private String getZkAddrWithBackup(){
        if (null == zookerAddress || zookerAddress.indexOf("backup") < 0) {
            return null;
        }

        int i = zookerAddress.indexOf("//");
        if (i < 0) {
            return null;
        }
        int end = zookerAddress.indexOf(STR_BACKUP, i + 2);
        if (end < 0) {
            return null;
        }
        String fstAddr = zookerAddress.substring(i + 2, end);
        // 只返回第一个地址
        return fstAddr;
    }


    private String getZkAddrWithoutBackup(){
        if (null == zookerAddress) {
            return null;
        }

        int i = zookerAddress.indexOf("//");
        if (i < 0) {
            return null;
        }
        int end = zookerAddress.indexOf("/", i + 2);
        if (end < 0) {
            return zookerAddress.substring(i + 2);
        }
        return zookerAddress.substring(i + 2, end);
    }

    public void deletePath(String path) throws Exception {
        if(!connected) {
            return;
        }
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    public Register() {
    }

    public Register(String zookerAddress) {
        this.zookerAddress = zookerAddress;
    }

    public static void main(String[] args) {

        Register register = new Register("zookeeper://127.0.0.1:2181");
        System.out.println(register.getZkAddrWithBackup());
        System.out.println(register.getZkAddrWithoutBackup());


    }

}
