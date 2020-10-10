package com.example.parcial_practico;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    public  void guardarAsignatura(){
        con = new databaseHelper(this.getContext(),"parcial",null,1);
        SQLiteDatabase db = con.getWritableDatabase();
        if (codigo.getText().toString().equals("")  ||  nombre.getText().toString().equals("")){
            new SweetAlertDialog(this.getContext())
                    .setTitleText("LLene todos los datos")
                    .show();
        }else{
            ContentValues values = new ContentValues();
            values.put("CODIGO",codigo.getText().toString());
            values.put("NOMBRE",nombre.getText().toString());
            Long id_resultado = db.insert("asignatura","NOMBRE",values);
            new SweetAlertDialog(this.getContext())
                    .setTitleText("Asignaturas registradas hasta la fecha: "+id_resultado)
                    .show();
        }
        db.close();
    }

    public void actualizar_asignatura(){
        con = new databaseHelper(this.getContext(),"parcial",null,1);
        SQLiteDatabase db = con.getReadableDatabase();
        String[] codigo_ = {codigo.getText().toString()};

        ContentValues values = new ContentValues();
        values.put("NOMBRE",nombre.getText().toString());

        db.update("asignatura",values,"CODIGO=?",codigo_);
        Toast.makeText(this.getContext(),"Actualizado correctamente",Toast.LENGTH_LONG).show();
        db.close();
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
                        SQLiteDatabase db = con.getReadableDatabase();
                        String[] codigo_ = {codigo.getText().toString()};

                        db.delete("asignatura","CODIGO=?",codigo_);
                        Toast.makeText(getContext(),"Borrado correctamente",Toast.LENGTH_LONG).show();
                        codigo.setText("");
                        nombre.setText("");
                        db.close();
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

    public void buscar(){
        con = new databaseHelper(this.getContext(),"parcial",null,1);
        SQLiteDatabase db = con.getReadableDatabase();
        String[] campos ={"CODIGO","NOMBRE"};
        String[] selector = {codigo.getText().toString()};
        Cursor cursor = db.query("asignatura", campos, "CODIGO=?",selector,null,null,null);
        cursor.moveToFirst();
        try {
            nombre.setText(cursor.getString(1));
            codigo.setEnabled(false);
        }catch (Exception e){
            new SweetAlertDialog(this.getContext())
                    .setTitleText("No existe ningun registro con ese codigo ")
                    .show();
            codigo.setText("");
            nombre.setText("");
        }
        db.close();
    }

}