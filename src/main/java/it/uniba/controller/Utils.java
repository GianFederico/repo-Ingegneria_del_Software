package it.uniba.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * 'ENTITY'
 * 
 *<p>Class that support the Controller class in its main functions.
 *It assigns values to the arguments and chooses the user's request.
 *Then returns the chosen request to Controller.
 *
 *<p>Classe che supporta la classe Controller nelle sue funzioni principali.
 *Assegna i valori agli argomenti e sceglie la giusta richiesta dell'utente.
 *Quindi ritorna la richiesta scelta a Controller.
 */
public final class Utils {

  /**
 * Class constructor.
 */
  public Utils() {

  }

  private String[] ptid = {"", ""}; //Array containing the post type id used to filter the query results.
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

  public String[] getPtid() {
    String[] var;
    var = this.ptid;
    return var;
  }

  public void setPtid(final String[] var) {
    this.ptid[0] = var[0];
    this.ptid[1] = var[1];
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

  public String getGroupby() {
    return this.groupby;
  }

  public void setGroupby(final String var) {
    this.groupby = var;
  }

  public String getColumn3() {
    return this.column3;
  }

  public void setColumn3(final String var) {
    this.column3 = var;
  }

  public String getQuery() {
    return this.query;
  }

  public void setQuery(final String var) {
    this.query = var;
  }

  public String getYyyy() {
    return this.yyyy;
  }

  public void setYyyy(final String var) {
    this.yyyy = var;
  }

  public String getMm() {
    return this.mm;
  }

  public void setMm(final String var) {
    this.mm = var;
  }

  public String getDd() {
    return this.dd;
  }

  public void setDd(final String var) {
    this.dd = var;
  }

  public String getTipo() {
    return this.tipo;
  }

  public void setTipo(final String var) {
    this.tipo = var;
  }

  public String getTaglike() {
    return this.taglike;
  }

  public void setTaglike(final String var) {
    this.taglike = var;
  }

  public String getLimit() {
    return this.limit;
  }

  public void setLimit(final String var) {
    this.limit = var;
  }

  public String getUser() {
    return this.user;
  }

  public void setUser(final String var) {
    this.user = var;
  }

  /**
   * Method that sort values from the input from command-line and control their format.
   * If it finds an invalid format, it doesn't let the program to continue it's execution.
   * 
   *<p>Metodo che smista i valori ottenuti da command-line e ne controlla la validità nel formato.
   * Se il metodo trova un valore di formato non valido, non concede al resto del programma di proseguire.
   *
   * @param args The command-line arguments.
   * @throws IOException  See stack trace for proper location.
   * @throws InterruptedException  See stack trace for proper location.
   * @throws GeneralSecurityException  See stack trace for proper location.
   * @throws URISyntaxException  See stack trace for proper location.
   * @return exit If the method finds a invalid input, doesn't let the program to continue.
   */
  public boolean switching(final String[] args) throws  IOException,
                                       InterruptedException,
                                       GeneralSecurityException,
                                       URISyntaxException {
    boolean exit = false;
    final int soyear = 2008;
    final int soday = 15;
    final int lastMM = 12;
    final int lastDD = 31;
    Calendar soLaunch = new GregorianCalendar(soyear, Calendar.SEPTEMBER, soday);
    Calendar today = Calendar.getInstance();

    for (int i = 0; i < args.length; i++) {
      switch ((args[i].split("="))[0]) {
        case "yyyy":
          this.yyyy = ((args[i].split("="))[1]);
          if (Integer.parseInt(this.yyyy) < soyear
              || Integer.parseInt(this.yyyy) > today.get(Calendar.YEAR)) {
            System.out.println("Anno fuori dal range");
            exit = true;
          }
          break;
        case "mm":
          this.mm = ((args[i].split("="))[1]);
          if (Integer.parseInt(this.mm) < 1 || Integer.parseInt(this.mm) > lastMM) {
            System.out.println("Mese fuori dal range");
            exit = true;
          }
          break;
        case "dd":
          this.dd = ((args[i].split("="))[1]);
          if (Integer.parseInt(this.dd) < 1 || Integer.parseInt(this.dd) > lastDD) {
            System.out.println("Giorno fuori dal range");
            exit = true;
          }
          break;
        case "type":
          this.tipo = ((args[i].split("="))[1]);
          switch (tipo) {
            case "question": case "questions":
              this.ptid[0] = "1";
              this.ptid[1] = "1";
              break;
            case "answer": case "answers":
              this.ptid[0] = "2";
              this.ptid[1] = "2";
              break;
            case "post": case "posts":
              this.ptid[0] = "1";
              this.ptid[1] = "2";
              break;
            default:
              System.out.println("Type non definito");
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
              exit = true;
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
              exit = true;
              break;
          }
          break;
        case "limit":
          this.limit = ((args[i].split("="))[1]);
          if (Integer.parseInt(this.limit) < 0) {
            System.out.println("Limit non valido");
            exit = true;
          }
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
              + "weight=(yes or no) limit=___ \n"
              + " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  "
              + "edge=(yes or no)  weight=(yes or no)  limit=___");
          exit = true;
          break;
      }
    }
    if (!dd.equals("")) {
     DateValidator dtv = new DateValidator();
     if (dtv.isThisDateValid(this.yyyy, this.mm, this.dd)) {
         Calendar chDate = new GregorianCalendar(Integer.parseInt(this.yyyy),
           Integer.parseInt(this.mm), Integer.parseInt(this.dd));
         if (chDate.before(soLaunch) || chDate.after(today)) {
           System.out.println("Data fuori dal range");
           exit = true;
         }
     } else {
       System.out.println("Formato data non valido");
       exit = true;
     }
    } else {
      if (Integer.parseInt(this.yyyy) == soyear && Integer.parseInt(this.mm)
          < soLaunch.get(Calendar.MONTH)) {
        System.out.println("Data fuori dal range");
        exit = true;
      }
    }
    return exit;
  }

  /**
   *<p>Method that choose which Sprint 1 request to fulfill based on the data from command-line.
   *
   *<p>Metodo che sceglie la richiesta dello Sprint 1 da assolvere basandosi sui dati ottenuti da command-line.
   * 
   * @throws IOException  See stack trace for proper location.
   * @throws InterruptedException  See stack trace for proper location.
   * @throws GeneralSecurityException  See stack trace for proper location.
   * @throws URISyntaxException  See stack trace for proper location.
   * @return request  Contains which sprint and which user story the user choose.
   */
  public String[] sprint1() throws IOException,
                                   InterruptedException,
                                   GeneralSecurityException,
                                   URISyntaxException {
    String[] request = {"", ""};
    /**
     * Requests 1,2 and 3 of the Sprint 1
     * 
     * Richieste 1,2 e 3 dello Sprint 1
     */
      if (!this.edge && !this.weight && this.taglike.equals("")) {
        this.query = "1";
        switch (this.tipo) {
          case "question": case "questions":
            System.out.println("Visualizza la lista dei primi " + this.limit + " id utente (User)"
                + " che hanno fatto almeno una domanda ");
            request[0] = "1";
            request[1] = "1";
            break;
          case "answer": case "answers":
            System.out.println("Visualizza la lista dei primi " + this.limit + " id utente (User)"
                + " che hanno dato almeno una risposta ");
            request[0] = "1";
            request[1] = "2";
            break;
          case "post": case "posts":
            System.out.println("Visualizza la lista dei primi " + this.limit + " id utente (User)"
                + " che hanno fatto almeno un post ");
            request[0] = "1";
            request[1] = "3";
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
            System.out.println("Visualizzare la lista dei primi " + this.limit + " id utente (User) "
                + "che hanno fatto almeno una domanda (Question) su un dato argomento (Tag) ");
            request[0] = "1";
            request[1] = "4";
            break;
          case "answer": case "answers":
            System.out.println("Visualizzare la lista dei primi " + this.limit + " id utente (User) "
                + "che hanno dato almeno una risposta (Answer) su un dato argomento (Tag) ");
            request[0] = "1";
            request[1] = "5";
            break;
          case "post": case "posts":
            System.out.println("Visualizzare la lista dei primi " + this.limit + " id utente (User) "
                + "che hanno fatto almeno un Post su un dato argomento (Tag)");
            request[0] = "1";
            request[1] = "6";
            break;
          default:
            break;
        }
      }
      return request;
  }


  /**
   *<p>Method that choose which Sprint 2 request to fulfill based on the data from command-line.
   *
   *<p>Metodo che sceglie la richiesta dello Sprint 2 da assolvere basandosi sui dati ottenuti da command-line.
   * 
   * @throws IOException  See stack trace for proper location.
   * @throws InterruptedException  See stack trace for proper location.
   * @throws GeneralSecurityException  See stack trace for proper location.
   * @throws URISyntaxException  See stack trace for proper location.
   * @return request  Contains which sprint and which user story the user choose.
   */
  public String[] sprint2() throws IOException,
                                   InterruptedException,
                                   GeneralSecurityException,
                                   URISyntaxException {
    String[] request = {"", ""};
    /**
     * Requests 1,2 and 3 of the Sprint 2
     * 
     * Richieste 1,2 e 3 dello Sprint 2
     */
      if (this.edge && !this.weight) {
        switch (this.tipo) {
          case "question": case "questions":
            if (this.user.equals("")) {
              System.out.println("Visualizzare la lista delle prime " + this.limit + " coppie (from, to) relative "
                  + "a domande (Question) poste in un dato anno, mese e giorno");
              this.query = "3";
              request[0] = "2";
              request[1] = "1";
            } else {
              System.out.println("Visualizzare la lista delle prime " + this.limit + " coppie (from, to) relative "
                  + "a domande (Question) poste da un determinato utente.");
              this.query = "4";
              request[0] = "2";
              request[1] = "2";
            }
            break;
          case "answer": case "answers":
            System.out.println("Visualizzare la lista delle prime " + this.limit + " coppie (from, to) relative "
                + "a risposte (Answer) date da un determinato utente.");
            this.query = "5";
            request[0] = "2";
            request[1] = "3";
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
        this.groupby = " GROUP BY Risposte.owner_user_id, Domande.owner_user_id";
        this.column3 = ", COUNT (*) AS weight";
        switch (this.tipo) {
          case "question": case "questions":
            if (this.user.equals("")) {
              System.out.println("Visualizzare la lista delle prime " + this.limit + " triple (from, to, weight) "
                  + "relative a domande (Question) poste "
                  + "in un dato anno, mese e giorno");
              this.query = "6";
              request[0] = "2";
              request[1] = "4";
            } else {
              System.out.println("Visualizzare la lista delle prime " + this.limit + " triple (from, to, weight) "
                  + "relative a domande (Question) poste da un determinato utente");
              this.query = "7";
              request[0] = "2";
              request[1] = "5";
            }
            break;
          case "answer": case "answers":
            System.out.println("Visualizzare la lista delle prime " + this.limit + " triple (from, to, weight) "
                + "relative a risposte (Answer) date "
                + "da un determinato utente");
            this.query = "8";
            request[0] = "2";
            request[1] = "6";
            break;
          default:
            break;
        }
      }
      return request;
  }

}
