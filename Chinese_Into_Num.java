import java.math.*;
public class Chinese_Into_Num {
    public static String ChineseConvertToNumber(String chineseAmount) {
        if (chineseAmount == null || chineseAmount.length() <= 0 || chineseAmount == "") {
            return null;
        }

        chineseAmount = chineseAmount
                .replace("元", "")
                .replace("整", "");

        char[] wordCharArray = chineseAmount.toCharArray();

        BigDecimal numberAmount = BigDecimal.ZERO;

        boolean yuan = false;
        //表示有十位
        boolean shi = false;
        //表示有百位
        boolean bai = false;
        //表示有千位
        boolean qian = false;
        //表示有万位
        boolean wan = false;
        //表示有亿位
        boolean yi = false;
        for (int i = (wordCharArray.length - 1); i >= 0; i--) {
            //当前位金额值
            BigDecimal currentPlaceAmount = BigDecimal.ZERO;

            //判断当前位对应金额标志量
            if (wordCharArray[i] == '元') {
                yuan = true;
                continue;
            } else if (wordCharArray[i] == '拾') {
                shi = true;
                continue;
            } else if (wordCharArray[i] == '佰') {
                bai = true;
                continue;
            } else if (wordCharArray[i] == '仟') {
                qian = true;
                continue;
            } else if (wordCharArray[i] == '万') {
                wan = true;
                continue;
            } else if (wordCharArray[i] == '亿') {
                yi = true;
                continue;
            }

            //根据标志量转换金额为实际金额
            double t = 0;
            if (shi) {
                t = ConvertNameToSmall(wordCharArray[i]) * 10;
            } else if (bai) {
                t = ConvertNameToSmall(wordCharArray[i]) * 100;
            } else if (qian) {
                t = ConvertNameToSmall(wordCharArray[i]) * 1000;
            } else {
                t = ConvertNameToSmall(wordCharArray[i]);
            }
            currentPlaceAmount = new BigDecimal(t);
            if (yi) {
                currentPlaceAmount = currentPlaceAmount.multiply(new BigDecimal(100000000));
            } else if (wan) {
                currentPlaceAmount = currentPlaceAmount.multiply(new BigDecimal(10000));
            }
            numberAmount = numberAmount.add(currentPlaceAmount);
            shi = false;
            bai = false;
            qian = false;
            yuan = false;
        }
        return numberAmount.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    }


    private static int ConvertNameToSmall(char chinese) {
        int number = 0;
        String s = String.valueOf(chinese);
        switch (s) {
            case "零":
                number = 0;
                break;
            case "壹":
                number = 1;
                break;
            case "贰":
                number = 2;
                break;
            case "叁":
                number = 3;
                break;
            case "肆":
                number = 4;
                break;
            case "伍":
                number = 5;
                break;
            case "陆":
                number = 6;
                break;
            case "柒":
                number = 7;
                break;
            case "捌":
                number = 8;
                break;
            case "玖":
                number = 9;
                break;
        }
        return number;
    }

}
