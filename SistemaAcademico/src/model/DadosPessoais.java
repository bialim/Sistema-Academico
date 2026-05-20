package model;

public class DadosPessoais {
	//Atributos
	private int rgmDados;
	private String nomeDados;
	private String dataDados;
	private String emailDados;
	private String endereco;
	private String municipio;
	private String uf;
	private String celular;
	
	//construtores
	public DadosPessoais() {	
	}

	public DadosPessoais(int rgmDados, String nomeDados, String dataDados, String emailDados, String endereco,
			String municipio, String uf, String celular) {

		this.rgmDados = rgmDados;
		this.nomeDados = nomeDados;
		this.dataDados = dataDados;
		this.emailDados = emailDados;
		this.endereco = endereco;
		this.municipio = municipio;
		this.uf = uf;
		this.celular = celular;
	}
	//getters e setters
	public int getRgmDados() {
		return rgmDados;
	}

	public void setRgmDados(int rgmDados) {
		this.rgmDados = rgmDados;
	}

	public String getNomeDados() {
		return nomeDados;
	}

	public void setNomeDados(String nomeDados) {
		this.nomeDados = nomeDados;
	}

	public String getDataDados() {
		return dataDados;
	}

	public void setDataDados(String dataDados) {
		this.dataDados = dataDados;
	}

	public String getEmailDados() {
		return emailDados;
	}

	public void setEmailDados(String emailDados) {
		this.emailDados = emailDados;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
	
}
