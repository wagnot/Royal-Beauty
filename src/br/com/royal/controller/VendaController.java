package br.com.royal.controller;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.royal.dao.FiadoDao;
import br.com.royal.dao.ItensComandaProdutoDao;
import br.com.royal.dao.ItensComandaServicoDao;
import br.com.royal.dao.ItensVendaProdutoDao;
import br.com.royal.dao.ItensVendaServicoDao;
import br.com.royal.dao.VendaProdutoDao;
import br.com.royal.dao.VendaServicoDao;
import br.com.royal.model.Cartao;
import br.com.royal.model.Cheque;
import br.com.royal.model.ComandaProduto;
import br.com.royal.model.ComandaServico;
import br.com.royal.model.EntradaCaixa;
import br.com.royal.model.Fiado;
import br.com.royal.model.Pagamento;
import br.com.royal.model.Parcela;
import br.com.royal.model.Usuario;
import br.com.royal.model.VendaProduto;
import br.com.royal.model.VendaServico;

public class VendaController {
	private ItensComandaProdutoDao icpdao= new ItensComandaProdutoDao();
	private ItensComandaServicoDao icsdao = new ItensComandaServicoDao();
	private VendaProdutoDao vpdao = new VendaProdutoDao();
	private VendaServicoDao vsdao = new VendaServicoDao();
	private Usuario usuarioLogado = UsuarioController.getUsuarioLogado();
	private ItensVendaProdutoDao ivpDao = new ItensVendaProdutoDao();
	private ItensVendaServicoDao ivsDao = new ItensVendaServicoDao();
	private EntradaCaixaController ecc = new EntradaCaixaController();	
	
	public void cadastrosVenda(Pagamento pagamento, ComandaServico comandaServico, ComandaProduto comandaProduto, ArrayList<Parcela> parcelas, ArrayList<Cartao> cartoes, ArrayList<Cheque> cheques,ArrayList<Fiado>fiados){
		int codComandaServico=cadastraComandaServico(comandaServico), codComandaProduto=this.cadastrarComandaProduto(comandaProduto);
		pagamento.setCodComandaServico(codComandaServico);
		pagamento.setCodComandaProduto(codComandaProduto);
		
		comandaProduto.setCodComandaProduto(codComandaProduto);
		comandaServico.setComandaServico(codComandaServico);
		
		icpdao.cadastrarItensComandaProduto(comandaProduto);
		icsdao.cadastrarItensComandaServico(comandaServico);

		int codPagamento =PagamentoController.cadastrarPagamento(pagamento);
		
		VendaProduto vp = new VendaProduto();
		vp.setValorTotalProduto(comandaProduto.getSubTotalProduto());
		vp.setCodCliente(pagamento.getCodCliente());
		vp.setCodFuncionarioProduto(usuarioLogado.getFuncionario().getCodFuncionario());
		vp.setCodComandaProduto(codComandaProduto);
		
		int codVendaProduto = vpdao.cadastrarVendaProduto(vp);
		
		VendaServico vs = new VendaServico();
		vs.setValorTotalServico(comandaServico.getSubTotalComanda());
		vs.setCodCliente(pagamento.getCodCliente());
		vs.setCodFuncionario(usuarioLogado.getFuncionario()
				.getCodFuncionario());
		vs.setCodComandaServico(codComandaServico);

		int codVendaServico = vsdao.cadastrarVendaServico(vs);
		vs.setCodVendaServico(codVendaServico);
		vp.setCodVendaProduto(codVendaProduto);
		
		ivpDao.cadastrarItensVendaProduto(vp, comandaProduto);
		ivsDao.cadastrarItensVendaServico(vs,comandaServico);
		
		int codParcela=0;
		int indexCartao=0;
		int indexCheque = 0;
		int indexFiado = 0;
		for(Parcela p : parcelas){
			p.setCodVendaProduto(codVendaProduto);
			p.setCodVendaServico(codVendaServico);
			p.setCodPagamento(codPagamento);
			
			ParcelaController pc = new ParcelaController();
			EntradaCaixa ec = new EntradaCaixa();
			ec.setDataEntradaCaixa(pagamento.getDataPagamento());
			ec.setValor(p.getValorParcela());
			//cheque
			if(p.getCodFormaDePagamento()==1){
				ChequeController cController = new ChequeController();
				p.setDataVencimento(pagamento.getDataPagamento().substring(0, 10));
				codParcela = pc.cadastrarParcela(p);
				cheques.get(indexCheque).setCodParcela(codParcela);
				cController.cadastrarCheque(cheques.get(indexCheque));
				indexCheque++;
				
				ec.setCodFormaDePagamento(1);
				ec.setCodMotivoEntrada(1);
				new CaixaController().cadastrarEntradaCaixa(ec);
			}
			//cartao
			if(p.getCodFormaDePagamento()==2){
				CartaoController cc = new CartaoController();
				
				if(p.getCodQuantidadeParcela()>1){
					Calendar calendario = Calendar.getInstance();
					
					int dia = calendario.get(Calendar.DAY_OF_MONTH);
					int mes = calendario.get(Calendar.MONTH);
					int ano = calendario.get(Calendar.YEAR);
					for(int i=0;i<=p.getCodQuantidadeParcela()-1;i++){ // se a primeira parcela for esse mes e so por o i em 1
						mes ++;
						if (mes>12){
							ano++;
							mes = 1;
						}
						p.setDataVencimento(dia+"/"+mes+"/"+ano);
						
						codParcela=pc.cadastrarParcela(p);
						cartoes.get(indexCartao).setCodParcela(codParcela);
						
						cc.cadastrarCartao(cartoes.get(indexCartao));
					}
				}else{
					p.setDataVencimento(pagamento.getDataPagamento());
					codParcela=pc.cadastrarParcela(p);
					cartoes.get(indexCartao).setCodParcela(codParcela);
					
					cc.cadastrarCartao(cartoes.get(indexCartao));
					
				}
				indexCartao++;

				ec.setCodFormaDePagamento(1);
				ec.setCodMotivoEntrada(1);
				new CaixaController().cadastrarEntradaCaixa(ec);
			}
			
			//dinheiro
			if (p.getCodFormaDePagamento()==3){
				p.setDataVencimento(pagamento.getDataPagamento());
				pc.cadastrarParcela(p);
			}
			
			//fiado
			if (p.getCodFormaDePagamento()==4){
				p.setDataVencimento(pagamento.getDataPagamento());
				FiadoController fc = new FiadoController();
				codParcela = pc.cadastrarParcela(p);
				fiados.get(indexFiado).setCodParcela(codParcela);
				fc.cadastrarFiado(fiados.get(indexFiado));
				
				indexFiado++;
			}
			
		}
		// na hora de apresentar as qtd de parcelas de um cartao, a gente dividi a qtd de parcelas pelo preço da parcela
	}
	
	public void pagarFiado(Fiado f,Pagamento pagamento, int codComandaProduto,int CodComandaServico,int codVendaProduto, int codVendaServico,ArrayList<Parcela> parcelas, ArrayList<Cartao> cartoes, ArrayList<Cheque> cheques,ArrayList<Fiado>fiados){
		int codPagamento =PagamentoController.cadastrarPagamento(pagamento);
		
		FiadoDao fDao = new FiadoDao();
		fDao.pagarFiado(f);
		
		
		
		int codParcela=0;
		int indexCartao=0;
		int indexCheque = 0;
		int indexFiado = 0;
		for(Parcela p : parcelas){
			p.setCodVendaProduto(codVendaProduto);
			p.setCodVendaServico(codVendaServico);
			p.setCodPagamento(codPagamento);
			
			ParcelaController pc = new ParcelaController();
			EntradaCaixa ec = new EntradaCaixa();
			ec.setDataEntradaCaixa(pagamento.getDataPagamento());
			ec.setValor(p.getValorParcela());
			//cheque
			if(p.getCodFormaDePagamento()==1){
				ChequeController cController = new ChequeController();
				p.setDataVencimento(pagamento.getDataPagamento().substring(0, 10));
				codParcela = pc.cadastrarParcela(p);
				cheques.get(indexCheque).setCodParcela(codParcela);
				cController.cadastrarCheque(cheques.get(indexCheque));
				indexCheque++;
				
				ec.setCodFormaDePagamento(1);
				ec.setCodMotivoEntrada(1);
				new CaixaController().cadastrarEntradaCaixa(ec);
			}
			//cartao
			if(p.getCodFormaDePagamento()==2){
				CartaoController cc = new CartaoController();
				
				if(p.getCodQuantidadeParcela()>1){
					Calendar calendario = Calendar.getInstance();
					
					int dia = calendario.get(Calendar.DAY_OF_MONTH);
					int mes = calendario.get(Calendar.MONTH);
					int ano = calendario.get(Calendar.YEAR);
					for(int i=0;i<=p.getCodQuantidadeParcela()-1;i++){ // se a primeira parcela for esse mes e so por o i em 1
						mes ++;
						if (mes>12){
							ano++;
							mes = 1;
						}
						p.setDataVencimento(dia+"/"+mes+"/"+ano);
						
						codParcela=pc.cadastrarParcela(p);
						cartoes.get(indexCartao).setCodParcela(codParcela);
						
						cc.cadastrarCartao(cartoes.get(indexCartao));
					}
				}else{
					p.setDataVencimento(pagamento.getDataPagamento());
					codParcela=pc.cadastrarParcela(p);
					cartoes.get(indexCartao).setCodParcela(codParcela);
					
					cc.cadastrarCartao(cartoes.get(indexCartao));
					
				}
				indexCartao++;

				ec.setCodFormaDePagamento(1);
				ec.setCodMotivoEntrada(1);
				new CaixaController().cadastrarEntradaCaixa(ec);
				
			}
			
			//dinheiro
			if (p.getCodFormaDePagamento()==3){
				p.setDataVencimento(pagamento.getDataPagamento());
				pc.cadastrarParcela(p);
			}
			
			//fiado
			if (p.getCodFormaDePagamento()==4){
				p.setDataVencimento(pagamento.getDataPagamento());
				FiadoController fc = new FiadoController();
				codParcela = pc.cadastrarParcela(p);
				fiados.get(indexFiado).setCodParcela(codParcela);
				fc.cadastrarFiado(fiados.get(indexFiado));
				
				indexFiado++;
			}
			
		}

		
	}
	
	public ArrayList<VendaProduto> listarVendasProduto (){
		return vpdao.getVendasProduto();
	}
	
	public ArrayList<VendaServico> listarVendasServico(){
		return vsdao.getVendasServico();
	}
	
	public int cadastraComandaServico(ComandaServico comanda){
		return ComandaServicoController.cadastrarComandaServico(comanda);
	}
	
	public int cadastrarComandaProduto(ComandaProduto comanda){
		return ComandaProdutoController.cadastarComandaProduto(comanda);
	}

}
