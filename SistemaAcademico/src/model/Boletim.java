package model;

public class Boletim {
	//atributos
	private int rgmBo;
	private String cursoBo;
	private String nomeBo;
	
	//construtores
	public Boletim() {	
	}

	public Boletim(int rgmBo, String cursoBo, String nomeBo) {
		this.rgmBo = rgmBo;
		this.cursoBo = cursoBo;
		this.nomeBo = nomeBo;
	}

	public int getRgmBo() {
		return rgmBo;
	}

	public void setRgmBo(int rgmBo) {
		this.rgmBo = rgmBo;
	}

	public String getCursoBo() {
		return cursoBo;
	}

	public void setCursoBo(String cursoBo) {
		this.cursoBo = cursoBo;
	}

	public String getNomeBo() {
		return nomeBo;
	}

	public void setNomeBo(String nomeBo) {
		this.nomeBo = nomeBo;
	}
	
	
}
