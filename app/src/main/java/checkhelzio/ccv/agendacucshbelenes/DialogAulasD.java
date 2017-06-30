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
            st_aulas += "FBD 10";
        } else if (fbd4.isChecked()) {
            st_aulas += "FBD 11";
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
