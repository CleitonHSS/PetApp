package br.com.ipet.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.ipet.IPetApplication;
import br.com.ipet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.usuario_text_input)
    TextInputLayout usuarioTextInput;
    @BindView(R.id.usuario_edit_text)
    TextInputEditText usuarioEditText;

    @BindView(R.id.password_text_input)
    TextInputLayout passwordTextInput;
    @BindView(R.id.password_edit_text)
    TextInputEditText passwordEditText;

    @BindView(R.id.button_facebook_login)
    LoginButton facebookButton;

    @BindView(R.id.entrar_button)
    MaterialButton entrarButton;
    @BindView(R.id.cadastrar_button)
    MaterialButton cadastrarButton;

    private CallbackManager facebookCallbackManager;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        facebookCallbackManager = CallbackManager.Factory.create();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null || IPetApplication.usuarioLogado != null) {
            navegarParaMenu();
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        facebookButton.setReadPermissions("email", "public_profile");
        facebookButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Timber.d("facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Timber.d("facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Timber.e(error, "facebook:onError");
            }
        });

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navegarParaCadastro();
            }
        });
        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = usuarioEditText.getText().toString();
                String senha = passwordEditText.getText().toString();

                if(email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "O usuário e a senha não podem estar vazios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Timber.d("signInWithCustomToken");
                                    IPetApplication.usuarioLogado = firebaseAuth.getCurrentUser();
                                    navegarParaMenu();
                                } else {
                                    Timber.w("signInWithCustomToken:failure");
                                    //TODO: tratar cada erro no login
                                    Toast.makeText(LoginActivity.this, "Ocorreu um erro ao fazer login.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void navegarParaCadastro() {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    private void navegarParaMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Timber.d("handleFacebookAccessToken:%s", token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Timber.d("signInWithCredential:success");
                            IPetApplication.usuarioLogado = firebaseAuth.getCurrentUser();
                            navegarParaMenu();
                        } else {
                            Timber.w(task.getException(), "signInWithCredential:failure");
                            Toast.makeText(LoginActivity.this, "Ocorreu um erro ao fazer login pelo facebook.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}