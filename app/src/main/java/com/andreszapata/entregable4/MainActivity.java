package com.andreszapata.entregable4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    EditText Nombrep, Apellidop, Correop, Contrasenap;
    private Button anadir;

    private Button Datos;

    private Button consultas;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);

        // Obtener referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

        Nombrep = findViewById(R.id.txtNombre);
        Apellidop = findViewById(R.id.txtApellido);
        Correop = findViewById(R.id.txtCorreo);
        Contrasenap = findViewById(R.id.txtPassword);
        anadir = findViewById(R.id.AnadirRegistro);
        Datos = findViewById(R.id.VerRegistros);
        consultas = findViewById(R.id.Consultas);

        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los datos del usuario
                String nombre = Nombrep.getText().toString();
                String apellido = Apellidop.getText().toString();
                String correo = Correop.getText().toString();
                String contrasena = Contrasenap.getText().toString();

                // Crear un objeto Usuario
                Usuario usuario = new Usuario(nombre, apellido, correo, contrasena);

                // Guardar el usuario en la base de datos
                databaseReference.push().setValue(usuario);

                //Mostrar al usuario que se guardo el registro
                Toast.makeText(MainActivity.this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show();

                // Limpiar los campos después de guardar los datos
                Nombrep.setText("");
                Apellidop.setText("");
                Correop.setText("");
                Contrasenap.setText("");
            }
        });

        Datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Crear un Intent para abrir la actividad ListaDatos
                    Intent intent = new Intent(MainActivity.this, ListaDatos.class);
                    // Iniciar la actividad ListaDatos
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error al abrir la actividad ListaDatos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        consultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para abrir la actividad ConsultaActivity
                Intent intent = new Intent(MainActivity.this, ConsultaActivity.class);
                // Iniciar la actividad ConsultaActivity
                startActivity(intent);
            }
        });

    }
}
