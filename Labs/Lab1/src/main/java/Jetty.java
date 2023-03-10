import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

public class Jetty {

  private Server server;

  public Jetty(int port) {
    server = new Server();
    ServerConnector connector = new ServerConnector(server);
    connector.setPort(port);
    server.setConnectors(new Connector[]{connector});
    ServletHandler servletHandler = new ServletHandler();
    server.setHandler(servletHandler);
  }

  public void addServlet(Class<? extends Servlet> servlet, String pathSpec) {
    ServletHandler servletHandler = (ServletHandler) server.getHandler();
    servletHandler.addServletWithMapping(servlet, pathSpec);
  }

  public void addServlet(ServletHolder servlet, String pathSpec) {
    ServletHandler servletHandler = (ServletHandler) server.getHandler();
    servletHandler.addServletWithMapping(servlet, pathSpec);
  }

  public void start() throws Exception {
    server.start();
    server.join();
  }

  public void stop() throws Exception {
    server.stop();
  }

}
