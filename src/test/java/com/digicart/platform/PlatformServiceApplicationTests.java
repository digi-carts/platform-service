package com.digicart.platform;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Needs DATABASE_URL / JWT_SECRET; covered by WebMvcTest and Cucumber")
@SpringBootTest
class PlatformServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
