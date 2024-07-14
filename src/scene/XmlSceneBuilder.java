package scene;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import primitives.Color;
import primitives.Point;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * This class is responsible for building a scene from an XML file.
 * It provides a static method to create a Scene object based on the XML configuration.
 *
 * @author Amit and Yinon
 */
public class XmlSceneBuilder {

    /**
     * Builds a Scene object from an XML file.
     *
     * @param sceneName The name of the scene to be created
     * @param xmlPath The file path of the XML configuration
     * @return A new Scene object configured according to the XML file
     */
    public static Scene buildScene(String sceneName, String xmlPath) {
        Scene scene = new Scene(sceneName);
        try {
            Document document = loadXmlDocument(xmlPath);
            parseScene(document, scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scene;
    }

    /**
     * Loads and parses the XML document from the given file path.
     *
     * @param filePath The path to the XML file
     * @return A Document object representing the parsed XML
     * @throws Exception If there's an error in loading or parsing the XML
     */
    private static Document loadXmlDocument(String filePath) throws Exception {
        File inputFile = new File(filePath);
        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
        Document document = dBuilder.parse(inputFile);
        document.getDocumentElement().normalize();
        return document;
    }

    /**
     * Parses the main scene element from the XML document and configures the Scene object.
     *
     * @param document The parsed XML document
     * @param scene The Scene object to be configured
     */
    private static void parseScene(Document document, Scene scene) {
        NodeList sceneNodes = document.getElementsByTagName("scene");
        Node sceneNode = sceneNodes.item(0);
        if (sceneNode.getNodeType() == Node.ELEMENT_NODE) {
            Element sceneElement = (Element) sceneNode;
            setSceneBackground(sceneElement, scene);
            parseSceneChildren(sceneElement, scene);
        }
    }

    /**
     * Sets the background color of the scene based on the XML configuration.
     *
     * @param sceneElement The XML element containing scene information
     * @param scene The Scene object to be configured
     */
    private static void setSceneBackground(Element sceneElement, Scene scene) {
        if (sceneElement.hasAttribute("background-color")) {
            String[] bgColorValues = sceneElement.getAttribute("background-color").split(" ");
            Color backgroundColor = new Color(
                    Double.parseDouble(bgColorValues[0]),
                    Double.parseDouble(bgColorValues[1]),
                    Double.parseDouble(bgColorValues[2])
            );
            scene.setBackground(backgroundColor);
        }
    }

    /**
     * Parses and processes child elements of the scene (ambient light and geometries).
     *
     * @param sceneElement The XML element containing scene information
     * @param scene The Scene object to be configured
     */
    private static void parseSceneChildren(Element sceneElement, Scene scene) {
        NodeList sceneChildNodes = sceneElement.getChildNodes();
        for (int j = 0; j < sceneChildNodes.getLength(); j++) {
            Node childNode = sceneChildNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                switch (childElement.getTagName()) {
                    case "ambient-light":
                        setAmbientLight(childElement, scene);
                        break;
                    case "geometries":
                        parseGeometries(childElement, scene);
                        break;
                }
            }
        }
    }

    /**
     * Sets the ambient light of the scene based on the XML configuration.
     *
     * @param ambientElement The XML element containing ambient light information
     * @param scene The Scene object to be configured
     */
    private static void setAmbientLight(Element ambientElement, Scene scene) {
        String[] ambientColorValues = ambientElement.getAttribute("color").split(" ");
        Color ambientColor = new Color(
                Double.parseDouble(ambientColorValues[0]),
                Double.parseDouble(ambientColorValues[1]),
                Double.parseDouble(ambientColorValues[2])
        );
        scene.setAmbientLight(new AmbientLight(ambientColor, 1));
    }

    /**
     * Parses and adds geometries (spheres and triangles) to the scene.
     *
     * @param geometriesElement The XML element containing geometries information
     * @param scene The Scene object to be configured
     */
    private static void parseGeometries(Element geometriesElement, Scene scene) {
        NodeList geometryList = geometriesElement.getChildNodes();
        for (int k = 0; k < geometryList.getLength(); k++) {
            Node geometryNode = geometryList.item(k);
            if (geometryNode.getNodeType() == Node.ELEMENT_NODE) {
                Element geometryElement = (Element) geometryNode;
                switch (geometryElement.getTagName()) {
                    case "sphere":
                        addSphere(geometryElement, scene);
                        break;
                    case "triangle":
                        addTriangle(geometryElement, scene);
                        break;
                }
            }
        }
    }

    /**
     * Adds a sphere to the scene based on the XML configuration.
     *
     * @param sphereElement The XML element containing sphere information
     * @param scene The Scene object to which the sphere will be added
     */
    private static void addSphere(Element sphereElement, Scene scene) {
        Sphere sphere = new Sphere(
                parsePoint(sphereElement.getAttribute("center")),
                Double.parseDouble(sphereElement.getAttribute("radius")));
        scene.geometries.add(sphere);
    }

    /**
     * Adds a triangle to the scene based on the XML configuration.
     *
     * @param triangleElement The XML element containing triangle information
     * @param scene The Scene object to which the triangle will be added
     */
    private static void addTriangle(Element triangleElement, Scene scene) {
        Point p0 = parsePoint(triangleElement.getAttribute("p0"));
        Point p1 = parsePoint(triangleElement.getAttribute("p1"));
        Point p2 = parsePoint(triangleElement.getAttribute("p2"));
        Triangle triangle = new Triangle(p0, p1, p2);
        scene.geometries.add(triangle);
    }

    /**
     * Parses a string representation of a point into a Point object.
     *
     * @param pointStr The string representation of a point (format: "x y z")
     * @return A new Point object
     */
    private static Point parsePoint(String pointStr) {
        String[] values = pointStr.split(" ");
        return new Point(
                Double.parseDouble(values[0]),
                Double.parseDouble(values[1]),
                Double.parseDouble(values[2])
        );
    }
}