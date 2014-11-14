package jp.dip.tako4652.auctioncalc_control_adapter;

import java.util.ArrayList;

import jp.dip.tako4652.auctioncalc_model_dao.SelectNameDuplicateException;
import jp.dip.tako4652.auctioncalc_model_dto.AuctionGuildDTO;

public class AuctionGuildAdapter {
	private ArrayList<AuctionGuildDTO> gList;

	public AuctionGuildAdapter() {
		gList = new ArrayList<AuctionGuildDTO>();
	}

	public void add(AuctionGuildDTO auctionGuildDTO) throws RuntimeException {
		boolean flug = false;
		for (int i = 0; i < gList.size(); i++) {
			if (auctionGuildDTO.getGuildName().equals(
					gList.get(i).getGuildName())) {
				flug = true;
				break;
			}
		}
		if (flug) {
			throw new SelectNameDuplicateException();
		} else {
			gList.add(auctionGuildDTO);
		}
	}

	public void remove(int i) {
		gList.remove(i);
	}

	public int size() {
		return gList.size();
	}

	public void clear() {
		gList.clear();
	}

	public AuctionGuildDTO get(int i) {
		return gList.get(i);
	}
}
