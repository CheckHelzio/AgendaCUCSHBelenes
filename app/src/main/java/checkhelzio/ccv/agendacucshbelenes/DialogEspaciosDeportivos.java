package checkhelzio.ccv.agendacucshbelenes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
public class DialogEspaciosDeportivos extends Activity {

    final ArrayList<CheckBox> lista_checkbox = new ArrayList<>();
    String st_aulas = "";
    private CheckBox fbad1;
    private CheckBox fbad2;
    private CheckBox fbad3;
    private CheckBox fbad4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_depo);

        findViewById(R.id.tv_guardar_evento).setOnClickListener(this::aceptar);

        fbad1 = findViewById(R.id.fbad1);
        fbad2 = findViewById(R.id.fbad2);
        fbad3 = findViewById(R.id.fbad3);
        fbad4 = findViewById(R.id.fbad4);

        st_aulas = getIntent().getStringExtra("AULAS");

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogEspaciosDeportivos.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

        startPostponedEnterTransition();
        lista_checkbox.add(fbad1);
        lista_checkbox.add(fbad2);
        lista_checkbox.add(fbad3);
        lista_checkbox.add(fbad4);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        cerrar(null);
    }

    public void aceptar(View view) {
        st_aulas = "";
        if (fbad1.isChecked()) {
            st_aulas += "FBAD 1";
        } else if (fbad2.isChecked()) {
            st_aulas += "FBAD 2";
        } else if (fbad3.isChecked()) {
            st_aulas += "FBAD 3";
        } else if (fbad4.isChecked()) {
            st_aulas += "FBAD 4";
        }
        Intent i = getIntent();
        i.putExtra("AULAS", st_aulas);
        setResult(RESULT_OK, i);
        cerrar(null);
    }

    public void cerrar(View view) {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(DialogEspaciosDeportivos.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(slide);
        finishAfterTransition();
    }

    public void togle(View view) {
        for (CheckBox c : lista_checkbox){
            c.setChecked(false);
        }
        ((CheckBox) ((ViewGroup) view).getChildAt(0)).setChecked(true);
    }
}
