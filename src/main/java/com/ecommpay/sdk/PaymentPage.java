package com.ecommpay.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.stream.Collectors;

/**
 * Class for build payment URL
 */
public class PaymentPage
{
    /**
     * payment domain with path
     */
    private String baseUrl = "https://paymentpage.ecommpay.com/payment";

    /**
     * Signature handler for generate signature
     */
    private SignatureHandler signatureHandler;

    /**
     * com.ecommpay.sdk.PaymentPage constructor
     * @param signHandler signature handler for generate signature
     */
    public PaymentPage(SignatureHandler signHandler) {
        signatureHandler = signHandler;
    }

    /**
     * Method for set base payment page URL
     * @param url
     * @return
     */
    public PaymentPage setBaseUrl(String url) {
        baseUrl = url;

        return this;
    }

    /**
     * Method build payment URL
     * @param payment com.ecommpay.sdk.Payment instance with payment params
     * @return string URL that you can use for redirect on payment page
     */
    public String getUrl(Payment payment) {
        payment.setParam("signature", signatureHandler.sign(payment.getParams()));

        return
            baseUrl
                .concat("?")
                .concat(payment.toString());

    }


}
