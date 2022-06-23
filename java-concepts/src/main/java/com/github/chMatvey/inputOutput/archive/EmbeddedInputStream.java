package com.github.chMatvey.inputOutput.archive;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class EmbeddedInputStream extends FilterInputStream {
    private static final int EOF = -1;
    private static final int BUFF_SIZE = EmbeddedOutputStream.BUFF_SIZE;

    private final byte[] buffer = new byte[BUFF_SIZE];
    private int bufferPosition = 0;
    private int bufferLimit = 0;
    private boolean streamDataFinished = false;

    public EmbeddedInputStream(InputStream in) {
        super(Objects.requireNonNull(in, "in is null"));
    }

    @Override
    public int read() throws IOException {
        byte[] bytes = new byte[1];
        int bytesRead = read(bytes);
        return bytesRead == 1 ? bytes[0] : EOF;
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return read(bytes, 0, bytes.length);
    }

    @Override
    public int read(byte[] bytes, int off, int len) throws IOException {
        int bytesRead = 0;
        while (0 < len) {
            if (isBufferEmpty() && readBufferFromUnderlyingStream() <= 0) {
                break;
            }
            int byteGotFromBuffer = getBytesFromBuffer(bytes, off, len);
            bytesRead += byteGotFromBuffer;
            off += byteGotFromBuffer;
            len -= byteGotFromBuffer;
        }
        return bytesRead;
    }

    @Override
    public long skip(long bytesToSkip) throws IOException {
        for (long i = 0; i < bytesToSkip; i++) {
            if (read() == EOF) {
                return i;
            }
        }
        return bytesToSkip;
    }

    @Override
    public void close() throws IOException {
        if (streamDataFinished) {
            return;
        }
        in.close();
        this.streamDataFinished = true;
    }

    private int getBytesFromBuffer(byte[] bytes, int off, int len) {
        int remainingBytesInBuffer = buffer.length - bufferPosition;
        int bytesToRead = Math.min(len, remainingBytesInBuffer);
        System.arraycopy(buffer, bufferPosition, bytes, off, bytesToRead);
        bufferPosition += bytesToRead;

        return bytesToRead;
    }

    private boolean isBufferEmpty() {
        return bufferPosition == 0;
    }

    private int readBufferFromUnderlyingStream() throws IOException {
        bufferPosition = (in.read() << 8) & 0xFF + in.read() & 0xFF;
        int readBytes = in.read(buffer, 0, bufferPosition);
        bufferPosition = 0;
        return readBytes;
    }
}
