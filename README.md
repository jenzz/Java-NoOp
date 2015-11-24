Java - NoOp [![Build Status](https://travis-ci.org/jenzz/Java-NoOp.svg?branch=develop)](https://travis-ci.org/jenzz/Java-NoOp)
===========

This is probably one of the simplest Java annotation processing libraries out there.

It generates [no-op implementations](https://en.wikipedia.org/wiki/NOP) of your interfaces.

* Supports **Generics** & **Multiple Inheritance**

Usage
-----
* Simply annotate your interface with `@NoOp` like this:

```java
@NoOp
public interface TestInterface {

    byte aByte();

    short aShort();

    int anInt();

    long aLong();

    float aFloat();

    double aDouble();

    char aChar();

    boolean aBoolean();

    String aString();

    void aVoid();
}
```

* And it will generate the following no-op implementation at compile time:

```java
public class NoOpTestInterface implements TestInterface {

  public static final TestInterface INSTANCE = new NoOpTestInterface();

  @Override
  public byte aByte() {
    return (byte) 0;
  }

  @Override
  public short aShort() {
    return (short) 0;
  }

  @Override
  public int anInt() {
    return 0;
  }

  @Override
  public long aLong() {
    return 0L;
  }

  @Override
  public float aFloat() {
    return 0.0F;
  }

  @Override
  public double aDouble() {
    return 0.0D;
  }

  @Override
  public char aChar() {
    return '';
  }

  @Override
  public boolean aBoolean() {
    return false;
  }

  @Override
  public String aString() {
    return null;
  }

  @Override
  public void aVoid() {
  }
  
  public static TestInterface noOpTestInterface() {
      return INSTANCE;
    }
}
```

The implemented methods return the default data type value as per [Java Data Types](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html):

| Data type               | Default value |
|:------------------------|:--------------|
| byte                    | 0             |
| short                   | 0             |
| int                     | 0             |
| long                    | 0L            |
| float                   | 0.0F          |
| double                  | 0.0D          |
| char                    | '\u0000'      |
| String (or any object)  | null          |
| boolean                 | false         |

* It also supports Generics (bounded/unbounded) and multiple inheritance of interfaces:

```java
@NoOp
public interface TestInterface<A, B, C extends Throwable> extends TestInterface2, TestInterface3 {

    A genericA();
    B genericB();
    C genericC();
}

interface TestInterface2 {
    void fromTestInterface2();
}
    
interface TestInterface3 {
    void fromTestInterface3();
}
```

* Generated no-op implementation:

```java
public class NoOpTestInterface<A, B, C extends Throwable> implements TestInterface<A, B, C> {

  @Override
  public A genericA() {
    return null;
  }

  @Override
  public B genericB() {
    return null;
  }

  @Override
  public C genericC() {
    return null;
  }

  @Override
  public void fromTestInterface2() {
  }

  @Override
  public void fromTestInterface3() {
  }
}
```

Download
--------

Gradle (using [apt](https://bitbucket.org/hvisser/android-apt)):

```groovy
def noOpVersion = '1.2.0'
compile "com.jenzz.noop:annotation:$noOpVersion"
apt "com.jenzz.noop:processor:$noOpVersion"
```

Maven (using [maven-compiler-plugin](http://maven.apache.org/plugins/maven-compiler-plugin)):

```xml
<dependency>
    <groupId>com.jenzz.noop</groupId>
    <artifactId>annotation</artifactId>
    <version>1.2.0</version>
</dependency>

<build>
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>2.5.1</version>
                <dependencies>
                    <dependency>
                        <groupId>com.jenzz.noop</groupId>
                        <artifactId>processor</artifactId>
                        <version>1.2.0</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </pluginManagement>
</build>
```

Snapshot versions are available in [Sonatype's SNAPSHOTS repository](https://oss.sonatype.org/content/repositories/snapshots).

License
-------
This project is licensed under the [MIT License](https://raw.githubusercontent.com/jenzz/Java-NoOp/master/LICENSE).
