package com.example.parcial_practico;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        ArrayAdapter adaptador = new ArrayAdapter(this.getContext(),R.layout.asignaturalayout,listaInformacion);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eliminar(i);
            }
        });
        return view;
    }

    public void  consultarLista(){
        SQLiteDatabase db = con.getReadableDatabase();
        Nota nota = null;
        notas = new ArrayList<Nota>();
        Cursor cursor = db.rawQuery("SELECT * FROM nota",null);

        while (cursor.moveToNext()){
            nota = new Nota();
            nota.setId(cursor.getInt(0));
            nota.setAsignatura(cursor.getString(1));
            nota.setCorte1(cursor.getDouble(2));
            nota.setCorte2(cursor.getDouble(3));
            nota.setCorte3(cursor.getDouble(4));
            nota.setNotafinal(cursor.getDouble(5));
            notas.add(nota);
        }
        obtenerLista();
        db.close();
    }

    public  void obtenerLista(){
        listaInformacion = new ArrayList<String>();

        for (int i = 0; i<notas.size();i++){
            listaInformacion.add("   Asignatura: "+notas.get(i).asignatura+"\n"+
                    "   Corte1: "+notas.get(i).corte1+"\n"
                    +"   Corte2: "+notas.get(i).corte2+"\n"
                    +"   Corte3: "+notas.get(i).corte3+"\n"
                    +"   Nota final: "+notas.get(i).notafinal);
        }
    }

    public Nota nota_eliminar;
    public void eliminar(int pos){
        nota_eliminar = notas.get(pos);
        new SweetAlertDialog(this.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Esta seguro de que quiere eliminar la nota?")
                .setContentText("Asignatura: "+nota_eliminar.asignatura+", Notafinal: "+nota_eliminar.notafinal)
                .setConfirmText("Eliminar!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        SQLiteDatabase db = con.getReadableDatabase();
                        String[] id = {nota_eliminar.id+""};

                        db.delete("nota","ID=?",id);
                        Toast.makeText(getContext(),"Borrado correctamente",Toast.LENGTH_LONG).show();
                        consultarLista();
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
}