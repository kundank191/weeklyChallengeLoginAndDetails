package com.example.android.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nextButton)
    Button mNextButton;
    @BindView(R.id.emailField)
    TextInputLayout mEmailLayout;
    @BindView(R.id.Email)
    EditText mEmail;
    @BindView(R.id.usernameField)
    TextInputLayout mUsernameLayout;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.aboutYouField)
    TextInputLayout mAboutLayout;
    @BindView(R.id.aboutYou)
    EditText mAbout;

    private SharedPreferences mSharedPref;

    private String EMAIL_ERROR = "Not a valid email address !"
                    , USERNAME_ERROR = "This field is required"
                    , ABOUT_ERROR = "This field is required";

    public final static String EMAIL_KEY = " email key",
                                USER_KEY = " user key",
                                ABOUT_KEY = " about key",
                                SAVED_FILE_KEY = "123124214";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarMain));
        mSharedPref = this.getBaseContext().getSharedPreferences(SAVED_FILE_KEY,MODE_PRIVATE);

        ButterKnife.bind(this);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameEntered()){
                    if(emailEnteredAndValid()){
                        if(aboutMeEntered()){
                            saveData();
                            Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                            startActivity(intent);
                        } else {
                            setError(mAboutLayout,ABOUT_ERROR);
                        }
                    } else {
                        setError(mEmailLayout,EMAIL_ERROR);
                    }
                } else {
                    setError(mUsernameLayout,USERNAME_ERROR);
                }

            }
        });

        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!usernameEntered()){
                    setError(mUsernameLayout,USERNAME_ERROR);
                } else {
                    removeError(mUsernameLayout);
                }
            }
        });

        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!emailEnteredAndValid()){
                    setError(mEmailLayout,EMAIL_ERROR);
                } else {
                    removeError(mEmailLayout);
                }
            }
        });

        mAbout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!aboutMeEntered()){
                    setError(mAboutLayout,ABOUT_ERROR);
                } else {
                    removeError(mAboutLayout);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.detailButton:
                if (mSharedPref.contains(USER_KEY)){
                    Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this,"No previous records found",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Extracts data from each edit text and saves it in Shared preference
     */
    private void saveData(){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(USER_KEY,mUsername.getText().toString());
        editor.putString(EMAIL_KEY,mEmail.getText().toString());
        editor.putString(ABOUT_KEY,mAbout.getText().toString());
        editor.apply();
    }

    /**
     *
     * @param layout the layout on which error is to be shown
     * @param message the message to be shown
     */
    private void setError(TextInputLayout layout, String message){
        layout.setErrorEnabled(true);
        layout.setError(message);
    }

    /**
     *
     * @param layout from which previous error message has to be removed
     */
    private void removeError(TextInputLayout layout){
        layout.setErrorEnabled(false);
    }

    /**
     *
     * @return true if a correct email is provided else false
     */
    private boolean emailEnteredAndValid() {
        return !TextUtils.isEmpty(mEmailLayout.getEditText().getText()) && validateEmail(mEmailLayout.getEditText().getText().toString());
    }

    /**
     *
     * @return true if username is provided else returns false
     */
    private boolean usernameEntered(){
        return !TextUtils.isEmpty(mUsernameLayout.getEditText().getText());
    }

    /**
     *
     * @return true if password is provided else returns false
     */
    private boolean aboutMeEntered(){
        return !TextUtils.isEmpty(mAboutLayout.getEditText().getText());
    }

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
