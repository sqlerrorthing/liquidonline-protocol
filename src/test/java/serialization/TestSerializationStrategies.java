package serialization;

import fun.sqlerrorthing.liquidonline.dto.party.InvitedMemberDto;
import fun.sqlerrorthing.liquidonline.dto.party.PartyDto;
import fun.sqlerrorthing.liquidonline.dto.party.PartyMemberDto;
import fun.sqlerrorthing.liquidonline.dto.play.HealthDto;
import fun.sqlerrorthing.liquidonline.dto.play.PlayDto;
import fun.sqlerrorthing.liquidonline.dto.play.PositionDto;
import fun.sqlerrorthing.liquidonline.dto.play.RotationDto;
import fun.sqlerrorthing.liquidonline.packets.Packet;
import fun.sqlerrorthing.liquidonline.packets.s2c.party.S2CPartyMemberPlayUpdate;
import fun.sqlerrorthing.liquidonline.packets.s2c.party.S2CPartySync;
import fun.sqlerrorthing.liquidonline.packets.strategy.PacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.gson.json.GsonJsonPacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.json.JacksonJsonPacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.jackson.messagepack.JacksonMessagePackPacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.CompilerTimeByteBufPacketSerializationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public class TestSerializationStrategies {
    static Stream<Arguments> strategyProvider() {
        return Stream.of(
                Arguments.of(new CompilerTimeByteBufPacketSerializationStrategy()),
                Arguments.of(new JacksonJsonPacketSerializationStrategy()),
                Arguments.of(new GsonJsonPacketSerializationStrategy()),
                Arguments.of(new JacksonMessagePackPacketSerializationStrategy())
        );
    }

    static Stream<Arguments> strategyWithDifferentSerializationAndDeserialization() {
        return Stream.of(
                Arguments.of(
                        new JacksonJsonPacketSerializationStrategy(),
                        new GsonJsonPacketSerializationStrategy()
                ),
                Arguments.of(
                        new GsonJsonPacketSerializationStrategy(),
                        new JacksonJsonPacketSerializationStrategy()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("strategyProvider")
    void testMiniPacketWithNull(PacketSerializationStrategy strategy) throws IOException {
        var packet = S2CPartyMemberPlayUpdate.builder()
                .memberId(20)
                .data(null)
                .build();

        var serialized = strategy.serializePacket(packet);
        var deserialized = strategy.deserializePacket(serialized);

        Assertions.assertEquals(packet, deserialized);
    }

    @ParameterizedTest
    @MethodSource("strategyProvider")
    void testBigPacketWithSameSerializationAndDeserializationStrategy(PacketSerializationStrategy strategy) throws IOException {
        testBigPacketWithDifferentSerializationAndDeserializationStrategy(strategy, strategy);
    }

    @ParameterizedTest
    @MethodSource("strategyWithDifferentSerializationAndDeserialization")
    void testBigPacketWithDifferentSerializationAndDeserializationStrategy(PacketSerializationStrategy serializationStrategy, PacketSerializationStrategy deserializationStrategy) throws IOException {
        var packet = bigPacket();

        var serialized = serializationStrategy.serializePacket(packet);
        var deserialized = deserializationStrategy.deserializePacket(serialized);

        Assertions.assertEquals(packet, deserialized);
    }

    private Packet bigPacket() {
        var members = new ArrayList<PartyMemberDto>();

        members.add(buildDummyPartyMember(0, "sqlerrorthing", "sqlerrorthing", Color.RED, true));
        members.add(buildDummyPartyMember(1, "xakerxlop", "xakerxlop", Color.BLACK, true));
        members.add(buildDummyPartyMember(2, "1zuna", "1zun4", Color.WHITE, false));
        members.add(buildDummyPartyMember(3, "superblaubeere2", "superblaubeere27", Color.CYAN, true));
        members.add(buildDummyPartyMember(4, "Mukja", "Mukja", Color.PINK, true));
        members.add(buildDummyPartyMember(5, "sssl", "sssl", Color.DARK_GRAY, true));
        members.add(buildDummyPartyMember(6, "sosiska", "schizophrenia_bl", Color.GREEN, true));
        members.add(buildDummyPartyMember(7, "member", "topppperrrkk", new Color(203, 21, 102), true));
        members.add(buildDummyPartyMember(8, "xlamidia", "plaa", new Color(203, 0, 255), true));

        var invited = new ArrayList<InvitedMemberDto>();

        invited.add(buildInvited("InvitedMember1", 1));
        invited.add(buildInvited("InvitedMember2", 6));
        invited.add(buildInvited("InvitedMember3", 5));
        invited.add(buildInvited("InvitedMember4", 4));
        invited.add(buildInvited("InvitedMember5", 3));
        invited.add(buildInvited("InvitedMember6", 2));
        invited.add(buildInvited("InvitedMember", 1));
        invited.add(buildInvited("InvitedMember71", 1));
        invited.add(buildInvited("asdd", 1));
        invited.add(buildInvited("jdvsa", 1));
        invited.add(buildInvited("qmuadh", 1));
        invited.add(buildInvited("jfvjdsfj", 1));
        invited.add(buildInvited("djfjdaeiwque", 1));
        invited.add(buildInvited("io34i7qwmoeiu", 1));
        invited.add(buildInvited("jdsgfhjasd", 1));

        var dummyParty = PartyDto.builder()
                .id(UUID.randomUUID())
                .name("DummyParty")
                .partyPublic(false)
                .maxMembers(10)
                .ownerId(0)
                .members(members)
                .invitedMembers(invited)
                .build();

        return S2CPartySync.builder()
                .party(dummyParty)
                .build();
    }

    private InvitedMemberDto buildInvited(String username, int senderId) {
        return InvitedMemberDto.builder()
                .inviteUuid(UUID.randomUUID())
                .username(username)
                .senderId(senderId)
                .build();
    }

    private PartyMemberDto buildDummyPartyMember(int id, String name, String minecraftName, Color color, boolean usePlayData) {
        return PartyMemberDto.builder()
                .memberId(id)
                .username(name)
                .minecraftUsername(minecraftName)
                .skin(new byte[] { 22, 21, -23, 111, -10, 32 })
                .color(color)
                .playData(usePlayData ? randomPlayDto() : null)
                .build();
    }

    private PlayDto randomPlayDto() {
        var random = new Random();

        return PlayDto.builder()
                .entityId(random.nextInt())
                .dimension("minecraft:ownerworld")
                .server("mc.funtime.su")
                .health(HealthDto.builder()
                        .health(random.nextFloat(20))
                        .maxHealth(20)
                        .hurtTime(0)
                        .build())
                .position(PositionDto.builder()
                        .x(random.nextFloat())
                        .y(random.nextFloat())
                        .z(random.nextFloat())
                        .build())
                .rotation(RotationDto.builder()
                        .yaw(random.nextFloat(180))
                        .pitch(random.nextFloat(90))
                        .build())
                .build();
    }
}
