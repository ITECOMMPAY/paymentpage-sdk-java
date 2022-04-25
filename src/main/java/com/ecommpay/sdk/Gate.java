package com.ecommpay.sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;

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
     * Payment params validation domain with path to API
     */
    private String validationUrl = "https://sdk.ecommpay.com/v1/params/check";

    /**
     * Enable (true) or disable (false) payment params validation
     */
    private Boolean validatorEnabled = true;


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
    public String getPurchasePaymentPageUrl(Payment payment) throws IOException, URISyntaxException {
        if (this.validatorEnabled) {
            this.Validate(payment);
        }

        return paymentPageUrlBuilder.getUrl(payment);
    }

    /**
     * Method for validate payment params
     * @param payment com.ecommpay.sdk.Payment instance with payment params
     * @throws IOException
     * @throws URISyntaxException
     */
    private void Validate(Payment payment) throws IOException, URISyntaxException {
        String paramsUrl = this.validationUrl + "?" + payment.toString();
        URL url = new URI(paramsUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new Error(String.valueOf(parseResponseBody(connection.getErrorStream())));
        }
    }

    /**
     * Method for handle and return response body as array
     * @param connect connection with response body json
     * @return Array contains the response body strings from json
     * @throws IOException
     */
    private ArrayList<String> parseResponseBody(InputStream connect) throws IOException {
        BufferedReader br = null;
        String strRespCurrentLine;
        ArrayList<String> responseBody = new ArrayList<String>();

        br = new BufferedReader(new InputStreamReader(connect));
        while ((strRespCurrentLine = br.readLine()) != null) {
            responseBody.add(strRespCurrentLine);
        }

        return responseBody;
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
