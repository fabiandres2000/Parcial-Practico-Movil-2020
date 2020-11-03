package com.example.parcial_practico;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GestionAsignaturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GestionAsignaturaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GestionAsignaturaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RasignaturaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GestionAsignaturaFragment newInstance(String param1, String param2) {
        GestionAsignaturaFragment fragment = new GestionAsignaturaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText codigo,nombre;
    Button guardar,buscarasig,borrar_asig,actualizar_asig;
    databaseHelper con;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rasignatura, container, false);
        nombre = view.findViewById(R.id.nombre);
        codigo = view.findViewById(R.id.codigo);
        buscarasig = view.findViewById(R.id.buscar_asig);
        guardar = view.findViewById(R.id.guardar);
        borrar_asig = view.findViewById(R.id.borrar_asignatura);
        actualizar_asig = view.findViewById(R.id.actualizar_asignatura);

        guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarAsignatura();
                }
        });

        buscarasig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }
        });

        borrar_asig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar_asignatura();
            }
        });

        actualizar_asig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar_asignatura();
            }
        });
        return view;
    }

    String respuesta;
    public  void guardarAsignatura(){
        dialogo = new SweetAlertDialog(this.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        dialogo.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialogo.setTitleText("Espere ...");
        dialogo.setCancelable(true);
        dialogo.show();

        String url="https://parcial2movil.000webhostapp.com/guardar_asignatura.php?nombre="+nombre.getText().toString()+"&codigo="+codigo.getText().toString();
        url = url.replace(" ","%20");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("respuesta");
                JSONObject jsonObject = null;
                try {
                    jsonObject = json.getJSONObject(0);
                    respuesta = jsonObject.optString("res");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialogo.hide();
                new SweetAlertDialog(getContext())
                        .setTitleText(respuesta)
                        .show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(error.toString())
                        .show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(request);
    }

    public void actualizar_asignatura(){
        dialogo = new SweetAlertDialog(this.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        dialogo.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialogo.setTitleText("Espere ...");
        dialogo.setCancelable(true);
        dialogo.show();

        String url="https://parcial2movil.000webhostapp.com/actualizar_asignatura.php?nombre="+nombre.getText().toString()+"&codigo="+codigo.getText().toString();
        url = url.replace(" ","%20");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("respuesta");
                JSONObject jsonObject = null;
                try {
                    jsonObject = json.getJSONObject(0);
                    respuesta = jsonObject.optString("res");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialogo.hide();
                new SweetAlertDialog(getContext())
                        .setTitleText(respuesta)
                        .show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(error.toString())
                        .show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(request);
    }

    public void borrar_asignatura(){
        con = new databaseHelper(this.getContext(),"parcial",null,1);
        new SweetAlertDialog(this.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Esta seguro de que quiere eliminar la asignatura?")
                .setContentText("no podra revertir esta accion!")
                .setConfirmText("Eliminar!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        dialogo = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                        dialogo.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        dialogo.setTitleText("Espere ...");
                        dialogo.setCancelable(true);
                        dialogo.show();

                        String url="https://parcial2movil.000webhostapp.com/borrar_asignatura.php?codigo="+codigo.getText().toString();
                        url = url.replace(" ","%20");
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray json = response.optJSONArray("respuesta");
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = json.getJSONObject(0);
                                    respuesta = jsonObject.optString("res");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                dialogo.hide();
                                new SweetAlertDialog(getContext())
                                        .setTitleText(respuesta)
                                        .show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText(error.toString())
                                        .show();
                            }
                        });
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(request);
                    }
                })
                .setCancelButton("Calcelar", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    SweetAlertDialog dialogo;
    JsonObjectRequest jsonObjectRequest;
    public void buscar(){
        dialogo = new SweetAlertDialog(this.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        dialogo.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialogo.setTitleText("Espere ...");
        dialogo.setCancelable(true);
        dialogo.show();

        String url="https://parcial2movil.000webhostapp.com/buscar_asignatura.php?codigo="+codigo.getText().toString();
        url = url.replace(" ","%20");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("asignatura");
                JSONObject jsonObject = null;
                try {
                        Asignatura asignatura = new Asignatura();
                        jsonObject = json.getJSONObject(0);
                        asignatura.setCodigo(jsonObject.optString("codigo"));
                        asignatura.setNombre(jsonObject.optString("nombre"));
                        nombre.setText(asignatura.getNombre());
                        dialogo.hide();
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "no se pudo establecer conexion", Toast.LENGTH_LONG).show();
                    dialogo.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(error.toString())
                        .show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(request);
    }

}