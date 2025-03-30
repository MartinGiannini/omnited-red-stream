package coop.bancocredicoop.omnited.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import coop.bancocredicoop.omnited.entity.Operador;
import coop.bancocredicoop.omnited.entity.Peer;
import coop.bancocredicoop.omnited.service.rabbit.RabbitMessageHandler;
import coop.bancocredicoop.omnited.service.redis.RedisStreamPublisherService;
import java.util.HashMap;
import java.util.Map;

public class OperadorStatusHandler implements RabbitMessageHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisStreamPublisherService redisStreamPublisherService;

    public OperadorStatusHandler(
            RedisStreamPublisherService redisStreamPublisherService
    ) {
        this.redisStreamPublisherService = redisStreamPublisherService;
    }
    
    
    @Override
    public void handle(String idMensaje, String mensajeJson) throws Exception {
        
        // Parseo el JSON entrante a un objeto Peer
        Operador operador = objectMapper.readValue(mensajeJson, Operador.class);
        System.out.println("El peer es: " + operador.getUsuarioUsuario());
        
        // Construyo un mapa con la información que quiero publicar en Redis
        Map<String, Object> operatorData = new HashMap<>();
        
        // Asumo que 'peer' es el identificador del operador.
        operatorData.put("OperadorId", operador.getUsuarioUsuario());
        operatorData.put("OperadorIdEstado", operador.getIdEstado());
        

        try {
            // Publico la actualización en Redis usando el servicio dedicado
            redisStreamPublisherService.publishOperatorUpdate(operador.getUsuarioUsuario(), operatorData);

        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

}
