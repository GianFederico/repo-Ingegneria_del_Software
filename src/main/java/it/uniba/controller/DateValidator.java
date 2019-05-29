package it.uniba.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 'ENTITY'
 * 
 *<p> This class is used to validate the date obtained via the user input from command-line.
 * 
 *<p> Questa classe è utilizzata per la validazione della data ottenuta dall'input dell'utente da command-line.
 *
 */
public final class DateValidator {

  public DateValidator() {

  }

  /**
   * Main method of the class. It fulfills the responsibilities of the class
   * 
   * <p>Metodo principale della classe. Assolve le responsabilità della classe
   * 
   * @param yyyy year of the date to be validated
   * @param mm month of the date to be validated
   * @param dd month of the date to be validated
   * @return valid
   */
  public boolean isThisDateValid(final String yyyy, final String mm, final String dd) {

    String dateToValidate = dd + "/" + mm + "/" + yyyy;
    String dateFromat = "dd/MM/yyyy";
    boolean valid = false;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
    sdf.setLenient(false);
    try {
      sdf.parse(dateToValidate); //if not valid, it will throw ParseException
      valid = true;
    } catch (ParseException e) {
      valid = false;
    }
    return valid;
  }
}

