package com.example.parcial_practico;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
 * Use the {@link GestionNotaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GestionNotaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GestionNotaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RnotaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GestionNotaFragment newInstance(String param1, String param2) {
        GestionNotaFragment fragment = new GestionNotaFragment();
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
    Spinner lista;


    ArrayList<String> listaInformacion;
    ArrayList<Asignatura> asignaturas;
    databaseHelper con;

    Button  guardar_nota,mostrar,mostrar2,mostrar3;
    Button agregarnota1,agregarnota2,agregarnota3;
    LinearLayout oculto_,oculto2_,oculto3_;
    EditText nota1,porcentaje1,actividad1;
    EditText nota2,porcentaje2,actividad2;
    EditText nota3,porcentaje3,actividad3;
    TextView utilizado1,utilizado2,utilizado3;
    TextView fcorte1,fcorte2,fcorte3;
    int porcentaje_total1=0;
    int porcentaje_total2=0;
    int porcentaje_total3=0;
    double definitiva_corte1=0;
    double definitiva_corte2=0;
    double definitiva_corte3=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_rnota, container, false);
        lista = view.findViewById(R.id.spiner_asignatura);
        guardar_nota = view.findViewById(R.id.guardar_nota);
        mostrar = view.findViewById(R.id.mostrar);
        mostrar2 = view.findViewById(R.id.mostrar2);
        mostrar3 = view.findViewById(R.id.mostrar3);
        oculto_ = view.findViewById(R.id.oculto);
        oculto2_ = view.findViewById(R.id.oculto2);
        oculto3_ = view.findViewById(R.id.oculto3);

        //botones
        agregarnota1 = view.findViewById(R.id.agregar_nota_corte1);
        agregarnota2 = view.findViewById(R.id.agregar_nota_corte2);
        agregarnota3 = view.findViewById(R.id.agregar_nota_corte3);
        //campos de entrada
        nota1 = view.findViewById(R.id.notacorte1);
        nota2 = view.findViewById(R.id.notacorte2);
        nota3 = view.findViewById(R.id.notacorte3);
        porcentaje1 = view.findViewById(R.id.porcentajecorte1);
        porcentaje2 = view.findViewById(R.id.porcentajecorte2);
        porcentaje3 = view.findViewById(R.id.porcentajecorte3);
        actividad1 = view.findViewById(R.id.actividad1);
        actividad2 = view.findViewById(R.id.actividad2);
        actividad3 = view.findViewById(R.id.actividad3);
        //textos que muestran porcentaje
        utilizado1 = view.findViewById(R.id.porcentaje_utilizado1);
        utilizado2 = view.findViewById(R.id.porcentaje_utilizado2);
        utilizado3 = view.findViewById(R.id.porcentaje_utilizado3);
        //
        fcorte1 = view.findViewById(R.id.finalcorte1);
        fcorte2 = view.findViewById(R.id.finalcorte2);
        fcorte3 = view.findViewById(R.id.finalcorte3);

        con = new databaseHelper(this.getContext(),"parcial",null,1);
        llenarspinner();

        guardar_nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar_nota();
            }
        });

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oculto_.setVisibility(View.VISIBLE);
                oculto2_.setVisibility(view.GONE);
                oculto3_.setVisibility(view.GONE);
            }
        });

        mostrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oculto_.setVisibility(View.GONE);
                oculto2_.setVisibility(view.VISIBLE);
                oculto3_.setVisibility(view.GONE);
            }
        });

        mostrar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oculto_.setVisibility(View.GONE);
                oculto2_.setVisibility(view.GONE);
                oculto3_.setVisibility(view.VISIBLE);
            }
        });

        agregarnota1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarnotacorte1();
            }
        });

        agregarnota2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarnotacorte2();
            }
        });

        agregarnota3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarnotacorte3();
            }
        });
        return  view;

    }

    public void  llenarspinner(){

        asignaturas = new ArrayList<Asignatura>();
        dialogo = new SweetAlertDialog(this.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        dialogo.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialogo.setTitleText("Espere ...");
        dialogo.setCancelable(true);
        dialogo.show();

        String url="https://parcial2movil.000webhostapp.com/listar_asignaturas.php";
        url = url.replace(" ","%20");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("asignatura");
                JSONObject jsonObject = null;

                try {
                    //obtengo la lista de materias
                    for (int i = 0;i<json.length(); i++ ){
                        Asignatura asignatura = new Asignatura();
                        jsonObject = json.getJSONObject(i);
                        asignatura.setCodigo(jsonObject.optString("codigo"));
                        asignatura.setNombre(jsonObject.optString("nombre"));
                        asignaturas.add(asignatura);
                    }

                    //lleno lalista de strig para despues adaptarlo al spiner
                    listaInformacion = new ArrayList<String>();
                    for (int i = 0; i<asignaturas.size();i++){
                        listaInformacion.add(asignaturas.get(i).Nombre);
                    }

                    //adapto la lista al spinner
                    ArrayAdapter adaptador = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,listaInformacion);
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


    public void agregarnotacorte1(){
        double porcentaje_temporal = porcentaje_total1+Integer.parseInt(porcentaje1.getText().toString());
        if (porcentaje_temporal<=100){
            if (Double.parseDouble(nota1.getText().toString())>=0 && Double.parseDouble(nota1.getText().toString())<=5){
                porcentaje_total1 = porcentaje_total1+Integer.parseInt(porcentaje1.getText().toString());

                double nota = Double.parseDouble(nota1.getText().toString());
                Integer porcentaje = Integer.parseInt(porcentaje1.getText().toString());

                //voy sacando la definitiva del corte 1;
                definitiva_corte1 = definitiva_corte1+(nota*porcentaje/100);
                utilizado1.setText("porcentaje utilizado : "+porcentaje_total1+"%");
                fcorte1.setText("nota del corte1: "+definitiva_corte1);
            }else{
                new SweetAlertDialog(this.getContext()).setTitleText("las notas deben estar entre 0 y 5").show();
            }
        }else{
            new SweetAlertDialog(this.getContext()).setTitleText("No puede utilizar este porcentaje debido a que la sumatoria pasaría de 100%").show();
        }
    }

    public void agregarnotacorte2(){
        double porcentaje_temporal = porcentaje_total2+Integer.parseInt(porcentaje2.getText().toString());
        if (porcentaje_temporal<=100){
            if (Double.parseDouble(nota2.getText().toString())>=0 && Double.parseDouble(nota2.getText().toString())<=5){
                porcentaje_total2 = porcentaje_total2+Integer.parseInt(porcentaje2.getText().toString());

                double nota = Double.parseDouble(nota2.getText().toString());
                Integer porcentaje = Integer.parseInt(porcentaje2.getText().toString());

                //voy sacando la definitiva del corte 2;
                definitiva_corte2 = definitiva_corte2+(nota*porcentaje/100);
                utilizado2.setText("porcentaje utilizado : "+porcentaje_total2+"%");
                fcorte2.setText("nota del corte2: "+definitiva_corte2);
            }else{
                new SweetAlertDialog(this.getContext()).setTitleText("Las notas deben estar entre 0 y 5").show();
            }
        }else{
            new SweetAlertDialog(this.getContext()).setTitleText("No puede utilizar este porcentaje debido a que la sumatoria pasaría de 100%").show();
        }
    }

    public void agregarnotacorte3(){
        double porcentaje_temporal = porcentaje_total3+Integer.parseInt(porcentaje3.getText().toString());
        if (porcentaje_temporal<=100){
            if (Double.parseDouble(nota3.getText().toString())>=0 && Double.parseDouble(nota3.getText().toString())<=5){
                porcentaje_total3 = porcentaje_total3+Integer.parseInt(porcentaje3.getText().toString());

                double nota = Double.parseDouble(nota3.getText().toString());
                Integer porcentaje = Integer.parseInt(porcentaje3.getText().toString());

                //voy sacando la definitiva del corte 3;
                definitiva_corte3 = definitiva_corte3+(nota*porcentaje/100);
                utilizado3.setText("porcentaje utilizado : "+porcentaje_total3+"%");
                fcorte3.setText("nota del corte3: "+definitiva_corte3);
            }else{
                new SweetAlertDialog(this.getContext()).setTitleText("Las notas deben estar entre 0 y 5").show();
            }
        }else{
            new SweetAlertDialog(this.getContext()).setTitleText("No puede utilizar este porcentaje debido a que la sumatoria pasaría de 100%").show();
        }
    }

    SweetAlertDialog dialogo;
    String respuesta;

    public void guardar_nota(){

        double notafinal = (definitiva_corte1*0.3+definitiva_corte2*0.3+definitiva_corte3*0.4);
        String asignatura = lista.getSelectedItem().toString();
        if (porcentaje_total1==100 && porcentaje_total2==100 && porcentaje_total3==100){

            ContentValues values = new ContentValues();
            values.put("ASIGNATURA",asignatura);
            values.put("CORTE1",definitiva_corte1);
            values.put("CORTE2",definitiva_corte2);
            values.put("CORTE3",definitiva_corte3);
            values.put("NOTA",notafinal);

            dialogo = new SweetAlertDialog(this.getContext(), SweetAlertDialog.PROGRESS_TYPE);
            dialogo.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialogo.setTitleText("Espere ...");
            dialogo.setCancelable(true);
            dialogo.show();

            String url="https://parcial2movil.000webhostapp.com/guardar_nota.php?asignatura="+asignatura+"&corte1="+definitiva_corte1+
                    "&corte2="+definitiva_corte2+"&corte3="+definitiva_corte3+"&nota="+notafinal;
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
            //reseteo valores
            porcentaje_total1=0;
            porcentaje_total2=0;
            porcentaje_total3=0;
            definitiva_corte1=0;
            definitiva_corte2=0;
            definitiva_corte3=0;
            nota1.setText("");
            nota3.setText("");
            nota2.setText("");
            porcentaje1.setText("");
            porcentaje2.setText("");
            porcentaje3.setText("");
            actividad1.setText("");
            actividad2.setText("");
            actividad3.setText("");
        }else{
            new SweetAlertDialog(this.getContext()).setTitleText("Alguno de los porcentajes del corte no suman 100%").show();
        }
    }
}