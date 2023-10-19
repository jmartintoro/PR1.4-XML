import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class PR141Main {

    public static void main(String[] args) {
        try {
            // Crea una factoria de constructors de documents
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // Crea un constructor de documents
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Crea un nou document XML
            Document doc = db.newDocument();

            // Crea l'element root del document XML
            Element elmRoot = doc.createElement("biblioteca");
            // Afegeix l'element root al document XML
            doc.appendChild(elmRoot);
            // Crea l'element "llibre"
            Element elmllibre = doc.createElement("llibre");
            // Crea un atribut "id"
            Attr attrId = doc.createAttribute("id");
            // Estableix el valor de l'atribut "id"
            attrId.setValue("001");
            // Afegeix l'atribut "id" a l'element "llibre"
            elmllibre.setAttributeNode(attrId);

            // Crea l'element "titol"
            Element elmTitol = doc.createElement("titol");
            // Crea un node de text per al nom del destinatari
            Text nodeTextTitol = doc.createTextNode("Libro 001");
            // Afegeix el node de text a l'element "titol"
            elmTitol.appendChild(nodeTextTitol);
            // Afegeix els elements a l'element "llibre"
            elmllibre.appendChild(elmTitol);

            // Crea l'element "autor"
            Element elmAutor = doc.createElement("autor");
            // Crea un node de text per al nom del destinatari
            Text nodeTextAutor = doc.createTextNode("Pakito Remolacha");
            // Afegeix el node de text a l'element "autor"
            elmAutor.appendChild(nodeTextAutor);
            // Afegeix els elements a l'element "llibre"
            elmllibre.appendChild(elmAutor);

            // Crea l'element "anyPublicacio"
            Element elmAny = doc.createElement("anyPublicacio");
            // Crea un node de text per al nom del destinatari
            Text nodeTextAny = doc.createTextNode("2028");
            // Afegeix el node de text a l'element "anyPublicacio"
            elmAny.appendChild(nodeTextAny);
            // Afegeix els elements a l'element "llibre"
            elmllibre.appendChild(elmAny);

            // Crea l'element "editorial"
            Element elmEditorial = doc.createElement("editorial");
            // Crea un node de text per al nom del destinatari
            Text nodeTextEditorial = doc.createTextNode("Libros del Futuro");
            // Afegeix el node de text a l'element "editorial"
            elmEditorial.appendChild(nodeTextEditorial);
            // Afegeix els elements a l'element "llibre"
            elmllibre.appendChild(elmEditorial);

            // Crea l'element "genere"
            Element elmGenere = doc.createElement("genere");
            // Crea un node de text per al nom del destinatari
            Text nodeTextGenere = doc.createTextNode("Accio");
            // Afegeix el node de text a l'element "genere"
            elmGenere.appendChild(nodeTextGenere);
            // Afegeix els elements a l'element "llibre"
            elmllibre.appendChild(elmGenere);

            // Crea l'element "pagines"
            Element elmPagines = doc.createElement("pagines");
            // Crea un node de text per al nom del destinatari
            Text nodeTextPagines = doc.createTextNode("222");
            // Afegeix el node de text a l'element "pagines"
            elmPagines.appendChild(nodeTextPagines);
            // Afegeix els elements a l'element "llibre"
            elmllibre.appendChild(elmPagines);

            // Crea l'element "disponible"
            Element elmDisponible = doc.createElement("disponible");
            // Crea un node de text per al nom del destinatari
            Text nodeTextDisponible = doc.createTextNode("false");
            // Afegeix el node de text a l'element "disponible"
            elmDisponible.appendChild(nodeTextDisponible);
            // Afegeix els elements a l'element "llibre"
            elmllibre.appendChild(elmDisponible);
            
            // Afegeix l'element "llibre" a l'element root
            elmRoot.appendChild(elmllibre);

            write("./data/biblioteca.xml", doc);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void write (String path, Document doc) throws TransformerException, IOException {
        if (!new File(path).exists()) { new File(path).createNewFile(); }
        // Crea una factoria de transformadors XSLT
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        // Crea un transformador XSLT
        Transformer transformer = transformerFactory.newTransformer();
        // Estableix la propietat OMIT_XML_DECLARATION a "no" per no ometre la declaració XML del document XML resultant
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        // Estableix la propietat INDENT a "yes" per indentar el document XML resultant
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // Crea una instància de DOMSource a partir del document XML
        DOMSource source = new DOMSource(doc);
        // Crea una instància de StreamResult a partir del camí del fitxer XML
        StreamResult result = new StreamResult(new File(path));
        // Transforma el document XML especificat per source i escriu el document XML
        // resultant a l'objecte especificat per result
        transformer.transform(source, result);
    }

}
