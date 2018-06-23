package ar.edu.itba.crypto.engine;

import ar.edu.itba.crypto.utils.Configuration;

import java.nio.ByteBuffer;

public interface Processor {

    public ByteBuffer process(Configuration conf, ByteBuffer bytes);
}
