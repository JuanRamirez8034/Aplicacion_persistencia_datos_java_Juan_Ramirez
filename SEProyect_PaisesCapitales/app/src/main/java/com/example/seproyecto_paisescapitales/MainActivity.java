package com.example.seproyecto_paisescapitales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import java.io.*;
import java.util.ArrayList;

//case principal
public class MainActivity extends AppCompatActivity {
    private TextView txtPrincipal, txtResultado;
    private RadioGroup rdgroup;
    private RadioButton rb1, rb2;
    private EditText editValor;
    private Button btn, btn2;
    private Spinner spinner;

    //declaracion de variables
    ArrayList<String> capitales2 = new ArrayList<>();
    ArrayList<String> paises2 = new ArrayList<>();
    String filename1 = "capitales.txt";
    String filename2 = "paises.txt";
    String valor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conectando la parte app con parte visual
        this.txtPrincipal = (TextView) findViewById(R.id.txtPrincipal);
        this.txtResultado = (TextView) findViewById(R.id.idmsjText);
        this.editValor = (EditText) findViewById(R.id.editValor);
        this.btn = (Button) findViewById(R.id.idbtn);
        this.btn2 = (Button) findViewById(R.id.idbtn2);
        this.rdgroup = (RadioGroup) findViewById(R.id.rdgroup);
        this.rb1 = (RadioButton) findViewById(R.id.rdbutton1);
        this.rb2 = (RadioButton) findViewById(R.id.rdbutton2);
        this.spinner = (Spinner) findViewById(R.id.spinner);

        verificarArchivo1();
        verificarArchivo2();

        rdgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rdbutton1) {
                    editValor.setHint("Ingrese la capital");
                    //array de artículos dentro del Spinner
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, capitales2);
                    spinnerAdapter.setDropDownViewResource(R.layout.spinner_iten_dropdown);
                    spinner.setAdapter(spinnerAdapter);

                } else if (checkedId == R.id.rdbutton2) {
                    editValor.setHint("Ingrese el país");
                    //array de artículos dentro del Spinner
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, paises2);
                    spinnerAdapter.setDropDownViewResource(R.layout.spinner_iten_dropdown);
                    spinner.setAdapter(spinnerAdapter);
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select el RadioButton de Capital 
                if (rb1.isChecked()) {
                    valor = editValor.getText().toString();
                    if (valor.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Ingrese una capital, por favor", Toast.LENGTH_LONG).show();
                    } else {comparaCapitales(valor);}

                //Select RadioButton de País
                } else if (rb2.isChecked()) {
                    valor = editValor.getText().toString();
                    if (valor.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Ingrese un país, por favor", Toast.LENGTH_LONG).show();
                    } else {comparaPaises(valor);}

                //De resto, hacer...
                }else{
                    Toast.makeText(MainActivity.this, "Seleccione una opción, por favor", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void verificarArchivo1() {
        Archivo conexion = new Archivo();
        conexion.setFilename(MainActivity.this, filename1);

        //Verificndo la existencia del archivo
        if (conexion.verificarArch(this)) {

            //lo leemos
            if (conexion.leer(this)) {
                //Después de leerlo pasamos los datos al ArrayList
                capitales2 = conexion.getDatos();
            } else {
                //mensaje de error
                Toast.makeText(MainActivity.this, "Error al obtener información", Toast.LENGTH_LONG).show();
            }
        } else {
            //no existe el archivo sale un mensaje
            Toast.makeText(MainActivity.this, "No existe el archivo capitales.txt", Toast.LENGTH_LONG).show();
            //Si no existe el archivo se hace visible el botón para ir al Activity donde agregamos los datos
            btn2.setVisibility(View.VISIBLE);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Mandamos al Segundo Activity los datos necesarios
                    Bundle capitales = new Bundle();
                    capitales.putStringArrayList("capitales", capitales2);
                    Bundle paises = new Bundle();
                    paises.putStringArrayList("paises", paises2);

                    Intent next = new Intent(MainActivity.this, MainActivity2.class);
                    next.putExtra("filename1", filename1);
                    next.putExtra("filename2", filename2);

                    next.putExtras(capitales);
                    next.putExtras(paises);
                    startActivity(next);
                }
            });
        }
    }

    public void verificarArchivo2() {
        Archivo conexion = new Archivo();
        conexion.setFilename(MainActivity.this, filename2);
        if (conexion.verificarArch(this)) {
            if (conexion.leer(this)) {
                paises2 = conexion.getDatos();
            } else {
                Toast.makeText(MainActivity.this, "Error al obtener información", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "No existe el archivo paises.txt", Toast.LENGTH_LONG).show();
            btn2.setVisibility(View.VISIBLE);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Mandamos al otro Segundo Activity los datos ingresados
                    Bundle capitales = new Bundle();
                    capitales.putStringArrayList("capitales", capitales2);
                    Bundle paises = new Bundle();
                    paises.putStringArrayList("paises", paises2);

                    Intent next = new Intent(MainActivity.this, MainActivity2.class);
                    next.putExtra("filename1", filename1);
                    next.putExtra("filename2", filename2);

                    next.putExtras(capitales);
                    next.putExtras(paises);
                    startActivity(next);
                }
            });
        }
    }

    public void comparaCapitales(String capital) {
        int n = 0;
        String msj;
        for (int i = 0; i < capitales2.size(); i++) {
            if (capitales2.get(i).equals(capital)) {
                n = i;
            }
        }
        if (capitales2.get(n).equals(capital)) {
            msj = "La capital que ingresaste es: " + capital + ", y su país es: " + paises2.get(n);
            txtResultado.setText(msj);
        } else {
            msj = "Lo sentimos, la capital que está ingresando no la conocemos." +
                    "\n¿Quiere ayudarnos a agrandar nuestros conocimientos?";
            txtResultado.setText(msj);

            btn2.setVisibility(View.VISIBLE);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Mandamos al otro Segundo Activity los datos ingresados
                    Bundle capitales = new Bundle();
                    capitales.putStringArrayList("capitales", capitales2);
                    Bundle paises = new Bundle();
                    paises.putStringArrayList("paises", paises2);

                    Intent next = new Intent(MainActivity.this, MainActivity2.class);
                    next.putExtra("filename1", filename1);
                    next.putExtra("filename2", filename2);

                    next.putExtras(capitales);
                    next.putExtras(paises);
                    startActivity(next);
                }
            });
        }
    }

    public void comparaPaises(String pais) {
        int n = 0;
        String msj;
        for (int i = 0; i < paises2.size(); i++) {
            if (paises2.get(i).equals(pais)) {
                n = i;
            }
        }
        if (paises2.get(n).equals(pais)) {
            msj = "El país que ingresaste es: " + pais + ", y su capital es: " + capitales2.get(n);
            txtResultado.setText(msj);
        } else {
            msj = "Lo sentimos, el país que está ingresando no la conocemos." +
                    "\n¿Quiere ayudarnos a agrandar nuestros conocimientos?";
            txtResultado.setText(msj);
            btn2.setVisibility(View.VISIBLE);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Mandamos al otro Segundo Activity los datos ingresados
                    Bundle capitales = new Bundle();
                    capitales.putStringArrayList("capitales", capitales2);
                    Bundle paises = new Bundle();
                    paises.putStringArrayList("paises", paises2);

                    Intent next = new Intent(MainActivity.this, MainActivity2.class);
                    next.putExtra("filename1", filename1);
                    next.putExtra("filename2", filename2);

                    next.putExtras(capitales);
                    next.putExtras(paises);
                    startActivity(next);
                }
            });
        }
    }
}