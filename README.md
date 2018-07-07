# Semesteraufgabe

## Systembeschreibung: Filmdatenbank

Es soll ein System zur Verwaltung von Filmen und Watchlists erstellt werden. Mit dem System sollen
Filme mit verschiedenen Daten (wie Name, Produktionsjahr und Regisseur) erfasst, angezeigt und bearbeitet
werden können. Filme sollen außerdem als gesehen markiert werden und bewertet werden können
(z.B. auf einer Skala von 1 - 10). Schließlich soll der Benutzer Filme in Watchlists verwalten können.

 - **Technische Anforderungen:** Das System soll modular aufgebaut und leicht erweiterbar (z.B. um Serien) sein
 - **Freiwillige Zusatzaufgabe (schwer):** Filmbeschreibungen sollen aus einer Onlinefilmdatenbank importiert werden können.

## Dokumentation:

### GUI:

![GUI Diagram](GUI.png)

Der Router ist im Singleton-Muster implementiert und kann Objekte die Renderable implementieren im layout rendern.

Viele GUI-Komponenten wurden in FXML Geschrieben und haben einen internen Controller um auf Events zu reagieren / das Template mit Daten zu füllen.

## ToDo:

**Views**
 - [x] Login 
 - [x] Signup
 - [x] Adding new movie
 - [ ] Home
 	- [x] Events verkabeln
 	- [ ] Suche implementieren
 - [ ] Movie details view
 	- [ ] Implement rating / deleting from watchlist
 - [x] MovieCard
 	- [x] Click event verkabeln (set checked)
 	- [x] DoubleClick verkabeln (open Movie detail view)
 
**Methods in the persistence layer**
 - getWatchlist(user)
 - addToWatchlist(movie)
 - removeFromWatchlist(movie)
 - setWatched(movie, boolean watched)
 - setRating(movie, rating)
 - login(username, password)
 - register(username, password)
 
 The movies actors and persons in general are **not** stored in the DB but are pulled in real time from RT.
 