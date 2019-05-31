/**
 * 
 */
package it.uniba.controller.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import it.uniba.controller.DateValidator;

/**
 * Test della classe DateValidator.java
 *
 */
class DateValidatorTest {

  private static DateValidator dtv = null;

  @BeforeAll
  static void setUp() {
     dtv = new DateValidator();
  }

  @AfterAll
  static void tearDown() {
    dtv = null;
  }

  @Test
  @DisplayName ("Test di convalida della data")
  void isThisDateValidTrueTest() {
    assertTrue(dtv.isThisDateValid("2000", "01", "01"));
  }

  @Test
  @DisplayName ("Test con data errata")
  void isThisDateValidFalseTest() {
    assertFalse(dtv.isThisDateValid("-1", "01", "01")); //Formato anno non valido
    assertFalse(dtv.isThisDateValid("2000", "13", "01")); //Formato mese non valido
    assertFalse(dtv.isThisDateValid("2000", "01", "32")); //Formato giorno non valido
    assertFalse(dtv.isThisDateValid("-1", "13", "01")); //Formato anno e mese non validi
    assertFalse(dtv.isThisDateValid("2000", "13", "32")); //Formato mese e giorno non validi
    assertFalse(dtv.isThisDateValid("-1", "01", "32")); //Formato anno e giorno non validi
    assertFalse(dtv.isThisDateValid("-1", "13", "32")); //Formato anno, mese e giorno non valuidi
  }
}
