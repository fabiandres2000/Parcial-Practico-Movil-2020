package com.example.parcial_practico;

import android.graphics.Color;
import android.os.Bundle;
import cn.pedant.SweetAlert.SweetAlertDialog;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarNotaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarNotaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListarNotaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LnotaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarNotaFragment newInstance(String param1, String param2) {
        ListarNotaFragment fragment = new ListarNotaFragment();
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

    ListView lista;
    ArrayList<String> listaInformacion;
    ArrayList<Nota> notas;
    databaseHelper con;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lnota, container, false);
        lista = view.findViewById(R.id.lista_notas);

        con = new databaseHelper(this.getContext(),"parcial",null,1);

        consultarLista();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eliminar(i);
            }
        });
        return view;
    }

    SweetAlertDialog dialogo;

    public void  consultarLista(){
        notas = new ArrayList<Nota>();
        dialogo = new SweetAlertDialog(this.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        dialogo.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialogo.setTitleText("Espere ...");
        dialogo.setCancelable(true);
        dialogo.show();

        String url="https://parcial2movil.000webhostapp.com/listar_notas.php";
        url = url.replace(" ","%20");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("notas");
                JSONObject jsonObject = null;
                try {
                    //obtengo la lista de notas
                    for (int i = 0;i<json.length(); i++ ){
                        Nota nota = new Nota();
                        jsonObject = json.getJSONObject(i);
                        nota.setId(jsonObject.optInt("ID"));
                        nota.setAsignatura(jsonObject.optString("ASIGNATURA"));
                        nota.setCorte1(jsonObject.optDouble("CORTE1"));
                        nota.setCorte2(jsonObject.optDouble("CORTE2"));
                        nota.setCorte3(jsonObject.optDouble("CORTE3"));
                        nota.setNotafinal(jsonObject.optDouble("NOTA"));
                        notas.add(nota);
                    }

                    //lleno lalista de strig para despues adaptarlo al listview
                    listaInformacion = new ArrayList<String>();
                    for (int i = 0; i<notas.size();i++){
                        listaInformacion.add("   Asignatura: "+notas.get(i).asignatura+"\n"+
                                "   Corte1: "+notas.get(i).corte1+"\n"
                                +"   Corte2: "+notas.get(i).corte2+"\n"
                                +"   Corte3: "+notas.get(i).corte3+"\n"
                                +"   Nota final: "+notas.get(i).notafinal);
                    }

                    //adapto la lista al listview

                    ArrayAdapter adaptador = new ArrayAdapter(getContext(),R.layout.asignaturalayout,listaInformacion);
                    lista.setAdapter(adaptador);
                    dialogo.hide();

                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se pudo establecer conexión", Toast.LENGTH_LONG).show();
                    dialogo.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo establecer conexión", Toast.LENGTH_LONG).show();
                dialogo.hide();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(request);

    }


    public Nota nota_eliminar;
    String respuesta;
    public void eliminar(int pos){
        nota_eliminar = notas.get(pos);
        new SweetAlertDialog(this.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Está seguro de eliminar la nota?")
                .setContentText("Asignatura: "+nota_eliminar.asignatura+", Notafinal: "+nota_eliminar.notafinal)
                .setConfirmText("Eliminar!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                         int id = nota_eliminar.id;
                         String url="https://parcial2movil.000webhostapp.com/borrar_nota.php?id="+id;
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
                                new SweetAlertDialog(getContext())
                                        .setTitleText(respuesta)
                                        .show();

                                //refrescar listview


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
                .setCancelButton("Cancelar", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}