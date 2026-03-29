package com.carbooking.taskApp.api.config;

import com.carbooking.dto.AuthRequestDto;
import com.carbooking.taskApp.utils.LogUtil;
import com.carbooking.utils.TestNGListener;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;


@Listeners(TestNGListener.class)
public class BaseApiTest {

    protected static final Logger log = LoggerFactory.getLogger(BaseApiTest.class);
    public static String token;

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://dev.pshacks.org/api/v3";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );

        log.info("✅ BaseApiTest initialised");
        log.info(" BaseURI: {}", RestAssured.baseURI);
    }


//    protected String login() {
//        LogUtil.step("Logging in");
//
//
//        AuthRequestDto loginRequest = AuthRequestDto.builder()
//                .email("test808@test.me")
//                .password("password123")
//                .build();
//
//
//        Response response = RestAssured
//                .given()
//                .log().all()
//                .contentType(ContentType.JSON)
//                .body(loginRequest)
//                .when()
//                .post("/auth/login")
//                .then()
//                .extract()
//                .response();
//        response.then().log().all();
//
//                LogUtil.apiResponse(response.getStatusCode());
//        assertEquals(response.getStatusCode(), 200, "Error during login attempt!");
//
//        // Извлекаем токен из JSON-ответа
//        String token = response.getBody().path("token");
//
//        LogUtil.info("The token has been successfully obtained.");
//
//        return token;
//    protected String login() {
//        LogUtil.step("Попытка входа с точной имитацией порядка полей");
//
//        // Используем LinkedHashMap для сохранения порядка полей
//        Map<String, String> credentials = new LinkedHashMap<>();
//        credentials.put("email", "test807@test.me");
//        credentials.put("password", "password123");
//
//
//        Response response = RestAssured
//                .given()
//                // Маскировка под браузер
//                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
//                .header("Accept", "application/json, text/plain, */*")
//                // Добавляем Origin (адрес вашего фронтенда без слэша в конце)
//                .header("Origin", "https://dev.pshacks.org")
//                .header("Referer", "https://dev.pshacks.org/login")
//                .contentType(ContentType.JSON)
//                .body(credentials)
//                .when()
//                .post("/auth/login");
//
//        // Выводим полный ответ для диагностики
//        System.out.println("--- ПОЛНЫЙ ЛОГ ОТВЕТА ---");
//        response.then().log().all();
//
//        if (response.getStatusCode() != 200) {
//            // Если опять 401, выводим сообщение из тела ответа
//            String errorMsg = response.jsonPath().getString("error");
//            System.err.println("Ошибка сервера: " + errorMsg);
//            assertEquals(response.getStatusCode(), 200, "Логин не удался: " + errorMsg);
//        }
//
//        String token = response.jsonPath().getString("token");
//        if (token == null) {
//            token = response.jsonPath().getString("accessToken");
//        }
//
//        return token;
//    }
//    protected String login() {
//        LogUtil.step("Попытка логина с полной имитацией браузерного запроса");
//
//        // Используем LinkedHashMap для строгого порядка: password затем email
//        Map<String, String> credentials = new LinkedHashMap<>();
//        credentials.put("email", "test808@test.me");
//        credentials.put("password", "password123");
//
//
//        Response response = RestAssured
//                .given()
//                // Полный набор заголовков как в Chrome
//                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
//                .header("Accept", "application/json, text/plain, */*")
//                .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
//                .header("Origin", "https://dev.pshacks.org")
//                .header("Referer", "https://dev.pshacks.org/login")
//                .header("Sec-Fetch-Dest", "empty")
//                .header("Sec-Fetch-Mode", "cors")
//                .header("Sec-Fetch-Site", "same-origin")
//                .contentType(ContentType.JSON)
//                .body(credentials)
//                .when()
//                .post("/auth/login");
//
//        // Печатаем лог ответа для анализа
//        System.out.println("--- АНАЛИЗ ОТВЕТА СЕРВЕРА ---");
//        response.then().log().all();
//
//        if (response.getStatusCode() != 200) {
//            System.out.println("Тело ошибки: " + response.getBody().asString());
//        }
//
//        assertEquals(response.getStatusCode(), 200, "Сервер отклонил логин (401). Проверьте базу данных!");
//
//        // Извлекаем токен из ответа
//        String token = response.jsonPath().getString("token");
//        if (token == null) {
//            token = response.jsonPath().getString("accessToken");
//        }
//
//        System.out.println("Успешный логин. Токен получен.");
//        return token;
//    }
//    protected String login() {
//        LogUtil.step("Попытка логина с максимальной имитацией браузера");
//
//        // Используем LinkedHashMap для сохранения порядка полей: сначала password, потом email
//        // Это соответствует вашему скриншоту из Swagger
//        Map<String, String> credentials = new LinkedHashMap<>();
//        credentials.put("email", "test808@test.me");
//        credentials.put("password", "!12345Qwerty");
//
//
//        Response response = RestAssured
//                .given()
//                // Эмуляция реального пользователя (User-Agent и дополнительные заголовки)
//                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
//                .header("Accept", "application/json, text/plain, */*")
//                .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
//                .header("Origin", "https://dev.pshacks.org")
//                .header("Referer", "https://dev.pshacks.org/login")
//                // Заголовки безопасности Fetch Metadata (используются современными браузерами)
//                .header("Sec-Fetch-Dest", "empty")
//                .header("Sec-Fetch-Mode", "cors")
//                .header("Sec-Fetch-Site", "same-origin")
//                .contentType(ContentType.JSON)
//                .body(credentials)
//                .when()
//                // Проверьте, что в RestAssured.baseURI указан верный адрес домена
//                .post("/auth/login");
//
//        // Печатаем подробный лог ответа для финальной диагностики
//        System.out.println("=== ДИАГНОСТИКА ОТВЕТА СЕРВЕРА ===");
//        response.then().log().all();
//
//        LogUtil.apiResponse(response.getStatusCode());
//
//        // Если статус не 200, выводим тело ошибки для анализа в консоли
//        if (response.getStatusCode() != 200) {
//            System.err.println("Сервер ответил ошибкой: " + response.getBody().asString());
//        }
//
//        assertEquals(response.getStatusCode(), 200, "Не удалось авторизоваться через API. Проверьте учетные данные или IP-блокировку.");
//
//        // Извлекаем токен из ответа (пробуем разные ключи)
//        String token = response.jsonPath().getString("token");
//        if (token == null) {
//            token = response.jsonPath().getString("accessToken");
//        }
//
//        System.out.println("Авторизация успешна. JWT Токен получен.");
//        return token;
//    }
    protected String login() {
        // 1. Формируем тело запроса ровно как в Swagger
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", "test808@test.me");
        credentials.put("password", "!12345Qwerty");

        // 2. Отправляем запрос на /auth/login
        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body(credentials)
                .when()
                .post("/auth/login");

        // 3. Проверка: если в Swagger 200, то и тут должно быть 200
        if (response.getStatusCode() != 200) {
            System.out.println("ОШИБКА: Сервер вернул " + response.getStatusCode());
            response.prettyPrint();
        }

        assertEquals(response.getStatusCode(), 200, "Авторизация не прошла!");

        // 4. Забираем accessToken (именно так он называется в твоем Swagger)
        String token = response.jsonPath().getString("accessToken");

        return token;
    }
}


