import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Kontenerowiec> statki = new ArrayList<>();
    private static List<Kontener> kontenery = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            pokazMenu();
            int wybor = wczytajInt("Wybierz opcję: ");
            try {
                if (wybor == 1) {
                    dodajKontenerowiec();
                } else if (wybor == 2) {
                    usunKontenerowiec();
                } else if (wybor == 3) {
                    dodajKontener();
                } else if (wybor == 4) {
                    zaladujKontenerNaStatek();
                } else if (wybor == 5) {
                    usunKontenerZeStatku();
                } else if (wybor == 6) {
                    rozladujKontener();
                } else if (wybor == 7) {
                    zamienKontenerNaStatku();
                } else if (wybor == 8) {
                    przeniesKontenerMiedzyStatkami();
                } else if (wybor == 9) {
                    wypiszKontenery();
                } else if (wybor == 10) {
                    wypiszStatki();
                } else if (wybor == 0) {
                    System.out.println("Koniec programu.");
                    return;
                } else {
                    System.out.println("Nieznana opcja.");
                }
            } catch (Exception e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }

    private static void pokazMenu() {
        System.out.println("\nLista kontenerowców:");
        if (statki.isEmpty()) System.out.println("Brak");
        else {
            for (int i = 0; i < statki.size(); i++) {
                Kontenerowiec k = statki.get(i);
                System.out.println((i + 1) + ": " + k.getNazwa() + " (maxKontenerów=" + k.getMaxKontenerow() +", maxWaga=" + k.getMaxWaga() + " ton" + ", maxPrędkość=" + k.getMaxPredkosc() + " węzłów)");
            }
        }

        System.out.println("Lista kontenerów:");
        if (kontenery.isEmpty()) System.out.println("Brak");
        else {
            for (int i = 0; i < kontenery.size(); i++) {
                Kontener k = kontenery.get(i);
                System.out.printf("%d: %s%n", i + 1, k);
            }
        }

        System.out.println("\nMożliwe akcje:");
        System.out.println("1. Dodaj kontenerowiec");
        System.out.println("2. Usuń kontenerowiec");
        System.out.println("3. Dodaj kontener");
        System.out.println("4. Załaduj kontener na statek");
        System.out.println("5. Usuń kontener ze statku");
        System.out.println("6. Rozładuj kontener");
        System.out.println("7. Zamień kontener na statku");
        System.out.println("8. Przenieś kontener między statkami");
        System.out.println("9. Wypisz kontenery");
        System.out.println("10. Wypisz statki");
        System.out.println("0. Wyjdź");
    }

    private static int wczytajInt(String polecenie) {
        System.out.print(polecenie);
        while (!scanner.hasNextInt()) {
            System.out.println("To nie jest liczba. Spróbuj ponownie.");
            scanner.next();
            System.out.print(polecenie);
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // konsumuj enter
        return val;
    }

    private static double wczytajDouble(String polecenie) {
        System.out.print(polecenie);
        while (!scanner.hasNextDouble()) {
            System.out.println("To nie jest liczba. Spróbuj ponownie.");
            scanner.next();
            System.out.print(polecenie);
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }

    private static String wczytajString(String polecenie) {
        System.out.print(polecenie);
        return scanner.nextLine();
    }

    private static void dodajKontenerowiec() {
        String nazwa = wczytajString("Podaj nazwę statku: ");
        double maxPredkosc = wczytajDouble("Podaj maksymalną prędkość (węzły): ");
        int maxKontenerow = wczytajInt("Podaj maksymalną liczbę kontenerów: ");
        double maxWaga = wczytajDouble("Podaj maksymalną wagę kontenerów (w tonach): ");
        Kontenerowiec statek = new Kontenerowiec(nazwa, maxPredkosc, maxKontenerow, maxWaga);
        statki.add(statek);
        System.out.println("Dodano kontenerowiec: " + nazwa);
    }

    private static void usunKontenerowiec() {
        if (statki.isEmpty()) {
            System.out.println("Brak statków do usunięcia.");
            return;
        }
        int idx = wczytajInt("Podaj numer statku do usunięcia: ") - 1;
        if (idx < 0 || idx >= statki.size()) {
            System.out.println("Niepoprawny numer statku.");
            return;
        }
        Kontenerowiec usuwany = statki.remove(idx);
        System.out.println("Usunięto kontenerowiec: " + usuwany.getNazwa());
    }

    private static void dodajKontener() throws OverfillException {
        System.out.println("Wybierz typ kontenera:");
        System.out.println("1. Kontener chłodniczy (banany)");
        System.out.println("2. Kontener na płyny (mleko/paliwo)");
        System.out.println("3. Kontener na gaz (hel)");
        int typ = wczytajInt("Twój wybór: ");

        double wysokosc = wczytajDouble("Wysokość (cm): ");
        double wagaWlasna = wczytajDouble("Waga własna (kg): ");
        double glebokosc = wczytajDouble("Głębokość (cm): ");
        double maxLadownosc = wczytajDouble("Maksymalna ładowność (kg): ");

        Kontener nowy = null;

        if (typ == 1) {
            String produkt = "banany";
            double temperatura = wczytajDouble("Aktualna temperatura kontenera (C): ");
            double wymaganaTemp = wczytajDouble("Wymagana temperatura dla produktu (C): ");
            nowy = new KonteneryChlodnicze(wysokosc, wagaWlasna, glebokosc, maxLadownosc, produkt, temperatura, wymaganaTemp);
        } else if (typ == 2) {
            String ladunekNiebezpiecznyStr = wczytajString("Czy ładunek jest niebezpieczny? (tak/nie): ").toLowerCase();
            boolean ladunekNiebezpieczny = ladunekNiebezpiecznyStr.equals("tak") || ladunekNiebezpiecznyStr.equals("t");
            nowy = new KonteneryNaPlyny(wysokosc, wagaWlasna, glebokosc, maxLadownosc, ladunekNiebezpieczny);
        } else if (typ == 3) {
            double cisnienie = wczytajDouble("Ciśnienie (atm): ");
            nowy = new KonteneryNaGaz(wysokosc, wagaWlasna, glebokosc, maxLadownosc, cisnienie);
        } else {
            System.out.println("Niepoprawny typ kontenera.");
            return;
        }
        kontenery.add(nowy);
        System.out.println("Dodano kontener: " + nowy.getNumerSeryjny());
    }

    private static Kontener znajdzKontenerPoNumerze(String numer) {
        for (Kontener k : kontenery) {
            if (k.getNumerSeryjny().equalsIgnoreCase(numer)) return k;
        }
        return null;
    }

    private static Kontenerowiec znajdzStatekPoNazwie(String nazwa) {
        for (Kontenerowiec s : statki) {
            if (s.getNazwa().equalsIgnoreCase(nazwa)) return s;
        }
        return null;
    }

    private static void zaladujKontenerNaStatek() throws Exception {
        if (statki.isEmpty()) {
            System.out.println("Brak statków.");
            return;
        }
        if (kontenery.isEmpty()) {
            System.out.println("Brak kontenerów.");
            return;
        }
        String numerKontenera = wczytajString("Podaj numer seryjny kontenera do załadunku: ");
        Kontener k = znajdzKontenerPoNumerze(numerKontenera);
        if (k == null) {
            System.out.println("Nie znaleziono kontenera.");
            return;
        }

        double masa = wczytajDouble("Podaj masę ładunku do załadowania (kg): ");
        k.zaladujKontener(masa);

        String nazwaStatku = wczytajString("Podaj nazwę statku, na który chcesz załadować kontener: ");
        Kontenerowiec statek = znajdzStatekPoNazwie(nazwaStatku);
        if (statek == null) {
            System.out.println("Nie znaleziono statku.");
            return;
        }
        statek.zaladujKontener(k);
        System.out.println("Załadowano kontener " + k.getNumerSeryjny() + " na statek " + nazwaStatku);
    }

    private static void usunKontenerZeStatku() {
        if (statki.isEmpty()) {
            System.out.println("Brak statków.");
            return;
        }
        String nazwaStatku = wczytajString("Podaj nazwę statku: ");
        Kontenerowiec statek = znajdzStatekPoNazwie(nazwaStatku);
        if (statek == null) {
            System.out.println("Nie znaleziono statku.");
            return;
        }
        String numerKontenera = wczytajString("Podaj numer kontenera do usunięcia: ");
        statek.usunKontener(numerKontenera);
        System.out.println("Usunięto kontener " + numerKontenera + " ze statku " + nazwaStatku);
    }

    private static void rozladujKontener() throws OverfillException {
        String numerKontenera = wczytajString("Podaj numer kontenera do rozładunku: ");
        Kontener k = znajdzKontenerPoNumerze(numerKontenera);
        if (k == null) {
            System.out.println("Nie znaleziono kontenera.");
            return;
        }
        k.wyladujKontener();
        System.out.println("Kontener " + numerKontenera + " rozładowany.");
    }

    private static void zamienKontenerNaStatku() throws Exception {
        String nazwaStatku = wczytajString("Podaj nazwę statku: ");
        Kontenerowiec statek = znajdzStatekPoNazwie(nazwaStatku);
        if (statek == null) {
            System.out.println("Nie znaleziono statku.");
            return;
        }
        String numerStarego = wczytajString("Podaj numer kontenera do zastąpienia: ");
        String numerNowego = wczytajString("Podaj numer nowego kontenera: ");

        Kontener nowy = znajdzKontenerPoNumerze(numerNowego);
        if (nowy == null) {
            System.out.println("Nie znaleziono nowego kontenera.");
            return;
        }
        statek.zamienKontener(numerStarego, nowy);
        System.out.println("Zamieniono kontener " + numerStarego + " na " + numerNowego + " na statku " + nazwaStatku);
    }

    private static void przeniesKontenerMiedzyStatkami() throws Exception {
        String numerKontenera = wczytajString("Podaj numer kontenera do przeniesienia: ");
        String nazwaStatkuZrodlowego = wczytajString("Podaj nazwę statku źródłowego: ");
        String nazwaStatkuDocelowego = wczytajString("Podaj nazwę statku docelowego: ");

        Kontenerowiec zrodlo = znajdzStatekPoNazwie(nazwaStatkuZrodlowego);
        Kontenerowiec cel = znajdzStatekPoNazwie(nazwaStatkuDocelowego);

        if (zrodlo == null || cel == null) {
            System.out.println("Nie znaleziono statków.");
            return;
        }
        zrodlo.przeniesKontener(cel, numerKontenera);
        System.out.println("Przeniesiono kontener " + numerKontenera + " ze statku " + nazwaStatkuZrodlowego +
                " na statek " + nazwaStatkuDocelowego);
    }

    private static void wypiszKontenery() {
        if (kontenery.isEmpty()) {
            System.out.println("Brak kontenerów.");
            return;
        }
        for (Kontener k : kontenery) {
            System.out.println(k);
        }
    }

    private static void wypiszStatki() {
        if (statki.isEmpty()) {
            System.out.println("Brak statków.");
            return;
        }
        for (Kontenerowiec s : statki) {
            System.out.println(s);
        }
    }
}
/*
ZWYKŁE SPRAWDZENIE DZIAŁANIA PROGRAMU
public class Main {
    public static void main(String[] args) {
        try{
            Kontenerowiec statek = new Kontenerowiec("Pirat", 30, 5, 500);
            KonteneryNaPlyny   s1=new KonteneryNaPlyny(500, 400,100,2000,false);
            KonteneryNaGaz s2 = new KonteneryNaGaz(400, 100, 200, 1000, 200);
            KonteneryChlodnicze s3 = new KonteneryChlodnicze(200, 10, 300, 500, "Mleko", 10, 0);
            s1.zaladujKontener(800);
            s2.zaladujKontener(700);
            s3.zaladujKontener(600);

            statek.zaladujKontener(s1);
            statek.zaladujKontener(s2);
            statek.zaladujKontener(s3);
            statek.wypiszInformacje();
        }
        catch(Exception e){
            System.out.println("Błąd: "+e.getMessage());
        }
    }
}
 */