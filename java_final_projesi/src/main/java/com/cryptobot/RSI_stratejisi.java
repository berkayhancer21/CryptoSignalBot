package com.cryptobot;

import java.util.List;

public class RSI_stratejisi {

    // RSI hesaplama (Örnek olarak 14 periyot)
    public static double calculateRSI(List<Double> fiyatlar, int periyot) {
        double kar = 0;
        double zarar = 0;

        for (int i = 1; i < periyot; i++) {
            double degisim = fiyatlar.get(i) - fiyatlar.get(i - 1);
            if (degisim > 0) {
                kar += degisim;
            } else {
                zarar -= degisim;
            }
        }

        double ortKar = kar / periyot;
        double ortZarar = zarar / periyot;

        if (ortZarar == 0) {
            return 100; // Hareketsiz piyasa
        }

        double rs = ortKar / ortZarar;
        return 100 - (100 / (1 + rs));
    }

    // AL/SAT sinyali üret
    public static String generateSignal(double rsiDegeri) {
        if (rsiDegeri > 70) {
            return "SAT";
        } else if (rsiDegeri < 30) {
            return "AL";
        }
        return "NONE";
    }
}
