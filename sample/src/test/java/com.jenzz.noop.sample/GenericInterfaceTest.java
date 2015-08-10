package com.jenzz.noop.sample;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GenericInterfaceTest {

    private final GenericInterface<String, Integer, Exception> genericInterface = new NoOpGenericInterface<>();

    @Test
    public void testDefaultGenericValues() {
        assertThat(genericInterface.genericA()).isNull();
        assertThat(genericInterface.genericB()).isNull();
        assertThat(genericInterface.genericC()).isNull();
    }
}
