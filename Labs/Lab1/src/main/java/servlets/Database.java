package servlets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

  private static final String USERNAME = "postgres";
  private static final String PASSWORD = "";
  private static final String DB = "jdbc:postgresql://127.0.0.1:5432/parks";


  public static void deleteTask(Long id) {
    String SQL_UPDATE = "DELETE FROM public.tasks WHERE id="+id+';';

    try (Connection conn = DriverManager.getConnection(
      DB, USERNAME, PASSWORD)) {
      Statement stmt = conn.createStatement();
      int result = stmt.executeUpdate(SQL_UPDATE);
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void editTask(Task task) {
    String SQL_UPDATE = "UPDATE public.tasks\n" +
      "\tSET task_type='"+task.getTaskType()+"', description='"+task.getDescription()+"', " +
      "comment='"+task.getComment()+"', status='"+task.getStatus()+"', is_approved='"+task.getApproved()+"'\n" +
      "\tWHERE id ="+task.getId()+";";

    try (Connection conn = DriverManager.getConnection(
      DB, USERNAME, PASSWORD)) {
      Statement stmt = conn.createStatement();
      int result = stmt.executeUpdate(SQL_UPDATE);
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void addTask(Task task) {
    StringBuilder params = new StringBuilder();
    params.append('\'' + task.getTaskType() + "',");
    params.append('\'' + task.getDescription() + "',");
    params.append('\'' + task.getStatus() + "',");
    params.append(task.getApproved());
    String SQL_INSERT = "INSERT INTO tasks(task_type, description, status, is_approved)" +
                                    "VALUES ("+ params +");";

    try (Connection conn = DriverManager.getConnection(
      DB, USERNAME, PASSWORD)) {
      Statement stmt = conn.createStatement();
      int result = stmt.executeUpdate(SQL_INSERT);
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static List<Task> getTasts() {
    List<Task> result = new ArrayList<>();

    String SQL_SELECT = "Select * from tasks ORDER BY id";

    // auto close connection and preparedStatement
    try (Connection conn = DriverManager.getConnection(
      DB, USERNAME, PASSWORD);
         PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {

        long id = resultSet.getLong("id");
        String taskType = resultSet.getString("task_type");
        String description = resultSet.getString("description");
        String comment = resultSet.getString("comment");
        String status = resultSet.getString("status");
        Boolean isApproved = resultSet.getBoolean("is_approved");

        Task obj = new Task();
        obj.setId(id);
        obj.setTaskType(taskType);
        obj.setDescription(description);
        obj.setComment(comment);
        obj.setStatus(status);
        obj.setApproved(isApproved);
        result.add(obj);

      }
      result.forEach(x -> System.out.println(x));
      return result;
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Task getTask(Long task_id) {
    List<Task> result = new ArrayList<>();

    String SQL_SELECT = "SELECT * FROM tasks t WHERE t.id = "+task_id+';';

    // auto close connection and preparedStatement
    try (Connection conn = DriverManager.getConnection(
      DB, USERNAME, PASSWORD);
         PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT)) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {

        long id = resultSet.getLong("id");
        String taskType = resultSet.getString("task_type");
        String description = resultSet.getString("description");
        String comment = resultSet.getString("comment");
        String status = resultSet.getString("status");
        Boolean isApproved = resultSet.getBoolean("is_approved");

        Task obj = new Task();
        obj.setId(id);
        obj.setTaskType(taskType);
        obj.setDescription(description);
        obj.setComment(comment);
        obj.setStatus(status);
        obj.setApproved(isApproved);
        result.add(obj);

      }
      result.forEach(x -> System.out.println(x));
      return result.get(0);
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
