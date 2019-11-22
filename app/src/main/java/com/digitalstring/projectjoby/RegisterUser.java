package com.digitalstring.projectjoby;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterUser extends AppCompatActivity {

    //Atributes
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;


    //Declare a firebaseAuth object
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        editTextEmail = findViewById(R.id.usuarioLoginActivity);
        editTextPassword = findViewById(R.id.passwordLoginActivity);

        ///initialize  firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void register( View view ){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if ( TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se requiere ingresar email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Se requiere ingresar contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("realizando consulta en linea...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ){
                            Toast.makeText(RegisterUser.this , " se ha registrado el email", Toast.LENGTH_LONG).show();
                        }else{
                            if ( task.getException() instanceof  FirebaseAuthUserCollisionException ) {
                                Toast.makeText(RegisterUser.this, "Usuario existente", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterUser.this, "no se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }


}
