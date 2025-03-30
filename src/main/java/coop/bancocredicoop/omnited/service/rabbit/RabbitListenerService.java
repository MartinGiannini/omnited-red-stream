package coop.bancocredicoop.omnited.service.rabbit;

import java.util.Map;
import java.util.HashMap;
import coop.bancocredicoop.omnited.config.MessageOut.MensajeJSON;
import coop.bancocredicoop.omnited.message.NewStateHandler;
import coop.bancocredicoop.omnited.message.OperadorDataHandler;
import coop.bancocredicoop.omnited.message.OperadorStatusHandler;
import coop.bancocredicoop.omnited.message.PeerStatusHandler;
import coop.bancocredicoop.omnited.service.redis.RedisStreamPublisherService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitListenerService {

    private final Map<String, RabbitMessageHandler> handlers = new HashMap<>();

    /**
     * Servicios que se registran en la carga de la aplicación.
     *
     * @param redisStreamPublisherService
     */
    public RabbitListenerService(
            RedisStreamPublisherService redisStreamPublisherService
    ) {
        // Registrar handlers para cada tipo de mensaje
        
        // operadorStatus => Solo tendrá el estado actual del operador.
        handlers.put("OperadorStatus", new OperadorStatusHandler(redisStreamPublisherService));
        
        // operadorDataDB => Tendrá los datos completos del operador (Nombre, apellido, Usuario, Habilidades, Estados, Permisos)
        handlers.put("OperadorDataDB", new OperadorDataHandler(redisStreamPublisherService));
        
        // peerStatusAST => Tendrá los datos del Peer (Peer, Status, Time)
        handlers.put("PeerStatusAST", new PeerStatusHandler(redisStreamPublisherService));
        
        // newStateAST => Tendrá los datos del nuevo estado del peer (Destino, Time)
        handlers.put("NewStateAST", new NewStateHandler(redisStreamPublisherService));
    }

    /**
     * Colas registradas en Rabbit. Esta aplicación solo escuchará mensajes
     * provinientes de esas colas.
     *
     * @param message
     */
    @RabbitListener(queues = {
        "#{@environment.getProperty('spring.rabbitmq.colaAST')}",
        "#{@environment.getProperty('spring.rabbitmq.colaDBStream')}"
    })

    /**
     * Método que buscará manejar los mensajes de acuerdo al TYPE.
     *
     */
    public void receiveMessage(MensajeJSON message) {

        try {
            String idMensaje = message.getIdMensaje();
            String mensajeType = message.getMensajeType();
            String mensajeJson = message.getMensajeJson();

            // Identificar y procesar el mensaje según su tipo
            RabbitMessageHandler handler = handlers.get(mensajeType);
            
            System.out.println("");
            System.out.println("************************");
            System.out.println("El mensaje que llega es: " + mensajeType);
            System.out.println("************************");
            System.out.println("");
            
            if (handler != null) {
                handler.handle(idMensaje, mensajeJson);
            } else {
                System.err.println("No handler found for type: " + mensajeType);
            }
            
        } catch (Exception e) {
            System.err.println("Error handling message: " + e.getMessage());
        }
    }
}
