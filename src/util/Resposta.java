package util;

import java.io.Serializable;

public class Resposta implements Serializable {
    private int vencedor;

    public int getVencedor() {
        return vencedor;
    }

    public void setVencedor(int vencedor) {
        this.vencedor = vencedor;
    }
}
