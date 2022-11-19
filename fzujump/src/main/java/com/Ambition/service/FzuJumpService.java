package com.Ambition.service;

import java.util.HashMap;
import java.util.Map;

public interface FzuJumpService {
    int CheckError(String str);

    String ChineseConvertToNumber(String chineseAmount);

    int ConvertNameToSmall(char chinese);

    String NumIntoChinese(long x);
}
