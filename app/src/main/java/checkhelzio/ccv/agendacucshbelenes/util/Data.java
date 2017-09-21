package checkhelzio.ccv.agendacucshbelenes.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import checkhelzio.ccv.agendacucshbelenes.Eventos;
import checkhelzio.ccv.agendacucshbelenes.R;

public class Data {

    public static int getFondoAuditorio(String st_auditorio, String st_clase, String titulo, Context context) {

        if (titulo.contains("No se presentó - ")) {
            return context.getResources().getColor(R.color.color_no_presentado);
        }

        switch (st_auditorio) {
            case "1":
                if (st_clase.equals("C")) {
                    return context.getResources().getColor(R.color.color1c);
                } else {
                    return context.getResources().getColor(R.color.color1);
                }
            case "2":
                if (st_clase.equals("C")) {
                    return context.getResources().getColor(R.color.color2c);
                } else {
                    return context.getResources().getColor(R.color.color2);
                }
            case "3":
                if (st_clase.equals("C")) {
                    return context.getResources().getColor(R.color.color3c);
                } else {
                    return context.getResources().getColor(R.color.color3);
                }
            case "4":
                if (st_clase.equals("C")) {
                    return context.getResources().getColor(R.color.color4c);
                } else {
                    return context.getResources().getColor(R.color.color4);
                }
            case "5":
                if (st_clase.equals("C")) {
                    return context.getResources().getColor(R.color.color5c);
                } else {
                    return context.getResources().getColor(R.color.color5);
                }
            case "6":
                if (st_clase.equals("C")) {
                    return context.getResources().getColor(R.color.color6c);
                } else {
                    return context.getResources().getColor(R.color.color6);
                }
            default:
                return context.getResources().getColor(R.color.color_no_presentado);
        }
    }

    static String getStringFolio(int folio) {
        String stNuevoId = "" + (folio + 1);
        if (stNuevoId.length() == 1) {
            stNuevoId = "000" + stNuevoId;
        } else if (stNuevoId.length() == 2) {
            stNuevoId = "00" + stNuevoId;
        } else if (stNuevoId.length() == 3) {
            stNuevoId = "0" + stNuevoId;
        }
        return stNuevoId;
    }

    public static ArrayList<Eventos> getListaEventos(Context context) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Eventos>>() {
        }.getType();
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs_name), Context.MODE_PRIVATE);
        return gson.fromJson(prefs.getString(context.getResources().getString(R.string.prefs_lista_eventos), ""), type);
    }

    public static String getNombreAula(String st_aulas) {
        switch (st_aulas) {
            case "FBA 17":
                st_aulas = "Aula Dr. Fernando Pozos Ponce";
                break;
            case "FBC 21":
                st_aulas = "Sala de juntas 1 completa";
                break;
            case "FBC 22":
                st_aulas = "Sala de juntas 2 completa";
                break;
            case "FBC 23":
                st_aulas = "Salón de usos múltiples";
                break;
            case "FBF 2":
                st_aulas = "Área de uso libre";
                break;
            case "FBF2 1":
                st_aulas = "Laboratorio de SIG";
                break;
            case "FBF2 2":
                st_aulas = "Laboratorio CTA";
                break;
            case "FBC 21 N":
                st_aulas = "Sala de juntas 1 norte";
                break;
            case "FBC 22 N":
                st_aulas = "Sala de juntas 2 norte";
                break;
            case "FBC 21 S":
                st_aulas = "Sala de juntas 1 sur";
                break;
            case "FBC 22 S":
                st_aulas = "Sala de juntas 2 sur";
                break;
            case "FBD 22":
                st_aulas = "Auditorio";
                break;
            case "FBD 23":
                st_aulas = "CAG";
                break;
            case "FBD 24":
                st_aulas = "Computo 1er nivel";
                break;
            case "FBD 25":
                st_aulas = "Computo 2do nivel";
                break;
            case "FBD 26":
                st_aulas = "Computo 3er nivel";
                break;
            case "FBAD 1":
                st_aulas = "Cancha de fútbol";
                break;
            case "FBAD 2":
                st_aulas = "Cancha de básquetbol";
                break;
            case "FBAD 3":
                st_aulas = "Cancha de usos múltiples";
                break;
            case "FBAD 4":
                st_aulas = "Pista de Jogging";
                break;
            default:
                st_aulas = "Aula " + st_aulas;
                break;
        }
        return st_aulas;
    }

    public static String nombreAuditorio(String numero) {
        switch (numero) {
            case "1":
                return "Edificio A";
            case "2":
                return "Edificio B";
            case "3":
                return "Edificio C";
            case "4":
                return "Edificio D";
            case "5":
                return "Edificio F";
            case "6":
                return "Áreas deportivas";
        }
        return null;
    }

}
