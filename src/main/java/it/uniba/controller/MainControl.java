package it.uniba.controller;

import it.uniba.sotorrent.GoogleDocsUtils;
import it.uniba.sotorrent.IsoQuery;
import it.uniba.sotorrent.SoQuery;

import com.google.cloud.bigquery.Job;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;


/**
 * 'CONTROL'
 *
 *<p>Class that sorts and interprets the obtained data in order to
 * execute the method able to retrieve the correct results
 * based on the user's choice.
 *
 *<p>Classe che smista e interpreta i dati ottenuti in modo
 * da eseguire il metodo in grado di restituire i risultati corretti
 * in base alla scelta dell'utente
 *
 */

public final class MainControl {

  /**
 * Class constructor.
 */
  public MainControl() {

  }

  /**
 * Main method of the class. It fulfills the responsibilities of the class
 *
 *<p>Metodo principale della classe. Assolve le responsabilità della classe
 *
 * @param args The command-line arguments.
 * @throws FileNotFoundException See stack trace for proper location.
 * @throws IOException See stack trace for proper location.
 * @throws InterruptedException  See stack trace for proper location.
 * @throws GeneralSecurityException  See stack trace for proper location.
 * @throws URISyntaxException  See stack trace for proper location.
 * @return true if everything went fine, false otherwise.
 */
  public boolean control(final String[] args) throws FileNotFoundException,
                                            IOException,
                                            InterruptedException,
                                            GeneralSecurityException,
                                            URISyntaxException {
    GoogleDocsUtils gdut = new GoogleDocsUtils();
    Utils ut = new Utils();
    final int numminargs = 4;
    boolean exit = false;
    String spid = "";
    String[] request;
    boolean flag = false;
    /**
 * Sorting of the data obtained from command-line
 *
 * Smistamento dei dati ottenuti da command-line
 */
    if (args.length >= numminargs) {
      exit = ut.switching(args);
    } else {
      /**
    * Error message in case of empty input
    * 
    * Messaggio di errore nel caso di input vuoto
    */
      System.out.println("Formato non corretto! \n Inserire dati in formato:\n"
          + " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  limit=___ \n"
          + " - yyyy=____  mm=__  type=(question(s), answer(s) or post(s))  taglike=____ "
          + " limit=___ \n - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))"
          + " edge=(yes or no)  limit=___ \n"
          + " - type=(question(s), answer(s) or post(s))  user=___  edge=(yes or no) weight=(yes or no) limit=___ \n"
          + " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))"
          + " edge=(yes or no)  weight=(yes or no)  limit=___");
      exit = true;
    }

    /**
   * Messaggio di errore nel caso di input non corretto
   */
    if (!ut.isEdge() && ut.isWeight()) {
      System.out.println("Se il valore di 'weight' e' 'yes', il valore "
          + "di edge deve essere necessariamente 'yes'!");
      exit = true;
    }


   if (!exit) {
      //start Sprint 1
      request = ut.sprint1();
      //end Sprint 1

      //start Sprint 2
      request = ut.sprint2();
      //end Sprint 2


      IsoQuery soq = new SoQuery();
      Job job = null;

       /**
       * Choice of the query to be executed based on the values of the 'query' variable
       * 
       * Scelta della query da eseguire in base ai valori della variabile 'query'
       * 
       * 'query' values:    1-->User Story 1,2 e 3, Sprint 1
       *                    2-->User Story 4,5 e 6, Sprint 1
       *                    3-->User Story 1, Sprint 2
       *                    4-->User Story 2, Sprint 2
       *                    5-->User Story 3, Sprint 2
       *                    6-->User Story 4, Sprint 2
       *                    7-->User Story 5, Sprint 2
       *                    8-->User Story 6, Sprint 2
       */
      switch (ut.getQuery()) {
        case "1":
          job = soq.runQuerySprint1(ut.getYyyy(), ut.getMm(), ut.getDd(), ut.getPtid(), ut.getLimit());
          break;
        case "2":
          job = soq.runQuerySprint1(ut.getYyyy(), ut.getMm(), ut.getPtid(), ut.getTaglike(), ut.getLimit());
          break;
        case "3": case "6":
          job = soq.runQuerySprint2(ut.getYyyy(), ut.getMm(), ut.getDd(),
              ut.getLimit(), ut.getGroupby(), ut.getColumn3());
          break;
        case "4": case "7":
          job = soq.runQuerySprint2(ut.getUser(), ut.getLimit(), "Ris", "Domande", "Risposte",
              ut.getGroupby(), ut.getColumn3());
          break;
        case "5": case "8":
          job = soq.runQuerySprint2(ut.getUser(), ut.getLimit(), "Dom", "Risposte", "Domande",
              ut.getGroupby(), ut.getColumn3());
          break;
        default:
          break;
      }

      //Filling the list with the values obtained from the query.
      List<Long[]> res = soq.getResults(job, ut.getQuery());
      if (res.size() > 0) {
         //if the list is actually not empty, create a Google Sheet with the given title.
         spid = gdut.createSheet("Sprint " + request[0] + " hopcroft - User Story " + request[1]);
         //Makes available the access to the spreadsheet to anyone with the link.
         gdut.shareSheet(spid);
         gdut.getSheetByTitle(spid);
         //Writes the results contained in the 'res' list on the Google spreadsheet.
         flag = gdut.writeSheet(spid, res, ut.getQuery());
      }
   }
   return !(exit || !flag);
  }


}
