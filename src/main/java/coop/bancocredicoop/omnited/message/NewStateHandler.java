package coop.bancocredicoop.omnited.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import coop.bancocredicoop.omnited.entity.NewState;
import coop.bancocredicoop.omnited.service.rabbit.RabbitMessageHandler;
import coop.bancocredicoop.omnited.service.redis.RedisStreamPublisherService;
import java.util.HashMap;
import java.util.Map;

public class NewStateHandler implements RabbitMessageHandler {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisStreamPublisherService redisStreamPublisherService;

    public NewStateHandler(
            RedisStreamPublisherService redisStreamPublisherService
    ) {
        this.redisStreamPublisherService = redisStreamPublisherService;
    }
    
    
    @Override
    public void handle(String idMensaje, String mensajeJson) throws Exception {
        // Parseo el JSON entrante a un objeto Peer
        NewState state = objectMapper.readValue(mensajeJson, NewState.class);
        System.out.println("El peer es: " + state.getPeer());

        // Construyo un mapa con la información que quiero publicar en Redis
        Map<String, Object> stateData = new HashMap<>();
        
        // Mapeo las propiedades que me interesan:
        stateData.put("ExtensionComunicacion", state.getCallee());
        stateData.put("ExtensionEstado", state.getState());
        stateData.put("ExtensionUltimoTiempo", state.getTime());
        
        try {
            // Publico la actualización en Redis usando el servicio dedicado
            redisStreamPublisherService.publishOperatorUpdate(state.getPeer(), stateData);

        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }
}
