package Dao;

import java.sql.SQLException;
import java.util.List;

import Pojo.TodoPojo;

public interface TodoDao {

	void insertTodo(TodoPojo todo) throws SQLException;

	TodoPojo selectTodo(long todoId);

	List<TodoPojo> selectAllTodos();

	boolean deleteTodo(int id) throws SQLException;

	boolean updateTodo(TodoPojo todo) throws SQLException;

}