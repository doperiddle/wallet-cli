package org.tron.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class DecodeUtilTest {

  @Test
  public void addressValidNull() {
    Assert.assertFalse(DecodeUtil.addressValid(null));
  }

  @Test
  public void addressValidEmpty() {
    Assert.assertFalse(DecodeUtil.addressValid(new byte[0]));
  }

  @Test
  public void addressValidWrongLength() {
    // ADDRESS_SIZE = 42, so valid address is 21 bytes (42/2)
    byte[] tooShort = new byte[20];
    tooShort[0] = DecodeUtil.ADD_PRE_FIX_BYTE_MAINNET;
    Assert.assertFalse(DecodeUtil.addressValid(tooShort));

    byte[] tooLong = new byte[22];
    tooLong[0] = DecodeUtil.ADD_PRE_FIX_BYTE_MAINNET;
    Assert.assertFalse(DecodeUtil.addressValid(tooLong));
  }

  @Test
  public void addressValidWrongPrefix() {
    byte[] address = new byte[21];
    address[0] = 0x42; // wrong prefix (should be 0x41)
    Assert.assertFalse(DecodeUtil.addressValid(address));
  }

  @Test
  public void addressValidCorrect() {
    byte[] address = new byte[21];
    address[0] = DecodeUtil.ADD_PRE_FIX_BYTE_MAINNET; // 0x41
    Assert.assertTrue(DecodeUtil.addressValid(address));
  }

  @Test
  public void addressPrefixByte() {
    Assert.assertEquals((byte) 0x41, DecodeUtil.ADD_PRE_FIX_BYTE_MAINNET);
  }

  @Test
  public void addressSize() {
    Assert.assertEquals(42, DecodeUtil.ADDRESS_SIZE);
  }

  @Test
  public void createReadableString() {
    byte[] bytes = {0x0a, 0x1b, (byte) 0xff};
    String result = DecodeUtil.createReadableString(bytes);
    Assert.assertEquals("0a1bff", result);
  }

  @Test
  public void createReadableStringEmpty() {
    String result = DecodeUtil.createReadableString(new byte[0]);
    Assert.assertEquals("", result);
  }
}
