import java.util.Scanner;

class Kart {
    String renk;//renk
    int deger;//değeri
    boolean flip;//flip kart
    boolean isCift;//çiftmi


    public Kart(String renk, int deger) {
        //constructor metodu
        this.renk = renk;
        this.deger = deger;
        this.flip = false; // Kart başlangıçta çevrilmemiş durumda
        this.isCift = false; // Kart başlangıçta çift kart değil


    }

    @Override
    public String toString() {
        //yukarıda belirlediğimiz değişkenleri yazıya döktüğümüz kısım
        return renk + " " + deger;
    }
    public void flip() {
        this.flip = !this.flip; // Çevrildiyse çevrilmemiş, çevrilmemişse çevrili duruma getir
    }
    public void setCift() {
        this.isCift = true;
    }
}

public class KartOyunu {
    static Kart[] deste;//desteyi belirlediğimiz kısım
    static Kart[] oyuncuEl;//oyuncunun eli
    static Kart[] bilgisayarEl;//bilgisayarın eli
    static Kart[] oyuncuTahta;//oyuncunun tahtası
    static Kart[] bilgisayarTahta;//bilgisyaarın tahtası
    static Scanner tarayici = new Scanner(System.in);//scanner
    static int oyuncuKazanma = 0;//baştaki oyunucun skoru
    static int bilgisayarKazanma = 0;//baştaki bilgisayarın skoru
    static final int MAX_SKOR = 21;//maksimum skor 21
    static final int OYNANACAK_ROUND_SAYISI = 3;//maksimum oynanıcak raund sayısı
    static int desteIndex = 0;//baştaki destenin değeri

}
