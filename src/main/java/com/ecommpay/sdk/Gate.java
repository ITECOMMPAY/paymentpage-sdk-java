package com.ecommpay.sdk;

/**
 * Class for communicate with our
 */
public class Gate
{
    /**
     * com.ecommpay.sdk.SignatureHandler instance for check signature
     */
    private SignatureHandler signatureHandler;

    /**
     * com.ecommpay.sdk.PaymentPage instance for build payment URL
     */
    private PaymentPage paymentPageUrlBuilder;

    /**
     * com.ecommpay.sdk.Gate constructor
     * @param secret site salt
     */
    public Gate(String secret) {
        signatureHandler = new SignatureHandler(secret);
        paymentPageUrlBuilder = new PaymentPage(signatureHandler);
    }

    /**
     * Method for set base payment page URL
     * @param url payment page URL
     * @return self for fluent interface
     */
    public Gate setBaseUrl(String url) {
        paymentPageUrlBuilder.setBaseUrl(url);

        return this;
    }

    /**
     * Method build payment URL
     * @param payment com.ecommpay.sdk.Payment instance with payment params
     * @return string URL that you can use for redirect on payment page
     */
    public String getPurchasePaymentPageUrl(Payment payment) {
        return paymentPageUrlBuilder.getUrl(payment);
    }

    /**
     * Method for handling callback
     * @param data raw callback data in JSON format
     * @return com.ecommpay.sdk.Callback instance
     * @throws ProcessException throws when signature is invalid
     */
    public Callback handleCallback(String data) throws ProcessException {
        return new Callback(data, signatureHandler);
    }
}
