package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import JDBC.Utils;
import Pojo.TodoPojo;

public class TodoDaoImp implements TodoDao {

		private static final String INSERT_TODOS_SQL = "INSERT INTO todos"
				+ "  (title, username, description, target_date,  is_done) VALUES " + " (?, ?, ?, ?, ?);";

		private static final String SELECT_TODO_BY_ID = "select id,title,username,description,target_date,is_done from todos where id =?;";
		private static final String SELECT_ALL_TODOS = "select * from todos;";
		private static final String DELETE_TODO_BY_ID = "delete from todos where id = ?;";
		private static final String UPDATE_TODO = "update todos set title = ?, username= ?, description =?, target_date =?, is_done = ? where id = ?;";

		public TodoDaoImp() {
		}

		@Override
		public void insertTodo(TodoPojo todo) throws SQLException {
			System.out.println(INSERT_TODOS_SQL);
			// try-with-resource statement will auto close the connection.
			try (Connection connection = Utils.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TODOS_SQL)) {
				preparedStatement.setString(1, todo.getTitle());
				preparedStatement.setString(2, todo.getUsername());
				preparedStatement.setString(3, todo.getDescription());
				preparedStatement.setDate(4, Utils.getSQLDate(todo.getTargetDate()));
				preparedStatement.setBoolean(5, todo.isStatus());
				System.out.println(preparedStatement);
				preparedStatement.executeUpdate();
			} catch (SQLException exception) {
				Utils.printSQLException(exception);
			}
		}

		@Override
		public TodoPojo selectTodo(long todoId) {
			TodoPojo todo = null;
			// Step 1: Establishing a Connection
			try (Connection connection = Utils.getConnection();
					// Step 2:Create a statement using connection object
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TODO_BY_ID);) {
				preparedStatement.setLong(1, todoId);
				System.out.println(preparedStatement);
				// Step 3: Execute the query or update query
				ResultSet rs = preparedStatement.executeQuery();

				// Step 4: Process the ResultSet object.
				while (rs.next()) {
					long id = rs.getLong("id");
					String title = rs.getString("title");
					String username = rs.getString("username");
					String description = rs.getString("description");
					LocalDate targetDate = rs.getDate("target_date").toLocalDate();
					boolean isDone = rs.getBoolean("is_done");
					todo = new TodoPojo(id, title, username, description, targetDate, isDone);
				}
			} catch (SQLException exception) {
				Utils.printSQLException(exception);
			}
			return todo;
		}

		@Override
		public List<TodoPojo> selectAllTodos() {

			// using try-with-resources to avoid closing resources (boiler plate code)
			List<TodoPojo> todos = new ArrayList<>();

			// Step 1: Establishing a Connection
			try (Connection connection = Utils.getConnection();

					// Step 2:Create a statement using connection object
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TODOS)) {
				System.out.println(preparedStatement);
				// Step 3: Execute the query or update query
				ResultSet rs = preparedStatement.executeQuery();

				// Step 4: Process the ResultSet object.
				while (rs.next()) {
					long id = rs.getLong("id");
					String title = rs.getString("title");
					String username = rs.getString("username");
					String description = rs.getString("description");
					LocalDate targetDate = rs.getDate("target_date").toLocalDate();
					boolean isDone = rs.getBoolean("is_done");
					todos.add(new TodoPojo(id, title, username, description, targetDate, isDone));
				}
			} catch (SQLException exception) {
				Utils.printSQLException(exception);
			}
			return todos;
		}

		@Override
		public boolean deleteTodo(int id) throws SQLException {
			boolean rowDeleted;
			try (Connection connection = Utils.getConnection();
					PreparedStatement statement = connection.prepareStatement(DELETE_TODO_BY_ID);) {
				statement.setInt(1, id);
				rowDeleted = statement.executeUpdate() > 0;
			}
			return rowDeleted;
		}

		@Override
		public boolean updateTodo(TodoPojo todo) throws SQLException {
			boolean rowUpdated;
			try (Connection connection = Utils.getConnection();
					PreparedStatement statement = connection.prepareStatement(UPDATE_TODO);) {
				statement.setString(1, todo.getTitle());
				statement.setString(2, todo.getUsername());
				statement.setString(3, todo.getDescription());
				statement.setDate(4, Utils.getSQLDate(todo.getTargetDate()));
				statement.setBoolean(5, todo.isStatus());
				statement.setLong(6, todo.getId());
				rowUpdated = statement.executeUpdate() > 0;
			}
			return rowUpdated;
		}
}
