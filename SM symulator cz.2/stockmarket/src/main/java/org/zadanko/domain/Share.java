package org.zadanko.domain;
public class Share extends Asset {

    private static final double MAX_TRANSACTION_FEE = 5.0;
    private static final double COMMISSION_RATE = 0.01;

    public Share(String assetId, double pricePerUnit, double units) {
        super(assetId, pricePerUnit, units);

        if (Double.isNaN(pricePerUnit) || Double.isInfinite(pricePerUnit)) {
            throw new IllegalArgumentException("pricePerUnit must be a valid number");
        }
        if (Double.isNaN(units) || Double.isInfinite(units)) {
            throw new IllegalArgumentException("units must be a valid number");
        }
        if (MAX_TRANSACTION_FEE < 0) {
            throw new IllegalArgumentException("MAX_TRANSACTION_FEE must be >= 0");
        }
        if (COMMISSION_RATE < 0 || COMMISSION_RATE > 1) {
            throw new IllegalArgumentException("COMMISSION_RATE must be between 0 and 1");
        }
    }

    @Override
    public double realValue() {
        double totalGross = unitPrice * quantity;
        double transactionFee = Math.min(MAX_TRANSACTION_FEE, totalGross * COMMISSION_RATE);
        return Math.max(0.0, totalGross - transactionFee);
    }

    @Override
    public double purchaseCost() {
        double totalGross = unitPrice * quantity;
        double transactionFee = Math.min(MAX_TRANSACTION_FEE, totalGross * COMMISSION_RATE);
        return totalGross + transactionFee;
    }
}
