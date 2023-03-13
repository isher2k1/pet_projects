package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainPageServlet extends HttpServlet {

  private static final String TEMPLATE_HTML = "template.html";
  private static final String TEMPLATE_PLACE_HOLDER = "%CONTENT%";

  public MainPageServlet() {
  }

  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response) {
    System.out.println(request);

    response.setContentType("text/html");
    response.setStatus(HttpServletResponse.SC_OK);
    StringBuilder builder = new StringBuilder();
    for (Task task : Database.getTasts()) {
      builder.append("<tr> <td>").append(task.getId())
        .append("</td> <td>").append(task.getTaskType())
        .append("</td> <td>").append(task.getDescription())
        .append("</td> <td>").append(task.getComment())
        .append("</td> <td>").append(task.getStatus())
        .append("</td> <td> ").append(task.getApproved()).append("</td>\r\n").append("<td>");

      if (!task.getApproved()) {
        builder.append("<form action=\"/approveTask\">\n" + "  <input type=\"hidden\" name=\"id\" value=\"")
          .append(task.getId()).append("\">\n").append("  <input type=\"submit\" class=\"approve\" value=\"approve\">\n")
          .append("</form>");
      }
      builder.append("<form action=\"/editTask\">\n" + "  <input type=\"hidden\" name=\"id\" value=\"").append(task.getId()).append("\">\n").append("  <input type=\"submit\" class=\"approve\" value=\"edit\">\n").append("</form>\n").append("<form action=\"/deleteTask\">\n").append("  <input type=\"hidden\" name=\"id\" value=\"").append(task.getId()).append("\">\n").append("  <input type=\"submit\" class=\"approve\" value=\"delete\">\n").append("</form>");
      builder.append("</td> </tr>\r\n");

    }
    try (BufferedReader reader = new BufferedReader(
      new InputStreamReader(getClass().getClassLoader().getResourceAsStream(TEMPLATE_HTML)))) {
      String line = reader.readLine();
      while (line != null) {
        if (line.contains(TEMPLATE_PLACE_HOLDER)) {
          line = line.replace(TEMPLATE_PLACE_HOLDER, builder.toString());
        }
        response.getOutputStream().write(line.getBytes());
        line = reader.readLine();
      }
      response.getOutputStream().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
