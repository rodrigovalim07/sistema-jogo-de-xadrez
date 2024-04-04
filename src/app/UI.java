package app;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosiçaoXadrez;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosiçaoXadrez lerPosiçaoXadrez(Scanner teclado) {
		try {
			String s = teclado.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosiçaoXadrez(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro na leitura da posição. As posições válidas são de a1 até h8");
		}
	}
	
	public static void printPartida(PartidaXadrez partidaXadrez, List<PeçaXadrez> capturadas) {
		printTabuleiro(partidaXadrez.getPeças());
		System.out.println();
		printPeçasCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: " + partidaXadrez.getTurno());
		System.out.println("Aguardando o jogador: " + partidaXadrez.getJogadorAtual());
	}
	
	public static void printTabuleiro(PeçaXadrez[][] peças) {
		for (int i = 0; i < peças.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < peças.length; j++) {
				printPeça(peças[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printTabuleiro(PeçaXadrez[][] peças, boolean[][] movimentosPossiveis) {
		for (int i = 0; i < peças.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < peças.length; j++) {
				printPeça(peças[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPeça(PeçaXadrez peça, boolean corFundo) {
		if (corFundo) {
			System.out.print(ANSI_PURPLE_BACKGROUND);
		}
		if (peça == null) {
			System.out.print("-" + ANSI_RESET);
		}
		else {
			if (peça.getCor() == Cor.BRANCA) {
                System.out.print(ANSI_WHITE + peça + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peça + ANSI_RESET);
            }
		}
		System.out.print(" ");
	}
	
	private static void printPeçasCapturadas(List<PeçaXadrez> capturadas) {
		List<PeçaXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cor.BRANCA).collect(Collectors.toList());
		List<PeçaXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cor.PRETA).collect(Collectors.toList());
		System.out.println("Peças capturadas");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(brancas.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(pretas.toArray()));
		System.out.print(ANSI_RESET);
	}
}
