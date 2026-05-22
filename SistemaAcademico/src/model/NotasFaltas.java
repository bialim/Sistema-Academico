package model;

public class NotasFaltas {
    private int idNotaFalta;
    private int rgm;
    private int idDisciplina;
    private int semestre;
    private double a1;
    private double a2;
    private double media;
    private double af;
    private int faltas;
    private String statusAluno;

    // Construtor vazio
    public NotasFaltas() {
    }

    // Construtor completo
    public NotasFaltas(int idNotaFalta, int rgm, int idDisciplina, int semestre, double a1, double a2, double media, double af, int faltas, String statusAluno) {
        this.idNotaFalta = idNotaFalta;
        this.rgm = rgm;
        this.idDisciplina = idDisciplina;
        this.semestre = semestre;
        this.a1 = a1;
        this.a2 = a2;
        this.media = media;
        this.af = af;
        this.faltas = faltas;
        this.statusAluno = statusAluno;
    }

    // Métodos Getters e Setters
    public int getIdNotaFalta() { return idNotaFalta; }
    public void setIdNotaFalta(int idNotaFalta) { this.idNotaFalta = idNotaFalta; }

    public int getRgm() { return rgm; }
    public void setRgm(int rgm) { this.rgm = rgm; }

    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    public double getA1() { return a1; }
    public void setA1(double a1) { this.a1 = a1; }

    public double getA2() { return a2; }
    public void setA2(double a2) { this.a2 = a2; }

    public double getMedia() { return media; }
    public void setMedia(double media) { this.media = media; }

    public double getAf() { return af; }
    public void setAf(double af) { this.af = af; }

    public int getFaltas() { return faltas; }
    public void setFaltas(int faltas) { this.faltas = faltas; }

    public String getStatusAluno() { return statusAluno; }
    public void setStatusAluno(String statusAluno) { this.statusAluno = statusAluno; }
}