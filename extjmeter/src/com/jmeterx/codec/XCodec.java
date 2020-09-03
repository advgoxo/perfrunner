package com.jmeterx.codec;

public class XCodec {
    /*
     * 中文转unicode编码
     */
    public static String encodeUnicode(final String data) {
        char[] utfBytes = data.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }
    /*
     * unicode编码转中文
     */
    public static String decodeUnicode(final String data) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = data.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = data.substring(start + 2, data.length());
            } else {
                charStr = data.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }
}