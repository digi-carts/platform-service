package com.digicart.platform.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
        String schema = jdbc.queryForObject("SELECT current_schema()", String.class);
        List<Map<String, Object>> tables = jdbc.queryForList(
                "SELECT t.table_name, COUNT(c.column_name) AS column_count " +
                "FROM information_schema.tables t " +
                "JOIN information_schema.columns c " +
                "  ON c.table_name = t.table_name AND c.table_schema = t.table_schema " +
                "WHERE t.table_schema = ? AND t.table_type = 'BASE TABLE' " +
                "GROUP BY t.table_name ORDER BY t.table_name",
                schema);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("schema", schema);
        result.put("tableCount", tables.size());
        result.put("tables", tables);
        return result;
    }
}
