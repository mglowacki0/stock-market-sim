package org;

import java.util.Scanner;
import org.stockmarket.Stock;
import org.stockmarket.Portfolio;

public class Main {
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio(1000.0);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTwoja gotówka: " + portfolio.getCash());
            System.out.println("Liczba pozycji w portfelu: " + portfolio.getHoldingsCount());
            System.out.println("Wartość akcji: " + portfolio.calculateStockValue());
            System.out.println("Całkowita wartość portfela: " + portfolio.calculateTotalValue());

            System.out.println("\nOpcje:");
            System.out.println("1 - Dodaj akcję");
            System.out.println("2 - Sprawdź ilość akcji");
            System.out.println("0 - Wyjście");
            System.out.print("Wybierz opcję: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // czyszczenie bufora

            if (choice == 0) break;

            switch (choice) {
                case 1:
                    System.out.print("Podaj symbol akcji: ");
                    String symbol = scanner.nextLine();
                    System.out.print("Podaj nazwę spółki: ");
                    String name = scanner.nextLine();
                    System.out.print("Podaj cenę początkową: ");
                    double price = scanner.nextDouble();
                    System.out.print("Podaj ilość: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // czyszczenie bufora

                    Stock stock = new Stock(symbol, name, price);
                    try {
                        portfolio.addStock(stock, quantity);
                        System.out.println("Dodano akcje do portfela.");
                    } catch (IllegalStateException e) {
                        System.out.println("Nie można dodać akcji: portfel pełny.");
                    }
                    break;

                case 2:
                    System.out.print("Podaj symbol akcji: ");
                    String checkSymbol = scanner.nextLine();
                    Stock checkStock = new Stock(checkSymbol, "", 0);
                    int owned = portfolio.getStockQuantity(checkStock);
                    System.out.println("Masz " + owned + " akcji " + checkSymbol);
                    break;

                default:
                    System.out.println("Nieznana opcja.");
            }
        }

        scanner.close();
        System.out.println("Do widzenia!");
    }
}