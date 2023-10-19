import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class PR142Main {
    public static void main(String[] args) {
        String filePath = "data/cursos.xml";

        // Cargar el archivo XML
        File inputFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            // Crear un objeto XPath
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            System.out.println("1 - Llistar ids de cursos, tutors i total d'alumnes.");
            funcion1(doc, xpath);

            System.out.println("2 - Mostrar ids i titols dels mòduls a partir d'un id de curs.");
            String cursoID = "AWS1";
            funcion2(doc, xpath, cursoID);

            System.out.println("3 - Llistar alumnes d'un curs.");
            cursoID = "AWS1";
            funcion3(doc, xpath, cursoID);

            System.out.println("4 - Afegir un alumne a un curs.");
            cursoID = "AWS1";
            String nuevoAlumno = "MESSI, Leo";
            funcion4(doc, xpath, cursoID, nuevoAlumno, filePath);

            System.out.println("5 - Eliminar un alumne d'un curs.");
            String alumnoAEliminar = "VAZQUEZ, Albert";
            funcion5(doc, xpath, cursoID, alumnoAEliminar, filePath);


        } catch (Exception e) { e.printStackTrace(); } 
    }

    private static void funcion5(Document doc, XPath xpath, String cursoID, String alumnoAEliminar, String filePath) {
        try {
            NodeList cursos = doc.getElementsByTagName("curs");
            for (int i = 0; i < cursos.getLength(); i++) {
                Element curso = (Element) cursos.item(i);
                String id = curso.getAttribute("id");

                if (cursoID.equals(id)) {
                    // Obtén la lista de alumnos en el curso
                    Element alumnes = (Element) curso.getElementsByTagName("alumnes").item(0);

                    // Obtén la lista de alumnos
                    NodeList alumnos = alumnes.getElementsByTagName("alumne");

                    for (int j = 0; j < alumnos.getLength(); j++) {
                        Element alumno = (Element) alumnos.item(j);
                        String nombreAlumno = alumno.getTextContent();

                        if (nombreAlumno.equals(alumnoAEliminar)) {
                            // Elimina el nodo del alumno
                            alumnes.removeChild(alumno);

                            // Guarda los cambios en el archivo XML
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            DOMSource source = new DOMSource(doc);
                            StreamResult result = new StreamResult(new File(filePath));
                            transformer.transform(source, result);

                            System.out.println("Alumno '" + alumnoAEliminar + "' eliminado del curso con ID: " + cursoID);
                            return; // Sal del bucle si se eliminó el alumno
                        }
                    }

                    System.out.println("No se encontró el alumno '" + alumnoAEliminar + "' en el curso con ID: " + cursoID);
                }
            }
        } catch (Exception e) { e.printStackTrace(); } 
    }

    private static void funcion4(Document doc, XPath xpath, String cursoID, String nuevoAlumno, String filePath) {
        try {
        NodeList cursos = doc.getElementsByTagName("curs");
            for (int i = 0; i < cursos.getLength(); i++) {
                Element curso = (Element) cursos.item(i);
                String id = curso.getAttribute("id");

                if (cursoID.equals(id)) {
                    // Agregar un nuevo nodo de alumno
                    Element alumnes = (Element) curso.getElementsByTagName("alumnes").item(0);
                    Element alumne = doc.createElement("alumne");
                    alumne.appendChild(doc.createTextNode(nuevoAlumno));
                    alumnes.appendChild(alumne);

                    // Guardar los cambios en el archivo XML
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(filePath));
                    transformer.transform(source, result);

                    System.out.println("Alumno '" + nuevoAlumno + "' agregado al curso con ID: " + cursoID);
                    System.out.println();
                }
            }
        } catch (Exception e) { e.printStackTrace(); } 
    }

    private static void funcion3(Document doc, XPath xpath, String cursoID) {
        try {
            String xpathExpression = "/cursos/curs[@id='" + cursoID + "']/alumnes/alumne";
            XPathExpression alumneExpr = xpath.compile(xpathExpression);
            NodeList alumnes = (NodeList) alumneExpr.evaluate(doc, XPathConstants.NODESET);

            if (alumnes.getLength() == 0) {
                System.out.println("No se encontraron alumnos para el curso con ID: " + cursoID);
            } else {
                System.out.println(" Alumnos para el curso con ID: " + cursoID);
                for (int i = 0; i < alumnes.getLength(); i++) {
                    Node alumne = alumnes.item(i);
                    String nombreAlumno = alumne.getTextContent();
                    System.out.println("  - " + nombreAlumno);
                }
                System.out.println();
            }

        } catch (Exception e) { e.printStackTrace(); } 
    }

    private static void funcion2(Document doc, XPath xpath, String cursoID) {
        try {

           // Usar XPath para obtener los módulos de un curso específico
           String xpathExpression = "/cursos/curs[@id='" + cursoID + "']/moduls/modul";
           XPathExpression modulExpr = xpath.compile(xpathExpression);
           NodeList moduls = (NodeList) modulExpr.evaluate(doc, XPathConstants.NODESET);

           if (moduls.getLength() == 0) {
               System.out.println("No se encontraron módulos para el curso con ID: " + cursoID);
           } else {
               System.out.println("Módulos para el curso con ID: " + cursoID);
               for (int i = 0; i < moduls.getLength(); i++) {
                   Node modul = moduls.item(i);
                   String modulID = modul.getAttributes().getNamedItem("id").getNodeValue();
                   String modulTitol = xpath.compile("titol").evaluate(modul);
                   System.out.println("ID del módulo: " + modulID);
                   System.out.println("Título del módulo: " + modulTitol);
                   System.out.println();
               }
           }
        } catch (Exception e) { e.printStackTrace(); } 
    }

    private static void funcion1(Document doc, XPath xpath) {
        try {
            // Usar XPath para obtener los IDs de los cursos, tutores y el total de alumnos
            XPathExpression cursoExpr = xpath.compile("/cursos/curs");
            NodeList cursos = (NodeList) cursoExpr.evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < cursos.getLength(); i++) {
                String id = cursos.item(i).getAttributes().getNamedItem("id").getNodeValue();
                String tutor = ((org.w3c.dom.Element) cursos.item(i)).getElementsByTagName("tutor").item(0).getTextContent();
                NodeList alumnes = ((org.w3c.dom.Element) cursos.item(i)).getElementsByTagName("alumne");
                int totalAlumnes = alumnes.getLength();

                System.out.println("ID del curso: " + id);
                System.out.println("Tutor del curso: " + tutor);
                System.out.println("Total de alumnos: " + totalAlumnes);
                System.out.println();
            }
        } catch (Exception e) { e.printStackTrace(); } 
    }
}
