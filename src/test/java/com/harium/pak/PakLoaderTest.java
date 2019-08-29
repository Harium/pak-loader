package com.harium.pak;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.harium.pak.PakFile;
import com.harium.pak.PakFileEntry;
import com.harium.pak.PakLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Before;
import org.junit.Test;

public class PakLoaderTest {

  String path;
  PakLoader loader;

  @Before
  public void setUp() {
    loader = new PakLoader();

    path = System.getProperty("user.dir");
    path += "/src/test/resources/";
  }

  @Test
  public void testLoad() throws IOException {
    PakFile file = loader.load(path + "housp-pak0.pak");
    assertEquals(256, file.getSize());
    assertEquals(4, file.getFileEntries().size());
  }

  @Test
  public void testLoadBytes() throws IOException {
    PakFile file = loader.load(path + "housp-pak0.pak");
    InputStream stream = file.getFile("progs/spider.mdl");

    byte[] header = new byte[4];
    stream.read(header);

    assertArrayEquals(new byte[]{(byte) 'I', (byte) 'D', (byte) 'P', (byte) 'O'}, header);
  }

}
