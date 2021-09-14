package com.tencent.nft.entity.nft.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

//作用: 实现前后端交互
@Data
@Accessors(chain = true)
@NoArgsConstructor  //无参构造
@AllArgsConstructor //全参构造
public class SysResult implements Serializable { //规范的写法
    private boolean success;  //状态
    private Integer code;  //状态码  200 201
    private String message;      //服务器返回的提示信息
    private Object data;     //服务器返回的业务数据
    private String timestamp; //返回的时间

    //重载:  方法名称相同,参数不同
    //为了用户使用VO对象 更加的方便 重载一些方法 简化程序的调用
    public static SysResult fail(){
        return new SysResult(false,1, "业务执行失败",null,"");
    }
    //1.不带参数的正确返回
    public static SysResult success(){
        return new SysResult(true,0, "服务器处理成功", null,"");
    }

    //2.带返回值的正确返回  用户传递什么/返回值就是什么 String token!!!
    public static SysResult success(Object data){
        return new SysResult(true,0, "服务器处理成功", data,"");
    }

    //3.带返回值,携带提示信息
    public static SysResult success(String message,Object data){
        return new SysResult(true,0, message,data,"");
    }

    //4.这样的写不对的!!! 重载方法 参数不要耦合!!!
   /* public static SysResult success(String msg){
        return new SysResult(200,msg,null);
    }*/
}
