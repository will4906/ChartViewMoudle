package com.example.will.chartviewlib.Common;

import java.text.DecimalFormat;

/**
 * 此类用来处理和浮点型有关的数据
 * @author will4906.
 * @Time 2016/12/1.
 */

public class FloatTool {

    /**
     * 获取小数点后几位
     * @param data 需要获取的数据
     * @return 小数点后的长度
     */
    public static int getPointAfter(float data){
        int len = 0;
        String strData = data + "";
        if (strData.indexOf("E") == -1) {
            len = strData.length() - strData.indexOf(".") - 1;
            if (len == 1) {
                String newStr = strData.substring(strData.length() - 1);
                if (newStr.equals("0")) {
                    len = 0;
                }
            }
        } else {                        //针对被转换为带E的情况
            String[] strArr = strData.split("E");
            String[] strLeft = strArr[0].split("\\.");
            strLeft[0] = strLeft[0].replace("-","");
            int rightLen;
            if (strLeft[1].equals("0")){
                rightLen = 0;
            } else {
                rightLen = strLeft[1].length();
            }
            strArr = strArr[1].split("-");
            len = Integer.valueOf(strArr[1]);
            len = len + rightLen;
        }
        return len;
    }

    /**
     * 保留小数点后几位，并返回字符串
     * @param len 保留几位
     * @return 处理后的字符串
     */
    public static String getFormatPointAfterString(float data, int len){
        String strData;
        String strFormat = "#0";
        for (int i = 0; i < len ; i ++){
            if (i == 0){
                strFormat += ".";
            }
            strFormat += "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat(strFormat);
        strData = decimalFormat.format(data);
        return strData;
    }
}
