package com.axerios.fondos_app.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.axerios.fondos_app.JSON.JSONParser;
import com.axerios.fondos_app.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.regex.Pattern;

/**
 * Created by Axerios on 13/08/2016.
 */
public class register extends AppCompatActivity{
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();


    // url to get all products list
    private String url_register;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PASS = "pass";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_EDAD = "edad";
    private static final String TAG_SEXO = "sexo";

    TextView cajaEmail;
    TextView cajaPass;
    TextView cajaNombre;
    TextView cajaEdad;
    String email = "";
    String pass = "";
    String nombre = "";
    String edad = "";
    String estado = "";
    String sexo = "";

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    // products JSONArray
    JSONArray user = null;
    JSONParser jsonParser = new JSONParser();

    private User_Sharedpreferences preferences = null;

    Conexion_existente conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        // Set up the login form.
        url_register = getString(R.string.path_register);
        preferences = new User_Sharedpreferences(this,"glusens_login");
        conn = new Conexion_existente(this);
        cajaEmail = (TextView) findViewById(R.id.email);
        cajaPass = (TextView) findViewById(R.id.password);
        cajaNombre = (TextView) findViewById(R.id.nombre);
        cajaEdad = (TextView) findViewById(R.id.edad);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);

        Obtener_cuenta();
    }


    public void Obtener_cuenta(){
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                TextView caja = (TextView) findViewById(R.id.email);
                caja.setText(possibleEmail);
            }
        }
    }

    public void registro(View view){
        email = cajaEmail.getText().toString();
        pass = cajaPass.getText().toString();
        nombre = cajaNombre.getText().toString();
        edad = cajaEdad.getText().toString();
        int selectedId=radioSexGroup.getCheckedRadioButtonId();
        radioSexButton=(RadioButton)findViewById(selectedId);
        if(radioSexGroup.getCheckedRadioButtonId() != -1) {
            if (radioSexButton.getText().equals(getString(R.string.radio_M))) {
                sexo = "M";
            } else {
                sexo = "F";
            }
        }
        //sexo = cajaSexo.getText().toString();
        estado = getString(R.string.prompt_registrando);
        if(conn.isNetworkAvailable()) {
            if(!cajaEmail.getText().toString().equals(null)&&!cajaEmail.getText().toString().equals("")
                    &&!cajaPass.getText().toString().equals(null)&&!cajaPass.getText().toString().equals("")
                    &&!cajaNombre.getText().toString().equals(null)&&!cajaNombre.getText().toString().equals("")
                    &&!cajaEdad.getText().toString().equals(null)&&!cajaEdad.getText().toString().equals("")
                    &&radioSexGroup.getCheckedRadioButtonId() != -1) {
                new registrar().execute();
            }else{
                mensajeLlenardatos();
            }
        }else{
            mensajeErrorConexion();
        }
    }


    @Override
    public void onPause(){

        super.onPause();
        if(pDialog != null)
            pDialog.dismiss();
    }

    class registrar extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Registrar.this);
            pDialog.setMessage(estado);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_EMAIL,email));
            params.add(new BasicNameValuePair(TAG_NOMBRE, nombre));
            params.add(new BasicNameValuePair(TAG_PASS, pass));
            params.add(new BasicNameValuePair(TAG_EDAD, edad));
            params.add(new BasicNameValuePair(TAG_SEXO, sexo));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            try{

                JSONObject json = jsonParser.makeHttpRequest(url_register,"POST", params);

                // check json success tag
                try {
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // successfully updated
                        preferences.Guardar_string_individual("email", email);
                        preferences.Guardar_string_individual("session", "open");
                        Intent i = new Intent(Registrar.this, Interfaz.class);
                        i.putExtra("new","new");
                        startActivity(i);
                        cerrar();

                    } else if (success == 2) {
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.prompt_email_existente), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Error", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }catch (Exception e) {
                mensajeError();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();


        }

    }

    public void mensajeError(){
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_consulta), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    public void cerrar(){
        this.finish();
    }

    public void mensajeLlenardatos(){
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.mensaje_llenar_datos), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void mensajeErrorConexion(){
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_conexion), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
