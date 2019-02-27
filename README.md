# EcommPay payment page SDK

This is a set of libraries in the Java language to ease integration of your service
with the EcommPay Payment Page.

## Payment flow

![Payment flow](https://raw.githubusercontent.com/ITECOMMPAY/paymentpage-sdk-java/master/flow.png)

### Get URL for payment

```java
Gate gate = new Gate('secret');
Payment payment = new Payment(402);

payment
    .setParam(Payment.PAYMENT_AMOUNT, 1001)
    .setParam(Payment.PAYMENT_CURRENCY, "EUR");

String paymentUrl = gate.getPurchasePaymentPageUrl(payment);
``` 

`paymentUrl` here is the signed URL.

### Handle callback from Ecommpay

You'll need to autoload this code in order to handle notifications:

```java
Gate gate = new Gate('secret');
Callback callback = gate.handleCallback(callbackData);
```

`data` is the JSON string received from payment system;

`callback` is the Callback object describing properties received from payment system;
`callback` implements these methods: 
1. `callback.getPaymentStatus();`
    Get payment status.
2. `callback.getPayment();`
    Get all payment data.
3. `callback.getPaymentId();`
    Get payment ID in your system.
