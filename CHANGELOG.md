# Changelog

## [1.0.0] - 2026-08-29

### Features
- implement GET /api/platform/cleanup/schema endpoint
- add AI config, info-content, cloudflare DNS, firebase domains, AI chat, and admin user management endpoints
- add /api/platform/manage endpoint
- add GCP Cloud Run service status endpoint
- add setup-wizard config endpoint
- add subscription level/maxUses fields and business-level CRUD endpoints
- expand platform-config API with sub-paths, PATCH merge, and cloudflare test
- add /api/platform/analytics endpoint for superadmin dashboard
- add JavaDoc, health aliases, and component tests

### Bug Fixes
- add SQL console backend and analytics SUPERADMIN guard
- enforce SUPERADMIN role on platform admin endpoints
- align support controller paths with /api/platform/ prefix
- only call Thread.interrupt() for InterruptedException in ServiceStatusController
- add PATCH /api/platform/templates/:id to enable/disable a template
- add GET /api/platform/templates endpoint
- migrate UUID columns to varchar(36) to fix Hibernate String id binding
- change @JdbcTypeCode(SqlTypes.JSON) fields from String to Map<String,Object>
- add @JdbcTypeCode(SqlTypes.JSON) to jsonb columns to fix type mismatch
- remove liquibase default-schema to allow fresh DB bootstrap
- update controller @RequestMapping paths to match gateway routes
- run create-schema always so it recreates if missing
- accept any checksum for idempotent create-schema changeset
- limit HikariCP pool to 2 connections (db-f1-micro max 25 total)
- disable Hibernate validation (Liquibase owns schema, uuid vs String mismatch)
- set liquibase-schema=public so schema is created before tracking tables
- add Cloud SQL postgres-socket-factory for Cloud Run connectivity

### Documentation
- add complete project documentation

### CI/Build
- retrigger prod deploy
- retrigger after db-g1-small upgrade
- trigger first dev build
- use separate GCP project IDs for dev (digi-carts-dev) and prod (digi-carts)