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


public class DialogAulasC extends Activity {

    final ArrayList<CheckBox> lista_checkbox = new ArrayList<>();
    String st_aulas = "";
    private CheckBox fbc1;
    private CheckBox fbc2;
    private CheckBox fbc3;
    private CheckBox fbc4;
    private CheckBox fbc5;
    private CheckBox fbc6;
    private CheckBox fbc7;
    private CheckBox fbc8;
    private CheckBox fbc9;
    private CheckBox fbc10;
    private CheckBox fbc11;
    private CheckBox fbc12;
    private CheckBox fbc13;
    private CheckBox fbc14;
    private CheckBox fbc15;
    private CheckBox fbc16;
    private CheckBox fbc17;
    private CheckBox fbc18;
    private CheckBox fbc19;
    private CheckBox fbc20;
    private CheckBox fbc21n;
    private CheckBox fbc21s;
    private CheckBox fbc21;
    private CheckBox fbc22;
    private CheckBox fbc22n;
    private CheckBox fbc22s;
    private CheckBox fbc23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_c);

        fbc1 = findViewById(R.id.fbc1);
        fbc2 = findViewById(R.id.fbc2);
        fbc3 = findViewById(R.id.fbc3);
        fbc4 = findViewById(R.id.fbc4);
        fbc5 = findViewById(R.id.fbc5);
        fbc6 = findViewById(R.id.fbc6);
        fbc7 = findViewById(R.id.fbc7);
        fbc8 = findViewById(R.id.fbc8);
        fbc9 = findViewById(R.id.fbc9);
        fbc10 = findViewById(R.id.fbc10);
        fbc11 = findViewById(R.id.fbc11);
        fbc12 = findViewById(R.id.fbc12);
        fbc13 = findViewById(R.id.fbc13);
        fbc14 = findViewById(R.id.fbc14);
        fbc15 = findViewById(R.id.fbc15);
        fbc16 = findViewById(R.id.fbc16);
        fbc17 = findViewById(R.id.fbc17);
        fbc18 = findViewById(R.id.fbc18);
        fbc19 = findViewById(R.id.fbc19);
        fbc20 = findViewById(R.id.fbc20);
        fbc21n = findViewById(R.id.fbc21n);
        fbc21s = findViewById(R.id.fbc21s);
        fbc21 = findViewById(R.id.fbc21);
        fbc22 = findViewById(R.id.fbc22);
        fbc22n = findViewById(R.id.fbc22n);
        fbc22s = findViewById(R.id.fbc22s);
        fbc23 = findViewById(R.id.fbc23);

        st_aulas = getIntent().getStringExtra("AULAS");

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogAulasC.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

        findViewById(R.id.tv_guardar_evento).setOnClickListener(this::aceptar);

        startPostponedEnterTransition();
        lista_checkbox.add(fbc1);
        lista_checkbox.add(fbc2);
        lista_checkbox.add(fbc3);
        lista_checkbox.add(fbc4);
        lista_checkbox.add(fbc5);
        lista_checkbox.add(fbc6);
        lista_checkbox.add(fbc7);
        lista_checkbox.add(fbc8);
        lista_checkbox.add(fbc9);
        lista_checkbox.add(fbc10);
        lista_checkbox.add(fbc11);
        lista_checkbox.add(fbc12);
        lista_checkbox.add(fbc13);
        lista_checkbox.add(fbc14);
        lista_checkbox.add(fbc15);
        lista_checkbox.add(fbc16);
        lista_checkbox.add(fbc17);
        lista_checkbox.add(fbc18);
        lista_checkbox.add(fbc19);
        lista_checkbox.add(fbc20);
        lista_checkbox.add(fbc21);
        lista_checkbox.add(fbc21n);
        lista_checkbox.add(fbc21s);
        lista_checkbox.add(fbc22);
        lista_checkbox.add(fbc22n);
        lista_checkbox.add(fbc22s);
        lista_checkbox.add(fbc23);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        cerrar(null);
    }

    public void aceptar(View view) {
        st_aulas = "";
        if (fbc1.isChecked()) {
            st_aulas += "FBC 1";
        } else if (fbc2.isChecked()) {
            st_aulas += "FBC 2";
        } else if (fbc3.isChecked()) {
            st_aulas += "FBC 3";
        } else if (fbc4.isChecked()) {
            st_aulas += "FBC 4";
        } else if (fbc5.isChecked()) {
            st_aulas += "FBC 5";
        } else if (fbc6.isChecked()) {
            st_aulas += "FBC 6";
        } else if (fbc7.isChecked()) {
            st_aulas += "FBC 7";
        } else if (fbc8.isChecked()) {
            st_aulas += "FBC 8";
        } else if (fbc9.isChecked()) {
            st_aulas += "FBC 9";
        } else if (fbc10.isChecked()) {
            st_aulas += "FBC 10";
        } else if (fbc11.isChecked()) {
            st_aulas += "FBC 11";
        } else if (fbc12.isChecked()) {
            st_aulas += "FBC 12";
        } else if (fbc13.isChecked()) {
            st_aulas += "FBC 13";
        } else if (fbc14.isChecked()) {
            st_aulas += "FBC 14";
        } else if (fbc15.isChecked()) {
            st_aulas += "FBC 15";
        } else if (fbc16.isChecked()) {
            st_aulas += "FBC 16";
        } else if (fbc17.isChecked()) {
            st_aulas += "FBC 17";
        } else if (fbc18.isChecked()) {
            st_aulas += "FBC 18";
        } else if (fbc19.isChecked()) {
            st_aulas += "FBC 19";
        } else if (fbc20.isChecked()) {
            st_aulas += "FBC 20";
        } else if (fbc21.isChecked()) {
            st_aulas += "FBC 21";
        } else if (fbc21n.isChecked()) {
            st_aulas += "FBC 21 N";
        } else if (fbc21s.isChecked()) {
            st_aulas += "FBC 21 S";
        } else if (fbc22.isChecked()) {
            st_aulas += "FBC 22";
        } else if (fbc22n.isChecked()) {
            st_aulas += "FBC 22 N";
        } else if (fbc22s.isChecked()) {
            st_aulas += "FBC 22 S";
        } else if (fbc23.isChecked()) {
            st_aulas += "FBC 23";
        }
        Intent i = getIntent();
        i.putExtra("AULAS", st_aulas);
        setResult(RESULT_OK, i);
        cerrar(null);
    }

    public void cerrar(View view) {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(DialogAulasC.this));
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
