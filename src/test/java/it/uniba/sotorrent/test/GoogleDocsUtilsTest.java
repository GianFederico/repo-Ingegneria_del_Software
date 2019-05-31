package it.uniba.sotorrent.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.sotorrent.GoogleDocsUtils;
/**
 *
 */
 abstract class GoogleDocsUtilsTest {
  private static GoogleDocsUtils gdut = null;

  @BeforeAll
  static void setUp() {
    System.out.println("setUpAll");
    gdut = new GoogleDocsUtils();
  }

  @AfterAll
  static void tearDown() {
    System.out.println("tearDownAll");
    gdut = null;
  }

  @Test
  @DisplayName ("Test createSheet")
  public void createSheetTest() {
    try {
      assertNotNull(gdut.createSheet("Prova"));
    } catch (Exception e) {
      System.err.println(e);
    }
  }



}

