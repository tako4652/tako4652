package jp.dip.tako4652.auctioncalc_model_dto;

public class AuctionGuildDTO {

	private String GuildName;
	private int MemberCount;
	private int GuildPayd;

	public AuctionGuildDTO() {
		GuildName = "";
		MemberCount = 0;
		GuildPayd = 0;
	}

	// Constractor
	public AuctionGuildDTO(String guildName) {
		this();
		GuildName = guildName;
	}

	public AuctionGuildDTO(String guildName, int memberCount) {
		this(guildName);
		MemberCount = memberCount;
	}

	public AuctionGuildDTO(String guildName, int memberCount, int guildPayd) {
		this(guildName, memberCount);
		GuildPayd = guildPayd;
	}

	// Method
	public String toString() {
		String str = "";
		str += "GuildName=" + GuildName + "\n";
		str += "MemberCount=" + String.format("%d", MemberCount) + "\n";
		str += "GuildPayd=" + String.format("%d", GuildPayd) + "\n";
		return str;
	}

	// Setter
	public void setAuctionGuild(String guildName, int memberCount, int guildPayd) {
		GuildName = guildName;
		MemberCount = memberCount;
		GuildPayd = guildPayd;
	}

	public void setAuctionGuild(String guildName, int memberCount) {
		GuildName = guildName;
		MemberCount = memberCount;
	}

	public void setGuildName(String guildName) {
		GuildName = guildName;
	}

	public void setMemberCount(int memberCount) {
		MemberCount = memberCount;
	}

	public void setGuildPayd(int guildPayd) {
		GuildPayd = guildPayd;
	}

	// Getter
	public String getGuildName() {
		return GuildName;
	}

	public int getMemberCount() {
		return MemberCount;
	}

	public int getGuildPayd() {
		return GuildPayd;
	}

}
