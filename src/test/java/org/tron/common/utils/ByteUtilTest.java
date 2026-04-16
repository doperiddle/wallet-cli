package org.tron.common.utils;

import java.math.BigInteger;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ByteUtilTest {

  // --- cloneBytes ---

  @Test
  public void cloneBytesNull() {
    Assert.assertNull(ByteUtil.cloneBytes(null));
  }

  @Test
  public void cloneBytesEmpty() {
    byte[] result = ByteUtil.cloneBytes(new byte[0]);
    Assert.assertNotNull(result);
    Assert.assertEquals(0, result.length);
  }

  @Test
  public void cloneBytesValue() {
    byte[] original = {1, 2, 3};
    byte[] clone = ByteUtil.cloneBytes(original);
    Assert.assertArrayEquals(original, clone);
    // Mutating original should not affect clone
    original[0] = 99;
    Assert.assertEquals(1, clone[0]);
  }

  // --- bigIntegerToBytes(BigInteger, int) ---

  @Test
  public void bigIntegerToBytesNullWithSize() {
    Assert.assertNull(ByteUtil.bigIntegerToBytes(null, 4));
  }

  @Test
  public void bigIntegerToBytesZeroWithSize() {
    byte[] result = ByteUtil.bigIntegerToBytes(BigInteger.ZERO, 4);
    Assert.assertArrayEquals(new byte[]{0, 0, 0, 0}, result);
  }

  @Test
  public void bigIntegerToBytesWithSize() {
    byte[] result = ByteUtil.bigIntegerToBytes(BigInteger.valueOf(256), 2);
    Assert.assertArrayEquals(new byte[]{1, 0}, result);
  }

  // --- bigIntegerToBytes(BigInteger) ---

  @Test
  public void bigIntegerToBytesNull() {
    Assert.assertNull(ByteUtil.bigIntegerToBytes((BigInteger) null));
  }

  @Test
  public void bigIntegerToBytesZero() {
    byte[] result = ByteUtil.bigIntegerToBytes(BigInteger.ZERO);
    Assert.assertArrayEquals(new byte[]{0}, result);
  }

  @Test
  public void bigIntegerToBytesPositive() {
    byte[] result = ByteUtil.bigIntegerToBytes(BigInteger.valueOf(255));
    Assert.assertArrayEquals(new byte[]{(byte) 0xff}, result);
  }

  @Test
  public void bigIntegerToBytesLarge() {
    // 256 = 0x100 → should be [1, 0] with no leading sign byte
    byte[] result = ByteUtil.bigIntegerToBytes(BigInteger.valueOf(256));
    Assert.assertArrayEquals(new byte[]{1, 0}, result);
  }

  // --- merge ---

  @Test
  public void mergeArrays() {
    byte[] a = {1, 2};
    byte[] b = {3, 4};
    byte[] result = ByteUtil.merge(a, b);
    Assert.assertArrayEquals(new byte[]{1, 2, 3, 4}, result);
  }

  @Test
  public void mergeSingleArray() {
    byte[] a = {5, 6, 7};
    byte[] result = ByteUtil.merge(a);
    Assert.assertArrayEquals(a, result);
  }

  @Test
  public void mergeEmptyArrays() {
    byte[] result = ByteUtil.merge(new byte[0], new byte[0]);
    Assert.assertEquals(0, result.length);
  }

  // --- appendByte ---

  @Test
  public void appendByte() {
    byte[] bytes = {1, 2, 3};
    byte[] result = ByteUtil.appendByte(bytes, (byte) 4);
    Assert.assertArrayEquals(new byte[]{1, 2, 3, 4}, result);
  }

  // --- nibblesToPrettyString ---

  @Test
  public void nibblesToPrettyString() {
    byte[] nibbles = {0x0a, 0x1b};
    String result = ByteUtil.nibblesToPrettyString(nibbles);
    Assert.assertEquals("\\x0a\\x1b", result);
  }

  // --- oneByteToHexString ---

  @Test
  public void oneByteToHexStringSingleDigit() {
    Assert.assertEquals("0f", ByteUtil.oneByteToHexString((byte) 0x0f));
  }

  @Test
  public void oneByteToHexStringDoubleDigit() {
    Assert.assertEquals("ff", ByteUtil.oneByteToHexString((byte) 0xff));
  }

  // --- toHexString ---

  @Test
  public void toHexStringNull() {
    Assert.assertEquals("", ByteUtil.toHexString(null));
  }

  @Test
  public void toHexStringBytes() {
    Assert.assertEquals("0102ff", ByteUtil.toHexString(new byte[]{1, 2, (byte) 0xff}));
  }

  // --- byteArrayToInt ---

  @Test
  public void byteArrayToIntNull() {
    Assert.assertEquals(0, ByteUtil.byteArrayToInt(null));
  }

  @Test
  public void byteArrayToIntEmpty() {
    Assert.assertEquals(0, ByteUtil.byteArrayToInt(new byte[0]));
  }

  @Test
  public void byteArrayToIntValue() {
    Assert.assertEquals(256, ByteUtil.byteArrayToInt(new byte[]{1, 0}));
  }

  // --- isSingleZero ---

  @Test
  public void isSingleZeroTrue() {
    Assert.assertTrue(ByteUtil.isSingleZero(new byte[]{0}));
  }

  @Test
  public void isSingleZeroFalseMultipleBytes() {
    Assert.assertFalse(ByteUtil.isSingleZero(new byte[]{0, 0}));
  }

  @Test
  public void isSingleZeroFalseNonZero() {
    Assert.assertFalse(ByteUtil.isSingleZero(new byte[]{1}));
  }

  // --- intToBytesNoLeadZeroes ---

  @Test
  public void intToBytesNoLeadZeroesZero() {
    byte[] result = ByteUtil.intToBytesNoLeadZeroes(0);
    Assert.assertArrayEquals(ByteUtil.EMPTY_BYTE_ARRAY, result);
  }

  @Test
  public void intToBytesNoLeadZeroesSmall() {
    byte[] result = ByteUtil.intToBytesNoLeadZeroes(1);
    Assert.assertArrayEquals(new byte[]{1}, result);
  }

  @Test
  public void intToBytesNoLeadZeroesLarger() {
    byte[] result = ByteUtil.intToBytesNoLeadZeroes(256);
    Assert.assertArrayEquals(new byte[]{1, 0}, result);
  }

  // --- intToBytes ---

  @Test
  public void intToBytes() {
    byte[] result = ByteUtil.intToBytes(1);
    Assert.assertArrayEquals(new byte[]{0, 0, 0, 1}, result);
  }

  @Test
  public void intToBytesZero() {
    byte[] result = ByteUtil.intToBytes(0);
    Assert.assertArrayEquals(new byte[]{0, 0, 0, 0}, result);
  }

  // --- bytesToBigInteger ---

  @Test
  public void bytesToBigIntegerNull() {
    Assert.assertEquals(BigInteger.ZERO, ByteUtil.bytesToBigInteger(null));
  }

  @Test
  public void bytesToBigIntegerEmpty() {
    Assert.assertEquals(BigInteger.ZERO, ByteUtil.bytesToBigInteger(new byte[0]));
  }

  @Test
  public void bytesToBigIntegerValue() {
    Assert.assertEquals(BigInteger.valueOf(255),
        ByteUtil.bytesToBigInteger(new byte[]{(byte) 0xff}));
  }

  // --- byteArrayToLong ---

  @Test
  public void byteArrayToLongNull() {
    Assert.assertEquals(0L, ByteUtil.byteArrayToLong(null));
  }

  @Test
  public void byteArrayToLongEmpty() {
    Assert.assertEquals(0L, ByteUtil.byteArrayToLong(new byte[0]));
  }

  @Test
  public void byteArrayToLongValue() {
    Assert.assertEquals(1L, ByteUtil.byteArrayToLong(new byte[]{0, 0, 0, 0, 0, 0, 0, 1}));
  }

  // --- firstNonZeroByte ---

  @Test
  public void firstNonZeroByteAllZeros() {
    Assert.assertEquals(-1, ByteUtil.firstNonZeroByte(new byte[]{0, 0, 0}));
  }

  @Test
  public void firstNonZeroByteFirstPos() {
    Assert.assertEquals(0, ByteUtil.firstNonZeroByte(new byte[]{1, 0, 0}));
  }

  @Test
  public void firstNonZeroByteMiddlePos() {
    Assert.assertEquals(2, ByteUtil.firstNonZeroByte(new byte[]{0, 0, 5}));
  }

  // --- stripLeadingZeroes ---

  @Test
  public void stripLeadingZeroesNull() {
    Assert.assertNull(ByteUtil.stripLeadingZeroes(null));
  }

  @Test
  public void stripLeadingZeroesAllZeros() {
    byte[] result = ByteUtil.stripLeadingZeroes(new byte[]{0, 0, 0});
    Assert.assertArrayEquals(ByteUtil.ZERO_BYTE_ARRAY, result);
  }

  @Test
  public void stripLeadingZeroesNoZeros() {
    byte[] input = {1, 2, 3};
    byte[] result = ByteUtil.stripLeadingZeroes(input);
    Assert.assertArrayEquals(input, result);
  }

  @Test
  public void stripLeadingZeroesSomeLeadingZeros() {
    byte[] result = ByteUtil.stripLeadingZeroes(new byte[]{0, 0, 1, 2});
    Assert.assertArrayEquals(new byte[]{1, 2}, result);
  }

  // --- copyToArray ---

  @Test
  public void copyToArrayZero() {
    byte[] result = ByteUtil.copyToArray(BigInteger.ZERO);
    Assert.assertEquals(32, result.length);
    for (byte b : result) {
      Assert.assertEquals(0, b);
    }
  }

  @Test
  public void copyToArrayValue() {
    byte[] result = ByteUtil.copyToArray(BigInteger.ONE);
    Assert.assertEquals(32, result.length);
    Assert.assertEquals(1, result[31]);
  }

  // --- numberOfLeadingZeros ---

  @Test
  public void numberOfLeadingZerosAllZeros() {
    byte[] input = {0, 0, 0};
    Assert.assertEquals(24, ByteUtil.numberOfLeadingZeros(input));
  }

  @Test
  public void numberOfLeadingZerosNoZeros() {
    // 0xff = 11111111 → 0 leading zeros
    byte[] input = {(byte) 0xff};
    Assert.assertEquals(0, ByteUtil.numberOfLeadingZeros(input));
  }

  // --- parseBytes ---

  @Test
  public void parseBytesOffsetBeyondLength() {
    byte[] input = {1, 2, 3};
    byte[] result = ByteUtil.parseBytes(input, 10, 4);
    Assert.assertArrayEquals(ByteUtil.EMPTY_BYTE_ARRAY, result);
  }

  @Test
  public void parseBytesZeroLength() {
    byte[] input = {1, 2, 3};
    byte[] result = ByteUtil.parseBytes(input, 0, 0);
    Assert.assertArrayEquals(ByteUtil.EMPTY_BYTE_ARRAY, result);
  }

  @Test
  public void parseBytesNormal() {
    byte[] input = {1, 2, 3, 4, 5};
    byte[] result = ByteUtil.parseBytes(input, 1, 3);
    Assert.assertArrayEquals(new byte[]{2, 3, 4}, result);
  }

  // --- parseWord ---

  @Test
  public void parseWordFirst() {
    byte[] input = new byte[64];
    input[31] = 42;
    byte[] word = ByteUtil.parseWord(input, 0);
    Assert.assertEquals(32, word.length);
    Assert.assertEquals(42, word[31]);
  }

  @Test
  public void parseWordWithOffset() {
    byte[] input = new byte[64];
    input[32] = 7;
    byte[] word = ByteUtil.parseWord(input, 0, 1);
    Assert.assertEquals(32, word.length);
    Assert.assertEquals(7, word[0]);
  }

  // --- comparison methods ---

  @Test
  public void greaterTrue() {
    Assert.assertTrue(ByteUtil.greater(new byte[]{2}, new byte[]{1}));
  }

  @Test
  public void greaterFalse() {
    Assert.assertFalse(ByteUtil.greater(new byte[]{1}, new byte[]{2}));
  }

  @Test
  public void greaterOrEqualsEqual() {
    Assert.assertTrue(ByteUtil.greaterOrEquals(new byte[]{1}, new byte[]{1}));
  }

  @Test
  public void lessTrue() {
    Assert.assertTrue(ByteUtil.less(new byte[]{1}, new byte[]{2}));
  }

  @Test
  public void lessFalse() {
    Assert.assertFalse(ByteUtil.less(new byte[]{2}, new byte[]{1}));
  }

  @Test
  public void lessOrEqualsEqual() {
    Assert.assertTrue(ByteUtil.lessOrEquals(new byte[]{5}, new byte[]{5}));
  }

  @Test
  public void equalsTrue() {
    Assert.assertTrue(ByteUtil.equals(new byte[]{1, 2}, new byte[]{1, 2}));
  }

  @Test
  public void equalsFalse() {
    Assert.assertFalse(ByteUtil.equals(new byte[]{1, 2}, new byte[]{1, 3}));
  }

  // --- isNullOrZeroArray ---

  @Test
  public void isNullOrZeroArrayNull() {
    Assert.assertTrue(ByteUtil.isNullOrZeroArray(null));
  }

  @Test
  public void isNullOrZeroArrayEmpty() {
    Assert.assertTrue(ByteUtil.isNullOrZeroArray(new byte[0]));
  }

  @Test
  public void isNullOrZeroArrayNonEmpty() {
    Assert.assertFalse(ByteUtil.isNullOrZeroArray(new byte[]{1}));
  }

  // --- hexToBytes ---

  @Test
  public void hexToBytes() {
    byte[] result = ByteUtil.hexToBytes("0102ff");
    Assert.assertArrayEquals(new byte[]{1, 2, (byte) 0xff}, result);
  }

  // --- convertBytesVectorToVector ---

  @Test
  public void convertBytesVectorToVectorSingleByte() {
    // 0x01 = 00000001 → 7 false, 1 true
    List<Boolean> result = ByteUtil.convertBytesVectorToVector(new byte[]{1});
    Assert.assertEquals(8, result.size());
    for (int i = 0; i < 7; i++) {
      Assert.assertFalse(result.get(i));
    }
    Assert.assertTrue(result.get(7));
  }

  @Test
  public void convertBytesVectorToVectorAllOnes() {
    // 0xff = 11111111
    List<Boolean> result = ByteUtil.convertBytesVectorToVector(new byte[]{(byte) 0xff});
    Assert.assertEquals(8, result.size());
    for (Boolean b : result) {
      Assert.assertTrue(b);
    }
  }

  @Test
  public void convertBytesVectorToVectorEmpty() {
    List<Boolean> result = ByteUtil.convertBytesVectorToVector(new byte[0]);
    Assert.assertTrue(result.isEmpty());
  }
}
