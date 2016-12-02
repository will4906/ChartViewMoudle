package com.example.will.chartviewlib.Common;

import android.graphics.Bitmap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static java.math.BigDecimal.ROUND_CEILING;
import static java.math.BigDecimal.ROUND_FLOOR;

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

    /**
     * 精确乘法
     * @param one
     * @param second
     * @return
     */
    public static float accurateMultiply(float one, float second){
        BigDecimal oneBig = new BigDecimal(String.valueOf(one));
        BigDecimal secondBig = new BigDecimal(String.valueOf(second));
        return Float.valueOf(oneBig.multiply(secondBig).toString());
    }
    /**
     * 精确除法，前提是被除数和除数是精确的，不管对不对，先这么搞了，不稳定先废除
     * @param dividend
     * @param divisor
     * @return
     */
//    public static float accurateDivide(float dividend, float divisor){
//        BigDecimal dividendBig = new BigDecimal(String.valueOf(dividend));
//        BigDecimal divisorBig = new BigDecimal(String.valueOf(divisor));
//        if (divisor == 0){
//            return 0;
//        }
//        return Float.valueOf(dividendBig.divide(divisorBig,ROUND_FLOOR).toString());
//    }

    /**
     * 精确加法，前提是两个加数都是精确地
     * @param param1
     * @param param2
     * @return
     */
    public static float accurateAdd(float param1, float param2){
        BigDecimal oneBig = new BigDecimal(String.valueOf(param1));
        BigDecimal secondBig = new BigDecimal(String.valueOf(param2));
        return Float.valueOf(oneBig.add(secondBig).toString());
    }

    /**
     * 精确减法，前提是被减数和减数都是精确的
     * @param Minuend
     * @param Meiosis
     * @return
     */
    public static float accurateMinus(float Minuend, float Meiosis){
        BigDecimal minuendBig = new BigDecimal(String.valueOf(Minuend));
        BigDecimal meiosisBig = new BigDecimal(String.valueOf(Meiosis));
        return Float.valueOf(minuendBig.subtract(meiosisBig).toString());
    }
    /**
     * 四舍五入，保留设定的小数位
     * @param pointAfter
     * @param data
     * @return
     */
    public static float Rounding(int pointAfter, float data){
        String strData = String.valueOf(data);
        BigDecimal   bigData   =   new   BigDecimal(strData);
        return Float.valueOf(bigData.setScale(pointAfter, RoundingMode.HALF_UP).toString());
    }

}
