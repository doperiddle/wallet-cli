package org.tron.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class ByteArrayWrapperTest {

  @Test(expected = NullPointerException.class)
  public void constructorNullThrows() {
    new ByteArrayWrapper(null);
  }

  @Test
  public void constructorEmpty() {
    ByteArrayWrapper wrapper = new ByteArrayWrapper(new byte[0]);
    Assert.assertArrayEquals(new byte[0], wrapper.getData());
  }

  @Test
  public void constructorWithData() {
    byte[] data = {1, 2, 3};
    ByteArrayWrapper wrapper = new ByteArrayWrapper(data);
    Assert.assertArrayEquals(data, wrapper.getData());
  }

  @Test
  public void equalsNull() {
    ByteArrayWrapper wrapper = new ByteArrayWrapper(new byte[]{1, 2});
    Assert.assertFalse(wrapper.equals(null));
  }

  @Test
  public void equalsDifferentType() {
    ByteArrayWrapper wrapper = new ByteArrayWrapper(new byte[]{1, 2});
    Assert.assertFalse(wrapper.equals("not a wrapper"));
  }

  @Test
  public void equalsSameData() {
    ByteArrayWrapper a = new ByteArrayWrapper(new byte[]{1, 2, 3});
    ByteArrayWrapper b = new ByteArrayWrapper(new byte[]{1, 2, 3});
    Assert.assertTrue(a.equals(b));
  }

  @Test
  public void equalsDifferentData() {
    ByteArrayWrapper a = new ByteArrayWrapper(new byte[]{1, 2, 3});
    ByteArrayWrapper b = new ByteArrayWrapper(new byte[]{1, 2, 4});
    Assert.assertFalse(a.equals(b));
  }

  @Test
  public void equalsSelf() {
    ByteArrayWrapper wrapper = new ByteArrayWrapper(new byte[]{5, 6, 7});
    Assert.assertTrue(wrapper.equals(wrapper));
  }

  @Test
  public void hashCodeConsistency() {
    ByteArrayWrapper a = new ByteArrayWrapper(new byte[]{1, 2, 3});
    ByteArrayWrapper b = new ByteArrayWrapper(new byte[]{1, 2, 3});
    Assert.assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  public void compareToEqual() {
    ByteArrayWrapper a = new ByteArrayWrapper(new byte[]{1, 2, 3});
    ByteArrayWrapper b = new ByteArrayWrapper(new byte[]{1, 2, 3});
    Assert.assertEquals(0, a.compareTo(b));
  }

  @Test
  public void compareToLess() {
    ByteArrayWrapper a = new ByteArrayWrapper(new byte[]{1, 2, 3});
    ByteArrayWrapper b = new ByteArrayWrapper(new byte[]{1, 2, 4});
    Assert.assertTrue(a.compareTo(b) < 0);
  }

  @Test
  public void compareToGreater() {
    ByteArrayWrapper a = new ByteArrayWrapper(new byte[]{1, 2, 5});
    ByteArrayWrapper b = new ByteArrayWrapper(new byte[]{1, 2, 4});
    Assert.assertTrue(a.compareTo(b) > 0);
  }

  @Test
  public void toStringHex() {
    ByteArrayWrapper wrapper = new ByteArrayWrapper(new byte[]{0x0a, (byte) 0xff});
    Assert.assertEquals("0aff", wrapper.toString());
  }

  @Test
  public void toStringEmpty() {
    ByteArrayWrapper wrapper = new ByteArrayWrapper(new byte[0]);
    Assert.assertEquals("", wrapper.toString());
  }
}
