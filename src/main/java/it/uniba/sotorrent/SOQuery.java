/**
 * 
 */
/**
 * 
 */
package it.uniba.sotorrent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
import java.util.UUID;
import java.util.List;

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
	public Job runQuerySprint1(String yyyy, String mm, String dd, String[] type, String limit) throws InterruptedException {
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
					
			        + " UNION DISTINCT"
			        
					+ "(SELECT DISTINCT owner_user_id as User "
			        + "FROM `bigquery-public-data.stackoverflow.posts_answers`"
			        + "WHERE owner_user_id IS NOT null "
			        + "AND post_type_id=" +type[1]
			        + " AND extract(year from creation_date)=" +yyyy
			        + " AND extract(month from creation_date)=" +mm
			        + " AND extract(day from creation_date)=" +dd
			        + " ORDER BY User)"
			        + " ORDER BY User "
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
	public Job runQuerySprint1(String yyyy, String mm, String[] type, String taglike, String limit) throws InterruptedException {
		
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(
				 "(SELECT DISTINCT owner_user_id as User "							//Select Distinct +colonna1=((query==1)? "owner_user_id as User" : "Risposte.owner_user_id as User"
				+ "FROM `bigquery-public-data.stackoverflow.posts_questions` "
				+ "WHERE owner_user_id IS NOT null"
				+ " AND post_type_id="+type[0]
				+ " AND extract(year from creation_date)="+yyyy
				+ " AND extract(month from creation_date)="+mm
		        + " AND Tags like '%"+taglike+"%'"
				+ " ORDER BY owner_user_id ASC)"
				
				+ " UNION DISTINCT "
				
				+ "( SELECT DISTINCT Risposte.owner_user_id as User "
			    + "FROM `bigquery-public-data.stackoverflow.posts_answers` as Risposte "
                + "INNER JOIN `bigquery-public-data.stackoverflow.posts_questions` as Domande "
                + "ON Domande.id = Risposte.parent_id "
				+ "WHERE Risposte.owner_user_id IS NOT null "
				+ "AND Risposte.post_type_id="+type[1]+" AND extract(year from Risposte.creation_date)="+yyyy
				+ " AND extract(month from Risposte.creation_date)="+mm
                + " AND Domande.Tags LIKE '%"+taglike+"%' "
                + "ORDER BY Risposte.owner_user_id ASC)"
				+ " ORDER BY User "
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
	public Job runQuerySprint2(String yyyy, String mm, String dd, String limit, String groupby, String column3) throws InterruptedException{

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT Risposte.owner_user_id as Ris, Domande.owner_user_id as Dom" +column3
				+" FROM `bigquery-public-data.stackoverflow.posts_questions` as Domande " 
				+"INNER JOIN `bigquery-public-data.stackoverflow.posts_answers` as Risposte ON Domande.id = Risposte.parent_id " 
				+"WHERE Risposte.owner_user_id is NOT NULL "
				+"AND Domande.owner_user_id is NOT NULL "
				+" AND extract(year from Domande.creation_date)="+yyyy
				+" AND extract(month from Domande.creation_date)="+mm
				+" AND extract(day from Domande.creation_date)="+dd
				+groupby
				+" ORDER BY Ris, Dom" 
				+" LIMIT "+limit)
				
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
	public Job runQuerySprint2(String user, String limit, String order, String where, String nnull) throws InterruptedException{
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT distinct Risposte.owner_user_id as Ris, Domande.owner_user_id as Dom " 
				+"FROM `bigquery-public-data.stackoverflow.posts_questions` as Domande " 
				+"INNER JOIN `bigquery-public-data.stackoverflow.posts_answers` as Risposte ON Domande.id = Risposte.parent_id " 
				+"WHERE "+where+".owner_user_id = "+user
				+" AND "+nnull+".owner_user_id is NOT NULL "
				+" ORDER BY "+order 
				+" LIMIT "+limit)
				
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
	



	public List<Long[]> getResults(final Job queryJob, int query) throws JobException, InterruptedException {
	    
	    List<Long[]> results = new ArrayList<>();
	    if (queryJob != null) {
	    	int d=0;
	        TableResult result = queryJob.getQueryResults();
	        switch (query) {
	         case 1: case 2:
	        	for (FieldValueList row : result.getValues()) {
						d++;
			            Long[] UserID= {row.get("User").getLongValue()};
			            System.out.println("#"+d+" User: "+UserID[0]);
			            results.add(UserID);  
	        	 }
	        	 break;
	         case 3: case 4: case 5:
	        	 for (FieldValueList row : result.getValues()) {
        				d++;
        				Long[] valori= {row.get("Ris").getLongValue(), row.get("Dom").getLongValue()};
        				System.out.println("#"+d+" from:"+valori[0]+" to:"+valori[1]);
        				results.add(valori);
        		}
        		break;
	         case 6:
	        	 for (FieldValueList row : result.getValues()) {
     				d++;
     				Long[] valori= {row.get("Ris").getLongValue(), row.get("Dom").getLongValue(), row.get("weight").getLongValue()};
     				System.out.println("#"+d+" from:"+valori[0]+" to:"+valori[1]+" weight:"+valori[2]);
     				results.add(valori);
     		}
	        	 break;
	        }    
	   }
	    return results;
	}

}