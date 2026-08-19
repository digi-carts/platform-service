package com.digicart.platform.repository;

import com.digicart.platform.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for subscription  persistence.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
}
