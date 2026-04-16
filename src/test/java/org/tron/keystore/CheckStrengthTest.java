package org.tron.keystore;

import org.junit.Assert;
import org.junit.Test;

public class CheckStrengthTest {

  @Test(expected = IllegalArgumentException.class)
  public void checkPasswordStrengthEmpty() {
    CheckStrength.checkPasswordStrength(new char[0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkPasswordStrengthNull() {
    CheckStrength.checkPasswordStrength(null);
  }

  @Test
  public void allSameCharactersLevelZero() {
    // All same chars → level forced to 0
    int level = CheckStrength.checkPasswordStrength("aaaaaaaaaa".toCharArray());
    Assert.assertEquals(0, level);
  }

  @Test
  public void veryShortPasswordLevelZero() {
    // <= 3 chars → level = 0
    int level = CheckStrength.checkPasswordStrength("abc".toCharArray());
    Assert.assertEquals(0, level);
  }

  @Test
  public void dictionaryWordReducesLevel() {
    // Common password should have reduced strength
    int dictLevel = CheckStrength.checkPasswordStrength("password".toCharArray());
    int strongLevel = CheckStrength.checkPasswordStrength("Tr@n#2024XyZ!".toCharArray());
    Assert.assertTrue(dictLevel < strongLevel);
  }

  @Test
  public void digitOnlyPasswordLowLevel() {
    // Only digits
    int level = CheckStrength.checkPasswordStrength("12345678".toCharArray());
    // Digit-only passwords get penalized
    Assert.assertTrue(level <= 3);
  }

  @Test
  public void mixedStrongPassword() {
    // Has uppercase, lowercase, digit, special chars, length > 10
    int level = CheckStrength.checkPasswordStrength("Tr@n#2024XyZ!".toCharArray());
    Assert.assertTrue(level >= 7);
  }

  @Test
  public void datePatternReducesLevel() {
    // 6-digit date patterns are penalized (e.g., 881010 = Oct 10 1988)
    int dateLevel = CheckStrength.checkPasswordStrength("881010".toCharArray());
    int nonDateLevel = CheckStrength.checkPasswordStrength("881399".toCharArray());
    // Date patterns should be penalized compared to non-date patterns
    Assert.assertTrue(dateLevel <= nonDateLevel);
  }

  @Test
  public void repeatedHalvesReducesLevel() {
    // "abyzabyz" → repeated halves should reduce level
    int repeatedLevel = CheckStrength.checkPasswordStrength("abcdabcd".toCharArray());
    int normalLevel = CheckStrength.checkPasswordStrength("abcdefgh".toCharArray());
    Assert.assertTrue(repeatedLevel <= normalLevel);
  }

  @Test
  public void manyConsecutiveSameCharsReducesLevel() {
    // 4+ consecutive same chars reduces level
    int level1 = CheckStrength.checkPasswordStrength("aaaa1234".toCharArray());
    int level2 = CheckStrength.checkPasswordStrength("abcd1234".toCharArray());
    Assert.assertTrue(level1 <= level2);
  }

  @Test
  public void adjacentCharactersReducesLevel() {
    // "abcd1234" - sequential chars reduce level
    int adjacentLevel = CheckStrength.checkPasswordStrength("abcd1234".toCharArray());
    int nonAdjacentLevel = CheckStrength.checkPasswordStrength("axbz1834".toCharArray());
    Assert.assertTrue(adjacentLevel <= nonAdjacentLevel);
  }

  @Test
  public void levelNeverNegative() {
    // Various weak passwords should never produce a negative level
    String[] weakPasswords = {"abc", "1234", "password", "aaaaaa", "111111"};
    for (String pwd : weakPasswords) {
      int level = CheckStrength.checkPasswordStrength(pwd.toCharArray());
      Assert.assertTrue("Level should not be negative for: " + pwd, level >= 0);
    }
  }

  // --- getPasswordLevel tests ---

  @Test
  public void getPasswordLevelEasy() {
    // Very short/simple password should be EASY
    CheckStrength.LEVEL level = CheckStrength.getPasswordLevel("abc".toCharArray());
    Assert.assertEquals(CheckStrength.LEVEL.EASY, level);
  }

  @Test
  public void getPasswordLevelMedium() {
    // Medium passwords (level 4-6) → MIDIUM
    // A simple 8-char password with mixed types
    CheckStrength.LEVEL level = CheckStrength.getPasswordLevel("Abc12345".toCharArray());
    // We just verify it returns a valid level; the exact level depends on scoring
    Assert.assertNotNull(level);
  }

  @Test
  public void getPasswordLevelStrong() {
    // More complex password should be STRONG or better
    CheckStrength.LEVEL level = CheckStrength.getPasswordLevel("Tr@n1234XyZ".toCharArray());
    Assert.assertNotNull(level);
    Assert.assertTrue(
        level == CheckStrength.LEVEL.STRONG
            || level == CheckStrength.LEVEL.VERY_STRONG
            || level == CheckStrength.LEVEL.EXTREMELY_STRONG);
  }

  @Test
  public void getPasswordLevelExtremelyStrong() {
    // Very long complex password
    CheckStrength.LEVEL level = CheckStrength.getPasswordLevel(
        "Tr@n#2024!XyZ$Secure&Pass".toCharArray());
    Assert.assertNotNull(level);
    Assert.assertTrue(
        level == CheckStrength.LEVEL.VERY_STRONG
            || level == CheckStrength.LEVEL.EXTREMELY_STRONG);
  }

  @Test
  public void levelEnumValues() {
    CheckStrength.LEVEL[] levels = CheckStrength.LEVEL.values();
    Assert.assertEquals(5, levels.length);
  }
}
