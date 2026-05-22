package model;

public class Curso {
	//atributos
	private String curso;
	private String campus;
	private String periodo;
	
	//Costrutores
	public Curso() {
	}

	public Curso(String curso, String campus, String periodo) {
		super();
		this.curso = curso;
		this.campus = campus;
		this.periodo = periodo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	
}
