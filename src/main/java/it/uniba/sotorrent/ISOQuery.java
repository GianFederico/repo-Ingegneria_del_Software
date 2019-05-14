package it.uniba.sotorrent;

import com.google.cloud.bigquery.JobException;

//import java.util.Map;
import java.util.List;
import com.google.cloud.bigquery.Job;

/**
 * Interface for running a query on Stack Overflow via Google's BigQuery service.
 */
public interface ISOQuery {

	/**
	 * Starts the query.
	 * @return The job for the query.
	 * @throws InterruptedException Raised on timeouts.
	*/
	Job runQuerySprint1(String yyyy, String mm, String dd, String[] type, String limit) throws InterruptedException;
	Job runQuerySprint1(String yyyy, String mm, String[] type, String taglike, String limit) throws InterruptedException;

	Job runQuerySprint2(String yyyy, String mm, String dd, String limit, String group, String column3) throws InterruptedException;

	Job runQuerySprint2(String user, String limit, String order, String where, String nnull) throws InterruptedException;

	
	
	/**
	 * Returns the results from the query job.
	 * @param job The job associated to the query.
	 * @return Results as a hash map //, with URL as key and view count as value.
	 * @throws JobException Generic error occurred.
	 * @throws InterruptedException Raised on timeouts.
	 */

	List<Long[]> getResults(Job job, int query) throws JobException, InterruptedException;

}


