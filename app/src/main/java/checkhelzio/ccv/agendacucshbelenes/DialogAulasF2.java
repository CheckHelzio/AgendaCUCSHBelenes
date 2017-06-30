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

import static checkhelzio.ccv.agendacucshbelenes.R.id.fbf1;
import static checkhelzio.ccv.agendacucshbelenes.R.id.fbf2;

public class DialogAulasF2 extends Activity {

    @BindView(R.id.fbf2_1)
    CheckBox fbf2_1;
    @BindView(R.id.fbf2_2)
    CheckBox fbf2_2;

    String st_aulas = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_f2);
        ButterKnife.bind(this);

        st_aulas = getIntent().getStringExtra("AULAS");

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogAulasF2.this));
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
        if (fbf2_1.isChecked()) {
            st_aulas += "FBF2 1" + "::";
        }
        if (fbf2_2.isChecked()) {
            st_aulas += "FBF2 2" + "::";
        }
        Intent i = getIntent();
        i.putExtra("AULAS", st_aulas);
        setResult(RESULT_OK, i);
        cerrar(null);
    }

    public void cerrar(View view) {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(DialogAulasF2.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(slide);
        finishAfterTransition();
    }

    public void togle(View view) {
        ((CheckBox) ((ViewGroup) view).getChildAt(0)).toggle();
    }
}
