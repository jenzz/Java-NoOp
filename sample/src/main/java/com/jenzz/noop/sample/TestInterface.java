package com.jenzz.noop.sample;

import com.jenzz.noop.annotation.NoOp;

@NoOp
public interface TestInterface {

    byte aByte();
    Byte aByteObject();

    short aShort();
    Short aShortObject();

    int anInt();
    Integer anIntObject();

    long aLong();
    Long aLongObject();

    float aFloat();
    Float aFloatObject();

    double aDouble();
    Double aDoubleObject();

    char aChar();
    Character aCharObject();

    boolean aBoolean();
    Boolean aBooleanObject();

    void aVoid();
}
