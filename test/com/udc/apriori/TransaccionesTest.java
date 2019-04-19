/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.udc.apriori;

import com.esotericsoftware.yamlbeans.YamlException;
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
public class TransaccionesTest {

    Transacciones trans = new Transacciones();

    public TransaccionesTest() {
        trans.agregarTransaccion(100, "1");
        trans.agregarTransaccion(100, "2");
        trans.agregarTransaccion(200, "4");
        trans.agregarTransaccion(300, "4");
        trans.agregarTransaccion(300, "5");
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
     * Test of agregarTransaccion method, of class Transacciones.
     */
    @Test
    public void testAgregarTransaccion()
    {
        System.out.println("agregarTransaccion");
        int id = 0;
        String idarticulo = "0";
        Transacciones instance = new Transacciones();
        //instance.agregarTransaccion(id, idarticulo);
    }

    /**
     * Test of agregarYML method, of class Transacciones.
     */
    @Test
    public void agregarYML() throws YamlException, IOException
    {
        System.out.println("agregarYML");
        Transacciones trans2 = new Transacciones();

        trans2.agregarYML("test-transacciones.yml");
        Map result = trans2.getTransacion();
        
        Map expResult = new HashMap();
        expResult.put(100, "1, 3, 4");
        expResult.put(200, "2, 3, 5");
        expResult.put(300, "1, 2, 3, 5");
        expResult.put(400, "2, 5");

        assertEquals(expResult, result);
        
    }

    /**
     * Test of getTransacion method, of class Transacciones.
     */
    @Test
    public void testGetTransacion()
    {
        System.out.println("getTransacion");
        Map expResult = new HashMap();
        expResult.put(100, "1, 2");
        expResult.put(200, "4");
        expResult.put(300, "4, 5");


       Map result = trans.getTransacion();
       assertEquals(expResult, result);

    }

    /**
     * Test of calcularL1 method, of class Transacciones.
     */
    @Test
    public void calcularL1()
    {
        System.out.println("calcularL1");
        Map expResult = new HashMap();
        expResult.put("1", (double) 1 );
        expResult.put("2", (double) 1 );
        expResult.put("4", (double) 2 );
        expResult.put("5", (double) 1 );

        Map result = trans.calcularL1();
        assertEquals(expResult, result);


        /*----------------items que no superan el soporte--------------------*/
        Transacciones trans2 = new Transacciones();

        //trasaccion 1
        trans2.agregarTransaccion(100, "1");
        trans2.agregarTransaccion(100, "2");
        trans2.agregarTransaccion(100, "3");
        trans2.agregarTransaccion(100, "4");
        
        //transaccion 2
        trans2.agregarTransaccion(200, "5");
        trans2.agregarTransaccion(200, "6");

        //transaccion 3
        trans2.agregarTransaccion(300, "7");
        trans2.agregarTransaccion(300, "8");

        //transaccion 4
        trans2.agregarTransaccion(400, "2");
        trans2.agregarTransaccion(400, "4");
        
        //transaccion 5
        trans2.agregarTransaccion(500, "3");
        trans2.agregarTransaccion(500, "1");
        
        //transaccion 6
        trans2.agregarTransaccion(600, "4");
        trans2.agregarTransaccion(600, "2");
        
        //probar que los items 5,6 y 7 no aparecen porque no ser frecuentes
        //con un soporte de 20%.
        expResult = new HashMap();
        expResult.put("1", (double) 2 );
        expResult.put("2", (double) 3 );
        expResult.put("3", (double) 2 );
        expResult.put("4", (double) 3 );

        result = trans2.calcularL1();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of GenerarCandidatos method, of class Transacciones.
     */
    @Test
    public void GenerarCandidatos() throws IOException
    {
        System.out.println("GenerarCandidatos");
        Map expResult = new HashMap();
        expResult.put("1", (double) 1 );
        expResult.put("2", (double) 1 );
        expResult.put("4", (double) 2 );
        expResult.put("5", (double) 1 );

        Map result = trans.calcularL1();
        assertEquals(expResult, result);


        result = trans.GenerarCandidatos(result);
        expResult = new HashMap();
        expResult.put("5, 4", (double) 1 );
        expResult.put("1, 5", (double) 0 );
        expResult.put("2, 1", (double) 1 );
        expResult.put("2, 5", (double) 0 );
        expResult.put("2, 4", (double) 0 );
        expResult.put("1, 4", (double) 0 );
        assertEquals("soporte al 20%", expResult, result);
        //System.out.println(result);

        //----------------crea otra transaccion paragenerar
        //candidatos de 2itemset con soporte de 50%---------
        Transacciones trans2 = new Transacciones();
        trans2.soporte = 0.5;

        trans2.agregarYML("test-transacciones.yml");
        result = trans2.getTransacion();

        expResult = new HashMap();
        expResult.put(100, "1, 3, 4");
        expResult.put(200, "2, 3, 5");
        expResult.put(300, "1, 2, 3, 5");
        expResult.put(400, "2, 5");
        assertEquals("soporte al 50%", expResult, result);

        //verifica a L1
        expResult = new HashMap();
        expResult.put("1", (double) 2.0);
        expResult.put("2", (double) 3.0);
        expResult.put("3", (double) 3.0);
        expResult.put("5", (double) 3.0);
        result = trans2.calcularL1();
        assertEquals(expResult, result);
        
        result = trans2.GenerarCandidatos(result);
        expResult = new HashMap();
        expResult.put("2, 1", (double) 1.0 );
        expResult.put("3, 1", (double) 2.0 );
        expResult.put("1, 5", (double) 1.0 );
        expResult.put("3, 2", (double) 2.0 );
        expResult.put("2, 5", (double) 3.0 );
        expResult.put("3, 5", (double) 2.0 );
        assertEquals("soporte al 50%", expResult, result);
        //System.out.println(result);
        
        System.out.println("\t Obtiene items frecuentes");
        expResult = new HashMap();
        expResult.put("3, 1", (double) 2.0 );
        expResult.put("3, 2", (double) 2.0 );
        expResult.put("2, 5", (double) 3.0 );
        expResult.put("3, 5", (double) 2.0 );
        result = trans2.getItemsFrecuentes(result);
        assertEquals("soporte al 50%", expResult, result);
        //System.out.println("\t" + result);

        System.out.println("\t Generar candidatos de 3-itemset");
        expResult = new HashMap();
        expResult.put("3, 1, 5", (double) 1.0 );
        expResult.put("3, 2, 1", (double) 1.0 );
        expResult.put("3, 2, 5", (double) 2.0 );
        result = trans2.GenerarCandidatos(result);
        assertEquals("soporte al 50%", expResult, result);
        //System.out.println("\t" + result);

        System.out.println("\t Obtiene items frecuentes");
        expResult = new HashMap();
        expResult.put("3, 2, 5", (double) 2.0 );
        result = trans2.getItemsFrecuentes(result);
        assertEquals("soporte al 50%", expResult, result);
        //System.out.println("\t" + result);

        System.out.println("\t Intenta generar candidatos de 4-itemset");
        expResult = new HashMap();
        result = trans2.GenerarCandidatos(result);
        assertEquals("soporte al 50%, no se obtiene candidatos de 4-itemset", expResult, result);
        //System.out.println("\t" + result);

        System.out.println("\t Intenta obtener items frecuentes");
        expResult = new HashMap();
        result = trans2.getItemsFrecuentes(result);
        assertEquals("soporte al 50%, no se obtienen items frecuentes", expResult, result);
        
        
        
    }

    /**
     * Test of getSoporte method, of class Transacciones.
     */
    @Test
    public void getSoporte()
    {
        System.out.println("GetSoporte");
        Transacciones trans2 = new Transacciones();

        //trasaccion 1
        trans2.agregarTransaccion(100, "1");
        trans2.agregarTransaccion(100, "2");
        trans2.agregarTransaccion(100, "3");
        trans2.agregarTransaccion(100, "4");

        //transaccion 2
        trans2.agregarTransaccion(200, "5");
        trans2.agregarTransaccion(200, "6");
        trans2.agregarTransaccion(200, "1");
        trans2.agregarTransaccion(200, "2");

        //transaccion 3
        trans2.agregarTransaccion(300, "1");
        trans2.agregarTransaccion(300, "2");
        trans2.agregarTransaccion(300, "7");
        trans2.agregarTransaccion(300, "8");

        //transaccion 4
        trans2.agregarTransaccion(400, "2");
        trans2.agregarTransaccion(400, "4");

        //transaccion 5
        trans2.agregarTransaccion(500, "3");
        trans2.agregarTransaccion(500, "1");

        //transaccion 6
        trans2.agregarTransaccion(600, "1");
        trans2.agregarTransaccion(600, "4");
        trans2.agregarTransaccion(600, "2");

        double Result = trans2.getSoporte("1, 2");
        assertEquals(4.0, Result, 0);


        Result = trans2.getSoporte("2, 3");
        assertEquals(1.0, Result, 0);

        Result = trans2.getSoporte("4, 1");
        assertEquals(2.0, Result, 0);

        Result = trans2.getSoporte("1, 5");
        assertEquals(1.0, Result, 0);

        Result = trans2.getSoporte("1");
        assertEquals(5.0, Result, 0);
       // System.out.println(Result);
    }


    /**
     * Test of calcularCombinaciones method, of class Transacciones.
     */
    @Test
    public void calcularCombinaciones()
    {
        System.out.println("calcularCombinaciones");
        String[] arrStr = {"a", "b", "c", "d"};
        ArrayList<String[]> result;
        result = trans.calcularCombinaciones(arrStr, 2);

        ArrayList<String[]> expResult = new ArrayList<String[]>();
        String[] arr1 = {"a", "b"};
        expResult.add( arr1 );
        String[] arr2 = {"a", "c"};
        expResult.add( arr2 );
        String[] arr3 = {"a", "d"};
        expResult.add( arr3 );
        String[] arr4 = {"b", "c"};
        expResult.add( arr4 );
        String[] arr5 = {"b", "d"};
        expResult.add( arr5 );
        String[] arr6 = {"c", "d"};
        expResult.add( arr6 );

        assertEquals(6, result.size());
        for (int i = 0; i < 6; i++)
        {
            String[] arrStr2 = result.get(i);
            assertArrayEquals(expResult.get(i), arrStr2);
        }
        


        //------------------------------------------------------------------
        
        result = trans.calcularCombinaciones(arrStr, 3);
        expResult.clear();
        arr1 = new String[3];
        arr1[0] = "a";
        arr1[1] = "b";
        arr1[2] = "c";
        expResult.add(arr1);
        arr2 = new String[3];
        arr2[0] = "a";
        arr2[1] = "b";
        arr2[2] = "d";
        expResult.add(arr2);
        arr3 = new String[3];
        arr3[0] = "a";
        arr3[1] = "c";
        arr3[2] = "d";
        expResult.add(arr3);
        arr4 = new String[3];
        arr4[0] = "b";
        arr4[1] = "c";
        arr4[2] = "d";
        expResult.add(arr4);
        assertEquals(4, result.size());


        
        for (int i = 0; i < 4; i++)
        {
            String[] arrStr2 = result.get(i);
            String[] expRe = expResult.get(i);
            assertArrayEquals( expRe, arrStr2);
        }      


        //------------------------------------------------------------------

        result = trans.calcularCombinaciones(arrStr, 4);
        assertEquals(1, result.size());

        expResult.clear();
        arr1 = new String[4];
        arr1[0] = "a";
        arr1[1] = "b";
        arr1[2] = "c";
        arr1[3] = "d";
        expResult.add(arr1);

        String[] expRe = expResult.get(0);
        assertArrayEquals( arr1, expRe);



        //--------------------------------------------------
        
        result = trans.calcularCombinaciones(arrStr, 1);
        expResult.clear();
        //arr1 = new String[1];
        //arr1[0] = "a";
        expResult.add( new String[]{"a"});
        //arr2 = new String[1];
        //arr2[0] = "b";
        expResult.add( new String[] {"b"});
        //arr3 = new String[1];
        //arr3[0] = "c";
        expResult.add( new String[] {"c"} );
        //arr4 = new String[1];
        //arr4[0] = "d";
        expResult.add( new String[] {"d"} );
        assertEquals(4, result.size());
        String debug = "";

        for (int i = 0; i < 4; i++)
        {
            String[] arrStr2 = result.get(i);
            expRe = expResult.get(i);
            assertArrayEquals( expRe, arrStr2);
        }


        //------------------------------------------------------------
        String[] arrPrueba2 = {"a", "b", "c", "d", "e", "f"};

        result = trans.calcularCombinaciones(arrPrueba2, 3);
        assertEquals(20, result.size());//20 combinaciones esto es una locura
        debug = "";
        
    }

    
    /**
     * Test of calcularCombinaciones method, of class Transacciones.
     */
    @Test
    public void getArraySustraccionTest()
    {
        String[] arrMinuendo = {"a", "b", "c", "d"};
        String[] arrSustraendo = {"a", "b"};
        String[] ExpResult = {"c", "d"};

        String[] result= trans.getArraySustraccion(arrMinuendo, arrSustraendo);
        assertArrayEquals(ExpResult, result);


        String[] arrMinuendo2 = {"a", "b", "c", "d", "e"};
        String[] arrSustraendo2 = {"d", "b"};
        String[] ExpResult2 = {"a", "c", "e"};
        result= trans.getArraySustraccion(arrMinuendo2, arrSustraendo2);
        assertArrayEquals(ExpResult2, result);
        
        
    }


}