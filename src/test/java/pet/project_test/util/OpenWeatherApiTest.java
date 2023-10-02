package pet.project_test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import pet.project_test.Controller.OpenWeatherService.OpenWeatherApiService;
import pet.project_test.Entity.Location.Location;
import pet.project_test.Entity.Location.LocationDAO;
import pet.project_test.Controller.OpenWeatherService.WeatherEntity.LocationSearchDTO;
import pet.project_test.Entity.Session.SessionDAO;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OpenWeatherApiTest {
    protected OpenWeatherApiService openWeatherApiService = new OpenWeatherApiService();
    protected UserDAO userDAO= new UserDAO();
    protected SessionDAO sessionDAO= new SessionDAO();
    protected LocationDAO locationDAO = new LocationDAO();



    String simpeCity = "[\n" +
            "    {\n" +
            "        \"name\": \"Ryazan\",\n" +
            "        \"local_names\": {\n" +
            "            \"mr\": \"रायझन\",\n" +
            "            \"da\": \"Rjasan\",\n" +
            "            \"ar\": \"ريازان\",\n" +
            "            \"nn\": \"Rjazan\",\n" +
            "            \"tl\": \"Ryazan\",\n" +
            "            \"lv\": \"Rjazaņa\",\n" +
            "            \"ko\": \"랴잔\",\n" +
            "            \"id\": \"Ryazan\",\n" +
            "            \"be\": \"Разань\",\n" +
            "            \"de\": \"Rjasan\",\n" +
            "            \"ascii\": \"Ryazan\",\n" +
            "            \"eo\": \"Rjazan\",\n" +
            "            \"vi\": \"Ryazan\",\n" +
            "            \"bg\": \"Рязан\",\n" +
            "            \"nl\": \"Rjazan\",\n" +
            "            \"cs\": \"Rjazaň\",\n" +
            "            \"sk\": \"Riazaň\",\n" +
            "            \"zh\": \"梁赞\",\n" +
            "            \"hr\": \"Rjazanj\",\n" +
            "            \"ur\": \"ریازان\",\n" +
            "            \"cu\": \"Рѣꙁан҄ь\",\n" +
            "            \"af\": \"Rjazan\",\n" +
            "            \"no\": \"Rjazan\",\n" +
            "            \"he\": \"ריאזן\",\n" +
            "            \"fi\": \"Rjazan\",\n" +
            "            \"en\": \"Ryazan\",\n" +
            "            \"pt\": \"Riazan\",\n" +
            "            \"ro\": \"Riazan\",\n" +
            "            \"lt\": \"Riazanė\",\n" +
            "            \"oc\": \"Riazan\",\n" +
            "            \"az\": \"Ryazan\",\n" +
            "            \"ru\": \"Рязань\",\n" +
            "            \"it\": \"Rjazan'\",\n" +
            "            \"fa\": \"ریازان\",\n" +
            "            \"el\": \"Ριαζάν\",\n" +
            "            \"ca\": \"Riazan\",\n" +
            "            \"ja\": \"リャザン\",\n" +
            "            \"et\": \"Rjazan\",\n" +
            "            \"fr\": \"Riazan\",\n" +
            "            \"ba\": \"Рязань\",\n" +
            "            \"cy\": \"Ryazan\",\n" +
            "            \"sr\": \"Рјазањ\",\n" +
            "            \"hu\": \"Rjazany\",\n" +
            "            \"tt\": \"Рәзән\",\n" +
            "            \"tr\": \"Ryazan\",\n" +
            "            \"tg\": \"Рязан\",\n" +
            "            \"ka\": \"რიაზანი\",\n" +
            "            \"hy\": \"Ռյազան\",\n" +
            "            \"la\": \"Resania\",\n" +
            "            \"es\": \"Riazán\",\n" +
            "            \"uk\": \"Рязань\",\n" +
            "            \"ga\": \"Ryazan\",\n" +
            "            \"br\": \"Ryazan\",\n" +
            "            \"sv\": \"Rjazan\",\n" +
            "            \"cv\": \"Кисан\",\n" +
            "            \"feature_name\": \"Ryazan\",\n" +
            "            \"pl\": \"Riazań\"\n" +
            "        },\n" +
            "        \"lat\": 54.6295687,\n" +
            "        \"lon\": 39.7425039,\n" +
            "        \"country\": \"RU\",\n" +
            "        \"state\": \"Ryazan Oblast\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Razan\",\n" +
            "        \"local_names\": {\n" +
            "            \"en\": \"Razan\",\n" +
            "            \"fa\": \"رازان\"\n" +
            "        },\n" +
            "        \"lat\": 33.5644898,\n" +
            "        \"lon\": 48.8697854,\n" +
            "        \"country\": \"IR\",\n" +
            "        \"state\": \"Lorestan Province\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Razanj\",\n" +
            "        \"local_names\": {\n" +
            "            \"sr\": \"Ражањ\",\n" +
            "            \"en\": \"Razanj\",\n" +
            "            \"ru\": \"Ржань\"\n" +
            "        },\n" +
            "        \"lat\": 43.6724063,\n" +
            "        \"lon\": 21.5489925,\n" +
            "        \"country\": \"RS\",\n" +
            "        \"state\": \"Central Serbia\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Razan\",\n" +
            "        \"local_names\": {\n" +
            "            \"fa\": \"رزن\",\n" +
            "            \"fr\": \"Razan\",\n" +
            "            \"ja\": \"ラザン\",\n" +
            "            \"ku\": \"Rezen\",\n" +
            "            \"ru\": \"Резен\",\n" +
            "            \"en\": \"Razan\"\n" +
            "        },\n" +
            "        \"lat\": 35.3884459,\n" +
            "        \"lon\": 49.0344948,\n" +
            "        \"country\": \"IR\",\n" +
            "        \"state\": \"Hamadan Province\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Razan\",\n" +
            "        \"local_names\": {\n" +
            "            \"en\": \"Razan\",\n" +
            "            \"fa\": \"رزن\"\n" +
            "        },\n" +
            "        \"lat\": 36.2016203,\n" +
            "        \"lon\": 52.1814108,\n" +
            "        \"country\": \"IR\",\n" +
            "        \"state\": \"Mazandaran Province\"\n" +
            "    }\n" +
            "]";

    String simpleWeather =
            "{\n" +
            "    \"coord\": {\n" +
            "        \"lon\": -122.4199,\n" +
            "        \"lat\": 37.779\n" +
            "    },\n" +
            "    \"weather\": [\n" +
            "        {\n" +
            "            \"id\": 804,\n" +
            "            \"main\": \"Clouds\",\n" +
            "            \"description\": \"overcast clouds\",\n" +
            "            \"icon\": \"04d\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"base\": \"stations\",\n" +
            "    \"main\": {\n" +
            "        \"temp\": 13.5,\n" +
            "        \"feels_like\": 13.23,\n" +
            "        \"temp_min\": 12.17,\n" +
            "        \"temp_max\": 15.43,\n" +
            "        \"pressure\": 1007,\n" +
            "        \"humidity\": 89\n" +
            "    },\n" +
            "    \"visibility\": 10000,\n" +
            "    \"wind\": {\n" +
            "        \"speed\": 9.39,\n" +
            "        \"deg\": 315,\n" +
            "        \"gust\": 10.73\n" +
            "    },\n" +
            "    \"clouds\": {\n" +
            "        \"all\": 100\n" +
            "    },\n" +
            "    \"dt\": 1696084439,\n" +
            "    \"sys\": {\n" +
            "        \"type\": 2,\n" +
            "        \"id\": 2017837,\n" +
            "        \"country\": \"US\",\n" +
            "        \"sunrise\": 1696082648,\n" +
            "        \"sunset\": 1696125307\n" +
            "    },\n" +
            "    \"timezone\": -25200,\n" +
            "    \"id\": 5391959,\n" +
            "    \"name\": \"San Francisco\",\n" +
            "    \"cod\": 200\n" +
            "}";

    @Test
    public void LocationManytoOneTest(){
        String login = "asd@asd";
        User user = new User(login,"asd@asd");
        userDAO.save(user);
        var location = new Location(user,"RZN",new BigDecimal("0.2222"),new BigDecimal("0.22222"));
        locationDAO.save(location);
        user = userDAO.findByLogin(login).get();
        Assertions.assertEquals(location.getLatitide(),location.getLatitide());
        Assertions.assertEquals(location.getLongitide(),location.getLongitide());
        Assertions.assertEquals(location.getName(),location.getName());
        locationDAO.deleteForId(location.getId());

    }

    private HttpClient createMockHttpClientforFoundCity() throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);

        URI uri = openWeatherApiService.buildUriForGeocodingRequest("Simple");
        HttpRequest request = OpenWeatherApiService.buildRequest(uri);

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockResponse.body()).thenReturn(simpeCity);

        when(httpClient.send(request,HttpResponse.BodyHandlers.ofString())).thenReturn(mockResponse);
        return httpClient;
    }
    private HttpClient createMockHttpClientforFoundWeather() throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);

        URI uri = openWeatherApiService.buildUriForWeatherForLocationRequest(new BigDecimal("0"),new BigDecimal("0"));
        HttpRequest request = OpenWeatherApiService.buildRequest(uri);

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockResponse.body()).thenReturn(simpleWeather);

        when(httpClient.send(request,HttpResponse.BodyHandlers.ofString())).thenReturn(mockResponse);
        return httpClient;
    }
    @Test
    public void testLocationFound() throws IOException, InterruptedException {
        openWeatherApiService= new OpenWeatherApiService(createMockHttpClientforFoundCity());
        List<LocationSearchDTO> location = openWeatherApiService.geocodingByName("Simple");
        Assertions.assertEquals("Ryazan",location.get(0).getName());
    }
    @Test
    public void testWeatherFound() throws IOException, InterruptedException {
        openWeatherApiService= new OpenWeatherApiService(createMockHttpClientforFoundWeather());
        var weather = openWeatherApiService.weatherForLocation(
                (new Location(new User("pass", "pass"), "Mock", new BigDecimal("0"), new BigDecimal("0"))));
        Assertions.assertEquals(13.5,weather.getMain().getTemp());
        Assertions.assertEquals("San Francisco",weather.getName());
    }
}
