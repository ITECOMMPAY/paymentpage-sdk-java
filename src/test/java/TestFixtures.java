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
    public static String paymentSignature = "njv3u8EgX6UJjgMO5wqzKSINmtk+VgdUEBuLsKN0VUizf86uwW+5gbMw1vtetjZu9MwlUemam" +
            "6RX7o6FAfOZzA==";
    public static String testUrl = "https://test.test.test.test/payment";
    public static String baseUrl = "https://paymentpage.ecommpay.com/payment";
    public static String compareParams = "?project_id=123&account_token=qwerty&cashier_predefined_amounts=22,33,44&bes" +
            "t_before=2200-12-12T12:12:12+03:00&signature=bchBZJkqp8NMSV%2FJt7AUqw0kwRFxJiKmAFx535FRUeOIrYlDXbfkMsfxOa" +
            "w72CEDDGwphGOk0mwEWVfltkTS6g%3D%3D";
    public static String callbackDataInvalid = "{...";
    public static String callbackDataInvalidSignature = "{" +
        "\"body\": {" +
            "\"payment\": {" +
                "\"id\": \"" + paymentId + "1\"," +
                "\"status\": \"" + paymentStatus + "\"" +
            "}," +
            "\"signature\": \"" + paymentSignature + "\"" +
        "}" +
    "}";
    public static String callbackData = "{" +
        "\"body\": {" +
            "\"payment\": {" +
                "\"id\": \"" + paymentId + "\"," +
                "\"status\": \"" + paymentStatus + "\"" +
            "}," +
            "\"signature\": \"" + paymentSignature + "\"" +
        "}" +
    "}";
    public static String callbackDataWithoutPaymentId = "{" +
        "\"body\": {" +
            "\"payment\": {" +
                "\"status\": \"" + paymentStatus + "\"" +
            "}," +
            "\"signature\": \"AB4BUxuJEkzJ0l/Dliz+FX8CpvXYzpePuQ1UPU+3hE8y7prWZ0Lj56XArIHK4/9GrzHD6ystryMQ0pPcTQvpzA==\"" +
        "}" +
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
        Payment payment = new Payment("123");
        ZonedDateTime bestBefore = ZonedDateTime.parse("2200-12-12T12:12:12+03:00[Europe/Moscow]");
        DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(bestBefore);

        return
            payment
                .setParam(Payment.ACCOUNT_TOKEN, "qwerty")
                .setParam(Payment.BEST_BEFORE, bestBefore)
                .setParam(Payment.CASHIER_PREDEFINED_AMOUNTS, Arrays.asList("22", "33", "44"));
    }
}
