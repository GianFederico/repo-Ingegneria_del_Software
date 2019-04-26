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

		if (args.length > 0) {
			switch (args[3]) {
			case "type=question":
				System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda ");
				break;

			case "type=answer":
				System.out.println("");
				break;
				
			case "type=post":
				System.out.println("");
				break;
				
			case "taglike=java":
				switch (args[2]) {
				case "type=question":
					System.out.println("");
					break;
				
				case "type=answer":
					System.out.println("");
					break;
					
				case "type=post":
					System.out.println("");
					break;
				}
			}
		} else {System.out.println("Inserire dati in formato yyyy=____ mm=__ dd=__ type=________ limit=___ %n oppure yyyy=____ mm=__ type=________ taglike=____ limit=___");
				System.exit(0);
			}
		String yyyy=args[0].substring(args[0].length() - (args[0].length()-5));
		String mm=args[1].substring(args[1].length() - (args[1].length()-3));
		String dd=args[2].substring(args[2].length() - (args[2].length()-3) );
		String type="1";
		if (args[3].length()<13) { 
			if (args[3].length()==11||args[3].length()==12) {
			type="2";
			} else {type="post";}
		}
		String limit=args[4].substring(args[4].length() - (args[4].length()-6));

		ISOQuery soq = new SOQuery();
		Job job = soq.runQuery(yyyy, mm, dd, type, limit);
		Map<String, Double> res = soq.getResults(job);
		

		GoogleDocsUtils ut = new GoogleDocsUtils();
		String spid = ut.createSheet("Prova sna4so");
		ut.shareSheet(spid);
		ut.getSheetByTitle(spid);
		ut.writeSheet(spid, res);

	}

}
