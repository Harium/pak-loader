package com.harium.pak;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class PakWriterTest {

  PakWriter writer;

  @Before
  public void setUp() {
    writer = new PakWriter();
  }

  @Test
  public void testCharName() {
    byte[] b = new byte[56];
    b[0] = (byte) 'a';
    assertArrayEquals(b, writer.nameArray("a"));
  }

  @Test
  public void testCharNameWithFolder() {
    byte[] b = new byte[56];
    b[0] = (byte) 'a';
    b[1] = (byte) '/';
    b[2] = (byte) 'b';
    b[3] = (byte) '.';
    b[4] = (byte) 'e';
    b[5] = (byte) 'x';
    b[6] = (byte) 't';

    assertArrayEquals(b, writer.nameArray("a/b.ext"));
  }

}
