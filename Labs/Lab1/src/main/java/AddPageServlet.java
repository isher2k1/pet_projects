import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddPageServlet extends HttpServlet{

  private static final String TEMPLATE_HTML = "addTask.html";

  public AddPageServlet() {
  }

  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response) {
    if (request.getParameterValues("task_type") != null &&
      request.getParameterValues("description") != null) {
      Task obj = new Task();
      obj.setTaskType(request.getParameterValues("task_type")[0]);
      obj.setDescription(request.getParameterValues("description")[0]);
      obj.setStatus("To do");
      obj.setApproved(false);
      Database.addTask(obj);
    }

    response.setContentType("text/html");
    response.setStatus(HttpServletResponse.SC_OK);
    try (BufferedReader reader = new BufferedReader(
      new InputStreamReader(getClass().getClassLoader().getResourceAsStream(TEMPLATE_HTML)))) {
      String line = reader.readLine();
      while (line != null) {
//        if (line.contains(TEMPLATE_PLACE_HOLDER)) {
//          line = line.replace(TEMPLATE_PLACE_HOLDER, builder.toString());
//        }
        response.getOutputStream().write(line.getBytes());
        line = reader.readLine();
      }
      response.getOutputStream().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
