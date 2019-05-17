package it.uniba.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.cloud.bigquery.Job;

import it.uniba.sotorrent.GoogleDocsUtils;
import it.uniba.sotorrent.ISOQuery;
import it.uniba.sotorrent.SOQuery;

/**
 * <<BOUNDARY>>
 * 
 * La classe principale del progetto. 
 * Acquisisce i dati da command-line interpretandoli in modo
 * da eseguire la funzione in grado di restituire i risultati corretti
 * in base ai dati inseriti dall'utente  
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
	 *  This is the main entry of the application.
	 *
	 * @param args The command-line arguments.
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
           
		/**
		 * Smistamento dati ottenuti da command-line
		 */
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
						//Messaggio di errore in caso di input errato
						System.out.println("Formato non corretto! \n Inserire dati in formato:\n"
								+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  limit=___ \n"
								+ " - yyyy=____  mm=__  type=(question(s), answer(s) or post(s))  taglike=____  limit=___ \n"
								+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s)) edge=(yes or no)  limit=___ \n"
								+ " - type=(question(s), answer(s) or post(s))  user=___  edge=(yes or no)  limit=___ \n"
								+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  edge=(yes or no)  weight=(yes or no)  limit=___");
						System.exit(0);
					break;
				}				
			}
		} else {
			//Messaggio di errore nel caso di input vuoto
			System.out.println("Formato non corretto! \n Inserire dati in formato:\n"
					+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  limit=___ \n"
					+ " - yyyy=____  mm=__  type=(question(s), answer(s) or post(s))  taglike=____  limit=___ \n"
					+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s)) edge=(yes or no)  limit=___ \n"
					+ " - type=(question(s), answer(s) or post(s))  user=___  edge=(yes or no)  limit=___ \n"
					+ " - yyyy=____  mm=__  dd=__  type=(question(s), answer(s) or post(s))  edge=(yes or no)  weight=(yes or no)  limit=___");
			System.exit(0);
		}
        
		
		//inizio Sprint 1
		
		/**
		 * Richieste 1,2 e 3 dello Sprint 1
		 */
		if (!edge && !weight && taglike.equals("")) { //
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
		
		/**
		 * Richieste 4,5 e 6 dello Sprint 1
		 */
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
		
		/**
		 * Richieste 1,2 e 3 dello Sprint 2
		 */
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
		
		/**
		 * Richieste 4,5 e 6 dello Sprint 2
		 */
		if (edge && weight) {
			groupby=" GROUP BY Risposte.owner_user_id, Domande.owner_user_id";
			column3=", COUNT (*) AS weight";
			switch (tipo) {
			case "question": case "questions":
				if (user.equals("")) {
					System.out.println("Visualizzare la lista delle prime 100 triple (from, to, weight) relative a domande (Question) poste " + 
							"in un dato anno, mese e giorno");
					query=6;					
					spid = ut.createSheet("Sprint 2 hopcroft - User Story 4");
				}else {
					System.out.println("Visualizzare la lista delle prime 100 triple (from, to, weight) relative a domande (Question) poste " + 
							"da un determinato utente");
					query=7;					
					spid = ut.createSheet("Sprint 2 hopcroft - User Story 5");
				}
				break;
			case "answer": case "answers":
				System.out.println("Visualizzare la lista delle prime 100 triple (from, to, weight) relative a risposte (Answer) date " + 
						"da un determinato utente");
					query=8;
					spid = ut.createSheet("Sprint 2 hopcroft - User Story 6");
				break;
		 }
		}
		
		/**
		 * Messaggio di errore nel caso di input non corretto
		 */
		if (!edge && weight) {
			System.out.println("Se il valore di 'weight' e' 'yes', il valore di edge deve essere necessariamente 'yes'!");
			System.exit(0);
		}
		//fine Sprint 2
		
		
		ISOQuery soq = new SOQuery();
		Job job = null;
		
		/** 
		 * Scelta della query da eseguire in base ai valori della variabile 'query'
		 * 
		 * Valori di 'query': 1-->User Story 1,2 e 3 dello Sprint 1
		 * 				      2-->User Story 4,5 e 6 dello Sprint 1
		 * 				      3-->User Story 1 dello Sprint 2
		 * 				      4-->User Story 2 dello Sprint 2
		 * 				      5-->User Story 3 dello Sprint 2	 
		 *  			      6-->User Story 4 dello Sprint 2
		 *   			      7-->User Story 5 dello Sprint 2
		 *   			      8-->User Story 6 dello Sprint 2	 	 	 
		 */
		switch (query) {
			case 1:
				job = soq.runQuerySprint1(yyyy, mm, dd, type, limit);
				break;
			case 2:
				job = soq.runQuerySprint1(yyyy, mm, type, taglike, limit);
				break;
			case 3: case 6:
				job = soq.runQuerySprint2(yyyy, mm, dd, limit, groupby, column3);
				break;
			case 4: case 7:
				job = soq.runQuerySprint2(user, limit, "Ris", "Domande", "Risposte", groupby, column3);
				break;
			case 5: case 8:
				job = soq.runQuerySprint2(user, limit, "Dom", "Risposte", "Domande", groupby, column3);
				break;
		}
		
		
		List<Long[]> res= soq.getResults(job, query); //Riempimento della lista coi valori ottenuti dalla query
		ut.shareSheet(spid);						  //Rende disponibile a chiunque abbia il link l'accesso allo spreadsheet coi risultati
		ut.getSheetByTitle(spid);					  //Associa il titolo dello spreadsheet al suo id
		ut.writeSheet(spid, res, query);			  //Scrive i risultati contenuti nella lista res sullo spreadsheet di Google
		System.exit(0);
		

	}
}
