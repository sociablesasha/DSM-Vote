package dto;

public class CandidateDTO extends DTO {

	private String division;
	private int number;
	private String master;
	private String slave1;
	private String slave2;
	private String slave3;
	private String commitment;
	
	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getSlave1() {
		return slave1;
	}

	public void setSlave1(String slave1) {
		this.slave1 = slave1;
	}

	public String getSlave2() {
		return slave2;
	}

	public void setSlave2(String slave2) {
		this.slave2 = slave2;
	}

	public String getSlave3() {
		return slave3;
	}

	public void setSlave3(String slave3) {
		this.slave3 = slave3;
	}

	public String getCommitment() {
		return commitment;
	}

	public void setCommitment(String commitment) {
		this.commitment = commitment;
	}
}
