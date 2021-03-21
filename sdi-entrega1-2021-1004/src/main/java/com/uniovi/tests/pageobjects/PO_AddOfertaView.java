package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_AddOfertaView extends PO_NavView {
	static public void fillForm(WebDriver driver, String a, String b, String c) {
		WebElement titulo = driver.findElement(By.name("titulo"));
		titulo.click();
		titulo.clear();
		titulo.sendKeys(a);
		WebElement detalle = driver.findElement(By.name("detalle"));
		detalle.click();
		detalle.clear();
		detalle.sendKeys(b);
		WebElement precio = driver.findElement(By.name("precio"));
		precio.click();
		precio.clear();
		precio.sendKeys(c);
		// Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void fillValid(WebDriver driver) {
		PO_AddOfertaView.fillForm(driver, "Aparato raro", "No funciona", "27");
	}

	static public void fillEmptyTitulo(WebDriver driver) {
		PO_AddOfertaView.fillForm(driver, "", "Josefo", "10");
	}

}