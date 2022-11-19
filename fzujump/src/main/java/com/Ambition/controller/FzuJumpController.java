package com.Ambition.controller;

import com.Ambition.service.FzuJumpService;
import com.Ambition.utils.ResultData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FzuJumpController {

    @Resource
    private FzuJumpService fzuJumpService;

    @RequestMapping("/conversion")
    public ResultData conversion(String number){
        ResultData resultData = new ResultData();
        //判断错误
        int i = fzuJumpService.CheckError(number);
        //错误
        if(i == -1){
            resultData.setCode(300);
            resultData.setMsg("格式错误");
        }
        //全中文转数字
        else if(i == 1){
            String s = fzuJumpService.ChineseConvertToNumber(number);
            resultData.setCode(200);
            resultData.setMsg("转换成功");
            resultData.setData(s);
        }
        //全数字转中文
        else if(i == 0){
            long num = Long.parseLong(number);
            String s = fzuJumpService.NumIntoChinese(num);
            resultData.setCode(200);
            resultData.setMsg("转换成功");
            resultData.setData(s);
        }
        return resultData;
    }
}
