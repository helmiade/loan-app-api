package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.LoanTransactionDetailService;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionService;
import com.enigmacamp.livecode_loan_app.dto.Request.TransactionRequest;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class MidtransController {
    private final LoanTransactionDetailService loanTransactionDetailService;
    @PostMapping("/generate-token")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        Midtrans.serverKey = "SB-Mid-server-tNE2MEu7i1IX6ch8ZexF8gCO";
        System.out.println(transactionRequest.getGrossAmount()+" "+ transactionRequest.getOrderId());

        Midtrans.isProduction = false;
        try {
            // Buat parameter request untuk Snap API
            Map<String, Object> params = new HashMap<>();
            Map<String, Object> transactionDetails = new HashMap<>();
            transactionDetails.put("order_id", transactionRequest.getOrderId());
            transactionDetails.put("gross_amount", transactionRequest.getGrossAmount());

            params.put("transaction_details", transactionDetails);
            params.put("credit_card", Map.of("secure", true));

            // Dapatkan transaction token dari Midtrans Snap API
            String transactionToken = SnapApi.createTransactionToken(params);

            // Kirim token ke frontend
            return ResponseEntity.ok(Map.of("token", transactionToken));
        } catch (MidtransError e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/notification")
    public ResponseEntity<String> handleNotification(@RequestBody Map<String, Object> notification) {
        try {
            // Log notifikasi yang diterima (untuk debugging)
            System.out.println("Notification Received: " + notification);

            // Lakukan validasi signature key
            String orderId = (String) notification.get("order_id");
            String statusCode = (String) notification.get("status_code");
            String grossAmount = (String) notification.get("gross_amount");
            String signatureKey = (String) notification.get("signature_key");

            // Server Key Anda
            String serverKey = "SB-Mid-server-tNE2MEu7i1IX6ch8ZexF8gCO";
            String calculatedSignature = generateSignature(orderId, statusCode, grossAmount, serverKey);

            if (!calculatedSignature.equals(signatureKey)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid signature key");
            }

            // Lakukan tindakan berdasarkan status pembayaran
            String transactionStatus = (String) notification.get("transaction_status");
            switch (transactionStatus) {
                case "capture":
                    // Pembayaran berhasil (capture)
                    System.out.println("Payment Success!");
                    loanTransactionDetailService.payById(orderId);
                    break;
                case "settlement":
                    // Pembayaran selesai (settlement)
                    System.out.println("Payment Settled!");
                    loanTransactionDetailService.payById(orderId);
                    break;
                case "deny":
                case "cancel":
                case "expire":
                    // Pembayaran gagal
                    System.out.println("Payment Failed!");
                    break;
                default:
                    System.out.println("Unknown Transaction Status");
            }

            return ResponseEntity.ok("Notification processed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing notification");
        }
    }

    // Metode untuk menghitung signature key
    private String generateSignature(String orderId, String statusCode, String grossAmount, String serverKey) {
        try {
            String payload = orderId + statusCode + grossAmount + serverKey;
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(payload.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(hash).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

