package com.example.tarea.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea.R;
import com.example.tarea.model.Tarea;

import java.util.List;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {
    private List<Tarea> tareas;
    private OnTareaClickListener listener;

    public interface OnTareaClickListener {
        void onTareaStateChanged(Tarea tarea);
        void onTareaDelete(Tarea tarea);
    }

    public TareaAdapter(List<Tarea> tareas, OnTareaClickListener listener) {
        this.tareas = tareas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);
        holder.bind(tarea);
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public void updateTareas(List<Tarea> nuevasTareas) {
        this.tareas = nuevasTareas;
        notifyDataSetChanged();
    }

    class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView textTitulo, textDescripcion;
        CheckBox checkBoxEstado;
        Button buttonEliminar;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textTitulo);
            textDescripcion = itemView.findViewById(R.id.textDescripcion);
            checkBoxEstado = itemView.findViewById(R.id.checkBoxEstado);
            buttonEliminar = itemView.findViewById(R.id.buttonEliminar);
        }

        public void bind(Tarea tarea) {
            textTitulo.setText(tarea.getTitulo());
            textDescripcion.setText(tarea.getDescripcion());
            checkBoxEstado.setChecked(tarea.isEstado());

            checkBoxEstado.setOnCheckedChangeListener((buttonView, isChecked) -> {
                tarea.setEstado(isChecked);
                if (listener != null) {
                    listener.onTareaStateChanged(tarea);
                }
            });

            buttonEliminar.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTareaDelete(tarea);
                }
            });
        }
    }
}