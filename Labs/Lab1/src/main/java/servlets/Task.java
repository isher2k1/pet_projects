package servlets;

import java.util.Objects;

public class Task {
  private Long id;
  private String taskType;
  private String description;
  private String comment;
  private String status;
  private Boolean isApproved;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Boolean getApproved() {
    return isApproved;
  }

  public void setApproved(Boolean approved) {
    isApproved = approved;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task = (Task) o;
    return Objects.equals(id, task.id) && Objects.equals(taskType, task.taskType) && Objects.equals(description, task.description) && Objects.equals(comment, task.comment) && Objects.equals(status, task.status) && Objects.equals(isApproved, task.isApproved);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, taskType, description, comment, status, isApproved);
  }

  @Override
  public String toString() {
    return "servlets.Task{" +
      "id=" + id +
      ", taskType='" + taskType + '\'' +
      ", description='" + description + '\'' +
      ", comment='" + comment + '\'' +
      ", status='" + status + '\'' +
      ", isApproved=" + isApproved +
      '}';
  }
}
