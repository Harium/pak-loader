package com.harium.pak;

import static com.harium.loader.ByteUtils.intToByteBigEndian;
import static com.harium.loader.ByteUtils.intToByteLittleEndian;
import static com.harium.pak.PakLoader.HEADER_PACK;
import static com.harium.pak.PakLoader.HEADER_SIZE;
import static com.harium.pak.PakLoader.NAME_SIZE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reference: https://quakewiki.org/wiki/.pak#Format_specification
 */
public class PakWriter {

  public void write(String path, String output) throws IOException {
    File folder = new File(path);
    if (folder.isDirectory() && folder.exists()) {
      FileOutputStream fos = new FileOutputStream(output);

      List<File> files = listFile(folder);

      writeHeader(fos, files);
      int offset = HEADER_SIZE;

      for (File file : files) {
        writeFileContent(fos, file);
      }

      for (File file : files) {
        offset = writeFileEntryHeader(fos, offset, folder, file);
      }

      fos.close();
    }
  }

  private void writeFileContent(FileOutputStream fos, File file) throws IOException {
    byte[] content = new byte[(int) file.length()];
    FileInputStream fis = new FileInputStream(file);
    fis.read(content);
    fis.close();
    fos.write(content);
  }

  public List<File> listFile(File root) {
    List<File> files = new ArrayList<>();
    for (File child : root.listFiles()) {
      listFile(child, files);
    }

    Collections.sort(files);

    return files;
  }

  private void listFile(File root, List<File> files) {
    if (!root.isDirectory()) {
      files.add(root);
    } else {
      for (File child : root.listFiles()) {
        listFile(child, files);
      }
    }
  }

  private void writeHeader(FileOutputStream fos, List<File> files) throws IOException {
    fos.write(HEADER_PACK);

    int total = 12;

    for (File file : files) {
      total += file.length();
    }

    fos.write(intToByteLittleEndian(total));

    int size = files.size() * 64;
    fos.write(intToByteLittleEndian(size));
  }

  private int writeFileEntryHeader(FileOutputStream fos, int offset, File folder, File file)
      throws IOException {
    String fileName = file.getAbsolutePath().substring(folder.getAbsolutePath().length() + 1);
    byte[] nameArray = nameArray(fileName);

    int size = (int) file.length();
    fos.write(nameArray);
    fos.write(intToByteBigEndian(offset));
    fos.write(intToByteBigEndian(size));

    return offset + size;
  }

  byte[] nameArray(String fileName) {
    byte[] nameArray = new byte[NAME_SIZE];

    byte[] source = fileName.getBytes();
    for (int i = 0; i < source.length; i++) {
      nameArray[i] = source[i];
    }

    return nameArray;
  }


}
