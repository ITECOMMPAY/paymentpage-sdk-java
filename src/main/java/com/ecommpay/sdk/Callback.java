package com.ecommpay.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for processing our callbacks
 * Has constants like STATUS_* - possible statuses of payment which we got
 */
public class Callback
{
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_DECLINE = "decline";
    public static final String STATUS_AW_3DS = "awaiting 3ds result";
    public static final String STATUS_AW_REDIRECT = "awaiting redirect result";
    public static final String STATUS_AW_CUSTOMER = "awaiting customer";
    public static final String STATUS_AW_CLARIFICATION = "awaiting clarification";
    public static final String STATUS_AW_CAPTURE = "awaiting capture";
    public static final String STATUS_CANCELLED = "cancelled";
    public static final String STATUS_REFUNDED = "refunded";
    public static final String STATUS_PARTIALLY_REFUNDED = "partially refunded";
    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_REVERSED = "reversed";

    /**
     * Decoded callback data
     */
    private HashMap<String, Object> data;

    /**
     * com.ecommpay.sdk.SignatureHandler instance for check callback signature
     */
    private SignatureHandler signatureHandler;

    /**
     * com.ecommpay.sdk.Callback signature
     */
    private String signature;

    /**
     * com.ecommpay.sdk.Callback constructor decode callback data and check signature
     * @param callbackData raw callback data
     * @param signHandler signatureHandler for check callback signature
     * @throws ProcessException throws when signature is incorrect
     */
    public Callback(String callbackData, SignatureHandler signHandler) throws ProcessException {
        decodeResponse(callbackData);

        signatureHandler = signHandler;

        if (!checkSignature()) {
            throw new ProcessException();
        }
    }

    /**
     * @return Map with payment data
     */
    public Map getPayment() {
        return (HashMap) getValueByName("payment", data);
    }

    /**
     * @return payment status
     */
    public String getPaymentStatus() {
        return getValueByName("status", getPayment()).toString();
    }

    /**
     * @return our payment id
     */
    public String getPaymentId() {
        return getValueByName("id", getPayment()).toString();
    }

    /**
     * @return callback signature
     */
    public String getSignature() {
        if (signature == null) {
            signature = getValueByName("signature", data).toString();
        }

        return signature;
    }

    /**
     * @return that signature valid or not valid
     */
    public boolean checkSignature() {
        String signature = getSignature();
        removeParam("signature", data);

        return signatureHandler.check(signature, data);
    }

    /**
     * Method for get value in multilevel map by key
     * @param name key for searching
     * @param data map with callback data
     * @return value or empty string
     */
    private Object getValueByName(String name, Map<String, Object> data) {
        Object value = data.get(name);

        if (value != null) {
            return value;
        }

        for(Map.Entry<String, Object> entry : data.entrySet()) {
            Object entryValue = entry.getValue();

            if (entryValue instanceof Map) {
                Object subValue = getValueByName(name, (HashMap) entryValue);

                if (subValue != "") {
                    return subValue;
                }
            }
        }

        return "";
    }

    /**
     * Method for remove value in multilevel map
     * @param name key for searching
     * @param data map with callback data
     */
    private void removeParam(String name, Map<String, Object> data) {
        Object value = data.get(name);

        if (value != null) {
            data.remove(name);
            return;
        }

        for(Map.Entry<String, Object> entry : data.entrySet()) {
            Object entryValue = entry.getValue();

            if (entryValue instanceof Map) {
                removeParam(name, (HashMap) entryValue);
            }
        }
    }

    /**
     * Method for decode callback data
     * @param callbackData raw data in JSON format
     * @throws ProcessException throws when can't decode
     */
    private void decodeResponse(String callbackData) throws ProcessException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            data = mapper.readValue(callbackData, new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            throw new ProcessException(e);
        }
    }
}
