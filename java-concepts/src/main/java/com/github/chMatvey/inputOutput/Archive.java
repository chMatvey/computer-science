package com.github.chMatvey.inputOutput;

import com.github.chMatvey.inputOutput.archive.ArchiveReader;
import com.github.chMatvey.inputOutput.archive.ArchiveWriter;

import java.io.IOException;
import java.nio.file.Path;

public class Archive {
    public static void main(String[] args) throws IOException {
        Path sourceDirectory = Path.of("test-source-directory");
        Path archive = Path.of("archive-result");
        try (ArchiveWriter archiveWriter = new ArchiveWriter(archive)) {
            archiveWriter.addDirectory(sourceDirectory);
        }

        Path targetDirectory = Path.of("test-target-directory");
        try (ArchiveReader archiveReader = new ArchiveReader(archive)) {
            archiveReader.extractToDirectory(targetDirectory);
        }
    }
}
