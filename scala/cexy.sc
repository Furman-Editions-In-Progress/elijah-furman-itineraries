
import scala.io.Source
import edu.holycross.shot.scm._
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.seqcomp._
import edu.holycross.shot.dse._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.citerelation._
import scala.xml.XML

:load utilities.sc 

:load config/config.sc 

/* Class for holding one version of a text */
case class TextVec(catalog: String, text: String)


/*
		Maps the data in config/config.sc to a Vector[NodeSeq],
		each item of which is a sequence of the top-level citation
		nodes.
*/

val xmlTexts: Vector[ scala.xml.NodeSeq ] = {
	textConfig.map( tc => {
		val xml: scala.xml.Elem = XML.loadFile( tc.filePath )
		val allNodes: scala.xml.NodeSeq = xml
		val citationNodes: scala.xml.NodeSeq = {
		  tc.topNodes.foldLeft(allNodes)((e,s) => e \\ s)
		}
		citationNodes
	})
}

/* 

	Now we have, for each text, a Vector[NodeSeq] representing
	the top-level citation element. We can map that to a new structure,
	a proto-OHCO2 expression.

*/

// Clean-up leafNode
def cleanUpText(
	node: scala.xml.Node,
	omitNodes: Vector[String],
	replaceText: Vector[(String, String)] = forbiddenChars
): String = {
	val omittedNodes: Vector[String] = node.nonEmptyChildren.map( c => {
		val thisElementName: String = c.label
		if (omitNodes.contains(thisElementName)) ""
		else c.text.toString
	}).toVector

	val nodeString: String = omittedNodes.mkString(" ")

	// here we use recursion to work through the
	// 'replaceText' list.

	def replaceStuff(s: String, replaceTupleVec: Vector[(String, String)]): String = {

		@tailrec def replaceRecurse( s: String, replaceTupleVec: Vector[(String, String)], limit: Int, n: Int): String = {

			if (n < limit) {
				val findString: String = replaceTupleVec(n)._1
				val replaceString: String = replaceTupleVec(n)._2
				val newS: String = s.replaceAll(findString,replaceString)
				val newN: Int = n + 1
				replaceRecurse( newS, replaceTupleVec, limit, newN)
			} else { // last one!
				val findString: String = replaceTupleVec(n)._1
				val replaceString: String = replaceTupleVec(n)._2
				val newS: String = s.replaceAll(findString,replaceString)
				newS
			}

		}
		val limit: Int = replaceTupleVec.size - 1
		replaceRecurse(s, replaceTupleVec, limit, 0)
	}

	replaceStuff(nodeString, replaceText).trim

}

// Map for processing one leaf-node
def getPlainTextLeafNode( 
	leafNode: scala.xml.Node, 
	omitNodes: Vector[String],
	replaceText: Vector[(String,String)] = forbiddenChars
): String = {
		cleanUpText(leafNode, omitNodes)
}

// Process all leaf nodes for a text
// N.b. we use just a counter for the 2nd-level citations 
// because of inconsistent attributes on the <seg> elements.
def getLeafNodes(
	topLevelCitation: String,
	leafNodes: scala.xml.NodeSeq,
	omitNodes: Vector[String],
	stripId: String = """(GeoNar|GN1S|intro)""", 
	replaceText: Vector[(String,String)] = forbiddenChars
): Vector[String]= {
	leafNodes.zipWithIndex.map( n_i => {
		val n: scala.xml.Node = n_i._1
		val i: String = (n_i._2 + 1).toString
		val citation: String = n.attributes.head.value.toString.replaceAll(stripId,"")
		val cexText: String = getPlainTextLeafNode(n, omitNodes)
		s"${topLevelCitation}.${citation}#${cexText}"
	}).toVector
}

// Map for processing one text
def getText(
	nodeSeq: scala.xml.NodeSeq,
	leafNodeType: String,
	stripId: String = """(GeoNar|GN[0-9]S|intro)""",
	omitNodes: Vector[String] = Vector("note")
): Vector[String] = {

	nodeSeq.map( n => {
		val citation: String = n.attributes.head.value.toString.replaceAll(stripId,"")
		val leafNodes: scala.xml.NodeSeq = {
			n \\ leafNodeType
		}
		getLeafNodes(citation, leafNodes, omitNodes, stripId)
	}).toVector.flatten

}

// Process Catalog info for one text
def getCatalog(
	config: CexyConfig,
	teiVersion: Boolean = false,
	teiVersionExt: String = " TEI-XML version.",
	teiUrnExt: String = "_tei"
): String = {

	val newVersion = {
		if (teiVersion) s"${config.version}${teiVersionExt}"
		else config.version
	}

	val newUrn = {
		if (teiVersion) s"${config.urnString}${teiUrnExt}"
		else config.urnString
	}

	val header = "#!ctscatalog\nurn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online#lang"
	val entry: String = Vector(
		newUrn,
		config.citation,
		config.textGroup,
		config.work,
		newVersion,
		config.exemplar,
		config.online,
		config.lang
	).mkString("#")
	header + "\n" + entry + "\n\n"
}

def processAllTexts(
	config: Vector[CexyConfig] = textConfig,
	header: String = cexHeader,
	swapChars: Vector[(String, String)] = forbiddenChars
): String = {

	val allTexts: Vector[scala.xml.NodeSeq] = config.map( tc => {
		val xml: scala.xml.Elem = XML.loadFile( tc.filePath )
		val allNodes: scala.xml.NodeSeq = xml
		val citationNodes: scala.xml.NodeSeq = {
		  tc.topNodes.foldLeft(allNodes)((e,s) => e \\ s)
		}
		citationNodes
	})

	println(allTexts.size)

	val allCatalogs: Vector[String] = {
		config.map( c => {
			getCatalog(c)
		})
	}

	val allTextCex: Vector[String] = {
		allTexts.zipWithIndex.map( ti => {
			val t: scala.xml.NodeSeq = ti._1
			val i: Int = ti._2
			val justAText: Vector[String] = {
				getText(t, config(i).otherNodes, config(i).stripFromIDs, config(i).omitNodes )
			}
			val addedUrns: Vector[String] = {
				justAText.map( t => {
					config(i).urnString + t
				})
			}
			val sv: Vector[String] = Vector("\n\n#!ctsdata") ++ addedUrns ++ Vector("\n")
			sv.mkString("\n")
		})
	}

	val saveCexStr: String = {
		(Vector(header) ++ allCatalogs ++ allTextCex).mkString("\n\n")
	}

	showMe(saveCexStr)

	saveCexStr

}

val finalCex = processAllTexts()

saveString(finalCex, "cex/", "test.cex")
