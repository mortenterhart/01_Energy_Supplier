# Lambdas / Stream (LAS) - Energy Supplier

## Aufgabenstellung
Transformation von 12 SQL-Abfragen in Lambdas und Streams.

**Aufgabe:** **01 Energy Supplier** 

## Features
* CSV-Import der Datensätze aus `data/customers.csv`
* Anlegung eines Logs mit vollständigen SQL-Befehlen und ihren
  äquivalenten Lambda-Streams (`log/lambda_queries.log`)
* Ausführung von Lambda-Streams mit gleichen Ergebnissen wie
  SQL-Befehle
* Tests für Lambda-Streams mit Vergleich der Ergebnisse der
  SQL-Befehle

## Wichtige Hinweise:
* Implementierung einer technisch einwandfrei lauffähigen Applikation in Java 8.
* Test der Implementierung mit JUnit und Gewährleistung der Funktionsweise.
* Pro Team wird eine Aufgabe bearbeitet.
* Die Zuordnung einer Aufgabe zu einem Team erfolgt mit einem Zufallsgenerator.
* Nutzung der camelCase-Notation, um die Lesbarkeit zu vereinfachen.
* Zulässige externe Bibliotheken: junit*.jar, opentest4j.jar und hsqldb.jar.
* Verwendung geeigneter englischer Begriffe für Namen und Bezeichnungen.
* Erstellung einer vollständigen und verschlüsselten 7-Zip-Datei unter Beachtungdes Prozedere für die Abgabe von Prüfungsleistungen und der Namenskonvention.
* Zeitansatz: 5 Stunden
* Abgabetermin: Sonntag, 11.02.2018
* Bewertung: Testat

## Ergebnisse der SQL-Befehle
```sql
--- query 01 (count)
SELECT COUNT(*) FROM data
1000000  

--- query 02 (count, where)
SELECT COUNT(*) FROM data
    WHERE region = 'A'
    AND type = 'S'
38085  

--- query 03 (count, where, in)
SELECT COUNT(*) FROM data
    WHERE region = 'A'
    AND type IN ('S','L')
    AND energyConsumption0To6 >= 25
    AND energyConsumption0To6 <= 50
8757  

--- query 04 (count, where, not in)
SELECT COUNT(*) FROM data
    WHERE type NOT IN ('L','M')
    AND region = 'B'
    AND bonusLevel >= 2
    AND hasSmartTechnology = 'true'
    AND energyConsumption12To18 <= 25
1262  

--- query 05 (id, where, in, order by desc limit)
SELECT id FROM data
    WHERE region = 'A'
    AND type IN ('S','L')
    AND energyConsumption0To6 >= 25
    AND energyConsumption0To6 <= 50
    ORDER BY energyConsumption0To6 DESC LIMIT 3
602612  
330966  
329281  

--- query 06 (id, where, in, order by desc, order by asc)
SELECT id FROM data
    WHERE region = 'C'
    AND type IN ('K','L')
    AND bonusLevel <= 2
    AND hasSmartTechnology = 'true'
    AND energyConsumption0To6 <= 5
    AND energyConsumption6To12 >= 10
    AND energyConsumption6To12 <= 15
    ORDER BY bonusLevel DESC, energyConsumption6To12
11811  
472294  
371316  
811093  

--- query 07 (count, group by)
SELECT hasSmartTechnology, COUNT(*) FROM data
    GROUP BY hasSmartTechnology
false 750476  
true 249524  

--- query 08 (count, where, group by)
SELECT region, COUNT(*) FROM data
    WHERE energyConsumption0To6 <= 50
    GROUP BY region
C 31008  
B 27637  
F 28822  
A 31227  
E 28287  
G 30151  
D 28923  

--- query 09 (count, where, in, group by)
SELECT bonusLevel, COUNT(*) FROM data
    WHERE TYPE IN ('L','M')
    GROUP BY bonusLevel
1 167362  
3 166160  
2 166734  

--- query 10 (count, where, not in, group by)
SELECT region, COUNT(*) FROM data
    WHERE region NOT IN ('A','B')
    AND hasSmartTechnology = 'true'
    GROUP BY region
C 37449  
D 34839  
F 35225  
E 34253  
G 36693  

--- query 11 (sum, where, not in, in, group by)
SELECT hasSmartTechnology, SUM(energyConsumption6To12) FROM data
    WHERE region NOT IN ('B','C')
    AND bonusLevel IN (1,3)
    AND type = 'M'
    GROUP BY hasSmartTechnology
false 12290721  
true 4096222  

--- query 12 (avg, where, in, in, group by)
SELECT region, AVG(energyConsumption6To12) FROM data
    WHERE region IN ('A','C')
    AND type IN ('L','M')
    AND hasSmartTechnology = 'false'
    GROUP BY region
A 137  
C 137  
```
