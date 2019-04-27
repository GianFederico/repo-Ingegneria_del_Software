/**
 * 
 */
package it.uniba.sotorrent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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


public final class SOQuery implements ISOQuery {
	/**
	 * Instance of BigQuery service.
	 */
	private BigQuery bigquery;
	/**
	 * URL of credentials JSON file.
	 */
	private static final String url = "http://neo.di.uniba.it/credentials/project-hopcroft-dfhf4t.json";

	/**
	 * Default constructor, instantiates BigQuery API service.
	 * @throws FileNotFoundException The remote JSON file with credential is 404.
	 * @throws IOException Malformed JSON file.
	 */
	public SOQuery() throws FileNotFoundException, IOException {
		bigquery = BigQueryOptions.newBuilder().setProjectId("sna4so-238509")
				.setCredentials(ServiceAccountCredentials.fromStream(new URL(url).openStream())).build()
				.getService();
	}

	
	@Override
	public Job runQuery(String yyyy, String mm, String dd, String[] type, String limit) throws InterruptedException {
		// Use standard SQL syntax for queries.
		// See: https://cloud.google.com/bigquery/sql-reference/
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(
				
				      "(SELECT DISTINCT owner_user_id as User "
					+ "FROM `bigquery-public-data.stackoverflow.posts_questions`"
					+ "WHERE owner_user_id IS NOT null "
					+ "AND post_type_id=" +type[0]
					+ " AND extract(year from creation_date)=" +yyyy
					+ " AND extract(month from creation_date)=" +mm
					+ " AND extract(day from creation_date)=" +dd
					+ " ORDER BY User)"
					
			        + "UNION DISTINCT"
			        
					+ "(SELECT DISTINCT owner_user_id as User "
			        + "FROM `bigquery-public-data.stackoverflow.posts_answers`"
			        + "WHERE owner_user_id IS NOT null "
			        + "AND post_type_id=" +type[1]
			        + " AND extract(year from creation_date)=" +yyyy
			        + " AND extract(month from creation_date)=" +mm
			        + " AND extract(day from creation_date)=" +dd
			        + " ORDER BY User)"
			        + "ORDER BY User"
			        + "LIMIT " +limit)
				
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
	public Map<String, Double> getResults(final Job queryJob) throws JobException, InterruptedException {
		Map<String, Double> results = new HashMap<String, Double>();

		if (queryJob != null) {
			TableResult result = queryJob.getQueryResults();
			int i=0;
			// Print all pages of the results.
			for (FieldValueList row : result.iterateAll()) {
				i++;
				String d=Integer.toString(i);
				
				//String UserID=row.get("User").getStringValue();
				Double UserID = row.get("User").getDoubleValue();
				System.out.printf("#: %s User: %.0f%n", d, UserID);
				results.put(d, UserID);
			}
		}
		return results;

}
}
