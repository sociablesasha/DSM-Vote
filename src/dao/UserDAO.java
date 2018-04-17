package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.UserDTO;

public class UserDAO extends DAO {
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public UserDAO () {
		super();
		this.connection = super.connection;
	}
	
	public boolean doLogin (UserDTO userDTO) {
		String loginSQL = "SELECT * FROM `user` WHERE identification = ? AND password = ? AND name = ?";
		boolean result;
		try {
			preparedStatement = connection.prepareStatement(loginSQL);
			preparedStatement.setString(1, userDTO.getIdentification());
			preparedStatement.setString(2, userDTO.getPassword());
			preparedStatement.setString(3, userDTO.getName());
			resultSet = preparedStatement.executeQuery();
			result = resultSet.next() ? true : false;
		} catch (Exception exception) {
			exception.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public UserDTO doGet (UserDTO userDTO) {
		String getSQL = "SELECT `done` FROM `user` WHERE identification = ? AND password = ? AND name = ?";
		try {
			preparedStatement = connection.prepareStatement(getSQL);
			preparedStatement.setString(1, userDTO.getIdentification());
			preparedStatement.setString(2, userDTO.getPassword());
			preparedStatement.setString(3, userDTO.getName());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				userDTO.setDone(resultSet.getString(1));
			} else {
				userDTO = null;
			}
		} catch (Exception exception) {
			userDTO = null;
		}
		return userDTO;
	}
	
}
