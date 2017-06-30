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

import static android.os.Build.VERSION_CODES.M;

public class DialogAulasA extends Activity {

    @BindView(R.id.fba1)
    CheckBox fba1;
    @BindView(R.id.fba2)
    CheckBox fba2;
    @BindView(R.id.fba3)
    CheckBox fba3;
    @BindView(R.id.fba4)
    CheckBox fba4;
    @BindView(R.id.fba5)
    CheckBox fba5;
    @BindView(R.id.fba6)
    CheckBox fba6;
    @BindView(R.id.fba7)
    CheckBox fba7;
    @BindView(R.id.fba8)
    CheckBox fba8;
    @BindView(R.id.fba9)
    CheckBox fba9;
    @BindView(R.id.fba10)
    CheckBox fba10;
    @BindView(R.id.fba11)
    CheckBox fba11;
    @BindView(R.id.fba12)
    CheckBox fba12;
    @BindView(R.id.fba13)
    CheckBox fba13;
    @BindView(R.id.fba14)
    CheckBox fba14;
    @BindView(R.id.fba15)
    CheckBox fba15;
    @BindView(R.id.fba16)
    CheckBox fba16;
    @BindView(R.id.fba17)
    CheckBox fba17;

    String st_aulas = "";
    final ArrayList<CheckBox> lista_checkbox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_a);
        ButterKnife.bind(this);

        st_aulas = getIntent().getStringExtra("AULAS");

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogAulasA.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

        startPostponedEnterTransition();
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

    @OnClick(R.id.tv_guardar_evento)
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
