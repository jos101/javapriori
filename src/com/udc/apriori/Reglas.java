/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.udc.apriori;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 * @author udc
 *
 * Representa las relaciones de elementos
 * y sus confianza respectiva
 *
 */
public class Reglas
{
    private String[] antedente;
    private String[] consecuente;
    private double confianza = 0.0;
    private double minConfianza = 0.0;
    private boolean frecuente = false;



    /**
     * Inicializa el objeto
     * @param antedente String[] con los antecedentes
     * @param consecuente String[] con los consecuentes
     * @param confianza
     */
    public Reglas(String[] antedente, String[] consecuente, double confianza)
    {
        this.antedente = antedente;
        this.consecuente = consecuente;
        this.minConfianza = confianza;

        actualizarFrecuente();
    }

    /**
     * Inicializa el objeto
     * @param antedente
     * @param presedente
     * @param confianza la confiaza de la regla
     * @param minConfianza la confianza minima para que una regla sea considerada frecuente
     */
    public Reglas(String[] antedente, String[] presedente, double confianza,  double minConfianza)
    {
        this.antedente = antedente;
        this.consecuente = presedente;
        this.confianza = confianza;
        this.minConfianza = minConfianza;

        actualizarFrecuente();
    }

    /**
     * Inicializa el objeto
     * @param antedente
     * @param presedente
     */
    public Reglas(String[] antedente, String[] presedente)
    {
        this.antedente = antedente;
        this.consecuente = presedente;

        actualizarFrecuente();
    }

    /**
     * Devuelve la confianza
     * @return double valor entre cero y uno
     */
    public double getConfianza()
    {
        return confianza;
    }

    /**
     * Devuelve el valor minimo de la confianza para que la regla sea considerada
     * frecuente
     * @return
     */
    public double getMinConfianza()
    {
        return minConfianza;
    }
    

    /**
     * Cambia el valor de la confianza minima
     * @param minConfianza
     */
    public void setMinConfianza(double minConfianza)
    {
        this.minConfianza = minConfianza;

        actualizarFrecuente();
    }

    /**
     * Cambia el valor de la confianza
     * @param confianza
     */
    public void setConfianza(double confianza)
    {
        this.confianza = confianza;

        actualizarFrecuente();
    }


    /**
     * Define si el objeto es o no frecuente
     */
    private void actualizarFrecuente()
    {
        if( this.confianza >= this.minConfianza)
        {
            frecuente = true;
        }
        else
        {
            frecuente = false;
        }
    }

    /**
     * Devuelve true si la regla supera el minimo de confianza para considerarse
     * una regla fuerte
     * @return
     */
    public boolean isFrecuente()
    {
        return frecuente;
    }



    /**
     * Devuelve los antecedentes de la regla
     * @return String[]
     */
    public String[] getAntedente()
    {
        return antedente;
    }

    /**
     * Devuelve una cadena de los elementos del antecedente
     * de la forma "item1", "item2", "item3"...
     * @return
     */
    public String getStrAntecedente()
    {
        String res = "";//resultado
        String[] arr = antedente;
        for (int i = 0; i < arr.length; i++)
        {
            String str = arr[i];
            str = str.trim();
            res += ""+str+"";
            if( (i+1) < arr.length )
            {
                res += ",";
            }
        }
        return res;
    }

    /**
     * Devuelve los items del consecuente
     * @return String[]
     */
    public String[] getConsecuente()
    {
        return consecuente;
    }

    /**
     * Devuelve una cadena de los elementos del consecuente
     * de la forma "item1", "item2", "item3"...
     * @return
     */
    public String getStrConsecuente()
    {
        String res = "";//resultado
        String[] arr = consecuente;
        for (int i = 0; i < arr.length; i++)
        {
            String str = arr[i];
            str = str.trim();
            res += ""+str+"";
            if( (i+1) < arr.length )
            {
                res += ",";
            }
        }
        return res;
    }

    /**
     * Cambia los items del antecedente
     * @param antedente
     */
    public void setAntedente(String[] antedente)
    {
        this.antedente = antedente;
    }

    /**
     * Cambia los items del consecuente
     * @param cosecuente
     */
    public void setConsecuente(String[] cosecuente)
    {
        this.consecuente = cosecuente;
    }

    /**
     * Muestra los antecedentes y los consecuentes
     * @return
     */
    @Override
    public String toString()
    {
        //return super.toString();
        return Arrays.deepToString(antedente) + " => " + Arrays.deepToString(consecuente);
        
    }

    
}
