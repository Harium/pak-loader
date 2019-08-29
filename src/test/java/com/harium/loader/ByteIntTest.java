package com.harium.loader;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteIntTest {

  @Test
  public void testIntToByteBigEndian() {
    assertArrayEquals(new byte[]{108, 108, 6, 0}, ByteInt.intToByteBigEndian(420972));
    assertArrayEquals(new byte[]{-1, -1, -1, 127},
        ByteInt.intToByteBigEndian(Integer.MAX_VALUE));
    assertArrayEquals(new byte[]{0, 0, 0, -128}, ByteInt.intToByteBigEndian(Integer.MIN_VALUE));
  }

  @Test
  public void readIntBigEndian() {
    assertEquals(ByteInt.readIntBigEndian(new byte[]{-1, -1, -1, 127}), Integer.MAX_VALUE);
    assertEquals(ByteInt.readIntBigEndian(new byte[]{0, 0, 0, -128}), Integer.MIN_VALUE);
  }

}
