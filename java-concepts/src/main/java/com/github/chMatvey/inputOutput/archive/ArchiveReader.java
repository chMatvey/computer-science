package com.github.chMatvey.inputOutput.archive;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;

public class ArchiveReader implements AutoCloseable {
    private final DataInputStream inputStream;
    private ArchiveEntry currentEntry = null;

    public ArchiveReader(Path file) throws IOException {
        inputStream = new DataInputStream(Files.newInputStream(file));
    }

    public void extractToDirectory(Path directory) throws IOException {
        for (ArchiveEntry entry = nextEntry(); entry != null; entry = nextEntry()) {
            Path targetFile = directory.resolve(entry.getName());
            extractFile(targetFile, entry);
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    private void extractFile(Path targetFile, ArchiveEntry entry) throws IOException {
        ensureParentDirectoryExists(targetFile);
        Files.copy(entry.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);
        BasicFileAttributeView fileAttribute = Files.getFileAttributeView(targetFile, BasicFileAttributeView.class);
        fileAttribute.setTimes(
                FileTime.fromMillis(entry.getLastModifiedTime()),
                null,
                FileTime.fromMillis(entry.getCreationTime())
        );
    }

    private void ensureParentDirectoryExists(Path targetFile) throws IOException {
        Path parentDirectory = targetFile.getParent();
        Files.createDirectories(parentDirectory);
    }

    private ArchiveEntry nextEntry() throws IOException {
        ArchiveEntry nextEntry = readEntry();
        if (nextEntry == null) {
            return null;
        }
        if (currentEntry != null) {
            currentEntry.getInputStream().close();
        }
        currentEntry = nextEntry;
        return currentEntry;
    }

    private ArchiveEntry readEntry() {
        ArchiveEntry entry = new ArchiveEntry();
        try {
            entry.setName(inputStream.readUTF());
            entry.setCreationTime(inputStream.readLong());
            entry.setLastModifiedTime(inputStream.readLong());
            entry.setInputStream(new EmbeddedInputStream(inputStream));
        } catch (IOException e) {
            return null;
        }
        return entry;
    }
}
