package com.harium.pak;

public class PakFileEntry {

  String name;
  int offset;
  int size;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
