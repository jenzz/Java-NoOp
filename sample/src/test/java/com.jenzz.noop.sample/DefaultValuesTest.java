package com.jenzz.noop.sample;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultValuesTest {

    private final TestInterface testInterface = new NoOpTestInterface();

    @Test
    public void testDefaultByteValue() {
        assertThat(testInterface.aByte()).isEqualTo((byte) 0);
    }

    @Test
    public void testDefaultShortValue() {
        assertThat(testInterface.aShort()).isEqualTo((short) 0);
    }

    @Test
    public void testDefaultIntValue() {
        assertThat(testInterface.anInt()).isEqualTo(0);
    }

    @Test
    public void testDefaultLongValue() {
        assertThat(testInterface.aLong()).isEqualTo(0L);
    }

    @Test
    public void testDefaultFloatValue() {
        assertThat(testInterface.aFloat()).isEqualTo(0.0F);
    }

    @Test
    public void testDefaultDoubleValue() {
        assertThat(testInterface.aDouble()).isEqualTo(0.0D);
    }

    @Test
    public void testDefaultCharValue() {
        assertThat(testInterface.aChar()).isEqualTo('\u0000');
    }

    @Test
    public void testDefaultBooleanValue() {
        assertThat(testInterface.aBoolean()).isEqualTo(false);
    }

    @Test
    public void testDefaultStringValue() {
        assertThat(testInterface.aString()).isNull();
    }
}
