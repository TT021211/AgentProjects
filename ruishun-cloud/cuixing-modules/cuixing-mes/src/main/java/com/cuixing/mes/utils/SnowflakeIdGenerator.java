package com.cuixing.mes.utils;

/**
 * 生成雪花id的工具类
 * @Author wupuj
 * @create 2024/6/25 16:59
 */
public class SnowflakeIdGenerator {

    private static final long START_TIMESTAMP = 1609459200000L; // 设置起始时间戳，可以根据需要进行调整
    private static final long WORKER_ID_BITS = 5L; // 机器ID所占位数
    private static final long WORKER_ID_INIT = 1L; // 机器ID所占位数
    private static final long DATACENTER_ID= 1L; // 机器ID所占位数
    private static final long DATACENTER_ID_BITS = 5L; // 数据中心ID所占位数
    private static final long SEQUENCE_BITS = 12L; // 序列号所占位数

    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS); // 机器ID的最大值
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS); // 数据中心ID的最大值

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS; // 机器ID的位移量
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS; // 数据中心ID的位移量
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS; // 时间戳的位移量

    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS); // 序列号的屏蔽位

    private  final long workerId; // 机器ID
    private  final long datacenterId; // 数据中心ID
    private static long lastTimestamp = -1L; // 上次生成ID的时间戳
    private static long  sequence = 0L; // 序列号

    public SnowflakeIdGenerator() {
        if (WORKER_ID_INIT > MAX_WORKER_ID || WORKER_ID_INIT < 0) {
            throw new IllegalArgumentException("Worker ID 超出范围");
        }
        if (WORKER_ID_INIT > MAX_DATACENTER_ID || WORKER_ID_INIT < 0) {
            throw new IllegalArgumentException("数据中心 ID 超出范围");
        }
        this.workerId = WORKER_ID_INIT;
        this.datacenterId = DATACENTER_ID;
    }

    public static synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("系统时间不正确");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT) |
                (DATACENTER_ID << DATACENTER_ID_SHIFT) |
                (WORKER_ID_INIT << WORKER_ID_SHIFT) |
                sequence;
    }

    public static long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        long l = nextId();

        System.out.println(l);
    }
}
