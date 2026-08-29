package com.cuixing.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.*;

/**
 * @program: security-manage
 * @description:
 * @author: wupujian
 * @create: 2022-04-15 16:50
 **/
public class MyCollectionUtils {

    /**
     * 添加所有给定的元素至给定的集合中
     *
     * @param c 给定的集合
     * @param elements 需要添加至集合中的给定元素
     * @param <T> 给定集合的类型参数
     * @return 返回<tt>true</tt>说明添加成功，反之亦然
     */
    @SafeVarargs
    public static <T> boolean addAll(Collection<T> c, T... elements) {
        boolean result = false;
        if (elements == null) {
            return false;
        }
        for (T element : elements) {
            result |= c.add(element);
        }
        return result;
    }

    /**
     * 添加所有给定的元素至给定的集合中并返回集合对象
     *
     * @param list 给定的集合
     * @param elements 需要添加至集合中的给定元素
     * @param <T> 给定集合的类型参数
     * @return 返回集合对象
     */
    public static <T> List<T> add(List<T> list, T... elements) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (elements != null) {
            list.addAll(Arrays.asList(elements));
        }
        return list;
    }

    /**
     * 连接集合为字符串
     *
     * @param coll 集合对象
     * @param ch 分割字符
     * @return String
     */
    public static String join(Collection<Object> coll, CharSequence ch) {
        if (isEmpty(coll)) {
            return "";
        }
        StringBuilder acquire = new StringBuilder();
        coll.forEach(o -> acquire.append(o).append(ch));
        if (acquire.length() > 0) {
            acquire.delete(acquire.length() - ch.length(), acquire.length());
        }
        return acquire.toString();
    }

    /**
     * 连接集合为字符串
     *
     * @param coll 集合对象
     * @param ch 分割字符
     * @param fun 获取对象连接属性值
     * @return String
     */
    public static <T> String join(Collection<T> coll, CharSequence ch, Function<T, Object> fun) {
        if (isEmpty(coll)) {
            return "";
        }
        StringBuilder acquire = new StringBuilder();
        coll.forEach(o -> acquire.append(fun.apply(o)).append(ch));
        if (acquire.length() > 0) {
            acquire.delete(acquire.length() - ch.length(), acquire.length());
        }
        return acquire.toString();
    }

    /**
     * 连接数组为字符串
     *
     * @param objects 集合对象
     * @param ch 分割字符
     * @return String
     */
    public static String join(Object[] objects, CharSequence ch) {
        return join(Arrays.asList(objects), ch);
    }

    /**
     * 连接数组为字符串
     *
     * @param ch 分割字符
     * @param objects 集合对象
     * @return String
     */
    public static String join(CharSequence ch, Object... objects) {
        return join(objects, ch);
    }

    /**
     * 切割字符串
     *
     * @param ch 切割标识
     * @param string 字符串
     * @return 返回切合字符串集合
     */
    public static List<String> split(String ch, String string) {
        ch = ch == null ? "," : ch;
        if (StringUtils.isEmpty(string)) {
            return Collections.emptyList();
        }
        return Arrays.asList(string.split(ch));
    }

    /**
     * 切割字符串
     *
     * @param string 字符串
     * @return 返回切合字符串集合
     */
    public static List<String> split(String string) {
        return split(null, string);
    }

    /**
     * 切割字符串
     *
     * @param ch 切割标识
     * @param string 字符串
     * @param consumer 消费函数
     */
    public static void split(String ch, String string, Consumer<String> consumer) {
        isNotEmpty(split(ch, string), consumer);
    }

    /**
     * 切割字符串
     *
     * @param string 字符串
     * @param consumer 消费函数
     */
    public static void split(String string, Consumer<String> consumer) {
        isNotEmpty(split(string), consumer);
    }

    /**
     * 获取list集合中的元素(防止数组越界异常)
     *
     * @param list list集合
     * @param index 索引下标
     * @param defaultValue 默认值
     * @return 如果获取成功则返回对应元素，否则返回null
     */
    public static <T> T get(List<T> list, int index, T defaultValue) {
        if (isEmpty(list) || index > list.size() - 1 || index < 0) {
            return defaultValue;
        }
        return list.get(index);
    }

    /**
     * 获取list集合中的元素(防止数组越界异常)
     *
     * @param list list集合
     * @param index 索引下标
     * @return 如果获取成功则返回对应元素，否则返回null
     */
    public static <T> T get(List<T> list, int index) {
        return get(list, index, null);
    }

    /**
     * 获取list集合中的元素(防止数组越界异常)
     *
     * @param list list集合
     * @return 如果获取成功则返回对应元素，否则返回null
     */
    public static <T> T getFirst(List<T> list) {
        return get(list, 0);
    }

    /**
     * 获取数组中的某个元素(防止数组越界异常)
     *
     * @param arr list集合
     * @param index 索引下标
     * @param defaultValue 默认值
     * @return 如果获取成功则返回对应元素，否则返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Object[] arr, int index, T defaultValue) {
        if (isEmpty(arr) || index > arr.length - 1 || index < 0) {
            return defaultValue;
        }
        return (T) arr[index];
    }

    /**
     * 获取数组中的某个元素(防止数组越界异常)
     *
     * @param arr list集合
     * @param index 索引下标
     * @return 如果获取成功则返回对应元素，否则返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Object[] arr, int index) {
        return get(arr, index, null);
    }

    /**
     * 获取数组中的某个元素(防止数组越界异常)
     *
     * @param arr list集合
     * @return 如果获取成功则返回对应元素，否则返回null
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFirst(Object[] arr) {
        return get(arr, 0);
    }

    /**
     * 判断集合为空集合
     *
     * @param coll 集合
     * @return 返回<tt>true</tt>说明是空集合，反之亦然
     */
    public static boolean isEmpty(Collection coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * 判断map是否为空
     *
     * @param map map
     * @return 返回<tt>true</tt>说明是空map，反之亦然
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }


    /**
     * 判断map是否为空
     *
     * @param map map
     * @param biConsumer 消费函数
     */
    public static <K, V> void isNotEmpty(Map<K, V> map, BiConsumer<K, V> biConsumer) {
        if (isNotEmpty(map)) {
            map.forEach(biConsumer);
        }
    }

    /**
     * 判断数组是否为空
     *
     * @param arr 数组
     * @return 返回<tt>true</tt>说明是空数组，反之亦然
     */
    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    /**
     * 判断集合不为空集合
     *
     * @param coll 集合
     * @return 返回<tt>true</tt>说明不是空集合，反之亦然
     */
    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    /**
     * 集合不为空时进行消费
     *
     * @param coll 集合
     * @param consumer 消费函数
     */
    public static <T> void isNotEmpty(Collection<T> coll, Consumer<T> consumer) {
        if (!isEmpty(coll)) {
            coll.forEach(consumer);
        }
    }


    /**
     * 集合不为空时进行消费
     *
     * @param arr 数组
     * @param consumer 消费函数
     */
    @SuppressWarnings({"LambdaBodyCanBeCodeBlock", "unchecked"})
    public static <T> void isNotEmpty(Object[] arr, Consumer<T> consumer) {
        if (!isEmpty(arr)) {
            Arrays.asList(arr).forEach(item -> consumer.accept((T) item));
        }
    }

    /**
     * 集合不为空时进行消费
     *
     * @param coll 集合
     * @param biConsumer 消费函数
     */
    public static <T> void isNotEmpty(Collection<T> coll, BiConsumer<T, Integer> biConsumer) {
        if (isNotEmpty(coll)) {
            int index = 0;
            for (T t : coll) {
                biConsumer.accept(t, index);
                index++;
            }
        }
    }

    /**
     * 判断map是否不为空
     *
     * @param map map
     * @return 返回<tt>true</tt>说明不是空map，反之亦然
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * 判断数组是否不为空
     *
     * @param arr 数组
     * @return 返回<tt>true</tt>说明不是空数组，反之亦然
     */
    public static boolean isNotEmpty(Object[] arr) {
        return !isEmpty(arr);
    }

    /**
     * 集合转set
     *
     * @param coll 集合
     * @param <T> 泛型参数
     * @return 返回Set<T>
     */
    public static <T> Set<T> toSet(Collection<T> coll) {
        if (coll == null || coll.size() == 0) {
            return new HashSet<>();
        }
        return new HashSet<>(coll);
    }

    /**
     * 数组转set
     *
     * @param arr 集合
     * @param <T> 泛型参数
     * @return 返回Set<T>
     */
    public static <T> Set<T> arrToSet(T[] arr) {
        if (arr == null || arr.length == 0) {
            return new HashSet<>();
        }
        return new HashSet<T>(Arrays.asList(arr));
    }

    /**
     * 查找集合元素
     *
     * @param coll 集合
     * @param predicate 断言函数
     * @return 返回断言匹配的元素
     */
    public static <T> T find(Collection<T> coll, Predicate<T> predicate) {
        if (isEmpty(coll)) {
            return null;
        }
        for (T item : coll) {
            if (predicate.test(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * list转map
     *
     * @param list 集合
     * @param keyFun 根据集合元素生成map key函数
     * @param <K> map key泛型参数
     * @param <T> 集合元素泛型参数
     * @return 返回集合转化后的map
     */
    public static <K, T> Map<K, List<T>> listToMap(List<T> list, Function<T, K> keyFun) {
        Map<K, List<T>> map = new HashMap<>();
        isNotEmpty(list, t -> map.computeIfAbsent(keyFun.apply(t), key -> new ArrayList<>()).add(t));
        return map;
    }
    /**
     * 向map添加新元素
     *
     * @param map map对象
     * @param key1 键1
     * @param o1 值1
     */
    public static Map<String, Object> put(Map<String, Object> map, String key1, Object o1) {
        if (map == null) {
            return new HashMap<String, Object>() {{
                this.put(key1, o1);
            }};
        }
        map.put(key1, o1);
        return map;
    }

    /**
     * 向map添加新元素
     *
     * @param map map对象
     * @param key1 键1
     * @param o1 值1
     */
    public static Map<String, Object> put(Map<String, Object> map, String key1, Object o1,
                                          String key2, Object o2) {
        if (map == null) {
            return new HashMap<String, Object>() {{
                this.put(key1, o1);
                this.put(key2, o2);
            }};
        }
        map.put(key1, o1);
        return map;
    }

    /**
     * 向map添加新元素
     *
     * @param map map对象
     * @param key1 键1
     * @param o1 值1
     */
    public static Map<String, Object> put(Map<String, Object> map, String key1, Object o1,
                                          String key2, Object o2, String key3, Object o3) {
        if (map == null) {
            return new HashMap<String, Object>() {{
                this.put(key1, o1);
                this.put(key2, o2);
                this.put(key3, o3);
            }};
        }
        map.put(key1, o1);
        return map;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception{
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

    public static <T> List<T> mapAsList(List list, Supplier<T> supplier){
        List<T> result = new ArrayList<>();
        isNotEmpty(list,item -> {
            T obj = supplier.get();
            BeanUtils.copyProperties(item, obj);
            result.add(obj);
        });
        return result;
    }

    public static String getValue(Map<String,String> map,String key){
        if(StringUtils.isNotEmpty(key)){
            for (Map.Entry<String, String> entry: map.entrySet()) {
                if(StringUtils.equals(entry.getValue(), key)){
                    return entry.getKey();
                }
            }
        }
        return "";
    }
}
