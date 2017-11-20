package com.github.lany192.pinyin;

import android.text.TextUtils;
import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingYinUtil {
    private final static String TAG = "PingYinUtil";

    public static boolean isHanZi(char c) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher = pattern.matcher(String.valueOf(c));
        return matcher.matches();
    }

    public static boolean isEnglish(char c) {
        return String.valueOf(c).matches("^[a-zA-Z]*");
    }

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     */
    public static String getPingYin(String inputString) {
        if (TextUtils.isEmpty(inputString)) {
            return "";
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";
        try {
            for (int i = 0; i < input.length; i++) {
                if (isHanZi(input[i])) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    if (temp != null) {
                        output += temp[0];
                    }
                } else {
                    output += Character.toString(input[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "getPingYin: " + e.getMessage());
        }
        return output;
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     *
     * @param chinese 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chinese) {
        if (TextUtils.isEmpty(chinese)) {
            return "";
        }
        String pinyinName = "";
        char[] nameChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        if (TextUtils.isEmpty(chinese)) {
            return "";
        }
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }
}