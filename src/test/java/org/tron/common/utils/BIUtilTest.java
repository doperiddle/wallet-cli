package org.tron.common.utils;

import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Test;

public class BIUtilTest {

  @Test
  public void isLessThanTrue() {
    Assert.assertTrue(BIUtil.isLessThan(BigInteger.ZERO, BigInteger.ONE));
  }

  @Test
  public void isLessThanFalseEqual() {
    Assert.assertFalse(BIUtil.isLessThan(BigInteger.ONE, BigInteger.ONE));
  }

  @Test
  public void isLessThanFalseGreater() {
    Assert.assertFalse(BIUtil.isLessThan(BigInteger.TEN, BigInteger.ONE));
  }

  @Test
  public void isLessThanNegativeValues() {
    BigInteger negTwo = BigInteger.valueOf(-2);
    BigInteger negOne = BigInteger.valueOf(-1);
    Assert.assertTrue(BIUtil.isLessThan(negTwo, negOne));
    Assert.assertFalse(BIUtil.isLessThan(negOne, negTwo));
  }

  @Test
  public void isLessThanLargeValues() {
    BigInteger two = BigInteger.valueOf(2);
    BigInteger large = two.pow(256).subtract(BigInteger.ONE);
    BigInteger larger = two.pow(256);
    Assert.assertTrue(BIUtil.isLessThan(large, larger));
  }
}
