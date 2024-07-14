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
 * Authors: Amit and Yinon
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
            Document document = loadXmlDocument(xmlPath);  // Load the XML document from the file path
            parseScene(document, scene);  // Parse the document and configure the scene
        } catch (Exception e) {
            e.printStackTrace();  // Print any exceptions that occur
        }
        return scene;  // Return the configured scene
    }

    /**
     * Loads and parses the XML document from the given file path.
     *
     * @param filePath The path to the XML file
     * @return A Document object representing the parsed XML
     * @throws Exception If there's an error in loading or parsing the XML
     */
    private static Document loadXmlDocument(String filePath) throws Exception {
        File inputFile = new File(filePath);  // Create a File object for the input file
        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();  // Create a DocumentBuilderFactory
        DocumentBuilder dBuilder = dFactory.newDocumentBuilder();  // Create a DocumentBuilder
        Document document = dBuilder.parse(inputFile);  // Parse the input file to create a Document
        document.getDocumentElement().normalize();  // Normalize the document
        return document;  // Return the Document
    }

    /**
     * Parses the main scene element from the XML document and configures the Scene object.
     *
     * @param document The parsed XML document
     * @param scene The Scene object to be configured
     */
    private static void parseScene(Document document, Scene scene) {
        NodeList sceneNodes = document.getElementsByTagName("scene");  // Get the list of scene elements
        Node sceneNode = sceneNodes.item(0);  // Get the first scene element
        if (sceneNode.getNodeType() == Node.ELEMENT_NODE) {  // Check if the node is an element
            Element sceneElement = (Element) sceneNode;  // Cast the node to an element
            setSceneBackground(sceneElement, scene);  // Set the background color of the scene
            parseSceneChildren(sceneElement, scene);  // Parse and process the child elements of the scene
        }
    }

    /**
     * Sets the background color of the scene based on the XML configuration.
     *
     * @param sceneElement The XML element containing scene information
     * @param scene The Scene object to be configured
     */
    private static void setSceneBackground(Element sceneElement, Scene scene) {
        if (sceneElement.hasAttribute("background-color")) {  // Check if the background-color attribute exists
            String[] bgColorValues = sceneElement.getAttribute("background-color").split(" ");
            // Split the color values
            Color backgroundColor = new Color(  // Create a new Color object with the parsed values
                    Double.parseDouble(bgColorValues[0]),
                    Double.parseDouble(bgColorValues[1]),
                    Double.parseDouble(bgColorValues[2])
            );
            scene.setBackground(backgroundColor);  // Set the background color of the scene
        }
    }

    /**
     * Parses and processes child elements of the scene (ambient light and geometries).
     *
     * @param sceneElement The XML element containing scene information
     * @param scene The Scene object to be configured
     */
    private static void parseSceneChildren(Element sceneElement, Scene scene) {
        NodeList sceneChildNodes = sceneElement.getChildNodes();  // Get the list of child nodes
        for (int j = 0; j < sceneChildNodes.getLength(); j++) {  // Iterate over the child nodes
            Node childNode = sceneChildNodes.item(j);  // Get the current child node
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {  // Check if the child node is an element
                Element childElement = (Element) childNode;  // Cast the child node to an element
                switch (childElement.getTagName()) {  // Switch based on the tag name
                    case "ambient-light":  // If the tag name is "ambient-light"
                        setAmbientLight(childElement, scene);  // Set the ambient light of the scene
                        break;
                    case "geometries":  // If the tag name is "geometries"
                        parseGeometries(childElement, scene);  // Parse and add geometries to the scene
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
        String[] ambientColorValues = ambientElement.getAttribute("color").split(" ");  // Split the color values
        Color ambientColor = new Color(  // Create a new Color object with the parsed values
                Double.parseDouble(ambientColorValues[0]),
                Double.parseDouble(ambientColorValues[1]),
                Double.parseDouble(ambientColorValues[2])
        );
        scene.setAmbientLight(new AmbientLight(ambientColor, 1));  // Set the ambient light of the scene
    }

    /**
     * Parses and adds geometries (spheres and triangles) to the scene.
     *
     * @param geometriesElement The XML element containing geometries information
     * @param scene The Scene object to be configured
     */
    private static void parseGeometries(Element geometriesElement, Scene scene) {
        NodeList geometryList = geometriesElement.getChildNodes();  // Get the list of geometry nodes
        for (int k = 0; k < geometryList.getLength(); k++) {  // Iterate over the geometry nodes
            Node geometryNode = geometryList.item(k);  // Get the current geometry node
            if (geometryNode.getNodeType() == Node.ELEMENT_NODE) {  // Check if the node is an element
                Element geometryElement = (Element) geometryNode;  // Cast the node to an element
                switch (geometryElement.getTagName()) {  // Switch based on the tag name
                    case "sphere":  // If the tag name is "sphere"
                        addSphere(geometryElement, scene);  // Add a sphere to the scene
                        break;
                    case "triangle":  // If the tag name is "triangle"
                        addTriangle(geometryElement, scene);  // Add a triangle to the scene
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
                parsePoint(sphereElement.getAttribute("center")),  // Parse the center point of the sphere
                Double.parseDouble(sphereElement.getAttribute("radius")));  // Parse the radius of the sphere
        scene.geometries.add(sphere);  // Add the sphere to the scene's geometries
    }

    /**
     * Adds a triangle to the scene based on the XML configuration.
     *
     * @param triangleElement The XML element containing triangle information
     * @param scene The Scene object to which the triangle will be added
     */
    private static void addTriangle(Element triangleElement, Scene scene) {
        Point p0 = parsePoint(triangleElement.getAttribute("p0"));  // Parse the first point of the triangle
        Point p1 = parsePoint(triangleElement.getAttribute("p1"));  // Parse the second point of the triangle
        Point p2 = parsePoint(triangleElement.getAttribute("p2"));  // Parse the third point of the triangle
        Triangle triangle = new Triangle(p0, p1, p2);  // Create a new Triangle object
        scene.geometries.add(triangle);  // Add the triangle to the scene's geometries
    }

    /**
     * Parses a string representation of a point into a Point object.
     *
     * @param pointStr The string representation of a point (format: "x y z")
     * @return A new Point object
     */
    private static Point parsePoint(String pointStr) {
        String[] values = pointStr.split(" ");  // Split the string into x, y, and z values
        return new Point(
                Double.parseDouble(values[0]),  // Parse the x value
                Double.parseDouble(values[1]),  // Parse the y value
                Double.parseDouble(values[2])  // Parse the z value
        );
    }
}
