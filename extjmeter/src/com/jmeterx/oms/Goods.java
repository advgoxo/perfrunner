package com.jmeterx.oms;

public class Goods {

    public static String SizeIdBeg = "'size');\\\">";
    public static String ColorIdBeg = "'color');\\\">";
    public static String OptionBeg = "<option";
    public static String ValueBeg = "value=";
    public static String OptionEnd = "<\\/option>";
    public static String SelectEnd = "<\\/select>";

    public static String trimFirstAndLastChar(String str, String element){
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do{
            int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
            int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
            str = str.substring(beginIndex, endIndex);
            beginIndexFlag = (str.indexOf(element) == 0);
            endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
        } while (beginIndexFlag || endIndexFlag);
        return str;
    }

    public static String getSizeId(final String data) {
        return getOptionValue(SizeIdBeg, data);
    }

    public static String getColorId(final String data) {
        return getOptionValue(ColorIdBeg, data);
    }

    public static String getOptionV(final String option) {
        int begv = option.indexOf(ValueBeg);
        if (begv < 0) {
            return "";
        }

        begv = begv + ValueBeg.length();
        int endv = option.indexOf(" ",begv);
        if (endv < 0) {
            return "";
        }

        String value = option.substring(begv, endv);
        value = trimFirstAndLastChar(value, "\\");
        value = trimFirstAndLastChar(value, "\"");
        value = trimFirstAndLastChar(value, "\\");
        return value;
    }

    public static String getOptionValue(final String prefix, final String data) {
        int beg = data.indexOf(prefix);
        if (beg < 0) {
            return "";
        }

        int end = data.indexOf(SelectEnd,beg);
        if (end < 0) {
            return "";
        }

        String value = "";
        String selectOption = data.substring(beg, end);
        for(beg = 0; beg >= 0; beg = end) {
            beg = selectOption.indexOf("<option ", beg);
            if (beg < 0 ) {
                continue;
            }

            end = selectOption.indexOf(">", beg);
            if (end < 0) {
                continue;
            }

            String option = selectOption.substring(beg, end);
            if (option.length() < 0) {
                continue;
            }

            value = getOptionV(option);
            if (value.length() <= 0) {
                continue;
            }

            if(option.indexOf("selected") >= 0) {
                return value;
            }
        }

        return value;
    }
}
