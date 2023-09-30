package pet.project_test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pet.project_test.Controller.OpenWeatherService.OpenWeatherApiService;
import pet.project_test.Controller.OpenWeatherService.WeatherEntity.OpenWeatherLocationDTO;
import pet.project_test.Entity.Location.Location;
import pet.project_test.Entity.Location.LocationDAO;
import pet.project_test.Entity.Location.LocationSearchDTO;
import pet.project_test.Entity.User.User;
import pet.project_test.Entity.User.UserDAO;
import pet.project_test.Exception.ExceptionUserAlreadyExists;

import java.math.BigDecimal;
import java.util.List;

public class OpenWeatherApiTest {
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
    public  void LocationDTOFromJSONTest() throws JsonProcessingException {
        var list = OpenWeatherApiService.objectMapper.readValue(
                simpeCity,
                new TypeReference<List<LocationSearchDTO>>() {}
        );
        Assertions.assertEquals("Ryazan",list.get(0).getName());
        Assertions.assertEquals("RU",list.get(0).getCountry());
        Assertions.assertEquals(new BigDecimal("54.6295687"),list.get(0).getLat());
    }
    @Test
    public void LocationManytoOneTest() throws ExceptionUserAlreadyExists {
        String login = "asd@asd";
        User user = new User(login,"asd@asd");
        UserDAO.save(user);
        var location = new Location(user,"RZN",new BigDecimal("0.2222"),new BigDecimal("0.22222"));
        LocationDAO.save(location);
        user = UserDAO.findByLogin(login).get();
        Assertions.assertEquals(location.getLatitide(),location.getLatitide());
        Assertions.assertEquals(location.getLongitide(),location.getLongitide());
        Assertions.assertEquals(location.getName(),location.getName());
        LocationDAO.deleteForId(location.getId());

    }
    @Test
    public  void WeatherLocationDTOFromJSONTest() throws JsonProcessingException {
        var weather = OpenWeatherApiService.objectMapper.readValue(
                simpleWeather,
                new TypeReference<OpenWeatherLocationDTO>() {}
        );
        Assertions.assertEquals("San Francisco",weather.getName());
        Assertions.assertEquals(9.39,weather.getWind().getSpeed());

    }
}
