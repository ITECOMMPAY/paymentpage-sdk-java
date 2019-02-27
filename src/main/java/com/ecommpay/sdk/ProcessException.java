package com.ecommpay.sdk;

/**
 * Exception that throws when has some errors in payment flow
 */
public class ProcessException extends Exception
{
    /**
     * Can be without message
     */
    public ProcessException() {
    }

    /**
     * Can be with message
     * @param var1 exception message
     */
    public ProcessException(Throwable var1) {
        super(var1);
    }
}
