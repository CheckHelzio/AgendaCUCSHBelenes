package checkhelzio.ccv.agendacucshbelenes;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import checkhelzio.ccv.agendacucshbelenes.util.Data;
import checkhelzio.ccv.agendacucshbelenes.util.EliminarEvento;
import checkhelzio.ccv.agendacucshbelenes.util.RegistrarUpdate;

public class DialogInfoEventosHelzio extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    private static final int EDITAR_EVENTO = 313;
    RelativeLayout fondo;
    TextView tv_titulo;
    TextView tv_auditorios;
    TextView tv_tipoActividad;
    TextView tv_fecha;
    TextView tv_dependencia;
    TextView tv_ext_sol;
    TextView tv_nom_solic;
    TextView tv_nota;
    TextView tv_nota_csg;
    TextView tv_id;
    TextView tv_horario;
    TextView tv_nom_resp;
    TextView tv_cel_resp;
    TextView tv_marca_agua;
    EditText et_pin;
    ImageView iv_delete;
    CoordinatorLayout snackposs;
    ImageButton fab_edit;

    private Boolean pin_correcto_eliminar = false;
    private String alerta = "";
    private Eventos evento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_info_evento);


        fondo = findViewById(R.id.fondo);
        tv_titulo = findViewById(R.id.tv_titulo);
        tv_auditorios = findViewById(R.id.tv_auditorios);
        tv_tipoActividad = findViewById(R.id.tv_tipo_actividad);
        tv_fecha = findViewById(R.id.tv_fecha);
        tv_dependencia = findViewById(R.id.tv_nombre_dependencia);
        tv_ext_sol = findViewById(R.id.tv_ext_sol);
        tv_nom_solic = findViewById(R.id.tv_nombre_solicitante);
        tv_nota = findViewById(R.id.tv_notas);
        tv_nota_csg = findViewById(R.id.tv_notas_csg);
        tv_id = findViewById(R.id.tv_id);
        tv_horario = findViewById(R.id.tv_horario);
        tv_nom_resp = findViewById(R.id.tv_nombre_responsable);
        tv_cel_resp = findViewById(R.id.tv_cel_resp);
        tv_marca_agua = findViewById(R.id.marca_agua);
        et_pin = findViewById(R.id.et_pin);
        tv_titulo = findViewById(R.id.titulo);
        iv_delete = findViewById(R.id.iv_delete);
        fab_edit = findViewById(R.id.fab_edit);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        postponeEnterTransition();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        evento = getIntent().getParcelableExtra("EVENTO");
        tv_titulo.setText(evento.getTitulo());
        String aula = Data.nombreAuditorio(evento.getAuditorio()) + " - " + Data.getNombreAula(evento.getAula());
        tv_auditorios.setText(aula);
        if (evento.getClase().equals("C")) {
            String clase = "Clase - " + evento.getTipoEvento();
            tv_tipoActividad.setText(clase);
        } else {
            tv_tipoActividad.setText(evento.getTipoEvento());
        }
        tv_fecha.setText(fecha(evento.getFecha()));
        String horario = horasATetxto(Integer.parseInt(evento.getHoraInicial().replaceAll("[^0-9]+", ""))) + " - " + horasATetxto(Integer.parseInt(evento.getHoraFinal().replaceAll("[^0-9]+", "")));
        tv_horario.setText(horario);
        tv_dependencia.setText(evento.getDependencia());
        tv_ext_sol.setText(evento.getNumTelSolicitante().equals("") ? "Sin extensión" : evento.getNumTelSolicitante());
        tv_nota.setText(evento.getNotas());
        tv_nota_csg.setText(evento.getNotas2());
        tv_nom_resp.setText(evento.getNombreResponsable());
        tv_cel_resp.setText(evento.getCelularResponsable());
        tv_id.setText(evento.getId());
        tv_nom_solic.setText(evento.getNombreSolicitante());
        String marca_agua = status(evento.getStatusEvento()) + " por " + evento.getQuienR() + "  -  " + evento.getCuandoR().split("~")[0] + " a las " + evento.getCuandoR().split("~")[1];
        tv_marca_agua.setText(marca_agua);
        fondo.setBackgroundColor(evento.getFondo());

        et_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (et_pin.getText().toString()) {
                    case "1308":
                        pin_correcto_eliminar = true;
                        et_pin.setTextColor(Color.WHITE);
                        break;
                    case "2886":
                        pin_correcto_eliminar = true;
                        et_pin.setTextColor(Color.WHITE);
                        break;
                    case "4343":
                        pin_correcto_eliminar = true;
                        et_pin.setTextColor(Color.WHITE);
                        break;
                    default:
                        pin_correcto_eliminar = false;
                        et_pin.setTextColor(Color.RED);
                        break;
                }
            }
        });

        iv_delete.setOnClickListener(view -> {
            if (pin_correcto_eliminar) {
                if (Eliminar(evento.getFecha(), evento.getHoraFinal())) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DialogInfoEventosHelzio.this);
                    alertDialogBuilder.setMessage("\n" + (alerta.equals("") ? "¿Deseas eliminar este evento?" : alerta));
                    alertDialogBuilder.setPositiveButton("ACEPTAR",
                            (arg0, arg1) -> getSupportLoaderManager().initLoader(0, null, DialogInfoEventosHelzio.this));
                    alertDialogBuilder.setNegativeButton("CANCELAR",
                            null);

                    alertDialogBuilder.create().show();

                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DialogInfoEventosHelzio.this);
                    alertDialogBuilder.setMessage("\n" + (alerta.equals("") ? "Este evento finalizó hace más de 36 horas, no se puede eliminar" : alerta));
                    alertDialogBuilder.setPositiveButton("ACEPTAR", null);

                    alertDialogBuilder.create().show();
                }
            } else {
                Snackbar snack = Snackbar.make(snackposs, "La contraseña que ingresaste es incorrecta", Snackbar.LENGTH_LONG);
                snack.show();
            }
        });

        fab_edit.setOnClickListener(view -> EditarEnvento());

        fondo.postDelayed(() -> {
                    final Rect endBounds = new Rect(fondo.getLeft(), fondo.getTop(), fondo.getRight(), fondo.getBottom());
                    ChangeBoundBackground2.setup(DialogInfoEventosHelzio.this, fondo, endBounds, getViewBitmap(fondo));
                    getWindow().getSharedElementEnterTransition();
                    startPostponedEnterTransition();
                }
                , 30);
    }

    private String status(String statusEvento) {
        switch (statusEvento) {
            case "R":
                statusEvento = "Registrado";
                break;
            case "E":
                statusEvento = "Editado";
                break;
        }
        return statusEvento;
    }

    private boolean Eliminar(String fecha, String hora_final) {
        Boolean eliminar;
        //CALENDARIO CON LA FECHA DE HOY
        Calendar c = Calendar.getInstance();

        //CALENDARIO CON LA FECHA FINAL Y HORA FINAL DEL EVENTO
        Calendar e = Calendar.getInstance();
        e.set(2016, 0, 1);
        e.set(Calendar.DAY_OF_YEAR, Integer.parseInt(fecha));
        horasaCalendario(e, hora_final);

        int plazoEliminarEditar = 36;
        int unDiaEnMilisegundos = 1000 * 60 * 60 * plazoEliminarEditar;

        // SI LA FECHA FINAL Y HORA FINAL DEL EVENTO EN ANTERIOR A LA FECHA DE AYER EL EVENTO NO SE PUEDE ELIMINAR PORQUE YA TERMINO
        // SE AMPLIO EL PLAZO A 24 HRS PARA MODIFICAR Y ELIMINAR EVENTOS A PETICION DE PAZ.
        eliminar = e.getTimeInMillis() >= (c.getTimeInMillis() - unDiaEnMilisegundos);
        return eliminar;
    }

    private void horasaCalendario(Calendar e, String hora_inicial) {

        int hora = (Integer.parseInt(hora_inicial) / 2) + 7;
        e.set(Calendar.HOUR_OF_DAY, hora);

        if (Integer.parseInt(hora_inicial) % 2 != 0) {
            e.set(Calendar.MINUTE, 30);
        }
    }

    public void EditarEnvento() {
        if (Eliminar(evento.getFecha(), evento.getHoraFinal())) {
            Intent intent = new Intent(this, EditarEventosB.class);
            intent.putExtra("DONDE", "PRINCIPAL");
            intent.putExtra("EVENTO", evento);
            FabTransition.addExtras(intent, (Integer) getAcentColor(), R.drawable.ic_edit);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab_edit,
                    getString(R.string.transition_date_dialog_helzio));
            startActivityForResult(intent, EDITAR_EVENTO, options.toBundle());
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DialogInfoEventosHelzio.this);
            alertDialogBuilder.setMessage("\n" + (alerta.equals("") ? "Este evento finalizó hace más de 36 horas, no se puede editar" : alerta.replace("eliminar", "editar")));
            alertDialogBuilder.setPositiveButton("ACEPTAR", null);

            alertDialogBuilder.create().show();
        }
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

    private String fecha(String fecha_inicial) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(2016, 0, 1);
        fecha.add(Calendar.DAY_OF_YEAR, Integer.valueOf(fecha_inicial.replaceAll("[^0-9]+", "")) - 1);
        SimpleDateFormat format = new SimpleDateFormat("cccc',' d 'de' MMMM 'del' yyyy", Locale.getDefault());
        fecha_inicial = (format.format(fecha.getTime()));
        return fecha_inicial;
    }

    @Override
    public void onBackPressed() {
        dismiss(null);
    }

    public void dismiss(View view) {
        finishAfterTransition();
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

    private String horasATetxto(int numero) {
        String am_pm, st_min, st_hora;

        int hora = (numero / 2) + 7;
        if (hora > 12) {
            hora = hora - 12;
            am_pm = " PM";
        } else {
            am_pm = " AM";
        }

        if (hora < 10) {
            st_hora = "0" + hora;
        } else {
            st_hora = "" + hora;
        }

        if (numero % 2 == 0) {
            st_min = "00";
        } else {
            st_min = "30";
        }

        return st_hora + ":" + st_min + am_pm;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String s_fecha = evento.getFecha();

            ArrayList<Eventos> listaEventosNuevos = data.getParcelableArrayListExtra("LISTA");
            evento = listaEventosNuevos.get(0);
            tv_titulo.setText(evento.getTitulo());
            tv_auditorios.setText(nombreAuditorio(evento.getAuditorio()));
            tv_tipoActividad.setText(evento.getTipoEvento());
            tv_fecha.setText(fecha(evento.getFecha()));
            String hora = horasATetxto(Integer.parseInt(evento.getHoraInicial().replaceAll("[^0-9]+", ""))) + " - " + horasATetxto(Integer.parseInt(evento.getHoraFinal().replaceAll("[^0-9]+", "")));
            tv_horario.setText(hora);
            tv_nom_resp.setText(evento.getNombreResponsable());
            tv_cel_resp.setText(evento.getCelularResponsable().equals("") ? "Sin número telefónico" : evento.getCelularResponsable());
            tv_nota.setText(evento.getNotas());
            tv_dependencia.setText(evento.getDependencia());
            tv_nom_solic.setText(evento.getNombreSolicitante());
            tv_ext_sol.setText(evento.getNumTelSolicitante().equals("") ? "Sin número" : evento.getNumTelSolicitante());
            tv_nota_csg.setText(evento.getNotas2());
            tv_id.setText(evento.getId());
            String marca_agua = status(evento.getStatusEvento()) + " por " + evento.getQuienR() + "  -  " + evento.getCuandoR().split("~")[0] + " a las " + evento.getCuandoR().split("~")[1];
            tv_marca_agua.setText(marca_agua);
            fondo.setBackgroundColor(evento.getFondo());

            ArrayList<Eventos> listaDeRetorno = new ArrayList<>();
            for (Eventos e : listaEventosNuevos) {
                if (e.getFecha().equals(s_fecha)) {
                    listaDeRetorno.add(e);
                }
            }

            Intent i = getIntent();
            i.putParcelableArrayListExtra("LISTA", listaDeRetorno);
            setResult(RESULT_FIRST_USER, i);
            finishAfterTransition();
        }
    }

    @Override
    public android.support.v4.content.Loader<Object> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 0:
                return new EliminarEvento(this, evento.aTag());
            case 1:
                return new RegistrarUpdate(this);
            default:
                return new EliminarEvento(this, evento.aTag());
        }
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case 0:
                if (data == null) {
                    Snackbar.make(snackposs, "Hay un problema con la conexión a la base de datos. Verifica tu conexión a internet.", Snackbar.LENGTH_LONG).show();
                } else {
                    getSupportLoaderManager().initLoader(1, null, this);
                }
                break;
            case 1:
                if (data != null) {
                    SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);
                    prefs.edit().putString(getString(R.string.prefs_st_eventos_guardados), data.toString()).apply();
                    Intent i = getIntent();
                    i.putExtra("POSITION", i.getIntExtra("POSITION", 0));
                    setResult(RESULT_OK, i);
                    finishAfterTransition();
                } else {
                    Snackbar.make(snackposs, "Hay un problema con la conexión a la base de datos. Verifica tu conexión a internet.", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Object> loader) {

    }

    private String nombreAuditorio(String numero) {
        String st = "";
        switch (numero) {
            case "1":
                st = "Auditorio Salvador Allende";
                break;
            case "2":
                st = "Auditorio Silvano Barba";
                break;
            case "3":
                st = "Auditorio Carlos Ramírez";
                break;
            case "4":
                st = "Auditorio Adalberto Navarro";
                break;
            case "5":
                st = "Sala de Juicios Orales Mariano Otero";
                break;
        }
        return st;
    }
}


