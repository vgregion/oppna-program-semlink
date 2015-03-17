# EpiServer plugin #


Användaren måste göra en medveten aktiv handling för att aktivera hitta relaterade artiklar/dokument. Detta görs genom att klicka på en flik.


![http://oppna-program-semlink.googlecode.com/svn/wiki/images/epi_plugin_nav.jpg](http://oppna-program-semlink.googlecode.com/svn/wiki/images/epi_plugin_nav.jpg)


EpiServer anropar web servicen som skickar tillbaka en lista på förslag. Då användaren valt att finna relaterade länkar kommer dessa grafiskt separeras genom huvudkällan.
Denna separering görs redan vid harvesting, då användaren specificerar vilka informationskällor som skall användas.

Då användaren väljer ett godtyckligt antal relaterade länkar och klickar på spara körs ett web service anrop som i sin tur lägger till länkarna i en databas (MySQL databas). Denna information kommer sedan användas för att visa relaterade länkar på sidan.

![http://oppna-program-semlink.googlecode.com/svn/wiki/images/epi_plug.jpg](http://oppna-program-semlink.googlecode.com/svn/wiki/images/epi_plug.jpg)

Då användaren vill se huruvida det tillkommit fler relaterade länkar klickar denne på  samma flik och ett web service anrop frågar huruvida det finns nya tillgängliga länkar. Användaren får återigen göra ett val.