package com.andreszapata.entregable4;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ConsultaActivity extends AppCompatActivity {

    private EditText busquedaNombreEditText;
    private EditText busquedaAppelidoEditText;
    private EditText busquedaCorreoEditText;
    private Button buscarButton;
    private ListView resultadosListView;
    private DatabaseReference databaseReference;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        busquedaNombreEditText = findViewById(R.id.busquedaNombreEditText);
        busquedaAppelidoEditText = findViewById(R.id.busquedaApellidoEditText);
        busquedaCorreoEditText = findViewById(R.id.busquedaCorreoEditText);
        buscarButton = findViewById(R.id.buscarButton);
        resultadosListView = findViewById(R.id.resultadosListView);

        usuarioList = new ArrayList<>();
        usuarioAdapter = new UsuarioAdapter(this, usuarioList);
        resultadosListView.setAdapter(usuarioAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarRegistros();
            }
        });
    }

    private void buscarRegistros() {
        final String busquedaNombre = busquedaNombreEditText.getText().toString().trim();
        final String busquedaApellido = busquedaAppelidoEditText.getText().toString().trim();
        final String busquedaCorreo = busquedaCorreoEditText.getText().toString().trim();

        if (TextUtils.isEmpty(busquedaNombre) && TextUtils.isEmpty(busquedaApellido) && TextUtils.isEmpty(busquedaCorreo)) {
            Toast.makeText(this, "Ingrese al menos un criterio de búsqueda", Toast.LENGTH_SHORT).show();
            return;
        }

        Query consulta = databaseReference.orderByChild("nombre").startAt(busquedaNombre).endAt(busquedaNombre + "\uf8ff");

        consulta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    if ((TextUtils.isEmpty(busquedaNombre) || usuario.getNombre().toLowerCase().contains(busquedaNombre.toLowerCase())) &&
                            (TextUtils.isEmpty(busquedaApellido) || usuario.getApellido().toLowerCase().contains(busquedaApellido.toLowerCase())) &&
                            (TextUtils.isEmpty(busquedaCorreo) || usuario.getCorreo().toLowerCase().contains(busquedaCorreo.toLowerCase()))) {
                        usuarioList.add(usuario);
                    }
                }
                usuarioAdapter.notifyDataSetChanged();

                // Mostrar el ListView
                resultadosListView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ConsultaActivity.this, "Error al realizar la búsqueda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
