public class KonteneryNaPlyny extends Kontener implements IHazardNotifier{
    private boolean ladunekNiebezpieczny;
    public KonteneryNaPlyny(double wysokosc, double wagaWlasna, double glebokosc, double maxLadownoscKontenera, boolean ladunekNiebezpieczny) {
        super('L', wysokosc, wagaWlasna, glebokosc, maxLadownoscKontenera);
        this.ladunekNiebezpieczny = ladunekNiebezpieczny;
    }
    @Override
    public void zaladujKontener(double masa2) throws OverfillException{
        double limit;
        if(ladunekNiebezpieczny) {
            limit=0.5*maxLadownoscKontenera;
        }
        else{
            limit=0.9 *maxLadownoscKontenera;
        }
        if(masa2>limit)
        {
            notifyDanger("Próba niebezpiecznego załadunku kontenera: "+numerSeryjny);
            throw new OverfillException("Przekroczono limity ładunku: "+numerSeryjny);
        }
        this.masa=masa2;
    }
    @Override
    public void notifyDanger(String message)
    {
        System.out.println("!UWAGA: "+message);
    }
    @Override
    public String toString(){
        return super.toString()+" Typ: Płyny"+", Ładunek niebezpieczny: "+ladunekNiebezpieczny;
    }
}
