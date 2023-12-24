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

    public static void main(String[] args) {
        //main metod
        desteOlustur();
        //run tuşu
        System.out.println("Welcome to the Card Game!");

        for (int round = 1; round <= OYNANACAK_ROUND_SAYISI; round++) {
            //raound sayısı 3 olana kadar tekrar tekrar oyunu oynat loop
            roundOyna();
            oyunDurumuYazdir();
        }

        System.out.println("Game over!");
        oyunTarihiniYazdir();
    }

    static void desteOlustur() {
        String[] renkler = {"green", "blue", "yellow", "red"};
        int desteBoyutu = renkler.length * 6;
        deste = new Kart[desteBoyutu];
        int index = 0;
        for (String renk : renkler) {
            for (int deger = 1; deger <= 6; deger++) {
                Kart kart = new Kart(renk, deger);
                // Kart çevrili veya çift kart olabilir
                if (Math.random() < 0.04) {
                    kart.flip();
                }
                if (Math.random() < 0.04) {
                    kart.setCift();
                }
                deste[index++] = kart;
            }
        }
        desteKaristir(deste);
    }

    static void desteKaristir(Kart[] dizi) {
        //desteyi karıştırma metodu
        for (int i = dizi.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Kart temp = dizi[i];
            dizi[i] = dizi[j];
            dizi[j] = temp;
        }
    }

    static void roundOyna() {
        //roundun içeriği
        oyuncuEl = new Kart[10];//oyuncunun eline 10 kart
        bilgisayarEl = new Kart[10];//bilgisayarın eline 10 kart
        oyuncuTahta = new Kart[5];//oyuncunun tahtasına 5 kart
        bilgisayarTahta = new Kart[5];//bilgisayar tahtasına 5 kart

        System.out.println("Round " + (oyuncuKazanma + bilgisayarKazanma + 1) + ":");//round bilgisi

        baslangicKartlariniDagit();
        kartlariYazdir();

        oyuncuSira();
        bilgisayarSira();

        roundSonucuDegerlendir();
        //oyunun çalışması için gerekli olan metotları çalıştırıyoruz burada
    }

    static void baslangicKartlariniDagit() {
        //bu metot başlangıçtaki kartların dağıtımını sağlar
        for (int i = 0; i < 5; i++) {
            //destenin düzgün dağıtılmasını sağlayan döngü
            if (desteIndex < deste.length) {
                oyuncuEl[i] = deste[desteIndex++];
            }
            if (desteIndex < deste.length) {
                bilgisayarEl[i] = deste[desteIndex++];
            }
        }

        for (int i = 5; i < 8; i++) {
            //ilk 5 dağıtıldıktan sonra sonraki 3 kart dağıtılır
            if (desteIndex < deste.length) {
                oyuncuEl[i] = deste[desteIndex++];
            }
            if (desteIndex < deste.length) {
                bilgisayarEl[i] = deste[desteIndex++];
            }
        }

        if (desteIndex < deste.length) {
            oyuncuEl[8] = deste[desteIndex++];
        }
        if (desteIndex < deste.length) {
            bilgisayarEl[8] = deste[desteIndex++];
        }

        if (desteIndex < deste.length) {
            oyuncuEl[9] = deste[desteIndex++];
        }
        if (desteIndex < deste.length) {
            bilgisayarEl[9] = deste[desteIndex++];
        }
    }

    static void oyuncuSira() {
        //sıraları belirttiğimiz kısım
        int oyuncuElIndex = 0;

        while (true) {
            //döngünün sürekli çalışması için while içine true koyduk
            System.out.print("Enter 'h' to draw or pass by entering 's': ");
            char secim = tarayici.next().charAt(0);
//h a basrsak kar çeker
            if (secim == 'h') {
                if (desteIndex < deste.length && oyuncuElIndex < oyuncuEl.length) {
                    oyuncuEl[oyuncuElIndex++] = deste[desteIndex++];
                    System.out.println(
                            //kart çekti
                            "The player drew a card: " + oyuncuEl[oyuncuElIndex - 1]);
                } else {
                    //kart bitti
                    System.out.println("There are no more cards in the deck.");
                    break;
                }
                //s e basrsak bekler
            } else if (secim == 's') {
                break;
            } else {
                //h ve s dışında bir şey yazarsak bu sonucu verir
                System.out.println("Invalid option. Please enter 'h' and 's'.");
            }

            kartlariYazdir();
            //oyuncunun skorunu hesaplayan metot
            int oyuncuSkor = elDegeriniHesapla(oyuncuEl, oyuncuElIndex);
            //skoru:
            System.out.println("Person score: " + oyuncuSkor);
            if (oyuncuSkor >= MAX_SKOR) {
                break;
            }
        }
    }

    static void bilgisayarSira() {
        //sıraları belirrtiğimiz kısım
        int bilgisayarElIndex = 0;

        while (elDegeriniHesapla(bilgisayarEl, bilgisayarElIndex) < 4) {
            if (desteIndex < deste.length) {
                bilgisayarEl[bilgisayarElIndex++] = deste[desteIndex++];
                //bilgisayar kartr çekti
                System.out.println("The computer pulled a card: " + bilgisayarEl[bilgisayarElIndex - 1]);
            } else {
                //destede kart kalmadı
                System.out.println("There are no more cards in the deck.");
                break;
            }
        }
    }

    static void roundSonucuDegerlendir() {
        //roundun sonucunu değerlendir
        int oyuncuSkor = elDegeriniHesapla(oyuncuTahta, elBoyutunuHesapla(oyuncuTahta));
        int bilgisayarSkor = elDegeriniHesapla(bilgisayarTahta, elBoyutunuHesapla(bilgisayarTahta));
//bilgisayarın ve kişinin skorunu belirttiğimiz kısım
        System.out.println("Computer Board: " + diziToString(bilgisayarTahta, elBoyutunuHesapla(bilgisayarTahta)) +
                " (Score: " + (bilgisayarSkor <= MAX_SKOR ? bilgisayarSkor : "EXPLODED") + ")");
        System.out.println("Player Board: " + diziToString(oyuncuTahta, elBoyutunuHesapla(oyuncuTahta)) +
                " (Score: " + (oyuncuSkor <= MAX_SKOR ? oyuncuSkor : "EXPLODED") + ")");

        if ((oyuncuSkor <= MAX_SKOR && oyuncuSkor > bilgisayarSkor) || (bilgisayarSkor > MAX_SKOR)) {
            //skor şarları sağlanınca kişi kazanır
            System.out.println("The player won this round!");
            oyuncuKazanma++;
        } else {
            //skor şartları sağlanınca bilgisayar kazanır
            System.out.println("The computer won this round!");
            bilgisayarKazanma++;
        }
    }

    static int elDegeriniHesapla(Kart[] el, int boyut) {
        //elin değerini hesaplayan metot
        int toplam = 0;
        for (int i = 0; i < boyut; i++) {
            toplam += el[i].deger;
        }
        return toplam;
        //toplam döndürür
    }

    static void kartlariYazdir() {
        //bilgisayar ve kişinin ellerini gösteren metot
        System.out.println("Computer Hand: " + diziToString(bilgisayarEl, elBoyutunuHesapla(bilgisayarEl)));
        System.out.println(
                "Player Hand:" + diziToString(oyuncuEl, elBoyutunuHesapla(oyuncuEl)));
    }

    static void oyunDurumuYazdir() {
        //kimin kazandığını yazdıran metot
        System.out.println("Player Wins: " + oyuncuKazanma + " | Computer Wins: " + bilgisayarKazanma);
    }

    static void oyunTarihiniYazdir() {
        //oyun geçmişini tutan metot
        System.out.println("Game History:");
        for (int i = 1; i <= oyuncuKazanma + bilgisayarKazanma; i++) {
            System.out.println("Round " + i + ": Player " + (oyuncuKazanma >= i ? "1" : "0") +
                    " - Computer " + (bilgisayarKazanma >= i ? "1" : "0"));
        }
    }

    static int elBoyutunuHesapla(Kart[] dizi) {
        //elin boyutunun lenghtini hesaplayıp döndüren metot
        for (int i = 0; i < dizi.length; i++) {
            if (dizi[i] == null) {
                return i;
            }
        }
        return dizi.length;
        //burda büyüklüğünü döndürmüşüz
    }

    static String diziToString(Kart[] dizi, int boyut) {
        //eli yazdıran string metodu
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boyut; i++) {
            sb.append(dizi[i].toString()).append(" ");
        }
        return sb.toString();
    }

}
