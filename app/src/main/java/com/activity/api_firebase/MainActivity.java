package com.activity.api_firebase;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnClick, btnThem;
    String url = "https://5fd1be42b485ea0016eeea83.mockapi.io/user";
    UserAdapter adapter;
    ArrayList<User> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    EditText editFirstName, editLastName, editGender, editSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClick = findViewById(R.id.btnClick);
        recyclerView = findViewById(R.id.recyclerView);
        btnThem = findViewById(R.id.btnSua);
        editFirstName = findViewById(R.id.editLastName);
        editLastName = findViewById(R.id.editFirstName);
        editGender = findViewById(R.id.editGender);
        editSalary = findViewById(R.id.edtSalary);

        GetArrayJson(url);


        btnClick.setOnClickListener(v -> {
            arrayList.clear();
            GetArrayJson(url);
        });
        btnThem.setOnClickListener(view -> PostApi(url));
    }


    private void GetArrayJson(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = (JSONObject) response.get(i);

                    String id = object.getString("id");
                    String firstName = object.getString("FIRSTNAME");
                    String lastname = object.getString("LASTNAME");
                    String gender = object.getString("GENDER");
                    String salary = object.getString("SALARY");

                    User user = new User(firstName, lastname, gender, salary, id);

                    arrayList.add(user);
                    adapter = new UserAdapter(MainActivity.this, arrayList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Toast.makeText(MainActivity.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void PostApi(String url) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url,
                response -> Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("FIRSTNAME", editFirstName.getText().toString());
                params.put("LASTNAME", editLastName.getText().toString());
                params.put("GENDER", editGender.getText().toString());
                params.put("SALARY", editSalary.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
