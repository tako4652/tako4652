package jp.dip.tako4652.auctioncalc_model_dto;

public class AuctionItemDTO {

	private String ItemName;
	private int Bid;

	// Constractor
	public AuctionItemDTO() {
		ItemName = "";
		Bid = 0;
	}

	public AuctionItemDTO(String itemName) {
		this();
		ItemName = itemName;
	}

	public AuctionItemDTO(String itemName, int bid) {
		this(itemName);
		Bid = bid;
	}

	// Method
	public String toString() {
		String str = "";
		str += "ItemName=" + ItemName + "\n";
		str += "Bid=" + String.format("%d", Bid) + "\n";
		return str;
	}

	// Setter
	public void setAuctionItem(String itemName, int bid) {
		setItemName(itemName);
		setBid(bid);
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public void setBid(int bid) {
		Bid = bid;
	}

	// Getter
	public String getItemName() {
		return ItemName;
	}

	public int getBid() {
		return Bid;
	}

}
