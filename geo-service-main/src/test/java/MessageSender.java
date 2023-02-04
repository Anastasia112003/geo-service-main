import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;

public class MessageSender {
    @Test
    void russianMessage() {
        String ip = "172.0.0.0";
        HashMap mapRus = new HashMap();
        mapRus.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String preferences = messageSender.send(mapRus);
        String expected = "Добро пожаловать";
        Assertions.assertEquals(preferences, expected);

    }

    @Test
    void englishMessage() {
        String ip = "96.0.0.0";
        HashMap mapEng = new HashMap();
        mapEng.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(new Location("New York", Country.USA, null, 0));
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        String preferences = messageSender.send(mapEng);
        String expected = "Welcome";
        Assertions.assertEquals(preferences, expected);
    }

    @Test
    void locationByIpTest() {
        String ip = "96.0.0.0";
        Location expected = new Location("New York", Country.USA, " 10th Avenue", 32);
        GeoService geoService = new GeoServiceImpl();
        Location preferences = geoService.byIp(ip);
        Assertions.assertEquals(expected.getCountry(), preferences.getCountry());
    }

    @Test
    void localeTest() {
        String expected = "Добро пожаловать";
        String preferences = new LocalizationServiceImpl().locale(Country.RUSSIA);
        Assertions.assertEquals(expected, preferences);

    }
}
