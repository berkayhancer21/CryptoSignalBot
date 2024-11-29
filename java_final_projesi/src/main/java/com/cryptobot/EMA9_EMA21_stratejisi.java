package com.cryptobot;

import java.util.ArrayList;
import java.util.List;

public class EMA9_EMA21_stratejisi {

    // EMA hesaplama
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

    // AL/SAT sinyali üret
    public static String generateSignal(List<Double> kisaEMA, List<Double> uzunEMA) {
        String sinyal = "NONE";
        int sonIndeks = kisaEMA.size() - 1;
        double kisaEMASon = kisaEMA.get(sonIndeks);
        double uzunEMASon = uzunEMA.get(sonIndeks);

        if (kisaEMASon > uzunEMASon) {
            sinyal = "AL";
        } else if (kisaEMASon < uzunEMASon) {
            sinyal = "SAT";
        }

        return sinyal;
    }
}
