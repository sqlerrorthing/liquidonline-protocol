import fun.sqlerrorthing.liquidonline.dto.party.InvitedMemberDto;
import fun.sqlerrorthing.liquidonline.dto.party.PartyDto;
import fun.sqlerrorthing.liquidonline.dto.party.PartyMemberDto;
import fun.sqlerrorthing.liquidonline.dto.play.HealthDto;
import fun.sqlerrorthing.liquidonline.dto.play.PlayDto;
import fun.sqlerrorthing.liquidonline.dto.play.PositionDto;
import fun.sqlerrorthing.liquidonline.dto.play.RotationDto;
import fun.sqlerrorthing.liquidonline.packets.s2c.party.S2CInvitePartyMemberResult;
import fun.sqlerrorthing.liquidonline.packets.s2c.party.S2CPartyMemberPlayUpdate;
import fun.sqlerrorthing.liquidonline.packets.s2c.party.S2CPartySync;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.NettyBuffer;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.NettyBufferPacketSerializationStrategy;
import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.compilertime.CompilerTimeByteBufPacketSerializationStrategy;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class TestNettyBufferSerialization {
    @Test
    public void testSerialization() throws IOException {
        NettyBuffer buf = NettyBuffer.builder().build();

        var children = new Person(
                "Children1",
                2,
                102,
                Color.BLACK,
                new Person[0]
        );

        var person = new Person(
                "Test1",
                25,
                173,
                null,
                new Person[] { children }
        );

        var buff = Unpooled.buffer();
        buf.serialize(buff, person);

        var deserializedPersonBytes = buff.array();
        var deserializedPerson = buf.deserialize(Unpooled.wrappedBuffer(deserializedPersonBytes), Person.class);

        Assertions.assertEquals(person, deserializedPerson);
    }

    @Test
    public void testEnum() throws IOException {
        var strategy = new NettyBufferPacketSerializationStrategy();
        var packet = S2CInvitePartyMemberResult.builder()
                .result(S2CInvitePartyMemberResult.Result.INVITED)
                .build();

        var serialized = strategy.serializePacket(packet);
        var deserialized = strategy.deserializePacket(serialized);

        Assertions.assertEquals(packet, deserialized);
    }

    public static void main(String[] args) throws IOException {
        new TestNettyBufferSerialization().testBigPacketSerialization();
    }

    @Test
    public void testMiniPacketSerialization() throws IOException {
        var strategy = new NettyBufferPacketSerializationStrategy();

        var packet = S2CPartyMemberPlayUpdate.builder()
                .memberId(20)
                .data(randomPlayDto())
                .build();

        var serialized = strategy.serializePacket(packet);
        var deserialized = strategy.deserializePacket(serialized);

        Assertions.assertEquals(packet, deserialized);
    }

    @Test
    public void testBigPacketSerialization() throws IOException {
        var strategy = new CompilerTimeByteBufPacketSerializationStrategy();

        var members = new ArrayList<PartyMemberDto>();

        members.add(buildDummyPartyMember(0, "sqlerrorthing", "sqlerrorthing", Color.RED, true));
        members.add(buildDummyPartyMember(1, "xakerxlop", "xakerxlop", Color.BLACK, true));
        members.add(buildDummyPartyMember(2, "1zuna", "1zun4", Color.WHITE, false));
        members.add(buildDummyPartyMember(3, "superblaubeere2", "superblaubeere27", Color.CYAN, true));
        members.add(buildDummyPartyMember(4, "Mukja", "Mukja", Color.PINK, false));
        members.add(buildDummyPartyMember(5, "sssl", "sssl", Color.DARK_GRAY, true));
        members.add(buildDummyPartyMember(6, "sosiska", "schizophrenia_bl", Color.GREEN, true));

        var invited = new ArrayList<InvitedMemberDto>();

        var dummyParty = PartyDto.builder()
                .id(UUID.randomUUID())
                .name("DummyParty")
                .partyPublic(false)
                .maxMembers(10)
                .ownerId(0)
                .members(members)
                .invitedMembers(invited)
                .build();

        var packet = S2CPartySync.builder()
                .party(dummyParty)
                .build();

        var serialized = strategy.serializePacket(packet);
        var deserialized = strategy.deserializePacket(serialized);

        Assertions.assertEquals(packet, deserialized);
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

    @SuppressWarnings("unused")
    public static class Person {
        private String name;
        private int age;
        private int height;
        private Color color;

        private Person[] childrens;

        public Person() {

        }

        public Person(String name, int age, int height, Color color, Person[] childrens) {
            this.name = name;
            this.age = age;
            this.height = height;
            this.color = color;
            this.childrens = childrens;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && height == person.height && Objects.equals(name, person.name) && Objects.equals(color, person.color) && Objects.deepEquals(childrens, person.childrens);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, height, color, Arrays.hashCode(childrens));
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", height=" + height +
                    ", color=" + color +
                    ", childrens=" + Arrays.toString(childrens) +
                    '}';
        }
    }

}
