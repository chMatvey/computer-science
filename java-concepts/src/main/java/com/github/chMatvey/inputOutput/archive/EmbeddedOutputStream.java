package com.github.chMatvey.inputOutput.archive;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class EmbeddedOutputStream extends FilterOutputStream {
    static final int BUFF_SIZE = 65535;

    private final byte[] buffer = new byte[BUFF_SIZE];
    private int bufferPosition = 0;
    private boolean closed = false;

    public EmbeddedOutputStream(OutputStream out) {
        super(Objects.requireNonNull(out, "out is null"));
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[(byte) b]);
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        write(bytes, 0, bytes.length);
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        while (0 < len) {
            int bytesAddedToBuffer = addBytesToBuffer(bytes, off, len);
            if (isBufferFull()) {
                writeBufferToUnderlyingStream();
            }
            off += bytesAddedToBuffer;
            len -= bytesAddedToBuffer;
        }
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            return;
        }
        if (!isBufferEmpty()) {
            writeBufferToUnderlyingStream();
        }
        writeEndOfStreamMarker();
        closed = true;
    }

    private int addBytesToBuffer(byte[] bytes, int off, int len) {
        int remainingBytesInBuffer = buffer.length - bufferPosition;
        int bytesToWrite = Math.min(len, remainingBytesInBuffer);
        System.arraycopy(bytes, off, buffer, bufferPosition, bytesToWrite);
        bufferPosition += bytesToWrite;

        return bytesToWrite;
    }

    private boolean isBufferFull() {
        return bufferPosition == buffer.length;
    }

    private boolean isBufferEmpty() {
        return bufferPosition == 0;
    }

    private void writeBufferToUnderlyingStream() throws IOException {
        out.write((bufferPosition >> 8) & 0xFF);
        out.write(bufferPosition & 0xFF);
        out.write(buffer, 0, bufferPosition);
        bufferPosition = 0;
    }

    private void writeEndOfStreamMarker() throws IOException {
        out.write(0);
        out.write(0);
    }
}
