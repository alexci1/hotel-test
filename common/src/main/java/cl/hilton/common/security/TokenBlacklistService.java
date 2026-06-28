package cl.hilton.common.security;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling
public class TokenBlacklistService {

    private final Map<String, Date> blacklist = new ConcurrentHashMap<>();

    public void addToBlacklist(String token, Date expirationDate) {
        blacklist.put(token, expirationDate);
        log.info("Token agregado a la blacklist. Tokens en blacklist: {}", blacklist.size());
    }

    public boolean isBlacklisted(String token) {
        return blacklist.containsKey(token);
    }

    @Scheduled(fixedRate = 600000)
    public void limpiarTokensExpirados() {
        Date ahora = new Date();
        int antes = blacklist.size();
        blacklist.entrySet().removeIf(entry -> entry.getValue().before(ahora));
        int eliminados = antes - blacklist.size();
        if (eliminados > 0) {
            log.info("Blacklist limpiada: {} tokens expirados eliminados. Restantes: {}",
                    eliminados, blacklist.size());
        }
    }
}