# Aligning Hebrew: final workshop for the New Classics Series 

## Sinai Rusinek

Jewish travel narratives have a long historical tradition. Their geographical data is extremely important to the understanding of the spatial and cultural history of early Jewish communities. This double dimension between geography and storytelling can ideally be investigated with digital and interdisciplinary methods. This is the main purpose of the TraveLab project at Haifa.

The itinerary of Benjamin of Tudela is more than a pilgrimage: it is like the Marco Polo of Jewish travelogues. It has been translated in several languages, and the project is going to publish a multilingual digital edition in English, Hebrew, and Arabic. While the Arabic version was already available in digital format in MarkDown through OpenITI, and the English edition was already available in XML format in the Gutenberg Library, the Hebrew was digitized independently at Haifa using Transkribus. 
The Hebrew original was annotated and georeferenced using Recogito. Kima, the historical gazetteer of Hebrew sources, was used as an authoritative reference to classify the placenames in the text and associate them to additional information.

The digital edition that will be created from this project will be a conversation between the spatial and the textual, integrating the geographical aspect of the itinerary and its textual narrative. 

The main issue with digitizing an early Hebrew source is to determine the citation structure of the text, so that there is a stable way of identifying equal portions of texts in the digital world. This is also tremendously important when it comes to mapping different versions of the text, including translations, so that the computer can identify which part of the text in one edition corresponds to the same part in another edition.

The text of Benjamin of Tudela was structured in XML on the basis of its geographical components: the different geographical sections of the itinerary were identified as "geonarratives" and numbered in sequence. The structure of the original text in Hebrew was then mapped to the English and to the Arabic translation with the XML Planter (xmlplanter.dh-dev.com).

## Christopher Blackwell

The mapped editions created by Sinai and her colleagues constitute now a canonical citation scheme of Benjamin of Tudela, where every part of the text has a unique name and a recognizable, stable structure.

Moreover, the XML edition of Benjamin of Tudela marks up important components of the text, such as personal names and place names. At the Classics Department at Furman, we used these texts for a few different alignment experiments.

The XML citable texts were converted into CTS, a standard canonical citation format previously used for the Homer Multitext Project (the way in which we converted XML into CTS formats is described in the notes to the previous meetings).

We then ingested the new texts into the CITE Environment, a digital platform for the exploration of texts in parallel editions (including different languages). The CITE Environment gives us a good look into how a marked XML edition would look like: the place names and personal names, previously marked up in the complicated XML editor, are now highlighted in different colors so that even reader who do not know the language can recognize them in the text at the first look.

We also used the CITE Environment to do some distant reading explorations across our new corpus. For example, we looked into n-grams, recurring groups of words across the different versions of the text. The Environment can also redirect us to the parts of the text where these expressions occur, and we can use the CTS structure to retrieve the corresponding passages in other editions, to verify if the same expression occurs in the same way across the different translations of the text (hint: it not always does).

A citation scheme can be arbitrarily granular: it can certainly limit itself to individual sections and chapters of a text, but as philologists, our analysis typically goes to the level of the single word. So, we created a tokenized version of our texts, were each single word is associated to a unique citation format. We can then use those tokenized files to align individual words and group of words across translations. This is particularly useful where manual markup was completed for only one version/language of the text, as it is in our case: by using digital alignment, readers and editors can map important words (or all words) across different versions of a text.

In order to perform text alignment for this workshop, we used the DUCAT "Daughter of Ugarit Citation Alignment Tool" developed by Chris Blackwell at Furman.


## Aseel Sharara

Aseel shows us how we can manually establish correspondences across different translations of our itinerary, particularly focusing on placenames across Hebrew, Arabic, and English.

The alignments can then be downloaded as a citable index of aligned words. They can either be ingested in a notebook for further manipulation or they can be re-uploaded into the text alignment environment.  


## Sinai Rusinek

After aligning the placenames across languages, we can further enrich our vocabulary of placenames with references to them in Arabic, Hebrew, and English. This is ideal for enriching existing gazetteers and digital atlases with new references from primary sources.


## Chris Blackwell

Since placenames are very clearly identified in the Hebrew text through Recogito georeferencing, it is possible to use them to create a citable collection of "places mentioned in Benjamin of Tudela", where we can visualize all the information associated to placename references in the text, including geographical coordinates. From this simple markup in the Hebrew, we can harvest all information about placenames, and recognize the passages where the place is mentioned in the original Hebrew, and then retrieve the mapped sections across different editions.


