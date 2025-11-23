package StockmarketTest;

import org.junit.jupiter.api.Test;
import org.stockmarket.Stock;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void testStockCreation() {
        Stock s = new Stock("CDR", "CD Projekt", 150.0);

        assertEquals("CDR", s.getSymbol());
        assertEquals("CD Projekt", s.getName());
        assertEquals(150.0, s.getInitialPrice());
    }

    @Test
    void testStocksWithSameSymbolAreEqual() {
        Stock a = new Stock("CDR", "CD Projekt Red", 100);
        Stock b = new Stock("CDR", "Some Other Name", 200);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testStocksWithDifferentSymbolsAreNotEqual() {
        Stock a = new Stock("CDR", "CD Projekt", 150);
        Stock b = new Stock("PLW", "Playway", 300);

        assertNotEquals(a, b);
    }

    @Test
    void testEqualsWithDifferentObjectType() {
        Stock a = new Stock("CDR", "CD Projekt", 150);
        assertNotEquals(a, "CDR");
    }

    @Test
    void testEqualsWithNullReturnsFalse() {
        Stock s = new Stock("CDR", "CD Projekt", 100);

        assertNotEquals(s, null);
    }

}