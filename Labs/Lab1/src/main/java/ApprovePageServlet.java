import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApprovePageServlet extends HttpServlet {

  public ApprovePageServlet() {
  }

  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response) {
      if (request.getParameterValues("id") != null) {
        Task task = Database.getTask(Long.parseLong(request.getParameterValues("id")[0]));
        task.setApproved(true);
        Database.editTask(task);
      }
    try {
      response.sendRedirect("/main");
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

  }
}
