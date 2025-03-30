package coop.bancocredicoop.omnited.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import coop.bancocredicoop.omnited.entity.Peer;
import coop.bancocredicoop.omnited.service.rabbit.RabbitMessageHandler;
import coop.bancocredicoop.omnited.service.redis.RedisStreamPublisherService;
import java.util.HashMap;
import java.util.Map;

public class PeerStatusHandler implements RabbitMessageHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisStreamPublisherService redisStreamPublisherService;

    public PeerStatusHandler(
            RedisStreamPublisherService redisStreamPublisherService
    ) {
        this.redisStreamPublisherService = redisStreamPublisherService;
    }
    
    
    @Override
    public void handle(String idMensaje, String mensajeJson) throws Exception {
        // Parseo el JSON entrante a un objeto Peer
        Peer peer = objectMapper.readValue(mensajeJson, Peer.class);
        System.out.println("El peer es: " + peer.getPeer());

        // Construyo un mapa con la información que quiero publicar en Redis
        Map<String, Object> peerData = new HashMap<>();

        // Mapeo las propiedades que trae el mensaje.
        peerData.put("ExtensionPeer", peer.getPeer());
        peerData.put("ExtensionEstado", peer.getPeerStatus());
        peerData.put("ExtensionUltimoTiempo", peer.getTime());

        try {
            // Publico la actualización en Redis usando el servicio dedicado
            redisStreamPublisherService.publishOperatorUpdate(peer.getPeer(), peerData);

        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

}
