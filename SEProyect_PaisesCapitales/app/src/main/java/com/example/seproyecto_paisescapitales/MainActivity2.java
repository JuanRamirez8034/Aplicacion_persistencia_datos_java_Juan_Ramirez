package com.example.seproyecto_paisescapitales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private TextView txtPrincipal2;
    private EditText editCapital, editPais;
    private Button btnGuardar, btnRegresar;
    private Spinner spinner;
    ArrayList<String> capitales2 = new ArrayList<>();
    ArrayList<String> paises2 = new ArrayList<>();
    String valor1, valor2;
    String filename1, filename2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.txtPrincipal2 = (TextView)findViewById(R.id.txtPrincipal2);
        this.editCapital = (EditText) findViewById(R.id.editCapital);
        this.editPais = (EditText)findViewById(R.id.editPais);
        this.btnGuardar = (Button)findViewById(R.id.idGuardar);
        this.btnRegresar = (Button)findViewById(R.id.idRegresar);
        this.spinner = (Spinner)findViewById(R.id.spinner2);

        traerDatos();
        //Mostramos el array de artículos dentro del Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity2.this, R.layout.spinner_item, paises2);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_iten_dropdown);
        spinner.setAdapter(spinnerAdapter);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valor1 = editCapital.getText().toString();
                valor2 = editPais.getText().toString();
                if(valor1.isEmpty() && valor2.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Ingrese los datos, por favor", Toast.LENGTH_LONG).show();
                }else{
                    escribirCapital(valor1);
                    escribirPais(valor2);
                    //Mostramos el array de artículos dentro del Spinner
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity2.this, R.layout.spinner_item, paises2);
                    spinnerAdapter.setDropDownViewResource(R.layout.spinner_iten_dropdown);
                    spinner.setAdapter(spinnerAdapter);
                }
            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mandamos al otro Segundo Activity los datos ingresados
                Intent next = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(next);
            }
        });
    }
    public void traerDatos(){
        //Traemos a este Activity los datos ingresados en el 1er Activity
        filename1 = getIntent().getStringExtra("filename1");
        filename2 = getIntent().getStringExtra("filename2");

        Bundle capitales = getIntent().getExtras();
        capitales2 = capitales.getStringArrayList("capitales");
        Bundle paises = getIntent().getExtras();
        paises2 = paises.getStringArrayList("paises");
    }
    public void escribirCapital(String valor){
        Archivo conexion = new Archivo();
        conexion.setFilename(MainActivity2.this, filename1);
        conexion.agregar(valor1, capitales2);
        capitales2 = conexion.getDatos();

        if (conexion.grabar(MainActivity2.this, capitales2)){
            Toast.makeText(MainActivity2.this, "Se grabó correctamente la capital", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(MainActivity2.this, "Error al grabar el dato", Toast.LENGTH_LONG).show();
        }
    }
    public void escribirPais(String valor){
        Archivo conexion = new Archivo();
        conexion.setFilename(MainActivity2.this, filename2);
        conexion.agregar(valor2, paises2);
        paises2 = conexion.getDatos();
        if (conexion.grabar(MainActivity2.this, paises2)){
            Toast.makeText(MainActivity2.this, "Se grabó correctamente el país", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(MainActivity2.this, "Error al grabar el dato", Toast.LENGTH_LONG).show();
        }
    }
}