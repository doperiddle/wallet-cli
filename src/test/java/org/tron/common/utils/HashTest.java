package org.tron.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class HashTest {

  // SHA3 (Keccak-256) of empty input is a well-known constant
  private static final byte[] KECCAK256_OF_EMPTY = ByteArray.fromHexString(
      "c5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470");

  @Test
  public void sha3EmptyInput() {
    byte[] result = Hash.sha3(new byte[0]);
    Assert.assertEquals(32, result.length);
    Assert.assertArrayEquals(KECCAK256_OF_EMPTY, result);
  }

  @Test
  public void sha3NonEmpty() {
    byte[] input = "hello".getBytes();
    byte[] result = Hash.sha3(input);
    Assert.assertEquals(32, result.length);
    // Same input always produces same output
    byte[] result2 = Hash.sha3(input);
    Assert.assertArrayEquals(result, result2);
  }

  @Test
  public void sha3TwoInputsConcatenated() {
    byte[] part1 = "hel".getBytes();
    byte[] part2 = "lo".getBytes();
    byte[] combined = "hello".getBytes();
    byte[] hashTwoParts = Hash.sha3(part1, part2);
    byte[] hashCombined = Hash.sha3(combined);
    Assert.assertArrayEquals(hashCombined, hashTwoParts);
  }

  @Test
  public void sha3WithRange() {
    byte[] input = "hello world".getBytes();
    byte[] fullHash = Hash.sha3("hello".getBytes());
    byte[] rangeHash = Hash.sha3(input, 0, 5);
    Assert.assertArrayEquals(fullHash, rangeHash);
  }

  @Test
  public void sha512Length() {
    byte[] result = Hash.sha512("test".getBytes());
    Assert.assertEquals(64, result.length);
  }

  @Test
  public void sha512Deterministic() {
    byte[] input = "test".getBytes();
    Assert.assertArrayEquals(Hash.sha512(input), Hash.sha512(input));
  }

  @Test
  public void sha512DifferentFromSha3() {
    byte[] input = "test".getBytes();
    byte[] sha3 = Hash.sha3(input);
    byte[] sha512 = Hash.sha512(input);
    // They are different algorithms and different lengths
    Assert.assertNotEquals(sha3.length, sha512.length);
  }

  // --- encodeElement ---

  @Test
  public void encodeElementNull() {
    // null/empty → [0x80]
    byte[] result = Hash.encodeElement(null);
    Assert.assertArrayEquals(new byte[]{(byte) 0x80}, result);
  }

  @Test
  public void encodeElementEmpty() {
    byte[] result = Hash.encodeElement(new byte[0]);
    Assert.assertArrayEquals(new byte[]{(byte) 0x80}, result);
  }

  @Test
  public void encodeElementSingleZero() {
    // Single zero → returns the byte itself (no length prefix)
    byte[] input = new byte[]{0};
    byte[] result = Hash.encodeElement(input);
    Assert.assertArrayEquals(new byte[]{0}, result);
  }

  @Test
  public void encodeElementSingleByteBelow0x80() {
    // Single byte in [0x01, 0x7f] → no prefix, just the byte
    byte[] input = new byte[]{0x42};
    byte[] result = Hash.encodeElement(input);
    Assert.assertArrayEquals(new byte[]{0x42}, result);
  }

  @Test
  public void encodeElementShortByteArray() {
    // 2-byte array gets prefixed with 0x80 + length
    byte[] input = {0x01, 0x02};
    byte[] result = Hash.encodeElement(input);
    Assert.assertEquals(3, result.length);
    Assert.assertEquals((byte) (0x80 + 2), result[0]);
    Assert.assertEquals(0x01, result[1]);
    Assert.assertEquals(0x02, result[2]);
  }

  @Test
  public void encodeElementLongByteArray() {
    // 56-byte array → long-form encoding (0xb7 + length-of-length)
    byte[] input = new byte[56];
    byte[] result = Hash.encodeElement(input);
    Assert.assertTrue(result.length > 56);
    // First byte should be 0xb8 (= 0xb7 + 1 byte for length)
    Assert.assertEquals((byte) 0xb8, result[0]);
  }

  // --- sha3omit12 ---

  @Test
  public void sha3omit12Length() {
    byte[] input = new byte[64]; // typical uncompressed pubkey bytes (without 0x04 prefix)
    byte[] result = Hash.sha3omit12(input);
    // Returns last 21 bytes of sha3 (32) → [11..31]
    Assert.assertEquals(21, result.length);
  }

  @Test
  public void sha3omit12Prefix() {
    byte[] input = new byte[64];
    byte[] result = Hash.sha3omit12(input);
    // First byte should be the address prefix byte
    Assert.assertEquals(DecodeUtil.addressPreFixByte, result[0]);
  }
}
