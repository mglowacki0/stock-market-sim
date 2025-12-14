package org.zadanko.domain;

public abstract class Asset {
    protected final String id;
    protected final double unitPrice;
    protected final double quantity;

    // Przykładowe maksymalne limity
    private static final double MAX_UNIT_PRICE = 1_000_000;
    private static final double MAX_QUANTITY = 10_000_000;

    protected Asset(String id, double unitPrice, double quantity) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be null or blank");
        }
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("unitPrice must be > 0");
        }
        if (unitPrice > MAX_UNIT_PRICE) {
            throw new IllegalArgumentException("unitPrice is unrealistically high");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        if (quantity > MAX_QUANTITY) {
            throw new IllegalArgumentException("quantity is unrealistically high");
        }
        // Sprawdzenie przepełnienia wartości brutto
        if (unitPrice * quantity > Double.MAX_VALUE) {
            throw new IllegalArgumentException("Total asset value exceeds allowable limit");
        }

        this.id = id;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    // Zwraca rzeczywistą (netto) wartość aktywa
    public abstract double realValue();

    // Zwraca całkowity koszt zakupu aktywa
    public abstract double purchaseCost();
}
