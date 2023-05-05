package se.yrgo.schedule;

import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

/**
 * A class implementing the Formatter interface. Formats a List of Assignment
 * to HTML.
 *
 */
public class XMLFormatter implements Formatter {

  public String format(List<Assignment> assignments) {

    if (assignments.size() == 0) {
      return new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
      .append("<schedules></schedules>\n")
              .toString();
    }
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
      Document doc = documentBuilder.newDocument();
      Element rootElement = doc.createElement("SCHEDULES");
      doc.appendChild(rootElement);

      for(Assignment assignment: assignments){
        Element schedule = doc.createElement("SCHEDULE");
        schedule.setAttribute("date", assignment.date());

        Element school = doc.createElement("school");
        Element schoolName = doc.createElement("schoolName");
        schoolName.appendChild(doc.createTextNode(assignment.school().getName()));
        school.appendChild(schoolName);

        Element schoolAddress = doc.createElement("address");
        schoolAddress.appendChild(doc.createTextNode(assignment.school().getAddress()));
        school.appendChild(schoolAddress);

        schedule.appendChild(school);

        Element substitute = doc.createElement("SUBSTITUTE");
        Element substituteName = doc.createElement("name");

        substituteName.appendChild(doc.createTextNode(assignment.teacher().getName()));
        substitute.appendChild(substituteName);

        schedule.appendChild(substitute);

        rootElement.appendChild(schedule);
      }

      StringWriter stringWriter = new StringWriter();

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();

      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");

      DOMSource domSource = new DOMSource(doc);
      StreamResult streamResult = new StreamResult(stringWriter);

      transformer.transform(domSource, streamResult);

      return stringWriter.toString();

    } catch (ParserConfigurationException | TransformerException e) {
      return "XML-Error";
    }
  }
}
