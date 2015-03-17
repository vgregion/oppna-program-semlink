# Metadatatjänst och UMLS #

Metadatatjänsten använder sig av National Library of Medicine's kontrollerade vokabulär och thesaurus _MeSH_ och den svenska översättningen _SWEMeSH_. SWEMeSH innehåller inga relationer utan verkar endast som en översättning åt den engelska versionen.

**Semantic Network**
De noder som existerar i MeSH innhåller bl a egenskapen Semantic Type som beskriver ämneskategorier. Semantic Network innehåller dessutom relationer mellan dessa Semantic Types.
Semantic Type är inkluderat i MeSH men inte översatt i SWEMeSH. Ett exempel på en Semantic Type är ”Body Part, Organ, or Organ Component” som bl a termen  ”Heart” är kategoriserat som.

**Synonymer**
Noderna innerhåller även ett set av synonymer med hög eller låg grad av "bredd", _"Narrower"_ eller _"Broader"_. Dessa har även en ”preferred” synonym för alla noder.

**Parent/Child**
En nods "förälder" och dess undernoder.

**Exempel**
För att få ut relaterad information utifrån MeSH är det lämpligast att man söker på Parent/Child, Broader/Narrower och/eller undernoder för att få ut de relaterade koncept som senare kommer att användas i den semantiska applikationen. Bilden nedan visar en sökning på termen "Hammer Toe Syndrom" och detta koncepts position i hierarkin. Syskonen som omfattas av sökningen är i bilden gråmarkerad. Dessa begrepp kan sedan bl a användas som uppslag för applikationen.

MeSH innehåller interna relationer för att positionera ett koncept i en hierarki. Således kan man inte få ut relationer såsom att rökning orsakar cancer utan att utöka med fler vokabulär och mappningar. Fler vokabulär skulle kunna visa fler relationer såsom Other Related Concepts (OR) och Co-occuring Concepts (CC). Samma fråga hade exempelvis visat att relaterade koncept (i CC) under kategorin Anatomy. Dermis, Foot, Forefoot, Human, Forelimb, Hallux structure, mm och under kategorin Procedures: Amputation, mm.

[[\*bild\* ](.md)]


