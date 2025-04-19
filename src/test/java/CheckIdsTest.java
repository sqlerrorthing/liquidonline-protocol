import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.Packets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class CheckIdsTest {
    @Test
    public void checkIds() {
        Assertions.assertDoesNotThrow(() -> Packets.PACKETS_WITH_ID);
    }
}
