package checkhelzio.ccv.agendacucshbelenes;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;

import checkhelzio.ccv.agendacucshbelenes.util.Data;
import checkhelzio.ccv.agendacucshbelenes.util.GuardarEvento;
import checkhelzio.ccv.agendacucshbelenes.util.RegistrarUpdate;

public class RegistrarEventoB extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    private final static int INICIAL = 333;
    private final static int AGREGAR = 334;
    private final static int AULAS = 335;
    protected static ArrayList<Conflictos> listaConflictos = new ArrayList<>();
    protected ArrayList<Fecha> listaFechas = new ArrayList<>();
    private View color_reveal;
    private AutoCompleteTextView atv_titulo_evento;
    private AutoCompleteTextView atv_nombre_dependencia;
    private AutoCompleteTextView atv_tipo_evento;
    private AutoCompleteTextView atv_nombre_sol;
    private AutoCompleteTextView atv_nombre_resp;
    private RelativeLayout full_header;
    private Spinner sp_auditorios;
    private TextView tv_tipo_evento_label;
    private TextView tv_nom_resp_label;
    private TextView tv_nom_sol_label;
    private TextView tv_contraseña_label;
    private RelativeLayout selectAula;
    private TextView tv_aula;
    private TextView tv_ext_sol_label;
    private TextView tv_nombre_dependencia;
    private TextView tv_label_aula;
    private TextView tv_no_cel_label;
    private EditText et_ext_sol;
    private EditText et_contraseña;
    private EditText et_nota;
    private EditText et_nota_csg;
    private EditText et_cel_resp;
    private RecyclerView rv_conflictos;
    private RecyclerView rv_fechas;
    private RelativeLayout contenedorAsistencia;
    private CoordinatorLayout snackposs;
    private RelativeLayout conteConflictos;
    private Switch switch_clase;
    private Handler handler;
    private boolean pin_correcto_eliminar = false;
    private boolean comprobando = false;
    private Intent i;
    private String AD;
    private String st_quien;
    private int int_fecha;
    private String st_aulas = "";
    private ArrayList<Eventos> listaDeEventosNuevos;
    private Runnable mRunnable;
    private Eventos evento;
    private SharedPreferences prefs;
    private ArrayList<Eventos> listaEventos;

    public RegistrarEventoB() {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PONEMOS LAYOT QUE VAMOS A USAR EN ESTA ACTIVITY
        setContentView(R.layout.dialog_registrar_evento);
        RelativeLayout sfondo = findViewById(R.id.ly);
        switch_clase = findViewById(R.id.switch_clase);
        full_header = findViewById(R.id.toolbar_dialog);

        try {
            evento = getIntent().getParcelableExtra("EVENTO");
        } catch (Exception ignored) {
        }

        //OCULTAR EL TECLADO PARA QUE NO SE ABRA AL INICIAR LA ACTIVITY
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getIntent().getStringExtra("DONDE").equals("PRINCIPAL")) {
            //ANIMACION DE FAB A DIALOG
            FabTransition.setup(this, sfondo);
            getWindow().getSharedElementEnterTransition();
        } else {
            i = getIntent();
            //POSPONEMOS LA ANIMACION DE TRANSICION PARA AGREGAR UNA PERSONALIZADA
            postponeEnterTransition();

            // CREAMOS LA TRANSICION DE ENTRADA Y LA INICIAMOS
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(RegistrarEventoB.this));
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(slide);
            full_header.setBackgroundColor(fondoAuditorio("1"));

            startPostponedEnterTransition();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //INICIAMOS TODOS LOS VIEWS QUE VAMOS A UTILIZAR
        iniciarObjetos();
        iniciarDatos();
    }

    private void iniciarObjetos() {

        color_reveal = findViewById(R.id.color_reveal);
        atv_titulo_evento = findViewById(R.id.atv_tituto_evento);
        atv_nombre_dependencia = findViewById(R.id.atv_nombre_dependencia);
        atv_tipo_evento = findViewById(R.id.atv_tipo_evento);
        atv_nombre_sol = findViewById(R.id.atv_nombre_sol);
        atv_nombre_resp = findViewById(R.id.atv_nombre_resp);
        sp_auditorios = findViewById(R.id.sp_auditorios);
        TextView tv_repeticion = findViewById(R.id.tv_repeticion);
        tv_tipo_evento_label = findViewById(R.id.tv_tipo_evento_label);
        tv_nom_resp_label = findViewById(R.id.tv_nom_resp_label);
        tv_nom_sol_label = findViewById(R.id.tv_nom_sol_label);
        tv_contraseña_label = findViewById(R.id.tv_contraseña_label);
        selectAula = findViewById(R.id.selectAula);
        tv_aula = findViewById(R.id.tv_aula);
        tv_ext_sol_label = findViewById(R.id.tv_ext_sol_label);
        tv_nombre_dependencia = findViewById(R.id.tv_nombre_dependencia);
        tv_label_aula = findViewById(R.id.tv_label_aula);
        tv_no_cel_label = findViewById(R.id.tv_no_cel_label);
        et_ext_sol = findViewById(R.id.et_ext_sol);
        et_contraseña = findViewById(R.id.et_contraseña);
        et_nota = findViewById(R.id.et_nota);
        et_nota_csg = findViewById(R.id.et_nota_csg);
        et_cel_resp = findViewById(R.id.et_cel_resp);
        rv_conflictos = findViewById(R.id.rv_conflictos);
        rv_fechas = findViewById(R.id.rv_fechas);
        contenedorAsistencia = findViewById(R.id.contenedor_asistencia);
        snackposs = findViewById(R.id.snackposs);
        conteConflictos = findViewById(R.id.conteConflictos);
        TextView tv_guardar_evento = findViewById(R.id.tv_guardar_evento);

        tv_guardar_evento.setOnClickListener(view -> registrarEvento());
        tv_repeticion.setOnClickListener(v -> abrirDialogAgregarFechas());

        // CREAMOS UN HANDLER PARA TAREAS CON TIEMPO DE RETRASO
        handler = new Handler();
        mRunnable = this::comprobarHoras;
    }

    private void iniciarDatos() {
        prefs = getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);
        contenedorAsistencia.setVisibility(View.GONE);

        listaEventos = Data.getListaEventos(RegistrarEventoB.this);

        // CONFIGURAR TEXT WATCHERS
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
                    atv_titulo_evento.setSelection(result.length());
                }
            }
        });
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

        String titulos = prefs.getString(getString(R.string.prefs_titulos), "");
        String tipos_de_evento = prefs.getString(getString(R.string.prefs_tipos_evento), "");
        String nombresDependencias = prefs.getString(getString(R.string.prefs_nombres_dependencias), "");
        String nombresSolicitantes = prefs.getString(getString(R.string.prefs_nombres_solicitante), "");
        String nombresResponsables = prefs.getString(getString(R.string.prefs_nombres_responsables), "");

        // DESPUES DE PONER EL TITULO INICIAMOS EL AUTOCOMPLETAR PARA EL NOMBRE DEL EVENTO PARA QUE NOS SALGA LA LISTA DE EVENTOS
        ArrayAdapter<String> nombresEAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titulos.split("¦"));
        atv_titulo_evento.setAdapter(nombresEAdapter);

        // CONFIGURAR SPINER PARA SELECCIONAR EL EDIFICIO
        String auditorio1 = "Edificio A";
        String auditorio2 = "Edificio B";
        String auditorio3 = "Edificio C";
        String auditorio4 = "Edificio D";
        String auditorio5 = "Edificio F1";
        String auditorio6 = "Áreas deportivas";
        String[] items = new String[]{auditorio1, auditorio2, auditorio3, auditorio4, auditorio5, auditorio6};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sp_auditorios.setAdapter(adapter);
        sp_auditorios.setSelection(0);
        sp_auditorios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!comprobando) {
                    comprobando = true;
                    comprobarHoras();
                }

                if (i == 1) {
                    tv_aula.setText(R.string.fbb1);
                    st_aulas = "FBB 1";
                    tv_label_aula.setTextColor(Color.parseColor("#121212"));
                    selectAula.setClickable(false);
                } else if (i == 4) {
                    tv_aula.setText(R.string.fbf1);
                    st_aulas = "FBF 1";
                    tv_label_aula.setTextColor(Color.parseColor("#121212"));
                    selectAula.setClickable(false);
                } else {
                    tv_label_aula.setTextColor(Color.RED);
                    tv_aula.setText(getString(R.string.selecciona_aula));
                    st_aulas = "";
                    selectAula.setClickable(true);
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
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tipos_de_evento.split("¦"));
        atv_tipo_evento.setAdapter(tiposEventoAdapter);

        // COLOCAMOS COMO FECHA INICIAL LA FECHA DEL DIA DEL EVENTO
        int_fecha = getIntent().getIntExtra("DIA_AÑO", -1);

        // CONFIGURAR LA PRIMER FECHA EN LA LISTA DE FECHAS
        if (int_fecha != -1) {
            listaFechas.add(new Fecha(int_fecha, 0, 2));
        }

        // DEPENDENCIAS
        ArrayAdapter<String> dependenciasAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresDependencias.split("¦"));
        atv_nombre_dependencia.setAdapter(dependenciasAdapter);
        tv_nombre_dependencia.setTextColor(Color.RED);

        // CONFIGURAR AUTOCOMPLETAR PARA EL NOMBRE DEL SOLICITANTE
        ArrayAdapter<String> nombresSolicitanteAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresSolicitantes.split("¦"));
        atv_nombre_sol.setAdapter(nombresSolicitanteAdapter);
        tv_nom_sol_label.setTextColor(Color.RED);

        // EXTENSION DEL SOLICITANTE
        tv_ext_sol_label.setTextColor(Color.RED);

        // NOMBRE DEL RESPONSABLE
        ArrayAdapter<String> nombresRespAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresResponsables.split("¦"));
        atv_nombre_resp.setAdapter(nombresRespAdapter);
        tv_nom_resp_label.setTextColor(Color.RED);

        // NUMERO CELULAR DEL RESPONSABLE
        tv_no_cel_label.setTextColor(Color.RED);

        // CONFIGURAMOS EL EDIT TEXT DE LA CONTRASEÑA
        tv_contraseña_label.setTextColor(Color.RED);

        // CONFIGURAR EL RECYCLER EL SWIPE TO DISMISS DE LAS FECHAS
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv_fechas.setLayoutManager(mLayoutManager);
        FechasAdaptador adaptador = new FechasAdaptador(listaFechas, RegistrarEventoB.this);
        rv_fechas.setAdapter(adaptador);

        ItemTouchHelper.Callback callback = new SwipeHelper(adaptador);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv_fechas);

        // CONFIGURAR EL RECYCLER DE LOS CONFLICTOS
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        rv_conflictos.setLayoutManager(mLayoutManager2);
        ConflictosAdaptador conflictosAdaptador = new ConflictosAdaptador(listaConflictos, RegistrarEventoB.this);
        rv_conflictos.setAdapter(conflictosAdaptador);
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
        }

        for (int x = 1; x < listaFechas.size(); x++) {

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
        }
    }

    private void comprobarConBaseDatos(Fecha f, int n) {

        // HORA Y FECHA DE LA FECHA
        Calendar c = Calendar.getInstance();
        c.set(2016, 0, 1);
        c.set(Calendar.DAY_OF_YEAR, f.getDia());

        // COMPROBAR PRIMERO SI SE ESTA INTENTANDO REGISTRAR UN EVENTO A LAS 9:00 PM O MAS TARDE
        if (f.getHoraInicial() > 27) {
            agregarConflictoV(new Conflictos(n, 0, "V"));
        } else {

            // NO PUEDE HABER CONFLICTOS QUE V PUES AQUELLOS SON CUANDO LOS EVENTOS INICIAN 8:30 O MAS TARDE
            quitarConflictosV(n);
            comprobarCupo(f);
        }
    }

    private void comprobarCupo(Fecha f) {

        for (Eventos e : listaEventos) {

            if (Integer.parseInt(e.getFecha()) == f.getDia() && e.getAuditorio().equals(AD)) {

                if (e.getAula().equals(st_aulas)) {
                    comprobarCupos2(f, e);
                } else {
                    if (st_aulas.equals("FBC 21") && (e.getAula().equals("FBC 21 N") || e.getAula().equals("FBC 21 S"))) {
                        comprobarCupos2(f, e);
                    } else if ((st_aulas.equals("FBC 21 N") || st_aulas.equals("FBC 21 S")) && e.getAula().equals("FBC 21")) {
                        comprobarCupos2(f, e);
                    } else if (st_aulas.equals("FBC 22") && (e.getAula().equals("FBC 22 N") || e.getAula().equals("FBC 22 S"))) {
                        comprobarCupos2(f, e);
                    } else if ((st_aulas.equals("FBC 22 N") || st_aulas.equals("FBC 22 S")) && e.getAula().equals("FBC 22")) {
                        comprobarCupos2(f, e);
                    } else {
                        quitarConflictosE(e);
                    }
                }

            } else {
                quitarConflictosE(e);
            }

        }
    }

    private void comprobarCupos2(Fecha f, Eventos e) {
        // EL EVENTO NO COMIENZA POR LO MENOS CON UNA HORA DE DIFERENCIA CON EL PROXIMO
        // LA HORA INICIAL DEL EVENTO ESTA JUSTO EN MEDIO DEL HORARIO DE OTRO
        if (f.getHoraInicial() > (Integer.valueOf(e.getHoraInicial()) - 2) && f.getHoraInicial() < Integer.valueOf(e.getHoraFinal())) {
            agregarConflictoE(new Conflictos(0, e, fondoErrores(e.getAuditorio(), e.getClase()), "E"));
        }

        // LA HORA INICIAL DEL EVENTO ES POR LO MENOS UNA HORA ANTES QUE EL PROX EVENTO
        else if (f.getHoraInicial() < (Integer.valueOf(e.getHoraInicial()) - 1)) {

            // EL EVENTO FINALIZA DENTRO DEL HORARIO DEL PROXIMO O EL HORARIO ES TAN EXTENSO QUE CABE UN EVENTO DENTRO
            if (f.getHoraFinal() > Integer.valueOf(e.getHoraInicial())) {
                agregarConflictoE(new Conflictos(0, e));
            } else {
                quitarConflictosE(e);
            }
        } else {
            quitarConflictosE(e);
        }
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

    private void quitarConflictosE(Eventos ee) {
        try {
            int x = 0;
            for (Conflictos c : listaConflictos) {
                if (c.getNum_fecha() == 0 && c.getQueEvento() == ee) {
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

    public void cerrar(View view) {
        listaConflictos.clear();
        listaFechas.clear();
        handler.removeCallbacks(mRunnable);
        finishAfterTransition();
    }

    private int fondoAuditorio(String numero) {
        int st = 0;
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

    public void abrirDialogAgregarFechas() {
        if (listaConflictos.size() > 0) {
            Snackbar.make(snackposs, "Antes de agregar más fechas para el evento soluciona los problemas de cupo.", Snackbar.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, DialogAgregarFechas.class);
            intent.putExtra("DIA", int_fecha);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivityForResult(intent, AGREGAR, bundle);
        }
    }

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
            // Todos los datos son correctos... registrar el evento
            Bundle bundle = new Bundle();
            bundle.putString("TITULO", atv_titulo_evento.getText().toString().trim());
            bundle.putString("AUDITORIO", "" + (sp_auditorios.getSelectedItemPosition() + 1));
            bundle.putString("TIPO DE EVENTO", atv_tipo_evento.getText().toString().trim());
            bundle.putString("NOMBRE DEL SOLICITANTE", atv_nombre_sol.getText().toString().trim());
            bundle.putString("EXTRENSION DEL SOLICITANTE", TextUtils.isEmpty(et_ext_sol.getText().toString()) ? "Sin número" : et_ext_sol.getText().toString());
            bundle.putString("ESTATUS DEL EVENTO", evento == null ? "R" : "E");
            bundle.putString("QUIEN REGISTRO", st_quien);
            bundle.putString("NOTA", TextUtils.isEmpty(et_nota.getText().toString()) ? "Sin notas" : et_nota.getText().toString());
            bundle.putString("NOTA 2", TextUtils.isEmpty(et_nota_csg.getText().toString()) ? "Sin notas" : et_nota_csg.getText().toString());
            bundle.putString("FONDO", "" + (sp_auditorios.getSelectedItemPosition() + 1));
            bundle.putString("CLASE", switch_clase.isChecked() ? "C" : "E");
            bundle.putString("DEPENDENCIA", atv_nombre_dependencia.getText().toString().trim());
            bundle.putString("NOMBRE DEL RESPONSABLE", atv_nombre_resp.getText().toString().trim());
            bundle.putString("CELULAR DEL RESPONSABLE", et_cel_resp.getText().toString().trim());
            bundle.putString("AULA", st_aulas);
            bundle.putParcelableArrayList("LISTA FECHAS", listaFechas);
            int folio = prefs.getInt(getString(R.string.prefs_id_prox), 0);
            bundle.putInt("FOLIO", evento == null ? folio : Integer.parseInt(evento.getId()));
            bundle.putInt("FECHA DEL DIA", int_fecha);
            getSupportLoaderManager().initLoader(0, bundle, this);
            Snackbar.make(snackposs, "Registrando evento, por favor espere...", Snackbar.LENGTH_INDEFINITE).show();
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
                        tv_aula.setText(getString(R.string.selecciona_aula));
                        tv_label_aula.setTextColor(Color.RED);
                    }
                } else {
                    tv_aula.setText(getString(R.string.selecciona_aula));
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
                    Collections.sort(listaFechas, (f1, f2) -> {
                        Integer i1 = f1.getDia();
                        Integer i2 = f2.getDia();

                        if (Objects.equals(i1, i2)) {

                            Integer i3 = f1.getHoraInicial();
                            Integer i4 = f2.getHoraInicial();
                            if (Objects.equals(i3, i4)) {
                                Integer i5 = f1.getHoraFinal();
                                Integer i6 = f2.getHoraFinal();
                                return i5.compareTo(i6);
                            } else {
                                return i3.compareTo(i4);
                            }
                        } else {
                            return i1.compareTo(i2);
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

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 0:
                return new GuardarEvento(this, args, evento);
            case 1:
                return new RegistrarUpdate(this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case 0:
                if (data == null) {
                    Snackbar.make(snackposs, "Hay un problema con la conexión a la base de datos. Verifica tu conexión a internet e intentalo nuevamente", Snackbar.LENGTH_LONG).show();
                } else {
                    listaDeEventosNuevos = (ArrayList<Eventos>) data;
                    getSupportLoaderManager().initLoader(1, null, this);
                }
                break;
            case 1:
                if (data != null) {
                    rv_conflictos.setVisibility(View.GONE);
                    i = getIntent();
                    i.putParcelableArrayListExtra("LISTA", listaDeEventosNuevos);
                    setResult(RESULT_OK, i);
                    cerrar(null);
                } else {
                    Snackbar.make(snackposs, "Hay un problema con la conexión a la base de datos. Verifica tu conexión a internet e intentalo nuevamente.", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }
}

