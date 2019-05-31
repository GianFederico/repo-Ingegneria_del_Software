package it.uniba.sotorrent;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 'ENTITY'
 * 
 *<p>Utility class for creating, sharing, and deleting Google spreadsheets.
 * 
 *<p>Classe di utility per la creazione, condivisione ed eliminazione dei Google spreadsheets.
 * 
 *<p>For more, refer to <a href = "https://developers.google.com/sheets/api/samples/">this documentation</a>.
 */
public class GoogleDocsUtils {
  /**
 * The app name.
 */
  private static final String APPLICATION_NAME = "sna4so";
  /**
 * Permissions to manage Google Drive.
 */
  private static final List<String> SCOPES = Arrays.asList(SheetsScopes.DRIVE);
  /**
 * The instance of the Google Spreadsheet service.
 */
  private Sheets sheetsService;
  /**
 * The instance of the Google Drive service.
 */
  private Drive driveService;
  /**
 * The object built from the JSON credential file.
 */
  private Credential credential;
  /**
 * The location where the SON credential file is stored on the Internet.
 */
  private static final String URL = "http://neo.di.uniba.it/credentials/project-hopcroft-dfhf4t.json";

  /**
* Default constructor, authenticates and instantiate services.
*/
  public GoogleDocsUtils() {
    try {
      credential = authorize();
      sheetsService = getSheetsService();
      driveService = getDriveService();
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  /**
 * Performs Google authentication process.
 * @return Credential object.
 * @throws IOException Generic I/O error
 * @throws GeneralSecurityException Failed authentication.
 * @throws URISyntaxException Malformed URI.
 */
  private Credential authorize() throws IOException, GeneralSecurityException, URISyntaxException {
    GoogleCredential authCred = GoogleCredential.fromStream(new URL(URL).openStream()).toBuilder()
        .setServiceAccountScopes(SCOPES).build();
    return authCred;
  }

  /**
 * Instantiates the the Google Sheets service.
 * @return Instance of the Google Sheets service.
 * @throws IOException Generic I/O error.
 * @throws GeneralSecurityException Failed authentication.
 * @throws URISyntaxException Malformed URI.
 */
  private Sheets getSheetsService() throws IOException, GeneralSecurityException,
      URISyntaxException {
    return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
        JacksonFactory.getDefaultInstance(), credential)
        .setApplicationName(APPLICATION_NAME).build();
  }

  /**
   * Instantiates the Google Drive service.
   * @return Instance of the Google Drive service.
   * @throws IOException Generic I/O error.
   * @throws GeneralSecurityException Failed authentication.
   * @throws URISyntaxException Malformed URI.
   */
  private Drive getDriveService() throws IOException, GeneralSecurityException, URISyntaxException {
    return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(),
        JacksonFactory.getDefaultInstance(), credential)
        .setApplicationName(APPLICATION_NAME).build();
  }

  /**
   * Creates a new sheet on every execution.
   * @param title Spreadsheet title.
   * @return The spreadsheet id.
   * @throws IOException Generic I/O error.
   */
  public String createSheet(final String title) throws IOException {
    Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties()
          .setTitle(title));
    spreadsheet = sheetsService.spreadsheets().create(spreadsheet).setFields("spreadsheetId")
        .execute();
    String spid = spreadsheet.getSpreadsheetId();
    System.out.println("Spreadsheet ID: " + spid);
    System.out.println("Spreadhsheet URL: https://docs.google.com/spreadsheets/d/" + spid);
    return spid;
  }

  /**
   * Returns the spreadsheet id by title.
   * @param spid The spreadsheet id.
   * @throws IOException Generic I/O error.
   */
  public void getSheetByTitle(final String spid) throws IOException {
    Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spid);
    Spreadsheet response = request.execute();
    System.out.println(response);
  }


  /**
   * Instantiates the write requests for the Google spreadsheet.
   * 
   *<p>Istanzia le richieste di scrittura per il Google spreadsheet.
   * 
   * @param values Values that have to be written.
   * @param spid Google spreadsheet id.
   * @param rowIndex  Index of the row in which write.
   * @throws IOException Generic I/O error.
   */
  public void writeRequest(final List<CellData> values, final String spid, final int rowIndex)
      throws IOException  {
    List<Request> requests = new ArrayList<>();

    requests.add(new Request().setUpdateCells(
        new UpdateCellsRequest().setStart(new GridCoordinate().setSheetId(0).setRowIndex(rowIndex)
            .setColumnIndex(0))
        .setRows(Arrays.asList(new RowData().setValues(values)))
        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
    BatchUpdateSpreadsheetRequest batchUpdateRequest =
        new BatchUpdateSpreadsheetRequest().setRequests(requests);
    sheetsService.spreadsheets().batchUpdate(spid, batchUpdateRequest).execute();
  }


  /**
   * Write results to the spreadsheet relying on the query used to obtain them.
   * 
   *<p>Scrive i risultati sullo spreadsheet in base a quale query è stata utilizzata per ottenerli.
   * 
   *<p>Also, see <a href = "https://developers.google.com/sheets/api/guides/values">here</a>.
   * @param spid The spreadsheet id.
   * @param query Index used to identify which query has been used.
   * @param res list of the results, with as many column as the resultant table from the query.
   * @throws IOException Generic I/O error.
   */
  public boolean writeSheet(final String spid, final List<Long[]> res, final String query) throws IOException {
    List<CellData> values = new ArrayList<>();
    String[] colonne = {"", "to", "weight"};
    int rowIndex = 0;
    boolean flag = false;
    /**
     * Assegnazione titoli alle colonne del Google spreadsheet in base al valore di query.
     */
    switch (query) {
      case "1": case "2":
        colonne[0] = "owner_user_id";
        values.add(new CellData().setUserEnteredValue(new ExtendedValue()
            .setStringValue(colonne[0])));
        break;
      case "3": case "4": case "5":
        colonne[0] = "from";
        values.add(new CellData().setUserEnteredValue(new ExtendedValue()
            .setStringValue(colonne[0])));
        values.add(new CellData().setUserEnteredValue(new ExtendedValue()
            .setStringValue(colonne[1])));
        break;
      case "6": case "7": case "8":
        colonne[0] = "from";
        values.add(new CellData().setUserEnteredValue(new ExtendedValue()
            .setStringValue(colonne[0])));
        values.add(new CellData().setUserEnteredValue(new ExtendedValue()
            .setStringValue(colonne[1])));
        values.add(new CellData().setUserEnteredValue(new ExtendedValue()
            .setStringValue(colonne[2])));
        break;
      default:
        break;
    }
    writeRequest(values, spid, rowIndex);

    if (null !=  res) {
      rowIndex = 1;
      /**
         * Assegnazione dei valori ottenuti dalla query ad ogni riga del Google spreadsheet
         * in base al valore di query.
         */
      switch (query) {
        case "1": case "2":
          for (int i = 0; i < res.size(); i++) {
            values = new ArrayList<>();
            Long[] userID = res.get(i);
            values.add(
                new CellData().setUserEnteredValue(new ExtendedValue().setStringValue(
                    String.valueOf(userID[0]))));
            writeRequest(values, spid, rowIndex);
            rowIndex++;
          }
          flag = true;
          break;
        case "3": case "4": case "5":
          for (int i = 0; i < res.size(); i++) {
            values = new ArrayList<>();
            Long[] valori = res.get(i);
            values.add(
                new CellData().setUserEnteredValue(new ExtendedValue().setStringValue(
                    String.valueOf(valori[0]))));
            values.add(
                new CellData().setUserEnteredValue(new ExtendedValue().setStringValue(
                    String.valueOf(valori[1]))));
            writeRequest(values, spid, rowIndex);
            rowIndex++;
          }
          flag = true;
          break;
        case "6": case "7": case "8":
          for (int i = 0; i < res.size(); i++) {
            values = new ArrayList<>();
            Long[] valori = res.get(i);
            values.add(
                new CellData().setUserEnteredValue(new ExtendedValue().setStringValue(
                    String.valueOf(valori[0]))));
            values.add(
                new CellData().setUserEnteredValue(new ExtendedValue().setStringValue(
                    String.valueOf(valori[1]))));
            values.add(
                new CellData().setUserEnteredValue(new ExtendedValue().setStringValue(
                    String.valueOf(valori[2]))));
            writeRequest(values, spid, rowIndex);
            rowIndex++;
          }
          flag = true;
          break;
        default:
          break;
      }
    }
    return flag;
  }

  /**
   * Makes the spreadsheet readable to anyone with the link.
   * @param spid The spreadsheet id.
   * @throws IOException Generic I/O error.
   * @throws GeneralSecurityException Failed authentication.
   * @throws URISyntaxException Malformed URI.
   */
  public void shareSheet(final String spid) throws IOException, GeneralSecurityException,
      URISyntaxException {
    JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
      public void onFailure(final GoogleJsonError e, final HttpHeaders responseHeaders)
          throws IOException {
        // Handle error
        System.err.println(e.getMessage());
      }

      public void onSuccess(final Permission permission, final HttpHeaders responseHeaders)
          throws IOException {
        System.out.println("Permission ID: " + permission.getId());
      }
    };
    BatchRequest batch = driveService.batch();
    Permission userPermission = new Permission().setType("anyone").setRole("reader");
    driveService.permissions().create(spid, userPermission).setFields("id").queue(batch, callback);
    batch.execute();
  }

  // Intentionally not used it. Use it to delete a sheet.
  /**
   * Deletes a spreadsheet.
   * @param spid The spreadsheet id.
   * @throws IOException Generic I/O error.
   */
  @SuppressWarnings("unused")
  private void deleteSheet(final String spid) throws IOException {
    driveService.files().delete(spid).execute();
  }

}
