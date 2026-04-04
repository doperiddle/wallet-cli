package org.tron.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class Bech32Test {

  @Test
  public void encodeAndDecode() {
    String hrp = "tron";
    byte[] data = new byte[]{0, 1, 2, 3, 4};
    String encoded = Bech32.encode(hrp, data);
    Bech32.Bech32Data decoded = Bech32.decode(encoded);
    Assert.assertEquals(hrp, decoded.hrp);
    Assert.assertArrayEquals(data, decoded.data);
  }

  @Test
  public void encodeProducesSeparator() {
    String hrp = "bc";
    byte[] data = new byte[]{0, 1, 2, 3, 4};
    String encoded = Bech32.encode(hrp, data);
    // The separator '1' separates the human-readable part from the data part
    int sepPos = encoded.lastIndexOf('1');
    Assert.assertTrue(sepPos > 0);
    Assert.assertEquals(hrp, encoded.substring(0, sepPos));
  }

  @Test
  public void encodeNormalizesCaseToLower() {
    String encoded = Bech32.encode("TRX", new byte[]{0, 1, 2, 3, 4});
    Assert.assertEquals(encoded, encoded.toLowerCase());
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeTooShort() {
    // Input shorter than 8 chars
    Bech32.decode("abc1de");
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeTooLong() {
    // Input longer than 90 chars
    StringBuilder sb = new StringBuilder("a1");
    for (int i = 0; i < 90; i++) {
      sb.append('q');
    }
    Bech32.decode(sb.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeMixedCase() {
    Bech32.decode("Bc1qw508d6qejxtdg4y5r3zarvary0c5xw7kv8f3t4");
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeMissingHumanReadablePart() {
    // Starts with '1', so pos < 1
    Bech32.decode("1qqqqqqqqqqqqq");
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeInvalidChecksum() {
    // Valid-looking Bech32 with wrong checksum
    Bech32.decode("tron1qqqqqqqq");
  }

  @Test
  public void roundTripWithBech32DataObject() {
    // Encode via encode(hrp, data) then decode, comparing via encode(Bech32Data)
    String hrp = "test";
    byte[] data = {5, 10, 15, 20, 25};
    String encoded = Bech32.encode(hrp, data);
    Bech32.Bech32Data decoded = Bech32.decode(encoded);
    // Re-encode the decoded object using the Bech32Data overload
    String reEncoded = Bech32.encode(decoded);
    Assert.assertEquals(encoded, reEncoded);
    Assert.assertEquals(hrp, decoded.hrp);
    Assert.assertArrayEquals(data, decoded.data);
  }

  @Test
  public void encodeWithUppercaseHrpIsLowercased() {
    String encoded1 = Bech32.encode("TRON", new byte[]{1, 2, 3, 4, 5});
    String encoded2 = Bech32.encode("tron", new byte[]{1, 2, 3, 4, 5});
    Assert.assertEquals(encoded1, encoded2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void encodeEmptyHrp() {
    Bech32.encode("", new byte[]{0, 1, 2});
  }

  @Test(expected = IllegalArgumentException.class)
  public void encodeTooLongHrp() {
    // HRP longer than 83 chars
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 84; i++) {
      sb.append('a');
    }
    Bech32.encode(sb.toString(), new byte[]{0, 1, 2});
  }
}
