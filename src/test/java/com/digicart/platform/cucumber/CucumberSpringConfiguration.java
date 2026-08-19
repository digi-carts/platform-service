package com.digicart.platform.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import com.digicart.platform.exception.GlobalExceptionHandler;
import com.digicart.platform.controller.HealthController;
import com.digicart.platform.controller.SubscriptionController;
import com.digicart.platform.service.SubscriptionService;

@CucumberContextConfiguration
@WebMvcTest(controllers = { HealthController.class, SubscriptionController.class })
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
public class CucumberSpringConfiguration {
    @MockBean
    SubscriptionService subscriptionService;

}
