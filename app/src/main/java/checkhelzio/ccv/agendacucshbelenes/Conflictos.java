package checkhelzio.ccv.agendacucshbelenes;


class Conflictos {
    private int num_fecha;
    private int num_fecha_2;
    private String tipo;
    private Eventos queEvento;
    private int colorFondo;

    Conflictos(int n, Eventos e, int colorFondo, String tipo) {
        this.num_fecha = n;
        this.queEvento = e;
        this.colorFondo = colorFondo;
        this.tipo = tipo;
    }

    Conflictos(int num_fecha, Eventos queEvento) {
        this.num_fecha = num_fecha;
        this.queEvento = queEvento;
    }

    Conflictos(int num_fecha, int num_fecha_2, String tipo) {
        this.num_fecha = num_fecha;
        this.num_fecha_2 = num_fecha_2;
        this.tipo = tipo;
    }

    int getColorFondo() {
        return colorFondo;
    }

    String getTipo() {
        return tipo;
    }

    int getNum_fecha() {
        return num_fecha;
    }

    Eventos getQueEvento() {
        return queEvento;
    }

    int getNum_fecha_2() {
        return num_fecha_2;
    }

}
