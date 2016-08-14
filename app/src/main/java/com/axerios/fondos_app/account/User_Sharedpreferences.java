package com.axerios.fondos_app.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ww_er on 10/12/2015.
 */
public class User_Sharedpreferences {

    Context context=null;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String preferencia = "";

    public User_Sharedpreferences(Context context, String preferencia){
        this.context = context;
        this.preferencia = preferencia;
    }

    public void Guardar_string(String nombre,String valor){
        String cadena = Leer_string(nombre);
        settings =  context.getSharedPreferences(preferencia, 0);
        editor = settings.edit();
        if(cadena!=null){
            cadena += "/";
            cadena += valor;
            editor.putString(nombre, cadena);
        }else{
            editor.putString(nombre, valor);
        }
        editor.apply();
    }

    public void Guardar_string_individual(String nombre,String valor){
        settings =  context.getSharedPreferences(preferencia, 0);
        editor = settings.edit();
        editor.putString(nombre, valor);
        editor.apply();
    }

    public void Guardar_int(String nombre,int valor_int){
        settings =  context.getSharedPreferences(preferencia, 0);
        editor = settings.edit();
        editor.putInt(nombre, valor_int);
        editor.apply();
    }

    public String Leer_string(String nombre){
        settings = context.getSharedPreferences(preferencia, 0);
        String val = settings.getString(nombre, null);
        return val;
    }

    public int Leer_int(String nombre){
        settings = context.getSharedPreferences(preferencia, 0);
        int val = settings.getInt(nombre, 0);
        return val;
    }

    public boolean Existente_string(String nombre){
        settings = context.getSharedPreferences(preferencia, 0);
        String val = settings.getString(nombre, null);
        if(val!=null){
            return true;
        }else {
            return false;
        }
    }

    public boolean Existente_id(String nombre, String valor){
        settings = context.getSharedPreferences(preferencia, 0);
        String val = settings.getString(nombre, null);
        if(val!=null){
            String[] array = val.split("/");
            for(int i = 0;i< array.length;i++) {
                if (valor.equals(String.valueOf(array[i]))) {
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }

    public boolean Existente_int_0(String nombre){
        settings = context.getSharedPreferences(preferencia, 0);
        int val=0;
        val = settings.getInt(nombre, 0);
        String val_null = String.valueOf(val);
        if(val_null!=null&&val!=0){
            return true;
        }else{
            return false;
        }
    }

    public boolean Existente_int(String nombre){
        settings = context.getSharedPreferences(preferencia, 0);
        int val=0;
        val = settings.getInt(nombre, 0);
        String val_null = String.valueOf(val);
        if(val_null!=null){
            return true;
        }else{
            return false;
        }
    }

    public boolean Borrar_String_elemento3(String nombre, String valor){
        if(Existente_string(nombre)){
            String cadena = Leer_string(nombre);
            if(cadena!=null) {
                String[] array = cadena.split("/");
                if (array.length > 3) {
                    String cadena_nueva = null;
                    for (int i = 0; i < array.length; i += 3) {
                        if (!valor.equals(String.valueOf(array[i]))) {
                            if (cadena_nueva != null) {
                                cadena_nueva += "/";
                                cadena_nueva += array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                            } else {
                                cadena_nueva = array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                            }
                        }
                    }
                    settings = context.getSharedPreferences(preferencia, 0);
                    editor = settings.edit();
                    editor.putString(nombre, cadena_nueva);
                    editor.apply();
                    return true;
                } else if(array.length==3) {
                    settings = context.getSharedPreferences(preferencia, 0);
                    settings.edit().remove(nombre).commit();
                    return true;
                }else {
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean Borrar_String_elemento6(String nombre, String valor, String valor_cadena){
        if(Existente_string(nombre)){
            String cadena = Leer_string(nombre);
            if(cadena!=null) {
                String[] array = cadena.split("/");
                for (int i = 0; i < array.length; i++) {
                    Log.e("---------Array", String.valueOf(array[i]));
                }
                if (array.length > 6) {
                    String cadena_nueva = null;
                    for (int i = 0; i < array.length; i += 6) {
                        if (!valor.equals(String.valueOf(array[i]))) {
                            if (cadena_nueva != null) {
                                cadena_nueva += "/";
                                cadena_nueva += array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 3];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 4];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 5];
                            } else  {
                                cadena_nueva = array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 3];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 4];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 5];
                            }
                        }else{
                            if(cadena_nueva!=null) {
                                cadena_nueva += "/";
                                cadena_nueva += valor_cadena;
                            }else {
                                cadena_nueva = valor_cadena;
                            }
                        }
                    }
                    settings = context.getSharedPreferences(preferencia, 0);
                    editor = settings.edit();
                    editor.putString(nombre, cadena_nueva);
                    editor.apply();
                    return true;
                } else if(array.length==6) {
                    settings = context.getSharedPreferences(preferencia, 0);
                    editor = settings.edit();
                    editor.putString(nombre, valor_cadena);
                    editor.apply();
                    return true;
                }else {
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    public boolean Borrar_String_elemento4(String nombre, String valor, String cadena_nva){
        if(Existente_string(nombre)){
            String cadena = Leer_string(nombre);
            if(cadena!=null) {
                String[] array = cadena.split("/");
                if (array.length > 4) {
                    String cadena_nueva = null;
                    for (int i = 0; i < array.length; i += 4) {
                        if (valor.equals(String.valueOf(array[i]))) {
                            if (cadena_nueva != null) {
                                cadena_nueva += "/";
                                cadena_nueva += cadena_nva;
                            } else {
                                cadena_nueva = cadena_nva;
                            }
                        }else{
                            if (cadena_nueva != null) {
                                cadena_nueva += "/";
                                cadena_nueva += array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 3];;
                            } else {
                                cadena_nueva = array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 3];;
                            }
                        }
                    }
                    settings = context.getSharedPreferences(preferencia, 0);
                    editor = settings.edit();
                    editor.putString(nombre, cadena_nueva);
                    editor.apply();
                    return true;
                } else if(array.length==4) {
                    String cadena_nueva = null;
                    cadena_nueva = cadena_nva;

                    settings = context.getSharedPreferences(preferencia, 0);
                    editor = settings.edit();
                    editor.putString(nombre, cadena_nueva);
                    editor.apply();
                    return true;
                }else {
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean Borrar_String_elemento4_individual(String nombre, String valor){
        if(Existente_string(nombre)){
            String cadena = Leer_string(nombre);
            if(cadena!=null) {
                String[] array = cadena.split("/");
                if (array.length > 4) {
                    String cadena_nueva = null;
                    for (int i = 0; i < array.length; i += 4) {
                        if (!valor.equals(String.valueOf(array[i]))) {
                            if (cadena_nueva != null) {
                                cadena_nueva += "/";
                                cadena_nueva += array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 3];
                            } else {
                                cadena_nueva = array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 3];
                            }
                        }
                    }
                    settings = context.getSharedPreferences(preferencia, 0);
                    editor = settings.edit();
                    editor.putString(nombre, cadena_nueva);
                    editor.apply();
                    return true;
                } else if(array.length==4) {
                    settings = context.getSharedPreferences(preferencia, 0);
                    settings.edit().remove(nombre).commit();
                    return true;
                }else {
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean Borrar_String_elemento6_inicial(String nombre, String valor){
        if(Existente_string(nombre)){
            String cadena = Leer_string(nombre);
            if(cadena!=null) {
                String[] array = cadena.split("/");
                for (int i = 0; i < array.length; i++) {
                    Log.e("---------Array", String.valueOf(array[i]));
                }
                Log.e("---------Array",String.valueOf(array.length));
                if (array.length > 6) {
                    String cadena_nueva = null;
                    for (int i = 6; i < array.length; i += 6) {
                        if (i==6) {
                                cadena_nueva = array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 3];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 4];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 5];
                            }else if(i>6){
                                cadena_nueva += "/";
                                cadena_nueva += array[i];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 1];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 2];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 3];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 4];
                                cadena_nueva += "/";
                                cadena_nueva += array[i + 5];
                        }
                    }
                    settings = context.getSharedPreferences(preferencia, 0);
                    editor = settings.edit();
                    editor.putString(nombre, cadena_nueva);
                    editor.apply();
                    return true;
                }else {
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean Borrar_String_elemento1(String nombre, String valor){
        if(Existente_string(nombre)){
            String cadena = Leer_string(nombre);
            if(cadena!=null){
                String[] array = cadena.split("/");
                String cadena_nueva = null;

                    for (int i = 0; i < array.length; i ++) {
                        if (!valor.equals(String.valueOf(array[i]))) {
                            if (cadena_nueva != null) {
                                cadena_nueva += "/";
                                cadena_nueva += array[i];
                            } else {
                                cadena_nueva = array[i];
                            }
                        }
                    }
                settings = context.getSharedPreferences(preferencia, 0);
                editor = settings.edit();
                editor.putString(nombre, cadena_nueva);
                editor.apply();
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean Borrar_String(String nombre){
        if(Existente_string(nombre)){
            settings = context.getSharedPreferences(preferencia, 0);
            settings.edit().remove(nombre).commit();
            return true;
        }else{
            return false;
        }
    }
    public boolean Borrar_int(String nombre){
        if(Existente_string(nombre)){
            settings = context.getSharedPreferences(preferencia, 0);
            settings.edit().remove(nombre).commit();
            return true;
        }else{
            return false;
        }
    }



}
