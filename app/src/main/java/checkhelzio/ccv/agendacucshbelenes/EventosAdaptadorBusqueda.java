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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventosAdaptadorBusqueda extends RecyclerView.Adapter<EventosAdaptadorBusqueda.EventosViewHolder> {

    private List<Eventos> eventos;

    private final String auditorio1 = "Edificio A";
    private final String auditorio2 = "Edificio B";
    private final String auditorio3 = "Edificio C";
    private final String auditorio4 = "Edificio D";
    private final String auditorio5 = "Edificio F";
    private final int ELIMINAR_EVENTO = 4;
    private Context mContext;

    public EventosAdaptadorBusqueda(List<Eventos> eventos, Context context) {
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
    public void onBindViewHolder(final EventosViewHolder eventosViewHolder , int position) {
        final Eventos evento = eventos.get(position);
        eventosViewHolder.titulo_evento.setText(evento.getTitulo());
        eventosViewHolder.nombre_org.setText(evento.getNombreResponsable());
        eventosViewHolder.auditorio.setText(getNombreAula(evento.getAula()));
        eventosViewHolder.id.setText(evento.getDependencia());
        eventosViewHolder.fecha.setText(fecha(evento.getFecha()));
        eventosViewHolder.horario.setText(horasATetxto(Integer.parseInt(evento.getHoraInicial().replaceAll("[^0-9]+",""))) + " - " + horasATetxto(Integer.parseInt(evento.getHoraFinal().replaceAll("[^0-9]+",""))));
        eventosViewHolder.contenedor.setCardBackgroundColor(evento.getFondo());

        eventosViewHolder.contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DialogInfoEventosHelzio.class);
                intent.putExtra("EVENTO", evento);
                intent.putExtra("POSITION", eventosViewHolder.getAdapterPosition());
                final Rect startBounds = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                ChangeBoundBackground2.addExtras(intent, getViewBitmap(view), startBounds);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, view, "fondo");
                ((Activity) mContext).startActivityForResult(intent, ELIMINAR_EVENTO, options.toBundle());
            }
        });

    }

    private String getNombreAula(String st_aulas){
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
            case "FBD 1":
                st_aulas = "CAG";
                break;
            case "FBD 2":
                st_aulas = "Auditorio";
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
        if (hora == 12){
            am_pm = " PM";
        }else if (hora > 12) {
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

    private String nombreAuditorio(String numero) {
        String st = "";
        switch (numero) {
            case "1":
                st = auditorio1;
                break;
            case "2":
                st = auditorio2;
                break;
            case "3":
                st = auditorio3;
                break;
            case "4":
                st = auditorio4;
                break;
            case "5":
                st = auditorio5;
                break;
        }
        return st;
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class EventosViewHolder extends RecyclerView.ViewHolder{
        private TextView titulo_evento, nombre_org, auditorio, horario, fecha, id;
        private CardView contenedor;

        public EventosViewHolder(View itemView) {
            super(itemView);
            titulo_evento = (TextView) itemView.findViewById(R.id.tv_titulo);
            nombre_org = (TextView) itemView.findViewById(R.id.tv_organizador);
            auditorio = (TextView) itemView.findViewById(R.id.tv_auditorio);
            horario = (TextView) itemView.findViewById(R.id.tv_horario);
            fecha = (TextView) itemView.findViewById(R.id.tv_fecha);
            id = (TextView) itemView.findViewById(R.id.tv_id);
            contenedor = (CardView) itemView.findViewById(R.id.boton_eventos);
        }
    }

    public void removeItemAtPosition(int position) {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, eventos.size());

    }

    public void addItem(Eventos evento) {
        eventos.add(evento);
        notifyItemInserted(eventos.size() - 1);
    }

    public void ordenarItems() {
        ordenar();
        notifyDataSetChanged();
    }

    private void ordenar() {
        Collections.sort(eventos, new Comparator<Eventos>() {
            @Override
            public int compare(Eventos e1, Eventos e2) {
                Integer i1 = Integer.parseInt(e1.getFecha().replaceAll("[^0-9]+", ""));
                Integer i2 = Integer.parseInt(e2.getFecha().replaceAll("[^0-9]+", ""));
                if (i1.equals(i2)) {
                    Integer i3 = Integer.parseInt(e1.getHoraInicial());
                    Integer i4 = Integer.parseInt(e2.getHoraInicial());
                    if (i3.equals(i4)) {
                        Integer i5 = Integer.parseInt(e1.getHoraFinal());
                        Integer i6 = Integer.parseInt(e2.getHoraFinal());
                        return i5.compareTo(i6);
                    } else {
                        return i3.compareTo(i4);
                    }
                } else {
                    return i1.compareTo(i2);
                }
            }
        });
    }

    private String fecha(String fecha_inicial) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(2016, 0, 1);
        fecha.add(Calendar.DAY_OF_YEAR, Integer.valueOf(fecha_inicial.replaceAll("[^0-9]+","")) - 1);
        SimpleDateFormat format = new SimpleDateFormat("cccc',' d 'de' MMMM 'del' yyyy");
        fecha_inicial = (format.format(fecha.getTime()));
        return fecha_inicial;
    }

}
