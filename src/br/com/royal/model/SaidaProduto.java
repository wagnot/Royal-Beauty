package br.com.royal.model;

public class SaidaProduto {
	private int codSaidaProduto;
	private int codMotivoSaidaProduto;
	private long codProduto;
	private String dataSaidaProduto;
	private int quantidadeSaidaProduto;
	private String nomeProduto;
	private String motivo;

	public int getCodSaidaProduto() {
		return codSaidaProduto;
	}

	public void setCodSaidaProduto(int codSaidaProduto) {
		this.codSaidaProduto = codSaidaProduto;
	}

	public int getCodMotivoSaidaProduto() {
		return codMotivoSaidaProduto;
	}

	public void setCodMotivoSaidaProduto(int codMotivoSaidaProduto) {
		this.codMotivoSaidaProduto = codMotivoSaidaProduto;
	}

	public long getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(long codProduto) {
		this.codProduto = codProduto;
	}

	public String getDataSaidaProduto() {
		return dataSaidaProduto;
	}

	public void setDataSaidaProduto(String dataSaidaProduto) {
		this.dataSaidaProduto = dataSaidaProduto;
	}

	public int getQuantidadeSaidaProduto() {
		return quantidadeSaidaProduto;
	}

	public void setQuantidadeSaidaProduto(int quantidadeSaidaProduto) {
		this.quantidadeSaidaProduto = quantidadeSaidaProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
