package jp.dip.tako4652.auctioncalc_model_dto;

public class SelectDTO {

	private String Tag;
	private String Name;

	public SelectDTO() {
		Tag = "";
		Name = "";
	}

	// Constractor
	public SelectDTO(String tag, String selectName) {
		Tag = tag;
		Name = selectName;
	}

	// Method
	public String toString() {
		String str = "";
		str += "selectName=" + Name + "\n";
		return str;
	}

	// Setter
	public void setTag(String tag) {
		Tag = tag;
	}

	public void setName(String selectName) {
		Name = selectName;
	}

	// Getter
	public String getTag() {
		return Tag;
	}

	public String getName() {
		return Name;
	}

}
