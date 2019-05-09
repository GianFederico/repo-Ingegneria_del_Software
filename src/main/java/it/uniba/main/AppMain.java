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
        String[] prova= {"yyyy=2016", "mm=02", "dd=11", "type=question","edge=yes", "limit=100"};        
        
		if (prova.length > 0) {
			switch (prova[3]) {
			case "type=question":
				switch ((prova[4].split("=")[0])){
					case "edge":
						if (((prova[4].split("="))[1]).equals("yes")){
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
				if (((prova[3].split("="))[0]).equals("taglike")) {
					switch (prova[2]) {
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
		
		String yyyy=(prova[0].split("="))[1];
		String mm=(prova[1].split("="))[1];
		String dd="";
		ISOQuery soq = new SOQuery();
		Job job = null;
		String taglike="";
		String limit="";
		switch (query) {
			case 1:
				limit=(prova[4].split("="))[1];
				dd=(prova[2].split("="))[1];
				job = soq.runQuery1to3S1(yyyy, mm, dd, type, limit);
				break;
			case 2:
				limit=(prova[4].split("="))[1];
				taglike=(prova[3].split("="))[1];
				job = soq.runQuery4to6S1(yyyy, mm, type, taglike, limit);
				break;
			case 3:
				limit=(prova[5].split("="))[1];
				dd=(prova[2].split("="))[1];
				job = soq.runQuery1to3S1(yyyy, mm, dd, type, limit);
				break;
			case 4:
				limit=(prova[5].split("="))[1];
				dd=(prova[2].split("="))[1];
				job = soq.runQuery1to3S2(yyyy, mm, dd, limit);
				break;
				
		}
		
		Map<Double, Double> res = soq.getResults(job, query);
		ut.shareSheet(spid);
		ut.getSheetByTitle(spid);
		ut.writeSheet(spid, res,query);
		System.exit(0);

	}

}
