package controller;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import model.Produtos;

public class WebScraping {
	public static void main (String [] args) {
		criarPlanilha(rasparDados());
		
	}

	private static ArrayList<Produtos> rasparDados() {
		//Definir caminho do EdgeDriver
		System.setProperty("webdriver.edge.driver", "resources/msedgedriver.exe");
		
		EdgeOptions options = new EdgeOptions();
		
		//não exibe o navegador
		//options.addArguments("--headless=new");
		
		//Tenta previnir possiveis erros na execução
		
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		
		//Evite detecção dos sites
		
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", Collections.singleton("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", null);
		
		//Define o tamanho da janela
		options.addArguments("window-size=1600,800");
		
		//Ajuda a não ser identificado como bot
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; x64) AppleWebkit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
		
		WebDriver driver = new EdgeDriver(options = options);
		
		driver.get("https://amazon.com.br");
		
		WebElement inputPesquisa = driver.findElement(By.xpath("//input[@id=\"twotabsearchtextbox\"]"));
		
		
		inputPesquisa.sendKeys("smartphones");
		
		inputPesquisa.submit();
		
		//tempo de espera
		waitForIt(5000);
		
		List<WebElement> descricoesProdutos = driver.findElements(By.xpath("//h2/a/span | //h2/span"));
		
		List<WebElement> valoresProdutos = driver.findElements(By.xpath("//div[@class=\"a-row a-size-base a-color-base\"]"));

		ArrayList<Produtos> produtos = new ArrayList<>();
		for(int i = 0; i < Math.min(descricoesProdutos.size(), valoresProdutos.size()); i++) {
			
			produtos.add(new Produtos(descricoesProdutos.get(i).getText(), valoresProdutos.get(i).getText()));
			}
		
	
			
		
		waitForIt(5000);

		driver.quit();
		
		return produtos;
	}
	//METODO PARA PAUSAR A EXECUÇÃO
	private static void waitForIt(long temp) {
		try {
			new Thread().sleep(temp);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	//Cria um arquivo excel
	private static void criarPlanilha(ArrayList<Produtos> produtos){
		
		Workbook workbook = new XSSFWorkbook();
		
	//Criar a Planilha
		Sheet sheet = workbook.createSheet("PRODUTOS");
		
		//Definir fonte em negrito
		
		Font fonteNegrito = workbook.createFont();
		fonteNegrito.setBold(true);
		
		//Definir celular em negrito
		
		CellStyle estiloNegrito = workbook.createCellStyle();
		estiloNegrito.setFont(fonteNegrito);

	//Criar as colunas
		
		//1
		Row linha = sheet.createRow(0);
		Cell celula1 = linha.createCell(0);
		celula1.setCellValue("Descrição");
		celula1.setCellStyle(estiloNegrito);
		
		//2
		Cell celula2 = linha.createCell(1);
		celula2.setCellValue("Valor a Vista");
		celula2.setCellStyle(estiloNegrito);
		
		//3
		Cell celula3 = linha.createCell(2);
		celula3.setCellValue("Quantidade de Parcelas");
		celula3.setCellStyle(estiloNegrito);
		
		//4
		Cell celula4 = linha.createCell(3);
		celula4.setCellValue("Valor a Prazo");
		celula4.setCellStyle(estiloNegrito);
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		
		
		if(produtos.size() > 0) {
			int i = 1;
			for(Produtos produto: produtos) {
				Row linhaProduto = sheet.createRow(i);
				Cell celulaDescricao = linhaProduto.createCell(0);
				celulaDescricao.setCellValue(produto.getDescricao());
				
				Cell celulaValorAvista = linhaProduto.createCell(1);
				celulaValorAvista.setCellValue(produto.getValorAvista().toString());
				
				
				Cell celulaQuantidadeParcelas = linhaProduto.createCell(2);
				celulaQuantidadeParcelas.setCellValue(produto.getParcelas());
				
				
				Cell celulaValorAPrazo = linhaProduto.createCell(3);
				celulaValorAPrazo.setCellValue(produto.getValorAPrazo().toString());
				i++;
				
				
			}
		}
		
		try (FileOutputStream arquivo = new FileOutputStream("produtos.xlsx")){
			workbook.write(arquivo);
			arquivo.flush();
			
			JOptionPane.showMessageDialog(null, "Planilha criada com Sucesso");
			
			
	    } catch (Exception e) {
	    	JOptionPane.showMessageDialog(null, "ERRO ao gravar o arquivo " + e.getMessage());
	        System.out.println("Erro ao criar a planilha: " + e.getMessage());
	    } finally {
	        try {
	            if (workbook != null) {
	                workbook.close();
	            }
	        } catch (java.io.IOException e) {
	            System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
	        }
	    }
	}
	
}
