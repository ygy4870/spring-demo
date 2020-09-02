package com.ygy.study.zkdemo.zk;

import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class RegisterTest {


    @Autowired
    Register register;

    @Test
    void add() {

        String path = "/a";
        try {
            register.createZnode(path, ""+1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            register.getZnodeData(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            register.getZnodeData2(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建临时有序节点，客户端断开，则被删除掉（分布式锁就是利用这个特性）
     */
    @Test
    void addTmpNode() {
        String path = "/a/locks";
        String realPath = null;
        try {
            realPath = register.createZnodeWithMode(path, ""+1, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(realPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            register.getZnodeData2(realPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addTmpNode2() {
        String path = "/a/locks/lock";
        String realPath = null;
        try {
            realPath = register.createZnodeWithMode(path, ""+1, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(realPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            register.getZnodeData2(realPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getChildren() {
        String path = "/a";
        try {
            register.getZnodeChildren(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CountDownLatch latch = new CountDownLatch(3);
    }

    @Test
    void delete() {
        String path = "/a";
        try {
            register.deletePath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void set() {
        String path = "/a";

        try {
            register.getZnodeData2(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            register.setValue(path, 5+"");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            register.getZnodeData2(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void watch() {
        String path = "/a";

//        try {
//            register.addWatcherWithNodeCache(path);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            register.addWatcherWithChildrenCache(path);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            register.addWatcherWithTreeCache(path);
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
