package com.github.chMatvey.inputOutput;

import java.io.*;
import java.util.Objects;

import static java.io.StreamTokenizer.TT_EOF;

public class IO {
    public static void main(String[] args) throws IOException {
        try(InputStream inputStream = Objects.requireNonNull(
                IO.class.getClassLoader().getResourceAsStream("file.txt"),
                "File not found in resource directory"
        )) {
            StreamTokenizer streamTokenizer = new StreamTokenizer(new InputStreamReader(inputStream));
            copyByToken(streamTokenizer, System.out);
        }
    }

    public static void copyStreamByByteBuffer(InputStream inputStream, OutputStream outputStream) throws IOException {
        int BUF_SIZE = 1024;
        byte[] buf = new byte[BUF_SIZE];
        int bytesRead;
        while ((bytesRead = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, bytesRead);
        }
    }

    public static void copyStreamByCharBuffer(Reader reader, Writer writer) throws IOException {
        int BUF_SIZE = 512;
        char[] buf = new char[BUF_SIZE];
        int bytesRead;
        while ((bytesRead = reader.read(buf)) > 0) {
            writer.write(buf, 0, bytesRead);
        }
    }

    public static void copyStreamByLine(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line = reader.readLine();
        if (line != null) {
            writer.write(line);
        }
        while ((line = reader.readLine()) != null) {
            writer.newLine();
            writer.write(line);
        }
    }

    public static void copyByToken(StreamTokenizer streamTokenizer, PrintStream printStream) throws IOException {
        while (streamTokenizer.nextToken() != TT_EOF) {
            printStream.print(streamTokenizer);
        }
    }
}
