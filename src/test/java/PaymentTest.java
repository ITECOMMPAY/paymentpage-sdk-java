import com.ecommpay.sdk.Payment;
import com.ecommpay.sdk.ProcessException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PaymentTest
{
    private Payment payment;

    @Before
    public void initTest()
    {
        payment = (new Payment("123")).setParam(Payment.PAYMENT_ID, "test_payment");
    }

    @After
    public void afterTest()
    {
        payment = null;
    }

    @Test
    public void setParam()
    {
        assertTrue(payment.setParam(Payment.ACCOUNT_TOKEN, "qwerty") instanceof Payment);
    }

    @Test
    public void getParams()
    {
        Clock clock = Clock.system(ZoneId.of("Europe/Moscow"));
        ZonedDateTime bestBefore = ZonedDateTime.now(clock).plusYears(1);

        payment
            .setParam(Payment.ACCOUNT_TOKEN, "qwerty")
            .setParam(Payment.BEST_BEFORE, bestBefore)
            .setParam(Payment.OPERATION_TYPE, "sale");

        Map<String, String> condition = new HashMap<String, String>(){{
            put("project_id", "123");
            put("payment_id", "test_payment");
            put("interface_type", "{\"id\": 21}");
            put("account_token", "qwerty");
            put("operation_type", "sale");
            put("best_before", DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(bestBefore));
        }};

        assertEquals(condition, payment.getParams());
    }

    @Test
    public void cardOperationTypeTest()
    {
        payment.setParam(Payment.CARD_OPERATION_TYPE, "auth");

        Map<String, String> condition = new HashMap<String, String>(){{
            put("project_id", "123");
            put("payment_id", "test_payment");
            put("interface_type", "{\"id\": 21}");
            put("operation_type", "auth");
        }};

        assertEquals(condition, payment.getParams());
    }

    @Test
    public void testSetAllFieldsBookingInfo() throws Exception {
        Payment payment = new Payment("123");

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("booking_info.json");
        assertNotNull(is);

        JSONObject bookingInfo =
                (JSONObject) new JSONParser().parse(
                        new InputStreamReader(is, StandardCharsets.UTF_8)
                );

        payment.setBookingInfo(bookingInfo);

        String encoded = payment.getParams().get("booking_info").toString();
        assertNotNull(encoded);

        String decodedJson = new String(
                Base64.getDecoder().decode(encoded),
                StandardCharsets.UTF_8
        );

        JSONObject decoded =
                (JSONObject) new JSONParser().parse(decodedJson);

        assertEquals(bookingInfo, decoded);
    }

    @Test(expected = ProcessException.class)
    public void testSetBookingInfoNull() throws ProcessException {
        Payment payment = new Payment("123");
        payment.setBookingInfo(null);
    }

    @Test(expected = ProcessException.class)
    public void testSetBookingInfoEmpty() throws ProcessException {
        Payment payment = new Payment("123");
        payment.setBookingInfo(new HashMap<>());
    }

    @Test(expected = ProcessException.class)
    public void testSetBookingInfoInvalid() throws ProcessException {
        Payment payment = new Payment("123");

        HashMap<String, Object> invalid = new HashMap<>();
        invalid.put("bad", new Object());

        payment.setBookingInfo(invalid);
    }
}
