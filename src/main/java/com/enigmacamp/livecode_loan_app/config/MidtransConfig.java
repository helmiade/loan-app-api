package com.enigmacamp.livecode_loan_app.config;

import com.midtrans.Midtrans;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MidtransConfig {

    public MidtransConfig() {
        // Set server key (gunakan server key dari Midtrans Anda)
        Midtrans.serverKey = "SB-Mid-server-tNE2MEu7i1IX6ch8ZexF8gCO";

        // Gunakan environment sandbox (ubah ke `true` jika menggunakan produksi)
        Midtrans.isProduction = false;

        // Set timeout untuk API call
        Midtrans.setConnectTimeout(10); // dalam detik
    }
}

