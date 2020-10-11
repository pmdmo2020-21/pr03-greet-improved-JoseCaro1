package es.iessaladillo.pedrojoya.pr02_greetimproved;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import es.iessaladillo.pedrojoya.pr02_greetimproved.databinding.MainActivityBinding;
import es.iessaladillo.pedrojoya.pr02_greetimproved.utils.SoftInputUtils;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    private String name, sirname, alias;
    private int count = 0, countCharName = 20, countCharSirName = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialStage();
        setupViews();
    }

    


    private void initialStage() {
        alias = binding.rdbMr.getText().toString();
        binding.lblCountBar.setText(getString(R.string.intentsOther, count++));
        binding.lblCharsName.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        binding.lblCharsName.setText(getResources().getQuantityString(R.plurals.oneChar, countCharName, countCharName));
        binding.lblCharsSirname.setText(getResources().getQuantityString(R.plurals.oneChar, countCharName, countCharName));
    }



    private void setupViews() {
        binding.rdgColor.setOnCheckedChangeListener((radioGroup, i) -> showImage());
        binding.btnGreet.setOnClickListener(view -> showText());
        binding.swtPremium.setOnCheckedChangeListener((buttonView, isChecked) -> showBar(isChecked));
        binding.txtName.setOnFocusChangeListener((view, hasFocus) -> changeColor(binding.lblCharsName, hasFocus));
        binding.txtSirName.setOnFocusChangeListener((view, hasFocus) -> changeColor(binding.lblCharsSirname, hasFocus));
        binding.txtSirName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                showText();
                return true;
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        binding.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showChars(count, countCharName, binding.lblCharsName);
            }


            @Override
            public void afterTextChanged(Editable s) {
                validate(s, binding.txtName);

            }
        });

        binding.txtSirName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showChars(count, countCharSirName, binding.lblCharsSirname);
            }

            @Override
            public void afterTextChanged(Editable s) {
                validate(s, binding.txtSirName);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


            @Override
            public void afterTextChanged(Editable s) {
                

            }
        });

        binding.txtSirName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }



    private void showChars(int count, int countChar1, TextView textView) {
        textView.setText(getResources().getQuantityString(R.plurals.oneChar, countChar1 - count, countCharName - count));
    }



    private void validate(Editable s, EditText editText) {
        if (s.toString().isEmpty()) {
            editText.setError(getString(R.string.invalid));
        } else {
            editText.setError(null);
        }
    }



    private void changeColor(TextView textView, boolean hasFocus) {
        int colorResId = hasFocus ? R.color.colorAccent : R.color.textPrimary;
        textView.setTextColor(ContextCompat.getColor(this, colorResId));
    }


    private void showBar(boolean hasFocus) {
        restart();

        if (hasFocus) {
            binding.barProgress.setVisibility(View.GONE);
            binding.lblCountBar.setVisibility(View.GONE);
        } else {
            binding.lblCountBar.setText(getString(R.string.intentsOther, count++));
            binding.barProgress.setVisibility(View.VISIBLE);
            binding.lblCountBar.setVisibility(View.VISIBLE);

        }
    }



    private void restart() {
        count = 0;
        binding.barProgress.setProgress(count);
    }



    private void showImage() {
        if (binding.rdbMr.isChecked()) {
            binding.imgPhoto.setImageResource(R.drawable.ic_mr);
            alias = binding.rdbMr.getText().toString();
        } else if (binding.rdbMs.isChecked()) {
            binding.imgPhoto.setImageResource(R.drawable.ic_ms);
            alias = binding.rdbMs.getText().toString();
        } else {
            binding.imgPhoto.setImageResource(R.drawable.ic_mrs);
            alias = binding.rdbMrs.getText().toString();
        }

    }



    private void makeText() {
        Toast result;
        if (binding.chkPolitely.isChecked()) {
            result = Toast.makeText(this, getString(R.string.resultPolitely, alias, name, sirname), Toast.LENGTH_SHORT);
        } else {
            result = Toast.makeText(this, getString(R.string.result, name, sirname), Toast.LENGTH_SHORT);
        }
        result.setGravity(Gravity.BOTTOM, 0, 75);
        result.show();


    }




    private void showText() {
        name = binding.txtName.getText().toString();
        sirname = binding.txtSirName.getText().toString();

        if (count > 10 && !binding.swtPremium.isChecked()) {
            Toast result = Toast.makeText(this, getString(R.string.tenIntents), Toast.LENGTH_SHORT);
            result.setGravity(Gravity.BOTTOM, 0, 75);
            result.show();
        } else {
            pushButton();
        }


    }


    protected void pushButton() {
        if (!name.isEmpty() && !sirname.isEmpty()) {
            increaseBar();
            SoftInputUtils.hideSoftKeyboard(binding.txtSirName);
            makeText();


        } else {
            if (name.isEmpty()) {
                binding.txtName.setError(getString(R.string.invalid));
                binding.txtName.requestFocus();

            } else {
                binding.txtSirName.setError(getString(R.string.invalid));
                binding.txtSirName.requestFocus();
            }
        }
    }



    private void increaseBar() {
        binding.lblCountBar.setText(getString(R.string.intentsOther, count));
        binding.barProgress.setProgress(count);
        count++;
    }

}