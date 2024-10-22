package com.caicaigroup.hnuzslq_system.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交互结果封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    /**
     * 默认成功
     * @return
     */
    public static Result success() {
        return new Result(Constants.CODE_200, "", null);
    }

    /**
     * 成功返回数据
     * @param data 数据
     * @return
     */
    public static Result success(Object data) {
        return new Result(Constants.CODE_200, "", data);
    }

    /**
     * 错误返回状态码与消息
     * @param code 状态码
     * @param msg 消息
     * @return
     */
    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    /**
     * 默认错误
     * @return
     */
    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null);
    }

}
