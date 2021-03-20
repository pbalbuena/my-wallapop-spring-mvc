package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyWallapopTests {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "D:\\Universidad\\3\\SDI\\2021\\lab5\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";
	// En MACOSX (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas):
	// static String PathFirefox65 =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	} /* Resto del código de la clase */

	// Antes de cada prueba se navega al URL home de la aplicación
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// [Prueba1] Registro de Usuario con datos válidos.
	@Test
	public void PR01() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillValid(driver);
		PO_View.checkElement(driver, "text", "Usuario Autenticado");
	}

	// [Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos).
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillEmptyEmail(driver);
		PO_View.checkElementNotPresent(driver, "Usuario Autenticado");
		PO_RegisterView.fillEmptyName(driver);
		PO_View.checkElementNotPresent(driver, "Usuario Autenticado");
		PO_RegisterView.fillEmptyLastName(driver);
		PO_View.checkElementNotPresent(driver, "Usuario Autenticado");
	}
	// [[Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida).

	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillPasswordNotRepeated(driver);
		PO_View.checkElementNotPresent(driver, "Usuario Autenticado");
	}

	// [Prueba4] Registro de Usuario con datos inválidos (email existente).

	@Test
	public void PR04() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillExistingEmail(driver);
		PO_View.checkElementNotPresent(driver, "Usuario Autenticado");
	}

	// [Prueba5] Inicio de sesión con datos válidos (administrador).

	@Test
	public void PR05() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarAdmin(driver);
		PO_View.checkElement(driver, "text", "Usuario Autenticado");
	}

	// [Prueba6] Inicio de sesión con datos válidos (usuario estándar).

	@Test
	public void PR06() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarUsuario(driver);
		PO_View.checkElement(driver, "text", "Usuario Autenticado");
	}

	// [Prueba7] Inicio de sesión con datos inválidos (usuario estándar, campo email
	// y contraseña vacíos).

	@Test
	public void PR07() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarEmpty(driver);
		PO_View.checkElementNotPresent(driver, "Usuario Autenticado");
	}

	// [Prueba8] Inicio de sesión con datos válidos (usuario estándar, email
	// existente, pero contraseña incorrecta).

	@Test
	public void PR08() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarPasswordWrong(driver);
		PO_View.checkElementNotPresent(driver, "Usuario Autenticado");
	}

	// [Prueba9] Inicio de sesión con datos inválidos (usuario estándar, email no
	// existente en la aplicación).

	@Test
	public void PR09() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarNotExistingEmail(driver);
		PO_View.checkElementNotPresent(driver, "Usuario Autenticado");
	}

	// [Prueba10] Hacer click en la opción de salir de sesión y comprobar que se
	// redirige a la página de inicio de sesión (Login).

	@Test
	public void PR10() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarAdmin(driver);
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_View.checkElementNotPresent(driver, "Desconectar");
		String url = driver.getCurrentUrl();
		assertTrue(url.equals("http://localhost:8090/login?logout"));
	}

	// [Prueba11] Comprobar que el botón cerrar sesión no está visible si el usuario
	// no está autenticado.

	@Test
	public void PR11() {
		PO_View.checkElementNotPresent(driver, "Desconectar");
	}

	// [Prueba12] Mostrar el listado de usuarios y comprobar que se muestran todos
	// los que existen en el sistema.

	@Test
	public void PR12() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarAdmin(driver);
		driver.navigate().to("http://localhost:8090/usuario/list");
		int size = PO_View.getSizeOfPageable(driver);
		//los que hay + el que se añade en la prueba 1 y sin el admin
		assertTrue(size == 6);
		
	}

	// [Prueba13] Ir a la lista de usuarios, borrar el primer usuario de la lista,
	// comprobar que la lista se actualiza y que el usuario desaparece.

	@Test
	public void PR13() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarAdmin(driver);
		driver.navigate().to("http://localhost:8090/usuario/list");
		By enlace = By.xpath("/html/body/div/div[1]/table/tbody/tr[1]/td[6]/a");
		driver.findElement(enlace).click();
		int size = PO_View.getSizeOfPageable(driver);
		assertTrue(size==5);
	}

	// [Prueba14] Ir a la lista de usuarios, borrar el último usuario de la lista,
	// comprobar que la lista se actualiza y que el usuario desaparece.

	@Test
	public void PR14() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarAdmin(driver);
		driver.navigate().to("http://localhost:8090/usuario/list");
		By enlace = By.xpath("/html/body/div/div[1]/table/tbody/tr[2]/td[6]/a");
		driver.findElement(enlace).click();
		int size = PO_View.getSizeOfPageable(driver);
		assertTrue(size==4);
	}

	// [Prueba15] Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la
	// lista se actualiza y que los usuarios desaparecen.

	@Test
	public void PR15() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.entrarAdmin(driver);
		driver.navigate().to("http://localhost:8090/usuario/list");
		By enlace = By.xpath("/html/body/div/div[1]/table/tbody/tr[1]/td[6]/a");
		driver.findElement(enlace).click();
		int size = PO_View.getSizeOfPageable(driver);
		assertTrue(size==3);
		driver.findElement(enlace).click();
		size = PO_View.getSizeOfPageable(driver);
		assertTrue(size==2);
		driver.findElement(enlace).click();
		size = PO_View.getSizeOfPageable(driver);
		assertTrue(size==1);
	}

	// [Prueba16] Ir al formulario de alta de oferta, rellenarla con datos válidos y
	// pulsar el botón Submit. Comprobar que la oferta sale en el listado de ofertas
	// de dicho usuario.

	@Test
	public void PR16() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba17] Ir al formulario de alta de oferta, rellenarla con datos inválidos
	// (campo título vacío) y pulsar el botón Submit. Comprobar que se muestra el
	// mensaje de campo obligatorio.

	@Test
	public void PR17() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba18] Mostrar el listado de ofertas para dicho usuario y comprobar que
	// se muestran todas los que existen para este usuario.

	@Test
	public void PR18() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba19] Ir a la lista de ofertas, borrar la primera oferta de la lista,
	// comprobar que la lista se actualiza y que la oferta desaparece.

	@Test
	public void PR19() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba20] Ir a la lista de ofertas, borrar la última oferta de la lista,
	// comprobar que la lista se actualiza y que la oferta desaparece.

	@Test
	public void PR20() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba21] Hacer una búsqueda con el campo vacío y comprobar que se muestra
	// la página que corresponde con el listado de las ofertas existentes en el
	// sistema

	@Test
	public void PR21() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba22] Hacer una búsqueda escribiendo en el campo un texto que no exista
	// y comprobar que se muestra la página que corresponde, con la lista de ofertas
	// vacía.

	@Test
	public void PR22() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba23] Sobre una búsqueda determinada (a elección del desarrollador),
	// comprar una oferta que deja un saldo positivo en el contador del comprador.
	// Comprobar que el contador se actualiza correctamente en la vista del
	// comprador.

	@Test
	public void PR23() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba24] Sobre una búsqueda determinada (a elección del desarrollador),
	// comprar una oferta que deja un saldo 0 en el contador del comprador.
	// Comprobar que el contador se actualiza correctamente en la vista del
	// comprador.

	@Test
	public void PR24() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba25] Sobre una búsqueda determinada (a elección del desarrollador),
	// intentar comprar una oferta que esté por encima de saldo disponible del
	// comprador. Y comprobar que se muestra el mensaje de saldo no suficiente.

	@Test
	public void PR25() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba26] Ir a la opción de ofertas compradas del usuario y mostrar la
	// lista. Comprobar que aparecen las ofertas que deben aparecer.

	@Test
	public void PR26() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba27] Visualizar al menos cuatro páginas haciendo el cambio
	// español/inglés/español (comprobando que algunas de las etiquetas cambian al
	// idioma correspondiente). Página principal/Opciones principales de
	// usuario/Listado de usuarios /Vista de alta de oferta.

	@Test
	public void PR27() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba28] Intentar acceder sin estar autenticado a la opción de listado de
	// usuarios del administrador. Se deberá volver al formulario de login.

	@Test
	public void PR28() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba29] Intentar acceder sin estar autenticado a la opción de listado de
	// ofertas propias de un usuario estándar. Se deberá volver al formulario de
	// login.

	@Test
	public void PR29() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba30] Estando autenticado como usuario estándar intentar acceder a la
	// opción de listado de usuarios del administrador. Se deberá indicar un mensaje
	// de acción prohibida.

	@Test
	public void PR30() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba31] Sobre una búsqueda determinada de ofertas (a elección de
	// desarrollador), enviar un mensaje a una oferta concreta. Se abriría dicha
	// conversación por primera vez. Comprobar que el mensaje aparece en el listado
	// de mensajes.

	@Test
	public void PR31() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba32] Sobre el listado de conversaciones enviar un mensaje a una
	// conversación ya abierta. Comprobar que el mensaje aparece en la lista de
	// mensajes.

	@Test
	public void PR32() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba33] Mostrar el listado de conversaciones ya abiertas. Comprobar que el
	// listado contiene las conversaciones que deben ser.

	@Test
	public void PR33() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba34] Sobre el listado de conversaciones ya abiertas. Pinchar el enlace
	// Eliminar de la primera y comprobar que el listado se actualiza correctamente.

	@Test
	public void PR34() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba35] Sobre el listado de conversaciones ya abiertas, pulsar el enlace
	// Eliminar de la última y comprobar que el listado se actualiza correctamente.

	@Test
	public void PR35() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba36] Al crear una oferta marcar dicha oferta como destacada y a
	// continuación comprobar: i) que aparece en el listado de ofertas destacadas
	// para los usuarios y que el saldo del usuario se actualiza adecuadamente en la
	// vista del ofertante (-20).

	@Test
	public void PR36() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba37] Sobre el listado de ofertas de un usuario con menos de 20 euros de
	// saldo, pinchar en el enlace Destacada y a continuación comprobar: que aparece
	// en el listado de ofertas destacadas para los usuarios y que el saldo del
	// usuario se actualiza adecuadamente en la vista del ofertante (-20).

	@Test
	public void PR37() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// [Prueba38] Sobre el listado de ofertas de un usuario con menos de 20 euros de
	// saldo, pinchar en el enlace Destacada y a continuación comprobar que se
	// muestra el mensaje de saldo no suficiente.

	@Test
	public void PR38() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}
}
