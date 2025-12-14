package org.zadanko.domain;

public class Currency extends Asset {

    private final double transactionSpread;

    public Currency(String assetCode, double pricePerUnit, double units, double spreadValue) {
        super(assetCode, pricePerUnit, units);

        // --- SPREAD ---
        if (Double.isNaN(spreadValue)) {
            throw new IllegalArgumentException("spreadValue must not be NaN");
        }
        if (Double.isInfinite(spreadValue)) {
            throw new IllegalArgumentException("spreadValue must be finite");
        }
        if (spreadValue < 0.0) {
            throw new IllegalArgumentException("spreadValue must be >= 0");
        }

        // Spread nie może zjadać całej ceny (bid musi być dodatni)
        if (spreadValue >= pricePerUnit) {
            throw new IllegalArgumentException("spreadValue must be < pricePerUnit");
        }

        if (spreadValue > 1_000_000) {
            throw new IllegalArgumentException("spreadValue is unrealistically high");
        }

        this.transactionSpread = spreadValue;
    }

    public double getSpread() {
        return transactionSpread;
    }

    @Override
    public double realValue() {
        // Bid price – realna kwota, którą inwestor dostanie
        double bidPricePerUnit = unitPrice - transactionSpread;
        double netValue = bidPricePerUnit * quantity;

        // Waluta nie generuje długu
        return Math.max(0.0, netValue);
    }

    @Override
    public double purchaseCost() {
        // Płacimy ask (pełną cenę rynkową, bez potrącania spreadu)
        return unitPrice * quantity;
    }
}
