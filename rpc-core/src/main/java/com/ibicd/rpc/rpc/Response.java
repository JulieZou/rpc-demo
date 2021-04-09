package com.ibicd.rpc.rpc;

/**
 * @ClassName Response
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:25
 * @Version 1.0
 */
public class Response {

    /**
     * 对应请求中携带的messageId
     */
    private long requestId;

    /**
     * 99 - 异常
     * 0  - 正常
     */
    private int status;

    private Object content;// 响应内容，方法的执行结果

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Response{" +
                "requestId=" + requestId +
                ", status=" + status +
                ", content=" + content +
                '}';
    }
}
