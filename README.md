# PROJE ADI: BESTIMOL

## PROJE AÇIKLAMASI
Bu proje, Spring Boot kullanarak oluşturulmuş ve PostgreSQL ile entegre edilmiş bir web uygulamasıdır. Projede Liquibase kullanılarak veritabanı değişiklikleri yönetilmektedir ve Swagger ile API dokümantasyonu sağlanmaktadır.

## İÇERİK

### 1. PROJE OLUŞTURULDU
- **SPRING INITIALIZR İLE PROJE OLUŞTURULDU.**

### 2. POSTGRESQL VE SWAGGER ENTEGRASYONU YAPILDI
- **POSTGRESQL**: Veritabanı olarak PostgreSQL kullanıldı.
- **SWAGGER**: API dokümantasyonu için Swagger entegre edildi.

### 3. LIQUIBASE ENTEGRASYONU YAPILDI
- **LIQUIBASE**: Veritabanı değişikliklerini yönetmek için Liquibase kullanıldı.
- **POSTGRESQL ÜZERİNDEN DB OLUŞTURULDU.**

### 4. LIQUIBASE KULLANIMI
- **LIQUIBASE KOMUTLARINI ÇALIŞTIRABİLMEK İÇİN MAVEN CLEAN INSTALL VE MAVEN LIQUIBASE UPDATE KOMUTLARI KULLANILDI.**
- Proje çalıştırılmadan önce şu adımlar takip edilmelidir:
    1. **MAVEN CLEAN INSTALL**: `mvn clean install`
    2. **MAVEN LIQUIBASE UPDATE**: `mvn liquibase:update`
