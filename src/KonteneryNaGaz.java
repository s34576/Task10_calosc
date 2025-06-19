public class KonteneryNaGaz extends Kontener implements IHazardNotifier{
    private double cisnienie;
    public KonteneryNaGaz(double wysokosc, double wagaWlasna, double glebokosc, double maxLadownoscKontenera, double cisnienie) {
        super('G', wysokosc, wagaWlasna, glebokosc, maxLadownoscKontenera);
        this.cisnienie = cisnienie;

    }
    @Override
    public void zaladujKontener(double masa2) throws OverfillException{
        if(masa2>maxLadownoscKontenera){
            notifyDanger("Przekroczo limit załadowania gazowego kontenera: "+numerSeryjny);
            throw new OverfillException("Przekroczo ładowność kontenera: "+numerSeryjny);
        }
        this.masa=masa2;
    }
    @Override
    public void wyladujKontener(){
        this.masa=this.masa*0.05;
    }
    @Override
    public void notifyDanger(String message) {
        System.out.println("!UWAGA GAZ: "+message);
    }
    @Override
    public String toString() {
        return super.toString()+"\n, Typ: Gaz, Ciśnienie: "+cisnienie;
    }
}
