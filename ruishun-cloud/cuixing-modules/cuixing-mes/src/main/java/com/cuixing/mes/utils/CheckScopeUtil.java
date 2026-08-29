package com.cuixing.mes.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckScopeUtil {
    public static double extractFirstValue(String specification) {
        // 正则表达式匹配以下情况：
        // 1. 数字/数字（捕获斜杠前的数字）
        // 2. 数字*数字（捕获星号前的数字）
        // 3. 独立的小数或整数（例如 0.09）
        Pattern pattern = Pattern.compile("(\\d+\\.\\d*|\\d+)(?:/|\\*|[^0-9]|$)");
        Matcher matcher = pattern.matcher(specification);

        // 仅匹配第一个符合条件的值
        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group(1)); // 返回第一个匹配值
            } catch (NumberFormatException e) {
                System.err.println("非数字部分被忽略: " + matcher.group(1));
            }
        }

        return -1.0; // 如果没有匹配，返回 null
    }
}
