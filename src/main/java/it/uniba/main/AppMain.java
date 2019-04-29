package it.uniba.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
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
		GoogleDocsUtils ut = new GoogleDocsUtils();
		String spid="";
		String[] type= {"",""};
        int query=0;
        
		if (args.length > 0) {
			switch (args[3]) {
			case "type=question":
				System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda ");
				type[0]="1";
				type[1]="1";
				query=1;
				spid = ut.createSheet("Sprint 1 hopcroft - User Story 1");
				break;

			case "type=answer":
				System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno dato almeno una risposta ");
				type[0]="2";
				type[1]="2";
				query=1;
				spid = ut.createSheet("Sprint 1 hopcroft - User Story 2");
				break;
				
			case "type=post":
				System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno fatto almeno un post ");
				type[0]="1";
				type[1]="2";
				query=1;
				spid = ut.createSheet("Sprint 1 hopcroft - User Story 3");
				break;
				
			default: 
				if (((args[3].split("="))[0]).equals("taglike")) {
					switch (args[2]) {
					case "type=question":
						System.out.println("Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda (Question) su un dato argomento (Tag) ");
						type[0]="1";
						type[1]="1";
						query=2;
						spid = ut.createSheet("Sprint 1 hopcroft - User Story 4");
						break;
				
					case "type=answer":
						System.out.println("");
						break;
					
					case "type=post":
						System.out.println("");
						break;
					}
				} else {System.out.println("Inserire dati in formato yyyy=____ mm=__ dd=__ type=________ limit=___ /n oppure yyyy=____ mm=__ type=________ taglike=____ limit=___");
					System.exit(0);
					}
			}
		} else {System.out.println("Inserire dati in formato yyyy=____ mm=__ dd=__ type=________ limit=___ /n oppure yyyy=____ mm=__ type=________ taglike=____ limit=___");
				System.exit(0);
			}
		
		String yyyy=(args[0].split("="))[1];
		String mm=(args[1].split("="))[1];
		String limit=(args[4].split("="))[1];
		ISOQuery soq = new SOQuery();
		Job job;
		if(query==1)
		{
		 String dd=(args[2].split("="))[1];
		 job = soq.runQuery(yyyy, mm, dd, type, limit);
		}
		else
		{
			String taglike=(args[3].split("="))[1];
			job = soq.runQuery2(yyyy, mm, type, taglike, limit);
		}
				
		Map<Long, Long> res = soq.getResults(job);
		ut.shareSheet(spid);
		ut.getSheetByTitle(spid);
		ut.writeSheet(spid, res);

	}

}
