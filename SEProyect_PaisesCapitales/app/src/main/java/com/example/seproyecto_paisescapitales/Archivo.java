package com.example.seproyecto_paisescapitales;

import android.app.Activity;
import android.content.Context;
import java.io.*;
import java.util.ArrayList;

public class Archivo {
    public String filename;
    public ArrayList<String> datos = new ArrayList<>();

    public void setFilename(Context context, String name){
        this.filename = name;
    }
    public boolean verificarArch(Context context){
        String [] archivos = context.fileList();
        for (String archivo : archivos){
            if (archivo.equals(filename)){
                return true;
            }
        }
        return false;
    }

    public boolean grabar(Context context, ArrayList<String> datos){
        try{
            OutputStreamWriter archivo = new OutputStreamWriter(context.openFileOutput(filename, Activity.MODE_PRIVATE));
            for(String dato : datos){
                archivo.write(dato+"\n");
            }
            archivo.flush();
            archivo.close();

        }catch (IOException e){
            return false;
        }
        return true;
    }

    public boolean leer(Context context){
        try{
            InputStreamReader archivo = new InputStreamReader(context.openFileInput(filename));
            BufferedReader br = new BufferedReader(archivo);
            String linea = "";
            while(linea!=null){
                linea = br.readLine();
                if(linea!=null){
                    datos.add(linea);
                }
            }
            br.close();
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public ArrayList<String> getDatos(){
        return datos;
    }

    public void agregar(String dato, ArrayList<String> datos){
        this.datos = datos;
        this.datos.add(dato);
    }


}
