package checkhelzio.ccv.agendacucshbelenes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;


public class DialogAulasF1 extends Activity {

    String st_aulas = "";
    private CheckBox fbf1;
    private CheckBox fbf2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_f1);

        findViewById(R.id.tv_guardar_evento).setOnClickListener(this::aceptar);

        fbf1 = findViewById(R.id.fbf1);
        fbf2 = findViewById(R.id.fbf2);

        st_aulas = getIntent().getStringExtra("AULAS");

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogAulasF1.this));
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

    public void aceptar(View view) {
        st_aulas = "";
        if (fbf1.isChecked()) {
            st_aulas += "FBF 1" + "::";
        }
        if (fbf2.isChecked()) {
            st_aulas += "FBF 2" + "::";
        }
        Intent i = getIntent();
        i.putExtra("AULAS", st_aulas);
        setResult(RESULT_OK, i);
        cerrar(null);
    }

    public void cerrar(View view) {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(DialogAulasF1.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(slide);
        finishAfterTransition();
    }

    public void togle(View view) {
        ((CheckBox) ((ViewGroup) view).getChildAt(0)).toggle();
    }
}
