package com.harium.pak;

import static com.harium.loader.ByteUtils.compare;
import static com.harium.loader.ByteUtils.readIntLittleEndian;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Reference: https://quakewiki.org/wiki/.pak#Format_specification
 */
public class PakLoader {

  static final int HEADER_SIZE = 12;
  static final int HEADER_ENTRY = 56 + 8;
  //'PACK'
  static final byte[] HEADER_PACK = {80, 65, 67, 75};
  static final int NAME_SIZE = 56;

  public PakFile load(String path) throws IOException {

    PakFile pakFile = new PakFile(path);

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
    return entry;
  }

  private void parseEntryHeader(PakFileEntry entry, byte[] headerEntry) {
    StringBuilder name = new StringBuilder();
    for (int i = 0; i < NAME_SIZE; i++) {
      if (headerEntry[i] == 0) {
        break;
      }
      char ch = (char) headerEntry[i];
      name.append(ch);
    }
    entry.name = name.toString();
    entry.offset = readIntLittleEndian(headerEntry, headerEntry.length - 8);
    entry.size = readIntLittleEndian(headerEntry, headerEntry.length - 4);
  }

  private void readHeader(PakFile pakFile, RandomAccessFile fis) throws IOException {
    byte[] header = new byte[HEADER_SIZE];
    fis.read(header);

    if (!parseHeader(pakFile, header)) {
      throw new RuntimeException("Invalid .pak file.");
    }
  }

  private boolean parseHeader(PakFile pakFile, byte[] header) {
    if (!compare(HEADER_PACK, header)) {
      return false;
    }
    pakFile.offset = readIntLittleEndian(header, 4);
    pakFile.size = readIntLittleEndian(header, 8);
    return true;
  }

}
