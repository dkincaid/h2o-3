package water.parser;

import org.junit.Ignore;
import org.junit.Test;
import water.AutoBuffer;

import static org.junit.Assert.*;

/**
 * This is mostly a skeleton of the tests, feel free to implement the cases
 * Created by vpatryshev on 1/17/17.
 */
public class BufferedStringTest {

  @Test
  public void testWrite_impl() throws Exception {
    final String source = "this is not a string";
    BufferedString sut = new BufferedString(source);
    assertArrayEquals(source.getBytes(), sut.getBuffer());
    AutoBuffer ab = new AutoBuffer();
    sut.write_impl(ab);
    final byte[] expected = ("\u0015" + source).getBytes();
    final byte[] actual = ab.buf();
    assertArrayEquals(expected, actual);
  }

  @Test
  public void testRead_impl() throws Exception {

  }

  @Test
  public void testCompareTo() throws Exception {

  }

  @Test
  public void testHashCode() throws Exception {

  }

  @Test
  public void testAddChar() throws Exception {

  }

  @Test
  public void testAddBuff() throws Exception {

  }

  @Test
  public void testToString() throws Exception {

  }

  @Test
  public void testBytesToString() throws Exception {

  }

  @Test
  public void testToString1() throws Exception {

  }

  @Test
  public void testToBufferedString() throws Exception {

  }

  @Test
  public void testSet() throws Exception {

  }

  @Test
  public void testSet1() throws Exception {

  }

  @Test
  public void testSet2() throws Exception {

  }

  @Test
  public void testSetOff() throws Exception {

  }

  @Test
  public void testEquals() throws Exception {
    BufferedString sut = new BufferedString("abc");
    assertEquals(sut, sut);
    assertEquals(sut, new BufferedString("abc"));
    assertFalse(sut.equals("abc"));
    assertFalse(sut.equals(new BufferedString("abcd")));
    assertFalse(sut.equals(new BufferedString("ABCD")));
    assertFalse(sut.equals(new BufferedString(" abc")));
    assertFalse(sut.equals(new BufferedString("abc ")));
    assertFalse(sut.equals(new BufferedString("abc\0")));
    assertFalse(sut.equals(new BufferedString("ab")));
    assertFalse(sut.equals(new BufferedString("")));
    assertFalse(new BufferedString("").equals(sut));
  }

  @Test
  public void testSameString() throws Exception {
    BufferedString sut1 = new BufferedString("abc");
    assertFalse(sut1.sameString(null));
    assertTrue(sut1.sameString("abc"));
    assertFalse(sut1.sameString("ab"));
    assertFalse(sut1.sameString("abd"));
    assertFalse(sut1.sameString("abcd"));
    assertFalse(sut1.sameString("abC"));
    assertFalse(sut1.sameString("abс")); // this is Russian 'c' here
    assertFalse(sut1.sameString("ab"));
    BufferedString sut2 = new BufferedString("");
    assertTrue(sut2.sameString(""));
    assertFalse(sut1.sameString(null));
    assertFalse(sut2.sameString("a"));
    BufferedString sut3 = new BufferedString("a\u0100b");
    assertFalse(sut2.sameString("a\u0100b"));
  }
  
  @Ignore("This test is failing because the method is wrong and must be fixed, see PUBDEV-3957")
  @Test public void testSameStringUTF8() throws Exception {
    BufferedString sut4 = new BufferedString("a\u0088b");
    assertTrue(sut4.sameString("a\u0088b"));
  }

  @Test public void testIsOneOf() throws Exception {
    BufferedString sut = new BufferedString("abc");
    assertFalse(sut.isOneOf(null));
    assertFalse(sut.isOneOf(new String[]{}));
    assertFalse(sut.isOneOf(new String[]{"", "a", "b", "ab", "bc", "abcd", "xabc"}));
    assertTrue(sut.isOneOf(new String[]{"abc", "a", "b", "ab", "bc", "abcd"}));
    assertTrue(sut.isOneOf(new String[]{"a", "b", "ab", "bc", "abcd", "abc"}));
    assertTrue(sut.isOneOf(new String[]{"", "b", "ab", "bc", "abcd", "abc", "whateva"}));
    assertTrue(sut.isOneOf(new String[]{"", null, "ab", "bc", "abcd", "abc", "whateva"}));
  }
  
  @Test
  public void testGetBuffer() throws Exception {
    final String source = "this is not a string\u00f0";
    BufferedString sut = new BufferedString(source);
    assertArrayEquals(source.getBytes(), sut.getBuffer());
  }

  @Test
  public void testGetOffset() throws Exception {

  }

  @Test
  public void testLength() throws Exception {

  }

  @Test
  public void testGetNumericType() throws Exception {

  }
}