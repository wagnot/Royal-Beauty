package br.com.royal.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.royal.controllerReports.GerarRelatorioCaixa;
import br.com.royal.dao.CaixaDao;
import br.com.royal.model.Caixa;
import br.com.royal.model.EntradaCaixa;
import br.com.royal.model.MotivoEntradaCaixa;
import br.com.royal.model.MotivoSaidaCaixa;
import br.com.royal.model.SaidaCaixa;
import br.com.royal.modelReports.RelatorioCaixa;

public class CaixaController {
	private Date data = new Date();
	private SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public boolean verificaUltimoCaixa() {
		System.out.println(getUltimoCaixa().getDataCaixa());
		System.out.println((formatador.format(data).substring(0, 10)));
		if (this.getUltimoCaixa().getStatus()
				&& !this.getUltimoCaixa().getDataCaixa().equals(formatador.format(data).substring(0, 10)))
			return true;
		return false;
	}

	public Caixa getUltimoCaixa() {
		return CaixaDao.getUltimoCaixa();
	}

	public void cadastrarCaixa(Caixa caixa) {
		String dataCaixa = caixa.getDataCaixa().substring(0, 10);
		if (verificaCaixaAberto(dataCaixa) && !getCaixa(dataCaixa).getStatus()) {
			ativarCaixa(dataCaixa);
			cadastrarAtualizaEntradaCaixa(caixa);
		} else if (verificaCaixaAbertoStatus(dataCaixa)) {
			cadastrarAtualizaEntradaCaixa(caixa);
		} else
			cadastrar(caixa);
	}

	public void cadastrarAberturaEntradaCaixa(Caixa caixa) {
		EntradaCaixa ec = new EntradaCaixa();
		ec.setCodFormaDePagamento(3);
		ec.setCodMotivoEntrada(3);
		ec.setDataEntradaCaixa(caixa.getDataCaixa());
		ec.setValor(caixa.getValorAtual());
		ec.setCodCaixa(caixa.getCod());
		EntradaCaixaController.cadastrar(ec);

		// atualizarEntradaCaixa(caixa);
	}

	public void cadastrarVendaEntradaCaixa(Caixa caixa) {
		EntradaCaixa ec = new EntradaCaixa();
		ec.setCodFormaDePagamento(3);
		ec.setCodMotivoEntrada(4);
		ec.setDataEntradaCaixa(caixa.getDataCaixa());
		ec.setValor(caixa.getValorAtual());
		ec.setCodCaixa(caixa.getCod());
		EntradaCaixaController.cadastrar(ec);
		atualizarEntradaCaixa(caixa);
	}

	public void cadastrarEntradaCaixa(EntradaCaixa ec) {
		Caixa caixa = this.getCaixaAtual();
		ec.setCodCaixa(caixa.getCod());
		caixa.setValorAtual(ec.getValor());
		EntradaCaixaController.cadastrar(ec);
		atualizarEntradaCaixa(caixa);
	}

	public void cadastrarAtualizaEntradaCaixa(Caixa caixa) {
		EntradaCaixa ec = new EntradaCaixa();
		ec.setCodFormaDePagamento(3);
		ec.setCodMotivoEntrada(4);
		ec.setDataEntradaCaixa(caixa.getDataCaixa());
		ec.setValor(caixa.getValorAtual());
		ec.setCodCaixa(caixa.getCod());
		EntradaCaixaController.cadastrar(ec);
		atualizarEntradaCaixa(caixa);
	}

	private void cadastrar(Caixa caixa) {
		CaixaDao.cadastrarCaixa(caixa);
		cadastrarAberturaEntradaCaixa(caixa);
	}

	public void ativarCaixa(String data) {
		CaixaDao.ativarCaixa(data);
	}

	public boolean atualizarEntradaCaixa(Caixa caixa) {
		if (verificaCaixaAberto(caixa.getDataCaixa().substring(0, 10))) {
			// cadastrarEntradaCaixaVenda(caixa);
			CaixaDao.atualizarEntradaCaixa(caixa);
			return true;
		} else
			return false;
	}

	public boolean atualizarSaidaCaixa(Caixa caixa) {
		if (verificaCaixaAberto(caixa.getDataCaixa())) {
			CaixaDao.atualizarSaidaCaixa(caixa);
			return true;
		} else
			return false;
	}

	public boolean verificaCaixaAbertoStatus(String data) {
		if (CaixaDao.getCaixaStatus(data) != null)
			return true;
		else
			return false;
	}

	public boolean verificaCaixaAberto(String data) {
		if (CaixaDao.getCaixaData(data) != null)
			return true;
		else
			return false;
	}

	public Caixa getCaixaAtual() {
		return CaixaDao.getCaixaAtual(formatador.format(data).substring(0, 10));
	}

	public Caixa getCaixa(String data) {
		return CaixaDao.getCaixaData(data);
	}

	public void fecharCaixa(String data) {
		

		ArrayList<RelatorioCaixa> relatorioCaixa = new ArrayList<RelatorioCaixa>();
		Caixa c = CaixaDao.getCaixaData(data);
		GerarRelatorioCaixa grc = new GerarRelatorioCaixa();
		RelatorioCaixa rc = new RelatorioCaixa();
		rc.setDataAbertura(c.getDataCaixa());
		rc.setEstado("Fechado");
		rc.setValorInicial(c.getValorInicial() + "");
		rc.setValorFinal(c.getValorAtual() + "");
		rc.setData("");
		rc.setDataCompleta("");
		for (EntradaCaixa ec : EntradaCaixaController.getEntradaCaixasPCodCaixa(c.getCod())) {
			MotivoEntradaCaixa mec = new MotivoEntradaCaixa();
			mec.setCod(ec.getCodMotivoEntrada());
			mec = new MotivoEntradaCaixaController().getMotivoEntradaCaixa(mec.getCod());
			if (EntradaCaixaController.getEntradaCaixasPCodCaixa(c.getCod()).size()>1){
				if (rc.getEntradas()!=null){
					rc.setEntradas(rc.getEntradas()+ec.getDataEntradaCaixa().substring(ec.
							getDataEntradaCaixa().length()-8)+" - Valor: R$ "+ec.getValor()
							+", Motivo: "+mec.getDescricao()+"\n");
				}else{
					rc.setEntradas(ec.getDataEntradaCaixa().substring(ec.
							getDataEntradaCaixa().length()-8)+" - Valor: R$ "+ec.getValor()
							+", Motivo: "+mec.getDescricao()+"\n");
				}
				
			}else if (EntradaCaixaController.getEntradaCaixasPCodCaixa(c.getCod()).size()==1){
				rc.setEntradas(ec.getDataEntradaCaixa().substring(ec.getDataEntradaCaixa().
						length()-8)+" - Valor: R$ "+ec.getValor()+", Motivo: "+mec.
						getDescricao());
			}else{
				rc.setEntradas("");
			}
		}
		for (SaidaCaixa sc : SaidaCaixaController.getSaidaCaixasPCodCaixa(c.getCod())) {
			MotivoSaidaCaixa msc = new MotivoSaidaCaixa();
			msc.setCod(sc.getCodMotivoSaida());
			msc = new MotivoSaidaCaixaController().getMotivoSaidaCaixa(msc.getCod());
			if (SaidaCaixaController.getSaidaCaixasPCodCaixa(c.getCod()).size()>1){
				if (rc.getSaidas()!=null){
					rc.setSaidas(rc.getSaidas()+sc.getDataSaidaCaixa().substring(sc.
							getDataSaidaCaixa().length()-8)+" - Valor: R$ "+sc.getValor()
							+", Motivo: "+msc.getDescricao()+"\n");
				}else{
					rc.setSaidas(sc.getDataSaidaCaixa().substring(sc.
							getDataSaidaCaixa().length()-8)+" - Valor: R$ "+sc.getValor()
							+", Motivo: "+msc.getDescricao()+"\n");
				}
				
			}else if (SaidaCaixaController.getSaidaCaixasPCodCaixa(c.getCod()).size()==1){
				rc.setSaidas(sc.getDataSaidaCaixa().substring(sc.getDataSaidaCaixa().length()
						-8)+" - Valor: R$ "+sc.getValor()+", Motivo: "+msc.getDescricao());
			}else{
				rc.setSaidas("");
			}
		}
		relatorioCaixa.add(rc);
		grc.gerarCaixa(relatorioCaixa);
		CaixaDao.fecharCaixa(data);
	}

	private void cadastrarFechamentoSaidaCaixa(String data) {
		SaidaCaixa sd = new SaidaCaixa();
		sd.setCodMotivoSaida(3);
		sd.setDataSaidaCaixa(data);
		sd.setValor(getCaixa(data).getValorAtual());

		SaidaCaixaController.cadastrar(sd);
	}

	private void cadastrarSaidaCaixa(Caixa caixa) {
		SaidaCaixa sd = new SaidaCaixa();
		sd.setCodMotivoSaida(3);
		sd.setDataSaidaCaixa(caixa.getDataCaixa());
		sd.setValor(caixa.getValorAtual());
		sd.setCodCaixa(caixa.getCod());

		SaidaCaixaController.cadastrar(sd);

		atualizarSaidaCaixa(caixa);
	}

}
