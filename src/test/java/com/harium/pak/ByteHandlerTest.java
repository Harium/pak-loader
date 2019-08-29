package com.harium.pak;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.harium.pak.ByteHandler;
import org.junit.Test;

public class ByteHandlerTest {

  @Test
  public void testIntToByteBigEndian() {
    assertArrayEquals(new byte[]{108, 108, 6, 0}, ByteHandler.intToByteBigEndian(420972));
    assertArrayEquals(new byte[]{-1, -1, -1, 127},
        ByteHandler.intToByteBigEndian(Integer.MAX_VALUE));
    assertArrayEquals(new byte[]{0, 0, 0, -128}, ByteHandler.intToByteBigEndian(Integer.MIN_VALUE));
  }

  @Test
  public void readIntBigEndian() {
    assertEquals(ByteHandler.readIntBigEndian(new byte[]{-1, -1, -1, 127}), Integer.MAX_VALUE);
    assertEquals(ByteHandler.readIntBigEndian(new byte[]{0, 0, 0, -128}), Integer.MIN_VALUE);
  }

}
