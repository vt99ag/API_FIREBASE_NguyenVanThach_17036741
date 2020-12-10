package com.activity.api_firebase;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    String url = "https://5fd1be42b485ea0016eeea83.mockapi.io/user/";
    private ArrayList<User> users;
    private LayoutInflater mInflater;


    public UserAdapter(Context context, ArrayList<User> users) {
        this.users = users;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(R.layout.item, parent, false);
        return new UserViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserAdapter.UserViewHolder holder, int i) {
        final String tvFirst = users.get(i).getFirstName();
        final String tvlastName = users.get(i).getLastName();
        final String tvSalary = users.get(i).getSalary();
        final String tvGender = users.get(i).getGender();
        final String id = users.get(i).getId();

        holder.tvLastName.setText(tvlastName);
        holder.tvFirstName.setText(tvFirst);
        holder.tvGender.setText(tvGender);
        holder.tvSalary.setText(tvSalary);

        holder.btnSua.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(holder.itemView.getContext());
            dialog.setContentView(R.layout.dialog_sua);
            dialog.show();

            final EditText editFirstName = dialog.findViewById(R.id.editLastName);
            final EditText editLastName = dialog.findViewById(R.id.editFirstName);
            final EditText editGender = dialog.findViewById(R.id.editGender);
            final EditText editSalary = dialog.findViewById(R.id.edtSalary);
            Button btnSua = dialog.findViewById(R.id.btnSua);

            editFirstName.setText(tvFirst);
            editLastName.setText(tvlastName);
            editGender.setText(tvGender);
            editSalary.setText(tvSalary);

            btnSua.setOnClickListener(view1 -> {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.PUT,
                        url + '/' + id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(holder.itemView.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    }
                }, error -> Toast.makeText(holder.itemView.getContext(), "Error by Post data!", Toast.LENGTH_SHORT).show()) {
                    @Override
                    protected Map<String, String> getParams()
                            throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("FIRSTNAME", editLastName.getText().toString());
                        params.put("LASTNAME", editLastName.getText().toString());
                        params.put("GENDER", editGender.getText().toString());
                        params.put("SALARY", editSalary.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(holder.itemView.getContext());
                requestQueue.add(stringRequest);
                dialog.cancel();
            });
        });

        holder.btnDelete.setOnClickListener(view -> {
            StringRequest stringRequest = new StringRequest(
                    Request.Method.DELETE, url + '/' + id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(holder.itemView.getContext(), "Successfully", Toast.LENGTH_SHORT).show();
                }
            }, error -> Toast.makeText(holder.itemView.getContext(), "Error by Post data!", Toast.LENGTH_SHORT).show());
            RequestQueue requestQueue = Volley.newRequestQueue(holder.itemView.getContext());
            requestQueue.add(stringRequest);

        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvLastName;
        public final TextView tvFirstName;
        public final TextView tvGender;
        public final TextView tvSalary;
        public final Button btnSua, btnDelete;
        public final UserAdapter adapter;


        public UserViewHolder(@NonNull View itemView, UserAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnSua = itemView.findViewById(R.id.btnSua);
        }
    }


}
