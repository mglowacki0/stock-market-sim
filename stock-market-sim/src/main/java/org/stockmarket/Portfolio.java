
package org.stockmarket;


public class Portfolio {

    private static class StockHolding {
        private Stock stock;
        private int quantity;

        private StockHolding(Stock stock, int quantity) {
            this.stock = stock;
            this.quantity = quantity;
        }
    }

    private double cash;
    private StockHolding[] holdings;
    private int holdingsCount;

    public Portfolio(double initialCash) {
        this.cash = initialCash;
        this.holdings = new StockHolding[10]; // max 10 pozycji
        this.holdingsCount = 0;
    }

    public double getCash() {
        return cash;
    }

    public int getHoldingsCount() {
        return holdingsCount;
    }

    public int getStockQuantity(Stock stock) {
        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].stock.equals(stock)) {
                return holdings[i].quantity;
            }
        }
        return 0;
    }

    public void addStock(Stock stock, int quantity) {
        // Czy akcja już istnieje?
        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].stock.equals(stock)) {
                holdings[i].quantity += quantity;
                return;
            }
        }

        // Jeśli tablica pełna – nic nie rób (lub można rzucić wyjątek)
        if (holdingsCount >= holdings.length) {
            throw new IllegalStateException("Portfolio is full");
        }

        // Dodanie nowej pozycji
        holdings[holdingsCount] = new StockHolding(stock, quantity);
        holdingsCount++;
    }

    public double calculateStockValue() {
        double total = 0.0;
        for (int i = 0; i < holdingsCount; i++) {
            StockHolding h = holdings[i];
            total += h.quantity * h.stock.getInitialPrice();
        }
        return total;
    }

    public double calculateTotalValue() {
        return cash + calculateStockValue();
    }
}