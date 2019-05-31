package it.uniba.controller.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.controller.Utils;
/**
 * 
 *
 */
public class UtilsTest {
  private static Utils ut = null;

  @BeforeAll
  static void setUp() {
    System.out.println("setUpAll");
    ut = new Utils();
  }

  @AfterAll
  static void tearDown() {
    System.out.println("tearDownAll");
    ut = null;
  }

  @Test
  @DisplayName ("Test getPtid()")
  public void getPtidTest() {
    assertNotNull(ut.getPtid());
  }

  @Test
  @DisplayName ("Test setPtid()")
  public void setPtidTest() {
    String[] var = {"1", "2"};
    ut.setPtid(var);
    assertArrayEquals(var, ut.getPtid());
  }

  @Test
  @DisplayName ("Test isEdge()")
  public void isEdgeTest() {
    assertNotNull(ut.isEdge());
  }

  @Test
  @DisplayName ("Test setEdge()")
  public void setEdgeTest() {
    boolean var = true;
    ut.setEdge(var);
    assertEquals(var, ut.isEdge());
  }

  @Test

  @DisplayName ("Test isWeight()")
  public void isWeightTest() {
    assertNotNull(ut.isWeight());
    //assertFalse(ut.isWeight());
  }

  @Test
  @DisplayName ("Test setWeight()")
  public void setWeightTest() {
    boolean var = true;
    ut.setWeight(var);
    assertEquals(var, ut.isWeight());
    assertTrue(ut.isWeight());
  }

  @Test
  @DisplayName ("Test getGroupby()")
  public void getGroupbyTest() {
    assertNotNull(ut.getGroupby());
  }

  @Test
  @DisplayName ("Test setGroupby()")
  public void setGroupbyTest() {
    String var = "Prova";
    ut.setGroupby(var);
    assertEquals(var, ut.getGroupby());
  }

  @Test
  @DisplayName ("Test getColumn3()")
  public void getColumn3Test() {
    assertNotNull(ut.getColumn3());
  }

  @Test
  @DisplayName ("Test setColumn3()")
  public void setColumn3Test() {
    String var = "Prova";
    ut.setColumn3(var);
    assertEquals(var, ut.getColumn3());
  }

  @Test
  @DisplayName ("Test getQuery()")
  public void getQueryTest() {
    assertNotNull(ut.getQuery());
  }

  @Test
  @DisplayName ("Test setQuery()")
  public void setQueryTest() {
    String var = "Prova";
    ut.setQuery(var);
    assertEquals(var, ut.getQuery());
  }

  @Test
  @DisplayName ("Test getYyyy()")
  public void getYyyyTest() {
    assertNotNull(ut.getYyyy());
  }

  @Test
  @DisplayName ("Test setYyyy()")
  public void setYyyyTest() {
    String var = "Prova";
    ut.setYyyy(var);
    assertEquals(var, ut.getYyyy());
  }

  @Test
  @DisplayName ("Test getMm()")
  public void getMmTest() {
    assertNotNull(ut.getMm());
  }

  @Test
  @DisplayName ("Test setMm()")
  public void setMmTest() {
    String var = "Prova";
    ut.setMm(var);
    assertEquals(var, ut.getMm());
  }

  @Test
  @DisplayName ("Test getDd()")
  public void getDdTest() {
    assertNotNull(ut.getDd());
  }

  @Test
  @DisplayName ("Test setDd()")
  public void setDdTest() {
    String var = "Prova";
    ut.setDd(var);
    assertEquals(var, ut.getDd());
  }

  @Test
  @DisplayName ("Test getTipo()")
  public void getTipoTest() {
    assertNotNull(ut.getTipo());
  }

  @Test
  @DisplayName ("Test setTipo()")
  public void setTipoTest() {
    String var = "Prova";
    ut.setTipo(var);
    assertEquals(var, ut.getTipo());
  }

  @Test
  @DisplayName ("Test getTaglike()")
  public void getTaglikeTest() {
    assertNotNull(ut.getTaglike());
  }

  @Test
  @DisplayName ("Test setTaglike()")
  public void setTaglikeTest() {
    String var = "Prova";
    ut.setTaglike(var);
    assertEquals(var, ut.getTaglike());
  }

  @Test
  @DisplayName ("Test getLimit()")
  public void getLimitTest() {
    assertNotNull(ut.getLimit());
  }

  @Test
  @DisplayName ("Test setLimit()")
  public void setLimitTest() {
    String var = "Prova";
    ut.setLimit(var);
    assertEquals(var, ut.getLimit());
  }

  @Test
  @DisplayName ("Test getUser()")
  public void getUserTest() {
    assertNotNull(ut.getUser());
  }

  @Test
  @DisplayName ("Test setUser()")
  public void setUserTest() {
    String var = "Prova";
    ut.setUser(var);
    assertEquals(var, ut.getUser());
  }

  @Test
  @DisplayName ("Test switching()")
  public void switchingTest() throws IOException, InterruptedException, GeneralSecurityException, URISyntaxException {
    assumeNotNull(ut);
    String[] inputDProva = {"yyyy=2016", "mm=02", "dd=11", "type=question", "edge=yes",
        "weight=no", "limit=100", "user=1109", "taglike=java"};
    int i = 0;
    assertFalse(ut.switching(inputDProva));
    assertEquals(inputDProva[i++].split("=")[1], ut.getYyyy());
    assertEquals(inputDProva[i++].split("=")[1], ut.getMm());
    assertEquals(inputDProva[i++].split("=")[1], ut.getDd());
    assertEquals(inputDProva[i++].split("=")[1], ut.getTipo());
    assertTrue(ut.isEdge());
    assertFalse(ut.isWeight());
    i += 2;
    assertEquals(inputDProva[i++].split("=")[1], ut.getLimit());
    assertEquals(inputDProva[i++].split("=")[1], ut.getUser());
    assertEquals(inputDProva[i++].split("=")[1], ut.getTaglike());
    }

  @Test
  @DisplayName ("Test sprint1()")
  public void testSprint1() throws IOException, InterruptedException, GeneralSecurityException, URISyntaxException {
    assertNotNull(ut.sprint1());

    ut.setEdge(false);
    ut.setWeight(false);
    ut.setTaglike("");

    //assertEquals("1", ut.getQuery());
    ut.setTipo("question");
    assertEquals("1", ut.sprint1()[0]);
    assertEquals("1", ut.sprint1()[1]);
    ut.setTipo("answer");
    assertEquals("1", ut.sprint1()[0]);
    assertEquals("2", ut.sprint1()[1]);
    ut.setTipo("post");
    assertEquals("1", ut.sprint1()[0]);
    assertEquals("3", ut.sprint1()[1]);

    ut.setTaglike("java");

    //assertEquals("2", ut.getQuery());
    ut.setTipo("question");
    assertEquals("1", ut.sprint1()[0]);
    assertEquals("4", ut.sprint1()[1]);
    ut.setTipo("answer");
    assertEquals("1", ut.sprint1()[0]);
    assertEquals("5", ut.sprint1()[1]);
    ut.setTipo("post");
    assertEquals("1", ut.sprint1()[0]);
    assertEquals("6", ut.sprint1()[1]);
  }

  @Test
  @DisplayName ("Test sprint2()")
  public void testSprint2() throws IOException, InterruptedException, GeneralSecurityException, URISyntaxException {
    assertNotNull(ut.sprint2());

    ut.setEdge(true);
    ut.setWeight(false);

    ut.setUser("");
    ut.setTipo("question");
    //assertEquals("3", ut.getQuery());
    assertEquals("2", ut.sprint2()[0]);
    assertEquals("1", ut.sprint2()[1]);
    ut.setUser("Prova");
    //assertEquals("4", ut.getQuery());
    assertEquals("2", ut.sprint2()[0]);
    assertEquals("2", ut.sprint2()[1]);
    ut.setTipo("answer");
    //assertEquals("5", ut.getQuery());
    assertEquals("2", ut.sprint2()[0]);
    assertEquals("3", ut.sprint2()[1]);

    ut.setWeight(true);

    //assertEquals(" GROUP BY Risposte.owner_user_id, Domande.owner_user_id", ut.getGroupby());
    //assertEquals(", COUNT (*) AS weight", ut.getColumn3());
    ut.setTipo("question");
    ut.setUser("");
    //assertEquals("6", ut.getQuery());
    assertEquals("2", ut.sprint2()[0]);
    assertEquals("4", ut.sprint2()[1]);
    ut.setUser("Prova");
    //assertEquals("7", ut.getQuery());
    assertEquals("2", ut.sprint2()[0]);
    assertEquals("5", ut.sprint2()[1]);
    ut.setTipo("answer");
    //assertEquals("8", ut.getQuery());
    assertEquals("2", ut.sprint2()[0]);
    assertEquals("6", ut.sprint2()[1]);
  }

}

  //public void test
