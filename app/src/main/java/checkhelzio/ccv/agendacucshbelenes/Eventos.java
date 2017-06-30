package checkhelzio.ccv.agendacucshbelenes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by check on 23/09/2016.
 */

public class Eventos implements Parcelable {
    private String fecha;
    private String horaInicial;
    private String horaFinal;
    private String titulo;
    private String auditorio;
    private String tipoEvento;
    private String nombreSolicitante;
    private String numTelSolicitante;
    private String statusEvento;
    private String quienR;
    private String cuandoR;
    private String notas;
    String id;
    private String tag;
    private int fondo;
    private String clase;
    private String dependencia;
    private String nombreResponsable;
    private String celularResponsable;
    private String aula;
    private String notas2;

    protected String aTag(){
        String t = "";

        t += this.fecha + "::";
        t += this.horaInicial + "::";
        t += this.horaFinal + "::";
        t += this.titulo + "::";
        t += this.auditorio + "::";
        t += this.tipoEvento + "::";
        t += this.nombreSolicitante + "::";
        t += this.numTelSolicitante + "::";
        t += this.statusEvento + "::";
        t += this.quienR + "::";
        t += this.cuandoR + "::";
        t += this.notas + "::";
        t += this.id + "::";
        t += this.clase + "::";
        t += this.dependencia + "::";
        t += this.nombreResponsable + "::";
        t += this.celularResponsable + "::";
        t += this.aula + "::";
        t += this.notas2;

        return t;
    }

    public Eventos(String fecha, String horaInicial, String horaFinal, String titulo, String auditorio, String tipoEvento, String nombreSolicitante, String numTelSolicitante, String statusEvento, String quienR, String cuandoR, String notas, String id, String tag, int fondo, String clase, String dependencia, String nombreResponsable, String celularResponsable, String aula, String notas2) {
        this.fecha = fecha;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.titulo = titulo;
        this.auditorio = auditorio;
        this.tipoEvento = tipoEvento;
        this.nombreSolicitante = nombreSolicitante;
        this.numTelSolicitante = numTelSolicitante;
        this.statusEvento = statusEvento;
        this.quienR = quienR;
        this.cuandoR = cuandoR;
        this.notas = notas;
        this.id = id;
        this.tag = tag;
        this.fondo = fondo;
        this.clase = clase;
        this.dependencia = dependencia;
        this.nombreResponsable = nombreResponsable;
        this.celularResponsable = celularResponsable;
        this.aula = aula;
        this.notas2 = notas2;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAuditorio() {
        return auditorio;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public String getNumTelSolicitante() {
        return numTelSolicitante;
    }

    public String getStatusEvento() {
        return statusEvento;
    }

    public String getQuienR() {
        return quienR;
    }

    public String getCuandoR() {
        return cuandoR;
    }

    public String getNotas() {
        return notas;
    }

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public int getFondo() {
        return fondo;
    }

    public String getClase() {
        return clase;
    }

    public String getDependencia() {
        return dependencia;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public String getCelularResponsable() {
        return celularResponsable;
    }

    public String getAula() {
        return aula;
    }

    public String getNotas2() {
        return notas2;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fecha);
        dest.writeString(this.horaInicial);
        dest.writeString(this.horaFinal);
        dest.writeString(this.titulo);
        dest.writeString(this.auditorio);
        dest.writeString(this.tipoEvento);
        dest.writeString(this.nombreSolicitante);
        dest.writeString(this.numTelSolicitante);
        dest.writeString(this.statusEvento);
        dest.writeString(this.quienR);
        dest.writeString(this.cuandoR);
        dest.writeString(this.notas);
        dest.writeString(this.id);
        dest.writeString(this.tag);
        dest.writeInt(this.fondo);
        dest.writeString(this.clase);
        dest.writeString(this.dependencia);
        dest.writeString(this.nombreResponsable);
        dest.writeString(this.celularResponsable);
        dest.writeString(this.aula);
        dest.writeString(this.notas2);
    }

    protected Eventos(Parcel in) {
        this.fecha = in.readString();
        this.horaInicial = in.readString();
        this.horaFinal = in.readString();
        this.titulo = in.readString();
        this.auditorio = in.readString();
        this.tipoEvento = in.readString();
        this.nombreSolicitante = in.readString();
        this.numTelSolicitante = in.readString();
        this.statusEvento = in.readString();
        this.quienR = in.readString();
        this.cuandoR = in.readString();
        this.notas = in.readString();
        this.id = in.readString();
        this.tag = in.readString();
        this.fondo = in.readInt();
        this.clase = in.readString();
        this.dependencia = in.readString();
        this.nombreResponsable = in.readString();
        this.celularResponsable = in.readString();
        this.aula = in.readString();
        this.notas2 = in.readString();
    }

    public static final Creator<Eventos> CREATOR = new Creator<Eventos>() {
        @Override
        public Eventos createFromParcel(Parcel source) {
            return new Eventos(source);
        }

        @Override
        public Eventos[] newArray(int size) {
            return new Eventos[size];
        }
    };
}
