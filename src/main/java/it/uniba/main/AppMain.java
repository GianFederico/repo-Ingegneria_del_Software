package it.uniba.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
//import java.util.Map;
import java.util.List;

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
        //String[] prova= {"yyyy=2016", "mm=02", "dd=11", "type=question", "edge=no", "limit=100"};        
        
		if (args.length > 0) {
			switch (args[3]) {
			case "type=question":
				switch ((args[4].split("=")[0])){
					case "edge":
						if (((args[4].split("="))[1]).equals("yes")){
							System.out.println("Visualizzare la lista delle prime 100 coppie (from, to) relative a domande (Question) poste in un "
								+	"dato anno, mese e giorno");
							query=4;
							spid = ut.createSheet("Sprint 2 hopcroft - User Story 1");
							}
						else {
							query=3;
							System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda ");
							type[0]="1";
							type[1]="1";
							spid = ut.createSheet("Sprint 1 hopcroft - User Story 1");
							}
						break;
					case "limit":
						System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda ");
						type[0]="1";
						type[1]="1";
						query=1;
						spid = ut.createSheet("Sprint 1 hopcroft - User Story 1");
						break;}
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
						System.out.println("Visualizzare la lista dei primi 100 id utente (User) che hanno dato almeno una risposta (Answer) su un dato argomento (Tag) ");
						type[0]="2";
						type[1]="2";
						query=2;
						spid = ut.createSheet("Sprint 1 hopcroft - User Story 5");
						break;
					
					case "type=post":
						System.out.println("Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno un Post su un dato argomento (Tag)");
						type[0]="1";
						type[1]="2";
						query=2;
						spid = ut.createSheet("Sprint 1 hopcroft - User Story 6");
						break;
					}
				} else {System.out.println("Inserire dati in formato yyyy=____ mm=__ dd=__ type=________ limit=___ \n oppure yyyy=____ mm=__ type=________ taglike=____ limit=___ "
						+"\n oppure yyyy=____ mm=__ dd=__ type=________ edge=___ limit=___");
					System.exit(0);
					}
			}
		} else {System.out.println("Inserire dati in formato yyyy=____ mm=__ dd=__ type=________ limit=___ \n oppure yyyy=____ mm=__ type=________ taglike=____ limit=___ "
				+"\n oppure yyyy=____ mm=__ dd=__ type=________ edge=___ limit=___");
				System.exit(0);
			}
		
		String yyyy=(args[0].split("="))[1];
		String mm=(args[1].split("="))[1];
		String dd="";
		ISOQuery soq = new SOQuery();
		Job job = null;
		String taglike="";
		String limit="";
		switch (query) {
			case 1:
				limit=(args[4].split("="))[1];
				dd=(args[2].split("="))[1];
				job = soq.runQuerySprint1(yyyy, mm, dd, type, limit);
				break;
			case 2:
				limit=(args[4].split("="))[1];
				taglike=(args[3].split("="))[1];
				job = soq.runQuerySprint1(yyyy, mm, type, taglike, limit);
				break;
			case 3:
				limit=(args[5].split("="))[1];
				dd=(args[2].split("="))[1];
				job = soq.runQuerySprint1(yyyy, mm, dd, type, limit);
				break;
			case 4:
				limit=(args[5].split("="))[1];
				dd=(args[2].split("="))[1];
				job = soq.runQuery1to3S2(yyyy, mm, dd, limit);
				break;
				
		}
		
		List<Long> res= soq.getResults(job, query, 1);
		ut.shareSheet(spid);
		ut.getSheetByTitle(spid);
		if (query<4) {
			ut.writeSheet(spid, res, Integer.parseInt(limit));
		}else {
			List<Long> res2= soq.getResults(job, query, 2);
			ut.writeSheet(spid, res, res2, Integer.parseInt(limit));
		}
		System.exit(0);
		

	}

}
