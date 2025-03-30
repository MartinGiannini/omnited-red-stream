package coop.bancocredicoop.omnited.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisStreamPublisherService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Publica la información completa del operador en un Redis Stream
     * y actualiza la "fuente de verdad" en un hash.
     * @param operatorId
     * @param operatorData Mapa con la información del operador.
     */
    public void publishOperatorUpdate(String operatorId, Map<String, Object> operatorData) {
        // Construir el mensaje que se publicará en el stream
        Map<String, Object> message = new HashMap<>(operatorData);
        message.put("timestamp", System.currentTimeMillis());
        
        // Publicar en el stream llamado "operator-updates"
        String streamKey = "operator-updates";
        redisTemplate.opsForStream().add(streamKey, message);
        System.out.println("Publicado en Redis Stream: " + message);
        
        // Actualizar el hash (fuente de verdad) para que se dispare la keyspace notification.
        // Suponemos que operatorData contiene el identificador del operador en la clave "operatorId"
        String hashKey = "operator:" + operatorId;
        redisTemplate.opsForHash().putAll(hashKey, operatorData);
        System.out.println("Hash actualizado: " + hashKey + " -> " + operatorData);
    }
}