For å fullføre laben, ber vi deg om å svare på følgende spørsmål. Svar på spørsmålene ved å fylle ut denne filen. 
Disse 4 spørsmålene vil telle 0.5 poeng hver

## Forklar hvordan du har brukt grensesnitt til å gjenbruke kode i TetrisView.

I TetrisView gjør jeg dette ved bruker jeg grensesnitt ved å blant annet ta i bruk ViewableTetrisModel. Dette grensesnittet kobler sammen TetrisModel og TetrisView slik at TetrisView ikke jobber direkte med TetrisModel. TetrisModel tar inn både grensesnittet ControllableTetrisModel og ViewableTetrisModel. TetrisView trenger kun tilgang til den informasjonen som skal vises på skjermen som blant annet brettets dimensjoner, brettets celler, og cellene til den fallende tetrominoen. Ved å ta i bruk ViewableTetrisModel i TetrisView, får TetrisView kun tilgang til de metodene den trenger for å tegne det den skal på skjermen. 
Det gjør da at TetrisView ikke behøver å vite akkurat hvilke klasse den jobber med, bare at den klassen implementerer ViewableTetrisModel.
Dette gjør at det blir både enklere å teste og å gjenbruke. 

## Både Tetromino og Grid implementerer Iterable<GridCell>, men på hver sin måte, forklar forskjellen og hvorfor dette er viktig.

Når Iterable<GridCell> itererer gjennom Grid, vil den iterere gjennom alle cellene, uansett om de er tomme eller ikke. Det er dette som blir brukt til å tegne hele brettet. I motsetning til Grid, vil en iterasjon gjennom Tetromino kun returnere celler som er true eller rettere sagt de cellene som er en del av brikken. Dette er viktig i blant annet view hvor vi kan bruke samme kode for både å tegne grid og tetromino, men få forskjellig oppførsel ut i fra hva vi itererer over. 

## Forklar hvordan du har brukt grensesnitt for å opprettholde MVC-arkitekturen og sikre at visningen ikke kan påvirke logikken i modellen.

Jeg har brukt grensesnitt for å opprettholde MVC-arkitekturen ved å skille mellom hva view og controller har tilgang til. Litt som det jeg forklarte i første spørsmål om at TetrisView kun vil ha tilgang til de metodene den trenger for å tegne det den skal, og kan ikke endre noe i TetrisModel. På samme måte vil TetrisController kun ha tilgang på de metodene den trenger for å kontrollere spillet ved å flytte brikker, rotere og oppdatere tilstand gjennom grensesnittet ControllableTetrisModel. 
TetrisModel representerer model-delen og implementerer både ViewableTetrisModel og ControllableTetrisModel. Dette gjør at jeg opprettholder seperasjon mellom ansvarene i MVC-arkitekturen.

## Forklar hvordan du har gjort Tetromino uforanderlig og hvorfor dette er viktig for å sikre at spillet fungerer som forventet.

Tetromino er gjort uforanderlig ved at objektet ikke kan endres etter det er opprettet. Alle feltene blir satt i konstruktøren, og ingen av dem endres etterpå. Istednfor vil det tegnes en ny Tetromino både ved rotasjon, og forflytning. shiftedBy() og rotatedTetromino er eksempler på slike metoder som returnerer et nytt Tetromino-objekt. Dette vil blant annet gjøre det trygt å bruke objektet flere steder som for eksempel i shadowTetromino(). Dette vil også føre til mer forutsigbar oppførsel i spillet, og gjør at ikke objektet kan bli endret flere steder i programmet, som forhindrer bugs. Alt dette vil gjøre det enklere å teste eller å finne feil i programmet. 

