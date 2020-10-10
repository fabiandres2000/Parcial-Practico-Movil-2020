package com.example.parcial_practico;

public class Nota {
    public  String asignatura;
    public Double corte1,corte2,corte3,notafinal;
    int id;

    public Nota() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public Double getCorte1() {
        return corte1;
    }

    public void setCorte1(Double corte1) {
        this.corte1 = corte1;
    }

    public Double getCorte2() {
        return corte2;
    }

    public void setCorte2(Double corte2) {
        this.corte2 = corte2;
    }

    public Double getCorte3() {
        return corte3;
    }

    public void setCorte3(Double corte3) {
        this.corte3 = corte3;
    }

    public Double getNotafinal() {
        return notafinal;
    }

    public void setNotafinal(Double notafinal) {
        this.notafinal = notafinal;
    }
}
