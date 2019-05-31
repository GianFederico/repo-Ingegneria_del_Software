package it.uniba.controller.test;

import static org.junit.Assert.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;

//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.security.GeneralSecurityException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
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

  /*@Test
  @Disabled
  @DisplayName ("Test control")
  public void controlTest() throws FileNotFoundException, IOException,
    InterruptedException, GeneralSecurityException, URISyntaxException {
    String[] inputDProva = {"yyyy=2016", "mm=02", "dd=11", "type=question", "edge=yes",
        "weight=no", "limit=3", "user=1109", "taglike=java"};
    assertFalse(mc.control(inputDProva));
    inputDProva[0] = "yyyy=2000";
    assertTrue(mc.control(inputDProva));
  }*/
}
