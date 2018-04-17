package dto;

public class VoteDTO extends DTO {

	private int government;
	private int ministry;

	public int getGovernment() {
		return government;
	}

	public void setGovernment(int government) {
		this.government = government;
	}

	public int getMinistry() {
		return ministry;
	}

	public void setMinistry(int ministry) {
		this.ministry = ministry;
	}

}
