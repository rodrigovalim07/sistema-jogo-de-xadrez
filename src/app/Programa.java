package app;

import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;
import xadrez.PartidaXadrez;

public class Programa {

	public static void main(String[] args) {
		
		PartidaXadrez partida = new PartidaXadrez();
		UI.printTabuleiro(partida.getPeças());
	}

}
