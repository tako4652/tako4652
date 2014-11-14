package jp.dip.tako4652.auctioncalc_model_dto;

public class AuctionDTO {

	private String MissionName;
	private String MissionDateTime;
	private int GuildCount;
	private int MemberCount;
	private int Total;
	private int Devided;

	// Constractor
	public AuctionDTO() {
		MissionName = "";
		MissionDateTime = "";
		GuildCount = 0;
		MemberCount = 0;
		Total = 0;
		Devided = 0;
	}

	public AuctionDTO(String missionName) {
		this();
		MissionName = missionName;
	}

	public AuctionDTO(String missionName, String missionDateTime) {
		this(missionName);
		MissionDateTime = missionDateTime;
	}

	public AuctionDTO(String missionName, String missionDateTime, int guildCount) {
		this(missionName, missionDateTime);
		GuildCount = guildCount;
	}

	public AuctionDTO(String missionName, String missionDateTime,
			int guildCount, int memberCount) {
		this(missionName, missionDateTime, guildCount);
		MemberCount = memberCount;
	}

	public AuctionDTO(String missionName, String missionDateTime,
			int guildCount, int memberCount, int total) {
		this(missionName, missionDateTime, guildCount, memberCount);
		Total = total;
	}

	public AuctionDTO(String missionName, String missionDateTime,
			int guildCount, int memberCount, int total, int devided) {
		this(missionName, missionDateTime, guildCount, memberCount, total);
		Devided = devided;
	}

	// Method
	public void clear() {
		MissionName = "";
		MissionDateTime = "";
		GuildCount = 0;
		MemberCount = 0;
		Total = 0;
		Devided = 0;
	}

	public String toString() {
		String str = "";
		str += "MissionName=" + MissionName + "\n";
		str += "MissionDateTime=" + MissionDateTime + "\n";
		str += "GuildCount=" + String.format("%d", GuildCount) + "\n";
		str += "MemberCount=" + String.format("%d", MemberCount) + "\n";
		str += "Total=" + String.format("%d", Total) + "\n";
		str += "Devided=" + String.format("%d", Devided) + "\n";
		return str;
	}

	// Setter
	public void setAuctionDTO(String missionName, String missionDateTime) {
		MissionName = missionName;
		MissionDateTime = missionDateTime;
	}

	public void setMissionName(String missionName) {
		MissionName = missionName;
	}

	public void setMissionDateTime(String missionDateTime) {
		MissionDateTime = missionDateTime;
	}

	public void setGuildCount(int guildCount) {
		GuildCount = guildCount;
	}

	public void setMemberCount(int memberCount) {
		MemberCount = memberCount;
	}

	public void setTotal(int total) {
		Total = total;
	}

	public void setDevided(int devided) {
		Devided = devided;
	}

	// Getter
	public String getMissionName() {
		return MissionName;
	}

	public String getMissionDateTime() {
		return MissionDateTime;
	}

	public int getGuildCount() {
		return GuildCount;
	}

	public int getMemberCount() {
		return MemberCount;
	}

	public int getTotal() {
		return Total;
	}

	public int getDevided() {
		return Devided;
	}

}
