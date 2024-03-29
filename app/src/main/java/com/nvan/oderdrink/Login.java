package com.nvan.oderdrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nvan.oderdrink.ViewModel.AuthViewModel;
import com.nvan.oderdrink.Product.ProductActivity;


public class Login extends AppCompatActivity {

    TextView SignUp;
    private Button btnlogin;
    private EditText userName, passwordlogin;
    private ProgressDialog progressDialog;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.login);
        userName = findViewById(R.id.userName);
        passwordlogin = findViewById(R.id.passWord);
        SignUp = findViewById(R.id.SignUp);
        progressDialog = new ProgressDialog(this);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        SignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Login.this, Register.class);
                    startActivity(intent);
                }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSigin();
            }
        });
    }

    private void onClickSigin() {
        String strEmail = userName.getText().toString().trim();
        String strPassword = passwordlogin.getText().toString().trim();
        if (strEmail.equals("admin113@gmail.com") && strPassword.equals("zx123456")) {
            // Đăng nhập thành công với tài khoản admin
            Intent intent = new Intent(Login.this, ProductActivity.class);
            startActivity(intent);
            finishAffinity();
            return;
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            authViewModel.updateUserId();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            authViewModel.getUserId().setValue(null);
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}