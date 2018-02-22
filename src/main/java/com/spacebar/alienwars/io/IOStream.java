package com.spacebar.alienwars.io;

public interface IOStream<T> {

    void write(T t);

    void writeLine(T t);

    void writeInt(int value);

    T read();

    T readLine();

    int readInt();

    void clear();
}
