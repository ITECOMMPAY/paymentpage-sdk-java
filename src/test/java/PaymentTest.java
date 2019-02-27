import com.ecommpay.sdk.Payment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaymentTest
{
    private Payment payment;

    @Before
    public void initTest()
    {
        payment = new Payment("123");
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
            .setParam(Payment.CASHIER_PREDEFINED_AMOUNTS, Arrays.asList("22", "33", "44"));

        Map<String, String> condition = new HashMap<String, String>(){{
            put("project_id", "123");
            put("account_token", "qwerty");
            put("cashier_predefined_amounts", "22,33,44");
            put("best_before", DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(bestBefore));
        }};

        assertEquals(condition, payment.getParams());
    }
}