package coop.bancocredicoop.omnited.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import coop.bancocredicoop.omnited.entity.Operador;
import coop.bancocredicoop.omnited.service.rabbit.RabbitMessageHandler;
import coop.bancocredicoop.omnited.service.redis.RedisStreamPublisherService;
import java.util.HashMap;
import java.util.Map;

public class OperadorDataHandler implements RabbitMessageHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisStreamPublisherService redisStreamPublisherService;

    public OperadorDataHandler(
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
        operatorData.put("OperadorNombre", operador.getUsuarioNombre());
        operatorData.put("OperadorApellido", operador.getUsuarioApellido());
        operatorData.put("OperadorCorreo", operador.getUsuarioCorreo());
        operatorData.put("OperadorEstado", operador.getUsuarioEstado());
        operatorData.put("OperadorHabilidad", operador.getUsuarioHabilidad());
        operatorData.put("OperadorPermisos", operador.getUsuarioPermisoOperacion());
        operatorData.put("OperadorIdSector", operador.getIdSector());
        operatorData.put("OperadorIdEstado", operador.getIdEstado());

        try {
            // Publico la actualización en Redis usando el servicio dedicado
            redisStreamPublisherService.publishOperatorUpdate(operador.getUsuarioUsuario(), operatorData);

        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

}
