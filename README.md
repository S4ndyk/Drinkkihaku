# Drinkkihaku
tikape-k18

## Tehtäviä jäljellä:
* Drinkin lisäys toiminto
	* Santtu ainakin osittain
	* jos raaka-aineet tulee luoda ensin, siitä pitää huomauttaa
	* Raaka-aineen luominen tälle sivulle // runko toimii 
		* Aatu
		* Alkoholiprosentti mukaan
                * RaakaAine-taulu on muotoa id, nimi, alkoholiprosentti (double)
                * Aakkosjärjestestys yms. olisi hieno
	* raaka-aineet lisätään yksi kerrallaan ja näkyvät sivun yläosassa
		* raaka-aineet listassa aakkosjärjestyksessä!
		* määrä
		* lisää nappi
	* tallennusnappi
	* lopuksi tarvitaan poista-nappi raaka-aineelle jos käyttäjä mokaa
* Drinkin teko-ohjeen siirto Drinkki-tauluun
	* DrinkkiRaakaAine uusiksi ilman ohjetta
	* DrinkkiRaakaAineDAO vastaamaan muutosta
	* Drinkki-tauluun ohjekenttä
	* DrinkkiDAO
* Haku-toiminnon toteuttaminen
	* Dennis
	* case-insensitive
	* DrinkkiDAOon metodi List<Drinkki> findByName();
	* DrinkkiDAOon hakumetodi
* Alkoholiprosentti
	* Drinkki-luokassa metodi List<DrinkkiRaakaAine> getAinesosat();
	* Drinkki-luokassa metodi getAlkoholiProsentti();
* Drinkkien omat sivut
	* Eero
	* Ainekset
	* Ohje
	* Arvostelut JOS tekstiarvosteluja
		* toteutetaan mahdollisesti lopuksi
		* ei poisteta kenttää