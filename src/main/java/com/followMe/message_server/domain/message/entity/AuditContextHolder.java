package com.followMe.message_server.domain.message.entity;

import java.util.UUID;

public class AuditContextHolder {

    private static final ThreadLocal<UUID> CURRENT_AUDITOR = new ThreadLocal<>();

    public static void set(UUID userId) {
        CURRENT_AUDITOR.set(userId);
    }

    public static UUID get() {
        return CURRENT_AUDITOR.get();
    }

    public static void clear() {
        CURRENT_AUDITOR.remove();
    }
}
