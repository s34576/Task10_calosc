public class KonteneryChlodnicze extends Kontener {
    private String typProduktu;
    private double temperatura;
    private double wymaganaTemperatura;

    public KonteneryChlodnicze(double wysokosc, double wagaWlasna, double glebokosc, double maxLadownoscKontenera,String typProduktu, double temperatura, double wymaganaTemperatura) {
        super('C', wysokosc, wagaWlasna,glebokosc,maxLadownoscKontenera);
        this.typProduktu = typProduktu;
        this.temperatura = temperatura;
        this.wymaganaTemperatura = wymaganaTemperatura;
    }
    @Override
    public void zaladujKontener(double masa2) throws OverfillException{
        if(temperatura < wymaganaTemperatura){
            throw new OverfillException("Temepratura za niska dla produktu w kontenerze: "+ numerSeryjny);
        }
        super.zaladujKontener(masa2);
    }
    @Override
    public void wyladujKontener(){
        this.masa=0;
    }
    @Override
    public String toString() {
        return super.toString()+ ", Typ: Chłodniczy, Produkt: "+typProduktu+ ", Temperatura: "+temperatura+" C";
    }
}
