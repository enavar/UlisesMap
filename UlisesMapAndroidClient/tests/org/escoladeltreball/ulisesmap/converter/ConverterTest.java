package org.escoladeltreball.ulisesmap.converter;

import java.util.ArrayList;

import org.escoladeltreball.ulisesmap.model.Route;
import org.json.JSONException;


import junit.framework.TestCase;


public class ConverterTest extends TestCase {
	
	private String arrayRoutes01;
	private String arrayRoutes02;
	private ArrayList<Route> routes01;
	private ArrayList<Route> routes02;
	
	protected void setUp() throws Exception {
		initRoutes();
		super.setUp();
	}
	
	protected void tearDown() throws Exception {
        super.tearDown();
    }
	
	private void initRoutes() throws JSONException {
		Route r1 = new Route("Llegendes del Born", "Ruta a peu d'unes dues hores.", 4.5f);
		Route r2 = new Route("Secrets del Gòtic", "Ruta a peu d'unes dues hores. Utilitzarem radioguies.", 3.75f);
		Route r3 = new Route("La Guerra Civil", "Punto de encuentro: delante de la fachada del ayuntamiento", 2.4f);
		Route r4 = new Route("Caliente & canalla", "no és circular.", 2.1f);
		Route r5 = new Route("Nocturna & criminal", "Ruta a pie.", 5f);
		Route r6 = new Route("Carlos Ruiz Zafón", "Ruta literaria.", 4.5f);
		Route r7 = new Route("Historias del Raval", "Ruta a pie.", 3.5f);
		Route r8 = new Route("Gràcia, burguesa y revolucionaria", "Punto de encuentro:escaleras de la parroquia Els Josepets", 2.3f);
		Route r9 = new Route("La Catedral del Mar", "Ruta de dues hores", 2.3f);
		routes01 = new ArrayList<Route>();
		routes01.add(r1);
		routes01.add(r2);
		routes01.add(r3);
		routes01.add(r4);
		routes02 = new ArrayList<Route>();
		routes02.add(r5);
		routes02.add(r6);
		routes02.add(r7);
		routes02.add(r8);
		routes02.add(r9);
		arrayRoutes01 = "hola";
		arrayRoutes02 = "hdsfsd";
		System.out.println(arrayRoutes01);
		System.out.println(arrayRoutes02);
	}
	
	public void testConvertStringToRoutes() {
		ArrayList<Route> routes = Converter.convertStringToRoutes(arrayRoutes01);
		assertEquals(routes, routes01);
		routes = Converter.convertStringToRoutes(arrayRoutes02);
		assertEquals(routes, routes02);
	}

}
