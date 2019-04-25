package it.uniba.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Map;

import com.google.cloud.bigquery.Job;

import it.uniba.sotorrent.GoogleDocsUtils;
import it.uniba.sotorrent.ISOQuery;
import it.uniba.sotorrent.SOQuery;

/**
 * The main class for the project. It must be customized to meet the project
 * assignment specifications.
 * 
 * <b>DO NOT RENAME</b>
 */
public final class AppMain {

	/**
	 * Private constructor. Change if needed.
	 */
	private AppMain() {

	}

	/**
	 * 	 * This is the main entry of the application.
	 *
	 * @param args
	 *                 The command-line arguments.
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
		System.out.println("Current working dir: " + System.getProperty("user.dir"));

		/*if (args.length > 0) {
			switch (args[0]) {
			case "it":
				System.out.println(
						"Seleziona i 10 post piu' 'favoriti' su Stack Overflow "
						+ "sull'argomento 'google-bigquery'");
				break;

			case "en":
				System.out.println("Select the 10 most favorited Stack Overflow posts "
								   + "on 'google-bigquery'");
				break;

			default:
				System.out.println("Specify the language. "
								   + "Languages supported: 'it' or 'en'");
				break;

			}
		} else {
			//System.out.println("Using default language 'en'");
			System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda ");
		}*/
		System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda ");
		String yyyy=Integer.toString(Integer.parseInt(args[0]));
		String mm=Integer.toString(Integer.parseInt(args[1]));
		String dd=Integer.toString(Integer.parseInt(args[2]));
		/*String yyyy=args[0].substring(args[0].length() - 4 );
		String mm=args[1].substring(args[1].length() - 2 );
		String dd=args[2].substring(args[2].length() - 2 );*/
		String type="1";
		if (args[3].length()<13) { 
			if (args[3].length()==11||args[3].length()==12) {
			type="2";
			} else {type="post";}
		}
		String limit=Integer.toString(Integer.parseInt(args[4]));

		ISOQuery soq = new SOQuery();
		Job job = soq.runQuery(yyyy, mm, dd, type, limit);
		Map<String, String> res = soq.getResults(job);

		GoogleDocsUtils ut = new GoogleDocsUtils();
		String spid = ut.createSheet("Prova sna4so");
		ut.shareSheet(spid);
		ut.getSheetByTitle(spid);
		ut.writeSheet(spid, res);

	}

}
