package it.uniba.controller.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import it.uniba.controller.MainControl;
/**
 *
 */
class MainControlTest {

  private static MainControl mc = null;

  @BeforeAll
  static void setUp() {
    mc = new MainControl();
  }

  @AfterAll
  static void tearDown() {
    mc = null;
  }

  @Test
  @DisplayName ("Test constructor")
  public void constructorTest() {
    assertNotNull(mc);
  }

  @Test
  @DisplayName ("Test control")
  public void controlTest() {
    String[] inputDProva = {"yyyy=2016", "mm=02", "dd=11", "type=question", "edge=yes",
        "weight=no", "limit=3", "user=1109", "taglike=java"};
    try {
      assertTrue(mc.control(inputDProva));
    } catch (Exception e) {
      System.err.println(e);
    }
    inputDProva[0] = "yyyy=2000";
    try {
      assertFalse(mc.control(inputDProva));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

}
