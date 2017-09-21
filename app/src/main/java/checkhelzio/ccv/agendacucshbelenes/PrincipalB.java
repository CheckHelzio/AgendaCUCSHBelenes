package checkhelzio.ccv.agendacucshbelenes;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class PrincipalB extends AppCompatActivity {
    static final int HELZIO_DATE_DIALOG = 13;
    public static boolean esperar = false;
    protected static ArrayList<Eventos> lista_eventos = new ArrayList<>();
    protected static String titulos = "";
    protected static String tiposDeEvento = "";
    protected static String nombresDependencias = "";
    protected static String nombresResponsable = "";
    protected static String nombresSolicitante = "";
    protected static String stNuevoId = "";
    private final String auditorio1 = "Edificio A";
    private final String auditorio2 = "Edificio B";
    private final String auditorio3 = "Edificio C";
    private final String auditorio4 = "Edificio D";
    private final String auditorio5 = "Edificio F";
    private final String auditorio6 = "Áreas deportivas";
    protected TextView[] lista_numeros_del_mes = new TextView[42];
    protected TextView[] lista_nombre_dias_semana = new TextView[7];
    protected TextView[] lista_info1 = new TextView[42];
    protected TextView[] lista_info2 = new TextView[42];
    protected TextView[] lista_info3 = new TextView[42];
    protected LinearLayout[] lista_cajas_mes = new LinearLayout[42];
    protected int irHoyMes;
    protected int irHoyDiaSemana;
    protected int irHoyNumeroDiaMes;
    protected int irHoyAño;
    protected boolean filtro1 = true, filtro2 = true, filtro3 = true, filtro4 = true, filtro5 = true, filtro6 = true;
    protected Calendar calendarioActualizarDiasMes;
    TextView tv_header;
    TextView tv_conexion;
    TextView tv_lun;
    TextView tv_mar;
    TextView tv_mie;
    TextView tv_jue;
    TextView tv_vie;
    TextView tv_sab;
    TextView tv_dom;
    RelativeLayout conte_mes;
    GridLayout grid_layout;
    FloatingActionButton fab;
    View proteccion;
    private String[] eventos = new String[3660];
    private Calendar calendarioIrHoy;
    private Handler handler;
    private int id_prox = 0, dia_inicial_del_mes;
    private SharedPreferences prefs;
    private String st_update = "";
    private String st_eventos_guardados = "";
    private Integer diasemana;
    private TextView tv_hoy;
    private boolean bclick = false;
    private boolean filtroclases = true;
    private boolean filtroeventos= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (isScreenLarge()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        iniciarObjetos();
        iniciarToolbaryDrawer();
        setListenners();
        irHoy();
        actualizarFecha();
        iniciarPagina();
        new LlenarListaEventos().execute();
        checkNetworkConnection();
    }

    private void iniciarObjetos() {
        Log.v("BELENES33", "INICIAR OBJETOS");
        handler = new Handler();

        calendarioIrHoy = Calendar.getInstance();
        irHoyNumeroDiaMes = calendarioIrHoy.get(Calendar.DAY_OF_MONTH);
        irHoyDiaSemana = calendarioIrHoy.get(Calendar.DAY_OF_WEEK);
        irHoyAño = calendarioIrHoy.get(Calendar.YEAR);
        irHoyMes = calendarioIrHoy.get(Calendar.MONTH);

        calendarioActualizarDiasMes = Calendar.getInstance();
        calendarioActualizarDiasMes.set(Calendar.DAY_OF_MONTH, 1);

        prefs = getSharedPreferences("AGENDA DE EVENTOS CUCSH BELENES", Context.MODE_PRIVATE);
        st_eventos_guardados = prefs.getString("EVENTOS GUARDADOS BELENES", "");
        st_update = prefs.getString("UPDATE", "");
        Arrays.fill(eventos, "");
        llenarListaNombreDiasSemana();
        llenarListas();
    }

    private void llenarListas() {
        for (int x = 0; x <= 41; x++) {
            lista_info1[x] = (TextView) ((ViewGroup) ((ViewGroup) grid_layout.getChildAt(x)).getChildAt(1)).getChildAt(0);
            lista_info2[x] = (TextView) ((ViewGroup) ((ViewGroup) grid_layout.getChildAt(x)).getChildAt(1)).getChildAt(1);
            lista_info3[x] = (TextView) ((ViewGroup) ((ViewGroup) grid_layout.getChildAt(x)).getChildAt(1)).getChildAt(2);
            lista_cajas_mes[x] = (LinearLayout) grid_layout.getChildAt(x);
            lista_numeros_del_mes[x] = (TextView) ((ViewGroup) ((ViewGroup) grid_layout.getChildAt(x)).getChildAt(0)).getChildAt(0);
        }
    }

    private void llenarListaNombreDiasSemana() {
        lista_nombre_dias_semana[0] = tv_dom;
        lista_nombre_dias_semana[1] = tv_lun;
        lista_nombre_dias_semana[2] = tv_mar;
        lista_nombre_dias_semana[3] = tv_mie;
        lista_nombre_dias_semana[4] = tv_jue;
        lista_nombre_dias_semana[5] = tv_vie;
        lista_nombre_dias_semana[6] = tv_sab;
    }

    private void iniciarToolbaryDrawer() {
        Log.v("BELENES33", "INICIAR TOOLBAR Y DRAWER");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void irHoy() {
        Log.v("BELENES33", "IR HOY");
        int irHoyNumeroMesAño;
        if (irHoyAño == 2016) {
            irHoyNumeroMesAño = calendarioIrHoy.get(Calendar.MONTH);
        } else {
            irHoyNumeroMesAño = calendarioIrHoy.get(Calendar.MONTH);
            for (int x = 2016; x < irHoyAño; x++) {
                irHoyNumeroMesAño += 12;
            }
        }
    }

    private void actualizarFecha() {
        Log.v("BELENES33", "ACTUALIZAR FECHA");
        SimpleDateFormat format = new SimpleDateFormat("MMMM 'del' yyyy", Locale.forLanguageTag("es-MX"));
        String f = format.format(calendarioActualizarDiasMes.getTime());
        tv_header.setText(capitalize(f));
    }

    private void checkNetworkConnection() {

        Log.v("ELIMINAR", "CHECK NETWORK CONECTION");

        if (!esperar) {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
            if (activeInfo != null && activeInfo.isConnected()) {
                boolean wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
                boolean mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                if (wifiConnected) {
                    new DescargarUD().execute("http://148.202.6.72/aplicacion/update_belenes.txt", PrincipalB.this);
                } else if (mobileConnected) {
                    // SE HA DESCARGADO LA BASE DE DATOS DESDE LOS DATOS MOBILES, ENVIAMOS UNA ALERTA PARA QUE SE DESCARGUEN MANUALMENTE PARA NO CONSUMIR LOS DATOS DEL USUARIO
                    tv_conexion.setText(R.string.aviso_datos);
                }
            } else {
                // NO HAY CONEXCION A INTERNET MANDAR UN AVISO AL USUARIO Y COMPROBAR CADA SEGUNDO PARA NO SATURAR EL HILO PRINCIPAL
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // SI NO HAY INTERNET AVISAMOS AL USUARIO PARA QUE VERIFIQUE LAS CONEXIONES
                        tv_conexion.setText(R.string.aviso_conexion);

                        // A PESAR DE NO TENER INTENET SEGUIMOS INTENTANDO PARA CUANDO SE RECUPERE LA CONEXION
                        checkNetworkConnection();
                    }
                }, 1000);
            }
        } else {
            Log.v("ELIMINAR", "CHECK NETWORK CONECTION... ESPERANDO = TRUE");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkNetworkConnection();
                }
            }, 1000);
        }
    }

    private boolean isScreenLarge() {
        final int screenSize = getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE
                || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void clickbotones(View view) {
        Log.v("PRIMERO", "CLICK");
        if (!bclick) {
            bclick = true;
            int HELZIO_ELIMINAR_EVENTO = 4;
            try {
                int da = Integer.parseInt(view.getTag().toString());
                int dm = Integer.parseInt((((TextView) ((ViewGroup) ((ViewGroup) view).getChildAt(0)).getChildAt(0)).getText().toString()));
                final ArrayList<Eventos> lista_pequeña_eventos = new ArrayList<>();

                for (String v : eventos[da].split("¦")) {
                    if (filtro(v.split("::")[4].trim().replaceAll("[^0-9]+", ""), v.split("::")[13].trim())) {
                        lista_pequeña_eventos.add(agregarEvento(v));
                    }
                }

                // INTENT A LA LISTA DE EVENTOS
                Intent intent = new Intent(PrincipalB.this, DialogListaEventosHelzio.class);
                // PASAMOS EL NUMERO DE DIA
                intent.putExtra("DIA_MES", ((TextView) ((ViewGroup) ((ViewGroup) view).getChildAt(0)).getChildAt(0)).getText());

                // PASAMOS EL NUMERO DE DIA DESDE EL 2016
                intent.putExtra("DIA_AÑO", da);

                // PASAMOS EL NOMBRE DEL DIA
                intent.putExtra("NOMBRE_DIA", view.getResources().getResourceEntryName(view.getId()));

                // PASAMOS LA LISTA DE EVENTOS
                intent.putExtra("LISTA_EVENTOS", lista_pequeña_eventos);

                //BOLEAN PARA SABER SI ESTAMOS CLICKEANDO EL DIA DE HOY Y COLOREAR EL TEXTO EN EL DIALOG
                if (calendarioActualizarDiasMes.get(Calendar.MONTH) == irHoyMes && dm == irHoyNumeroDiaMes) {
                    intent.putExtra("ES_HOY", true);
                } else {
                    intent.putExtra("ES_HOY", false);
                }

                if (calendarioActualizarDiasMes.get(Calendar.YEAR) < irHoyAño) {
                    intent.putExtra("REGISTRAR", false);
                } else if (calendarioActualizarDiasMes.get(Calendar.YEAR) == irHoyAño) { //BOOLEAN PARA SABER SI SE PUEDE REGISTRAR O NO
                    // SI ESTAMOS EN UN MES ANTERIOR AL ACTUAL NO SE PUEDE REGISTRAR
                    if (calendarioActualizarDiasMes.get(Calendar.MONTH) < irHoyMes) {
                        intent.putExtra("REGISTRAR", false);
                    }
                    // SI ESTAMOS EN EL MISMO MES
                    else if (calendarioActualizarDiasMes.get(Calendar.MONTH) == irHoyMes) {
                        //SI EL DIA DEL MES ES ANTERIOR AL DIA DE HOY NO PODEMOS REGISTRAR
                        if (dm < irHoyNumeroDiaMes) {
                            intent.putExtra("REGISTRAR", false);
                        }
                        //SI EL DIA DEL MES ES HOY NO SE PUEDE AGENDAR DESPUES DE LAS 8:30PM
                        else if (dm == irHoyNumeroDiaMes) {
                            Calendar calendarioIrHoy = Calendar.getInstance();
                            if (calendarioIrHoy.get(Calendar.HOUR_OF_DAY) > 20) {
                                int hora = calendarioIrHoy.get(Calendar.HOUR_OF_DAY);
                                int minuto = calendarioIrHoy.get(Calendar.MINUTE);
                                if (hora == 21) {
                                    if (minuto < 30) {
                                        intent.putExtra("REGISTRAR", true);
                                    } else {
                                        intent.putExtra("REGISTRAR", false);
                                    }
                                } else {
                                    intent.putExtra("REGISTRAR", false);
                                }
                            } else {
                                intent.putExtra("REGISTRAR", true);
                            }
                        }
                        //SI ES DESPUES DE HOY SI SE PEUDE REGISTRAR
                        else {
                            intent.putExtra("REGISTRAR", true);
                        }
                    }
                    //SI ES EN UN MES FUTURO SE PUEDE REGISTRAR
                    else {
                        intent.putExtra("REGISTRAR", true);
                    }

                } else if (calendarioActualizarDiasMes.get(Calendar.YEAR) > irHoyAño) {
                    intent.putExtra("REGISTRAR", true);
                }

                final Rect startBounds = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                ChangeBoundBackground.addExtras(intent, getViewBitmap(view), startBounds);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PrincipalB.this, view, "fondo");
                startActivityForResult(intent, HELZIO_ELIMINAR_EVENTO, options.toBundle());
                view.setEnabled(true);
            } catch (Exception ignored) {

                int da = Integer.parseInt(view.getTag().toString());
                int dm = Integer.parseInt((((TextView) ((ViewGroup) ((ViewGroup) view).getChildAt(0)).getChildAt(0)).getText().toString()));
                final ArrayList<Eventos> lista_pequeña_eventos = new ArrayList<>();

                // INTENT A LA LISTA DE EVENTOS
                Intent intent = new Intent(PrincipalB.this, DialogListaEventosHelzio.class);
                // PASAMOS EL NUMERO DE DIA
                intent.putExtra("DIA_MES", ((TextView) ((ViewGroup) ((ViewGroup) view).getChildAt(0)).getChildAt(0)).getText());

                // PASAMOS EL NUMERO DE DIA DESDE EL 2016
                intent.putExtra("DIA_AÑO", da);

                // PASAMOS EL NOMBRE DEL DIA
                intent.putExtra("NOMBRE_DIA", view.getResources().getResourceEntryName(view.getId()));

                // PASAMOS LA LISTA DE EVENTOS
                intent.putExtra("LISTA_EVENTOS", lista_pequeña_eventos);

                //BOLEAN PARA SABER SI ESTAMOS CLICKEANDO EL DIA DE HOY Y COLOREAR EL TEXTO EN EL DIALOG
                if (calendarioActualizarDiasMes.get(Calendar.MONTH) == irHoyMes && dm == irHoyNumeroDiaMes) {
                    intent.putExtra("ES_HOY", true);
                } else {
                    intent.putExtra("ES_HOY", false);
                }

                if (calendarioActualizarDiasMes.get(Calendar.YEAR) < irHoyAño) {
                    intent.putExtra("REGISTRAR", false);
                } else if (calendarioActualizarDiasMes.get(Calendar.YEAR) == irHoyAño) {//BOOLEAN PARA SABER SI SE PUEDE REGISTRAR O NO
                    //SI ESTAMOS EN UN MES ANTERIOR AL ACTUAL NO SE PUEDE REGISTRAR
                    if (calendarioActualizarDiasMes.get(Calendar.MONTH) < irHoyMes) {
                        intent.putExtra("REGISTRAR", false);
                    }
                    //SI ESTAMOS EN EL MISMO MES
                    else if (calendarioActualizarDiasMes.get(Calendar.MONTH) == irHoyMes) {
                        //SI EL DIA DEL MES ES ANTERIOR AL DIA DE HOY NO PODEMOS REGISTRAR
                        if (dm < irHoyNumeroDiaMes) {
                            intent.putExtra("REGISTRAR", false);
                        }
                        //SI EL DIA DEL MES ES HOY NO SE PUEDE AGENDAR DESPUES DE LAS 8PM
                        else if (dm == irHoyNumeroDiaMes) {
                            Calendar calendarioIrHoy = Calendar.getInstance();
                            if (calendarioIrHoy.get(Calendar.HOUR_OF_DAY) > 20) {
                                int hora = calendarioIrHoy.get(Calendar.HOUR_OF_DAY);
                                int minuto = calendarioIrHoy.get(Calendar.MINUTE);
                                if (hora == 21) {
                                    if (minuto < 30) {
                                        intent.putExtra("REGISTRAR", true);
                                    } else {
                                        intent.putExtra("REGISTRAR", false);
                                    }
                                } else {
                                    intent.putExtra("REGISTRAR", false);
                                }
                            } else {
                                intent.putExtra("REGISTRAR", true);
                            }
                        }
                        //SI ES DESPUES DE HOY SI SE PEUDE REGISTRAR
                        else {
                            intent.putExtra("REGISTRAR", true);
                        }
                    }
                    //SI ES EN UN MES FUTURO SE PUEDE REGISTRAR
                    else {
                        intent.putExtra("REGISTRAR", true);
                    }

                } else if (calendarioActualizarDiasMes.get(Calendar.YEAR) > irHoyAño) {
                    intent.putExtra("REGISTRAR", true);
                }

                final Rect startBounds = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                ChangeBoundBackground.addExtras(intent, getViewBitmap(view), startBounds);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PrincipalB.this, view, "fondo");
                startActivityForResult(intent, HELZIO_ELIMINAR_EVENTO, options.toBundle());
                view.setEnabled(true);
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bclick = false;
                }
            }, 1000);
        }
    }

    private Eventos agregarEvento(String eventos_suelto) {
        return new Eventos(
                // FECHA
                eventos_suelto.split("::")[0].trim().replaceAll("[^0-9]+", ""),
                // HORA INCIAL
                eventos_suelto.split("::")[1].trim().replaceAll("[^0-9]+", ""),
                // HORA FINAL
                eventos_suelto.split("::")[2].trim().replaceAll("[^0-9]+", ""),
                // TITULO
                eventos_suelto.split("::")[3].trim(),
                // AUDITORIO
                eventos_suelto.split("::")[4].trim().replaceAll("[^0-9]+", ""),
                // TIPO DE EVENTO
                eventos_suelto.split("::")[5].trim(),
                // NOMBRE DEL SOLICITANTE
                eventos_suelto.split("::")[6].trim(),
                // EXTRENSION DEL SOLICITANTE
                eventos_suelto.split("::")[7].trim(),
                // STATUS DEL EVENTO
                eventos_suelto.split("::")[8].trim(),
                // QUIEN REGISTRO
                eventos_suelto.split("::")[9].trim(),
                // CUANDO REGISTRO
                eventos_suelto.split("::")[10].trim(),
                // NOTAS
                eventos_suelto.split("::")[11].trim(),
                // ID
                eventos_suelto.split("::")[12].trim().replaceAll("[^0-9]+", ""),
                // TAG
                eventos_suelto.trim(),
                // FONDO
                fondoAuditorio(eventos_suelto.split("::")[4].trim(), eventos_suelto.split("::")[13].trim(), eventos_suelto.split("::")[3].trim()),
                // CLASE
                eventos_suelto.split("::")[13].trim(),
                // DEPEDENCIA
                eventos_suelto.split("::")[14].trim(),
                // NOMBRE DEL RESPONSABLE
                eventos_suelto.split("::")[15].trim(),
                // CELULAR DEL RESPONSABLE
                eventos_suelto.split("::")[16].trim(),
                // AULA
                eventos_suelto.split("::")[17].trim(),
                // NOTAS 2
                eventos_suelto.split("::")[18].trim()
        );
    }

    public void checkAuditorios(View v) {

        //((Switch) v).setChecked(!((Switch)v).isChecked());

        switch (v.getId()) {
            case R.id.switcha:
                filtro1 = ((Switch)v).isChecked();
                break;
            case R.id.switchb:
                filtro2 = ((Switch)v).isChecked();
                break;
            case R.id.switchc:
                filtro3 = ((Switch)v).isChecked();
                break;
            case R.id.switchd:
                filtro4 = ((Switch)v).isChecked();
                break;
            case R.id.switchf:
                filtro5 = ((Switch)v).isChecked();
                break;
            case R.id.switchedad:
                filtro6 = ((Switch)v).isChecked();
                break;
            case R.id.switchclases:
                filtroclases = ((Switch)v).isChecked();
                break;
            case R.id.switcheventos:
                filtroeventos = ((Switch)v).isChecked();
                break;
        }

        new eventoEnCalendario().execute();
    }

    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream;
        String str = "";
        try {
            stream = downloadUrl(urlString);
            str = readIt(stream);
            assert stream != null;
            stream.close();
        } catch (Exception ignored) {
        }
        return str;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(0);
        conn.setConnectTimeout(0);
        return conn.getInputStream();
    }

    private String readIt(InputStream stream) throws IOException {
        String a = "";
        String linea;
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        while ((linea = br.readLine()) != null) {
            a += linea;
        }
        return a;
    }

    private void setListenners() {
        for (int x = 0; x < grid_layout.getChildCount() - 1; x++) {
            final int finalX = x;
            grid_layout.getChildAt(x).setOnTouchListener(new OnSwipeTouchListener(PrincipalB.this) {

                @Override
                public void onClick() {
                    grid_layout.getChildAt(finalX).performClick();
                }

                @Override
                public void onSelected(boolean b) {
                    grid_layout.getChildAt(finalX).setPressed(b);
                }

                @Override
                public void onLongClick() {
                    Log.v("PRIMERO", "LONGCLICK");
                    grid_layout.getChildAt(finalX).performLongClick();
                }

                @Override
                public void onSwipeLeft() {
                    bclick = true;
                    Log.v("PRIMERO", "SWIPE");
                    calendarioActualizarDiasMes.add(Calendar.MONTH, 1);
                    CambiarPagina();
                }

                @Override
                public void onSwipeRight() {
                    bclick = true;
                    Log.v("PRIMERO", "SWIPE");
                    int x = calendarioActualizarDiasMes.get(Calendar.YEAR);
                    int y = calendarioActualizarDiasMes.get(Calendar.MONTH);
                    if (!(x == 2016 && y == 0)) {
                        calendarioActualizarDiasMes.add(Calendar.MONTH, -1);
                        CambiarPagina();
                    }
                }
            });
        }
    }

    private void CambiarPagina() {
        final Animation fadeInPagina = AnimationUtils.loadAnimation(PrincipalB.this, R.anim.fadein_z);
        final Animation fadeOutPagina = AnimationUtils.loadAnimation(PrincipalB.this, R.anim.fadeout_z);
        fadeInPagina.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                conte_mes.setVisibility(View.VISIBLE);
                bclick = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeOutPagina.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                actualizarFecha();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iniciarPagina();
                new eventoEnCalendario().execute();
                conte_mes.startAnimation(fadeInPagina);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        conte_mes.startAnimation(fadeOutPagina);
    }

    private void iniciarPagina() {
        diasemana = obtenerDiaSemana(calendarioActualizarDiasMes.get(Calendar.DAY_OF_WEEK));
        int actuDias_Año = calendarioActualizarDiasMes.get(Calendar.YEAR);

        if (actuDias_Año == 2016) {
            dia_inicial_del_mes = calendarioActualizarDiasMes.get(Calendar.DAY_OF_YEAR);
        } else {
            dia_inicial_del_mes = calendarioActualizarDiasMes.get(Calendar.DAY_OF_YEAR);
            for (int x = 2016; x < actuDias_Año; x++) {
                Calendar cr = Calendar.getInstance();
                cr.set(x, 11, 31);
                dia_inicial_del_mes += cr.get(Calendar.DAY_OF_YEAR);
            }
        }
        Log.v("BELENES33", "INICIAR PAGINA - DIA INICIAL DEL MES - COMPLETO");

        for (int x = 28; x <= 41; x++) {
            lista_cajas_mes[x].setVisibility(View.VISIBLE);
        }

        if (diasemana + calendarioActualizarDiasMes.getActualMaximum(Calendar.DAY_OF_MONTH) >= 36) {
            lista_cajas_mes[35].setVisibility(View.VISIBLE);
            lista_cajas_mes[36].setVisibility(View.VISIBLE);
            lista_cajas_mes[37].setVisibility(View.VISIBLE);
            lista_cajas_mes[38].setVisibility(View.VISIBLE);
            lista_cajas_mes[39].setVisibility(View.VISIBLE);
            lista_cajas_mes[40].setVisibility(View.VISIBLE);
            lista_cajas_mes[41].setVisibility(View.VISIBLE);
        } else {
            lista_cajas_mes[35].setVisibility(View.GONE);
            lista_cajas_mes[36].setVisibility(View.GONE);
            lista_cajas_mes[37].setVisibility(View.GONE);
            lista_cajas_mes[38].setVisibility(View.GONE);
            lista_cajas_mes[39].setVisibility(View.GONE);
            lista_cajas_mes[40].setVisibility(View.GONE);
            lista_cajas_mes[41].setVisibility(View.GONE);
        }
        Log.v("BELENES33", "INICIAR PAGINA - VISIBILIDAD DE LA SEXTA SEMANA - COMPLETO");

        for (int y = 0; y < diasemana; y++) {
            lista_cajas_mes[y].setVisibility(View.GONE);
        }

        for (int y = diasemana; y <= 6; y++) {
            lista_cajas_mes[y].setVisibility(View.VISIBLE);
        }

        Log.v("BELENES33", "INICIAR PAGINA - LIMPIAR PRIMER SEMANA - COMPLETO");

        for (int x = 1; x <= calendarioActualizarDiasMes.getActualMaximum(Calendar.DAY_OF_MONTH); x++) {
            final String st_nd = "" + x;
            lista_numeros_del_mes[x - 1 + diasemana].setText(st_nd);
        }
        Log.v("BELENES33", "INICIAR PAGINA - NUMERO DE DIA - COMPLETO");

        if (calendarioActualizarDiasMes.get(Calendar.MONTH) == irHoyMes && irHoyAño == calendarioActualizarDiasMes.get(Calendar.YEAR)) {
            lista_numeros_del_mes[diasemana - 1 + irHoyNumeroDiaMes].setBackgroundResource(R.drawable.fondo_hoy);
            lista_numeros_del_mes[diasemana - 1 + irHoyNumeroDiaMes].setTextColor(Color.WHITE);
            lista_nombre_dias_semana[irHoyDiaSemana - 1].setTextColor((Integer) getAcentColor());
            tv_hoy = lista_numeros_del_mes[diasemana - 1 + irHoyNumeroDiaMes];
        } else {
            tv_hoy.setBackgroundColor(Color.TRANSPARENT);
            tv_hoy.setTextColor(Color.parseColor("#757575"));
            lista_nombre_dias_semana[irHoyDiaSemana - 1].setTextColor(Color.parseColor("#757575"));
        }
        Log.v("BELENES33", "INICIAR PAGINA - COLOREAR HOY DIA Y NUMERO - COMPLETO");

        for (int x = calendarioActualizarDiasMes.getActualMaximum(Calendar.DAY_OF_MONTH) + diasemana; x <= 35; x++) {
            lista_cajas_mes[x].setVisibility(View.GONE);
        }

    }

    private Integer obtenerDiaSemana(int i) {
        switch (i) {
            //domingo
            case 1:
                i = 6;
                break;
            //lunes
            case 2:
                i = 0;
                break;
            //martes
            case 3:
                i = 1;
                break;
            //miercoles
            case 4:
                i = 2;
                break;
            //jueves
            case 5:
                i = 3;
                break;
            //viernes
            case 6:
                i = 4;
                break;
            //sabado
            case 7:
                i = 5;
                break;
        }
        return i;
    }

    private int getFondo(String trim, String clase, String titulo) {
        int a = 0;
        if (titulo.contains("No se presentó - ")){
            return R.drawable.fondo_no_presento;
        }
        switch (trim) {
            case "1":
                if (clase.equals("C")){
                    a = (R.drawable.fondo1c);
                }else {
                    a = (R.drawable.fondo1);
                }
                break;
            case "2":
                if (clase.equals("C")){
                    a = (R.drawable.fondo2c);
                }else {
                    a = (R.drawable.fondo2);
                }
                break;
            case "3":
                if (clase.equals("C")){
                    a = (R.drawable.fondo3c);
                }else {
                    a = (R.drawable.fondo3);
                }
                break;
            case "4":
                if (clase.equals("C")){
                    a = (R.drawable.fondo4c);
                }else {
                    a = (R.drawable.fondo4);
                }
                break;
            case "5":
                if (clase.equals("C")){
                    a = (R.drawable.fondo5c);
                }else {
                    a = (R.drawable.fondo5);
                }
                break;
            case "6":
                if (clase.equals("C")){
                    a = (R.drawable.fondo6c);
                }else {
                    a = (R.drawable.fondo6);
                }
                break;
        }
        return a;
    }

    private boolean filtro(String auditorio, String clase) {
        boolean f = false;
        switch (auditorio) {
            case "1":
                f = filtro1;
                if (f){
                    if (clase.equals("C")){
                        f = filtroclases;
                    }else {
                        f = filtroeventos;
                    }
                }
                break;
            case "2":
                f = filtro2;
                if (f){
                    if (clase.equals("C")){
                        f = filtroclases;
                    }else {
                        f = filtroeventos;
                    }
                }
                break;
            case "3":
                f = filtro3;
                if (f){
                    if (clase.equals("C")){
                        f = filtroclases;
                    }else {
                        f = filtroeventos;
                    }
                }
                break;
            case "4":
                f = filtro4;
                if (f){
                    if (clase.equals("C")){
                        f = filtroclases;
                    }else {
                        f = filtroeventos;
                    }
                }
                break;
            case "5":
                f = filtro5;
                if (f){
                    if (clase.equals("C")){
                        f = filtroclases;
                    }else {
                        f = filtroeventos;
                    }
                }
                break;
            case "6":
                f = filtro6;
                if (f){
                    if (clase.equals("C")){
                        f = filtroclases;
                    }else {
                        f = filtroeventos;
                    }
                }
                break;
        }

        return f;
    }

    private int fondoAuditorio(String numero, String clase, String titulo) {
        int st = 0;
        if (titulo.contains("No se presentó - ")){
            return getResources().getColor(R.color.color_no_presentado);
        }
        switch (numero) {
            case "1":
                if (clase.equals("C")) {
                    st = getResources().getColor(R.color.color1c);
                } else {
                    st = getResources().getColor(R.color.color1);
                }
                break;
            case "2":
                if (clase.equals("C")) {
                    st = getResources().getColor(R.color.color2c);
                } else {
                    st = getResources().getColor(R.color.color2);
                }
                break;
            case "3":
                if (clase.equals("C")) {
                    st = getResources().getColor(R.color.color3c);
                } else {
                    st = getResources().getColor(R.color.color3);
                }
                break;
            case "4":
                if (clase.equals("C")) {
                    st = getResources().getColor(R.color.color4c);
                } else {
                    st = getResources().getColor(R.color.color4);
                }
                break;
            case "5":
                if (clase.equals("C")) {
                    st = getResources().getColor(R.color.color5c);
                } else {
                    st = getResources().getColor(R.color.color5);
                }
                break;
            case "6":
                if (clase.equals("C")) {
                    st = getResources().getColor(R.color.color6c);
                } else {
                    st = getResources().getColor(R.color.color6);
                }
                break;
        }
        return st;
    }

    public Object getAcentColor() {
        int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent;
        } else {
            //Get colorAccent defined for AppCompat
            colorAttr = getResources().getIdentifier("colorAccent", "attr", getPackageName());
        }
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public void fabClick() {
        Intent intent = new Intent(this, RegistrarEventoB.class);
        intent.putExtra("DONDE", "PRINCIPAL");
        FabTransition.addExtras(intent, (Integer) getAcentColor(), R.drawable.ic_add_black);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab,
                getString(R.string.transition_date_dialog_helzio));
        startActivityForResult(intent, HELZIO_DATE_DIALOG, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_fecha:
                Intent intent = new Intent(this, DateDialogHelzio.class);
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivityForResult(intent, HELZIO_DATE_DIALOG, bundle);
                return true;
            case R.id.menu_buscar:
                final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.search_view);
                final ImageView buscar = (ImageView) findViewById(R.id.menu_buscar2);
                final ImageView cancel = (ImageView) findViewById(R.id.menu_cerrar_buscar);
                final RelativeLayout conte_buscar = (RelativeLayout) findViewById(R.id.conte_buscar);
                conte_buscar.setVisibility(View.VISIBLE);

                String conceptos = titulos + auditorio1 + "¦" + auditorio2 + "¦" + auditorio3 + "¦" + auditorio4 + "¦" + auditorio5 + "¦" + auditorio6 + "¦" +
                        tiposDeEvento + nombresDependencias + nombresSolicitante + nombresResponsable;
                ArrayAdapter<String> nombresAdapter = new ArrayAdapter<>(PrincipalB.this, android.R.layout.simple_list_item_1, conceptos.split("¦"));
                autoCompleteTextView.setAdapter(nombresAdapter);
                autoCompleteTextView.setThreshold(2);
                autoCompleteTextView.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.toggleSoftInput(0, 0);
                            conte_buscar.setVisibility(View.INVISIBLE);
                            buscar(autoCompleteTextView.getText().toString());
                            autoCompleteTextView.setText("");
                            return true;
                        }
                        return false;
                    }
                });

                buscar.setOnClickListener(v -> {
                    if (!autoCompleteTextView.getText().toString().equals("")) {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.toggleSoftInput(0, 0);
                        conte_buscar.setVisibility(View.INVISIBLE);
                        buscar(autoCompleteTextView.getText().toString());
                        autoCompleteTextView.setText("");
                    }
                });

                autoCompleteTextView.setOnKeyListener((v, keyCode, event) -> {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.toggleSoftInput(0, 0);
                        conte_buscar.setVisibility(View.INVISIBLE);
                        buscar(autoCompleteTextView.getText().toString());
                        autoCompleteTextView.setText("");
                        return true;
                    }
                    return false;
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autoCompleteTextView.setText("");
                        conte_buscar.setVisibility(View.INVISIBLE);
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case HELZIO_DATE_DIALOG:
                if (resultCode == RESULT_OK) {
                    calendarioActualizarDiasMes.set(2016, 0, 1);
                    calendarioActualizarDiasMes.set(Calendar.MONTH, data.getExtras().getInt("NUMERO_DE_MES"));
                    CambiarPagina();
                }
                break;
        }
    }

    private void buscar(String s) {
        final ArrayList<Eventos> lista_pequeña_eventos = new ArrayList<>();

        switch (s) {
            case auditorio1:
                s = "1";
                for (Eventos ev : lista_eventos) {
                    if (ev.getAuditorio().equals(s))
                        lista_pequeña_eventos.add(ev);
                }
                break;
            case auditorio2:
                s = "2";
                for (Eventos ev : lista_eventos) {
                    if (ev.getAuditorio().equals(s))
                        lista_pequeña_eventos.add(ev);
                }
                break;
            case auditorio3:
                s = "3";
                for (Eventos ev : lista_eventos) {
                    if (ev.getAuditorio().equals(s))
                        lista_pequeña_eventos.add(ev);
                }
                break;
            case auditorio4:
                s = "4";
                for (Eventos ev : lista_eventos) {
                    if (ev.getAuditorio().equals(s))
                        lista_pequeña_eventos.add(ev);
                }
                break;
            case auditorio5:
                s = "5";
                for (Eventos ev : lista_eventos) {
                    if (ev.getAuditorio().equals(s))
                        lista_pequeña_eventos.add(ev);
                }
                break;
            case auditorio6:
                s = "6";
                for (Eventos ev : lista_eventos) {
                    if (ev.getAuditorio().equals(s))
                        lista_pequeña_eventos.add(ev);
                }
                break;
            default:
                for (Eventos ev : lista_eventos) {
                    if (ev.getTag().contains(s))
                        lista_pequeña_eventos.add(ev);
                }
                break;
        }

        // INTENT A LA LISTA DE EVENTOS
        Intent intent = new Intent(PrincipalB.this, DialogResultadoBusqueda.class);

        // PASAMOS LA LISTA DE EVENTOS
        intent.putExtra("LISTA_EVENTOS", lista_pequeña_eventos);

        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivityForResult(intent, HELZIO_DATE_DIALOG, bundle);


    }

    private String getNombreAula(String st_aulas){
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

    private class DescargarUD extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            try {
                return loadFromNetwork(params[0].toString());
            } catch (IOException e) {
                return "Error de conexión";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("Error de conexión")) {

                Log.v("ELIMINAR", "ACTUALIZADO");
                // ESCRIBIMOS EN EL FOOTER LA HORA DE ACTUALIZACION Y LA GUARDAMOS EN LA BASE DE DATOS PARA DAR REFERENCIA AL USUARIO DE LA ULTIMA VEZ QUE FUE ACTUALIZADA SI SE UTILIZA SIN INTERNET
                calendarioIrHoy = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("'Actualizado el 'd 'de' MMMM 'del' yyyy 'a las' h:mm a", Locale.forLanguageTag("es-MX"));
                tv_conexion.setText(format.format(calendarioIrHoy.getTime()));
                prefs.edit().putString("ACTUALIZACION", format.format(calendarioIrHoy.getTime())).apply();

                if (s.contains("</form>")) {
                    // MUCHAS VECES LA BASE DE DATOS ES DESCARGADA CON CODIGO HTML QUE NO NECESITOS, POR ESO AQUI LO REEMPLAZAMOS
                    s = s.split("</form>")[1].trim();
                }

                if (!s.trim().equals(st_update.trim())) {
                    st_update = s.trim();
                    prefs.edit().putString("UPDATE", st_update).apply();
                    tv_conexion.setText(R.string.st_aplicando_cambios);
                    Log.v("ELIMINAR", "NUEVOS CAMBIOS DETECTADOS");
                    // SI LA BASE DE DATOS QUE DESCARGARMOS NO ES IGUAL A LA QUE YA TENEMOS LA SOBREESCRIBIMOS Y DESPUES LLEMANOS NUEVAMENTE LA LISTA DE EVENTOS
                    new DescargarBD().execute("http://148.202.6.72/aplicacion/datos_belenes.txt", PrincipalB.this);

                }
            } else {
                Log.v("ELIMINAR", "DESCARGAR BASE DE DATOS... ERROR DE CONEXION");
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkNetworkConnection();
                }
            }, 1000);
        }
    }

    private class DescargarBD extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... objects) {
            Log.v("ELIMINAR", "DESCARGAR BASE DE DATOS... BACKGROUND");
            try {
                return loadFromNetwork(objects[0].toString());
            } catch (IOException e) {
                return "Error de conexión";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("Error de conexión")) {
                if (s.contains("</form>")) {
                    // MUCHAS VECES LA BASE DE DATOS ES DESCARGADA CON CODIGO HTML QUE NO NECESITOS, POR ESO AQUI LO REEMPLAZAMOS
                    s = s.split("</form>")[1].trim();
                }

                st_eventos_guardados = s;
                prefs.edit().putString("EVENTOS GUARDADOS BELENES", st_eventos_guardados).apply();
                new LlenarListaEventos().execute();
            } else {
                Log.v("ELIMINAR", "DESCARGAR BASE DE DATOS... ERROR DE CONEXION");
                new DescargarBD().execute("http://148.202.6.72/aplicacion/datos_belenes.txt", PrincipalB.this);
            }
        }
    }

    private class LlenarListaEventos extends AsyncTask<String, String, Void> {
        String st_eventos_sin_repetir = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eventos = new String[3660];
            Arrays.fill(eventos, "");
            lista_eventos.clear();
        }

        @Override
        protected Void doInBackground(String... aa12) {
            Log.v("BELENES33", "LLENAR LISTA");

            if (!esperar) {

                Log.v("BELENES33", "LLENAR LISTA... NO ESPERAR");
                // EN OCACIONES LA BASE DE DATOS DESCARGA CODIGO HTML QUE NO NECESITAMOS, LO QUITAMOS AQUI
                if (st_eventos_guardados.contains("</form>")) {
                    st_eventos_guardados = st_eventos_guardados.split("</form>")[1].trim();
                }

                if (st_eventos_guardados.trim().length() > 0) {
                    for (String eventos_suelto : st_eventos_guardados.trim().split("¦")) {

                        if (!eventos_suelto.trim().equals("")) {

                            if (!titulos.contains(eventos_suelto.trim().split("::")[3].trim())) {
                                titulos += eventos_suelto.trim().split("::")[3].trim() + "¦";
                            }
                            if (!tiposDeEvento.contains(eventos_suelto.trim().split("::")[5].trim())) {
                                tiposDeEvento += eventos_suelto.trim().split("::")[5].trim() + "¦";
                            }
                            if (!nombresSolicitante.contains(eventos_suelto.trim().split("::")[6].trim())) {
                                nombresSolicitante += eventos_suelto.trim().split("::")[6].trim() + "¦";
                            }
                            if (!nombresDependencias.contains(eventos_suelto.trim().split("::")[14].trim())) {
                                nombresDependencias += eventos_suelto.trim().split("::")[14].trim() + "¦";
                            }
                            if (!nombresResponsable.contains(eventos_suelto.trim().split("::")[15].trim())) {
                                nombresResponsable += eventos_suelto.trim().split("::")[15].trim() + "¦";
                            }

                            Eventos nuevo_evento = new Eventos(
                                    // FECHA
                                    eventos_suelto.split("::")[0].trim().replaceAll("[^0-9]+", ""),
                                    // HORA INCIAL
                                    eventos_suelto.split("::")[1].trim().replaceAll("[^0-9]+", ""),
                                    // HORA FINAL
                                    eventos_suelto.split("::")[2].trim().replaceAll("[^0-9]+", ""),
                                    // TITULO
                                    eventos_suelto.split("::")[3].trim(),
                                    // AUDITORIO
                                    eventos_suelto.split("::")[4].trim().replaceAll("[^0-9]+", ""),
                                    // TIPO DE EVENTO
                                    eventos_suelto.split("::")[5].trim(),
                                    // NOMBRE DEL SOLICITANTE
                                    eventos_suelto.split("::")[6].trim(),
                                    // EXTRENSION DEL SOLICITANTE
                                    eventos_suelto.split("::")[7].trim(),
                                    // STATUS DEL EVENTO
                                    eventos_suelto.split("::")[8].trim(),
                                    // QUIEN REGISTRO
                                    eventos_suelto.split("::")[9].trim(),
                                    // CUANDO REGISTRO
                                    eventos_suelto.split("::")[10].trim(),
                                    // NOTAS
                                    eventos_suelto.split("::")[11].trim(),
                                    // ID
                                    eventos_suelto.split("::")[12].trim().replaceAll("[^0-9]+", ""),
                                    // TAG
                                    eventos_suelto.trim(),
                                    // FONDO
                                    fondoAuditorio(eventos_suelto.split("::")[4].trim(), eventos_suelto.split("::")[13].trim(), eventos_suelto.split("::")[3].trim()),
                                    // CLASE
                                    eventos_suelto.split("::")[13].trim(),
                                    // DEPEDENCIA
                                    eventos_suelto.split("::")[14].trim(),
                                    // NOMBRE DEL RESPONSABLE
                                    eventos_suelto.split("::")[15].trim(),
                                    // CELULAR DEL RESPONSABLE
                                    eventos_suelto.split("::")[16].trim(),
                                    // AULA
                                    eventos_suelto.split("::")[17].trim(),
                                    // NOTAS 2
                                    eventos_suelto.split("::")[18].trim()
                            );

                            if (!st_eventos_sin_repetir.contains(nuevo_evento.getTag())) {
                                lista_eventos.add(nuevo_evento);
                                eventos[Integer.parseInt(eventos_suelto.split("::")[0].trim().replaceAll("[^0-9]+", ""))] += eventos_suelto + "¦";
                                if (nuevo_evento.getTag().contains("FBAD")) {
                                    Log.v("area deportiva", nuevo_evento.aTag());
                                }
                                st_eventos_sin_repetir += nuevo_evento.getTag() + "¦";
                            } else {
                                Log.v("REPETIDO", nuevo_evento.getTag());
                            }


                            // COMPROBAMOS EL ID DE CADA EVENTO PARA DETERMINAR SI ES MAYOR AL ANTERIOR Y AL FINAL OBTENER EL ID MAS ALTO
                            if (Integer.parseInt(eventos_suelto.split("::")[12].trim()) > id_prox) {
                                id_prox = Integer.parseInt(eventos_suelto.split("::")[12].trim());
                            }
                        }
                    }

                    stNuevoId = "" + (id_prox + 1);
                    if (stNuevoId.length() == 1) {
                        stNuevoId = "000" + stNuevoId;
                    } else if (stNuevoId.length() == 2) {
                        stNuevoId = "00" + stNuevoId;
                    } else if (stNuevoId.length() == 3) {
                        stNuevoId = "0" + stNuevoId;
                    }
                }
            } else {
                Log.v("ELIMINAR", "LLENAR LISTA... ESPERAR");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new LlenarListaEventos().execute();
                    }
                }, 900);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // ESCRIBIMOS EN EL FOOTER LA HORA DE ACTUALIZACION Y LA GUARDAMOS EN LA BASE DE DATOS PARA DAR REFERENCIA AL USUARIO DE LA ULTIMA VEZ QUE FUE ACTUALIZADA SI SE UTILIZA SIN INTERNET
            calendarioIrHoy = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("'Actualizado el 'd 'de' MMMM 'del' yyyy 'a las' h:mm a", Locale.forLanguageTag("es-MX"));
            tv_conexion.setText(format.format(calendarioIrHoy.getTime()));
            prefs.edit().putString("ACTUALIZACION", format.format(calendarioIrHoy.getTime())).apply();
            new eventoEnCalendario().execute();
        }
    }

    private class eventoEnCalendario extends AsyncTask<Void, Object, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int x = 1; x <= calendarioActualizarDiasMes.getActualMaximum(Calendar.DAY_OF_MONTH); x++) {
                String n = "";
                try {
                    for (String ev : eventos[dia_inicial_del_mes - 1 + x].split("¦")) {
                        if (!ev.equals("") && filtro(ev.split("::")[4].trim().replaceAll("[^0-9]+", ""), ev.split("::")[13].trim())) {
                            n += ev + "¦";
                        }
                    }
                    String ev[] = n.split("¦");
                    publishProgress(ev, x);
                } catch (Exception ignored) {
                    String ev[] = n.split("¦");
                    publishProgress(ev, x);
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            String eventos[] = (String[]) values[0];
            int x = (int) values[1];
            lista_cajas_mes[x - 1 + diasemana].setTag((dia_inicial_del_mes - 1 + x));
            try {
                if (eventos.length > 1 || (eventos.length == 1 && !eventos[0].equals(""))) {
                    lista_info1[x - 1 + diasemana].setVisibility(View.VISIBLE);
                    lista_info1[x - 1 + diasemana].setText(getNombreAula(eventos[0].split("::")[17].trim()));
                    lista_info1[x - 1 + diasemana].setBackgroundResource(getFondo(eventos[0].split("::")[4].trim().replaceAll("[^0-9]+", ""), eventos[0].split("::")[13].trim(), eventos[0].split("::")[3].trim()));
                    if (eventos.length >= 2) {
                        lista_info2[x - 1 + diasemana].setVisibility(View.VISIBLE);
                        lista_info2[x - 1 + diasemana].setText(getNombreAula(eventos[1].split("::")[17].trim()));
                        lista_info2[x - 1 + diasemana].setBackgroundResource(getFondo(eventos[1].split("::")[4].trim().replaceAll("[^0-9]+", ""), eventos[1].split("::")[13].trim(), eventos[1].split("::")[3].trim()));
                        if (eventos.length >= 3) {
                            lista_info3[x - 1 + diasemana].setVisibility(View.VISIBLE);
                            lista_info3[x - 1 + diasemana].setText("" + (eventos.length - 2) + " más");
                        } else {
                            lista_info3[x - 1 + diasemana].setVisibility(View.INVISIBLE);
                        }
                    } else {
                        lista_info2[x - 1 + diasemana].setVisibility(View.INVISIBLE);
                        lista_info3[x - 1 + diasemana].setVisibility(View.INVISIBLE);
                    }
                } else {
                    lista_info1[x - 1 + diasemana].setVisibility(View.INVISIBLE);
                    lista_info2[x - 1 + diasemana].setVisibility(View.INVISIBLE);
                    lista_info3[x - 1 + diasemana].setVisibility(View.INVISIBLE);
                }

            } catch (Exception ignored) {
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            proteccion.setVisibility(View.GONE);
        }
    }

}
