package com.gabrielaangebrandt.pregnancyapp.loginActivity.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gabrielaangebrandt.pregnancyapp.App;
import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.forgotenPassAcitivty.view.ForgottenPasswordAcitivity;
import com.gabrielaangebrandt.pregnancyapp.loginActivity.LoginContract;
import com.gabrielaangebrandt.pregnancyapp.mainActivity.view.MainActivity;
import com.gabrielaangebrandt.pregnancyapp.registrationActivity.view.RegistrationActivity;
import com.gabrielaangebrandt.pregnancyapp.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    private static final int RC_SIGN_IN = 100;
    private static final int FACEBOOK_LOGIN_REQUEST = 64206;
    @BindView(R.id.et_username)
    EditText email;

    @BindView(R.id.et_password)
    EditText password;

    @BindView(R.id.btn_login)
    Button loginButton;

    @BindView(R.id.btn_loginfb_real)
    LoginButton fbLoginButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        callbackManager = CallbackManager.Factory.create();
        fbLoginButton.setReadPermissions("email", "public_profile");
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("error", "facebook:onCancel");

                Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("error", "facebook:onError", error);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.GONE);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(this, MainActivity.class));
            } catch (ApiException e) {
                Log.w("ERROR: ", "signInResult:failed code=" + e.getStatusCode());
            }
        }
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == FACEBOOK_LOGIN_REQUEST) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.cannotLogIn, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_login)
    public void openMainActivity() {
        progressBar.setVisibility(View.VISIBLE);
        App.getFirebaseAuth().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed - local sign in.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @OnClick(R.id.btn_loginfb)
    public void fbLogin() {
        fbLoginButton.performClick();
    }

    @OnClick(R.id.btn_logingmail)
    public void gmailLogin() {
        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.tv_forgotten_pass)
    public void forgottenPass() {
        startActivity(new Intent(this, ForgottenPasswordAcitivity.class));
    }

    @OnClick(R.id.tv_register)
    public void openRegisterActivity() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    private void handleFacebookAccessToken(AccessToken token) {
        mAuth.signInWithCredential(FacebookAuthProvider.getCredential(token.getToken()))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Utils.setSharedPrefs("user", user.getEmail(), getApplicationContext());
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed - Facebook.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
