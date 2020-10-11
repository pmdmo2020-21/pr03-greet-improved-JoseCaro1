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

    /*
    Declarado el initialStage donde vamos a inicializar
    todo lo necesario para que nuestra aplicacion funcione correctamente
    */


    private void initialStage() {
        alias = binding.rdbMr.getText().toString();
        binding.lblCountBar.setText(getString(R.string.intentsOther, count++));
        binding.lblCharsName.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        binding.lblCharsName.setText(getResources().getQuantityString(R.plurals.oneChar, countCharName, countCharName));
        binding.lblCharsSirname.setText(getResources().getQuantityString(R.plurals.oneChar, countCharName, countCharName));
    }

    /*Lugar donde creamos la gran mayoria de Listeners para despues
    poder pasarselo al onCreate el cual es nuestro Hook method*/

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

    /*onResume es otro Hook method el cual nos soluciona el problema de
    la validacion que se puede causar si creamos esos Listeners
    * en el setupViews y se lo pasamos al onCreate*/

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

    /*Metodo generalizado para los textView que se encuentran
    debajo de los editText se utiliza en los Listeners de
    * los editText*/

    private void showChars(int count, int countChar1, TextView textView) {
        textView.setText(getResources().getQuantityString(R.plurals.oneChar, countChar1 - count, countCharName - count));
    }

    /*Metodo generalizado para poder hacer la validacion de los editText que se utiliza
    en los Listeners de los editText */

    private void validate(Editable s, EditText editText) {
        if (s.toString().isEmpty()) {
            editText.setError(getString(R.string.invalid));
        } else {
            editText.setError(null);
        }
    }

    /*Metodo para el cambio de los de los textView dependiendo de quien
    tenga el focus se utiliza en los Listeners de los textView de Name y Sirname*/

    private void changeColor(TextView textView, boolean hasFocus) {
        int colorResId = hasFocus ? R.color.colorAccent : R.color.textPrimary;
        textView.setTextColor(ContextCompat.getColor(this, colorResId));
    }

    /*Metodo para mostrar o esconder la barra de progreso dependiendo de si el usuario es premium o no
     * junto con el reinicio del contador, eleccion de si aumentar el contador o no y la barra por cada vez que se ejecute el metodo*/

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

    /*Reinicio de contador y progreso de barra utilizado en el metodo showBar()
    el cual se utiliza en el listener de swtPremium*/

    private void restart() {
        count = 0;
        binding.barProgress.setProgress(count);
    }

    /*Muestra el cambio de icono segun la eleccion del mister junto con el cambio del prefijo y se utliza este metodo
     * en el Listener de radioButton*/

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

    /*Metodo para hacer el texto del saludo segun si es de forma educada o no y se utiliza en el metodo
     * showText()*/

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


    /*Metodo el cual muestra segun si el contador no supera los 10 y no es premium o
    si no se llama al metodo pushButton*/

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
    /*Metodo el cual se encarga de que si name y sirname no estan vacios muestre en mensajede
    slaudo aumente la barra y se esconda el teclado o q si name esta vacio al
    pulsa el boton salga un error y o si esta solo el error en el sirname salga en el sirname */

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

    /*Aumenta el progreso de la barra junto con el contador*/

    private void increaseBar() {
        binding.lblCountBar.setText(getString(R.string.intentsOther, count));
        binding.barProgress.setProgress(count);
        count++;
    }

}