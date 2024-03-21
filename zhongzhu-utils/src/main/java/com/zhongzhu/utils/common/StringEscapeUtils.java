package com.zhongzhu.utils.common;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author admin
 * 字符串转义工具
 */
public class StringEscapeUtils {

    public static final String[] SPECIAL_SYMBOL_ARR = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};

    public static final String SPECIAL_SYMBOL_REGEX = "^[\\s\\S]*[$()*+.\\[\\]?^{}|][\\s\\S]*$";

    public static final Pattern SPECIAL_SYMBOL_PATTERN = Pattern.compile(SPECIAL_SYMBOL_REGEX);

    public static String escapeSpecialSymbol(String raw) {
        if (!StringUtils.hasText(raw)) {
            return raw;
        }

        Matcher matcher = SPECIAL_SYMBOL_PATTERN.matcher(raw);
        if (!matcher.matches()) {
            return raw;
        }

        for (String symbol : SPECIAL_SYMBOL_ARR) {
            if (raw.contains(symbol)) {
                raw = raw.replace(symbol, "\\" + symbol);
            }
        }
        return raw;
    }
}
