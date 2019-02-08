package br.com.royal.model;

public class Produto implements Comparable<Produto>{
	private long id;
	private String nomeProduto;
	private String descricaoProduto;
	private double valor;
	private int quantidade;
	private int codFabricante;
	private String codBarras;
	private int atividadeProduto;
	private int quantidadeProdutoComanda;
	private double custoProduto;
	private double lucroProduto;
	private int quantidadeProdutoEstoque;

	public int getQuantidadeProdutoEstoque() {
		return quantidadeProdutoEstoque;
	}

	public void setQuantidadeProdutoEstoque(int quantidadeProdutoEstoque) {
		this.quantidadeProdutoEstoque = quantidadeProdutoEstoque;
	}

	public double getCustoProduto() {
		return custoProduto;
	}

	public void setCustoProduto(double custoProduto) {
		this.custoProduto = custoProduto;
	}

	public double getLucroProduto() {
		return lucroProduto;
	}

	public void setLucroProduto(double lucroProduto) {
		this.lucroProduto = lucroProduto;
	}

	public int getQuantidadeProdutoComanda() {
		return quantidadeProdutoComanda;
	}

	public void setQuantidadeProdutoComanda(int quantidadeProdutoComanda) {
		this.quantidadeProdutoComanda = quantidadeProdutoComanda;
	}

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	private String nomeFabricante;

	private String fotoProduto;

	public String getNomeFabricante() {
		return nomeFabricante;
	}

	public void setNomeFabricante(String nomeFabricante) {
		this.nomeFabricante = nomeFabricante;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public String getFotoProduto() {
		return fotoProduto;
	}

	public void setFotoProduto(String fotoProduto) {
		this.fotoProduto = fotoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getCodFabricante() {
		return codFabricante;
	}

	public void setCodFabricante(int codFabricante) {
		this.codFabricante = codFabricante;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String toString() {
		return getNomeProduto();
	}

	public int getAtividadeProduto() {
		return atividadeProduto;
	}

	public void setAtividadeProduto(int atividadeProduto) {
		this.atividadeProduto = atividadeProduto;
	}

	@Override
	public int compareTo(Produto p) {
		return this.nomeProduto.compareToIgnoreCase(p.getNomeProduto());
	}
}
