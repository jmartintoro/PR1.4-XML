import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PR140Main {

    public static void main(String[] args) {
        String file = "./data/persones.xml";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Crea un constructor de documents
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Analitza el fitxer XML
            Document doc = dBuilder.parse(file);

            // Normalitza l'element arrel del document
            doc.getDocumentElement().normalize();

            // Obté una llista de tots els elements "student" del document
            NodeList listaPersones = doc.getElementsByTagName("persona");

            String head = "|=====================================================|\n" + 
                          "|Nom            |Cognom         |Edat |Ciutat         |\n" + 
                          "|---------------+---------------+-----+---------------|";
            System.out.println(head);
            String formato = "|%-15s|%-15s|%-5s|%-15s|%n";

            for (int cnt = 0; cnt < listaPersones.getLength(); cnt++) {
                Node nodeEstudiant = listaPersones.item(cnt);
                // Comprova si l'estudiant actual és un element
                if (nodeEstudiant.getNodeType() == Node.ELEMENT_NODE) {

                    Element elm = (Element) nodeEstudiant;

                    String name = elm.getElementsByTagName("nom").item(0).getTextContent();

                    String cognom = elm.getElementsByTagName("cognom").item(0).getTextContent();

                    String edat = elm.getElementsByTagName("edat").item(0).getTextContent();

                    String ciutat = elm.getElementsByTagName("ciutat").item(0).getTextContent();

                    System.out.printf(formato, name, cognom, edat, ciutat);
                }
            }
            System.out.println("|=====================================================|");
        } catch(Exception e) {
            // Imprimeix la pila d'errors en cas d'excepció
            e.printStackTrace();
        }
        

    }
    
}
