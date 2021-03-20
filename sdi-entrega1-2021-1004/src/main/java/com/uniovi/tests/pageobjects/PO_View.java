package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_View {
	
	protected static PO_Properties p = new PO_Properties("messages");
	protected static int timeout = 2;

	public static int getTimeout() {
		return timeout;
	}

	public static void setTimeout(int timeout) {
		PO_View.timeout = timeout;
	}

	public static PO_Properties getP() {
		return p;
	}

	public static void setP(PO_Properties p) {
		PO_View.p = p;
	}
	
	/**
	 * Espera por la visibilidad de un texto correspondiente a la propiedad key en el idioma locale en la vista actualmente cargandose en driver..
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param key: clave del archivo de propiedades.
	 * @param locale: Retorna el índice correspondient al idioma. 0 p.SPANISH y 1 p.ENGLISH.
	 * @return Se retornará la lista de elementos resultantes de la búsqueda.
	 */
	static public List<WebElement> checkKey(WebDriver driver, String key, int locale) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString(key, locale), getTimeout());
		return elementos;
	}
	/**
	 *  Espera por la visibilidad de un elemento/s en la vista actualmente cargandose en driver..
	 * 
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param type: 
	 * @param text:
	 * @return Se retornará la lista de elementos resultantes de la búsqueda.
	 */
	static public List<WebElement> checkElement(WebDriver driver, String type, String text) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, type, text, getTimeout());
		return elementos;		
	}
	
	static public void checkElementNotPresent(WebDriver driver, String text) {
		SeleniumUtils.textoNoPresentePagina(driver, text);	
	}
	
	static public int getSizeOfPageable(WebDriver driver) {
		int totalSize = 0;
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		List<WebElement> paginas = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Nos vamos a la última página si hay mas de una pagina
		if(paginas.size() == 4) {
			paginas.get(3).click();
		} else {
			
		}
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		//volvemos a cargar la lista de elementos
		List<WebElement> paginas2 = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Obtenemos el valor de la pagina actual
		int penultima = Integer.parseInt(paginas2.get(1).getText());
		int sizeLastPage = elementos.size();
		int sizePag = 3;
		if(paginas.size()==4) {
			totalSize = penultima*sizePag+sizeLastPage;
		} else {
			totalSize = sizeLastPage;
		}
		return totalSize;
	}
	
}
