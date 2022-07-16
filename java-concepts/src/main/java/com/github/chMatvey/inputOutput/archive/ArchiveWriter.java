package com.github.chMatvey.inputOutput.archive;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ArchiveWriter implements AutoCloseable {
    private final DataOutputStream outputStream;

    public ArchiveWriter(Path outputFile) throws IOException {
        outputStream = new DataOutputStream(Files.newOutputStream(
                outputFile,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        ));
    }

    public void addDirectory(Path directory) throws IOException {
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Path does not point to a directory");
        }
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                addFile(file, directory, attrs);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }

    private void addFile(Path file, Path baseDirectory, BasicFileAttributes fileAttributes) throws IOException {
        outputStream.writeUTF(baseDirectory.relativize(file).toString());
        outputStream.writeLong(fileAttributes.creationTime().toMillis());
        outputStream.writeLong(fileAttributes.lastModifiedTime().toMillis());
        try (OutputStream fileContentStream = new EmbeddedOutputStream(outputStream)) {
            Files.copy(file, fileContentStream);
        }
    }
}
