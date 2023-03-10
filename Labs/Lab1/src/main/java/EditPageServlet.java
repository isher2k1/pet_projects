import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditPageServlet extends HttpServlet {

  private static final String TEMPLATE_HTML = "editTask.html";

  public EditPageServlet() {
  }

  protected void doGet(
    HttpServletRequest request,
    HttpServletResponse response) throws IOException {
    if (request.getParameterValues("edit") != null) {
      Task taskTemp = Database.getTask(Long.parseLong(request.getParameterValues("id")[0]));
      taskTemp.setId(Long.valueOf(request.getParameterValues("id")[0]));
      taskTemp.setTaskType(request.getParameterValues("task_type")[0]);
      taskTemp.setDescription(request.getParameterValues("description")[0]);
      taskTemp.setComment(request.getParameterValues("comment")[0]);
      taskTemp.setStatus(request.getParameterValues("status")[0]);
      Database.editTask(taskTemp);
      response.sendRedirect("/editTask?id=" + taskTemp.getId());
    }


    Task task = null;
    if (request.getParameterValues("id") != null) {
      task = Database.getTask(Long.parseLong(request.getParameterValues("id")[0]));
    }
    if (task == null) throw new RuntimeException("Task not found id = " + request.getParameterValues("id")[0]);

    response.setContentType("text/html");
    response.setStatus(HttpServletResponse.SC_OK);
    try (BufferedReader reader = new BufferedReader(
      new InputStreamReader(getClass().getClassLoader().getResourceAsStream(TEMPLATE_HTML)))) {
      String line = reader.readLine();
      while (line != null) {
        if (line.contains("%ID%")) {
          line = line.replace("%ID%", request.getParameterValues("id")[0]);
        }

        if (line.contains("%TASK_TYPE_PLANTING%")) {
          if ("planting".equals(task.getTaskType())) {
            line = line.replace("%TASK_TYPE_PLANTING%", "selected");
          } else {
            line = line.replace("%TASK_TYPE_PLANTING%", "");
          }
        }

        if (line.contains("%TASK_TYPE_TREATMENT%")) {
          if ("treatment".equals(task.getTaskType())) {
            line = line.replace("%TASK_TYPE_TREATMENT%", "selected");
          } else {
            line = line.replace("%TASK_TYPE_TREATMENT%", "");
          }
        }

        if (line.contains("%TASK_TYPE_ARTISTIC%")) {
          if ("artistic processing".equals(task.getTaskType())) {
            line = line.replace("%TASK_TYPE_ARTISTIC%", "selected");
          } else {
            line = line.replace("%TASK_TYPE_ARTISTIC%", "");
          }
        }

        if (line.contains("%TASK_TYPE_DESTRUCTION%")) {
          if ("destruction".equals(task.getTaskType())) {
            line = line.replace("%TASK_TYPE_DESTRUCTION%", "selected");
          } else {
            line = line.replace("%TASK_TYPE_DESTRUCTION%", "");
          }
        }

        if (line.contains("%DESCRIPTION%")) {
          line = line.replace("%DESCRIPTION%", task.getDescription() == null ? "" : task.getDescription());
        }
        if (line.contains("%COMMENT%")) {
          line = line.replace("%COMMENT%", task.getComment() == null ? "" : task.getComment());
        }

        if (line.contains("%STATUS_TO_DO%")) {
          if ("To do".equals(task.getStatus())) {
            line = line.replace("%STATUS_TO_DO%", "selected");
          } else {
            line = line.replace("%STATUS_TO_DO%", "");
          }
        }

        if (line.contains("%STATUS_IN_PROGRESS%")) {
          if ("In progress".equals(task.getStatus())) {
            line = line.replace("%STATUS_IN_PROGRESS%", "selected");
          } else {
            line = line.replace("%STATUS_IN_PROGRESS%", "");
          }
        }

        if (line.contains("%STATUS_IN_FINISHED%")) {
          if ("Finished".equals(task.getStatus())) {
            line = line.replace("%STATUS_IN_FINISHED%", "selected");
          } else {
            line = line.replace("%STATUS_IN_FINISHED%", "");
          }
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
