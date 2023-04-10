package com.yldrmsrkn.Inventory.enums;

import java.util.Arrays;
import java.util.List;

public enum Region {
    MARMARA("Marmara", City.ISTANBUL, City.BURSA, City.KOCAELI, City.TEKIRDAG, City.YALOVA, City.SAKARYA, City.BILECIK), EGE("Ege", City.IZMIR, City.AYDIN, City.MANISA, City.DENIZLI, City.MUGLA, City.USAK, City.KUTAHYA), AKDENIZ("Akdeniz", City.ANTALYA, City.ADANA, City.MERSIN, City.HATAY, City.ISPARTA, City.BURDUR, City.OSMANIYE, City.KARAMAN), KARADENIZ("Karadeniz", City.TRABZON, City.SAMSUN, City.ORDU, City.ZONGULDAK, City.RIZE, City.AMASYA, City.SINOP, City.TOKAT), DOGU_ANADOLU(
            "Doğu Anadolu", City.ERZURUM, City.VAN, City.ARDAHAN, City.KARS, City.MALATYA, City.ELAZIG, City.TUNCELI, City.BINGOL), IC_ANADOLU("İç Anadolu", City.ANKARA, City.KONYA, City.KIRIKKALE, City.KIRSEHIR, City.AKSARAY, City.NEVSEHIR, City.NIGDE, City.YOZGAT, City.SIVAS, City.KAYSERI), GUNEYDOGU_ANADOLU("Güneydoğu Anadolu", City.DIYARBAKIR, City.GAZIANTEP, City.SANLIURFA, City.ADIYAMAN, City.MARDIN, City.SIIRT, City.BATMAN, City.HAKKARI);

    private final String name;
    private final List<City> cities;

    Region(String name, City... cities) {
        this.name = name;
        this.cities = Arrays.asList(cities);
    }

    public String getName() {
        return name;
    }

    public List<City> getCities() {
        return cities;
    }
}

