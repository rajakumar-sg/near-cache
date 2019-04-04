package com.reinvent.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 */
public class App {
    public static final String TEST_MAP = "TestMap";
    private static HazelcastInstance cluster;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        startHazelcast();
    }

    public static void startHazelcast() {
        Config config = new Config();
        config.addMapConfig(new MapConfig(TEST_MAP));
        cluster = Hazelcast.newHazelcastInstance(config);

        IMap testMap = cluster.getMap(TEST_MAP);
        testMap.put(1, "One");
        testMap.put(2, "Two");
        testMap.put(3, "Three");
        testMap.put(4, "Four");
        testMap.put(5, "Five");
        testMap.put(6, "Six");

        Timer timer = new Timer();
        TimerTask updater = new TimerTask() {
            @Override
            public void run() {
                updateValues();
                System.out.println(new Date() + " - Values updated..");
            }
        };

        timer.scheduleAtFixedRate(updater, 0L, 500L);
    }

    private static void updateValues() {
        IMap testMap = cluster.getMap(TEST_MAP);
        testMap.put(1, "One "+new Date());
        testMap.put(2, "Two "+new Date());
    }
}
