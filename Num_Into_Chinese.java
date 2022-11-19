import org.w3c.dom.ls.LSInput;
import java.util.Scanner;
public class  Num_Into_Chinese{
    public static String NumIntoChinese(long x) {
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
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long t = scan.nextLong();
        NumIntoChinese(t);
    }
}
