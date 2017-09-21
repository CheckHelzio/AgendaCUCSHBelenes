package checkhelzio.ccv.agendacucshbelenes;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EventosAdaptadorBusqueda extends RecyclerView.Adapter<EventosAdaptadorBusqueda.EventosViewHolder> {

    private final int ELIMINAR_EVENTO = 4;
    private List<Eventos> eventos;
    private Context mContext;

    EventosAdaptadorBusqueda(List<Eventos> eventos, Context context) {
        this.eventos = eventos;
        mContext = context;
    }

    @Override
    public EventosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eventos_full, parent, false);
        return new EventosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final EventosViewHolder eventosViewHolder, int position) {
        final Eventos evento = eventos.get(position);
        eventosViewHolder.titulo_evento.setText(evento.getTitulo());
        eventosViewHolder.nombre_org.setText(evento.getNombreResponsable());
        eventosViewHolder.auditorio.setText(getNombreAula(evento.getAula()));
        eventosViewHolder.id.setText(evento.getDependencia());
        eventosViewHolder.fecha.setText(fecha(evento.getFecha()));
        String horas = horasATetxto(Integer.parseInt(evento.getHoraInicial().replaceAll("[^0-9]+", ""))) + " - " + horasATetxto(Integer.parseInt(evento.getHoraFinal().replaceAll("[^0-9]+", "")));
        eventosViewHolder.horario.setText(horas);
        eventosViewHolder.contenedor.setCardBackgroundColor(evento.getFondo());

        eventosViewHolder.contenedor.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DialogInfoEventosHelzio.class);
            intent.putExtra("EVENTO", evento);
            intent.putExtra("POSITION", eventosViewHolder.getAdapterPosition());
            final Rect startBounds = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            ChangeBoundBackground2.addExtras(intent, getViewBitmap(view), startBounds);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, view, "fondo");
            ((Activity) mContext).startActivityForResult(intent, ELIMINAR_EVENTO, options.toBundle());
        });

    }

    private String getNombreAula(String st_aulas) {
        switch (st_aulas) {
            case "FBA 17":
                st_aulas = "Aula Dr. Fernando Pozos Ponce";
                break;
            case "FBC 21":
                st_aulas = "Sala de juntas 1 completa";
                break;
            case "FBC 22":
                st_aulas = "Sala de juntas 2 completa";
                break;
            case "FBC 23":
                st_aulas = "Salón de usos múltiples";
                break;
            case "FBF 2":
                st_aulas = "Área de uso libre";
                break;
            case "FBF2 1":
                st_aulas = "Laboratorio de SIG";
                break;
            case "FBF2 2":
                st_aulas = "Laboratorio CTA";
                break;
            case "FBC 21 N":
                st_aulas = "Sala de juntas 1 norte";
                break;
            case "FBC 22 N":
                st_aulas = "Sala de juntas 2 norte";
                break;
            case "FBC 21 S":
                st_aulas = "Sala de juntas 1 sur";
                break;
            case "FBC 22 S":
                st_aulas = "Sala de juntas 2 sur";
                break;
            case "FBD 22":
                st_aulas = "Auditorio";
                break;
            case "FBD 23":
                st_aulas = "CAG";
                break;
            case "FBD 24":
                st_aulas = "Computo 1er nivel";
                break;
            case "FBD 25":
                st_aulas = "Computo 2do nivel";
                break;
            case "FBD 26":
                st_aulas = "Computo 3er nivel";
                break;
            case "FBAD 1":
                st_aulas = "Cancha de fútbol";
                break;
            case "FBAD 2":
                st_aulas = "Cancha de básquetbol";
                break;
            case "FBAD 3":
                st_aulas = "Cancha de usos múltiples";
                break;
            case "FBAD 4":
                st_aulas = "Pista de Jogging";
                break;
            default:
                st_aulas = "Aula " + st_aulas;
                break;
        }
        return st_aulas;
    }

    private String horasATetxto(int numero) {
        String am_pm, st_min, st_hora;

        int hora = (numero / 2) + 7;
        if (hora == 12) {
            am_pm = " PM";
        } else if (hora > 12) {
            hora = hora - 12;
            am_pm = " PM";
        } else {
            am_pm = " AM";
        }

        if (hora < 10) {
            st_hora = "0" + hora;
        } else {
            st_hora = "" + hora;
        }

        if (numero % 2 == 0) {
            st_min = "00";
        } else {
            st_min = "30";
        }

        return st_hora + ":" + st_min + am_pm;

    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    void removeItemAtPosition(int position) {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, eventos.size());

    }

    private String fecha(String fecha_inicial) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(2016, 0, 1);
        fecha.add(Calendar.DAY_OF_YEAR, Integer.valueOf(fecha_inicial.replaceAll("[^0-9]+", "")) - 1);
        SimpleDateFormat format = new SimpleDateFormat("cccc',' d 'de' MMMM 'del' yyyy", Locale.getDefault());
        fecha_inicial = (format.format(fecha.getTime()));
        return fecha_inicial;
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo_evento, nombre_org, auditorio, horario, fecha, id;
        private CardView contenedor;

        EventosViewHolder(View itemView) {
            super(itemView);
            titulo_evento = itemView.findViewById(R.id.tv_titulo);
            nombre_org = itemView.findViewById(R.id.tv_organizador);
            auditorio = itemView.findViewById(R.id.tv_auditorio);
            horario = itemView.findViewById(R.id.tv_horario);
            fecha = itemView.findViewById(R.id.tv_fecha);
            id = itemView.findViewById(R.id.tv_id);
            contenedor = itemView.findViewById(R.id.boton_eventos);
        }
    }

}
