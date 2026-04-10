package com.km.wiki.common;

/**
 * 通用响应结果封装类
 * 用于统一API返回格式
 *
 * @param <T> 响应数据类型
 */
public class Result<T> {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 私有构造函数
     */
    private Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 私有构造函数
     *
     * @param code    响应码
     * @param message 响应消息
     * @param data    响应数据
     */
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功响应（带数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功响应（带消息和数据）
     *
     * @param message 响应消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 失败响应（带默认错误消息）
     *
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> error() {
        return new Result<>(500, "操作失败", null);
    }

    /**
     * 失败响应（带消息）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * 失败响应（带错误码和消息）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 参数校验失败
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> validateFailed(String message) {
        return new Result<>(400, message, null);
    }

    /**
     * 未授权
     *
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> unauthorized() {
        return new Result<>(401, "未授权，请先登录", null);
    }

    /**
     * 未授权（带消息）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(401, message, null);
    }

    /**
     * 禁止访问
     *
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> forbidden() {
        return new Result<>(403, "禁止访问", null);
    }

    /**
     * 资源未找到
     *
     * @param <T> 数据类型
     * @return Result对象
     */
    public static <T> Result<T> notFound() {
        return new Result<>(404, "资源未找到", null);
    }

    /**
     * 资源未找到（带消息）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return Result对象
     */
    public static <T> Result<T> notFound(String message) {
        return new Result<>(404, message, null);
    }

    // Getters and Setters
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
