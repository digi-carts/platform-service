package com.digicart.platform.service;

import com.digicart.platform.dto.SubscriptionDto;
import com.digicart.platform.entity.Subscription;
import com.digicart.platform.exception.EntityNotFoundException;
import com.digicart.platform.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository repository;

    @InjectMocks
    private SubscriptionService service;

    @Test
    void createSetsNameAndDefaults() {
        SubscriptionDto.CreateRequest req = new SubscriptionDto.CreateRequest();
        req.setName("Pro");
        when(repository.save(any(Subscription.class))).thenAnswer(i -> i.getArgument(0));
        Subscription sub = service.create(req);
        assertThat(sub.getName()).isEqualTo("Pro");
        assertThat(sub.getMaxProducts()).isEqualTo(50);
    }

    @Test
    void findByIdThrows() {
        when(repository.findById("x")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById("x")).isInstanceOf(EntityNotFoundException.class);
    }
}
