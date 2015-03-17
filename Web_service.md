# Web service #

Länkgeneratorn kommer att implementeras som en web service. Web servicen har följande tänkta ändamål:

## GetRelatedLinks ##
GetRelatedLinks är huvudoperationen där inputparametrar skickas från publiceringsverktyget och returnerar en lista på länkar och varför dessa länkar rekommenderas (ex. relationer och relevans)

**Inparametrar**
| **Namn** | **Typ** | **Förklaring** |
|:---------|:--------|:----------------|
| **keyword** | String | En String med keywords |

**Returvärde**
En Array av Linkobjekt - Link[.md](.md)

## AddPageLink ##
AddPageLink anropas av EpiServer då användaren valt x antal länkalternativ. Alternativen sparas i en MySQL databas. Föregående instanser av URL:en raderas.

**Inparametrar**
| **Namn** | **Typ** | **Förklaring** |
|:---------|:--------|:----------------|
| **referrer** | String | En String som är ”|” och ”,” separerad. ”|” är separering mellan objekt och ”,” mellan variabler |

**Returvärde**
Boolean (huruvida allt gått bra med databas hanteringen)


## GetPageLink ##
För att få ut relaterade länkar i sidan anropas GetPageLink som hämtar alla länkar tillhörande url X

**Inparametrar**
| **Namn** | **Typ** | **Förklaring** |
|:---------|:--------|:----------------|
| **url** | String | URL till sidan som refererar till länken, ex. http://www.vgregion.se |

**Returvärde**
En Array av PageLinkobjekt - PageLink[.md](.md)

## GetPageRDF ##

**Inparametrar**
| **Namn** | **Typ** | **Förklaring** |
|:---------|:--------|:----------------|
| **url** | String | URL till sidan som refererar till länken, ex. http://www.vgregion.se |

**Returvärde**
RDF


## AddJob ##
Då en sida redigeras eller skapas kan dokumenthanterarverktyget anropa AddJob för att meddela harvestern att en sida behövs harvestas.

**Inparametrar**
| **Namn** | **Typ** | **Förklaring** |
|:---------|:--------|:----------------|
| **job** | HarvesterJob | Skickar in ett HarvesterJob Objekt och lägger till dess attribut i databasen, ex. Site URL, Site Namn, Djup |

**Returvärde**
Inget


## CheckUrl ##
Går in i realtid och kollar så adresserna finns. Lägger till statuskod och statusmeddelande.

**Inparametrar**
| **Namn** | **Typ** | **Förklaring** |
|:---------|:--------|:----------------|
| **url** | String | Skickar in en url för att kontrollera status för de sparade länkarna |

**Returvärde**
Boolean