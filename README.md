# ileri_programlama_final_projesi
# JavaFinalProjesi

## **Proje Açıklaması**
Bu proje, Binance API kullanarak belirli kripto paritelerinde teknik analiz stratejileri çalıştıran bir **kripto ticaret botudur**. 
Proje, strateji sonuçlarını Telegram üzerinden bildirir ve MariaDB veritabanına kaydeder.

---

## **Projenin Yapısı**

```plaintext
JavaFinalProjesi/
├── .idea/                  # IntelliJ IDEA proje dosyaları
├── src/
│   ├── main/java/com/cryptobot/
│   │   ├── stratejiler/   # Teknik analiz stratejileri
│   │   │   ├── BollingerBands_StochasticRSI_stratejisi.java
│   │   │   ├── EMA9_EMA21_stratejisi.java
│   │   │   ├── MACD_EMA_stratejisi.java
│   │   │   ├── Momentum_stratejisi.java
│   │   │   ├── RSI_stratejisi.java
│   │   │   ├── Stochastic_stratejisi.java
│   │   ├── BinanceAPIClient.java  # Binance API entegrasyonu
│   │   ├── BinanceConfig.java     # API anahtar yönetimi
│   │   ├── Main.java              # Ana çalışma noktası
│   │   ├── MariadbConfig.java     # Veritabanı işlemleri
│   │   ├── PariteYonetimi.java    # Parite verilerini işleme
│   │   ├── StratejiYonetimi.java  # Stratejileri yönetme
│   │   ├── TelegramConfig.java    # Telegram botu yönetimi
│   ├── resources/
│       ├── application.properties # API ve veritabanı yapılandırmaları
├── .gitignore                      # Git tarafından izlenmeyecek dosyalar
├── pom.xml                         # Maven yapılandırma dosyası
├── README.md                       # Proje dokümantasyonu
```


## **Özellikler**
Binance API Entegrasyonu:

Binance üzerinden kripto parite verilerini alma (OHLCV verileri).
Anlık piyasa verilerini çekme.
Teknik Analiz Stratejileri:

EMA9/EMA21: 9 ve 21 günlük üstel hareketli ortalamalar.
MACD: Hareketli ortalamalar yakınsama/ıraksama göstergesi.
Momentum: Fiyat değişim hızını hesaplama.
RSI: Göreceli Güç Endeksi.
Stochastic: Stokastik osilatör.
BollingerBands_StochasticRSI: Bollinger Bantları ile Stokastik RSI kombinasyonu.
Telegram Bildirimleri:

Strateji sonuçlarını Telegram mesajı olarak gönderme.
MariaDB Veritabanı Desteği:

Strateji sonuçlarını kaydetme.
Tüm işlemlerin veritabanında tutulması.





## ***Kullanılan Teknolojiler***
Java: Projenin ana programlama dili.
Maven: Proje yapılandırma ve bağımlılık yönetimi.
Binance API: Kripto para piyasa verilerini almak için.
MariaDB: Strateji sonuçlarını saklamak için veritabanı.
Telegram API: Sinyal sonuçlarını anlık bildirim olarak göndermek için.
JSON: Binance API’den alınan verilerin işlenmesi için.




## **Kurulum**

### 1. Projeyi Klonlayın
```bash
git clone https://github.com/kullaniciAdi/JavaFinalProjesi.git
cd JavaFinalProjesi

mvn install







