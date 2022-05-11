package client;

import util.Comunicacao;
import util.Requisicao;
import util.Resposta;

import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Client {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 9876;
    private static final int OPCAO_CPU = 2;

    public static void main(String[] args) {
        Socket socket;
        Random rd = new Random();
        Scanner scanner = new Scanner(System.in);
        int entradaPrimeiraOpcao = 0,
                entradaSegundaOpcao = 0,
                continuarJogando = 0,
                contadorEmpates = 0,
                contadorVitoriasPrimeiroJogador = 0,
                contadorVitoriasSegundoJogador = 0;

        do {
            final int entradaTipoUsuario = persistirEntradaTipoUsuario(scanner);

            if (entradaTipoUsuario == OPCAO_CPU) {
                System.out.println("A CPU irá escolher uma opção....");
                entradaPrimeiraOpcao = rd.nextInt(3 - 1 + 1) + 1;
                System.out.println("Opção escolhida: " + entradaPrimeiraOpcao);
            } else {
                System.out.println("Primeiro usuário, escolha uma opção: ");
                entradaPrimeiraOpcao = persistirEntradaAcao(scanner);
                System.out.println("Opção escolhida: " + entradaPrimeiraOpcao);
            }

            System.out.println("Segunda usuário, escolha uma opção: ");
            entradaSegundaOpcao = persistirEntradaAcao(scanner);
            System.out.println("Opção escolhida: " + entradaSegundaOpcao);


            try {
                socket = new Socket(IP, PORT);

                Requisicao requisicao = new Requisicao(entradaPrimeiraOpcao, entradaSegundaOpcao);

                Comunicacao comunicacao = new Comunicacao(socket);

                comunicacao.send(requisicao);

                Resposta resposta = (Resposta) comunicacao.receive();

                if (resposta.getVencedor() == -1) {
                    System.out.println("\nEmpate!");
                    contadorEmpates = contadorEmpates + 1;
                } else if (resposta.getVencedor() == 1) {
                    System.out.println("\nO Jogador 2 venceu essa rodada!");
                    contadorVitoriasSegundoJogador = contadorVitoriasSegundoJogador + 1;
                } else if (resposta.getVencedor() == 0 && entradaTipoUsuario == OPCAO_CPU) {
                    System.out.println("\nA CPU venceu essa rodada!");
                    contadorVitoriasSegundoJogador = contadorVitoriasSegundoJogador + 1;
                } else {
                    System.out.println("\nO Jogador 1 venceu essa rodada!");
                    contadorVitoriasPrimeiroJogador = contadorVitoriasPrimeiroJogador + 1;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            montarPlacar(contadorEmpates, contadorVitoriasPrimeiroJogador, contadorVitoriasSegundoJogador);

            continuarJogando = persistirDigitoSaida(scanner);

        } while (continuarJogando == 1);

        montarPlacar(contadorEmpates, contadorVitoriasPrimeiroJogador, contadorVitoriasSegundoJogador);

        scanner.close();
    }

    private static int persistirEntradaTipoUsuario(Scanner enter) {
        int numeroDigitado = 0;
        boolean validaEntradaTipoUsuario = false;

        do {
            System.out.println("\n1. Jogador vs Jogador\n2. CPU vs Jogador");

            if (enter.hasNextInt()) {
                numeroDigitado = enter.nextInt();

                if (numeroDigitado < 1 || numeroDigitado > 2) {
                    enter.nextLine();
                    System.out.println("Digite um valor entre 1 e 2, inteiro");
                } else {
                    validaEntradaTipoUsuario = true;
                }
            } else {
                enter.nextLine();
                System.out.println("Digite um valor válido!");
            }

        } while (!validaEntradaTipoUsuario);

        return numeroDigitado;
    }

    private static int persistirEntradaAcao(Scanner enter) {
        int numeroDigitado = 0;
        boolean validaEntradaAcao = false;

        do {
            System.out.println("1. Pedra\n2. Papel\n3. Tesoura");

            if (enter.hasNextInt()) {
                numeroDigitado = enter.nextInt();

                if (numeroDigitado < 1 || numeroDigitado > 3) {
                    enter.nextLine();
                    System.out.println("Digite um valor entre 1 e 3, inteiro");
                } else {
                    validaEntradaAcao = true;
                }
            } else {
                enter.nextLine();
                System.out.println("Digite um valor válido!");
            }

        } while (!validaEntradaAcao);

        return numeroDigitado;
    }

    private static int persistirDigitoSaida(Scanner enter) {
        int numeroDigitado = 0;
        boolean validaEntradaUsuario = false;

        do {
            System.out.println("\nGostaria de continuar jogando? (Digite 1 para sim ou qualquer outro número para sair)");

            if (enter.hasNextInt()) {
                numeroDigitado = enter.nextInt();
                validaEntradaUsuario = true;
            } else {
                enter.nextLine();
                enter = new Scanner(System.in);
                System.out.println("\nDigite um valor válido!");
            }

        } while (!validaEntradaUsuario);

        return numeroDigitado;
    }

    private static void montarPlacar(int qtdEmpates, int qtdVitoriasPrimeiroJogador, int qtdVitoriasSegundoJogador) {
        System.out.println("\n***** PLACAR *****");

        System.out.println("EMPATES: " + qtdEmpates);
        System.out.println("VITÓRIAS PRIMEIRO JOGADOR: " + qtdVitoriasPrimeiroJogador);
        System.out.println("VITÓRIAS SEGUNDO JOGADOR: " + qtdVitoriasSegundoJogador);
    }
}