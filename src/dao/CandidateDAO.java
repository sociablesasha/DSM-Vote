package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.CandidateDTO;

public class CandidateDAO extends DAO {
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public CandidateDAO () {
		super();
		this.connection = super.connection;
	}
	
	// 트랜젝션 처리
	public CandidateDTO doWrite (CandidateDTO candidateDTO) {
		String writeSQL = "INSERT INTO `candidate` (`division`, `number`, `master`, `slave1`, `slave2`, `slave3`, `commitment`)"
				+ "VALUES (?, (SELECT COUNT(*) + 1 FROM `candidate` candidateNumber WHERE division = ?), ?, ?, ?, ?, ?)";
		String selectSQL = "SELECT `number` FROM `candidate` WHERE `division` = ? AND `master` = ? AND `slave1` = ? AND `slave2` = ? AND `slave3` = ? AND `commitment` = ?";

		try {
			preparedStatement = connection.prepareStatement(writeSQL);
			preparedStatement.setString(1, candidateDTO.getDivision());
			preparedStatement.setString(2, candidateDTO.getDivision());
			preparedStatement.setString(3, candidateDTO.getMaster());
			preparedStatement.setString(4, candidateDTO.getSlave1());
			preparedStatement.setString(5, candidateDTO.getSlave2() == null ? "" : candidateDTO.getSlave2());
			preparedStatement.setString(6, candidateDTO.getSlave3() == null ? "" : candidateDTO.getSlave3());
			preparedStatement.setString(7, candidateDTO.getCommitment());
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, candidateDTO.getDivision());
			preparedStatement.setString(2, candidateDTO.getMaster());
			preparedStatement.setString(3, candidateDTO.getSlave1());
			preparedStatement.setString(4, candidateDTO.getSlave2() == null ? "" : candidateDTO.getSlave2());
			preparedStatement.setString(5, candidateDTO.getSlave3() == null ? "" : candidateDTO.getSlave3());
			preparedStatement.setString(6, candidateDTO.getCommitment());
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				candidateDTO.setNumber(resultSet.getInt(1));
			} else {
				candidateDTO = null;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			candidateDTO = null;
		}
		return candidateDTO;
	}
	
	public boolean doUpdate (CandidateDTO candidateDTO) {
		String updateSQL = "UPDATE `candidate` SET `master` = ?, `slave1` = ?, `slave2` = ?, `slave3` = ?, `commitment` = ? WHERE division = ? AND number = ?";
		boolean result;
		try {
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, candidateDTO.getMaster());
			preparedStatement.setString(2, candidateDTO.getSlave1());
			preparedStatement.setString(3, candidateDTO.getSlave2() == null ? "" : candidateDTO.getSlave2());
			preparedStatement.setString(4, candidateDTO.getSlave3() == null ? "" : candidateDTO.getSlave3());
			preparedStatement.setString(5, candidateDTO.getCommitment());
			preparedStatement.setString(6, candidateDTO.getDivision());
			preparedStatement.setInt(7, candidateDTO.getNumber());
			preparedStatement.executeUpdate();
			result = true;
		} catch (Exception exception) {
			result = false;
		}
		return result;
	}
	
	public boolean doDelete (CandidateDTO candidateDTO) {
		String deleteSQL = "DELETE FROM `candidate` WHERE division = ? AND number = ?";
		String updateSQL = "UPDATE `candidate` SET `number` = `number` - 1 WHERE division = ? AND number > ?";
		boolean result;
		try {
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, candidateDTO.getDivision());
			preparedStatement.setInt(2, candidateDTO.getNumber());
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, candidateDTO.getDivision());
			preparedStatement.setInt(2, candidateDTO.getNumber());
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
	
	public CandidateDTO doDetail (CandidateDTO candidateDTO) {
		String detailSQL = "SELECT `master`, `slave1`, `slave2`, `slave3`, `commitment` FROM `candidate` AS candidate WHERE division = ? AND number = ?";
		try {
			preparedStatement = connection.prepareStatement(detailSQL);
			preparedStatement.setString(1, candidateDTO.getDivision());
			preparedStatement.setInt(2, candidateDTO.getNumber());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				candidateDTO.setMaster(resultSet.getString(1));
				candidateDTO.setSlave1(resultSet.getString(2));
				candidateDTO.setSlave2(resultSet.getString(3));
				candidateDTO.setSlave3(resultSet.getString(4));
				candidateDTO.setCommitment(resultSet.getString(5));
			} else {
				candidateDTO = null;
			}
		} catch (Exception exception) {
			candidateDTO = null;
		}
		return candidateDTO;
	}
	
	public ArrayList<CandidateDTO> doList (String division) {
		String listSQL = "SELECT `number`, `master`, `slave1`, `slave2`, `slave3`, `commitment` FROM `candidate` WHERE `division` = ? ORDER BY `number`";
		ArrayList<CandidateDTO> list = new ArrayList<CandidateDTO>();
		try {
			preparedStatement = connection.prepareStatement(listSQL);
			preparedStatement.setString(1, division);
			resultSet = preparedStatement.executeQuery();
			CandidateDTO candidateDTO;
			while (resultSet.next()) {
				candidateDTO = new CandidateDTO();
				candidateDTO.setDivision(division);
				candidateDTO.setNumber(resultSet.getInt(1));
				candidateDTO.setMaster(resultSet.getString(2));
				candidateDTO.setSlave1(resultSet.getString(3));
				candidateDTO.setSlave2(resultSet.getString(4));
				candidateDTO.setSlave3(resultSet.getString(5));
				candidateDTO.setCommitment(resultSet.getString(6));
				list.add(candidateDTO);
			}
			return list;
		} catch (Exception exception) {
			exception.printStackTrace();
			list = null;
		}
		return list;
	}
}
