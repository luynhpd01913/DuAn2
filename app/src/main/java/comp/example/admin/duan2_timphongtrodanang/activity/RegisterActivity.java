package comp.example.admin.duan2_timphongtrodanang.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import comp.example.admin.duan2_timphongtrodanang.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtName, edtMail, edtPassword, edtConfrim;
    private Button btnRegister;
    ProgressBar loading;
    private static String URL_REGIST = "http://192.168.1.222/codePHP/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading);
        edtConfrim = findViewById(R.id.edtConfrim);
        edtMail = findViewById(R.id.edtMail);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String mail = edtMail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if(name.equals("")|| mail.equals("") || password.equals("")){
                    Toast.makeText(RegisterActivity.this, "Vui long nhap dau thong tin", Toast.LENGTH_SHORT).show();
                }else {
                    Regist();
                }
            }
        });

    }
    public void Regist(){
        loading.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.GONE);

        final String name = this.edtName.getText().toString().trim();
        final String mail = this.edtMail.getText().toString().trim();
        final String password = this.edtPassword.getText().toString().trim();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String a = "\"success\": \"1\"";
                        if(response.toString().indexOf(a) != 0){
                            Toast.makeText(RegisterActivity.this,"Register Success",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            loading.setVisibility(View.GONE);
                        }else {
                            Toast.makeText(RegisterActivity.this,"Register error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this,"Register Error!!! " +error.toString(),Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        btnRegister.setVisibility(View.VISIBLE);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",mail);
                params.put("password",password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
