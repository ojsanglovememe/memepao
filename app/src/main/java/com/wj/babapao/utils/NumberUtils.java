/*
 * Copyright (c) 2014. kugou.com
 */

package com.wj.babapao.utils;

import android.text.TextUtils;


import com.wj.babapao.base.BaseApp;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * Created by LeonLee on 2014/9/4 17:53.<br>
 * <p><a href="mailto:liwenlong1@kugou.net">Email:liwenlong1@kugou.net</a></p>
 * <p/>
 * 对数字的处理
 * <p/>
 * <ul>
 * <li>{@link #string2int(String)} 字符串转换为int类型</li>
 * <li>{@link #string2long(String)} 字符串转换为long类型</li>
 * <li>{@link #getRandom(int, int)} 获取一个指定范围的随机数</li>
 * </ul>
 */
public class NumberUtils {
    private NumberUtils() {
    }

    /**
     * 字符串转换为int类型
     *
     * @param src 要转换的字符串
     */
    public static int string2int(String src) {
        return string2int(src, 0);
    }

    public static int dp2px(final float dpValue) {
        final float scale = BaseApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 字符串转换为int类型
     *
     * @param src 要转换的字符串
     */
    public static int string2int(String src, int defValue) {
        if (TextUtils.isEmpty(src)) {
            return defValue;
        }
        try {
            return Integer.parseInt(src);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 字符串转换为long类型
     *
     * @param src 要转换的字符串
     */
    public static long string2long(String src) {
        if (TextUtils.isEmpty(src)) {
            return 0;
        }
        try {
            return Long.parseLong(src);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long string2long(String src, long defValue) {
        if (TextUtils.isEmpty(src)) {
            return defValue;
        }
        try {
            return Long.parseLong(src);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static double string2Double(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static String formatDoubleStr(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    /**
     * 获取一个指定范围的随机数
     *
     * @param min 随机数最小值
     * @param max 随机数最大值
     */
    public static int getRandom(int min, int max) {
        int remainder = Math.abs(max - min);
        int realMin = (min <= max) ? min : max;
        Random random = new Random(System.currentTimeMillis());
        return realMin + random.nextInt(remainder);
    }

    public static int getIntRandom(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }


    /**
     * double数字格式化，不显示成科学计数法
     *
     * @param num
     * @return
     */
    public static String doubleFormat(double num) {
        //为了防止java的double的大数值时 不显示成科学计数法，使用NumberFormat
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);//默认每个三位会有个逗号隔开，这里不需要这样处理，设置为false
        return nf.format(num);
    }

    /**
     * 格式化数字
     *
     * @param nums
     * @return
     */
//    public static String getListenNumsFormat(double nums) {
//        String OnlineNums = nums + "";
//        if (nums >= 10000) {
//            OnlineNums = String.valueOf(nums);
//            OnlineNums = OnlineNums.substring(0, OnlineNums.length() - 3);
//            OnlineNums = OnlineNums.substring(0, OnlineNums.length() - 1) + "."
//                    + OnlineNums.substring(OnlineNums.length() - 1);
//            return OnlineNums + "万";
//        }
//        return OnlineNums;
//    }
//
//    public static String getNumberFormatWan(double nums) {
//        if (nums > 10000 * 100) {
//            double f = nums / 10000.0 * 100.0;
//            BigDecimal bg = new BigDecimal(f);
//            f = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            return String.valueOf(f + "百万");
//        }
//        if (nums > 10000) {
//            double f = nums / 10000.0;
//            // DecimalFormat df = new DecimalFormat("0.00");
//            // df.format(f);
//            BigDecimal bg = new BigDecimal(f);
//            f = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            return String.valueOf(f + "万");
//        } else {
//            return String.valueOf(nums);
//        }
//    }
//
//    public static String formatNumberToWanWithOnePrecision(int num) {
//        if (num >= 10000 && num < 1000000) {
//            return String.format("%.1f万", num * 1f / 10000).replace(".0", "");
//        } else if (num >= 1000000) {
//            return String.format("%.1f百万", num * 1f / 1000000).replace(".0", "");
//        } else {
//            return num + "";
//        }
//    }

    /**
     * 当少于10万时，显示具体的数字，大于10万时，显示格式X.Y万，保留一位小数，其中Y为四舍五入
     * 省略".0"
     *
     * @param number
     * @return x.y
     */
//    public static String formatHundredThousand(long number) {
//        final int basic = 100000;
//        if (number < basic) {
//            return String.valueOf(number);
//        }
//
//        return new DecimalFormat("#.#").format(number / basic) + "万";
//    }


    /**
     * 格式化double数据
     *
     * @param num
     * @param pattern 格式 默认"###0.00"
     * @param mode    默认四舍五入
     * @return
     */
    public static String formatDoubleNumberToString(double num, String pattern, RoundingMode mode) {
        if (TextUtils.isEmpty(pattern)) {
            pattern = "###0.00";
        }
        if (mode == null) {
            mode = RoundingMode.HALF_UP;
        }
        NumberFormat numberFormat = new DecimalFormat(pattern);
//        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
        numberFormat.setRoundingMode(mode);
        return numberFormat.format(num);
    }

    /**
     * 格式化星星数量<br/>万以下显示具体数字,万以上显示X.X万
     *
     * @param num 星星数量
     * @return
     */
    public static String formatStarNum(long num) {
        if (num < 10000)
            return String.valueOf(num);
        if (num > 10000000) {
            double dNum = (double) num / 10000000;
            NumberFormat numberFormat = new DecimalFormat("###0.0");
            numberFormat.setGroupingUsed(false);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            return numberFormat.format(dNum) + "千万";
        } else if (num >= 10000) {
            double dNum = (double) num / 10000;
            NumberFormat numberFormat = new DecimalFormat("###0.0");
            numberFormat.setGroupingUsed(false);
            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
            return CovertFloat(numberFormat.format(dNum)) + "万";
        }
        return "0";
    }

    /**
     * 去掉小数点后最后一位的0
     *
     * @return
     */
    public static String CovertFloat(String consumeMoney) {

        if (consumeMoney.contains(".")) {
            String str = consumeMoney.substring(consumeMoney.length() - 1);
            if (str.equals("0")) {
                return CovertFloat(consumeMoney.substring(0, consumeMoney.length() - 1));
            } else if (str.equals(".")) {
                return consumeMoney.substring(0, consumeMoney.length() - 1);
            }
        }
        return consumeMoney;
    }

    /**
     * 格式化数字，大于1千，显示x.x千，大于1万显示x.x万
     *
     * @param num
     * @return
     */
    public static String formatNumAboveK(int num) {

        if (num >= 0 && num < 1000) {
            return String.valueOf(num);
        }

        NumberFormat numberFormat = new DecimalFormat("###0.0");
        numberFormat.setGroupingUsed(false);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);

        if (num >= 1000 && num < 10000) {
            double dNum = (double) num / 1000;
            String value = numberFormat.format(dNum);
            if (value.equals("10.0"))
                return "1.0万";
            return value + "千";
        }

        if (num >= 10000) {
            double dNum = (double) num / 10000;
            return numberFormat.format(dNum) + "万";
        }

        return "0";
    }


    /*
     * 格式化关注数量
     * @param num
     * @return
     */
//    public static String formatViwerNum(int num) {
//        if (num < 10000)
//            return String.valueOf(num) + "人";
//        if (num >= 10000000) {
//            double dNum = (double) num / 10000000;
//            NumberFormat numberFormat = new DecimalFormat("###0.0");
//            numberFormat.setGroupingUsed(false);
//            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
//            return numberFormat.format(dNum) + "千万人";
//        } else if (num >= 10000) {
//            double dNum = (double) num / 10000;
//            NumberFormat numberFormat = new DecimalFormat("###0.0");
//            numberFormat.setGroupingUsed(false);
//            numberFormat.setRoundingMode(RoundingMode.HALF_UP);
//            return numberFormat.format(dNum) + "万人";
//        }
//        return "0";
//    }

//    public static String formatNumberToWan(int num) {
//        if (num < 10000) {
//            return num + "";
//        } else if (num < 100000) {
//            return (Math.round(num / 1000.0) / 10.0 + "万");
//        } else {
//            return (Math.round(num / 10000.0) + "万").replace(".0", "");
//        }
//    }

    /**
     * @param len    需要显示的长度(<font color="red">注意：长度是以byte为单位的，一个汉字是2个byte</font>)
     * @param symbol 用于表示省略的信息的字符，如“...”,“>>>”等。
     * @return 返回处理后的字符串
     */
    public static String getLimitLengthString(String src, int len, String symbol) throws UnsupportedEncodingException {
        int counterOfDoubleByte;
        byte b[];

        counterOfDoubleByte = 0;
        b = src.getBytes("GBK");
        if (b.length <= len)
            return src;
        for (int i = 0; i < len; i++) {
            if (b[i] < 0)
                counterOfDoubleByte++;
        }

        if (counterOfDoubleByte % 2 == 0)
            return new String(b, 0, len, "GBK") + symbol;
        else
            return new String(b, 0, len - 1, "GBK") + symbol;
    }

    /**
     * 秒转换分、时、天
     * 0<seconds<60当一分钟
     * seconds<=0 反馈空
     *
     * @param seconds
     * @return
     */
    public static String secondsFormat(long seconds) {
        if (seconds <= 0) {
            return "";
        }
        int minute = (int) Math.ceil(seconds / 60.0f);
        if (minute < 60) {
            return minute + "分钟";
        }

        StringBuilder result = new StringBuilder();
        int hour = minute / 60;
        int remainMinute = minute % 60;
        if (hour < 24) {
            result.append(hour);
            result.append("小时");
            if (remainMinute > 0) {
                result.append(remainMinute);
                result.append("分钟");
            }
            return result.toString();
        }

        int day = hour / 24;
        int remainHour = hour % 24;
        result.append(day);
        result.append("天");
        if (remainHour > 0) {
            result.append(remainHour);
            result.append("小时");
        }
        if (remainMinute > 0) {
            result.append(remainMinute);
            result.append("分钟");
        }
        return result.toString();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static String formatThreeNumStr(String num) {
        return formatThreeNumStr(string2long(num));
    }

    public static String formatThreeNumStr(long num) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }

    public static int formatMillisecond(int second) {
        return second * 1000;
    }

    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     *
     * @param c
     * @return
     */
    public static long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }
}
