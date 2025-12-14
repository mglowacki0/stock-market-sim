package org.zadanko.domain;

public class Commodity extends Asset {

    private final double storageRatePerUnit;

    public Commodity(String id, double unitPrice, double quantity, double storageRatePerUnit) {
        super(id, unitPrice, quantity);

        if (storageRatePerUnit < 0) {
            throw new IllegalArgumentException("storageRatePerUnit must be >= 0");
        }
        if (storageRatePerUnit >= unitPrice) {
            throw new IllegalArgumentException("storageRatePerUnit must be less than unitPrice");
        }

        this.storageRatePerUnit = storageRatePerUnit;

        // Sprawdzenie, czy koszty przechowywania nie przewyższają wartości brutto
        double grossValue = unitPrice * quantity;
        double totalStorage = storageRatePerUnit * quantity;
        if (totalStorage > grossValue) {
            throw new IllegalArgumentException("Total storage cost cannot exceed gross value");
        }
    }

    @Override
    public double realValue() {
        double gross = unitPrice * quantity;
        double storageCost = storageRatePerUnit * quantity;
        double net = gross - storageCost;

        // Walidacja realistyczna – realna wartość nie może być ujemna
        return Math.max(0.0, net);
    }

    @Override
    public double purchaseCost() {
        double gross = unitPrice * quantity;
        double initialStorage = storageRatePerUnit * quantity;

        // Płacimy pełną cenę plus koszt pierwszego magazynowania
        return gross + initialStorage;
    }
}
