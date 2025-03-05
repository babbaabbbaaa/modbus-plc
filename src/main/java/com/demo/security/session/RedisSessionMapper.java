package com.demo.security.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Slf4j
public class RedisSessionMapper implements BiFunction<String, Map<String, Object>, MapSession> {

    /**
     * The key in the hash representing {@link Session#getCreationTime()}.
     */
    private static final String CREATION_TIME_KEY = "creationTime";

    /**
     * The key in the hash representing {@link Session#getLastAccessedTime()}.
     */
    private static final String LAST_ACCESSED_TIME_KEY = "lastAccessedTime";

    /**
     * The key in the hash representing {@link Session#getMaxInactiveInterval()}.
     */
    private static final String MAX_INACTIVE_INTERVAL_KEY = "maxInactiveInterval";

    /**
     * The prefix of the key in the hash used for session attributes. For example, if the
     * session contained an attribute named {@code attributeName}, then there would be an
     * entry in the hash named {@code sessionAttr:attributeName} that mapped to its value.
     */
    private static final String ATTRIBUTE_PREFIX = "sessionAttr:";

    public RedisSessionMapper(Set<String> heartbeats) {
        this.heartbeats = heartbeats;
    }

    private static void handleMissingKey(String key) {
        throw new IllegalStateException(key + " key must not be null");
    }

    private final Set<String> heartbeats;

    @Override
    public MapSession apply(String sessionId, Map<String, Object> map) {
        Assert.hasText(sessionId, "sessionId must not be empty");
        Assert.notEmpty(map, "map must not be empty");
        MapSession session = new MapSession(sessionId);
        Long creationTime = (Long) map.get(CREATION_TIME_KEY);
        if (creationTime == null) {
            handleMissingKey(CREATION_TIME_KEY);
        }
        session.setCreationTime(Instant.ofEpochMilli(creationTime));
        Long lastAccessedTime = (Long) map.get(LAST_ACCESSED_TIME_KEY);
        if (lastAccessedTime == null) {
            handleMissingKey(LAST_ACCESSED_TIME_KEY);
        }
        if (!isHeartbeat()) {
            session.setLastAccessedTime(Instant.ofEpochMilli(lastAccessedTime));
        }
        Integer maxInactiveInterval = (Integer) map.get(MAX_INACTIVE_INTERVAL_KEY);
        if (maxInactiveInterval == null) {
            handleMissingKey(MAX_INACTIVE_INTERVAL_KEY);
        }
        session.setMaxInactiveInterval(Duration.ofSeconds(maxInactiveInterval));
        map.forEach((name, value) -> {
            if (name.startsWith(ATTRIBUTE_PREFIX)) {
                session.setAttribute(name.substring(ATTRIBUTE_PREFIX.length()), value);
            }
        });
        return session;
    }

    private boolean isHeartbeat() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String requestUri = ((ServletRequestAttributes) attributes).getRequest().getRequestURI();
            //                log.info("heartbeat uri: {}", requestUri);
            return heartbeats.contains(requestUri);
        }
        return false;
    }
}
