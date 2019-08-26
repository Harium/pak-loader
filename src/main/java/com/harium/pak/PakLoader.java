package com.harium.pak;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Reference: https://quakewiki.org/wiki/.pak#Format_specification
 */
public class PakLoader {

  private static final int HEADER_SIZE = 12;
  private static final int HEADER_ENTRY = 56 + 8;
  private static final byte[] HEADER_PACK = {80, 65, 67, 75};//'PACK'

  public PakFile load(String path) throws IOException {

    PakFile pakFile = new PakFile();

    RandomAccessFile raf = new RandomAccessFile(path, "r");

    readHeader(pakFile, raf);
    readEntries(pakFile, raf);

    raf.close();

    return pakFile;
  }

  private void readEntries(PakFile pakFile, RandomAccessFile raf) throws IOException {
    int offset = pakFile.offset;
    int fileCount = pakFile.size / 64;

    for (int i = 0; i < fileCount; i++) {
      PakFileEntry entry = readEntry(raf, offset + HEADER_ENTRY * i);
      pakFile.addFile(entry);
    }
  }

  private PakFileEntry readEntry(RandomAccessFile raf, int offset) throws IOException {
    PakFileEntry entry = new PakFileEntry();
    byte[] headerEntry = new byte[HEADER_ENTRY];
    raf.seek(offset);
    raf.read(headerEntry);

    parseEntryHeader(entry, headerEntry);
    System.out.println(entry.name);
    System.out.println(entry.size);
    System.out.println(entry.offset);
    return entry;
  }

  private void parseEntryHeader(PakFileEntry entry, byte[] headerEntry) {
    StringBuilder name = new StringBuilder();
    for (int i = 0; i < 56; i++) {
      if(headerEntry[i]==0) {
        break;
      }
      char ch = (char) headerEntry[i];
      name.append(ch);
    }
    entry.name = name.toString();
    entry.offset = readInt(headerEntry, headerEntry.length - 8);
    entry.size = readInt(headerEntry, headerEntry.length - 4);
  }

  private void readHeader(PakFile pakFile, RandomAccessFile fis) throws IOException {
    byte[] header = new byte[HEADER_SIZE];
    fis.read(header);

    if (!parseHeader(pakFile, header)) {
      throw new RuntimeException("Invalid .pak file.");
    }
  }

  private boolean parseHeader(PakFile pakFile, byte[] header) {
    if (!check(HEADER_PACK, header)) {
      return false;
    }
    pakFile.offset = readInt(header, 4);
    pakFile.size = readInt(header, 8);
    return true;
  }

  private boolean check(byte[] bytes, byte[] header) {
    for (int i = 0; i < bytes.length; i++) {
      if (bytes[i] == header[i]) {
        continue;
      }
      return false;
    }
    return true;
  }

  private int readInt(byte[] array, int offset) {
    return array[3 + offset] << 24
        | (array[2 + offset] & 0xff) << 16
        | (array[1 + offset] & 0xff) << 8
        | (array[0 + offset] & 0xff);
  }

}
