/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.udc.apriori;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author udc
 */
public class AprioriTest {

    public AprioriTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of resolver1 method, of class Apriori.
     */
    @Test
    public void testResolver() throws IOException
    {
        

        System.out.println("resolver");
        Apriori instance = new Apriori( (double) 0.5, (double) 0.8, "test-transacciones.yml", true);
        Map expResult = new HashMap();
        expResult.put("1", (double) 2.0);
        expResult.put("2", (double) 3.0);
        expResult.put("3", (double) 3.0);
        expResult.put("5", (double) 3.0);
        expResult.put("3, 1", (double) 2.0 );
        expResult.put("3, 2", (double) 2.0 );
        expResult.put("2, 5", (double) 3.0 );
        expResult.put("3, 5", (double) 2.0 );
        expResult.put("3, 2, 5", (double) 2.0 );
        Map result = instance.resolver1();
        assertEquals(expResult, result);

        //System.out.println( result );

        //obteniene las reglas fuertes
        System.out.println("\t Intenta obtener las reglas fuertes");
        //Map todosFrec = instance.getTodosItemsFrecuentes();
        ArrayList<Reglas> listReglasResult = new ArrayList<Reglas>();
        //String strExpResult = "";

        
        listReglasResult = instance.transacciones.getRelacionFuerte(result);
        assertEquals( "se obtienen 9 reglas", 14, listReglasResult.size());

        //compara los objetos usar debug
        //strExpResult =" [3] => [ 2], [ 2] => [3], [3] => [ 1], [ 1] => [3], [3] => [ 5], [ 5] => [3], [2] => [ 5], [ 5] => [2], [3] => [ 2,  5], [ 2] => [3,  5], [ 5] => [3,  2], [3,  2] => [ 5], [3,  5] => [ 2], [ 2,  5] => [3]";
        //assertEquals(strExpResult, listReglasResult);
    }

    /**
     * Test of resolver1 method, of class Apriori.
     */
    @Test
    public void testResolver1() throws IOException, FileNotFoundException, ClassNotFoundException
    {


        //se resuelve por primera vez
        System.out.println("resolver");
        Apriori instance = new Apriori( (double) 0.5, (double) 0.8, "test-transacciones.yml", true);
        Map expResult = new HashMap();
        expResult.put("1", (double) 2.0);
        expResult.put("2", (double) 3.0);
        expResult.put("3", (double) 3.0);
        expResult.put("5", (double) 3.0);
        expResult.put("3, 1", (double) 2.0 );
        expResult.put("3, 2", (double) 2.0 );
        expResult.put("2, 5", (double) 3.0 );
        expResult.put("3, 5", (double) 2.0 );
        expResult.put("3, 2, 5", (double) 2.0 );
        Map result = instance.resolver1();
        assertEquals(expResult, result);

        instance = new Apriori( (double) 0.5, (double) 0.8, "test-transacciones-02.yml", true);
        result = instance.resolver2();
        expResult = new HashMap();
        expResult.put("1", (double) 4.0);
        expResult.put("2", (double) 5.0);
        expResult.put("3", (double) 6.0);
        expResult.put("5", (double) 5.0);
        expResult.put("3, 1", (double) 4.0 );
        expResult.put("2, 5", (double) 5.0 );
        assertEquals(expResult, result);
        //System.out.println( "debug" );

    }

    /**
     * Test of setConfianza method, of class Apriori.
     */
    @Test
    public void testSetConfianza()
    {
        System.out.println("setConfianza");
        double confianza = 0.0;
        Apriori instance = new Apriori();
        instance.setConfianza(confianza);
    }

    /**
     * Test of getConfianza method, of class Apriori.
     */
    @Test
    public void testGetConfianza()
    {
        System.out.println("getConfianza");
        Apriori instance = new Apriori();
        double expResult = 0.0;
        double result = instance.getConfianza();
        assertEquals(expResult, result, 0.0);
    }




    /**
     * Test of setSoporte method, of class Apriori.
     */
    @Test
    public void testSetSoporte()
    {
        System.out.println("setSoporte");
        double soporte = 0.0;
        Apriori instance = new Apriori();
        instance.setSoporte(soporte);
    }

    /**
     * Test of getSoporte method, of class Apriori.
     */
    @Test
    public void testGetSoporte()
    {
        System.out.println("getSoporte");
        Apriori instance = new Apriori();
        double expResult = 0.0;
        double result = instance.getSoporte();
        assertEquals(expResult, result, 0.0);
    }

}