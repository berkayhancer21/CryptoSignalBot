
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

### **1. Binance API Entegrasyonu**
- **Kripto Parite Verilerini Alma:**
  - Binance üzerinden OHLCV (Open, High, Low, Close, Volume) verilerini çekme.
- **Anlık Piyasa Verileri:**
  - Belirlenen kripto paritelerinin güncel piyasa bilgilerini alma.

### **2. Teknik Analiz Stratejileri**
- **EMA9/EMA21:**
  - 9 ve 21 günlük üstel hareketli ortalamalar ile al/sat sinyalleri üretir.
- **MACD (Moving Average Convergence Divergence):**
  - Hareketli ortalamalar yakınsama/ıraksama göstergesi ile trend analizi yapar.
- **Momentum:**
  - Fiyat değişim hızını hesaplar ve al/sat sinyalleri oluşturur.
- **RSI (Relative Strength Index):**
  - Göreceli Güç Endeksi ile aşırı alım ve aşırı satım bölgelerini tespit eder.
- **Stochastic Oscillator:**
  - Stokastik osilatör kullanarak piyasa trendlerini analiz eder.
- **BollingerBands & StochasticRSI:**
  - Bollinger Bantları ve Stokastik RSI kombinasyonu ile piyasa analizleri yapar.

### **3. Telegram Bildirimleri**
- **Anlık Mesaj Gönderimi:**
  - Teknik analiz stratejilerinin sonuçlarını Telegram üzerinden mesaj olarak gönderir.

### **4. MariaDB Veritabanı Desteği**
- **Sonuçların Kaydedilmesi:**
  - Teknik analiz stratejilerinden üretilen al/sat sinyallerini veritabanına kaydeder.
- **Verilerin Saklanması:**
  - Tüm işlemleri veritabanında tutarak daha sonra erişim ve analiz sağlar.






## ***Kullanılan Teknolojiler***
Java: Projenin ana programlama dili.
Maven: Proje yapılandırma ve bağımlılık yönetimi.
Binance API: Kripto para piyasa verilerini almak için.
MariaDB: Strateji sonuçlarını saklamak için veritabanı.
Telegram API: Sinyal sonuçlarını anlık bildirim olarak göndermek için.
JSON: Binance API’den alınan verilerin işlenmesi için.




## **Kurulum**

## *** Projeyi Klonlayın***
```bash
git clone https://github.com/kullaniciAdi/JavaFinalProjesi.git
cd JavaFinalProjesi

mvn install
```    


## **Bağımlılıkları Yükleyin**
```bash
mvn install
```


## ***application.properties Dosyasını Düzenleyin***

src/main/resources/application.properties dosyasını açın ve Binance API anahtarlarınızı ve MariaDB bağlantı bilgilerinizi doldurun:

```bash
binance.apiKey=YOUR_BINANCE_API_KEY
binance.secretKey=YOUR_BINANCE_SECRET_KEY

db.url=jdbc:mysql://HOSTNAME:PORT/DB_NAME
db.username=YOUR_DB_USERNAME
db.password=YOUR_DB_PASSWORD

telegram.botToken=YOUR_TELEGRAM_BOT_TOKEN
telegram.chatId=YOUR_TELEGRAM_CHAT_ID
```

## ***Kullanım***

1. Projeyi Çalıştırma
Proje aşağıdaki komutla çalıştırılabilir:
```bash
mvn exec:java -Dexec.mainClass="com.cryptobot.Main"
```

2. Çalışma Süreci
  Binance API’den parite verileri alınır.
  Teknik analiz stratejileri çalıştırılır:
    EMA9/EMA21
    MACD
    Momentum
    RSI
    Stochastic
    BollingerBands & StochasticRSI
    Strateji sonuçları:
    Telegram’a gönderilir.
    MariaDB veritabanına kaydedilir.


## ***Stratejiler***

EMA9_EMA21: 9 günlük ve 21 günlük üstel hareketli ortalamalara göre al/sat sinyali üretir.

MACD: Hareketli ortalamalar yakınsama/ıraksama göstergesine göre sinyal üretir.

Momentum: Fiyat değişim hızını hesaplar ve sinyal üretir.

RSI: Göreceli Güç Endeksine göre aşırı alım/aşırı satım sinyalleri üretir.

Stochastic: Stokastik osilatör kullanılarak sinyal üretir.

BollingerBands_StochasticRSI: Bollinger Bantları ve Stokastik RSI kombinasyonu ile sinyal üretir.






