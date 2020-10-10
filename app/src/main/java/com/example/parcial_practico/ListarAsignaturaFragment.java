package com.example.parcial_practico;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarAsignaturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarAsignaturaFragment extends Fragment {

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
    ArrayList<Asignatura> asignaturas;
    databaseHelper con;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view =  inflater.inflate(R.layout.fragment_lasignatura, container, false);

        lista = view.findViewById(R.id.listaasignaturas);
        con = new databaseHelper(this.getContext(),"parcial",null,1);

        consultarLista();

        ArrayAdapter adaptador = new ArrayAdapter(this.getContext(),R.layout.asignaturalayout,listaInformacion);
        lista.setAdapter(adaptador);

        return  view;
    }

    public void  consultarLista(){
        SQLiteDatabase db = con.getReadableDatabase();

        Asignatura asignatura = null;

        asignaturas = new ArrayList<Asignatura>();

        Cursor cursor = db.rawQuery("SELECT * FROM asignatura",null);

        while (cursor.moveToNext()){
            asignatura = new Asignatura();
            asignatura.setCodigo(cursor.getString(1));
            asignatura.setNombre(cursor.getString(2));
            asignaturas.add(asignatura);
        }
        obtenerLista();
    }

    public  void obtenerLista(){
        listaInformacion = new ArrayList<String>();

        for (int i = 0; i<asignaturas.size();i++){
            listaInformacion.add("   Codigo: "+asignaturas.get(i).codigo+"\n"+"   Nombre: "+asignaturas.get(i).Nombre);
        }
    }
}