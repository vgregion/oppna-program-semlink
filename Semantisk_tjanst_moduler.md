# Semantisk tjänst #

Den semantiska tjänsten är uppbyggd av 4 moduler, se figur 2. Harvester Service extraherar innehållet från websidor, kategoriserar dem enligt en modell och sparar informationen i en RDF databas. Selection Service extraherar en viss typ av information och är indata för Indexing Service. Index Service indexterar informationen och lagrar detta i ett Lucene index. Query Service får nyckelord som inparametrar och använder sig av Taxonomy Service (DTS) för att hämta ut olika relationer. Resultaten från Taxonomy Service söks i indexet.

![http://oppna-program-semlink.googlecode.com/svn/wiki/images/modules1.jpg](http://oppna-program-semlink.googlecode.com/svn/wiki/images/modules1.jpg)

## 1. Harvester Service ##

Harvester servicen hanterar extraktionen och RDF konverteringen av html-sidor. Denna modul förbereder för ett gemensamt RDF storage där intern och extern data aggregeras. Harvester servicen har byggts med hjälp av Apertures harvester framework.

Aperture är ett open source harvester framework som fokuserar mot semantik och RDF. Framework:et stödjer bl a aggregering av data från:

  * Bibsonomy konton och extrahera bokmärken och taggar
  * Delicious konton och extrahera bokmärken och taggar
  * Filsystem och extrahera mappstrukturer
  * Flickr konton och extrahera taggar och fotometadata
  * Kalendrar i iCalendar format
  * Externa e-postlådor
  * Lokala e-postlådor
  * Webbsidor

Harvestern är konfigurerad att endast extrahera HTML-filer för att underlätta behovet av att lägga till RDF på sidorna. Harvestern kan extrahera meta data från html, PDF, word-dokument, bildfiler och ljudfiler.

Det bör nämnas att meta data bör i framtiden presenteras som RDF och anpassas efter de rekommendationer för att uttrycka Dublin Core som RDF (se bilaga). Eftersom detta tillåter ytterligare struktur på datan. Alternativt kan extra metadata hjälpa till.


### Harvest job ###
Den semantiska applikationen har en databas för olika källor som skall crawlas. Denna lista kommer att köras enligt en konfigurationsfil. Körningen kan konfigueras att exekveras exempelvis varje lördag klockan 06.00, varje dag klockan 17.00 eller första dagen i tredje månaden under en begränsad tid.
Listan på källor administreras via den en kontrollpanel i den semantiska applikationen.

### Harvest single job ###
Den semantiska applikationen är förberedd för att även externt hantera crawlerjobb för enskilda sidor. Exempelvis, då siterna är för många för att harvestern skall hinna crawla alla dokument inom ett visst tidsspann kan harvestern crawla alla källor under helger medan sidor som uppdateras under dagen körs dagligen. Denna funktion finns tillgänglig som en web service. I den redaktionella miljön gäller då att ett web service anrop lägger till en nyskapad eller redigerad sida i en arbetslista. Denna funktion existerar för att underlätta för harvestern och för att optimera harvesterkörningar.

### Översikt ###

![http://oppna-program-semlink.googlecode.com/svn/wiki/images/modules2.jpg](http://oppna-program-semlink.googlecode.com/svn/wiki/images/modules2.jpg)

#### **1. Request Jobs** ####
Harvestern hämtar site- och jobblistor för harvest.

#### **2. Extract content** ####
Harvestern extraherar och konverterar information. Under extraheringen kommer vissa sidor märkas som nya/modifierade/borttagna. I detta skede kommer de modifierade och borttagna sidorna sparas för att sedan kollas mot databasen med relaterade länkar och flagga länkar som inte längre existerar.

#### **3. Query Taxonomy** ####
Harvestern slår upp varje nyckelord i Taxonomin. Innan nyckelorden används körs de mot en stoppordlista. De ord som får 0 träffar i taxonomin kommer att sparas undan i en blacklist för att effektivisera frågorna mot taxonomin.

#### **4. Add Taxonomy terms** ####
Då Taxonomin får träff adderas information om dess MeSH id, föredragen term och synonymer.

Nedan visas ett exempel på sidan http://vard.vgregion.se/sv/Sjukdomar-och-besvar/Patientberattelser/ då harvestern extraherat, frågat taxonomin och lagt till information.

| Subjekt | Predikat | Objekt |
|:--------|:---------|:-------|
| <http:> | rdf:type | skos:Concept |
| <http:> | rdf:type | mesh:D000855 () |
| <http:> | rdf:type | mesh:D009369 () |
| <http:> | skos:altLabel | Anorexi |
| <http:> | skos:altLabel | Svulster |
| <http:> | skos:altLabel | Neoplasmer |
| <http:> | skos:altLabel | Cancer|
| <http:> | skos:prefLabel | Tumörer|
| <http:> | skos:prefLabel | Aptitlöshet|
| <http:> | nie:keyword | barnlöshet|
| <http:> | nie:keyword | patientberättelser|

Tabell 1. Pseudo RDF efter harvesting


## 2. Selection Service ##
För att undvika att råka ”missa” sidor i sökresultatet p g a avsaknaden av nyckelord eller bristfälliga nyckelord (till en början) kommer Selection Service modulen att sortera ut nyckelord, dc.titel och dc.description ur RDF repository. Dessa kommer sedan användas för Indexing Service för att indexera textsträngar i nyckelord, dc.titel och dc.description.
Genom att ta ut en större datamängd bör resultatet spegla verkligheten mer än att endast ta ut nyckelord. Det finns dock inget som säger att vi till en början ändå får en viss diskrepans mellan det förväntade resultatet och det som vi kommer att få.

## 3. Indexing Service ##
RDF databaser och SPARQL är ännu inte fullt optimerade såsom SQL och SQL databaser när det gäller snabbhet. För att kunna göra en fulltext sökning och för att effektivisera resultatet indexerar Indexing Service valda textsträngar i ett Lucene index.
Indexeringen görs på bl a skos:prefLabel, skos:altLabel, skos:hiddenLabel.

## 4. Query Service ##
För att mer effektivt söka i RDF har Lucenes sökmotor integrerats med ARQ (SPARQL motorn). Förutom en mer effektiv sökning kan Lucenes motor fulltext söka, mäta resultaten och filtrera ut på olika resultatträffar (ex. under 0.5).

## Harvest kontrollpanel ##
För att lägga till en sitelista över vilka siter som skall ingå i den semantiska tjänsten loggar man in på admin panelen.

![http://oppna-program-semlink.googlecode.com/svn/wiki/images/harvestermanager.jpg](http://oppna-program-semlink.googlecode.com/svn/wiki/images/harvestermanager.jpg)


  1. **"Site URL"** kommer anges som källa för dokumentet som harvestas. Detta används sedan för att gruppera de olika källorna. Urlen kommer sedan visas utan _"http://"_ och _"www"_.

> 2. **"StartURL"** anger vart harvestern skall börja extrahera information.

> 3. **"Harvestdjup"** anger hur djupt ner i länkarna harvestern skall ta sig. 0 indikerar att harvestern endast extraherar sidan som angetts utan att gå vidare. Exempelvis så tar sig harvestern, vid siffran 3, vidare på ett länkdjup av 3. Om man vill att harvestern skall gå vidare genom alla länkar anges -1.

> 4. **"Inkludera RegExp mönster"** betyder att man kan inkludera vissa sidor. Detta måste användas tillsammans med "Exkludera RegExp mönster".

> 5. **"Exkludera RegExp mönster"** betyder att man kan exkludera vissa sidor. Detta måste användas tillsammans med "Inkludera RegExp mönster".

> 6. Ändra eller ta bort källor från sitelistan.