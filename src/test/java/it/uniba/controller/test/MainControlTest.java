package it.uniba.controller.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import it.uniba.controller.MainControl;
/**
 *Classe di test per MainControl.java
 */
@Tag ("MainControl")
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
  @Tag ("control")
  @DisplayName ("Test control con pochi argomenti")
  public void fewArgsControlTest() {
    String[] provaFewargs = {"yyyy=2016", "mm=02"};
    try {
      assertFalse(mc.control(provaFewargs));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  @Test
  @Tag ("control")
  @DisplayName ("Test control case 1:")
  public void case1ControlTest() {
    String[] inputDProva = {"yyyy=2016", "mm=02", "dd=11", "type=question", "limit=2"};
    try {
      assertTrue(mc.control(inputDProva));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  @Test
  @Tag ("control")
  @DisplayName ("Test control case 2:")
  public void case2ControlTest() {
    String[] inputDProva = {"yyyy=2016", "mm=02", "type=question", "taglike=java", "limit=2"};
    try {
      assertTrue(mc.control(inputDProva));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  @Test
  @Tag ("control")
  @DisplayName ("Test control case 3: case 6:")
  public void case3n6ControlTest() {
    String[] inputDProva = {"yyyy=2016", "mm=02", "dd=11", "type=question", "edge=yes",
        "weight=no", "limit=2"};
    try {
      assertTrue(mc.control(inputDProva));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  @Test
  @Tag ("control")
  @DisplayName ("Test control case 4: case 7:")
  public void case4n7ControlTest() {
    String[] inputDProva = {"type=question", "edge=yes", "weight=no", "limit=2", "user=1109"};
    try {
      assertTrue(mc.control(inputDProva));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  @Test
  @Tag ("control")
  @DisplayName ("Test control case 5: case 8:")
  public void case5n8ControlTest() {
    String[] inputDProva = {"type=answer", "edge=yes", "weight=no", "limit=2", "user=1109"};
    try {
      assertTrue(mc.control(inputDProva));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  @Test
  @Tag ("control")
  @DisplayName ("Test control anno fuori dal range")
  public void yearOutOfBoundControlTest() {
    String[] inputDProva = {"yyyy=2000", "mm=02", "dd=11", "type=question", "edge=yes",
        "weight=no", "limit=2", "user=1109", "taglike=java"};
    try {
      assertFalse(mc.control(inputDProva));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  @Test
  @Tag ("control")
  @DisplayName ("Test control errore su combinazione edge e weight")
  public void notEdgeControlTest() {
    String[] provaNotEdge = {"yyyy=2016", "mm=02", "dd=11", "type=question", "edge=no",
        "weight=yes", "limit=2", "user=1109", "taglike=java"};
    try {
      assertFalse(mc.control(provaNotEdge));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

}
