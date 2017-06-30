package checkhelzio.ccv.agendacucshbelenes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogAreasDeportivas extends Activity {

    @BindView(R.id.fbad1)
    CheckBox fbad1;
    @BindView(R.id.fbad2)
    CheckBox fbad2;
    @BindView(R.id.fbad3)
    CheckBox fbad3;
    @BindView(R.id.fbad4)
    CheckBox fbad4;

    String st_aulas = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_depo);
        ButterKnife.bind(this);

        st_aulas = getIntent().getStringExtra("AULAS");

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogAreasDeportivas.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

        startPostponedEnterTransition();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        cerrar(null);
    }

    @OnClick(R.id.tv_guardar_evento)
    public void aceptar(View view) {
        st_aulas = "";
        if (fbad1.isChecked()) {
            st_aulas += "FBAD 1" + "::";
        }
        if (fbad2.isChecked()) {
            st_aulas += "FBAD 2" + "::";
        }
        if (fbad3.isChecked()) {
            st_aulas += "FBAD 3" + "::";
        }
        if (fbad4.isChecked()) {
            st_aulas += "FBAD 4" + "::";
        }
        Intent i = getIntent();
        i.putExtra("AULAS", st_aulas);
        setResult(RESULT_OK, i);
        cerrar(null);
    }

    public void cerrar(View view) {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(DialogAreasDeportivas.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(slide);
        finishAfterTransition();
    }

    public void togle(View view) {
        ((CheckBox) ((ViewGroup) view).getChildAt(0)).toggle();
    }
}
