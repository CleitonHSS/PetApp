package br.com.ipet.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.ipet.IPetApplication;
import br.com.ipet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CadastroActivity extends AppCompatActivity {
    @BindView(R.id.usuario_text_input)
    TextInputLayout usuarioTextInput;
    @BindView(R.id.usuario_edit_text)
    TextInputEditText usuarioEditText;

    @BindView(R.id.email_text_input)
    TextInputLayout emailTextInput;
    @BindView(R.id.email_edit_text)
    TextInputEditText emailEditText;

    @BindView(R.id.password_text_input)
    TextInputLayout passwordTextInput;
    @BindView(R.id.password_edit_text)
    TextInputEditText passwordEditText;

    @BindView(R.id.cadastrar_button)
    MaterialButton cadastrarButton;
    @BindView(R.id.voltar_button)
    MaterialButton voltarButton;


    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null || IPetApplication.usuarioLogado != null) {
            navegarParaMenu();
        }

        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navegarParaLogin();
            }
        });
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String senha = passwordEditText.getText().toString();

                if(email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "O e-mail e a senha não podem estar vazios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(senha.length() < 6) {
                    Toast.makeText(CadastroActivity.this, "A senha deve conter no mínimo 6 caracteres.", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Timber.d("createUserWithEmailAndPassword");
                                    IPetApplication.usuarioLogado = firebaseAuth.getCurrentUser();
                                    navegarParaMenu();
                                } else {
                                    Timber.w("createUserWithEmailAndPassword:failure");
                                    //TODO: tratar cada erro no login
                                    Toast.makeText(CadastroActivity.this, "Ocorreu um erro ao cadastrar novo usuário.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void navegarParaLogin() {
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void navegarParaMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}