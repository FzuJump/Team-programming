import java.util.*;
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
