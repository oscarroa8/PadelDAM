package com.example.padeldam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText etUsuario, etContrasenia;
    private FirebaseAuth mAuth;//Variable para usar los metodos de firebase Authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance(); //Instanciamos en servicio
        elementosInterfaz();
    }


    private void elementosInterfaz(){
        Button entrar = findViewById(R.id.btnEntrar);
        this.etUsuario = findViewById(R.id.etCorreo);
        this.etContrasenia = findViewById(R.id.etContrasena);
    }

    public void loginUsuario(View v){
        String emailUsuario = etUsuario.getText().toString();
        String contasenaUsuario = etContrasenia.getText().toString();

        Intent i = new Intent(Login.this, menuPrincipal.class);
        //Uso el signin que coge el correo y la contraseña para autenticar el ususario
        //Ese metodo devuelve un objeto Task<AuthResult> que es una tarea que se ejecutara en segundo plano para autenticar el usuario
        Task<AuthResult> getUserInfoTask = mAuth.signInWithEmailAndPassword(emailUsuario, contasenaUsuario).addOnCompleteListener(
                task -> {
                    if(task.isSuccessful()){//Si la tarea fue existosa (se autentico el usuario)
                        AuthResult authResult = task.getResult(); //Se obtiene el resultado de la tarea y se guarda en un objeto authresult
                        FirebaseUser currentUser = authResult.getUser();
                        i.putExtra("user", currentUser);
                        Toast.makeText(getApplicationContext(), "Bienvenido "+currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    } else {
                        // Si la autenticación no fue exitosa, se maneja el caso de error
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthInvalidUserException) { // se verifica si exception es una instancia de la clase de firebase
                            // El usuario no existe o ha sido eliminado
                            Toast.makeText(getApplicationContext(), "El usuario no existe o ha sido eliminado.", Toast.LENGTH_SHORT).show();
                        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                            // Credenciales de autenticación incorrectas
                            Toast.makeText(getApplicationContext(), "Credenciales de autenticación incorrectas.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Otros tipos de error
                            assert exception != null;
                            Toast.makeText(getApplicationContext(), "Error de autenticación: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }




}