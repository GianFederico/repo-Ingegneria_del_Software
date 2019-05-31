package it.uniba.main;

import it.uniba.controller.MainControl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

/**
 * 'BOUNDARY'
 * 
 *<p>Main class of the project.
 * It obtains data from command-line and
 * passes them to the control class.
 * 
 *<p>La classe principale del progetto.
 * Acquisisce i dati da command-line e
 * li passa alla classe di controllo
 * 
 *<b>DO NOT RENAME</b>
 */

public final class AppMain {

  /**
   * Private constructor. Change if needed.
   */
  private AppMain() {

  }

  /**
   *  This is the main entry of the application.
   *
   * @param args The command-line arguments.
   * @throws FileNotFoundException See stack trace for proper location.
   * @throws IOException  See stack trace for proper location.
   * @throws InterruptedException  See stack trace for proper location.
   * @throws GeneralSecurityException  See stack trace for proper location.
   * @throws URISyntaxException  See stack trace for proper location.
   */
  public static void main(final String[] args) throws FileNotFoundException,
      IOException,
      InterruptedException,
      GeneralSecurityException,
      URISyntaxException {

    boolean flag = false;
    String[] prova = {"yyyy=2016", "mm=02", "dd=11", "type=question", "limit=10"};
    System.out.println("Current working dir: " + System.getProperty("user.dir"));
    MainControl ct = new MainControl();
    flag = ct.control(prova);
    if (!flag) {
      System.out.println("Errore! La tua richiesta non ha prodotto alcun risultato");
    }
  }
}
