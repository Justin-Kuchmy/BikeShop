package com.justinkuchmy.order.Config;

public class RedisOperationException extends RuntimeException {
    public RedisOperationException(String message) {
        super(message);
    }

    public RedisOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
