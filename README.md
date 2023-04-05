# ArchivedWebAnalysis

This repository contains code for analysing material from the Internet Archive.
This analysis is part of my masters thesis in History at Aarhus University.

## Organisation of repository
* The code that creates the figures presented in my thesis is inside the src/main/java/org.vicventures directory.
* The figures created by the code, which are also presented in the thesis are available in the directory src/main/resources/output.
* The data that this analysis has been made on has not been made publicly available, but can be gathered by using the Wayback Machine Downloader as described in the methods chapter of the thesis.

## Requirements
To run this code and analysis locally you would need to have the following:
* JDK 19
* An IDE (Eg: IntelliJ IDEA or Eclipse)
* Data from Internet Archive collected with [Wayback Machine Downloader](https://github.com/hartator/wayback-machine-downloader)

## What does the code do?
There are some different java files (classes) in the analysis. In the following section I will describe the content of these en broader terms.
The class ```DataVisualisation``` does the drawing of the figures. This class contains methods that packs input data into graphs.
Then there are the classes ```DataLoader``` and ```DataTranformer```.
These classes contain methods that loads, transforms and analyses specific parts of the input data.
The analysis and counting of different filetypes present in the dataset also happens in these classes.

The class ```DataToAndFromDisk``` reads and writes files to and from the disk and is used to make different steps of the analysis more efficient, by saving some results and then reading them instead of calculating it all again.

TODO: 
Describe HtmlTagCounter and Main (Main should probably be deleted).
