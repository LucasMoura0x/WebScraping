package model;

import java.math.BigDecimal;

public class Produtos {
	private String descricao;
	private String dadosValores;
	private BigDecimal valorAvista;
	private BigDecimal ValorAPrazo;
	private int parcelas;
	
	
	public Produtos(String descricao, String dadosValores) {
		super();
		this.descricao = descricao;
		this.dadosValores = dadosValores;
		this.parcelas = getParcelas(dadosValores);	
		this.valorAvista = getValorAvista(dadosValores);	
		this.ValorAPrazo = getValorAPrazo(dadosValores);
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getDadosValores() {
		return dadosValores;
	}


	public void setDadosValores(String dadosValores) {
		this.dadosValores = dadosValores;
	}


	public BigDecimal getValorAvista(String dadosValores) {
	    try {
	        // Se não tiver cifrão, não tem preço válido
	        if (!dadosValores.contains("$")) return BigDecimal.ZERO;

	        // Pegamos apenas os números e a vírgula/ponto
	        // Removemos o R$ e espaços
	        String limpo = dadosValores.split("\n")[0]; // Pega só a primeira linha (onde fica o preço)
	        limpo = limpo.replaceAll("[^0-9,]", "").replace(",", ".");
	        
	        return new BigDecimal(limpo);
	    } catch (Exception e) {
	        return BigDecimal.ZERO; // Retorna 0 em vez de travar o programa
	    }
	}
	
	public BigDecimal getValorAvista() {
		return this.valorAvista;
	}


	public void setValorAvista(BigDecimal valorAvista) {
		this.valorAvista = valorAvista;
	}


	public BigDecimal getValorAPrazo(String dadosValores) {
		if(this.parcelas == 0)
			return BigDecimal.ZERO;
		
		return new BigDecimal(dadosValores.substring(dadosValores.lastIndexOf('$')+ 2, dadosValores.lastIndexOf(',')+ 3)
		.replaceAll("\\.",  "").replaceAll(",", "\\.")).multiply(new BigDecimal(this.parcelas));
	}
	
	public BigDecimal getValorAPrazo() {
		return this.ValorAPrazo;
	}


	public void setValorAPrazo(BigDecimal valorAPrazo) {
		ValorAPrazo = valorAPrazo;
	}

	public int getParcelas() {
		return parcelas;
	}
	public int getParcelas(String dadosValores) {
		if(dadosValores.indexOf("até ")>0)
			return Integer.parseInt(dadosValores.substring(dadosValores.indexOf("até ") + 4, dadosValores.indexOf("x de") ));
		
		return 0;
	}


	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}


	@Override
	public String toString() {
		return "Produtos [descricao=" + descricao + ", dadosValores=" + dadosValores + ", valorAvista=" + valorAvista
				+ ", ValorAPrazo=" + ValorAPrazo + ", parcelas=" + parcelas + "]";
	}
	
	

}
