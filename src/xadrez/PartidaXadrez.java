package xadrez;

import java.util.ArrayList;
import java.util.List;

import jogoTabuleiro.Peça;
import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	private List<Peça> peçasNoTabuleiro = new ArrayList<>();
	private List<Peça> peçasCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCA;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public PeçaXadrez[][] getPeças() {
		PeçaXadrez[][] mat = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getLinhas(); j++) {
				mat[i][j] = (PeçaXadrez) tabuleiro.peça(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] movimentosPossiveis(PosiçaoXadrez posiçaoOrigem) {
		 Posiçao posiçao = posiçaoOrigem.paraPosiçao();
		 validarPosiçaoOrigem(posiçao);
		 return tabuleiro.peça(posiçao).movimentosPossiveis();
	}
	
	public PeçaXadrez executarMovimento(PosiçaoXadrez posiçaoOrigem, PosiçaoXadrez posiçaoDestino) {
		Posiçao origem = posiçaoOrigem.paraPosiçao();
		Posiçao destino = posiçaoDestino.paraPosiçao();
		validarPosiçaoOrigem(origem);
		validarPosiçaoDestino(origem, destino);
		Peça peçaCapturada = façaMover(origem, destino);
		proximoTurno();
		return (PeçaXadrez)peçaCapturada;
	}
	
	private Peça façaMover(Posiçao origem, Posiçao destino) {
		Peça p = tabuleiro.removerPeça(origem);
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.colocarPeça(p, destino);
		
		if (peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			peçasCapturadas.add(peçaCapturada);
		}
		
		return peçaCapturada;
	}
	
	private void validarPosiçaoOrigem(Posiçao posiçao) {
		if (!tabuleiro.temUmaPeça(posiçao)) {
			throw new ExceçaoXadrez("Não há peça na posição.");
		}
		if (jogadorAtual != ((PeçaXadrez)tabuleiro.peça(posiçao)).getCor()) {
			throw new ExceçaoXadrez("A peça escolhida não é sua.");
		}
		if (!tabuleiro.peça(posiçao).existeMovimentoPossivel()) {
			throw new ExceçaoXadrez("Não existem movimentos possíveis para a peça escolhida.");
		}
	}
	
	private void validarPosiçaoDestino(Posiçao origem, Posiçao destino) {
		if (!tabuleiro.peça(origem).movimentoPossivel(destino)) {
			throw new ExceçaoXadrez("A peça escolhida não pode se mover para a posição de destino.");
		}
	}
	
	private void proximoTurno() {
		turno ++;
		jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.colocarPeça(peça, new PosiçaoXadrez(coluna, linha).paraPosiçao());
		peçasNoTabuleiro.add(peça);
	}
	
	private void setupInicial() {
		colocarNovaPeça('c', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('c', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('d', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('e', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('e', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('d', 1, new Rei(tabuleiro, Cor.BRANCA));

		colocarNovaPeça('c', 7, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeça('c', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeça('d', 7, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeça('e', 7, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeça('e', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeça('d', 8, new Rei(tabuleiro, Cor.PRETA));
	}
}
