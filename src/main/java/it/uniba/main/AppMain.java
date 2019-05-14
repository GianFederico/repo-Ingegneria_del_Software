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
        boolean edge=false;
        boolean weight=false;
        String yyyy="";
        String mm="";
        String dd="";
        String tipo="";
        String taglike="";
        String limit="";
		String groupby="";
		String column3="";
		String user="";
        //String[] prova={"yyyy=2016", "mm=02", "dd=11", "type=question", "edge=yes", "weight=no", "limit=100"};    
		if (args.length > 0) {
			for (int i=0;i<args.length;i++) {
				switch ((args[i].split("="))[0]) {
					case "yyyy": 
						yyyy=((args[i].split("="))[1]);
					break;
					case "mm": 
						mm=((args[i].split("="))[1]);
					break;
					case "dd": 
						dd=((args[i].split("="))[1]);
					break;
					case "type": 
						tipo=((args[i].split("="))[1]);
						switch (tipo) {
							case "question": case "questions":
								type[0]="1";
								type[1]="1";
								break;
							case "answer": case "answers":
								type[0]="2";
								type[1]="2";
								break;
							case "post": case "posts":
								type[0]="1";
								type[1]="2";
								break;
						}
					break;
					case "edge": 
						switch ((args[i].split("="))[1]) {
							case "yes":
								edge=(true);
								break;
							case "no":
								edge=(false);
								break;
							default:
								System.out.println("Errore! Campo 'edge' diverso da 'yes' o 'no'");
								System.exit(0);
								break;
						}
					break;
					case "weight": 
						switch ((args[i].split("="))[1]) {
						case "yes":
							weight=(true);
							break;
						case "no":
							weight=(false);
							break;
						default:
							System.out.println("Errore! Campo 'weight' diverso da 'yes' o 'no'");
							System.exit(0);
							break;
					}
					break;
					case "limit":
						limit=((args[i].split("="))[1]);
					break;
					case "taglike":
						taglike=((args[i].split("="))[1]);
						break;
					case "user":
						user=((args[i].split("="))[1]);
						break;
					default:
						System.out.println("Formato non corretto! \n Inserire dati in formato:\n"
								+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  limit=___ \n"
								+ " - yyyy=____  mm=__  type=(question(s), answer(s) or post(s))  taglike=____  limit=___ \n"
								+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s)) edge=(yes or no)  limit=___ \n"
								+ " - type=(question(s), answer(s) or post(s))  user=___  edge=(yes or no)  limit=___ \n"
								+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  edge=(yes or no)  weight=(yes or no)  limit=100");
						System.exit(0);
					break;
				}
				
			}
		} else {
			System.out.println("Formato non corretto! \n Inserire dati in formato:\n"
					+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  limit=___ \n"
					+ " - yyyy=____  mm=__  type=(question(s), answer(s) or post(s))  taglike=____  limit=___ \n"
					+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s)) edge=(yes or no)  limit=___ \n"
					+ " - type=(question(s), answer(s) or post(s))  user=___  edge=(yes or no)  limit=___ \n"
					+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  edge=(yes or no)  weight=(yes or no)  limit=100");
			System.exit(0);
		}
        
		
		//inizio Sprint 1
		if (!edge && !weight && taglike.equals("")) {
			switch (tipo){
				case "question": case "questions":
					System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda ");
					query=1;
					spid = ut.createSheet("Sprint 1 hopcroft - User Story 1");
					break;
				case "answer": case "answers":
					System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno dato almeno una risposta ");
					query=1;
					spid = ut.createSheet("Sprint 1 hopcroft - User Story 2");
					break;
				case "post": case "posts":
					System.out.println("Visualizza la lista dei primi 100 id utente (User) che hanno fatto almeno un post ");
					query=1;
					spid = ut.createSheet("Sprint 1 hopcroft - User Story 3");
					break;
			}			
		}
		
		if (!edge && !weight && !(taglike.equals(""))) {
			switch (tipo){
				case "question": case "questions":
					System.out.println("Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda (Question) su un dato argomento (Tag) ");
					query=2;
					spid = ut.createSheet("Sprint 1 hopcroft - User Story 4");
					break;
				case "answer": case "answers":
					System.out.println("Visualizzare la lista dei primi 100 id utente (User) che hanno dato almeno una risposta (Answer) su un dato argomento (Tag) ");
					query=2;
					spid = ut.createSheet("Sprint 1 hopcroft - User Story 5");
					break;
				case "post": case "posts":
					System.out.println("Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno un Post su un dato argomento (Tag)");
					query=2;
					spid = ut.createSheet("Sprint 1 hopcroft - User Story 6");
					break;
			}
		}
		//fine Sprint 1
		
		
		
		//inizio Sprint 2
		if (edge && !weight) {
			switch (tipo) {
				case "question": case "questions":
					if (user.equals("")) {
						System.out.println("Visualizzare la lista delle prime 100 coppie (from, to) relative a domande (Question) poste in un "
								+	"dato anno, mese e giorno");
						query=3;
						spid = ut.createSheet("Sprint 2 hopcroft - User Story 1");
					}else {
						System.out.println("Visualizzare la lista delle prime 100 coppie (from, to) relative a domande (Question) poste da un determinato utente.");
						query=4;
						spid = ut.createSheet("Sprint 2 hopcroft - User Story 2");
					}
					break;
				case "answer": case "answers":
					System.out.println("Visualizzare la lista delle prime 100 coppie (from, to) relative a risposte (Answer) date da un determinato utente.");
					query=5;            
					spid = ut.createSheet("Sprint 2 hopcroft - User Story 3");
					break;
			}
		}
		
		if (edge && weight) {
			switch (tipo) {
			case "question": case "questions":
				if (user.equals("")) {
					System.out.println("Visualizzare la lista delle prime 100 triple (from, to, weight) relative a domande (Question) poste " + 
							"in un dato anno, mese e giorno");
					query=6;
					groupby=" GROUP BY Risposte.owner_user_id, Domande.owner_user_id";
					column3=", COUNT (*) AS weight";
					spid = ut.createSheet("Sprint 2 hopcroft - User Story 4");
				}//else {
				
				//}
				break;
			case "answer": case "answers":
			
				break;
		}
		}
		
		if (!edge && weight) {
			System.out.println("Se il valore di 'weight' è 'yes', il valore di edge deve essere necessariamente 'yes'!");
			System.exit(0);
		}
		//fine Sprint 2
		

		
		ISOQuery soq = new SOQuery();
		Job job = null;
		switch (query) {
			case 1:
				job = soq.runQuerySprint1(yyyy, mm, dd, type, limit);
				break;
			case 2:
				job = soq.runQuerySprint1(yyyy, mm, type, taglike, limit);
				break;
			case 3:
				job = soq.runQuerySprint2(yyyy, mm, dd, limit, groupby, column3);
				break;
			case 4:
				job = soq.runQuerySprint2(user, limit, "Ris", "Domande", "Risposte");
				break;
			case 5:
				job = soq.runQuerySprint2(user, limit, "Dom", "Risposte", "Domande");
				break;
			case 6:
				job = soq.runQuerySprint2(yyyy, mm, dd, limit, groupby, column3);
		}
		

		List<Long[]> res= soq.getResults(job, query);
		ut.shareSheet(spid);
		ut.getSheetByTitle(spid);
		ut.writeSheet(spid, res, query);
		System.exit(0);
		

	}

}
