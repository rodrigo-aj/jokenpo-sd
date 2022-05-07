package util;

import java.io.Serializable;

public class Requisicao implements Serializable {
    private final int primeiraAcao;
    private final int segundaAcao;

    public Requisicao(int primeiraAcao, int segundaAcao) {
        this.primeiraAcao = primeiraAcao;
        this.segundaAcao = segundaAcao;
    }

    public int getPrimeiraAcao() {
        return primeiraAcao;
    }

    public int getSegundaAcao() {
        return segundaAcao;
    }
}