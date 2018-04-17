package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.CandidateDTO;
import dto.UserDTO;
import dto.VoteDTO;

public class VoteDAO extends DAO {
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public VoteDAO () {
		super();
		this.connection = super.connection;
	}
	
	public boolean doVote (UserDTO userDTO, VoteDTO voteDTO) {
		String userDoneSQL = "UPDATE `user` SET `done` = 'y' WHERE identification != 'admin' AND identification = ? AND password = ? AND name = ? AND `done` = 'n'";
		String voteSQL = "INSERT INTO `vote` (`division`, `number`) VALUES (?, ?)";
		boolean result;
		try {
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(userDoneSQL);
			preparedStatement.setString(1, userDTO.getIdentification());
			preparedStatement.setString(2, userDTO.getPassword());
			preparedStatement.setString(3, userDTO.getName());
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(voteSQL);
			preparedStatement.setString(1, "학생회");
			preparedStatement.setInt(2, voteDTO.getGovernment());
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(voteSQL);
			preparedStatement.setString(1, "자치부");
			preparedStatement.setInt(2, voteDTO.getMinistry());
			preparedStatement.executeUpdate();
			
			connection.commit();
			result = true;
		} catch (Exception preparedStatementException) {
			preparedStatementException.printStackTrace();
			result = false;
			try {
				connection.rollback();
			} catch (Exception rollbackException) {
				rollbackException.printStackTrace();
			}
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (Exception autoCommitException) {			
				autoCommitException.printStackTrace();
			}
		}
		return result;
	}
	
	public int doResult (CandidateDTO candidateDTO) {
		String resultSQL = "SELECT COUNT(*) FROM `vote` WHERE `division` = ? AND `number` = ?";
		int result;
		try {
			preparedStatement = connection.prepareStatement(resultSQL);
			preparedStatement.setString(1, candidateDTO.getDivision());
			preparedStatement.setInt(2, candidateDTO.getNumber());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result = resultSet.getInt(1);
			} else {
				result = -1;
			}
		} catch (Exception exception) {
			result = -1;
		}
		return result;
	}
	
}
