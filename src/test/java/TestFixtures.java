import com.ecommpay.sdk.Payment;
import com.ecommpay.sdk.SignatureHandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestFixtures {
    public static String secret = "qwerty";
    public static String paymentStatus = "success";
    public static String paymentId = "test.test-payment";
    public static String paymentSignature = "OonQm2ElTzZU80dNnsrS/ulnGU/eEMg7U1jfLRiKn0zx3GmnEOP2ne/pUOJ9LkzntYT7llgRh" +
            "TpbxcT3ldHyEA==";
    public static String testUrl = "https://test.test.test.test/payment";
    public static String baseUrl = "https://paymentpage.ecommpay.com/payment";
    public static String compareParams = "?project_id=123&payment_id=test_paymen" +
            "t&account_token=qwerty&interface_type={\"id\": 21}&best_before=2200-12-12T12:12:12+03:00&signature=Isx3ivYhho57twVTh943HLLger8nUAxu85" +
            "99dbieXVnbQHgl5FJ%2BUrMohHXUW3uZ16qBam%2F4nvxeFZUvaSF6gg%3D%3D";
    public static String callbackDataInvalid = "{...";
    public static String callbackDataInvalidSignatureRecursive = "{" +
        "\"body\": {" +
            "\"payment\": {" +
                "\"id\": \"" + paymentId + "1\"," +
                "\"status\": \"" + paymentStatus + "\"" +
            "}," +
            "\"signature\": \"" + paymentSignature + "\"" +
        "}" +
    "}";
    public static String callbackDataInvalidSignature = "{" +
        "\"payment\": {" +
            "\"id\": \"" + paymentId + "1\"," +
            "\"status\": \"" + paymentStatus + "\"" +
        "}," +
        "\"signature\": \"" + paymentSignature + "\"" +
    "}";
    public static String callbackData = "{" +
        "\"payment\": {" +
            "\"id\": \"" + paymentId + "\"," +
            "\"status\": \"" + paymentStatus + "\"" +
        "}," +
        "\"signature\": \"" + paymentSignature + "\"" +
    "}";
    public static String callbackDataWithoutPaymentId = "{" +
        "\"payment\": {" +
            "\"status\": \"" + paymentStatus + "\"" +
        "}," +
        "\"signature\": \"jRtwdhnY6bQGt39FRUVrXEBTgplUTjRpCA/37x95/c5FvdsnZMnlLUGXVRpNjKOHbawT0s9AmUy3rajlexddwQ==\"" +
    "}";

    public static Map getPaymentMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", "test.test-payment");
        map.put("status", "success");

        return map;
    }

    public static SignatureHandler getSignatureHandler() {
        return new SignatureHandler(secret);
    }

    public static Payment getPayment() {
        Payment payment = new Payment("123", "test_payment");
        ZonedDateTime bestBefore = ZonedDateTime.parse("2200-12-12T12:12:12+03:00[Europe/Moscow]");
        DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(bestBefore);

        return
            payment
                .setParam(Payment.ACCOUNT_TOKEN, "qwerty")
                .setParam(Payment.BEST_BEFORE, bestBefore);
    }
}
