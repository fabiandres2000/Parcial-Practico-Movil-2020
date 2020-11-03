package com.example.parcial_practico;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarAsignaturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarAsignaturaFragment extends Fragment implements  Response.Listener<JSONObject>,Response.ErrorListener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListarAsignaturaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LasignaturaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarAsignaturaFragment newInstance(String param1, String param2) {
        ListarAsignaturaFragment fragment = new ListarAsignaturaFragment();
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
    databaseHelper con;
    SweetAlertDialog dialogo;
    JsonObjectRequest jsonObjectRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view =  inflater.inflate(R.layout.fragment_lasignatura, container, false);

        lista = view.findViewById(R.id.listaasignaturas);
        con = new databaseHelper(this.getContext(),"parcial",null,1);

        llenar_lista();

        return  view;
    }
    private void llenar_lista() {

        dialogo = new SweetAlertDialog(this.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        dialogo.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialogo.setTitleText("Espere ...");
        dialogo.setCancelable(true);
        dialogo.show();

        String url="https://parcial2movil.000webhostapp.com/listar_asignaturas.php";
        url = url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(jsonObjectRequest);
    }




    @Override
    public void onResponse(JSONObject response) {
        dialogo.hide();
        JSONArray json = response.optJSONArray("asignatura");
        JSONObject jsonObject = null;
        ArrayList<String> asignaturas = new ArrayList<>();
        try {
            for (int i = 0;i<json.length(); i++ ){
                Asignatura asignatura = new Asignatura();
                jsonObject = json.getJSONObject(i);
                asignatura.setCodigo(jsonObject.optString("codigo"));
                asignatura.setNombre(jsonObject.optString("nombre"));
                asignaturas.add("Codigo: "+asignatura.getCodigo()+"\n"+"Nombre: "+asignatura.getNombre());
            }
        }catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "no se pudo establecer conexion", Toast.LENGTH_LONG).show();
            dialogo.hide();
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this.getContext(), R.layout.asignaturalayout, asignaturas);
        lista.setAdapter(adaptador);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se pudo conectar debido a:"+error.toString(), Toast.LENGTH_LONG).show();
        dialogo.hide();
    }
}