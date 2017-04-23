package org.pawkrol.arduino;

/**
 * Created by pawkrol on 2017-04-23.
 */
@FunctionalInterface
public interface SerialReader {
    void read(String val);
}
