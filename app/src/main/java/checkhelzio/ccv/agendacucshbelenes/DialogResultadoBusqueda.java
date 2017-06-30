package checkhelzio.ccv.agendacucshbelenes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogResultadoBusqueda extends Activity {

    @BindView(R.id.fondo)
    RelativeLayout fondo;
    @BindView(R.id.recycle)
    RecyclerView rvEventos;
    @BindView(R.id.tv_mensaje_no_evento)
    TextView tv_mensaje_no_eventos;
    @BindView(R.id.tv_mensaje_con_evento)
    TextView tv_mensaje_con_eventos;
    @BindView(R.id.tv_num_dia)
    TextView tv_num_dia;
    private List<Eventos> listaEventos;
    private  EventosAdaptadorBusqueda adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lista_eventos_busqueda);
        postponeEnterTransition();
        ButterKnife.bind(this);

        //rvEventos.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvEventos.setLayoutManager(mLayoutManager);

        inicializarDatos();

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogResultadoBusqueda.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

        startPostponedEnterTransition();
    }

    private void inicializarDatos() {
        try {
            listaEventos = getIntent().getParcelableArrayListExtra("LISTA_EVENTOS");

            if (listaEventos.size() > 0){
                tv_mensaje_no_eventos.setVisibility(View.GONE);
                tv_mensaje_con_eventos.setVisibility(View.GONE);
            }else {
                tv_mensaje_con_eventos.setVisibility(View.GONE);
                tv_mensaje_no_eventos.setText("No hay eventos que coincidan con la busqueda realizada.");
                tv_mensaje_no_eventos.setVisibility(View.VISIBLE);
            }
            iniciarAdaptador();

        }catch (Exception ignored){

        }

    }

    private void iniciarAdaptador() {
        adaptador = new EventosAdaptadorBusqueda(listaEventos, DialogResultadoBusqueda.this);
        rvEventos.setAdapter(adaptador);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        dismiss(null);
    }

    public void dismiss(View view) {
        finishAfterTransition();
    }

}
