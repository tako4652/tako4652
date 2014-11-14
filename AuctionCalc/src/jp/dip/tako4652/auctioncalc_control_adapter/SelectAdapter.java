package jp.dip.tako4652.auctioncalc_control_adapter;

import java.util.ArrayList;

import jp.dip.tako4652.auctioncalc_model_dao.GuildNameDuplicateException;
import jp.dip.tako4652.auctioncalc_model_dto.SelectDTO;

public class SelectAdapter {
	private ArrayList<SelectDTO> sList;

	public SelectAdapter() {
		sList = new ArrayList<SelectDTO>();
	}

	public void add(SelectDTO selectDTO) throws RuntimeException {
		boolean flug = false;
		for (int i = 0; i < sList.size(); i++) {
			if (selectDTO.getName().equals(sList.get(i).getName())) {
				flug = true;
				break;
			}
		}
		if (flug) {
			throw new GuildNameDuplicateException();
		} else {
			sList.add(selectDTO);
		}
	}

	public void remove(int i) {
		sList.remove(i);
	}

	public int size() {
		return sList.size();
	}

	public void cliar() {
		sList.clear();
	}

	public SelectDTO get(int i) {
		return sList.get(i);
	}

}
