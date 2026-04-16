package org.tron.common.utils;

import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Test;

public class Base58Test {

  @Test
  public void encodeEmpty() {
    Assert.assertEquals("", Base58.encode(new byte[0]));
  }

  @Test
  public void encodeAndDecode() {
    byte[] input = new byte[]{1, 2, 3, 4, 5};
    String encoded = Base58.encode(input);
    Assert.assertFalse(encoded.isEmpty());
    byte[] decoded = Base58.decode(encoded);
    Assert.assertArrayEquals(input, decoded);
  }

  @Test
  public void decodeEmpty() {
    byte[] result = Base58.decode("");
    Assert.assertArrayEquals(new byte[0], result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeInvalidCharacter() {
    // '0' is not in Base58 alphabet
    Base58.decode("0abc");
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeInvalidCharacterO() {
    // 'O' is not in Base58 alphabet
    Base58.decode("Oabc");
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeInvalidCharacterI() {
    // 'I' is not in Base58 alphabet
    Base58.decode("Iabc");
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeInvalidCharacterL() {
    // 'l' is not in Base58 alphabet
    Base58.decode("labc");
  }

  @Test
  public void encodeLeadingZeros() {
    // Leading zero bytes map to '1' characters in Base58
    byte[] input = new byte[]{0, 0, 1};
    String encoded = Base58.encode(input);
    Assert.assertTrue(encoded.startsWith("11"));
    byte[] decoded = Base58.decode(encoded);
    Assert.assertArrayEquals(input, decoded);
  }

  @Test
  public void encodeAllZeros() {
    byte[] input = new byte[]{0, 0, 0};
    String encoded = Base58.encode(input);
    // All zero bytes become leading '1' chars
    for (char c : encoded.toCharArray()) {
      Assert.assertEquals('1', c);
    }
    byte[] decoded = Base58.decode(encoded);
    Assert.assertArrayEquals(input, decoded);
  }

  @Test
  public void decodeToBigInteger() {
    byte[] input = new byte[]{0x01, 0x00};
    String encoded = Base58.encode(input);
    BigInteger bi = Base58.decodeToBigInteger(encoded);
    Assert.assertEquals(new BigInteger(1, input), bi);
  }

  @Test
  public void roundTripLargeData() {
    byte[] input = new byte[32];
    for (int i = 0; i < 32; i++) {
      input[i] = (byte) (i + 1);
    }
    String encoded = Base58.encode(input);
    byte[] decoded = Base58.decode(encoded);
    Assert.assertArrayEquals(input, decoded);
  }

  @Test
  public void alphabetContainsExpectedCharacters() {
    String alphabet = new String(Base58.ALPHABET);
    // Base58 alphabet should not contain 0, O, I, l
    Assert.assertFalse(alphabet.contains("0"));
    Assert.assertFalse(alphabet.contains("O"));
    Assert.assertFalse(alphabet.contains("I"));
    Assert.assertFalse(alphabet.contains("l"));
    Assert.assertEquals(58, Base58.ALPHABET.length);
  }
}
