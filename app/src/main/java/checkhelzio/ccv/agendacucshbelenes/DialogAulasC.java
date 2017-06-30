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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogAulasC extends Activity {

    @BindView(R.id.fbc1)
    CheckBox fbc1;
    @BindView(R.id.fbc2)
    CheckBox fbc2;
    @BindView(R.id.fbc3)
    CheckBox fbc3;
    @BindView(R.id.fbc4)
    CheckBox fbc4;
    @BindView(R.id.fbc5)
    CheckBox fbc5;
    @BindView(R.id.fbc6)
    CheckBox fbc6;
    @BindView(R.id.fbc7)
    CheckBox fbc7;
    @BindView(R.id.fbc8)
    CheckBox fbc8;
    @BindView(R.id.fbc9)
    CheckBox fbc9;
    @BindView(R.id.fbc10)
    CheckBox fbc10;
    @BindView(R.id.fbc11)
    CheckBox fbc11;
    @BindView(R.id.fbc12)
    CheckBox fbc12;
    @BindView(R.id.fbc13)
    CheckBox fbc13;
    @BindView(R.id.fbc14)
    CheckBox fbc14;
    @BindView(R.id.fbc15)
    CheckBox fbc15;
    @BindView(R.id.fbc16)
    CheckBox fbc16;
    @BindView(R.id.fbc17)
    CheckBox fbc17;
    @BindView(R.id.fbc18)
    CheckBox fbc18;
    @BindView(R.id.fbc19)
    CheckBox fbc19;
    @BindView(R.id.fbc20)
    CheckBox fbc20;
    @BindView(R.id.fbc21n)
    CheckBox fbc21n;
    @BindView(R.id.fbc21s)
    CheckBox fbc21s;
    @BindView(R.id.fbc21)
    CheckBox fbc21;
    @BindView(R.id.fbc22)
    CheckBox fbc22;
    @BindView(R.id.fbc22n)
    CheckBox fbc22n;
    @BindView(R.id.fbc22s)
    CheckBox fbc22s;
    @BindView(R.id.fbc23)
    CheckBox fbc23;

    String st_aulas = "";
    final ArrayList<CheckBox> lista_checkbox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_c);
        ButterKnife.bind(this);

        st_aulas = getIntent().getStringExtra("AULAS");

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogAulasC.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

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

    @OnClick(R.id.tv_guardar_evento)
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
