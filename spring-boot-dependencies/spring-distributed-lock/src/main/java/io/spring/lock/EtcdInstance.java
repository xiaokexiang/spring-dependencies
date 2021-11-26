package io.spring.lock;

import ch.qos.logback.core.util.TimeUtil;
import io.etcd.jetcd.*;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.lease.LeaseGrantResponse;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.watch.WatchResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author xiaokexiang
 * @link https://etcd.io/docs/v3.5/learning/api/
 * @link https://github.com/etcd-io/jetcd/tree/release-0.6.0
 * @link https://www.jianshu.com/p/8a4dc6d900cf
 * @since 2021/11/26
 * 获取etcd客户端
 */
@Slf4j
public class EtcdInstance {

    private static volatile Client CLIENT;

    private EtcdInstance() {
    }

    public static Client getInstance() {
        if (null == CLIENT) {
            synchronized (EtcdInstance.class) {
                if (null == CLIENT) {
                    CLIENT = Client.builder().endpoints("http://192.168.4.68:2381").build();
                }
            }
        }
        return CLIENT;
    }

    public static String lock(String lockName, long ttl, long timeout) throws ExecutionException, InterruptedException {
        StringJoiner joiner = new StringJoiner("/");
        String randomId = UUID.randomUUID().toString().replace("-", "");
        String lockKey = joiner.add(lockName).add(randomId).toString();
        Client client = EtcdInstance.getInstance();

        // 生成lease配置用于设置锁过期时间
        Lease leaseClient = client.getLeaseClient();
        CompletableFuture<LeaseGrantResponse> grant = leaseClient.grant(ttl, timeout, TimeUnit.SECONDS);
        long leaseId = grant.get().getID();

        KV kvClient = client.getKVClient();
        CompletableFuture<PutResponse> future = kvClient.put(ByteSequence.from(lockKey.getBytes()),
                ByteSequence.from(randomId.getBytes()),
                PutOption.newBuilder().withLeaseId(leaseId).build());

        // 获取当前客户端的revision
        long currentRevision = future.get().getHeader().getRevision();

        List<KeyValue> kvs = kvClient.get(ByteSequence.from(lockKey.getBytes()),
                GetOption.newBuilder().isPrefix(true).withSortField(GetOption.SortTarget.MOD).build()).get().getKvs();

        // 当前所有lockName为前缀的键值对中，当前revision是第一个，说明获取锁成功
        if (currentRevision == kvs.get(0).getModRevision()) {
            return lockKey;
        } else {
            for (int i = 1; i < kvs.size(); i++) {
                if (kvs.get(i).getModRevision() == currentRevision) {
                    Watch watchClient = client.getWatchClient();
                    watchClient.watch(kvs.get(i - 1).getKey(),
                            (WatchResponse response) -> {
                                if (response.getEvents().stream().anyMatch(g -> g.getEventType().name().equals("DELETE"))) {
                                    log.info("[lock]: lock Successfully, [revision]: " + currentRevision);
                                }
                            });
                }
            }
        }
        return lockKey;
    }

    public static void unlock(String lockName) throws InterruptedException, ExecutionException, TimeoutException {
        Client client = EtcdInstance.getInstance();
        client.getKVClient().delete(ByteSequence.from(lockName.getBytes())).get(10L, TimeUnit.SECONDS);
    }
}
