import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
  public static void main(String[] args) throws Exception {
    Jetty jetty = new Jetty(8080);
    jetty.addServlet(new ServletHolder(new MainPageServlet()), "/main");
    jetty.addServlet(new ServletHolder(new AddPageServlet()), "/addTask");
    jetty.addServlet(new ServletHolder(new EditPageServlet()), "/editTask");
    jetty.addServlet(new ServletHolder(new ApprovePageServlet()), "/approveTask");
    jetty.addServlet(new ServletHolder(new DeletePageServlet()), "/deleteTask");
    jetty.start();

  }
}
