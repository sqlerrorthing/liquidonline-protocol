import fun.sqlerrorthing.liquidonline.packets.strategy.impl.netty.buffer.buffer.NettyBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestNettyBufferSerialization {
    @Test
    public void testSerialization() throws IOException {
        NettyBuffer buf = new NettyBuffer();

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
