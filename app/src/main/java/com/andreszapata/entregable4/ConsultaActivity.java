package com.andreszapata.entregable4;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ConsultaActivity extends AppCompatActivity {

    private EditText correoEditText;
    private EditText apellidoEditText;
    private Button consultarButton;
    private TextView resultadoTextView;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        correoEditText = findViewById(R.id.correoEditText);
        apellidoEditText = findViewById(R.id.apellidoEditText);
        consultarButton = findViewById(R.id.consultarButton);
        resultadoTextView = findViewById(R.id.resultadoTextView);

        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

        consultarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarDatos();
            }
        });
    }

    private void consultarDatos() {
        String correo = correoEditText.getText().toString().trim();
        String apellido = apellidoEditText.getText().toString().trim();

        if (TextUtils.isEmpty(correo) && TextUtils.isEmpty(apellido)) {
            Toast.makeText(this, "Ingrese al menos un campo de consulta", Toast.LENGTH_SHORT).show();
            return;
        }

        Query consulta;

        if (!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(apellido)) {
            consulta = databaseReference.orderByChild("correo").equalTo(correo).orderByChild("apellido").equalTo(apellido);
        } else if (!TextUtils.isEmpty(correo)) {
            consulta = databaseReference.orderByChild("correo").equalTo(correo);
        } else {
            consulta = databaseReference.orderByChild("apellido").equalTo(apellido);
        }

        consulta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder resultados = new StringBuilder();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    // Construye el texto con los datos obtenidos
                    resultados.append("Nombre: ").append(usuario.getNombre()).append("\n");
                    resultados.append("Apellido: ").append(usuario.getApellido()).append("\n");
                    resultados.append("Correo: ").append(usuario.getCorreo()).append("\n\n");
                }
                // Actualiza el texto del TextView con los resultados
                resultadoTextView.setText(resultados.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja cualquier error de la base de datos aquí
                Toast.makeText(ConsultaActivity.this, "Error al realizar la consulta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
