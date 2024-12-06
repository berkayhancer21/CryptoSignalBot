package com.cryptobot.stratejiler;

import java.util.ArrayList;
import java.util.List;

public class EMA9_EMA21_stratejisi {

    // EMA hesaplama fonksiyonu
    public static List<Double> calculateEMA(List<Double> fiyatlar, int periyot) {
        List<Double> emaDegerleri = new ArrayList<>();
        double carpim = 2.0 / (periyot + 1);
        double oncekiEMA = fiyatlar.get(0);

        emaDegerleri.add(oncekiEMA);

        for (int i = 1; i < fiyatlar.size(); i++) {
            double guncelEMA = ((fiyatlar.get(i) - oncekiEMA) * carpim) + oncekiEMA;
            emaDegerleri.add(guncelEMA);
            oncekiEMA = guncelEMA;
        }

        return emaDegerleri;
    }

    // EMA stratejisi sinyal üretimi fonksiyonu
    public static String generateSignal(List<Double> kisaEMA, List<Double> uzunEMA) {
        String sinyal = "NONE"; // Başlangıçta sinyal yok
        int sonIndeks = kisaEMA.size() - 1;
        double kisaEMASon = kisaEMA.get(sonIndeks);
        double uzunEMASon = uzunEMA.get(sonIndeks);

        if (kisaEMASon > uzunEMASon) {
            sinyal = "AL"; // Kısa EMA, uzun EMA'yı yukarı kesiyor
        } else if (kisaEMASon < uzunEMASon) {
            sinyal = "SAT"; // Kısa EMA, uzun EMA'yı aşağı kesiyor
        }

        return sinyal;
    }
}
