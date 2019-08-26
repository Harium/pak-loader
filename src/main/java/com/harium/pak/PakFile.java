package com.harium.pak;

import java.util.HashMap;
import java.util.Map;

public class PakFile {

  int size;
  int offset;

  Map<String, PakFileEntry> files = new HashMap<>();

  public int getSize() {
    return size;
  }

  void addFile(PakFileEntry entry) {
    files.put(entry.name, entry);
  }

  public PakFileEntry getFile(String name) {
    return files.get(name);
  }

  public Map<String, PakFileEntry> getFiles() {
    return files;
  }
}
