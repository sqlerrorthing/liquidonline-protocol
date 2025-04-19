package fun.sqlerrorthing.liquidonline.packets;

public class SharedConstants {
    public static final String USERNAME_PATTERN = "^\\w{3,16}$";
    public static final String MINECRAFT_USERNAME_PATTERN = "^\\w{3,16}$";

    public static final String SERVER_IP_PATTERN = "^(singleplayer|((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}|(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(?:\\.(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})(:(6553[0-5]|655[0-2]\\d|65[0-4]\\d{2}|6[0-4]\\d{3}|[1-5]?\\d{1,4}|0))?)$";
}
