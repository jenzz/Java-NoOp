package com.jenzz.noop.sample;

import com.jenzz.noop.annotation.NoOp;

@NoOp
public interface GenericInterface<A, B, C extends Throwable> {

    A genericA();

    B genericB();

    C genericC();
}
