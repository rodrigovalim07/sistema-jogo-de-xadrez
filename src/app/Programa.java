package app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExceçaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;
import xadrez.PosiçaoXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner teclado = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PeçaXadrez> capturadas = new ArrayList<>();
		
		while (true) {
			try {
				UI.limparTela();
				UI.printPartida(partidaXadrez, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosiçaoXadrez origem = UI.lerPosiçaoXadrez(teclado);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPeças(), movimentosPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosiçaoXadrez destino = UI.lerPosiçaoXadrez(teclado);
				
				PeçaXadrez peçaCapturada = partidaXadrez.executarMovimento(origem, destino);
				
				if (peçaCapturada != null) {
					capturadas.add(peçaCapturada);
				}
			}
			catch (ExceçaoXadrez e) {
				System.out.print(e.getMessage());
				teclado.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.print(e.getMessage());
				teclado.nextLine();
			}
		}
	}
}
