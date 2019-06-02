# Report

## Indice

1. [Introduzione](#introduzione) 
2. [Modello di dominio](#modello-di-dominio) 
3. [Requisiti specifici](#requisiti-specifici)
4. [System Design](#system-design)
     – Stile architetturale adottato
     – Diagramma dei package
     – Diagramma dei componenti
5. [OO Design](#oo-design)
     – Diagrammi delle classi e diagrammi di sequenza, della prima user story dello Sprint 1 e della seconda user story dello Sprint 2
6. [Riepilogo del test](#riepilogo-dei-test) 
     – Tabella riassuntiva di Jacoco, con dati sul numero
        dei casi di test e copertura del codice 
7. [Manuale utente](#manuale-utente)
8. [Processo di sviluppo e organizzazione del lavoro](#processo-di-sviluppo-e-organizzazione-del-lavoro)
9. [Analisi retrospettiva](#analisi-retrospettiva)


# Introduzione
Gruppo Hopcroft (da John Hopcroft, informatico statunitense vincitore del premio Turing insieme a Robert Tarjan nel ’86 per il suo contributo nel disegno e nell’ analisi degli algoritmi e delle strutture di dati) composto da Ignazio Spadavecchia, Giovanni Federico Poli, Gianluca Patruno, Michele Giovanni Latronico, Giuseppe Zizzo, Danilo Meleleo. 
Il nome del progetto è SNA4SO: un’applicazione di social network analysis (SNA) applicata a Stack Overflow (SO), con interfaccia a linea di comando (CLI).

### Cosa è Stack Overflow?
Stack Overflow è un sito web nato nel 2008, che fa parte della rete Stack Exchange, nel quale si possono porre domande riguardo ai più svariati argomenti di programmazione. 
Stack Overflow vanta una vasta comunità di software developer, orientata a fornire supporto ad altri software developer, infatti il sito fornisce la possibilità agli utenti di chiedere o rispondere a domande e, attraverso i membri attivi, di votare le domande e le risposte in positivo o in negativo.
Viene visitato da più di 50 milioni di persone ogni mese, di cui si stima che 21 milioni siano sviluppatori professionisti (fonte: [**Developer Survey Results 2019**](https://insights.stackoverflow.com/survey/2019)) per imparare, condividere, e migliorare le proprie abilità nell’ambito della programmazione.

### A cosa serve SNA4SO:
Questa applicazione a Command-Line permetterà agli utenti di interfacciarsi, con specifiche richieste, a Stack Overflow attraverso l’API di Google BigQuery, eseguendo una serie di comandi partendo dal comando base 
`docker run --rm softeng1819infuniba/<nome_repository> <options>`
utilizzando il software Docker che mette a disposizione un container come macchina virtuale sul quale è possibile eseguirli. Tramite questi comandi l’utente sarà in grado di acquisire informazioni sugli utenti e sulle domande e/o risposte all’interno del sito (Per approfondimento vedi Manuale utente)

# Modello di dominio




![](doc/drawings/modello_1.png)




# Requisiti specifici

### Requisiti funzionali, l’utente deve poter:
1.	Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda (Question) in un dato anno, mese e giorno.
2.	Visualizzare la lista dei primi 100 id utente (User) che hanno dato almeno una risposta (Answer) in un dato anno, mese e giorno.
3.	Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno un Post in un dato mese, anno, giorno (un Post può essere una domanda o una risposta).
4.	Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno una domanda (Question) su un dato argomento (Tag) in un dato mese e anno.
5.	Visualizzare la lista dei primi 100 id utente (User) che hanno dato almeno una risposta (Answer) su un dato argomento (Tag) in un dato mese e anno.
6.	Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno un Post su un dato argomento (Tag) in un dato mese e anno (un Post può essere una domanda o una risposta).
7.	Visualizzare la lista delle prime 100 coppie (from, to) relative a domande (Question) poste in un dato anno, mese e giorno.
8.	Visualizzare la lista delle prime 100 coppie (from, to) relative a domande (Question) poste da un determinato utente.
9.	Visualizzare la lista delle prime 100 coppie (from, to) relative a risposte (Answer) date da un determinato utente.
10.	Visualizzare la lista delle prime 100 triple (from, to, weight) relative a domande (Question) poste in un dato anno, mese e giorno.
11.	Visualizzare la lista delle prime 100 triple (from, to, weight) relative a domande (Question) poste da un determinato utente.
12.	Visualizzare la lista delle prime 100 triple (from, to, weight) relative a risposte (Answer) date da un determinato utente.

### Requisiti non funzionali:
- Sono stati creati i casi di test automatici.
- Sono stati risolti i problemi segnalati da CheckStyle.
- Sono stati risolti i problemi segnalati da Findbugs.
- Sono stati applicati i principi di OO design.
- Viene creato scritto e condiviso il Google Sheet dal quale è possibile visualizzare i risultati ottenti.
- Viene convalidato il formato della data inserita da command-line (errata anche se minore dell’anno di creazione di Stack Overflow o maggiore della data corrente) 


# System Design

### Stile architetturale adottato:
Abbiamo deciso di utilizzare lo stile architetturale N(4)-Tier . Questo stile, indica un’architettura software le cui funzionalità sono logicamente separate, ovvero suddivise su più strati o livelli differenti, in comunicazione tra di loro. Ciascuno strato è in comunicazione diretta con quelli adiacenti, ovvero richiede e offre servizi e/o dati ai livelli con cui è a contatto.


![](doc/drawings/stile_architetturale.jpg)

### Diagramma dei package:



![](doc/drawings/package.png)
### Diagramma dei componenti:
![](doc/drawings/componenti.png)




# OO design

### Prima user story dello sprint 1

- Diagramma delle classi:


![](doc/drawings/modello_1.png)

- Diagramma di sequenza:
![](doc/drawings/dds_query1sprint1.PNG)



### Seconda user story dello sprint 2

- Diagramma delle classi:


![](doc/drawings/modello_2.png)




- Diagramma di sequenza:
![](doc/drawings/dds_query2sprint2.PNG)


Per lo sviluppo del progetto abbiamo seguito i principi dell'OO design tra i quali, i principi SOLID, DRY. 


# Riepilogo dei test

- ### Tabella riassuntiva di JaCoCo:

![](doc/drawings/JaCoCo.png)

- ### Casi di test di JUnit:

![](doc/drawings/testreport.png)

- ### Report di FindBugs:

![](doc/drawings/FindBugsReport.png)


# Manuale Utente
Per far partire l’applicazione, è necessario l’utilizzo di Docker, dal quale va scaricata l’ultima versione aggiornata dell’immagine, tramite il comando: `docker pull softeng1819infuniba/hopcroft`
	
I comandi per far partire il container di docker su Windows sono: `docker run --rm softeng1819infuniba/hopcroft`
Seguiti dai comandi qui di seguito per eseguire una delle funzioni implementate:

| comando | descrizione |
|-------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| `yyyy=[aaaa] mm=[mm] dd=[gg] type=question limit=[limit]` opzionale:`edge=no weight=no` | Visualizza la lista dei primi [limit] id utente (User) che hanno fatto almeno una domanda (Question) in un dato anno, mese e giorno. |
| `yyyy=[aaaa] mm=[mm] dd=[gg] type=answer limit=[limit]` opzionale:`edge=no weight=no` | Visualizza la lista dei primi [limit] id utente (User) che hanno dato almeno una risposta (Answer) in un dato anno, mese e giorno. |
| `yyyy=[aaaa] mm=[mm] dd=[gg] type=post limit=[limit]` opzionale:`edge=no weight=no` |Visualizza la lista dei primi [limit] id utente (User) che hanno fatto almeno un Post in un dato mese, anno, giorno (un Post può essere una domanda o una risposta). |
| `yyyy=[aaaa] mm=[mm] type=question taglike=[tag] limit=[limit]` opzionale:`edge=no weight=no` | Visualizza la lista dei primi [limit] id utente (User) che hanno fatto almeno una domanda (Question) su un dato argomento (Tag) in un dato mese e anno. |
| `yyyy=[aaaa] mm=[mm] type=answer taglike=[tag] limit=[limit]` opzionale:`edge=no weight=no` | Visualizza la lista dei primi [limit] id utente (User) che hanno dato almeno una risposta (Answer) su un dato argomento (Tag) in un dato mese e anno. |
| `yyyy=[aaaa] mm=[mm] type=post taglike=[tag] limit=[limit]` opzionale:`edge=no weight=no` | Visualizza la lista dei primi [limit] id utente (User) che hanno fatto almeno un Post su un dato argomento (Tag) in un dato mese e anno (un Post può essere una domanda o una risposta). |
| `yyyy=[aaaa] mm=[mm] dd=[gg] type=question edge=yes limit=[limit]` opzionale:`weight=no` | Visualizza la lista dei primi [limit] coppie (from, to) relative a domande (Question) poste in un dato anno, mese e giorno. |
| `type=question user=[userID] edge=yes limit=[limit]` opzionale:`weight=no` | Visualizza la lista dei primi [limit] coppie (from, to) relative a domande (Question) poste da un determinato utente. |
| `type=answer user=[userID] edge=yes limit=[limit]` opzionale:`weight=no` | Visualizza la lista dei primi [limit] coppie (from, to) relative a risposte (Answer) date da un determinato utente. |
| `yyyy=[aaaa] mm=[mm] dd=[gg] type=question edge=yes weight=yes limit=[limit]` | Visualizza la lista dei primi [limit] triple (from, to, weight) relative a domande (Question) poste in un dato anno, mese e giorno. |
| `type=question user=[userID] edge=yes weight=yes limit=[limit]` | Visualizza la lista dei primi [limit] triple (from, to, weight) relative a domande (Question) poste da un determinato utente. |
| `type=answer user=[userID] edge=yes weight=yes limit=[limit]` | Visualizza la lista dei primi [limit] triple (from, to, weight) relative a risposte (Answer) date da un determinato utente.|

### Legenda:
- [aaaa]:      anno (es. 2016). Il valore dell’anno è compreso tra 2008 e l’anno corrente.
- [mm]:	       mese (es. 02). Il valore del mese è compreso tra 01 e 12.
- [gg]:	       giorno (es.09). Il valore del giorno è compreso tra 01 e 31.
- [limit]:     numero risultati visualizzati (es.100).
- [userID]:    id dell’utente (es.1109).

Se i valori non rientrano nei range prestabiliti, l’applicazione restituisce un messaggio di errore specificando quale valore non è stato inserito correttamente. Se invece la richiesta non ha prodotto alcun risultato, l'applicazione restituisce un messaggio esplicitandolo.


# Processo di sviluppo e organizzazione del lavoro
Il carico di lavoro è stato gestito il più equamente possibile all’intero del gruppo, tenendo conto delle preferenze e della capacità di ogni suo componente. Ciò è stato possibile grazie a degli incontri giornalieri che oltre a permetterci di organizzarci il lavoro nel modo migliore e di esprimere le proprie opinioni sulle varie parti del progetto, ci hanno permesso anche di conoscerci meglio. 
L'università e le varie aule studio messe a disposizione agli studenti sono stati fondamentali per i nostri incontri. Qui abbiamo potuto confrontarci e andare avanti nello sviluppo del software, aiutati nell’organizzazione del gruppo stesso anche dai canali di comunicazione forniti dal docente (Slack) dove, oltre che coordinare i nostri incontri, abbiamo potuto anche scambiarci le nostre idee riugardo il software.
Utilizzando Git, è risultato semplice condividere il lavoro svolto singolarmente o al massimo in coppia, con il resto del gruppo ma anche con i docenti che, nelle ore di lezione, sono riusciti a dare giusti consigli sia dal punto di vista organizzativo che tecnico per lo sviluppo del software.
Ogni membro del gruppo è riuscito senza troppi problemi a seguire lo stile Scrum: utilizzando il corretto workflow di Git, ha creato un nuovo branch e si è dedicato alla risoluzione dell'issue assegnatogli. Dopo averlo risolto e “committato”, il lavoro svolto veniva trasformato in una pull request che, una volta approvata da almeno un altro membro del gruppo, dava il consenso per il merge sul branch master. Infine abbiamo modificato il progetto secondo i canoni di checkstyle, abbiamo risolto i warings sollevati da findbugs e testato il nostro progetto con junit.

# Analisi retrospettiva
A conclusione del lavoro svolto possiamo affermare che ciò che in primo luogo ci ha reso felici è l'aver creato un gruppo coeso in ambito lavorativo e sociale. Inoltre ciò che ci ha soddisfatto maggiormente è esserci potuti avvicinare a tanti nuovi elementi come Git, Slack, o lo stesso Java e poterli utilizzare con la certezza, confermata dagli incontri con gli ospiti portati a lezione dal docente, che quelle conoscenze non saranno solo fini a se stesse, ma saranno utili anche nel mondo del lavoro. L’utilizzo di Git e Github, seppure all’inizio sembrava fin troppo meticoloso, si è rivelato decisamente utile per il controllo di versione e per poter tornate ad una versione precendente se alcune modifiche si dimostravano errate o non complete. Stancante è stato commentare il codice con Javadoc perché purtroppo era stato da noi tralasciato come uno degli ultimi punti "to do", e per questo si sono accumulati fin troppi elementi non commentati nel corso dello sviluppo dell’applicazione. Ma anche questo sarà sicuramente di monito per il futuro. Siamo rimasti purtroppo amareggiati dai nostri stessi errori commessi durante lo Sprint 1 (in particolar modo quello di aver dimenticato di chiudere su GitHub il vecchio Sprint), i quali però ci hanno aiutato ad essere più attenti e precisi in quelli successivi. In conclusione, il gruppo di lavoro ha funzionato molto bene, grazie agli incontri giornalieri che hanno permesso di conoscerci, confrontarci e scambiare idee, migliorando giornalmente l’ambiente di lavoro.




