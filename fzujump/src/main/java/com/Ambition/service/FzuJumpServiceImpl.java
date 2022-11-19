package com.Ambition.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class FzuJumpServiceImpl implements FzuJumpService{

    public int CheckError(String str){
        if(str.charAt(0) == '0') {
            return -1;
        }

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        char Chinese_Bits[] = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
        char Other[] = {'拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟', '元', '整'};

        for(int i = 0; i < 10; ++i){
            map.put(Chinese_Bits[i], 1);
        }
        for(int i = 0 ; i <= 12; ++i){
            map.put(Other[i], 1);
        }
        int []a = {0, 0, 0};
        for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (map.containsKey(c)) {
                a[1] = 1;//1判断是中文
            } else if (Character.isDigit(c)) {
                a[2] = 1;
            } else a[0] = 1;
        }
        if(a[0] == 1 || (a[1] == 1 && a[2] == 1)){
            return -1;
        }
        if(a[1] == 1){
            if(str.length() > 25){
                return -1;
            }
            String ans1 = ChineseConvertToNumber(str);
            String str1 = NumIntoChinese(Long.parseLong(ans1));
            if(str1.equals(str)){
                return 1;
            }else{
                return -1;
            }
        }
        if(a[2] == 1){//全数字
            if(str.length() > 12){
                return -1;
            }
            long num = Long.parseLong(str);
            String str1 = NumIntoChinese(num);//全数字转中文
            String ans1 = ChineseConvertToNumber(str1);
            long num1 = Long.parseLong(ans1);
            if(num == num1){
                return 0;
            }else{
                return -1;
            }
        }
        return -1;
    }

    /**
     * 大写金额转数字
     *
     */
    public String ChineseConvertToNumber(String chineseAmount) {
        if (chineseAmount == null || chineseAmount.length() <= 0 || chineseAmount == "") {
            return null;
        }
        //移除计算干扰文字
        chineseAmount = chineseAmount
                .replace("元", "")
                .replace("整", "");

        // 字符切割
        char[] wordCharArray = chineseAmount.toCharArray();

        //最终要返回的数字金额
        BigDecimal numberAmount = BigDecimal.ZERO;

        //金额位标志量
        // 表示个位不为0
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

        //从低位到高位计算
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
            //每万位处理
            if (yi) {
                currentPlaceAmount = currentPlaceAmount.multiply(new BigDecimal(100000000));
            } else if (wan) {
                currentPlaceAmount = currentPlaceAmount.multiply(new BigDecimal(10000));
            }
            numberAmount = numberAmount.add(currentPlaceAmount);
            // 重置状态
            shi = false;
            bai = false;
            qian = false;
            yuan = false;
        }
        return numberAmount.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 转换中文数字为阿拉伯数字
     *
     * @param chinese
     * @return
     * @throws Exception
     */
    public int ConvertNameToSmall(char chinese) {
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

    public String NumIntoChinese(long x) {
        final String Chinese_Bits[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        final String Other[] = {"", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟"};
        String ans = "";
        int Count = 0;
        while(x > 0) {
            int t = (int)(x % 10);
            ans = (Chinese_Bits[t] + Other[Count]) + ans;
            x = x / 10;
            Count++;
        }
        ans = ans + "元整";
        ans = ans.replaceAll("零[万仟佰拾]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零$", "");
        return ans;
    }

    public void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long t = scan.nextLong();
        NumIntoChinese(t);
    }
}
