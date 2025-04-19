package fun.sqlerrorthing.liquidonline.packets;

/**
 * Enum representing the direction of a packet, indicating whether it is sent from the client to the server
 * or from the server to the client.
 *
 * <p>This enum is used to identify the sender of a packet. It helps to understand the direction
 * of communication in a client-server interaction.</p>
 *
 * <p>The {@link PacketBound#CLIENT} value indicates that the packet is sent from the client to the server
 * (e.g., a login request from the client), while {@link PacketBound#SERVER} indicates that the packet
 * is sent from the server to the client (e.g., a response from the server).</p>
 */
public enum PacketBound {
    /**
     * The packet is sent from the client to the server.
     */
    CLIENT,

    /**
     * The packet is sent from the server to the client.
     */
    SERVER
}
