package com.digicart.platform.cucumber;

import com.digicart.platform.entity.Subscription;
import com.digicart.platform.service.SubscriptionService;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;

public class SubscriptionStepDefinitions {
    @Autowired
    SubscriptionService subscriptionService;

    @Before
    public void stubs() {
        when(subscriptionService.findAll()).thenReturn(List.of(new Subscription()));
    }
}
