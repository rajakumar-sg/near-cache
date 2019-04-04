package com.reinvent.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.NearCacheConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.junit.Test;

import java.util.Date;

import static com.reinvent.hazelcast.App.TEST_MAP;

public class NearCacheTest {

    @Test
    public void test() throws InterruptedException {
        ClientConfig clientConfig = new ClientConfig();
        NearCacheConfig nearCacheConfig = new NearCacheConfig(TEST_MAP);
        nearCacheConfig.setTimeToLiveSeconds(5);
        nearCacheConfig.setEvictionConfig(
                new EvictionConfig(10, EvictionConfig.MaxSizePolicy.ENTRY_COUNT, EvictionPolicy.LRU)
        );
        clientConfig.addNearCacheConfig(nearCacheConfig);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        IMap<Object, Object> testMap = client.getMap(TEST_MAP);

        for (int i = 0; i < 10; i++) {
            System.out.println(new Date() + " - value of 1:" + testMap.get(1));
            System.out.println(new Date() + " - value of 2:" + testMap.get(2));

            Thread.sleep(1000L);
        }

        System.out.println("Stats :" + testMap.getLocalMapStats().getNearCacheStats());
    }
}
