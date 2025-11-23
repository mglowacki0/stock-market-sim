package StockmarketTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.stockmarket.Stock;
import org.stockmarket.Portfolio;

class PortfolioTest {

    @Test
    void testEmptyPortfolio() {
        Portfolio p = new Portfolio(1000.0);

        assertEquals(1000.0, p.getCash());
        assertEquals(0, p.getHoldingsCount());
        assertEquals(0.0, p.calculateStockValue());
        assertEquals(1000.0, p.calculateTotalValue());
    }

    @Test
    void testAddNewStock() {
        Portfolio p = new Portfolio(1000);
        Stock s = new Stock("CDR", "CD Projekt", 100);

        p.addStock(s, 5);

        assertEquals(1, p.getHoldingsCount());
        assertEquals(5, p.getStockQuantity(s));
    }

    @Test
    void testAddSameStockTwice() {
        Portfolio p = new Portfolio(1000);
        Stock s = new Stock("CDR", "CD Projekt", 100);

        p.addStock(s, 5);
        p.addStock(s, 3);

        assertEquals(1, p.getHoldingsCount());
        assertEquals(8, p.getStockQuantity(s));
    }

    @Test
    void testAddDifferentStocksCreatesSeparateHoldings() {
        Portfolio p = new Portfolio(1000);
        Stock s1 = new Stock("CDR", "CD Projekt", 100);
        Stock s2 = new Stock("PLW", "Playway", 50);

        p.addStock(s1, 5);
        p.addStock(s2, 10);

        assertEquals(2, p.getHoldingsCount());
        assertEquals(5, p.getStockQuantity(s1));
        assertEquals(10, p.getStockQuantity(s2));
    }

    @Test
    void testCalculateStockValue() {
        Portfolio p = new Portfolio(1000);
        Stock s1 = new Stock("CDR", "CD Projekt", 100);
        Stock s2 = new Stock("PLW", "Playway", 50);

        p.addStock(s1, 2);  // 200
        p.addStock(s2, 4);  // 200

        assertEquals(400.0, p.calculateStockValue());
    }

    @Test
    void testCalculateTotalValue() {
        Portfolio p = new Portfolio(500);
        Stock s = new Stock("CDR", "CD Projekt", 100);
        p.addStock(s, 3);  // 300

        assertEquals(800.0, p.calculateTotalValue());
    }

    @Test
    void testAddingStockWhenPortfolioIsFullThrowsError() {
        Portfolio p = new Portfolio(1000);
        for (int i = 0; i < 10; i++) {
            p.addStock(new Stock("S" + i, "Stock " + i, 10), 1);
        }

        assertThrows(IllegalStateException.class, () ->
                p.addStock(new Stock("EXTRA", "Extra Stock", 10), 1)
        );
    }

    @Test
    void testGetStockQuantityForNonExistingStockReturnsZero() {
        Portfolio p = new Portfolio(1000);
        Stock s = new Stock("CDR", "CD Projekt", 100);

        assertEquals(0, p.getStockQuantity(s));
    }

    @Test
    void testCashDoesNotChangeAfterAddingStock() {
        Portfolio p = new Portfolio(1000);
        Stock s = new Stock("CDR", "CD Projekt", 100);

        p.addStock(s, 5);

        // Cash should remain unchanged
        assertEquals(1000.0, p.getCash());
    }

}