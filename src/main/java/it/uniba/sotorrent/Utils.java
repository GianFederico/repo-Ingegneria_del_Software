package it.uniba.sotorrent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;


/**
 * 'noECB'
 * 
 *<p>Utility class that support the Controller class in its main functions.
 *It assigns values to the arguments and chooses the user's request.
 *Then returns the chosen request to Controller.
 *
 *<p>Classe di utility che supporta la classe Controller nelle sue funzioni principali.
 *Assegna i valori agli argomenti e sceglie la giusta richiesta dell'utente.
 *Quindi ritorna la richiesta scelta a Controller.
 */
public final class Utils {

  /**
 * Class constructor.
 */
  public Utils() {

  }

  private String spid = "";
  private String[] type = {"", ""};
  private String query = "";
  private boolean edge = false;
  private boolean weight = false;
  private String yyyy = "";
  private String mm = "";
  private String dd = "";
  private String tipo = "";
  private String taglike = "";
  private String limit = "";
  private String user = "";
  private String groupby = "";
  private String column3 = "";


  public String getGroupby() {
    return groupby;
  }


  public void setGroupby(final String var) {
    this.groupby = var;
  }


  public String getColumn3() {
    return column3;
  }


  public void setColumn3(final String var) {
    this.column3 = var;
  }


  public String getSpid() {
    return spid;
  }


  public void setSpid(final String var) {
    this.spid = var;
  }


  public String getQuery() {
    return query;
  }


  public void setQuery(final String var) {
    this.query = var;
  }


  public String[] getType() {
    return type;
  }


  public void setType(final String[] var) {
    this.type = var;
  }


  public boolean isEdge() {
    return edge;
  }


  public void setEdge(final boolean var) {
    this.edge = var;
  }


  public boolean isWeight() {
    return weight;
  }


  public void setWeight(final boolean var) {
    this.weight = var;
  }


  public String getYyyy() {
    return yyyy;
  }


  public void setYyyy(final String var) {
    this.yyyy = var;
  }


  public String getMm() {
    return mm;
  }


  public void setMm(final String var) {
    this.mm = var;
  }


  public String getDd() {
    return dd;
  }


  public void setDd(final String var) {
    this.dd = var;
  }


  public String getTipo() {
    return tipo;
  }


  public void setTipo(final String var) {
    this.tipo = var;
  }


  public String getTaglike() {
    return taglike;
  }


  public void setTaglike(final String var) {
    this.taglike = var;
  }


  public String getLimit() {
    return limit;
  }


  public void setLimit(final String var) {
    this.limit = var;
  }


  public String getUser() {
    return user;
  }


  public void setUser(final String var) {
    this.user = var;
  }


  /**
   * Main method of the class. It fulfills the responsibilities of the class
   *
   *<p>Metodo principale della classe. Assolve le responsabilità della classe
   *
   * @param args The command-line arguments.
   * @throws FileNotFoundException See stack trace for proper location.
   * @throws IOException  See stack trace for proper location.
   * @throws InterruptedException  See stack trace for proper location.
   * @throws GeneralSecurityException  See stack trace for proper location.
   * @throws URISyntaxException  See stack trace for proper location.
   */
  public void switching(final String[] args) throws  IOException,
                                       InterruptedException,
                                       GeneralSecurityException,
                                       URISyntaxException {

    for (int i = 0; i < args.length; i++) {
      switch ((args[i].split("="))[0]) {
        case "yyyy":
          this.yyyy = ((args[i].split("="))[1]);
          break;
        case "mm":
          this.mm = ((args[i].split("="))[1]);
          break;
        case "dd":
          this.dd = ((args[i].split("="))[1]);
          break;
        case "type":
          this.tipo = ((args[i].split("="))[1]);

          switch (tipo) {
            case "question": case "questions":
              this.type[0] = "1";
              this.type[1] = "1";
              break;
            case "answer": case "answers":
              this.type[0] = "2";
              this.type[1] = "2";
              break;
            case "post": case "posts":
              this.type[0] = "1";
              this.type[1] = "2";
              break;
            default:
              break;
          }
          break;
        case "edge":
          switch ((args[i].split("="))[1]) {
            case "yes":
              this.edge = (true);
              break;
            case "no":
              this.edge = (false);
              break;
            default:
              System.out.println("Errore! Campo 'edge' diverso da 'yes' o 'no'");
              System.exit(0);
              break;
          }
          break;
        case "weight":
          switch ((args[i].split("="))[1]) {
            case "yes":
              this.weight = (true);
              break;
            case "no":
              this.weight = (false);
              break;
            default:
              System.out.println("Errore! Campo 'weight' diverso da 'yes' o 'no'");
              System.exit(0);
              break;
          }
          break;
        case "limit":
          this.limit = ((args[i].split("="))[1]);
          break;
        case "taglike":
          this.taglike = ((args[i].split("="))[1]);
          break;
        case "user":
          this.user = ((args[i].split("="))[1]);
          break;
        default:
          /**
           * Error message in case of incorrect input
           * 
           * Messaggio di errore in caso di input errato
           */
          System.out.println("Formato non corretto! \n Inserire dati in formato:\n"
              + " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  "
              + "limit=___ \n"
              + " - yyyy=____  mm=__  type=(question(s), answer(s) or post(s))  taglike=____  "
              + "limit=___ \n"
              + " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s)) "
              + "edge=(yes or no)  limit=___ \n"
              + " - type=(question(s), answer(s) or post(s))  user=___  edge=(yes or no)  "
              + "limit=___ \n"
              + " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  "
              + "edge=(yes or no)  weight=(yes or no)  limit=___");
          System.exit(0);
          break;
      }
    }


  }

  public void sprint1(final GoogleDocsUtils ut) throws IOException,
                                                       InterruptedException,
                                                       GeneralSecurityException,
                                                       URISyntaxException {
    /**
     * Requests 1,2 and 3 of the Sprint 1
     * 
     * Richieste 1,2 e 3 dello Sprint 1
     */
      if (!this.edge && !this.weight && this.taglike.equals("")) {
        this.query = "1";
        switch (this.tipo) {
          case "question": case "questions":
            System.out.println("Visualizza la lista dei primi 100 id utente (User)"
                + " che hanno fatto almeno una domanda ");
            this.spid = ut.createSheet("Sprint 1 hopcroft - User Story 1");
            break;
          case "answer": case "answers":
            System.out.println("Visualizza la lista dei primi 100 id utente (User)"
                + " che hanno dato almeno una risposta ");
            this.spid = ut.createSheet("Sprint 1 hopcroft - User Story 2");
            break;
          case "post": case "posts":
            System.out.println("Visualizza la lista dei primi 100 id utente (User)"
                + " che hanno fatto almeno un post ");
            this.spid = ut.createSheet("Sprint 1 hopcroft - User Story 3");
            break;
          default:
            break;
        }
      }

      /**
       * Requests 4,5 and 6 of the Sprint 1
       * 
       * Richieste 4,5 e 6 dello Sprint 1
       */
      if (!this.edge && !this.weight && !(this.taglike.equals(""))) {
        this.query = "2";
        switch (this.tipo) {
          case "question": case "questions":
            System.out.println("Visualizzare la lista dei primi 100 id utente (User) "
                + "che hanno fatto almeno una domanda (Question) su un dato argomento (Tag) ");
            this.spid = ut.createSheet("Sprint 1 hopcroft - User Story 4");
            break;
          case "answer": case "answers":
            System.out.println("Visualizzare la lista dei primi 100 id utente (User) "
                + "che hanno dato almeno una risposta (Answer) su un dato argomento (Tag) ");
            this.spid = ut.createSheet("Sprint 1 hopcroft - User Story 5");
            break;
          case "post": case "posts":
            System.out.println("Visualizzare la lista dei primi 100 id utente (User) "
                + "che hanno fatto almeno un Post su un dato argomento (Tag)");
            this.spid = ut.createSheet("Sprint 1 hopcroft - User Story 6");
            break;
          default:
            break;
        }
      }

  }

  public void sprint2(final GoogleDocsUtils ut) throws IOException,
                                                       InterruptedException,
                                                       GeneralSecurityException,
                                                       URISyntaxException {
    /**
     * Requests 1,2 and 3 of the Sprint 2
     * 
     * Richieste 1,2 e 3 dello Sprint 2
     */
      if (this.edge && !this.weight) {
        switch (this.tipo) {
          case "question": case "questions":
            if (this.user.equals("")) {
              System.out.println("Visualizzare la lista delle prime 100 coppie (from, to) relative "
                  + "a domande (Question) poste in un dato anno, mese e giorno");
              this.query = "3";
              this.spid = ut.createSheet("Sprint 2 hopcroft - User Story 1");
            } else {
              System.out.println("Visualizzare la lista delle prime 100 coppie (from, to) relative "
                  + "a domande (Question) poste da un determinato utente.");
              this.query = "4";
              this.spid = ut.createSheet("Sprint 2 hopcroft - User Story 2");
            }
            break;
          case "answer": case "answers":
            System.out.println("Visualizzare la lista delle prime 100 coppie (from, to) relative "
                + "a risposte (Answer) date da un determinato utente.");
            this.query = "5";
            this.spid = ut.createSheet("Sprint 2 hopcroft - User Story 3");
            break;
          default:
            break;
        }
      }

      /**
     * Requests 4,5 and 6 of the Sprint 2
     * 
     * Richieste 4,5 e 6 dello Sprint 2
     */
      if (this.edge && this.weight) {
        groupby = " GROUP BY Risposte.owner_user_id, Domande.owner_user_id";
        column3 = ", COUNT (*) AS weight";
        switch (this.tipo) {
          case "question": case "questions":
            if (this.user.equals("")) {
              System.out.println("Visualizzare la lista delle prime 100 triple (from, to, weight) "
                  + "relative a domande (Question) poste "
                  + "in un dato anno, mese e giorno");
              this.query = "6";
              this.spid = ut.createSheet("Sprint 2 hopcroft - User Story 4");
            } else {
              System.out.println("Visualizzare la lista delle prime 100 triple (from, to, weight) "
                  + "relative a domande (Question) poste da un determinato utente");
              this.query = "7";
              this.spid = ut.createSheet("Sprint 2 hopcroft - User Story 5");
            }
            break;
          case "answer": case "answers":
            System.out.println("Visualizzare la lista delle prime 100 triple (from, to, weight) "
                + "relative a risposte (Answer) date "
                + "da un determinato utente");
            this.query = "8";
            this.spid = ut.createSheet("Sprint 2 hopcroft - User Story 6");
            break;
          default:
            break;
        }
      }
  }

}
