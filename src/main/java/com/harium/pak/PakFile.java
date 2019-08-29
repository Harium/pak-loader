package com.harium.pak;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class PakFile {

  int size;
  int offset;
  String filepath;

  Map<String, PakFileEntry> fileEntries = new HashMap<>();

  public PakFile() {
    super();
  }

  public PakFile(String filepath) {
    this.filepath = filepath;
  }

  public String getFilepath() {
    return filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public int getSize() {
    return size;
  }

  public int getOffset() {
    return offset;
  }

  void addFile(PakFileEntry entry) {
    fileEntries.put(entry.name, entry);
  }

  public PakFileEntry getFileEntry(String name) {
    return fileEntries.get(name);
  }

  public Map<String, PakFileEntry> getFileEntries() {
    return fileEntries;
  }

  public InputStream getFile(String entry) throws IOException {
    return getFile(fileEntries.get(entry));
  }

  public InputStream getFile(PakFileEntry entry) throws IOException {
    byte[] buffer = new byte[entry.size];

    RandomAccessFile randomAccessFile = new RandomAccessFile(new File(filepath), "r");
    randomAccessFile.seek(entry.offset);
    randomAccessFile.read(buffer);
    return new ByteArrayInputStream(buffer);
  }
}
