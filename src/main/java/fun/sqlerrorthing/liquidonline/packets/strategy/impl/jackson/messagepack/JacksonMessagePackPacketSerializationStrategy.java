package fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.messagepack;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.json.JacksonJsonPacketSerializationStrategy;
import org.msgpack.jackson.dataformat.MessagePackFactory;

/**
 * The strategy is a wrapper class around
 * {@link JacksonJsonPacketSerializationStrategy} but configured to use MessagePack
 * serialization and deserialization instead of JSON.
 *
 * <p>This class extends {@link JacksonJsonPacketSerializationStrategy} and utilizes
 * the Jackson ObjectMapper configured with a MessagePackFactory. It reuses the JSON
 * serialization logic, but changes the underlying data format to MessagePack, which
 * is a binary serialization format, providing more compact data representation than
 * JSON.</p>
 *
 * <p><b>Note</b>: By default, this class does not include the necessary dependencies for
 * MessagePack or Jackson. You need to ensure that the following dependencies are
 * available in your classpath:</p>
 * <ul>
 *     <li>jackson-databind</li>
 *     <li>jackson-datatype-jsr310</li>
 *     <li>jackson-dataformat-msgpack</li>
 * </ul>
 *
 * <p>Before using this class, ensure that these libraries are included in your project
 * dependencies. If they are not included, the application will fail to initialize due to
 * missing dependencies.</p>
 */
public class JacksonMessagePackPacketSerializationStrategy extends JacksonJsonPacketSerializationStrategy {
    public JacksonMessagePackPacketSerializationStrategy() {
        super(new ObjectMapper(new MessagePackFactory()));
    }
}
