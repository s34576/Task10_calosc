import java.util.ArrayList;

public class Kontenerowiec {
    private String nazwa;
    private double maxPredkosc;
    private int maxKontenerow;
    private double maxWaga;
    private ArrayList<Kontener> kontenery;
    public Kontenerowiec(String nazwa, double maxPredkosc, int maxKontenerow, double maxWaga) {
        this.nazwa = nazwa;
        this.maxPredkosc = maxPredkosc;
        this.maxKontenerow = maxKontenerow;
        this.maxWaga = maxWaga;
        this.kontenery = new ArrayList<>();
    }
    public void zaladujKontener(Kontener k) throws Exception{
        if(kontenery.size()>=maxKontenerow){
            throw new Exception("Za dużo kontnerów na statku!");
        }
        if(obliczWage()+k.wagaCalkowita()>maxWaga*1000){
            throw new Exception("Za duża waga całkowita kontenerów");
        }
        kontenery.add(k);
    }
    public void usunKontener(String numer){
        for(int i=0; i<kontenery.size(); i++){
            if(kontenery.get(i).getNumerSeryjny().equals(numer)){
                kontenery.remove(i);
                break;
            }
        }
    }
    public void zamienKontener(String numer, Kontener nowy){
        for(int i=0; i<kontenery.size(); i++){
            if(kontenery.get(i).getNumerSeryjny().equals(numer)){
                kontenery.set(i, nowy);
                break;
            }
        }
    }
    public void przeniesKontener(Kontenerowiec inny, String numer) throws Exception{
        for(int i=0; i<kontenery.size(); i++){
            if(kontenery.get(i).getNumerSeryjny().equals(numer)){
                inny.zaladujKontener(kontenery.get(i));
                kontenery.remove(i);
                break;
            }
        }
    }
    public double obliczWage(){
        double suma=0;
        for(int i=0; i<kontenery.size(); i++){
            suma+=kontenery.get(i).wagaCalkowita();
        }
        return suma;
    }
    public void wypiszInformacje(){
        System.out.println("Statek: " + nazwa);
        for(int i=0; i<kontenery.size(); i++){
            System.out.println(kontenery.get(i));
        }
    }
    public String getNazwa(){
        return nazwa;
    }
    public double getMaxPredkosc(){
        return maxPredkosc;
    }
    public int getMaxKontenerow(){
        return maxKontenerow;
    }
    public double getMaxWaga(){
        return maxWaga;
    }
}
