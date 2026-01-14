import com.ecommpay.sdk.Payment;
import com.ecommpay.sdk.ProcessException;
import com.ecommpay.sdk.model.booking.Booker;
import com.ecommpay.sdk.model.booking.BookingInfo;
import com.ecommpay.sdk.model.booking.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public void testSetBookingInfoMapSuccess() throws Exception {
        Payment payment = new Payment("123");

        Booker booker = new Booker()
                .setFirst_name("William")
                .setLast_name("Herschel")
                .setEmail("rsfellow@mail.com");
        List<Booker> bookers = new ArrayList<>();
        bookers.add(booker);

        Item item = new Item()
                .setDescription("VIP Arrival")
                .setStart_date(LocalDate.of(2026, 8, 12))
                .setEnd_date(LocalDate.of(2026, 8, 14));
        List<Item> items = new ArrayList<>();
        items.add(item);

        BookingInfo bookingInfo = new BookingInfo()
                .setStart_date(LocalDate.of(2026, 8, 12))
                .setEnd_date(LocalDate.of(2026, 8, 14))
                .setDescription("Sideris music festival full pass")
                .setTotal(200000)
                .setPax(1)
                .setReference("musicfestlink")
                .setId("83")
                .setBookers(bookers)
                .setItems(items);

        payment.setBookingInfo(bookingInfo);

        String encoded = payment.getParams().get("booking_info").toString();
        assertNotNull(encoded);

        String expected = "eyJzdGFydF9kYXRlIjoiMTItMDgtMjAyNiIsImVuZF9kYXRlIjoiMTQtMDgtMjAyNiIsImRlc2NyaXB0aW9uIjoiU2" +
                "lkZXJpcyBtdXNpYyBmZXN0aXZhbCBmdWxsIHBhc3MiLCJ0b3RhbCI6MjAwMDAwLCJwYXgiOjEsImJvb2tlcnMiOlt7ImZpcnN0X2" +
                "5hbWUiOiJXaWxsaWFtIiwibGFzdF9uYW1lIjoiSGVyc2NoZWwiLCJlbWFpbCI6InJzZmVsbG93QG1haWwuY29tIn1dLCJpdGVtcy" +
                "I6W3siZGVzY3JpcHRpb24iOiJWSVAgQXJyaXZhbCIsInN0YXJ0X2RhdGUiOiIxMi0wOC0yMDI2IiwiZW5kX2RhdGUiOiIxNC0wOC" +
                "0yMDI2In1dLCJyZWZlcmVuY2UiOiJtdXNpY2Zlc3RsaW5rIiwiaWQiOiI4MyJ9";
        assertEquals(expected, encoded);
    }

    @Test(expected = ProcessException.class)
    public void testSetBookingInfoEmpty() throws ProcessException {
        Payment payment = new Payment("123");
        payment.setBookingInfo(new BookingInfo());
    }
}
