package com.jenzz.noop.sample;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtendingInterface1Test {

    private final ExtendingInterface1 extendingInterface1 = new NoOpExtendingInterface1();

    @Test
    public void testDefaultGenericValues() {
        assertThat(extendingInterface1.fromInterface1()).isNull();
        assertThat(extendingInterface1.fromInterface2()).isNull();
        assertThat(extendingInterface1.fromInterface3()).isNull();
        assertThat(extendingInterface1.fromInterface4()).isNull();
        assertThat(extendingInterface1.fromInterface5()).isNull();
    }
}
