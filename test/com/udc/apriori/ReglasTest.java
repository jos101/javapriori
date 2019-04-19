/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.udc.apriori;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author José Luis
 */
public class ReglasTest {

    public ReglasTest() {
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
     * Test of getAntedente method, of class Reglas.
     */
    @Test
    public void testGetAntedente()
    {
        System.out.println("getAntedente");
        String[] arrStr = {"a", "b"};
        Reglas instance = new Reglas(arrStr, "c".split(""));
        String[] expResult = {"a", "b"};
        String[] result = instance.getAntedente();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStrAntecedente method, of class Reglas.
     */
    @Test
    public void testGetStrAntecedente()
    {
        System.out.println("getStrAntecedente");
        String[] arrStr = {"a", "b"};
        Reglas instance = new Reglas(arrStr, "c".split(""));
        String expResult = "a,b";
        String result = instance.getStrAntecedente();
        assertEquals("varios elementos",expResult, result);

        String[] arrStr2 = {"b"};
        instance = new Reglas(arrStr2, "c".split(""));
        expResult = "b";
        result = instance.getStrAntecedente();
        assertEquals("Un solo elementos",expResult, result);
    }

    /**
     * Test of getPresedente method, of class Reglas.
     */
    @Test
    public void testGetPresedente()
    {
        System.out.println("getPresedente");
        String[] arrStr = {"a", "b"};
        String[] pres = {"c"};
        Reglas instance = new Reglas(arrStr, pres);
        String[] expResult = {"c"};
        String[] result = instance.getConsecuente();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getStrConsecuente method, of class Reglas.
     */
    @Test
    public void getStrConsecuente()
    {
        System.out.println("getStrConsecuente");
        String[] arrStr = {"a", "b"};
        String[] pres = {"c"};
        Reglas instance = new Reglas(arrStr, pres);
        String expResult = "c";
        String result = instance.getStrConsecuente();
        assertEquals("Varios elementos", expResult, result);

        String[] arrStr2 = {"a", "b"};
        String[] pres2 = {"c", "d", "f"};
        instance = new Reglas(arrStr2, pres2);
        expResult = "c,d,f";
        result = instance.getStrConsecuente();
        assertEquals("Varios elementos", expResult, result);
    }

    /**
     * Test of setAntedente method, of class Reglas.
     */
    @Test
    public void testSetAntedente()
    {
        System.out.println("setAntedente");
        String[] antedente = {"c"};
        String[] arrStr = {"a", "b"};
        Reglas instance = new Reglas(arrStr, antedente);
        instance.setAntedente(antedente);

        assertArrayEquals(antedente, instance.getAntedente());
    }

    /**
     * Test of setPresedente method, of class Reglas.
     */
    @Test
    public void testSetPresedente()
    {
        System.out.println("setPresedente");
        String presedente[] = {"d"};
        String[] arrStr = {"a", "b"};
        Reglas instance = new Reglas(arrStr, "c".split(""));
        instance.setConsecuente(presedente);

        assertArrayEquals(presedente, instance.getConsecuente());
    }

    /**
     * Test of toString method, of class Reglas.
     */
    @Test
    public void testToString()
    {
        System.out.println("toString");
        String[] arrStr = {"1", "2"};
        String[] pres = {"3"};
        Reglas instance = new Reglas( arrStr, pres);
        String expResult = "[1, 2] => [3]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

     /**
     * Test of toString method, of class Reglas.
     */
    @Test
    public void isFrecuenteTest()
    {
        System.out.println("isFrecuenteTest");
        String[] arrStr = {"1", "2"};
        String[] pres = {"3"};
        Reglas instance = new Reglas( arrStr, pres, (double) 0.8);
        instance.setConfianza( (double) 0.7);

        boolean result= instance.isFrecuente();
        boolean expResult = false;
        assertEquals(expResult, result);

        instance.setConfianza( (double) 0.8);
        result = instance.isFrecuente();
        expResult = true;
        assertEquals(expResult, result);

        instance = new Reglas(arrStr, pres, (double) 0.7, (double) 0.7);
        result = instance.isFrecuente();
        expResult = true;
        assertEquals(expResult, result);

        instance = new Reglas(arrStr, pres, (double) 0.8, (double) 0.7);
        result = instance.isFrecuente();
        expResult = true;
        assertEquals(expResult, result);

        instance = new Reglas(arrStr, pres, (double) 0.5, (double) 0.7);
        result = instance.isFrecuente();
        expResult = false;
        assertEquals(expResult, result);

    }

}