package com.jenzz.noop.sample;

import com.jenzz.noop.annotation.NoOp;

@NoOp
public interface GenericInterface<A, B, C> {

    A genericA();

    B genericB();

    C genericC();
}
