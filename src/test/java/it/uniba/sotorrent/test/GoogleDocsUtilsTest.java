package it.uniba.sotorrent.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.sotorrent.GoogleDocsUtils;
import it.uniba.sotorrent.SoQuery;

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

  @Test
  @DisplayName ("Test writeSheet")
  public void writeSheetTest() {
    try {
      String spid = gdut.createSheet("Prova");
      SoQuery soq = new SoQuery();
      String yyyy = "2016";
      String mm = "11";
      String dd = "02";
      String[] ptid = {"1", "1"};
      String limit = "1";
      String query = "1";
      assertTrue(gdut.writeSheet(spid, soq.getResults(soq.runQuerySprint1(yyyy, mm, dd, ptid, limit), query), query));
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}

