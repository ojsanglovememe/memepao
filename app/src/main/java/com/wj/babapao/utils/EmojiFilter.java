package com.wj.babapao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiFilter {

    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        isEmoji = true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    isEmoji = true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    isEmoji = true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    isEmoji = true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    isEmoji = true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    isEmoji = true;
                }

                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        isEmoji = true;
                    }
                }
            }
        }
        return isEmoji;
    }



    public static String filterEmoji(String inputString) {
        if (inputString == null) {
            return null;
        }
        String reg = "☎|\uD83D\uDD7F|✆|℡|\uD83D\uDCDE|\uD83D\uDCF1|\uD83D\uDD7B|\uD83D\uDD7D|☏|\uD83D\uDD80|\uD83D\uDCF2|\uE08E|U+005cU+006e|U+005cU+0074|\uE08E|\uE00A|";
        //表里配置的 U+ 形式的正则，先转换成 \ u 真正的正则
        reg = reg.replaceAll("U\\+", "\\\\u");
        Pattern emoji = Pattern.compile(reg, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(inputString);
        if (emojiMatcher.find()) {
            //将所获取的表情转换为*
            inputString = emojiMatcher.replaceAll("*");
            return inputString;
        }
        return inputString;
    }
}