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

public class DialogAulasD extends Activity {

    final ArrayList<CheckBox> lista_checkbox = new ArrayList<>();
    String st_aulas = "";
    private CheckBox fbd1;
    private CheckBox fbd2;
    private CheckBox fbd3;
    private CheckBox fbd4;
    private CheckBox fbd5;
    private CheckBox fbd6;
    private CheckBox fbd7;
    private CheckBox fbd8;
    private CheckBox fbd9;
    private CheckBox fbd10;
    private CheckBox fbd11;
    private CheckBox fbd12;
    private CheckBox fbd13;
    private CheckBox fbd14;
    private CheckBox fbd15;
    private CheckBox fbd16;
    private CheckBox fbd17;
    private CheckBox fbd18;
    private CheckBox fbd19;
    private CheckBox fbd20;
    private CheckBox fbd21;
    private CheckBox fbd22;
    private CheckBox fbd23;
    private CheckBox fbd24;
    private CheckBox fbd25;
    private CheckBox fbd26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_d);

        fbd1 = findViewById(R.id.fbd1);
        fbd2 = findViewById(R.id.fbd2);
        fbd3 = findViewById(R.id.fbd3);
        fbd4 = findViewById(R.id.fbd4);
        fbd5 = findViewById(R.id.fbd5);
        fbd6 = findViewById(R.id.fbd6);
        fbd7 = findViewById(R.id.fbd7);
        fbd8 = findViewById(R.id.fbd8);
        fbd9 = findViewById(R.id.fbd9);
        fbd10 = findViewById(R.id.fbd10);
        fbd11 = findViewById(R.id.fbd11);
        fbd12 = findViewById(R.id.fbd12);
        fbd13 = findViewById(R.id.fbd13);
        fbd14 = findViewById(R.id.fbd14);
        fbd15 = findViewById(R.id.fbd15);
        fbd16 = findViewById(R.id.fbd16);
        fbd17 = findViewById(R.id.fbd17);
        fbd18 = findViewById(R.id.fbd18);
        fbd19 = findViewById(R.id.fbd19);
        fbd20 = findViewById(R.id.fbd20);
        fbd21 = findViewById(R.id.fbd21);
        fbd22 = findViewById(R.id.fbd22);
        fbd23 = findViewById(R.id.fbd23);
        fbd24 = findViewById(R.id.fbd24);
        fbd25 = findViewById(R.id.fbd25);
        fbd26 = findViewById(R.id.fbd26);

        st_aulas = getIntent().getStringExtra("AULAS");

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogAulasD.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

        findViewById(R.id.tv_guardar_evento).setOnClickListener(this::aceptar);

        startPostponedEnterTransition();
        lista_checkbox.add(fbd1);
        lista_checkbox.add(fbd2);
        lista_checkbox.add(fbd3);
        lista_checkbox.add(fbd4);
        lista_checkbox.add(fbd5);
        lista_checkbox.add(fbd6);
        lista_checkbox.add(fbd7);
        lista_checkbox.add(fbd8);
        lista_checkbox.add(fbd9);
        lista_checkbox.add(fbd10);
        lista_checkbox.add(fbd11);
        lista_checkbox.add(fbd12);
        lista_checkbox.add(fbd13);
        lista_checkbox.add(fbd14);
        lista_checkbox.add(fbd15);
        lista_checkbox.add(fbd16);
        lista_checkbox.add(fbd17);
        lista_checkbox.add(fbd18);
        lista_checkbox.add(fbd19);
        lista_checkbox.add(fbd20);
        lista_checkbox.add(fbd21);
        lista_checkbox.add(fbd22);
        lista_checkbox.add(fbd23);
        lista_checkbox.add(fbd24);
        lista_checkbox.add(fbd25);
        lista_checkbox.add(fbd26);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        cerrar(null);
    }

    public void aceptar(View view) {
        st_aulas = "";
        if (fbd1.isChecked()) {
            st_aulas += "FBD 1";
        } else if (fbd2.isChecked()) {
            st_aulas += "FBD 2";
        } else if (fbd3.isChecked()) {
            st_aulas += "FBD 3";
        } else if (fbd4.isChecked()) {
            st_aulas += "FBD 4";
        } else if (fbd5.isChecked()) {
            st_aulas += "FBD 5";
        } else if (fbd6.isChecked()) {
            st_aulas += "FBD 6";
        } else if (fbd7.isChecked()) {
            st_aulas += "FBD 7";
        } else if (fbd8.isChecked()) {
            st_aulas += "FBD 8";
        } else if (fbd9.isChecked()) {
            st_aulas += "FBD 9";
        } else if (fbd10.isChecked()) {
            st_aulas += "FBD 10";
        } else if (fbd11.isChecked()) {
            st_aulas += "FBD 11";
        } else if (fbd12.isChecked()) {
            st_aulas += "FBD 12";
        } else if (fbd13.isChecked()) {
            st_aulas += "FBD 13";
        } else if (fbd14.isChecked()) {
            st_aulas += "FBD 14";
        } else if (fbd15.isChecked()) {
            st_aulas += "FBD 15";
        } else if (fbd16.isChecked()) {
            st_aulas += "FBD 16";
        } else if (fbd17.isChecked()) {
            st_aulas += "FBD 17";
        } else if (fbd18.isChecked()) {
            st_aulas += "FBD 18";
        } else if (fbd19.isChecked()) {
            st_aulas += "FBD 19";
        } else if (fbd20.isChecked()) {
            st_aulas += "FBD 20";
        } else if (fbd21.isChecked()) {
            st_aulas += "FBD 21";
        } else if (fbd22.isChecked()) {
            st_aulas += "FBD 22";
        } else if (fbd23.isChecked()) {
            st_aulas += "FBD 23";
        } else if (fbd24.isChecked()) {
            st_aulas += "FBD 24";
        } else if (fbd25.isChecked()) {
            st_aulas += "FBD 25";
        } else if (fbd26.isChecked()) {
            st_aulas += "FBD 26";
        }
        Intent i = getIntent();
        i.putExtra("AULAS", st_aulas);
        setResult(RESULT_OK, i);
        cerrar(null);
    }

    public void cerrar(View view) {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(DialogAulasD.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(slide);
        finishAfterTransition();
    }

    public void togle(View view) {
        for (CheckBox c : lista_checkbox) {
            c.setChecked(false);
        }
        ((CheckBox) ((ViewGroup) view).getChildAt(0)).setChecked(true);
    }
}
