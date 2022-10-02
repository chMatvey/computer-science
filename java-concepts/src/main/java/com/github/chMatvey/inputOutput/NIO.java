package com.github.chMatvey.inputOutput;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class NIO {
    public static void main(String[] args) throws IOException {
        // channelExample();
        // transferDemo();
        fileExample();
    }

    public static final String SOURCE_FILE = "sample-mp4-file.mp4";
    public static final String TARGET_FILE = "result.mp4";

    public static void channelExample() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(SOURCE_FILE);
             FileOutputStream fileOutputStream = new FileOutputStream(TARGET_FILE)) {
            FileChannel inChannel = fileInputStream.getChannel();
            FileChannel outChannel = fileOutputStream.getChannel();
            int BUFFER_SIZE = 4096;
            ByteBuffer inBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            ByteBuffer outBuffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (inChannel.read(inBuffer) != -1) {
                inBuffer.flip();
                while (inBuffer.hasRemaining()) {
                    byte byteRead = inBuffer.get();
                    outBuffer.put(byteRead);
                }
                outBuffer.flip();
                outChannel.write(outBuffer);
                inBuffer.clear();
                outBuffer.clear();
            }
        }
    }

    public static void transferDemo() throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(SOURCE_FILE);
            FileOutputStream fileOutputStream = new FileOutputStream(TARGET_FILE)) {
            FileChannel inChannel = fileInputStream.getChannel();
            FileChannel outChannel = fileOutputStream.getChannel();

            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
    }

    public static void fileExample() throws IOException {
        Path path = Paths.get(".", "file.txt");
        try(Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.forEach(System.out::println);
        }
    }
}
