package com.aurora.day.auroratimerserver.utils;

import com.aurora.day.auroratimerserver.model.UserTimeBuffer;
import com.aurora.day.auroratimerserver.services.IUserTimeService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class UserTimeBufferedBatchWriter {
    @Inject
    IUserTimeService userTimeService;

    private static final int BATCH_SIZE_THRESHOLD = 10;
    private static final long BUFFER_TIMEOUT_MS = 30 * 60 * 1000; // 30分钟
    private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    private final ReentrantLock batchLock = new ReentrantLock(); // 批处理锁
    // 缓存与队列
    private final Cache<String, UserTimeBuffer> cache = Caffeine.newBuilder()
            .maximumSize(100) // 安全上限
            .removalListener((String key, UserTimeBuffer value, RemovalCause cause) -> {
                if (cause != RemovalCause.EXPLICIT) {
                    queue.remove(key); // 意外移除时清理队列
                }
            })
            .build();

    // 添加(或更新)元素到缓存和队列
    public void addToBuffer(String key, UserTimeBuffer entity) {
        batchLock.lock();
        try {
            cache.put(key, entity);
            if(!queue.contains(key)){
                queue.offer(key);
            }
            if (cache.estimatedSize() >= BATCH_SIZE_THRESHOLD) {
                batchInsertAndFlush(); // 触发数量阈值写入
            }
        } finally {
            batchLock.unlock();
        }
    }

    // 定时检查首个元素时间
    @Scheduled(fixedRate = 10 * 60 * 1000) // 每10分钟检查一次
    public void checkBufferTimeout() {
        if (queue.isEmpty()) return;

        long now = System.currentTimeMillis();
        String firstKey = queue.peek();
        if (firstKey == null) return;
        UserTimeBuffer first = cache.getIfPresent(firstKey);

        if (first != null && (now - first.getInternalUpdatedTime()) > BUFFER_TIMEOUT_MS) {
            batchLock.lock();
            try {
                if (!queue.isEmpty() && queue.peek().equals(firstKey)) { // Double-check
                    batchInsertAndFlush();
                }
            } finally {
                batchLock.unlock();
            }
        }
    }

    // 取出数据并清空当前批次
    private void batchInsertAndFlush() {
        if (queue.isEmpty()) return;
        List<UserTimeBuffer> batch = new ArrayList<>();
        List<String> keysToRemove = new ArrayList<>();
        Iterator<String> it = queue.iterator();
        while (it.hasNext()) {
            String key = it.next();
            UserTimeBuffer entity = cache.getIfPresent(key);
            if (entity != null) {
                batch.add(entity);
                keysToRemove.add(key);
            }
            it.remove();
            if (batch.size() >= BATCH_SIZE_THRESHOLD) break; // 安全截断
        }
        if (!batch.isEmpty()) {
            // 批量写入数据库
            userTimeService.batchUpdate(batch);
            cache.invalidateAll(keysToRemove); // 显式移除已处理key
        }
    }
}
