package com.andreszapata.entregable4;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaDatos extends AppCompatActivity {

    private ListView listViewDatos;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lista);

        // Inicializar Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("usuarios");

        // Inicializar la lista de usuarios
        usuarioList = new ArrayList<>();

        // Configurar adaptador
        usuarioAdapter = new UsuarioAdapter(this, usuarioList);

        // Referenciar el ListView
        listViewDatos = findViewById(R.id.listViewDatos);

        // Vincular el adaptador al ListView
        listViewDatos.setAdapter(usuarioAdapter);

        // Cargar usuarios desde la base de datos
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        // Limpiar la lista de usuarios
        usuarioList.clear();

        // Agregar un listener para obtener los datos de la base de datos
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Obtener el usuario de la base de datos
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    // Agregar el usuario a la lista
                    usuarioList.add(usuario);
                }
                // Notificar al adaptador que los datos han cambiado
                usuarioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de la base de datos aquí
                Toast.makeText(ListaDatos.this, "Error al cargar usuarios: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
