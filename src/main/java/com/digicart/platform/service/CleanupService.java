package com.digicart.platform.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CleanupService {

    private final JdbcTemplate jdbc;

    public CleanupService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Map<String, Object> getSchemaInfo() {
        List<Map<String, Object>> colRows = jdbc.queryForList(
                "SELECT c.table_schema, c.table_name, c.column_name, c.data_type, c.is_nullable " +
                "FROM information_schema.columns c " +
                "JOIN information_schema.tables t " +
                "  ON t.table_name = c.table_name AND t.table_schema = c.table_schema " +
                "WHERE c.table_schema NOT IN ('information_schema','pg_catalog','pg_toast') " +
                "  AND t.table_type = 'BASE TABLE' " +
                "ORDER BY c.table_schema, c.table_name, c.ordinal_position");

        List<Map<String, Object>> pkRows = jdbc.queryForList(
                "SELECT kcu.table_schema, kcu.table_name, kcu.column_name " +
                "FROM information_schema.table_constraints tc " +
                "JOIN information_schema.key_column_usage kcu " +
                "  ON tc.constraint_name = kcu.constraint_name AND tc.table_schema = kcu.table_schema " +
                "WHERE tc.constraint_type = 'PRIMARY KEY' " +
                "  AND tc.table_schema NOT IN ('information_schema','pg_catalog','pg_toast') " +
                "ORDER BY kcu.table_schema, kcu.table_name, kcu.ordinal_position");

        Map<String, Map<String, List<Map<String, Object>>>> tree = new LinkedHashMap<>();
        for (Map<String, Object> row : colRows) {
            String schema = (String) row.get("table_schema");
            String table = (String) row.get("table_name");
            Map<String, Object> col = new LinkedHashMap<>();
            col.put("name", row.get("column_name"));
            col.put("type", row.get("data_type"));
            col.put("nullable", "YES".equals(row.get("is_nullable")));
            tree.computeIfAbsent(schema, k -> new LinkedHashMap<>())
                .computeIfAbsent(table, k -> new ArrayList<>())
                .add(col);
        }

        Map<String, Map<String, List<String>>> pks = new LinkedHashMap<>();
        for (Map<String, Object> row : pkRows) {
            String schema = (String) row.get("table_schema");
            String table = (String) row.get("table_name");
            pks.computeIfAbsent(schema, k -> new LinkedHashMap<>())
               .computeIfAbsent(table, k -> new ArrayList<>())
               .add((String) row.get("column_name"));
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("tree", tree);
        result.put("pks", pks);
        return result;
    }

    public Map<String, Object> executeQuery(String query) {
        String normalized = query.trim().toLowerCase();
        Map<String, Object> result = new LinkedHashMap<>();
        if (normalized.startsWith("select") || normalized.startsWith("with")) {
            List<Map<String, Object>> rows = jdbc.queryForList(query);
            result.put("type", "select");
            result.put("rows", rows);
            result.put("count", rows.size());
        } else {
            int affected = jdbc.update(query);
            result.put("type", "exec");
            result.put("affected", affected);
        }
        return result;
    }
}
