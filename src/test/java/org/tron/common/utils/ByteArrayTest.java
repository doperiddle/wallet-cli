package org.tron.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class ByteArrayTest {

  @Test
  public void toHexStringNull() {
    Assert.assertEquals("", ByteArray.toHexString(null));
  }

  @Test
  public void toHexStringEmpty() {
    Assert.assertEquals("", ByteArray.toHexString(new byte[0]));
  }

  @Test
  public void toHexStringBytes() {
    byte[] input = new byte[]{0x0a, 0x1b, (byte) 0xff};
    Assert.assertEquals("0a1bff", ByteArray.toHexString(input));
  }

  @Test
  public void fromHexStringNull() {
    byte[] result = ByteArray.fromHexString(null);
    Assert.assertArrayEquals(ByteArray.EMPTY_BYTE_ARRAY, result);
  }

  @Test
  public void fromHexStringWithPrefix() {
    byte[] result = ByteArray.fromHexString("0x0a1bff");
    Assert.assertArrayEquals(new byte[]{0x0a, 0x1b, (byte) 0xff}, result);
  }

  @Test
  public void fromHexStringWithoutPrefix() {
    byte[] result = ByteArray.fromHexString("0a1bff");
    Assert.assertArrayEquals(new byte[]{0x0a, 0x1b, (byte) 0xff}, result);
  }

  @Test
  public void fromHexStringOddLength() {
    // odd-length input should be left-padded with '0'
    byte[] result = ByteArray.fromHexString("abc");
    Assert.assertArrayEquals(new byte[]{0x0a, (byte) 0xbc}, result);
  }

  @Test
  public void toHexStringRoundTrip() {
    byte[] original = new byte[]{(byte) 0xde, (byte) 0xad, (byte) 0xbe, (byte) 0xef};
    String hex = ByteArray.toHexString(original);
    byte[] decoded = ByteArray.fromHexString(hex);
    Assert.assertArrayEquals(original, decoded);
  }

  @Test
  public void toLongNull() {
    Assert.assertEquals(0L, ByteArray.toLong(null));
  }

  @Test
  public void toLongEmpty() {
    Assert.assertEquals(0L, ByteArray.toLong(new byte[0]));
  }

  @Test
  public void toLongValue() {
    byte[] bytes = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01};
    Assert.assertEquals(1L, ByteArray.toLong(bytes));
  }

  @Test
  public void toLongLargeValue() {
    byte[] bytes = ByteArray.fromLong(Long.MAX_VALUE);
    Assert.assertEquals(Long.MAX_VALUE, ByteArray.toLong(bytes));
  }

  @Test
  public void fromStringNull() {
    Assert.assertNull(ByteArray.fromString(null));
  }

  @Test
  public void fromStringValue() {
    byte[] result = ByteArray.fromString("hello");
    Assert.assertArrayEquals("hello".getBytes(), result);
  }

  @Test
  public void toStrNull() {
    Assert.assertNull(ByteArray.toStr(null));
  }

  @Test
  public void toStrValue() {
    byte[] bytes = "hello".getBytes();
    Assert.assertEquals("hello", ByteArray.toStr(bytes));
  }

  @Test
  public void fromLongZero() {
    byte[] result = ByteArray.fromLong(0L);
    Assert.assertEquals(8, result.length);
    Assert.assertEquals(0L, ByteArray.toLong(result));
  }

  @Test
  public void fromLongRoundTrip() {
    long val = 123456789L;
    byte[] bytes = ByteArray.fromLong(val);
    Assert.assertEquals(8, bytes.length);
    Assert.assertEquals(val, ByteArray.toLong(bytes));
  }

  @Test
  public void subArrayFull() {
    byte[] input = new byte[]{1, 2, 3, 4, 5};
    byte[] result = ByteArray.subArray(input, 0, 5);
    Assert.assertArrayEquals(input, result);
  }

  @Test
  public void subArrayPartial() {
    byte[] input = new byte[]{1, 2, 3, 4, 5};
    byte[] result = ByteArray.subArray(input, 1, 4);
    Assert.assertArrayEquals(new byte[]{2, 3, 4}, result);
  }

  @Test
  public void subArraySingle() {
    byte[] input = new byte[]{10, 20, 30};
    byte[] result = ByteArray.subArray(input, 2, 3);
    Assert.assertArrayEquals(new byte[]{30}, result);
  }
}
