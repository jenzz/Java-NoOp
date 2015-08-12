package com.jenzz.noop.sample;

import com.jenzz.noop.annotation.NoOp;

@NoOp
public interface ExtendingInterface1 extends ExtendingInterface2 {

    Object fromInterface1();
}
