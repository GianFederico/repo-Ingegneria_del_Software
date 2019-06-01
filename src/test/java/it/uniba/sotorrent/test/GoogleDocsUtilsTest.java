package it.uniba.sotorrent.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import it.uniba.sotorrent.GoogleDocsUtils;
import it.uniba.sotorrent.SoQuery;

/**
 *Casi di test riguardanti GoogleDocsUtils.java
 */
@Tag ("GoogleDocsUtils")
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
  @Tag ("writeSheet")
  @DisplayName ("Test writeSheet case 1: case 2:")
  public void case1n2WriteSheetTest() {
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

  @Test
  @Tag ("writeSheet")
  @DisplayName ("Test writeSheet case 3: case 4: case 5:")
  public void case34n5WriteSheetTest() {
    try {
      String spid = gdut.createSheet("Prova");
      SoQuery soq = new SoQuery();
      String yyyy = "2016";
      String mm = "11";
      String dd = "02";
      String limit = "1";
      String query = "3";
      String groupby = "";
      String column3 = "";
      assertTrue(gdut.writeSheet(spid, soq.getResults(soq.runQuerySprint2(yyyy, mm, dd, limit, groupby, column3),
          query), query));
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  @Test
  @Tag ("writeSheet")
  @DisplayName ("Test writeSheet case 6: case 7: case 8:")
  public void case67n8WriteSheetTest() {
    try {
      String spid = gdut.createSheet("Prova");
      SoQuery soq = new SoQuery();
      String user = "86";
      String limit = "1";
      String order = "Dom";
      String where = "Risposte";
      String nnull = "Domande";
      String query = "6";
      String groupby = " GROUP BY Risposte.owner_user_id, Domande.owner_user_id";
      String column3 = ", COUNT (*) AS weight";
      assertTrue(gdut.writeSheet(spid, soq.getResults(
          soq.runQuerySprint2(user, limit, order, where, nnull, groupby, column3), query), query));
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}

