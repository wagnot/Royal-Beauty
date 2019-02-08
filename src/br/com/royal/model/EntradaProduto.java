package br.com.royal.model;

public class EntradaProduto {
	private int idEntradaProduto;//
	private String dataEntradaProduto;//
	private int quantidadeEntradaProduto;//
	private int codMotivoEntradaProduto;//
	private String loteProduto;//
	private String dataValidadeLote;//
	private String motivo;//
	private String nomeProduto;
	private String nomeFornecedor;
	private long codProduto;
	private int codFornecedor;

	public int getIdEntradaProduto() {
		return idEntradaProduto;
	}

	public void setIdEntradaProduto(int idEntradaProduto) {
		this.idEntradaProduto = idEntradaProduto;
	}

	public String getDataEntradaProduto() {
		return dataEntradaProduto;
	}

	public void setDataEntradaProduto(String dataEntradaProduto) {
		this.dataEntradaProduto = dataEntradaProduto;
	}

	public int getCodMotivoEntradaProduto() {
		return codMotivoEntradaProduto;
	}

	public void setCodMotivoEntradaProduto(int codMotivoEntradaProduto) {
		this.codMotivoEntradaProduto = codMotivoEntradaProduto;
	}

	public String getLoteProduto() {
		return loteProduto;
	}

	public void setLoteProduto(String loteProduto) {
		this.loteProduto = loteProduto;
	}

	public String getDataValidadeLote() {
		return dataValidadeLote;
	}

	public void setDataValidadeLote(String dataValidadeLote) {
		this.dataValidadeLote = dataValidadeLote;
	}

	public int getQuantidadeEntradaProduto() {
		return quantidadeEntradaProduto;
	}

	public void setQuantidadeEntradaProduto(int quantidadeEntradaProduto) {
		this.quantidadeEntradaProduto = quantidadeEntradaProduto;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public long getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(long codProduto) {
		this.codProduto = codProduto;
	}

	public int getCodFornecedor() {
		return codFornecedor;
	}

	public void setCodFornecedor(int codFornecedor) {
		this.codFornecedor = codFornecedor;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

}
