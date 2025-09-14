package com.example.tarea.model;

public class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private boolean estado;

    public Tarea() {}

    public Tarea(String titulo, String descripcion, boolean estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Tarea(int id, String titulo, String descripcion, boolean estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
