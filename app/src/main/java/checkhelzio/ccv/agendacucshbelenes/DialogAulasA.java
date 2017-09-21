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


public class DialogAulasA extends Activity {



    final ArrayList<CheckBox> lista_checkbox = new ArrayList<>();
    String st_aulas = "";
    private CheckBox fba1;
    private CheckBox fba2;
    private CheckBox fba3;
    private CheckBox fba4;
    private CheckBox fba5;
    private CheckBox fba6;
    private CheckBox fba7;
    private CheckBox fba8;
    private CheckBox fba9;
    private CheckBox fba10;
    private CheckBox fba11;
    private CheckBox fba12;
    private CheckBox fba13;
    private CheckBox fba14;
    private CheckBox fba15;
    private CheckBox fba16;
    private CheckBox fba17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_a);

        st_aulas = getIntent().getStringExtra("AULAS");

        findViewById(R.id.tv_guardar_evento).setOnClickListener(this::aceptar);

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogAulasA.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

        startPostponedEnterTransition();

        fba1 = findViewById(R.id.fba1);
        fba2 = findViewById(R.id.fba2);
        fba3 = findViewById(R.id.fba3);
        fba4 = findViewById(R.id.fba4);
        fba5 = findViewById(R.id.fba5);
        fba6 = findViewById(R.id.fba6);
        fba7 = findViewById(R.id.fba7);
        fba8 = findViewById(R.id.fba8);
        fba9 = findViewById(R.id.fba9);
        fba10 = findViewById(R.id.fba10);
        fba11 = findViewById(R.id.fba11);
        fba12 = findViewById(R.id.fba12);
        fba13 = findViewById(R.id.fba13);
        fba14 = findViewById(R.id.fba14);
        fba15 = findViewById(R.id.fba15);
        fba16 = findViewById(R.id.fba16);
        fba17 = findViewById(R.id.fba17);

        lista_checkbox.add(fba1);
        lista_checkbox.add(fba2);
        lista_checkbox.add(fba3);
        lista_checkbox.add(fba4);
        lista_checkbox.add(fba5);
        lista_checkbox.add(fba6);
        lista_checkbox.add(fba7);
        lista_checkbox.add(fba8);
        lista_checkbox.add(fba9);
        lista_checkbox.add(fba10);
        lista_checkbox.add(fba11);
        lista_checkbox.add(fba12);
        lista_checkbox.add(fba13);
        lista_checkbox.add(fba14);
        lista_checkbox.add(fba15);
        lista_checkbox.add(fba16);
        lista_checkbox.add(fba17);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        cerrar(null);
    }

    public void aceptar(View view) {
        st_aulas = "";
        if (fba1.isChecked()) {
            st_aulas += "FBA 1";
        } else if (fba2.isChecked()) {
            st_aulas += "FBA 2";
        } else if (fba3.isChecked()) {
            st_aulas += "FBA 3";
        } else if (fba4.isChecked()) {
            st_aulas += "FBA 4";
        } else if (fba5.isChecked()) {
            st_aulas += "FBA 5";
        } else if (fba6.isChecked()) {
            st_aulas += "FBA 6";
        } else if (fba7.isChecked()) {
            st_aulas += "FBA 7";
        } else if (fba8.isChecked()) {
            st_aulas += "FBA 8";
        } else if (fba9.isChecked()) {
            st_aulas += "FBA 9";
        } else if (fba10.isChecked()) {
            st_aulas += "FBA 10";
        } else if (fba11.isChecked()) {
            st_aulas += "FBA 11";
        } else if (fba12.isChecked()) {
            st_aulas += "FBA 12";
        } else if (fba13.isChecked()) {
            st_aulas += "FBA 13";
        } else if (fba14.isChecked()) {
            st_aulas += "FBA 14";
        } else if (fba15.isChecked()) {
            st_aulas += "FBA 15";
        } else if (fba16.isChecked()) {
            st_aulas += "FBA 16";
        } else if (fba17.isChecked()) {
            st_aulas += "FBA 17";
        }

        Intent i = getIntent();
        i.putExtra("AULAS", st_aulas);
        setResult(RESULT_OK, i);

        cerrar(null);
    }

    public void cerrar(View view) {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(DialogAulasA.this));
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
