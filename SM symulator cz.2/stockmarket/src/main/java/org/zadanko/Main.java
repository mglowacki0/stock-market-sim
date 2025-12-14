package org.zadanko;

import org.zadanko.domain.Asset;
import org.zadanko.domain.Commodity;
import org.zadanko.domain.Currency;
import org.zadanko.domain.Share;
import org.zadanko.logic.InsufficientFundsException;
import org.zadanko.logic.Portfolio;

public class Main {

    public static void main(String[] args) {
        System.out.println("Symulator portfela inwestycyjnego");

        Portfolio myPortfolio = new Portfolio(3000.0);

        Asset[] myAssets = {
                new Share("SH-APPLE", 100.0, 10.0),
                new Commodity("CM-GOLD", 100.0, 10.0, 2.0),
                new Currency("FX-USD", 100.0, 10.0, 1.5)
        };

        System.out.println("\nKupno aktywów:");
        for (Asset a : myAssets) {
            try {
                myPortfolio.buy(a);
                System.out.println(a.getId() + " Koszt Zakupu = " + a.purchaseCost());
            } catch (InsufficientFundsException e) {
                System.out.println(a.getId() + " Koszt Zakupu = " + a.purchaseCost()
                        + " (brak środków)");
            }
        }

        System.out.println("\nStan portfela:");
        for (Asset a : myPortfolio.getAssets()) {
            double totalValue = a.getUnitPrice() * a.getQuantity();
            System.out.println(
                    a.getId()
                            + " Wartosc Brutto = " + totalValue
                            + " Wartosc Rzeczywista = " + a.realValue()
                            + " Strata = " + (totalValue - a.realValue())
            );
        }

        System.out.println("\nPodsumowanie portfela:");
        System.out.println("Wartosc wszystkich aktywów = " + myPortfolio.auditTotalValue());
        System.out.println("Dostępna gotówka = " + myPortfolio.getCash());

        System.out.println("\nDuża transakcja akcji:");
        Asset hugeShare = new Share("SH-BIG", 100.0, 2000.0);
        System.out.println(
                hugeShare.getId()
                        + " Wartosc Brutto = " + (hugeShare.getUnitPrice() * hugeShare.getQuantity())
                        + " Koszt Zakupu = " + hugeShare.purchaseCost()
                        + " Wartosc Rzeczywista = " + hugeShare.realValue()
        );

        System.out.println("\nObsługa wyjątku przy braku środków:");
        try {
            new Portfolio(50.0).buy(new Share("SH-FAIL", 100.0, 1.0));
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
