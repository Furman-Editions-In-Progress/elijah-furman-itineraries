
import scala.io.Source
import edu.holycross.shot.scm._
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.seqcomp._
import edu.holycross.shot.dse._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.citerelation._

:load utilities.sc 

def loadLibrary(fp:String = "cex/hmt-2020i.cex"): CiteLibrary = {
	val library = CiteLibrary(Source.fromFile(fp).getLines.mkString("\n"))
	library
}

val greekUrn = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:10")
val englishUrn = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.due_ebbott:10")

lazy val lib: CiteLibrary = loadLibrary("cex/short_test.cex")
lazy val tr: TextRepository = lib.textRepository.get
lazy val corp: Corpus = tr.corpus
lazy val cat: Catalog = tr.catalog
lazy val colls: CiteCollectionRepository = lib.collectionRepository.get
lazy val rels: CiteRelationSet = lib.relationSet.get

val corp: Corpus = lib.textRepository.get.corpus

val greekCorpus: Corpus = (corp ~~ greekUrn)
val englishCorpus: Corpus = (corp ~~ englishUrn)

val dseVec: DseVector = DseVector.fromCiteLibrary(lib)

val testDse = dseVec.tbsForText(CtsUrn("urn:cts:greekLit:tlg0012.tlg001.msA:10.2"))


def urnToFacsUrl( urn: Cite2Urn ): String = {
	val prefix = "https://www.homermultitext.org/facsimiles/venetus-a-2020/pages/"
	val urnString: String = {
		urn.toString.replaceAll(":","_")
		            .replaceAll("\\.","-")
	}
	s"${prefix}${urnString}.html"
}

