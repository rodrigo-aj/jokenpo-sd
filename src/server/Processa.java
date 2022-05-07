package server;

import util.Comunicacao;
import util.Requisicao;
import util.Resposta;

import java.net.Socket;

public class Processa extends Thread {
    private static final int PEDRA = 1;
    private static final int PAPEL = 2;
    private static final int TESOURA = 3;
    private static final int PRIMEIRO_JOGADOR = 0;
    private static final int SEGUNDO_JOGADOR = 1;
    private static final int EMPATE = -1;
    private static final int ERRO_DEFAULT = -2;

    Socket socket;
    Comunicacao comunicacao;

    public Processa(Socket socket) {
        this.socket = socket;
        comunicacao = new Comunicacao(socket);
    }

    @Override
    public void run() {
        Requisicao requisicao = (Requisicao) comunicacao.receive();

        int jogadaPrimeiroUsuario = requisicao.getPrimeiraAcao();
        int jogadaSegundoUsuario = requisicao.getSegundaAcao();

        Resposta resposta = new Resposta();

        switch (jogadaPrimeiroUsuario) {
            case PEDRA:
                switch (jogadaSegundoUsuario) {
                    case PEDRA:
                        resposta.setVencedor(EMPATE);
                        break;

                    case PAPEL:
                        resposta.setVencedor(SEGUNDO_JOGADOR);
                        break;

                    case TESOURA:
                        resposta.setVencedor(PRIMEIRO_JOGADOR);
                        break;

                    default:
                        resposta.setVencedor(ERRO_DEFAULT);
                }
                break;
            case PAPEL:
                switch (jogadaSegundoUsuario) {
                    case PEDRA:
                        resposta.setVencedor(PRIMEIRO_JOGADOR);
                        break;

                    case PAPEL:
                        resposta.setVencedor(EMPATE);
                        break;

                    case TESOURA:
                        resposta.setVencedor(SEGUNDO_JOGADOR);
                        break;

                    default:
                        resposta.setVencedor(-2);
                }
                break;
            case TESOURA:
                switch (jogadaSegundoUsuario) {
                    case PEDRA:
                        resposta.setVencedor(SEGUNDO_JOGADOR);
                        break;

                    case PAPEL:
                        resposta.setVencedor(PRIMEIRO_JOGADOR);
                        break;

                    case TESOURA:
                        resposta.setVencedor(EMPATE);
                        break;

                    default:
                        resposta.setVencedor(ERRO_DEFAULT);
                }
                break;
            default:
                resposta.setVencedor(ERRO_DEFAULT);
        }

        comunicacao.send(resposta);
    }
}
