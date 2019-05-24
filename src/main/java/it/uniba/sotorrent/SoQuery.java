package it.uniba.sotorrent;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



/**
  * 'ENTITY'
  * 
  * <p>Class which defines functions that set and run queries, using Google BigQuery service.
  * Is also defined the function able to fill a list with query results.
  * 
  * <p>Classe che definisce le funzioni in grado di impostare le query,
  * interfacciandosi con il servizio BigQuery di Google.
  * Inoltre e' definita la funzione in grado di riempire la lista coi risultati delle query.
  * 
  */

public final class SoQuery implements IsoQuery {
  /**
 * Instance of BigQuery service.
 */
  private BigQuery bigquery;
  /**
 * URL of credentials JSON file.
 */
  private static final String URL = "http://neo.di.uniba.it/credentials/project-hopcroft-dfhf4t.json";

  /**
 * Default constructor, instantiates BigQuery API service.
 * @throws FileNotFoundException The remote JSON file with credential is 404.
 * @throws IOException Malformed JSON file.
 */
  public SoQuery() throws FileNotFoundException, IOException {
    bigquery = BigQueryOptions.newBuilder().setProjectId("sna4so-238509")
.setCredentials(ServiceAccountCredentials.fromStream(new URL(URL).openStream())).build()
.getService();
  }

  @Override
public Job runQuerySprint1(final String yyyy, final String mm, final String dd,
      final String[] type, final String limit) throws InterruptedException {
    // Use standard SQL syntax for queries.
    // See: https://cloud.google.com/bigquery/sql-reference/

    QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(
        /**
         * La query seleziona univoci e non nulli user id dai dati di Stack Overflow
         * filtrandoli in base ad anno, mese e giorno
 * ed ordinandoli in modo crescente.
 * 
 * Se type[0]=1 && type[1]=1, la query prendera'� i risultati solamente dai post classificati
 * come domande perche' la seconda parte della query verra'� ignorata
 * in quanto post_type_id=1 e' incompatibile con
 * la fonte `bigquery-public-data.stackoverflow.posts_questions`.
 * 
 * Se type[0]=2 && type[1]=2, la query prenderà i risultati solamente dai post classificati
 * come risposte perchè la prima parte della query verrà ignorata
 * in quanto post_type_id=2 è incompatibile con
 * la fonte `bigquery-public-data.stackoverflow.posts_answers`.
 * 
 * Se type[0]=1 && type[1]=2, la query prenderà i risultati sia dalle domande che dalle risposte.
 */
        "(SELECT DISTINCT owner_user_id as User "
        + "FROM `bigquery-public-data.stackoverflow.posts_questions`"
        + "WHERE owner_user_id IS NOT null "
        + "AND post_type_id=" + type[0]
        + " AND extract(year from creation_date)=" + yyyy
        + " AND extract(month from creation_date)=" + mm
        + " AND extract(day from creation_date)=" + dd
        + " ORDER BY User)"

        + " UNION DISTINCT"

        + "(SELECT DISTINCT owner_user_id as User "
        + "FROM `bigquery-public-data.stackoverflow.posts_answers`"
        + "WHERE owner_user_id IS NOT null "
        + "AND post_type_id=" + type[1]
        + " AND extract(year from creation_date)=" + yyyy
        + " AND extract(month from creation_date)=" + mm
        + " AND extract(day from creation_date)=" + dd
        + " ORDER BY User)"
        + " ORDER BY User "
        + "LIMIT " + limit)

        .setUseLegacySql(false).build();

    // Create a job ID so that we can safely retry.
    JobId jobId = JobId.of(UUID.randomUUID().toString());
    Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

    // Wait for the query to complete.
    queryJob = queryJob.waitFor();

    // Check for errors
    if (queryJob == null) {
      throw new RuntimeException("Job no longer exists");
    } else if (queryJob.getStatus().getError() != null) {
      // You can also look at queryJob.getStatus().getExecutionErrors() for all
      // errors, not just the latest one.
      throw new RuntimeException(queryJob.getStatus().getError().toString());
    }
    return queryJob;
  }

  @Override
  public Job runQuerySprint1(final String yyyy, final String mm, final String[] type,
      final String taglike, final String limit) throws InterruptedException {

    QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(
         /**
 * La query seleziona univoci e non nulli user id dai dati di Stack Overflow
 * filtrandoli in base ad anno, mese e tag
 * ed ordinandoli in modo crescente.
 * 
 * Se type[0]=1 && type[1]=1, la query prenderà i risultati solamente dai post classificati come
 * domande perchè la seconda parte della query verrà ignorata
 * in quanto post_type_id=1 è incompatibile con
 * la fonte `bigquery-public-data.stackoverflow.posts_questions`.
 * 
 * Se type[0]=2 && type[1]=2, la query prenderà i risultati solamente dai post classificati come
 * risposte perchè la prima parte della query verrà ignorata
 * in quanto post_type_id=2 è incompatibile con
 * la fonte `bigquery-public-data.stackoverflow.posts_answers`.
 * 
 * Se type[0]=1 && type[1]=2, la query prenderà i risultati sia dalle domande che dalle risposte.
 * 
 * Nello specifico la seconda parte della query dovrà far riferimento ad
 * entrambe le fonti di domande e risposte in quanto il tag è applicabile solo alle domande
 * e dunque verranno visualizzate tutte le risposte alle domande contenenti il determinato tag.
 */
         "(SELECT DISTINCT owner_user_id as User "
         + "FROM `bigquery-public-data.stackoverflow.posts_questions` "
         + "WHERE owner_user_id IS NOT null"
         + " AND post_type_id=" + type[0]
         + " AND extract(year from creation_date)=" + yyyy
         + " AND extract(month from creation_date)=" + mm
         + " AND Tags like '%" + taglike + "%'"
         + " ORDER BY owner_user_id ASC)"

         + " UNION DISTINCT "

         + "( SELECT DISTINCT Risposte.owner_user_id as User "
         + "FROM `bigquery-public-data.stackoverflow.posts_answers` as Risposte "
         + "INNER JOIN `bigquery-public-data.stackoverflow.posts_questions` as Domande "
         + "ON Domande.id = Risposte.parent_id "
         + "WHERE Risposte.owner_user_id IS NOT null "
         + "AND Risposte.post_type_id=" + type[1]
         + " AND extract(year from Risposte.creation_date)=" + yyyy
         + " AND extract(month from Risposte.creation_date)=" + mm
         + " AND Domande.Tags LIKE '%" + taglike + "%' "
         + "ORDER BY Risposte.owner_user_id ASC)"
         + " ORDER BY User "
         + "LIMIT " + limit)

         .setUseLegacySql(false).build();

    // Create a job ID so that we can safely retry.
    JobId jobId = JobId.of(UUID.randomUUID().toString());
    Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

    // Wait for the query to complete.
    queryJob = queryJob.waitFor();

    // Check for errors
    if (queryJob == null) {
      throw new RuntimeException("Job no longer exists");
    } else if (queryJob.getStatus().getError() != null) {
      // You can also look at queryJob.getStatus().getExecutionErrors() for all
      // errors, not just the latest one.
      throw new RuntimeException(queryJob.getStatus().getError().toString());
    }
    return queryJob;
  }

  @Override
  public Job runQuerySprint2(final String yyyy, final String mm, final String dd,
      final String limit, final String groupby, final String column3)
          throws InterruptedException {

    QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(
        /**
         * La query seleziona coppie non nulle di user id relativi agli utenti
         * che hanno creato un post di domanda e chi vi ha risposto
         * I dati sono estrapolati da quelli di Stack Overflow e filtrati
         * in base ad anno, mese e giorno
         * ed ordinati in modo crescente.
         * 
         * Nel caso della quarta richiesta dello Sprint 2 verra'�
         * visualizzato anche il numero di risposte che un utente ha dato al domandante.
         */
        "SELECT Risposte.owner_user_id as Ris, Domande.owner_user_id as Dom"  + column3
        + " FROM `bigquery-public-data.stackoverflow.posts_questions` as Domande "
        + "INNER JOIN `bigquery-public-data.stackoverflow.posts_answers` "
        + "as Risposte ON Domande.id = Risposte.parent_id "
        + "WHERE Risposte.owner_user_id is NOT NULL "
        + "AND Domande.owner_user_id is NOT NULL "
        + " AND extract(year from Domande.creation_date)=" + yyyy
        + " AND extract(month from Domande.creation_date)=" + mm
        + " AND extract(day from Domande.creation_date)=" + dd
        + groupby
        + " ORDER BY Ris, Dom"
        + " LIMIT " + limit)

        .setUseLegacySql(false).build();

    // Create a job ID so that we can safely retry.
    JobId jobId = JobId.of(UUID.randomUUID().toString());
    Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

    // Wait for the query to complete.
    queryJob = queryJob.waitFor();

    // Check for errors
    if (queryJob == null) {
      throw new RuntimeException("Job no longer exists");
    } else if (queryJob.getStatus().getError() != null) {
      // You can also look at queryJob.getStatus().getExecutionErrors() for all
      // errors, not just the latest one.
      throw new RuntimeException(queryJob.getStatus().getError().toString());
    }
    return queryJob;
  }

  @Override
  public Job runQuerySprint2(final String user, final String limit,
      final String order, final String where, final String nnull, final String groupby,
      final String column3) throws InterruptedException {
    QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(
        /**
         * La query seleziona coppie non nulle di user id relativi agli utenti che
         * hanno creato un post di domanda e chi vi ha risposto.
         * I dati sono estrapolati da quelli di Stack Overflow,
         * filtrati sulla base di uno specifico utente ed ordinati in modo crescente.
         * 
         * Nel caso della seconda e quinta richiesta dello Sprint 2
         * l'utente specifico verra'� selezionato tra i domandanti e
         * verranno visualizzati tutti coloro che hanno risposto ai suoi post.
         * 
         * Nel caso della terza e sesta richiesta dello Sprint 2
         * l'utente specifico verra'� selezionato tra coloro che hanno risposto e
         * verranno visualizzati tutti coloro ai quali questo utente
         * ha risposto ad almeno una domanda.
         * 
         * In aggiunta, nel caso della quinta e sesta richiesta verra'
         * visualizzato anche il numero di risposte che un utente ha dato al domandante.
         */
        "SELECT distinct Risposte.owner_user_id as Ris, Domande.owner_user_id as Dom " + column3
        + " FROM `bigquery-public-data.stackoverflow.posts_questions`"
        + " as Domande "
        + "INNER JOIN `bigquery-public-data.stackoverflow.posts_answers`"
        + " as Risposte ON Domande.id = Risposte.parent_id "
        + "WHERE " + where + ".owner_user_id = " + user
        + " AND " + nnull + ".owner_user_id is NOT NULL "
        + groupby
        + " ORDER BY " + order
        + " LIMIT " + limit)

        .setUseLegacySql(false).build();

    // Create a job ID so that we can safely retry.
    JobId jobId = JobId.of(UUID.randomUUID().toString());
    Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

    // Wait for the query to complete.
    queryJob = queryJob.waitFor();

    // Check for errors
    if (queryJob == null) {
      throw new RuntimeException("Job no longer exists");
    } else if (queryJob.getStatus().getError() != null) {
      // You can also look at queryJob.getStatus().getExecutionErrors() for all
      // errors, not just the latest one.
      throw new RuntimeException(queryJob.getStatus().getError().toString());
    }
    return queryJob;
  }

  /**
   * Returns the results from the query job.
   * 
   * <p>Restituisce i valori dei risultati ottenuti tramite la query.
   * 
   * @param queryJob The job associated to the query.
   * @param query Index used to identify which query has been used.
   * @return Results as a list of arrays.
   * @throws JobException Generic error occurred.
   * @throws InterruptedException Raised on timeouts.
   */
  public List<Long[]> getResults(final Job queryJob, final String query)
      throws JobException, InterruptedException {

    List<Long[]> results = new ArrayList<>();
    if (queryJob != null) {
      int d = 0;
      TableResult result = queryJob.getQueryResults();
      switch (query) {

        case "1": case "2":

          for (FieldValueList row : result.getValues()) {
            d++;
            Long[] userID = {row.get("User").getLongValue()};
            System.out.println("#" + d + " User: " + userID[0]);
            results.add(userID);
          }
          break;

        case "3": case "4": case "5":
          for (FieldValueList row : result.getValues()) {

            d++;
            Long[] valori = {row.get("Ris").getLongValue(), row.get("Dom").getLongValue()};
            System.out.println("#" + d + " from:" + valori[0] + " to:" + valori[1]);
            results.add(valori);
          }
          break;

        case "6": case "7": case "8":

          for (FieldValueList row : result.getValues()) {
            d++;
            Long[] valori = {row.get("Ris").getLongValue(), row.get("Dom").getLongValue(),
                  row.get("weight").getLongValue()};
            System.out.println("#" + d + " from:" + valori[0] + " to:" + valori[1]
                  + " weight:" + valori[2]);
            results.add(valori);
          }
          break;
        default:
          break;
      }
    }

    return results;
  }
}
