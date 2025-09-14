package com.example.tarea.util;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea.R;
import com.example.tarea.adapter.TareaAdapter;
import com.example.tarea.dao.TareaDAO;
import com.example.tarea.model.Tarea;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TareaAdapter.OnTareaClickListener {
    private EditText editTitulo, editDescripcion;
    private Button buttonAgregar;
    private RecyclerView recyclerView;
    private TareaAdapter adapter;
    private TareaDAO tareaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupClickListeners();

        tareaDAO = new TareaDAO(this);
        cargarTareas();
    }

    private void initViews() {
        editTitulo = findViewById(R.id.editTitulo);
        editDescripcion = findViewById(R.id.editDescripcion);
        buttonAgregar = findViewById(R.id.buttonAgregar);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TareaAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
    }

    private void setupClickListeners() {
        buttonAgregar.setOnClickListener(v -> agregarTarea());
    }

    private void agregarTarea() {
        String titulo = editTitulo.getText().toString().trim();
        String descripcion = editDescripcion.getText().toString().trim();

        if (titulo.isEmpty()) {
            Toast.makeText(this, "El título es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        Tarea nuevaTarea = new Tarea(titulo, descripcion, false);

        tareaDAO.open();
        long result = tareaDAO.insertTarea(nuevaTarea);
        tareaDAO.close();

        if (result != -1) {
            editTitulo.setText("");
            editDescripcion.setText("");
            cargarTareas();
            Toast.makeText(this, "Tarea agregada exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al agregar la tarea", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarTareas() {
        tareaDAO.open();
        List<Tarea> tareas = tareaDAO.getAllTareas();
        tareaDAO.close();

        adapter.updateTareas(tareas);
    }

    @Override
    public void onTareaStateChanged(Tarea tarea) {
        tareaDAO.open();
        tareaDAO.updateTarea(tarea);
        tareaDAO.close();

        String estado = tarea.isEstado() ? "completada" : "pendiente";
        Toast.makeText(this, "Tarea marcada como " + estado, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTareaDelete(Tarea tarea) {
        tareaDAO.open();
        tareaDAO.deleteTarea(tarea.getId());
        tareaDAO.close();

        cargarTareas();
        Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
    }
}
