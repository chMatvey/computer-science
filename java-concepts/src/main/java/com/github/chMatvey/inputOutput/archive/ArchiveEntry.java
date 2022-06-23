package com.github.chMatvey.inputOutput.archive;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
public class ArchiveEntry {
    private String name;
    private Long creationTime;
    private Long lastModifiedTime;
    private InputStream inputStream;
}
