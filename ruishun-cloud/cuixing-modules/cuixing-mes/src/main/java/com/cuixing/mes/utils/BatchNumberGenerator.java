package com.cuixing.mes.utils;

import com.cuixing.common.core.constant.CacheConstants;
import com.cuixing.common.core.text.Convert;
import com.cuixing.common.core.utils.SpringUtils;
import com.cuixing.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ruoyi-common-core 批次号生成器
 * wupujian 2024-0603
 */
public class BatchNumberGenerator {



    private static final AtomicInteger sequenceNumber = new AtomicInteger(0);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    static LocalDateTime lastResetTime = LocalDateTime.now();

    /**
     * 生成批次号
     *
     * @param prefix 批次号前缀
     * @return 返回生成的批次号
     */
    public static String generateBatchNumber(String prefix) {

        try {
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();

            // 将当前时间格式化为时间戳字符串
            String timestamp = now.format(formatter);
            // 将上次重置时间格式化为时间戳字符串
            String lastResetTimeStr = lastResetTime.format(formatter);

            // 构造旧的缓存键
            String oldKey = CacheConstants.SYS_BATCH_NUMBER + lastResetTimeStr;
            // 构造新的缓存键
            String key = CacheConstants.SYS_BATCH_NUMBER + timestamp;

            // 获取 RedisService 实例
            RedisService redisService = SpringUtils.getBean(RedisService.class);
            // 从 Redis 中获取缓存对象，并转换为 Integer 类型
            Integer cacheObject = Convert.toInt(redisService.getCacheObject(key));

            // 判断当前日期是否与上次重置日期不同
            if (!now.toLocalDate().equals(lastResetTime.toLocalDate())) {
                // 如果缓存对象不为空
                if (cacheObject != null) {
                    // 更新序列号
                    sequenceNumber.set(cacheObject);
                } else {
                    // 如果缓存对象为空，设置新的缓存对象和序列号
                    redisService.setCacheObject(key, 0, 86400L, TimeUnit.SECONDS);
                    sequenceNumber.set(0);
                }
                LocalDateTime yesterday = now.minusDays(1);
                String yesterdayStr = yesterday.format(formatter);
                // 删除旧的缓存对象
                redisService.deleteObject(CacheConstants.SYS_BATCH_NUMBER + yesterdayStr);
                redisService.deleteObject(oldKey);
                // 更新上次重置时间
                lastResetTime = now;
            } else {
                // 如果当前日期与上次重置日期相同
                if (cacheObject != null) {
                    // 更新序列号
                    sequenceNumber.set(cacheObject);
                } else {
                    // 如果缓存对象为空，设置新的缓存对象（序列号仍为0）
                    redisService.setCacheObject(key, 0, 86400L, TimeUnit.SECONDS);
                }
            }

            // 递增序列号
            int increment = sequenceNumber.incrementAndGet();
            // 将新的序列号存入 Redis 缓存
            redisService.setCacheObject(key, increment, 86400L, TimeUnit.SECONDS);

            // 格式化序列号
            String formattedSequence = String.format("%03d", increment);

            // 构造批次号
            StringBuilder batchNumber = new StringBuilder(prefix);

            // 追加时间戳
            batchNumber.append(timestamp);
            // 追加随机数
            batchNumber.append((int) (Math.random() * 1000));
            // 追加格式化后的序列号
            batchNumber.append(formattedSequence);

            // 返回生成的批次号
            return batchNumber.toString();
        } catch (Exception e) {
            e.printStackTrace();
            // 出错时(redis 连接失败等)返回 雪花id
            return prefix + SnowflakeIdGenerator.nextId();
        }
    }



    public static void main(String[] args) {

        System.out.println(SnowflakeIdGenerator.nextId());
        System.out.println(generateBatchNumber("PROD_"));
//        LocalDate currentDate = LocalDate.now();
//        System.out.println("当前日期：" + currentDate);
//
//        LocalDate localDate = LocalDateTime.now().toLocalDate();
//        System.out.println("当前日期2：" + localDate);
//        for (int i = 0; i < 5; i++) {
//            System.out.println(generateBatchNumber("BT"));
//            String id = Seq.getId(Seq.uploadSeqType);
//            System.out.println(id);
//        }
    }
}
