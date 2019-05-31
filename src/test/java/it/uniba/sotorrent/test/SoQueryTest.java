package it.uniba.sotorrent.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.sotorrent.SoQuery;

/**
 */
class SoQueryTest {

  private static SoQuery soq = null;

  @BeforeAll
  static void setUp() throws Exception {
    soq = new SoQuery();
  }

  @AfterAll
  static void tearDown() throws Exception {
    soq = null;
  }

  @BeforeEach
  void setUpBigQueryAPI() {
    assertNotNull(soq.getBigQuery());
  }

  @Test
  @DisplayName ("Test runQuerySprint1 1 out of 2")
  public void test1RunQuerySprint1() {
    final String yyyy = "2016";
    String mm = "11";
    String dd = "02";
    String[] ptid = {"1", "1"};
    String limit = "1";
    try {
      assertNotNull(soq.runQuerySprint1(yyyy, mm, dd, ptid, limit));
    } catch (InterruptedException e) {
      System.err.println(e);
    }
    final String wyear = "aaaaa";
    assertThrows(RuntimeException.class, () -> soq.runQuerySprint1(wyear, mm, dd, ptid, limit));
  }

  @Test
  @DisplayName ("Test runQuerySprint1 2 out of 2")
  public void test2RunQuerySprint1() {
    String yyyy = "2016";
    String mm = "11";
    String taglike = "limit";
    String[] ptid = {"1", "1"};
    String limit = "1";
    try {
      assertNotNull(soq.runQuerySprint1(yyyy, mm, ptid, taglike, limit));
    } catch (InterruptedException e) {
      System.err.println(e);
    }
    final String wyear = "aaaaa";
    assertThrows(RuntimeException.class, () -> soq.runQuerySprint1(wyear, mm, ptid, taglike, limit));
  }

  @Test
  @DisplayName ("Test runQuerySprint2 1 out of 2")
  public void test1RunQuerySprint2() throws InterruptedException {
    String yyyy = "2016";
    String mm = "11";
    String dd = "02";
    String limit = "1";
    String groupby = " GROUP BY Risposte.owner_user_id, Domande.owner_user_id";
    String column3 = ", COUNT (*) AS weight";
    try {
      assertNotNull(soq.runQuerySprint2(yyyy, mm, dd, limit, groupby, column3));
    } catch (InterruptedException e) {
      System.err.println(e);
    }
    final String wyear = "aaaaa";
    assertThrows(RuntimeException.class, () -> soq.runQuerySprint2(wyear, mm, dd, limit, groupby, column3));
  }

  @Test
  @DisplayName ("Test runQuerySprint2 2 out of 2")
  public void test2RunQuerySprint2() {
    String user = "1109";
    String limit = "2";
    String order = "Ris";
    String where = "Domande";
    String nnull = "Risposte";
    String groupby = " GROUP BY Risposte.owner_user_id, Domande.owner_user_id";
    String column3 = ", COUNT (*) AS weight";
    try {
      assertNotNull(soq.runQuerySprint2(user, limit, order, where, nnull, groupby, column3));
    } catch (InterruptedException e) {
      System.err.println(e);
    }
    final String wuser = "aaaaa";
    assertThrows(RuntimeException.class, () -> soq.runQuerySprint2(wuser, limit, order, where, nnull,
        groupby, column3));
  }

  @Test
  @DisplayName ("Test getResults")
  public void testGetResults() {
    String yyyy = "2016";
    String mm = "11";
    String dd = "02";
    String[] ptid = {"1", "1"};
    String limit = "1";
    String query = "1";
    try {
      assertNotNull(soq.getResults(soq.runQuerySprint1(yyyy, mm, dd, ptid, limit), query));
    } catch (InterruptedException e) {
      System.err.println(e);
    }

    String taglike = "java";
    try {
      assertNotNull(soq.getResults(soq.runQuerySprint1(yyyy, mm, ptid, taglike, limit), query));
    } catch (InterruptedException e) {
      System.err.println(e);
    }

    query = "3";
    String groupby = " GROUP BY Risposte.owner_user_id, Domande.owner_user_id";
    String column3 = ", COUNT (*) AS weight";
    try {
      assertNotNull(soq.getResults(soq.runQuerySprint2(yyyy, mm, dd, limit, groupby, column3), query));
    } catch (InterruptedException e) {
      System.err.println(e);
    }

    query = "6";
    String user = "1109";
    String order = "Ris";
    String where = "Domande";
    String nnull = "Risposte";
    try {
      assertNotNull(soq.getResults(soq.runQuerySprint2(user, limit, order, where, nnull, groupby, column3), query));
    } catch (InterruptedException e) {
      System.err.println(e);
    }
  }
}
