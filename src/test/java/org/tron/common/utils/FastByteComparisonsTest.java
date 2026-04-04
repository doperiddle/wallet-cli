package org.tron.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class FastByteComparisonsTest {

  @Test
  public void isEqualSameContent() {
    byte[] a = {1, 2, 3};
    byte[] b = {1, 2, 3};
    Assert.assertTrue(FastByteComparisons.isEqual(a, b));
  }

  @Test
  public void isEqualDifferentContent() {
    byte[] a = {1, 2, 3};
    byte[] b = {1, 2, 4};
    Assert.assertFalse(FastByteComparisons.isEqual(a, b));
  }

  @Test
  public void isEqualDifferentLength() {
    byte[] a = {1, 2, 3};
    byte[] b = {1, 2};
    Assert.assertFalse(FastByteComparisons.isEqual(a, b));
  }

  @Test
  public void isEqualEmpty() {
    Assert.assertTrue(FastByteComparisons.isEqual(new byte[0], new byte[0]));
  }

  @Test
  public void isEqualSameReference() {
    byte[] a = {5, 6, 7};
    Assert.assertTrue(FastByteComparisons.isEqual(a, a));
  }

  @Test
  public void compareToEqual() {
    byte[] a = {1, 2, 3};
    byte[] b = {1, 2, 3};
    Assert.assertEquals(0, FastByteComparisons.compareTo(a, 0, a.length, b, 0, b.length));
  }

  @Test
  public void compareToLess() {
    byte[] a = {1, 2, 3};
    byte[] b = {1, 2, 4};
    Assert.assertTrue(FastByteComparisons.compareTo(a, 0, a.length, b, 0, b.length) < 0);
  }

  @Test
  public void compareToGreater() {
    byte[] a = {1, 2, 5};
    byte[] b = {1, 2, 4};
    Assert.assertTrue(FastByteComparisons.compareTo(a, 0, a.length, b, 0, b.length) > 0);
  }

  @Test
  public void compareToShorterLength() {
    byte[] a = {1, 2};
    byte[] b = {1, 2, 3};
    Assert.assertTrue(FastByteComparisons.compareTo(a, 0, a.length, b, 0, b.length) < 0);
  }

  @Test
  public void compareToWithOffset() {
    byte[] a = {0, 1, 2, 3};
    byte[] b = {1, 2, 3};
    // Compare a[1..3] vs b[0..2]
    Assert.assertEquals(0,
        FastByteComparisons.compareTo(a, 1, 3, b, 0, 3));
  }

  @Test
  public void compareToSameReference() {
    byte[] a = {1, 2, 3};
    Assert.assertEquals(0, FastByteComparisons.compareTo(a, 0, a.length, a, 0, a.length));
  }

  @Test
  public void compareToEmptyArrays() {
    Assert.assertEquals(0,
        FastByteComparisons.compareTo(new byte[0], 0, 0, new byte[0], 0, 0));
  }
}
