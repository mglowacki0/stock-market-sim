import org.junit.jupiter.api.Test;
import org.zadanko.domain.Commodity;
import org.zadanko.domain.Currency;
import org.zadanko.domain.Share;
import org.zadanko.logic.InsufficientFundsException;
import org.zadanko.logic.Portfolio;

import static org.junit.jupiter.api.Assertions.*;

public class BusinessLogicTest {

    @Test
    void polymorphismDifferentRealValues() {
        // Sprawdza, że różne aktywa zwracają różne realValue()
        double unitPrice = 100.0;
        double quantity = 10.0;

        Share share = new Share("S1", unitPrice, quantity);
        Commodity commodity = new Commodity("C1", unitPrice, quantity, 2.0);
        Currency currency = new Currency("CU1", unitPrice, quantity, 1.5);

        double vShare = share.realValue();
        double vCommodity = commodity.realValue();
        double vCurrency = currency.realValue();

        assertNotEquals(vShare, vCommodity);
        assertNotEquals(vShare, vCurrency);
        assertNotEquals(vCommodity, vCurrency);
    }

    @Test
    void assetRejectsInvalidArguments() {
        // Blokuje null, ujemną cenę i 0 ilości
        assertThrows(IllegalArgumentException.class,
                () -> new Share(null, 100.0, 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Share("S", -10.0, 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Share("S", 10.0, 0.0));
    }

    @Test
    void shareRejectsNaNAndInfiniteValues() {
        // Blokuje NaN i Infinity w unitPrice i quantity
        assertThrows(IllegalArgumentException.class,
                () -> new Share("S1", Double.NaN, 10.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Share("S2", Double.POSITIVE_INFINITY, 10.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Share("S3", 100.0, Double.NaN));
        assertThrows(IllegalArgumentException.class,
                () -> new Share("S4", 100.0, Double.NEGATIVE_INFINITY));
    }


    @Test
    void purchaseFailsWhenInsufficientFunds() {
        // Sprawdza, że zakup droższego aktywa rzuca wyjątek
        Portfolio portfolio = new Portfolio(50.0);
        Share expensiveShare = new Share("S-exp", 100.0, 1.0);

        InsufficientFundsException ex = assertThrows(
                InsufficientFundsException.class,
                () -> portfolio.buy(expensiveShare)
        );
        assertTrue(ex.getMessage().contains("Not enough cash"));
    }


    @Test
    void realValueCalculatesNetCorrectly() {
        // sprawdza czy realValue() dla Commodity poprawnie odejmuje koszt magazynowania od wartości brutto
        double unitPrice = 50.0;
        double quantity = 20.0;
        double storageRate = 2.0;

        Commodity commodity = new Commodity("C1", unitPrice, quantity, storageRate);

        // realValue = (unitPrice * quantity) - (storageRate * quantity)
        double expectedNet = (unitPrice * quantity) - (storageRate * quantity);
        assertEquals(expectedNet, commodity.realValue());
    }
}


