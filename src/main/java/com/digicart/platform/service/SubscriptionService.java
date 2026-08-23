package com.digicart.platform.service;

import com.digicart.platform.dto.SubscriptionDto;
import com.digicart.platform.entity.Subscription;
import com.digicart.platform.exception.EntityNotFoundException;
import com.digicart.platform.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service implementing subscription use cases for <em>platform-service</em>.
 */
@Service
public class SubscriptionService {

    private final SubscriptionRepository repository;

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public List<Subscription> findAll() {
        return repository.findAll();
    }

    public Subscription findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found: " + id));
    }

    public Subscription create(SubscriptionDto.CreateRequest req) {
        Subscription sub = new Subscription();
        sub.setName(req.getName());
        if (req.getMaxProducts() != null) sub.setMaxProducts(req.getMaxProducts());
        if (req.getPrice() != null) sub.setPrice(req.getPrice());
        if (req.getCurrency() != null) sub.setCurrency(req.getCurrency());
        if (req.getBillingPeriod() != null) sub.setBillingPeriod(req.getBillingPeriod());
        sub.setCustomDays(req.getCustomDays());
        if (req.getFeatures() != null) sub.setFeatures(req.getFeatures());
        sub.setDetails(req.getDetails());
        sub.setLevel(req.getLevel());
        if (req.getMaxUses() != null) sub.setMaxUses(req.getMaxUses());
        return repository.save(sub);
    }

    public Subscription update(String id, SubscriptionDto.UpdateRequest req) {
        Subscription sub = findById(id);
        if (req.getName() != null) sub.setName(req.getName());
        if (req.getMaxProducts() != null) sub.setMaxProducts(req.getMaxProducts());
        if (req.getPrice() != null) sub.setPrice(req.getPrice());
        if (req.getCurrency() != null) sub.setCurrency(req.getCurrency());
        if (req.getBillingPeriod() != null) sub.setBillingPeriod(req.getBillingPeriod());
        if (req.getCustomDays() != null) sub.setCustomDays(req.getCustomDays());
        if (req.getFeatures() != null) sub.setFeatures(req.getFeatures());
        if (req.getDetails() != null) sub.setDetails(req.getDetails());
        if (req.getLevel() != null) sub.setLevel(req.getLevel().isBlank() ? null : req.getLevel());
        if (req.getMaxUses() != null) sub.setMaxUses(req.getMaxUses());
        return repository.save(sub);
    }

    public List<String> findNamesByLevel(String levelKey) {
        return repository.findAll().stream()
                .filter(s -> levelKey.equals(s.getLevel()))
                .map(Subscription::getName)
                .toList();
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }
}
