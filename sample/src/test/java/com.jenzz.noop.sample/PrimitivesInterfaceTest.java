package com.jenzz.noop.sample;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PrimitivesInterfaceTest {

    private final PrimitivesInterface primitivesInterface = new NoOpPrimitivesInterface();

    @Test
    public void testDefaultByteValue() {
        assertThat(primitivesInterface.aByte()).isEqualTo((byte) 0);
    }

    @Test
    public void testDefaultShortValue() {
        assertThat(primitivesInterface.aShort()).isEqualTo((short) 0);
    }

    @Test
    public void testDefaultIntValue() {
        assertThat(primitivesInterface.anInt()).isEqualTo(0);
    }

    @Test
    public void testDefaultLongValue() {
        assertThat(primitivesInterface.aLong()).isEqualTo(0L);
    }

    @Test
    public void testDefaultFloatValue() {
        assertThat(primitivesInterface.aFloat()).isEqualTo(0.0F);
    }

    @Test
    public void testDefaultDoubleValue() {
        assertThat(primitivesInterface.aDouble()).isEqualTo(0.0D);
    }

    @Test
    public void testDefaultCharValue() {
        assertThat(primitivesInterface.aChar()).isEqualTo('\u0000');
    }

    @Test
    public void testDefaultBooleanValue() {
        assertThat(primitivesInterface.aBoolean()).isEqualTo(false);
    }

    @Test
    public void testDefaultStringValue() {
        assertThat(primitivesInterface.aString()).isNull();
    }
}
