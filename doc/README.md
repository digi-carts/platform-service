# platform-service

Platform SaaS control plane: subscription plans, super-admin users, global config, and support tickets. Port **3002**, schema **`platform_svc`**.

Platform design: [System design](https://github.com/digi-carts/doc/blob/main/architecture/system-design.md)

## Domain

Used by **platform-ui** (superadmin) and merchant subscription screens. Plans cap product counts and encode feature flags in JSON. Support tickets have comments. `PlatformConfig` is a singleton-style settings document.

## Tech stack

Java 21, Spring Boot 3.3.0, Web, JPA, Validation, Liquibase, PostgreSQL.

## Data model

| Entity | Table | Purpose |
|--------|--------|---------|
| `Subscription` | `subscriptions` | Plan name, `max_products`, price, `BillingPeriod` (MONTHLY / …), `features` jsonb |
| `AdminUser` | `admin_users` | Platform operators; lookup by email |
| `PlatformConfig` | `platform_config` | Global PUT/GET settings |
| `SupportTicket` | `support_tickets` | Merchant/platform tickets (`TicketType`, `TicketStatus`) |
| `TicketComment` | `ticket_comments` | Thread on a ticket |

## HTTP API

Gateway prefixes: `/api/platform/**`, `/api/subscriptions/**`, `/api/admin/**`, `/api/templates/**`, `/api/support/**`.

### Subscriptions — `/subscriptions`

| Method | Path |
|--------|------|
| GET | `/subscriptions` |
| GET | `/subscriptions/{id}` |
| POST | `/subscriptions` |
| PUT | `/subscriptions/{id}` |
| DELETE | `/subscriptions/{id}` |

### Admin users — `/admin-users`

| Method | Path |
|--------|------|
| GET | `/admin-users` |
| GET | `/admin-users/{id}` |
| GET | `/admin-users/by-email/{email}` |
| POST | `/admin-users` |
| PUT | `/admin-users/{id}` |
| DELETE | `/admin-users/{id}` |

### Platform config — `/platform-config`

| Method | Path |
|--------|------|
| GET | `/platform-config` |
| PUT | `/platform-config` |

### Support — `/support-tickets`, `/ticket-comments`

CRUD list/get/create/update/delete on tickets; list/get/create/delete on comments.

### Health

`GET /health`

Headers: optional `X-User-Id`, `X-User-Role`.

## Configuration

| Variable | Required | Default |
|----------|----------|---------|
| `DATABASE_URL` | yes | schema `platform_svc` |
| `PORT` | no | `3002` |

## Local run

```bash
export DATABASE_URL="jdbc:postgresql://localhost:5432/digicarts?currentSchema=platform_svc"
mvn spring-boot:run
```

## CI/CD

`digi-cart-platform-service-dev` / `digi-cart-platform-service` on Cloud Run.

## Related

- [platform-ui](https://github.com/digi-carts/platform-ui/blob/stage/doc/README.md)
- [auth-service](https://github.com/digi-carts/auth-service/blob/stage/doc/README.md)
- [payment-service](https://github.com/digi-carts/payment-service/blob/stage/doc/README.md) (`PaymentType.SUBSCRIPTION`)
