package com.digicart.platform.service;

import com.digicart.platform.entity.AdminStatus;
import com.digicart.platform.entity.TicketStatus;
import com.digicart.platform.repository.AdminUserRepository;
import com.digicart.platform.repository.SubscriptionRepository;
import com.digicart.platform.repository.SupportTicketRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Aggregates platform-wide stats from multiple schemas for the superadmin dashboard.
 */
@Service
public class AnalyticsService {

    private final AdminUserRepository adminRepo;
    private final SubscriptionRepository subscriptionRepo;
    private final SupportTicketRepository ticketRepo;
    private final JdbcTemplate jdbc;

    public AnalyticsService(AdminUserRepository adminRepo,
                            SubscriptionRepository subscriptionRepo,
                            SupportTicketRepository ticketRepo,
                            JdbcTemplate jdbc) {
        this.adminRepo = adminRepo;
        this.subscriptionRepo = subscriptionRepo;
        this.ticketRepo = ticketRepo;
        this.jdbc = jdbc;
    }

    public Map<String, Object> getStats() {
        // --- platform_svc (JPA) ---
        long adminTotal = adminRepo.count();
        long adminActive = adminRepo.countByStatus(AdminStatus.ACTIVE);

        long subscriptionTotal = subscriptionRepo.count();

        long ticketTotal = ticketRepo.count();
        long ticketPending = ticketRepo.countByStatusIn(
                List.of(TicketStatus.OPEN, TicketStatus.PENDING, TicketStatus.INPROGRESS));

        // --- store_svc (cross-schema) ---
        long storeTotal = count("SELECT COUNT(*) FROM store_svc.stores");
        long storePublished = count("SELECT COUNT(*) FROM store_svc.stores WHERE published = true");
        long storeExpired = count("SELECT COUNT(*) FROM store_svc.stores WHERE available_days <= 0");

        // --- order_svc (cross-schema) ---
        long orderTotal = count("SELECT COUNT(*) FROM order_svc.orders");
        BigDecimal revenue = jdbc.queryForObject(
                "SELECT COALESCE(SUM(total), 0) FROM order_svc.orders", BigDecimal.class);

        // --- auth_svc customers (role='user', cross-schema) ---
        long customerTotal = count("SELECT COUNT(*) FROM auth_svc.users WHERE role = 'user'");
        long customerActive = count(
                "SELECT COUNT(*) FROM auth_svc.users WHERE role = 'user' AND created_at >= NOW() - INTERVAL '30 days'");
        long customerInactive = customerTotal - customerActive;

        return Map.of(
                "admins", Map.of("total", adminTotal, "active", adminActive),
                "subscriptions", Map.of("total", subscriptionTotal),
                "stores", Map.of("total", storeTotal, "published", storePublished, "expired", storeExpired),
                "orders", Map.of("total", orderTotal, "revenue", revenue != null ? revenue : BigDecimal.ZERO),
                "customers", Map.of("total", customerTotal, "active", customerActive, "inactive", customerInactive),
                "supportTickets", Map.of("total", ticketTotal, "pending", ticketPending)
        );
    }

    private long count(String sql) {
        Long result = jdbc.queryForObject(sql, Long.class);
        return result != null ? result : 0L;
    }
}
