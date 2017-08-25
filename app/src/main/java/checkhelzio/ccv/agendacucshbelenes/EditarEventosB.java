package checkhelzio.ccv.agendacucshbelenes;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static checkhelzio.ccv.agendacucshbelenes.PrincipalB.lista_eventos;

public class EditarEventosB extends AppCompatActivity {

    @BindView(R.id.fondo)
    LinearLayout fondo;
    @BindView(R.id.ly)
    RelativeLayout sfondo;
    @BindView(R.id.color_reveal)
    View color_reveal;
    @BindView(R.id.atv_tituto_evento)
    AutoCompleteTextView atv_titulo_evento;
    @BindView(R.id.atv_nombre_dependencia)
    AutoCompleteTextView atv_nombre_dependencia;
    @BindView(R.id.atv_tipo_evento)
    AutoCompleteTextView atv_tipo_evento;
    @BindView(R.id.atv_nombre_sol)
    AutoCompleteTextView atv_nombre_sol;
    @BindView(R.id.atv_nombre_resp)
    AutoCompleteTextView atv_nombre_resp;
    @BindView(R.id.toolbar_dialog)
    RelativeLayout full_header;
    @BindView(R.id.sp_auditorios)
    Spinner sp_auditorios;
    @BindView(R.id.tv_repeticion)
    TextView tv_repeticion;
    @BindView(R.id.tv_titulo_label)
    TextView tv_titulo_label;
    @BindView(R.id.tv_tipo_evento_label)
    TextView tv_tipo_evento_label;
    @BindView(R.id.tv_nom_resp_label)
    TextView tv_nom_resp_label;
    @BindView(R.id.tv_nom_sol_label)
    TextView tv_nom_sol_label;
    @BindView(R.id.tv_contraseña_label)
    TextView tv_contraseña_label;
    @BindView(R.id.selectAula)
    RelativeLayout selectAula;
    @BindView(R.id.tv_aula)
    TextView tv_aula;
    @BindView(R.id.tv_ext_sol_label)
    TextView tv_ext_sol_label;
    @BindView(R.id.tv_nombre_dependencia)
    TextView tv_nombre_dependencia;
    @BindView(R.id.tv_label_aula)
    TextView tv_label_aula;
    @BindView(R.id.tv_no_cel_label)
    TextView tv_no_cel_label;
    @BindView(R.id.et_ext_sol)
    EditText et_ext_sol;
    @BindView(R.id.et_contraseña)
    EditText et_contraseña;
    @BindView(R.id.et_nota)
    EditText et_nota;
    @BindView(R.id.et_nota_csg)
    EditText et_nota_csg;
    @BindView(R.id.et_cel_resp)
    EditText et_cel_resp;
    @BindView(R.id.rv_conflictos)
    RecyclerView rv_conflictos;
    @BindView(R.id.rv_fechas)
    RecyclerView rv_fechas;
    @BindView(R.id.snackposs)
    CoordinatorLayout snackposs;
    @BindView(R.id.conteConflictos)
    RelativeLayout conteConflictos;
    @BindView(R.id.tv_guardar_evento)
    TextView tv_guardar_evento;
    @BindView(R.id.switch_clase)
    Switch switch_clase;
    @BindView(R.id.switch_noPresentado)
    Switch switch_noPresentado;

    private boolean auditoriosIniciado = false;
    private Handler handler, handler2;
    private boolean pin_correcto_eliminar = false;

    private boolean wifiConnected = false;
    private boolean mobileConnected = false;

    private boolean comprobando = false;

    private Intent i;
    private final String auditorio1 = "Edificio A";
    private final String auditorio2 = "Edificio B";
    private final String auditorio3 = "Edificio C";
    private final String auditorio4 = "Edificio D";
    private final String auditorio5 = "Edificio F1";
    private final String auditorio6 = "Áreas deportivas";
    private String AD;
    private String st_quien;
    private String st_eventos_guardados;
    private String data;
    private String st_update;
    private int int_fecha;
    private Boolean registroCorrecto = false;
    private final static int INICIAL = 333;
    private final static int AGREGAR = 334;
    private final static int AULAS = 335;
    private int unavez = 0;
    private String st_aulas = "";

    protected ArrayList<Fecha> listaFechas = new ArrayList<>();
    protected static ArrayList<Conflictos> listaConflictos = new ArrayList<>();
    private ArrayList<Eventos> listaDeEventosNuevos;
    private Runnable mRunnable;
    private Eventos evento_a_editar;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        evento_a_editar = getIntent().getParcelableExtra("EVENTO");

        //PONEMOS LAYOT QUE VAMOS A USAR EN ESTA ACTIVITY
        setContentView(R.layout.dialog_registrar_evento);

        //OCULTAR EL TECLADO PARA QUE NO SE ABRA AL INICIAR LA ACTIVITY
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //INICIAR EL PLUGIN QUE PERMITE HACER LAS DECLARACIONES MAS RAPIDO
        ButterKnife.bind(this);

        listaConflictos.clear();
        listaFechas.clear();

        if (getIntent().getStringExtra("DONDE").equals("PRINCIPAL")) {
            i = getIntent();
            //ANIMACION DE FAB A DIALOG
            FabTransition.setup(this, sfondo);
            getWindow().getSharedElementEnterTransition();
            //listaConflictos.clear();
            //listaFechas.clear();
        } else {
            i = getIntent();
            //POSPONEMOS LA ANIMACION DE TRANSICION PARA AGREGAR UNA PERSONALIZADA
            postponeEnterTransition();

            // CREAMOS LA TRANSICION DE ENTRADA Y LA INICIAMOS
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(EditarEventosB.this));
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(slide);
            full_header.setBackgroundColor(fondoAuditorio("1"));
            //listaConflictos.clear();
            //listaFechas.clear();

            startPostponedEnterTransition();
        }

        //INICIAMOS TODOS LOS VIEWS QUE VAMOS A UTILIZAR
        iniciarObjetos();
        iniciarDatos();
    }

    private void iniciarObjetos() {

        // CREAMOS UN HANDLER PARA TAREAS CON TIEMPO DE RETRASO
        handler = new Handler();
        handler2 = new Handler();
        mRunnable = new Runnable() {
            public void run() {
                comprobarHoras();
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        listaConflictos.clear();
        listaFechas.clear();
        comprobando = false;
    }

    private void iniciarDatos() {

        // TITULO DEL EVENTO
        atv_titulo_evento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll("::", "").replace("¦", "").replace("\"", "");
                if (!s.toString().equals(result)) {
                    atv_titulo_evento.setText(result);
                    //atv_titulo_evento.setSelection(result.length());
                }
            }
        });

        // DESPUES DE PONER EL TITULO INICIAMOS EL AUTOCOMPLETAR PARA EL NOMBRE DEL EVENTO PARA QUE NOS SALGA LA LISTA DE EVENTOS
        ArrayAdapter<String> nombresEAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PrincipalB.titulos.split("¦"));
        atv_titulo_evento.setAdapter(nombresEAdapter);

        atv_titulo_evento.setText(evento_a_editar.getTitulo().replace("No se presentó - ", ""));
        //atv_titulo_evento.setSelection(atv_titulo_evento.length());

        // CONFIGURAR SPINER PARA SELECCIONAR EL EDIFICIO
        String[] items = new String[]{auditorio1, auditorio2, auditorio3, auditorio4, auditorio5, auditorio6};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sp_auditorios.setAdapter(adapter);
        sp_auditorios.setSelection(Integer.parseInt(evento_a_editar.getAuditorio()) - 1);
        sp_auditorios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!comprobando) {
                    comprobando = true;
                    comprobarHoras();
                }

                if (!auditoriosIniciado) {
                    if (i == 1) {
                        auditoriosIniciado = true;
                        st_aulas = evento_a_editar.getAula();
                        tv_aula.setText(getNombreAula(st_aulas));
                        tv_label_aula.setTextColor(Color.parseColor("#121212"));
                        selectAula.setClickable(false);
                    } else if (i == 4) {
                        auditoriosIniciado = true;
                        st_aulas = evento_a_editar.getAula();
                        tv_aula.setText(getNombreAula(st_aulas));
                        tv_label_aula.setTextColor(Color.parseColor("#121212"));
                        selectAula.setClickable(false);
                    } else {
                        auditoriosIniciado = true;
                        st_aulas = evento_a_editar.getAula();
                        tv_aula.setText(getNombreAula(st_aulas));
                        tv_label_aula.setTextColor(Color.parseColor("#121212"));
                        selectAula.setClickable(true);
                    }
                } else {
                    if (i == 1) {
                        tv_aula.setText("FBB 1");
                        st_aulas = "FBB 1";
                        tv_label_aula.setTextColor(Color.parseColor("#121212"));
                        selectAula.setClickable(false);
                    } else if (i == 4) {
                        tv_aula.setText("FBF 1");
                        st_aulas = "FBF 1";
                        tv_label_aula.setTextColor(Color.parseColor("#121212"));
                        selectAula.setClickable(false);
                    } else {
                        tv_label_aula.setTextColor(Color.RED);
                        tv_aula.setText("Selecciona el aula del evento");
                        st_aulas = "";
                        selectAula.setClickable(true);
                    }
                }

                //CONFIGURAR EL COLOR DEL HEADER SEGUN EL AUDITORIO SELECCIOANDO
                colorReveal(fondoAuditorio((i + 1) + ""));
                AD = "" + (i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // TIPO DE EVENTO
        tv_tipo_evento_label.setTextColor(Color.RED);

        // INICIAMOS EL AUTOCOMPLETAR DE TIPOS DE EVENTO Y COLOCAMOS EL TIPO DE EVENTO CORRESPONDIENTE
        ArrayAdapter<String> tiposEventoAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PrincipalB.tiposDeEvento.split("¦"));
        atv_tipo_evento.setAdapter(tiposEventoAdapter);
        atv_tipo_evento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll("::", "").replace("¦", "").replace("\"", "");
                if (!s.toString().equals(result)) {
                    atv_tipo_evento.setText(result);
                    atv_tipo_evento.setSelection(result.length());
                }

                if (atv_tipo_evento.getText().toString().trim().length() == 0) {
                    tv_tipo_evento_label.setTextColor(Color.RED);
                } else {
                    tv_tipo_evento_label.setTextColor(Color.parseColor("#121212"));
                }
            }
        });
        atv_tipo_evento.setText(evento_a_editar.getTipoEvento());
        atv_tipo_evento.setSelection(atv_tipo_evento.length());

        // COLOCAMOS LA FECHA INICIAL
        int_fecha = Integer.parseInt(evento_a_editar.getFecha());
        listaFechas.add(new Fecha(int_fecha, Integer.parseInt(evento_a_editar.getHoraInicial()), Integer.parseInt(evento_a_editar.getHoraFinal())));


        // DEPENDENCIAS
        ArrayAdapter<String> dependenciasAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PrincipalB.nombresDependencias.split("¦"));
        atv_nombre_dependencia.setAdapter(dependenciasAdapter);
        tv_nombre_dependencia.setTextColor(Color.RED);
        atv_nombre_dependencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll("::", "").replace("¦", "").replace("\"", "");
                if (!s.toString().equals(result)) {
                    atv_nombre_dependencia.setText(result);
                    atv_nombre_dependencia.setSelection(result.length());
                }

                if (atv_nombre_dependencia.getText().toString().trim().length() == 0) {
                    tv_nombre_dependencia.setTextColor(Color.RED);
                } else {
                    tv_nombre_dependencia.setTextColor(Color.parseColor("#121212"));
                }
            }
        });
        atv_nombre_dependencia.setText(evento_a_editar.getDependencia());
        atv_nombre_dependencia.setSelection(atv_nombre_dependencia.length());

        // CONFIGURAR AUTOCOMPLETAR PARA EL NOMBRE DEL SOLICITANTE
        ArrayAdapter<String> nombresSolicitanteAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PrincipalB.nombresSolicitante.split("¦"));
        atv_nombre_sol.setAdapter(nombresSolicitanteAdapter);
        tv_nom_sol_label.setTextColor(Color.RED);
        atv_nombre_sol.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll("::", "").replace("¦", "").replace("\"", "");
                if (!s.toString().equals(result)) {
                    atv_nombre_sol.setText(result);
                    atv_nombre_sol.setSelection(result.length());
                }

                if (atv_nombre_sol.getText().toString().trim().length() == 0) {
                    tv_nom_sol_label.setTextColor(Color.RED);
                } else {
                    tv_nom_sol_label.setTextColor(Color.parseColor("#121212"));
                }
            }
        });
        atv_nombre_sol.setText(evento_a_editar.getNombreSolicitante());
        atv_nombre_sol.setSelection(atv_nombre_sol.length());

        // EXTENSION DEL SOLICITANTE
        tv_ext_sol_label.setTextColor(Color.RED);
        et_ext_sol.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_ext_sol.getText().toString().trim().length() == 0) {
                    tv_ext_sol_label.setTextColor(Color.RED);
                } else {
                    tv_ext_sol_label.setTextColor(Color.parseColor("#121212"));
                }
            }
        });
        et_ext_sol.setText(evento_a_editar.getNumTelSolicitante());
        et_ext_sol.setSelection(et_ext_sol.length());

        // NOMBRE DEL RESPONSABLE
        ArrayAdapter<String> nombresRespAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PrincipalB.nombresResponsable.split("¦"));
        atv_nombre_resp.setAdapter(nombresRespAdapter);
        tv_nom_resp_label.setTextColor(Color.RED);
        atv_nombre_resp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = s.toString().replaceAll("::", "").replace("¦", "").replace("\"", "");
                if (!s.toString().equals(result)) {
                    atv_nombre_resp.setText(result);
                    atv_nombre_resp.setSelection(result.length());
                }

                if (atv_nombre_resp.getText().toString().trim().length() == 0) {
                    tv_nom_resp_label.setTextColor(Color.RED);
                } else {
                    tv_nom_resp_label.setTextColor(Color.parseColor("#121212"));
                }
            }
        });
        atv_nombre_resp.setText(evento_a_editar.getNombreResponsable());
        atv_nombre_resp.setSelection(atv_nombre_resp.length());

        // NUMERO CELULAR DEL RESPONSABLE
        tv_no_cel_label.setTextColor(Color.RED);
        et_cel_resp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_cel_resp.getText().toString().trim().length() == 0) {
                    tv_no_cel_label.setTextColor(Color.RED);
                } else {
                    tv_no_cel_label.setTextColor(Color.parseColor("#121212"));
                }
            }
        });
        et_cel_resp.setText(evento_a_editar.getCelularResponsable());
        et_cel_resp.setSelection(et_cel_resp.length());

        // NOTA CTA
        et_nota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_cel_resp.getText().toString().trim().length() == 0) {
                    tv_no_cel_label.setTextColor(Color.RED);
                } else {
                    tv_no_cel_label.setTextColor(Color.parseColor("#121212"));
                }
            }
        });
        et_nota.setText(evento_a_editar.getNotas());
        et_nota.setSelection(et_nota.length());

        // NOTA CSG
        et_nota_csg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_cel_resp.getText().toString().trim().length() == 0) {
                    tv_no_cel_label.setTextColor(Color.RED);
                } else {
                    tv_no_cel_label.setTextColor(Color.parseColor("#121212"));
                }
            }
        });
        et_nota_csg.setText(evento_a_editar.getNotas2());
        et_nota_csg.setSelection(et_nota_csg.length());

        // CONFIGURAMOS EL EDIT TEXT DE LA CONTRASEÑA
        tv_contraseña_label.setTextColor(Color.RED);
        et_contraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_contraseña.getText().toString().trim().length() == 0) {
                    tv_contraseña_label.setTextColor(Color.RED);
                } else {
                    tv_contraseña_label.setTextColor(Color.parseColor("#121212"));
                }

                switch (et_contraseña.getText().toString()) {
                    case "2100886":
                        pin_correcto_eliminar = true;
                        et_contraseña.setTextColor(Color.parseColor("#121212"));
                        st_quien = "Paz";
                        break;
                    case "131913":
                        pin_correcto_eliminar = true;
                        et_contraseña.setTextColor(Color.parseColor("#121212"));
                        st_quien = "CTA";
                        break;
                    default:
                        pin_correcto_eliminar = false;
                        et_contraseña.setTextColor(Color.RED);
                        st_quien = "";
                        break;
                }
            }
        });

        // CONFIGURAR EL RECYCLER EL SWIPE TO DISMISS DE LAS FECHAS
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_fechas.setLayoutManager(mLayoutManager);
        FechasAdaptador adaptador = new FechasAdaptador(listaFechas, EditarEventosB.this);
        rv_fechas.setAdapter(adaptador);

        ItemTouchHelper.Callback callback = new SwipeHelper(adaptador);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv_fechas);

        // CONFIGURAR EL RECYCLER DE LOS CONFLICTOS
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        rv_conflictos.setLayoutManager(mLayoutManager2);
        ConflictosAdaptador conflictosAdaptador = new ConflictosAdaptador(listaConflictos, EditarEventosB.this);
        rv_conflictos.setAdapter(conflictosAdaptador);

        switch_clase.setChecked(evento_a_editar.getClase().equals("C"));
        switch_noPresentado.setChecked(evento_a_editar.getTitulo().contains("No se presentó"));

    }

    private void comprobarHoras() {

        int x = 0;
        // SI HAY MAS DE UNA FECHA DE REGISTRO
        if (listaFechas.size() > 1) {

            // HAY QUE COMPROBAR QUE LAS DISTINTAS FECHAS NO TENGAN CONFLICTO ENTRE ELLAS
            comprobarConFechas();

            // POR CADA FECHA ES UN EVENTO DIFERENTE, COMPROBAR CADA UNO DE ESOS EVENTOS
            for (Fecha f : listaFechas) {
                comprobarConBaseDatos(f, x);
                x++;
            }
        }

        // SI SOLO HAY UNA FECHA DE REGISTRO
        else if (listaFechas.size() == 1) {
            // QUITAR TODOS LOS PROBLEMAS ENTRE FECHAS
            quitarConflictosF(true, 0, 0);

            // COMPROBAR EL UNICO EVENTO CON LA BASE DE DATOS
            comprobarConBaseDatos(listaFechas.get(0), 0);
        }

        // SI NO HAY NINGUNA FECHA
        else {
            // QUITAR TODOS LOS CONFLICTOS
            listaConflictos.clear();
            rv_conflictos.getAdapter().notifyDataSetChanged();
        }

        // SI HAY UNO O MAS CONFLICTOS EN LA LISTA
        if (listaConflictos.size() > 0) {
            // MOSTRAMOS LA LISTA EN LA PANTALLA
            rv_conflictos.setVisibility(View.VISIBLE);
            conteConflictos.setVisibility(View.VISIBLE);

            int y = 0;
            for (Fecha f : listaFechas) {
                if (f.getLabel_inicial() != null) {
                    for (Conflictos c : listaConflictos) {
                        if (c.getNum_fecha() == y) {
                            f.getLabel_inicial().setTextColor(Color.RED);
                            f.getLabel_final().setTextColor(Color.RED);
                            break;
                        } else {
                            f.getLabel_inicial().setTextColor(Color.parseColor("#121212"));
                            f.getLabel_final().setTextColor(Color.parseColor("#121212"));
                        }
                    }
                }
                y++;
            }
        } else { // SI NO HAY CONFLICTOS
            rv_conflictos.setVisibility(View.INVISIBLE);
            conteConflictos.setVisibility(View.INVISIBLE);

            // COLOREAMOS DE NEGRO TODAS LAS HORAS YA QUE NO HAY ERRORES
            for (Fecha f : listaFechas) {
                if (f.getLabel_inicial() != null) {
                    f.getLabel_inicial().setTextColor(Color.parseColor("#121212"));
                    f.getLabel_final().setTextColor(Color.parseColor("#121212"));
                }
            }

        }

        // REPETIR EL PROCESO CADA 1.2 SEG
        handler.postDelayed(mRunnable, 1200);
    }

    private void comprobarConFechas() {
        // CORRECCION DEL METODO
        for (int x = 0; x < listaFechas.size(); x++) {
            // LA FECHA QUE VAMOS A COMPARAR.
            Fecha f1 = listaFechas.get(x);

            // SEPARAMOS EN EVENTOS INDIVIDUALES A PARTIR DEL SIGUIENTE QUE TOMAMOS
            for (int y = x + 1; y < listaFechas.size(); y++) {
                // LA FECHA CON LA QUE VAMOS A COMPRAR LA PRIMERA.
                Fecha f2 = listaFechas.get(y);

                // SI LAS DOS FECHAS SON DEL MISMO DIA...
                if (f1.getDia() == f2.getDia()) {

                    // EL EVENTO NO COMIENZA POR LO MENOS CON UNA HORA DE DIFERENCIA CON EL PROXIMO
                    // LA HORA INICIAL DEL EVENTO ESTA JUSTO EN MEDIO DEL HORARIO DE OTRO
                    if (f1.getHoraInicial() > (f2.getHoraInicial() - 2) && f1.getHoraInicial() < f2.getHoraFinal()) {
                        agregarConflictoF(new Conflictos(y, x, "F"));
                        break;
                    }

                    // LA HORA INICIAL DEL EVENTO ES POR LO MENOS UNA HORA ANTES QUE EL PROX EVENTO
                    else if (f1.getHoraInicial() < (f2.getHoraInicial() - 1)) {

                        // EL EVENTO FINALIZA DENTRO DEL HORARIO DEL PROXIMO O EL HORARIO ES TAN EXTENSO QUE CABE UN EVENTO DENTRO
                        if (f1.getHoraFinal() > f2.getHoraInicial()) {
                            agregarConflictoF(new Conflictos(x, y, "F"));
                            break;
                        } else {
                            quitarConflictosF(false, x, y);
                        }
                    } else {
                        quitarConflictosF(false, x, y);
                    }
                } else {
                    quitarConflictosF(false, x, y);
                }
            }
        }

       /* for (int x = 1; x < listaFechas.size(); x++) {

            // LA FECHA 1 QUE VAMOS A COMPARAR.
            Fecha f1 = listaFechas.get(x);

            // SEPARAMOS EN EVENTOS INDIVIDUALES
            for (int y = 0; y < x; y++) {

                // LA FECHA 2 CON LA QUE VAMOS A COMPRAR LA PRIMERA.
                Fecha f2 = listaFechas.get(y);

                // SI LAS DOS FECHAS SON DEL MISMO DIA...
                if (f1.getDia() == f2.getDia()) {

                    // EL EVENTO NO COMIENZA POR LO MENOS CON UNA HORA DE DIFERENCIA CON EL PROXIMO
                    // LA HORA INICIAL DEL EVENTO ESTA JUSTO EN MEDIO DEL HORARIO DE OTRO
                    if (f1.getHoraInicial() > (f2.getHoraInicial() - 2) && f1.getHoraInicial() < f2.getHoraFinal()) {
                        agregarConflictoF(new Conflictos(x, y, "F"));
                        break;
                    }

                    // LA HORA INICIAL DEL EVENTO ES POR LO MENOS UNA HORA ANTES QUE EL PROX EVENTO
                    else if (f1.getHoraInicial() < (f2.getHoraInicial() - 1)) {

                        // EL EVENTO FINALIZA DENTRO DEL HORARIO DEL PROXIMO O EL HORARIO ES TAN EXTENSO QUE CABE UN EVENTO DENTRO
                        if (f1.getHoraFinal() > f2.getHoraInicial()) {
                            agregarConflictoF(new Conflictos(x, y, "F"));
                            break;
                        } else {
                            quitarConflictosF(false, x, y);
                        }
                    } else {
                        quitarConflictosF(false, x, y);
                    }
                } else {
                    quitarConflictosF(false, x, y);
                }
            }
        }*/
    }

    private void comprobarConBaseDatos(Fecha f, int n) {

        // HORA Y FECHA DE LA FECHA
        Calendar c = Calendar.getInstance();
        c.set(2016, 0, 1);
        c.set(Calendar.DAY_OF_YEAR, f.getDia());

        // HORA Y FECHA ACTUAL
        Calendar c2 = Calendar.getInstance();

        // COMPROBAR PRIMERO SI SE ESTA INTENTANDO REGISTRAR UN EVENTO A LAS 9:00 PM O MAS TARDE
        if (f.getHoraInicial() > 27) {
            agregarConflictoV(new Conflictos(n, 0, "V"));
        } else {

            // NO PUEDE HABER CONFLICTOS QUE V PUES AQUELLOS SON CUANDO LOS EVENTOS INICIAN 8:30 O MAS TARDE
            quitarConflictosV(n);

            // VERFICIAR SI ES EL DIA DE HOY
            /*if (c.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {

                // VERIFICAR SI EL EVENTO YA PASO O ESTA MUY CERCA DE LA HORA ACTAUAL
                if (f.getHoraInicial() <= horaASpinner(c2.get(Calendar.HOUR_OF_DAY), c2.get(Calendar.MINUTE))) {
                    agregarConflictoV1(new Conflictos(n, 0, "V1"));
                } else {

                    // QUITAR CONFLICTO V1 QUE ES DE LOS EVENTOS QUE SE QUIEREN REGISTRAR EN UN HORARIO QUE YA PASO.
                    quitarConflictosV1(n);
                }
            }
*/
            comprobarCupo(f, 0);
        }
    }

    private void comprobarCupo(Fecha f, int n) {

        for (Eventos e : lista_eventos) {

            if (!e.getTag().equals(evento_a_editar.getTag())) {
                if (Integer.parseInt(e.getFecha()) == f.getDia() && e.getAuditorio().equals(AD)) {

                    if (e.getAula().equals(st_aulas)) {
                        comprobarCupos2(f, n, e);
                    } else {
                        if (st_aulas.equals("FBC 21") && (e.getAula().equals("FBC 21 N") || e.getAula().equals("FBC 21 S"))) {
                            comprobarCupos2(f, n, e);
                        } else if ((st_aulas.equals("FBC 21 N") || st_aulas.equals("FBC 21 S")) && e.getAula().equals("FBC 21")) {
                            comprobarCupos2(f, n, e);
                        } else if (st_aulas.equals("FBC 22") && (e.getAula().equals("FBC 22 N") || e.getAula().equals("FBC 22 S"))) {
                            comprobarCupos2(f, n, e);
                        } else if ((st_aulas.equals("FBC 22 N") || st_aulas.equals("FBC 22 S")) && e.getAula().equals("FBC 22")) {
                            comprobarCupos2(f, n, e);
                        } else {
                            quitarConflictosE(n, e);
                        }
                    }

                } else {
                    quitarConflictosE(n, e);
                }
            }
        }
    }

    private boolean comprobarCupos2(Fecha f, int n, Eventos e) {
        boolean break_loop = false;
        // EL EVENTO NO COMIENZA POR LO MENOS CON UNA HORA DE DIFERENCIA CON EL PROXIMO
        // LA HORA INICIAL DEL EVENTO ESTA JUSTO EN MEDIO DEL HORARIO DE OTRO
        if (f.getHoraInicial() > (Integer.valueOf(e.getHoraInicial()) - 2) && f.getHoraInicial() < Integer.valueOf(e.getHoraFinal())) {
            agregarConflictoE(new Conflictos(n, e, fondoErrores(e.getAuditorio(), e.getClase()), "E"));
            break_loop = true;
        }

        // LA HORA INICIAL DEL EVENTO ES POR LO MENOS UNA HORA ANTES QUE EL PROX EVENTO
        else if (f.getHoraInicial() < (Integer.valueOf(e.getHoraInicial()) - 1)) {

            // EL EVENTO FINALIZA DENTRO DEL HORARIO DEL PROXIMO O EL HORARIO ES TAN EXTENSO QUE CABE UN EVENTO DENTRO
            if (f.getHoraFinal() > Integer.valueOf(e.getHoraInicial())) {
                agregarConflictoE(new Conflictos(n, e));
                break_loop = true;
            } else {
                quitarConflictosE(n, e);
            }
        } else {
            quitarConflictosE(n, e);
        }
        return break_loop;
    }

    private int horaASpinner(int i, int i1) {
        i = i - 7;
        i = i * 2;
        if (i1 >= 30) {
            i++;
        }
        return i - 2;
    }

    private void agregarConflictoV(Conflictos conflicto) {
        boolean agregar = true;
        for (Conflictos c : listaConflictos) {
            if (conflicto.getNum_fecha() == c.getNum_fecha() && conflicto.getTipo().equals("V")) {
                agregar = false;
                break;
            }
        }

        if (agregar) {
            listaConflictos.add(conflicto);
            rv_conflictos.getAdapter().notifyDataSetChanged();
        }
    }

    // CONFLICTOS DE EVENTOS QUE INICIAN DESPUES DE LAS 8:30
    private void quitarConflictosV(int xx) {
        ArrayList<Conflictos> listaConflictosARemover = new ArrayList<>();
        for (Conflictos c : listaConflictos) {
            if (c.getNum_fecha() == xx && c.getTipo().equals("V")) {
                listaConflictosARemover.add(c);
            }
        }
        if (listaConflictosARemover.size() > 0) {
            listaConflictos.removeAll(listaConflictosARemover);
            rv_conflictos.getAdapter().notifyDataSetChanged();
        }
    }

    private void agregarConflictoV1(Conflictos conflicto) {
        boolean agregar = true;
        for (Conflictos c : listaConflictos) {
            if (conflicto.getNum_fecha() == c.getNum_fecha() && conflicto.getTipo().equals("V1")) {
                agregar = false;
                break;
            }
        }

        if (agregar) {
            listaConflictos.add(conflicto);
            rv_conflictos.getAdapter().notifyDataSetChanged();
        }
    }

    private void quitarConflictosV1(int xx) {
        int x = 0;
        for (Conflictos c : listaConflictos) {
            if (c.getNum_fecha() == xx && c.getTipo().equals("V1")) {
                listaConflictos.remove(x);
                rv_conflictos.getAdapter().notifyDataSetChanged();
            }
        }
    }

    private void quitarConflictosE(int xx, Eventos ee) {
        try {
            int x = 0;
            for (Conflictos c : listaConflictos) {
                if (c.getNum_fecha() == xx && c.getQueEvento() == ee) {
                    listaConflictos.remove(x);
                    rv_conflictos.getAdapter().notifyDataSetChanged();
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void agregarConflictoE(Conflictos conflicto) {
        boolean agregar = true;
        for (Conflictos c : listaConflictos) {
            if (conflicto.getNum_fecha() == c.getNum_fecha() && conflicto.getQueEvento() == c.getQueEvento()) {
                agregar = false;
                break;
            }
        }

        if (agregar) {
            listaConflictos.add(conflicto);
            rv_conflictos.getAdapter().notifyDataSetChanged();
        }
    }

    private void agregarConflictoF(Conflictos conflicto) {
        boolean agregar = true;
        for (Conflictos c : listaConflictos) {
            if (conflicto.getNum_fecha() == c.getNum_fecha() && conflicto.getNum_fecha_2() == c.getNum_fecha_2()) {
                agregar = false;
                break;
            }
        }

        if (agregar) {
            listaConflictos.add(conflicto);
            rv_conflictos.getAdapter().notifyDataSetChanged();
        }
    }

    private void quitarConflictosF(boolean b, int xx, int yy) {

        ArrayList<Conflictos> listaConflictosARemover = new ArrayList<>();
        if (b) { //  QUITA TODOS LOS CONFLICTOS DE FECHA
            if (listaConflictos.size() > 0) {
                for (Conflictos c : listaConflictos) {
                    if (c.getTipo().equals("F")) {
                        listaConflictosARemover.add(c);
                    }
                }

                if (listaConflictosARemover.size() > 0) {
                    listaConflictos.removeAll(listaConflictosARemover);
                    rv_conflictos.getAdapter().notifyDataSetChanged();
                }
            }
        } else {
            if (listaConflictos.size() > 0) {
                for (Conflictos c : listaConflictos) {
                    if (c.getTipo().equals("F") && c.getNum_fecha() == xx && c.getNum_fecha_2() == yy) {
                        listaConflictosARemover.add(c);
                    }
                }

                if (listaConflictosARemover.size() > 0) {
                    listaConflictos.removeAll(listaConflictosARemover);
                    rv_conflictos.getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    private void colorReveal(final int fondo) {

        //COLOREAMOS DEL COLOR DEL AUDITORIO EL FONDO INVISIBLE
        color_reveal.setBackgroundColor(fondo);

        //OBTENEMOS EL CENTRO DEL FONDO INVISIBLE, SERA EL ORIGEN DEL EFECTO REVELAR
        int cx = (color_reveal.getLeft() + color_reveal.getRight()) / 2;
        int cy = (color_reveal.getTop());

        //OBTENEMOS EL RADIO FINAL PARA EL CIRCULO DEL EFECTO REVELAR
        int finalRadius = Math.max(color_reveal.getWidth(), color_reveal.getHeight());

        //Creamos un animator para la view, el radio del efecto comienza en cero;
        Animator anim = ViewAnimationUtils.createCircularReveal(color_reveal, cx, cy, 0, finalRadius);
        anim.setDuration(500L);

        //AGREGAMOS UN LISTENER PARA CUANDO TERMINE LA ANIMACION ESCONDER DE NUEVO EL VIEW
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //CUANDO LA ANIMACION PONEMOS DEL MISMO COLOR EL FONDO INICIAL
                full_header.setBackgroundColor(fondo);

                //DESPUES OCULTAMOS NUEVAMENTE EL VIEW PARA EL EFECTO REVELAR
                color_reveal.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        //HACEMOS VISIBLE EL FONDO INVISIBLE Y COMENZAMOS LA ANIMACION
        color_reveal.setVisibility(View.VISIBLE);
        anim.start();

    }

    @OnClick(R.id.iv_cerrar_dialog)
    public void cerrar(View view) {
        listaConflictos.clear();
        listaFechas.clear();
        handler.removeCallbacks(mRunnable);
        finishAfterTransition();
    }

    private int fondoAuditorio(String numero) {
        int st = 0;
        if (switch_noPresentado.isChecked()){
            return getResources().getColor(R.color.color_no_presentado);
        }
        switch (numero) {
            case "1":
                if (switch_clase.isChecked()) {
                    st = getResources().getColor(R.color.color1c);
                } else {
                    st = getResources().getColor(R.color.color1);
                }
                break;
            case "2":
                if (switch_clase.isChecked()) {
                    st = getResources().getColor(R.color.color2c);
                } else {
                    st = getResources().getColor(R.color.color2);
                }
                break;
            case "3":
                if (switch_clase.isChecked()) {
                    st = getResources().getColor(R.color.color3c);
                } else {
                    st = getResources().getColor(R.color.color3);
                }
                break;
            case "4":
                if (switch_clase.isChecked()) {
                    st = getResources().getColor(R.color.color4c);
                } else {
                    st = getResources().getColor(R.color.color4);
                }
                break;
            case "5":
                if (switch_clase.isChecked()) {
                    st = getResources().getColor(R.color.color5c);
                } else {
                    st = getResources().getColor(R.color.color5);
                }
                break;
            case "6":
                if (switch_clase.isChecked()) {
                    st = getResources().getColor(R.color.color6c);
                } else {
                    st = getResources().getColor(R.color.color6);
                }
                break;
        }
        return st;
    }

    private int fondoErrores(String numero, String clase) {
        int st = 0;
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

    @OnClick(R.id.tv_repeticion)
    public void AbrirDialogAgregarFechas() {
        if (listaConflictos.size() > 0) {
            Snackbar.make(snackposs, "Antes de agregar más fechas para el evento soluciona los problemas de cupo.", Snackbar.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, DialogAgregarFechas.class);
            intent.putExtra("DIA", int_fecha);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivityForResult(intent, AGREGAR, bundle);
        }
    }

    @OnClick(R.id.tv_guardar_evento)
    public void registrarEvento() {
        if (listaConflictos.size() > 0) {
            Snackbar.make(snackposs, "Antes de registrar el evento soluciona los problemas de cupo.", Snackbar.LENGTH_LONG).show();
        } else if (atv_titulo_evento.getText().toString().trim().equals("")) {
            Snackbar.make(snackposs, "Ingresa el titulo del evento.", Snackbar.LENGTH_LONG).show();
        } else if (tv_aula.getText().toString().trim().equals("Selecciona el aula del evento")) {
            Snackbar.make(snackposs, "Selecciona el aula en la que se llebará acabo evento.", Snackbar.LENGTH_LONG).show();
        } else if (atv_tipo_evento.getText().toString().trim().equals("")) {
            Snackbar.make(snackposs, "Ingresa el tipo evento.", Snackbar.LENGTH_LONG).show();
        } else if (listaFechas.size() < 1) {
            Snackbar.make(snackposs, "Elige una fecha para el evento.", Snackbar.LENGTH_LONG).show();
        } else if (atv_nombre_dependencia.getText().toString().equals("")) {
            Snackbar.make(snackposs, "Ingresa la dependencia a la que pertenece el evento.", Snackbar.LENGTH_LONG).show();
        } else if (atv_nombre_sol.getText().toString().trim().equals("")) {
            Snackbar.make(snackposs, "Ingresa el nombre del solicitante del evento.", Snackbar.LENGTH_LONG).show();
        } else if (et_ext_sol.getText().toString().trim().equals("")) {
            Snackbar.make(snackposs, "Ingresa la extensión del solicitante del evento.", Snackbar.LENGTH_LONG).show();
        } else if (atv_nombre_resp.getText().toString().trim().equals("")) {
            Snackbar.make(snackposs, "Ingresa el nombre del responsable del evento.", Snackbar.LENGTH_LONG).show();
        } else if (tv_no_cel_label.getText().toString().trim().equals("")) {
            Snackbar.make(snackposs, "Ingresa el No. cel. del responsable del evento.", Snackbar.LENGTH_LONG).show();
        } else if (!pin_correcto_eliminar) {
            Snackbar.make(snackposs, "Ingresa una contraseña valida para registrar el evento.", Snackbar.LENGTH_LONG).show();
        } else {
            // revisa si hay conexcion a internet
            checkNetworkConnection();
        }
    }

    private void checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                tv_guardar_evento.setEnabled(false);
                new GuardarEvento().execute();
            } else if (mobileConnected) {
                tv_guardar_evento.setEnabled(false);
                new GuardarEvento().execute();
            }
        } else {
            Snackbar.make(snackposs, "Hay un problema con la conexión a la base de datos. Verifica tu conexión a internet.", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INICIAL:
                if (resultCode == RESULT_OK) {
                    int_fecha = data.getIntExtra("DIA_DEL_AÑO", 0);
                }
                break;
            case AULAS:
                if (resultCode == RESULT_OK) {
                    st_aulas = data.getStringExtra("AULAS");
                    if (!st_aulas.equals("")) {
                        tv_label_aula.setTextColor(Color.parseColor("#121212"));
                        String s_modificado;

                        switch (st_aulas) {
                            case "FBA 17":
                                s_modificado = "Aula Dr. Fernando Pozos Ponce";
                                break;
                            case "FBC 21":
                                s_modificado = "Sala de juntas 1 completa";
                                break;
                            case "FBC 22":
                                s_modificado = "Sala de juntas 2 completa";
                                break;
                            case "FBC 23":
                                s_modificado = "Salón de usos múltiples";
                                break;
                            case "FBF 2":
                                s_modificado = "Área de uso libre";
                                break;
                            case "FBF2 1":
                                s_modificado = "Laboratorio de SIG";
                                break;
                            case "FBF2 2":
                                s_modificado = "Laboratorio CTA";
                                break;
                            case "FBC 21 N":
                                s_modificado = "Sala de juntas 1 norte";
                                break;
                            case "FBC 22 N":
                                s_modificado = "Sala de juntas 2 norte";
                                break;
                            case "FBC 21 S":
                                s_modificado = "Sala de juntas 1 sur";
                                break;
                            case "FBC 22 S":
                                s_modificado = "Sala de juntas 2 sur";
                                break;
                            case "FBD 22":
                                s_modificado = "Auditorio";
                                break;
                            case "FBD 23":
                                s_modificado = "CAG";
                                break;
                            case "FBD 24":
                                s_modificado = "Computo 1er nivel";
                                break;
                            case "FBD 25":
                                s_modificado = "Computo 2do nivel";
                                break;
                            case "FBD 26":
                                s_modificado = "Computo 3er nivel";
                                break;
                            case "FBAD 1":
                                s_modificado = "Cancha de fútbol";
                                break;
                            case "FBAD 2":
                                s_modificado = "Cancha de básquetbol";
                                break;
                            case "FBAD 3":
                                s_modificado = "Cancha de usos múltiples";
                                break;
                            case "FBAD 4":
                                s_modificado = "Pista de Jogging";
                                break;
                            default:
                                s_modificado = st_aulas;
                                break;
                        }

                        tv_aula.setText(s_modificado);

                    } else {
                        tv_aula.setText("Selecciona el aula del evento");
                        tv_label_aula.setTextColor(Color.RED);
                    }
                } else {
                    tv_aula.setText("Selecciona el aula del evento");
                    tv_label_aula.setTextColor(Color.RED);
                }

                if (!comprobando) {
                    comprobando = true;
                    comprobarHoras();
                }
                break;
            case AGREGAR:
                if (resultCode == RESULT_OK) {

                    // TRAEMOS LA LISTA DE LAS FECHAS SELECCIONADAS
                    ArrayList<Integer> listaFechas2 = data.getIntegerArrayListExtra("LISTA_FECHAS");

                    for (Integer f : listaFechas2) {
                        if (listaFechas.size() > 0) {
                            int a = listaFechas.get(0).getHoraInicial();
                            int b = listaFechas.get(0).getHoraFinal();
                            listaFechas.add(new Fecha(f, a, b));
                        } else {
                            listaFechas.add(new Fecha(f, 0, 0));
                        }
                    }

                    // LA ORDENAMOS EN ORDEN ASCENDENTE PARA NO TENER DIAS SALTEADOS
                    Collections.sort(listaFechas, new Comparator<Fecha>() {
                        @Override
                        public int compare(Fecha f1, Fecha f2) {
                            Integer i1 = f1.getDia();
                            Integer i2 = f2.getDia();

                            if (i1 == i2) {

                                Integer i3 = f1.getHoraInicial();
                                Integer i4 = f2.getHoraInicial();
                                if (i3 == i4) {
                                    Integer i5 = f1.getHoraFinal();
                                    Integer i6 = f2.getHoraFinal();
                                    return i5.compareTo(i6);
                                } else {
                                    return i3.compareTo(i4);
                                }
                            } else {
                                return i1.compareTo(i2);
                            }

                        }
                    });

                    rv_fechas.getAdapter().notifyDataSetChanged();

                    if (!comprobando) {
                        comprobando = true;
                        comprobarHoras();
                    }
                }
                break;
            case 44:
                if (resultCode == RESULT_OK) {
                    listaConflictos.clear();
                }
                break;
        }
    }

    public void seleccionarAula(View view) {
        Intent intent = null;
        switch (sp_auditorios.getSelectedItemPosition()) {
            case 0:
                intent = new Intent(this, DialogAulasA.class);
                break;
            case 2:
                intent = new Intent(this, DialogAulasC.class);
                break;
            case 3:
                intent = new Intent(this, DialogAulasD.class);
                break;
            case 5:
                intent = new Intent(this, DialogEspaciosDeportivos.class);
                break;
        }

        if (intent != null) {
            intent.putExtra("AULAS", st_aulas);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivityForResult(intent, AULAS, bundle);
        }

    }

    public void switchClase(View view) {
        colorReveal(fondoAuditorio("" + (1 + sp_auditorios.getSelectedItemPosition())));
    }

    class GuardarEvento extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Calendar calendarioRegistro = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("d/MM/yyyy'~'h:mm a");

            String st_nota = "Sin notas";
            if (!et_nota.getText().toString().trim().equals("")) {
                st_nota = et_nota.getText().toString();
            }

            String st_notas2 = "Sin notas";
            if (!et_nota_csg.getText().toString().trim().equals("")) {
                st_notas2 = et_nota_csg.getText().toString();
            }

            for (Eventos e : PrincipalB.lista_eventos) {
                if (e.getTag().equals(evento_a_editar.getTag())) {
                    PrincipalB.lista_eventos.remove(e);
                    break;
                }
            }

            listaDeEventosNuevos = new ArrayList<>();
            for (Fecha f : listaFechas) {
                Eventos nuevoEvento = new Eventos(
                        // FECHA
                        "" + f.getDia(),
                        // HORA INCIAL
                        "" + f.getHoraInicial(),
                        // HORA FINAL
                        "" + f.getHoraFinal(),
                        // TITULO
                        switch_noPresentado.isChecked() ? "No se presentó - " + atv_titulo_evento.getText().toString().trim() : atv_titulo_evento.getText().toString().trim() ,
                        // AUDITORIO
                        "" + (sp_auditorios.getSelectedItemPosition() + 1),
                        // TIPO DE EVENTO
                        atv_tipo_evento.getText().toString().trim(),
                        // NOMBRE DEL ORGANIZADOR
                        atv_nombre_sol.getText().toString().trim(),
                        // NUMERO TELEFONICO DEL ORGANIZADOR
                        et_ext_sol.getText().toString(),
                        // STATUS DEL EVENTO
                        "E",
                        // QUIEN REGISTRO
                        st_quien,
                        // CUANDO REGISTRO
                        format.format(calendarioRegistro.getTime()),
                        // NOTAS
                        st_nota,
                        // ID
                        evento_a_editar.getId(),
                        // TAG
                        "",
                        // FONDO
                        fondoAuditorio("" + (sp_auditorios.getSelectedItemPosition() + 1)),
                        switch_clase.isChecked() ? "C" : "E",
                        atv_nombre_dependencia.getText().toString().trim(),
                        atv_nombre_resp.getText().toString().trim(),
                        et_cel_resp.getText().toString().trim(),
                        st_aulas,
                        st_notas2
                );
                nuevoEvento.setTag(nuevoEvento.aTag());
                lista_eventos.add(nuevoEvento);
            }

            for (Eventos e : PrincipalB.lista_eventos) {
                if (Integer.parseInt(e.getFecha()) == int_fecha) {
                    listaDeEventosNuevos.add(e);
                    break;
                }
            }


            Collections.sort(lista_eventos, new Comparator<Eventos>() {
                @Override
                public int compare(Eventos e1, Eventos e2) {
                    Integer i1 = Integer.parseInt(e1.getFecha().replaceAll("[^0-9]+", ""));
                    Integer i2 = Integer.parseInt(e2.getFecha().replaceAll("[^0-9]+", ""));
                    Log.v("COMPARADOR", "COMPARAR: " + i1 + " CON: " + i2);
                    if (i1.equals(i2)) {
                        Integer i3 = Integer.parseInt(e1.getHoraInicial());
                        Integer i4 = Integer.parseInt(e2.getHoraInicial());

                        Log.v("COMPARADOR", "COMPARAR: " + i3 + " CON: " + i4);
                        if (i3.equals(i4)) {
                            Integer i5 = Integer.parseInt(e1.getHoraFinal());
                            Integer i6 = Integer.parseInt(e2.getHoraFinal());

                            Log.v("COMPARADOR", "COMPARAR: " + i5 + " CON: " + i6);
                            Log.v("COMPARADOR", "return: " + i5.compareTo(i6));
                            return i5.compareTo(i6);
                        } else {
                            Log.v("COMPARADOR", "return: " + i3.compareTo(i4));
                            return i3.compareTo(i4);
                        }
                    } else {

                        Log.v("COMPARADOR", "return: " + i1.compareTo(i2));
                        return i1.compareTo(i2);
                    }
                }
            });

            st_eventos_guardados = "";
            for (Eventos item : lista_eventos) {
                st_eventos_guardados += item.aTag() + "¦";
            }
            data = "";
            registroCorrecto = false;
        }

        @Override
        protected Void doInBackground(String... aa12) {
            if (st_eventos_guardados.length() > 333) {
                try {
                    URL url = new URL("http://148.202.6.72/aplicacion/datos_belenes.php");
                    HttpURLConnection aaaaa = (HttpURLConnection) url.openConnection();
                    aaaaa.setReadTimeout(0);
                    aaaaa.setConnectTimeout(0);
                    aaaaa.setRequestMethod("POST");
                    aaaaa.setDoInput(true);
                    aaaaa.setDoOutput(true);

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("comentarios", st_eventos_guardados);
                    String query = builder.build().getEncodedQuery();

                    OutputStream os = aaaaa.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();

                    aaaaa.connect();

                    int aaaaaaa = aaaaa.getResponseCode();
                    if (aaaaaaa == HttpsURLConnection.HTTP_OK) {
                        registroCorrecto = true;
                        String aaaaaaaa;
                        BufferedReader br = new BufferedReader(new InputStreamReader(aaaaa.getInputStream(), "UTF-8"));
                        while ((aaaaaaaa = br.readLine()) != null) {
                            data += aaaaaaaa;
                        }
                    } else {
                        data = "error code: " + aaaaaaa;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (data.contains("error code: ") || !registroCorrecto) {
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkNetworkConnection();
                    }
                }, 1000);
            } else {
                rv_conflictos.setVisibility(View.GONE);
                //Toast.makeText(RegistrarEventoB.this, "El evento con el ID " + PrincipalB.stNuevoId + " ha sido registrado", Toast.LENGTH_LONG).show();
                SharedPreferences prefs = getSharedPreferences("EVENTOS CUCSH", Context.MODE_PRIVATE);
                prefs.edit().putString("EVENTOS GUARDADOS", st_eventos_guardados).apply();

                if (i != null) {
                    PrincipalB.esperar = true;
                    i.putParcelableArrayListExtra("LISTA", listaDeEventosNuevos);
                    setResult(RESULT_OK, i);
                }

                new GuardarUpdate().execute();

            }
        }
    }

    class GuardarUpdate extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data = "";
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yy HH:mm:ss");
            st_update = format.format(c.getTime());
        }

        @Override
        protected Void doInBackground(String... aa12) {
            if (st_update.length() == 19) {
                try {
                    URL url = new URL("http://148.202.6.72/aplicacion/update_belenes.php");
                    HttpURLConnection aaaaa = (HttpURLConnection) url.openConnection();
                    aaaaa.setReadTimeout(0);
                    aaaaa.setConnectTimeout(0);
                    aaaaa.setRequestMethod("POST");
                    aaaaa.setDoInput(true);
                    aaaaa.setDoOutput(true);

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("comentarios", st_update);
                    String query = builder.build().getEncodedQuery();

                    OutputStream os = aaaaa.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();

                    aaaaa.connect();

                    int aaaaaaa = aaaaa.getResponseCode();
                    if (aaaaaaa == HttpsURLConnection.HTTP_OK) {
                        registroCorrecto = true;
                        String aaaaaaaa;
                        BufferedReader br = new BufferedReader(new InputStreamReader(aaaaa.getInputStream(), "UTF-8"));
                        while ((aaaaaaaa = br.readLine()) != null) {
                            data += aaaaaaaa;
                        }
                    } else {
                        data = "error code: " + aaaaaaa;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (data.contains("error code: ")) {
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new GuardarUpdate().execute();
                    }
                }, 1000);
            } else {

                cerrar(null);
            }
        }
    }

    private String getNombreAula(String st_aulas) {
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

}

