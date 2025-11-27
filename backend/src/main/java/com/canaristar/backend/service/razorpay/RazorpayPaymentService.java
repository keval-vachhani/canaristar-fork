package com.canaristar.backend.service.razorpay;

import com.canaristar.backend.entity.orders.Orders;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class RazorpayPaymentService {

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;


    public String initializePayment(Orders orders) throws Exception {

        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject request = new JSONObject();
        request.put("amount", (int)((orders.getTotalPrice() - orders.getDiscountPrice()) * 100));
        request.put("currency", "INR");
        request.put("receipt", orders.getId());

//        razor payment order
        Order razorpayOrder = client.orders.create(request);

        return razorpayOrder.get("id");
    }


    public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) throws Exception {

        String payload = razorpayOrderId + "|" + razorpayPaymentId;

        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");

        mac.init(secretKeySpec);
        byte[] digest = mac.doFinal(payload.getBytes());

        String generatedSignature = Hex.encodeHexString(digest);

        return generatedSignature.equals(razorpaySignature);
    }
}
