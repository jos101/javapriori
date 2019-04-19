---
title: 'Javapriori: a java tool to get frequent items and association rules using the apriori algorithm.'
tags:
  - data mining
  - frequent items
  - market basket analysis
  - association rules 
authors:
 - name: Jose Bolaño
   orcid: 0000-0003-0299-2659
   affiliation: "1"
affiliations:
 - name: Universidad de Cartagena Colombia, Facultad de Ingenierí 
   index: 1
date: 19 April 2019
bibliography: paper.bib
---

```Background```
Apriori [@apriori] is a data mining algorithm focusing to obtain the proper 
association rules in a set of transactions. Where transactions represent the 
registration of a purchased done by a visitor in a basket. Market Basket Analysis 
is where this algorithm belongs; other techniques with the same goal are FP-Tree 
[Han2004] orFP-Growth [@borgelt]. Then the field of research initiated in the 
market field but has been useful in other fields like human resource management 
or entrepreneurship as explained in [@market].

Apriori base his analysis using two variables given in percentage, the support 
and the confidences. The support defines which items are taken as frequents 
over the total number of transactions. The confidence is a percentage that 
determines an association rule. The association rule is valid if the relation 
between number of appearances of the precedent and consequence in a transaction 
over the number of transactions in which the precedent appears are equal or 
greater than the confidence given.

```The java implemantation```

``Javapriori`` is a java tool that implements the apriori algorithm over a 
YML o Text file. The results of frequent items and the association rules are 
shown in the command line or export in a JSON or ``traditional`` format.

The ``traditional`` format is represented with the id of the items represented
in every row.

```traditional format
1 3 4
2 3 5 
1 2 3 5 
2
```

The YAML format represents the tuple "transaction id" and "item id". In this example
the first transaction is represented with the id 100 and the second with the id 200 
and so on.

```
## YAML Template.
---
tid: 100
item: 1
---
tid: 100
item: 3
---
tid: 100
item: 4
---
tid: 200
item: 2
---
tid: 200
item: 3
---
tid: 200
item: 5
.
.
.
```


This implementation uses the library Yamlbeans (https://github.com/EsotericSoftware/yamlbeans) 
to serialize data from a YML file, however a text format may be used as well.

``Features and funtionality ``
The ``Javapriori`` tool allows to specify the behaivor via the parameters of the command line.
the following example execute it over a text file with a 80% of confidence and 20% of support. 

```
 java -jar Javapriori.jar  -t -c 80 -s 20 -o output.txt transaction.txt
```


In this one the input format YML is dictated by the absence of the flag -t and aditionaly 
the output in JSON format is  given by the flag -json

* java -jar Javapriori.jar  -c 80 -s 20 -json -o output.json transaction.yml


```Porpuse```

The ``Javapriori`` was created with the intention to give researchers the 
possibility to analyse data stored in a text or yaml file. Also to export the 
results in the desired representation. YML and Jason may be useful to integrate 
in an API, whereas the raw format to isolated  research. 


# References