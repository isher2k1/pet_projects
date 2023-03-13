package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletePageServlet extends HttpServlet {

  public DeletePageServlet() {
  }

  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response) {
      if (request.getParameterValues("id") != null) {
        Database.deleteTask(Long.valueOf(request.getParameterValues("id")[0]));
      }
    try {
      response.sendRedirect("/main");
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }

  }
}
