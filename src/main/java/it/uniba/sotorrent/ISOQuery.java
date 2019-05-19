package it.uniba.sotorrent;

import com.google.cloud.bigquery.JobException;

import java.util.List;
import com.google.cloud.bigquery.Job;

/**
 * <<noECB>>
 * 
 * Interface to run queries on Stack Overflow via Google's BigQuery service.
 * 
 * Interfaccia per eseguire le query sui dati di Stack Overflow 
 * per mezzo del servizio BigQuery di Google.  
 * 
 */
public interface ISOQuery {

	/**
	 * Method able to run the queries necessary to satisfy the request 1, 2 and 3 related to Sprint 1.
	 * Based on the acquired parameters, the method able to process them is chosen.
	 * 
	 * Metodo in grado di eseguire le query necessarie a soddisfare le richieste 1,2 e 3 relative allo Sprint 1.
	 * In base ai parametri acquisiti viene scelta la metodo in grado di processarli.
	 * 
	 * @param yyyy Obtained from command-line, is the year used to filter the query results. 
	 * @param mm Obtained from command-line, is the month used to filter the query results. 
	 * @param dd Obtained from command-line, is the day used to filter the query results. 
	 * @param type Array containing the post type id used to filter the query results.
	 * @param limit Obtained from command-line, is the limit number for the shown results.
	 * @return The job for the query.
	 * @throws InterruptedException Raised on timeouts.
	*/
	Job runQuerySprint1(String yyyy, String mm, String dd, String[] type, String limit) throws InterruptedException;
	
	/**
	 * Method able to run the queries necessary to satisfy the request 4, 5 and 6 related to Sprint 1.
	 * Based on the acquired parameters, the method able to process them is chosen.
	 * 
	 * Metodo in grado di eseguire le query necessarie a soddisfare le richieste 4,5 e 6 relative allo Sprint 1.
	 * In base ai parametri acquisiti viene scelta la metodo in grado di processarli.
	 * 
	 * @param yyyy Obtained from command-line, is the year used to filter the query results. 
	 * @param mm Obtained from command-line, is the month used to filter the query results. 
	 * @param type Array containing the post type id used to filter the query results.
	 * @param taglike Obtained from command-line, is the post tag used to filter the query results.
	 * @param limit Obtained from command-line, is the limit number for the shown results.
	 * @return The job for the query.
	 * @throws InterruptedException Raised on timeouts.
	*/
	Job runQuerySprint1(String yyyy, String mm, String[] type, String taglike, String limit) throws InterruptedException;
	
	
	/**
	 * Method able to run the queries necessary to satisfy the request 1 and 4 related to Sprint 2.
	 * Based on the acquired parameters, the method able to process them is chosen.
	 * 
	 * Metodo in grado di eseguire le query necessarie a soddisfare le richieste 1 e 4 relative allo Sprint 2.
	 * In base ai parametri acquisiti viene scelta la metodo in grado di processarli.
	 * 
	 * @param yyyy Obtained from command-line, is the year used to filter the query results. 
	 * @param mm Obtained from command-line, is the month used to filter the query results. 
	 * @param dd Obtained from command-line, is the day used to filter the query results. 
	 * @param limit Obtained from command-line, is the limit number for the shown results.
	 * @param groupby Optional part of the sql code used to group the table results, empty if not needed.
	 * @param colmun3 Optional part of the sql code indicating the third column of the result table, empty if not needed.
	 * @return The job for the query.
	 * @throws InterruptedException Raised on timeouts.
	*/
	Job runQuerySprint2(String yyyy, String mm, String dd, String limit, String groupby, String column3) throws InterruptedException;
	
	/**
	 * Method able to run the queries necessary to satisfy the request 2, 3, 5 and 6 related to Sprint 2.
	 * Based on the acquired parameters, the method able to process them is chosen.
	 * 
	 * Metodo in grado di eseguire le query necessarie a soddisfare le richieste 2, 3, 5 e 6 relative allo Sprint 2.
	 * In base ai parametri acquisiti viene scelta la metodo in grado di processarli.
	 * 
	 * @param user Obtained from command-line, is the user id used to filter the query results.
	 * @param limit Obtained from command-line, is the limit number for the shown results.
	 * @param order Part of the sql code, used to sort the results basing on the specified field. 
	 * @param where Part of the sql code, used to filter the query results basing on the user request.
	 * @param nnull Part of the sql code, used to filter the query results basing on the user request.
	 * @param groupby Optional part of the sql code used to group the table results, empty if not needed.
	 * @param colmun3 Optional part of the sql code indicating the third column of the result table, empty if not needed.
	 * @return The job for the query.
	 * @throws InterruptedException Raised on timeouts.
	*/
	Job runQuerySprint2(String user, String limit, String order, String where, String nnull, String groupby, String column3) throws InterruptedException;
	
	
	/**
	 * Returns the results from the query job.
	 * 
	 * Restituisce i valori dei risultati ottenuti tramite la query.
	 * 
	 * @param job The job associated to the query.
	 * @param query Index used to identify which query has been used.
	 * @return Results as a list of arrays.
	 * @throws JobException Generic error occurred.
	 * @throws InterruptedException Raised on timeouts.
	 */
	List<Long[]> getResults(Job job, int query) throws JobException, InterruptedException;

}


