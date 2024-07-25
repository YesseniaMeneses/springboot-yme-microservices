package com.yme.movementsservice;

import org.junit.ClassRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;

@ActiveProfiles("dev-test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MovementsServiceApplication.class)
@SpringBootTest(classes = {MovementsServiceApplication.class})
public class BaseTest {

    private static final String DATABASE = "app_db";
    private static final String USERNAME = "sa";
    private static final String PWD = "sa";
    private static final String URL_FOR_TEST_CONTAINER = "jdbc:tc:mysql://localhost:3306/".concat(DATABASE);
    private static final String SYSTEM_PROP_URL = "spring.datasource.url";
    private static final String SYSTEM_PROP_USERNAME = "spring.datasource.username";
    private static final String SYSTEM_PROP_PASSWORD = "spring.datasource.password";

    public static final Long CLIENT_ID = 123L;
    public static final String ACCOUNT_NUMBER_1 = "2200000000";
    public static final String ACCOUNT_NUMBER_2 = "2200111111";
    public static final String ACCOUNT_NUMBER_3 = "2200222222";
    public static final String AHO = "AHO";
    public static final String COR = "COR";

    @ClassRule
    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.33")
            .withDatabaseName(DATABASE)
            .withUsername(USERNAME)
            .withPassword(PWD);

    // this needs to be in the static block in order that it runs before spring boot
    static {
        mySQLContainer.start();
        System.setProperty(SYSTEM_PROP_URL, URL_FOR_TEST_CONTAINER);
        System.setProperty(SYSTEM_PROP_USERNAME, mySQLContainer.getUsername());
        System.setProperty(SYSTEM_PROP_PASSWORD, mySQLContainer.getPassword());
    }
}
