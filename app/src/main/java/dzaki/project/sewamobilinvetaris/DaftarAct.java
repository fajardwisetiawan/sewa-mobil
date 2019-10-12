package dzaki.project.sewamobilinvetaris;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dzaki.project.sewamobilinvetaris.app.AppController;
import dzaki.project.sewamobilinvetaris.util.Server;

public class DaftarAct extends AppCompatActivity {
    ProgressDialog pDialog;
    Intent intent;

    private EditText etUsername,etPassword,etPassword2;
    private Button btnSignUp;
    private TextView tvLogin;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "register.php";

    private static final String TAG = DaftarAct.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DaftarAct.this,LoginAct.class));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String uid = FirebaseInstanceId.getInstance().getToken();
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    String confirm_password = etPassword2.getText().toString();

                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkRegister(username, password, confirm_password,uid);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
        private void checkRegister(final String username, final String password, final String confirm_password, final String uid) {
            pDialog = new ProgressDialog(this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Register ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.e(TAG, "Register Response: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        success = jObj.getInt(TAG_SUCCESS);

                        // Check for error node in json
                        if (success == 1) {

                            Log.e("Successfully Register!", jObj.toString());

                            Toast.makeText(getApplicationContext(),
                                    jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                            etUsername.setText("");
                            etPassword.setText("");
                            etPassword2.setText("");

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Login Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();

                    hideDialog();

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("confirm_password", confirm_password);
                    params.put("uid", uid);
                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

        @Override
        public void onBackPressed() {
            intent = new Intent(DaftarAct.this, LoginAct.class);
            finish();
            startActivity(intent);
        }
}
