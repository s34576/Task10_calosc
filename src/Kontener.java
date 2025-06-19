public abstract class Kontener {
    protected double masa;
    protected double wysokosc;
    protected double wagaWlasna; //waga samego kontenera, w kilogramach
    protected double glebokosc;
    protected double maxLadownoscKontenera;
    protected String numerSeryjny;
    private static int licznik = 1;
    public Kontener(char typ, double wysokosc, double wagaWlasna, double glebokosc, double maxLadownoscKontenera) {
        this.masa = 0;
        this.wysokosc = wysokosc;
        this.wagaWlasna = wagaWlasna;
        this.glebokosc = glebokosc;
        this.maxLadownoscKontenera = maxLadownoscKontenera;
        this.numerSeryjny = wygenerujNumerSeryjny(typ);
    }
    private String wygenerujNumerSeryjny(char t) {
        return "KON-"+t+"-"+(licznik++);
    }

    public String getNumerSeryjny() {
        return numerSeryjny;
    }
    public void zaladujKontener(double masa2) throws OverfillException{
        if(masa2>maxLadownoscKontenera){
            throw new OverfillException("Za duża masa kontenera"+numerSeryjny);
        }
        this.masa=masa2;
    }
    public void wyladujKontener(){
        this.masa=0;
    }
    public double getMasa() {
        return masa;
    }
    public double wagaCalkowita(){
        return wagaWlasna+masa;
    }

    public  String toString() {
        return "{ "+"Numer seryjny: "+ numerSeryjny + " Masa: "+ masa+ " }";
    }


}