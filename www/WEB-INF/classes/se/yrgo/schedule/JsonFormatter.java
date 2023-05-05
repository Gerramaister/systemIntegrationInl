package se.yrgo.schedule;

import java.util.List;
import org.json.*;

/**
 * A class implementing the Formatter interface. Formats a List of Assignment
 * to HTML.
 *
 */
public class JsonFormatter implements Formatter {

  public String format(List<Assignment> assignments) {

    if (assignments.size() == 0) {
      return "[]";
    }

    JSONArray jsonArray = new JSONArray();

    for(Assignment assignment: assignments){

      JSONObject assignmentJson = new JSONObject();
      JSONObject substituteJson = new JSONObject();
      JSONObject schoolJson = new JSONObject();
      assignmentJson.put("date", assignment.date());
      substituteJson.put("name", assignment.teacher().getName());
      assignmentJson.put("substitute", substituteJson);
      schoolJson.put("name", assignment.school().getName());
      schoolJson.put("address", assignment.school().getAddress());

      assignmentJson.put("school", schoolJson);
      jsonArray.put(assignmentJson);
    }
    return jsonArray.toString();
  }
}
