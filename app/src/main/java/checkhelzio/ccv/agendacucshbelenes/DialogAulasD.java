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
public class DialogAulasD extends Activity {

    @BindView(R.id.fbd1)
    CheckBox fbd1;
    @BindView(R.id.fbd2)
    CheckBox fbd2;
    @BindView(R.id.fbd3)
    CheckBox fbd3;
    @BindView(R.id.fbd4)
    CheckBox fbd4;
    @BindView(R.id.fbd5)
    CheckBox fbd5;
    @BindView(R.id.fbd6)
    CheckBox fbd6;
    @BindView(R.id.fbd7)
    CheckBox fbd7;
    @BindView(R.id.fbd8)
    CheckBox fbd8;
    @BindView(R.id.fbd9)
    CheckBox fbd9;
    @BindView(R.id.fbd10)
    CheckBox fbd10;
    @BindView(R.id.fbd11)
    CheckBox fbd11;
    @BindView(R.id.fbd12)
    CheckBox fbd12;
    @BindView(R.id.fbd13)
    CheckBox fbd13;
    @BindView(R.id.fbd14)
    CheckBox fbd14;
    @BindView(R.id.fbd15)
    CheckBox fbd15;
    @BindView(R.id.fbd16)
    CheckBox fbd16;
    @BindView(R.id.fbd17)
    CheckBox fbd17;
    @BindView(R.id.fbd18)
    CheckBox fbd18;
    @BindView(R.id.fbd19)
    CheckBox fbd19;
    @BindView(R.id.fbd20)
    CheckBox fbd20;
    @BindView(R.id.fbd21)
    CheckBox fbd21;
    @BindView(R.id.fbd22)
    CheckBox fbd22;
    @BindView(R.id.fbd23)
    CheckBox fbd23;
    @BindView(R.id.fbd24)
    CheckBox fbd24;
    @BindView(R.id.fbd25)
    CheckBox fbd25;
    @BindView(R.id.fbd26)
    CheckBox fbd26;

    String st_aulas = "";
    final ArrayList<CheckBox> lista_checkbox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.dialog_edificio_d);
        ButterKnife.bind(this);

        st_aulas = getIntent().getStringExtra("AULAS");

        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(AnimUtils.getLinearOutSlowInInterpolator(DialogAulasD.this));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);

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

    @OnClick(R.id.tv_guardar_evento)
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
        for (CheckBox c : lista_checkbox){
            c.setChecked(false);
        }
        ((CheckBox) ((ViewGroup) view).getChildAt(0)).setChecked(true);
    }
}
