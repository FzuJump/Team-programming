package com.Ambition.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData<T> {
    private int code; //响应状态
    private String msg; //返回信息
    private T data; //返回数据对象
}
