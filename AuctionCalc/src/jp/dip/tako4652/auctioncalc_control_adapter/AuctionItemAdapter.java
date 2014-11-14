package jp.dip.tako4652.auctioncalc_control_adapter;

import java.util.ArrayList;

import jp.dip.tako4652.auctioncalc_model_dto.AuctionItemDTO;

public class AuctionItemAdapter {
	private ArrayList<AuctionItemDTO> iList;

	public AuctionItemAdapter() {
		iList = new ArrayList<AuctionItemDTO>();
	}

	public void add(AuctionItemDTO auctionItemDTO) {
		iList.add(auctionItemDTO);
	}

	public void remove(int i) {
		iList.remove(i);
	}

	public int size() {
		return iList.size();
	}

	public void clear() {
		iList.clear();
	}

	public AuctionItemDTO get(int i) {
		return iList.get(i);
	}

}
