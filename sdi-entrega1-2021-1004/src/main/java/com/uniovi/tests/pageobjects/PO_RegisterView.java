package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_RegisterView extends PO_NavView {
	static public void fillForm(WebDriver driver, String dnip, String namep, String lastnamep, String passwordp,
			String passwordconfp) {
		WebElement dni = driver.findElement(By.name("email"));
		dni.click();
		dni.clear();
		dni.sendKeys(dnip);
		WebElement name = driver.findElement(By.name("name"));
		name.click();
		name.clear();
		name.sendKeys(namep);
		WebElement lastname = driver.findElement(By.name("lastName"));
		lastname.click();
		lastname.clear();
		lastname.sendKeys(lastnamep);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		WebElement passwordConfirm = driver.findElement(By.name("passwordConfirm"));
		passwordConfirm.click();
		passwordConfirm.clear();
		passwordConfirm.sendKeys(passwordconfp);
		// Pulsar el boton de Alta.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void fillValid(WebDriver driver) {
		PO_RegisterView.fillForm(driver, "email1@gmail.com", "Josefo", "Perez", "77777", "77777");
	}

	static public void fillEmptyEmail(WebDriver driver) {
		PO_RegisterView.fillForm(driver, "", "Josefo", "Perez", "77777", "77777");
	}

	static public void fillEmptyName(WebDriver driver) {
		PO_RegisterView.fillForm(driver, "email1@gmail.com", "", "Perez", "77777", "77777");
	}

	static public void fillEmptyLastName(WebDriver driver) {
		PO_RegisterView.fillForm(driver, "email1@gmail.com", "Josefo", "", "77777", "77777");
	}

	static public void fillPasswordNotRepeated(WebDriver driver) {
		PO_RegisterView.fillForm(driver, "email1@gmail.com", "Josefo", "Perez", "77777", "7777");
	}

	static public void fillExistingEmail(WebDriver driver) {
		PO_RegisterView.fillForm(driver, "uo111111@uniovi.es", "Josefo", "Perez", "77777", "77777");
	}

}