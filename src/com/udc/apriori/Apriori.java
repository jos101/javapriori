/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.udc.apriori;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author udc
 */
public class Apriori
{
    private double soporte;
    private double confianza;
    private String URL;
    public Transacciones transacciones;
    private HashMap todosItemsFrecuentes;
    private boolean esYml = false;


    public Apriori()
    {
    }

    /**
     * Inicializa el objeto apriori
     * @param soporte porcentaje comprendido entre 0 y 1
     * @param confianza porcentaje comprendido entre 0 y 1
     * @param URL directorio del archivo
     * @param yml true si es un archivo YAML de lo contrario false
     * @throws IOException
     */
    public Apriori(double soporte, double confianza, String URL, boolean yml) throws IOException
    {
        this.soporte = soporte;
        this.confianza = confianza;
        this.URL = URL;
        this.esYml = yml;

        todosItemsFrecuentes = new HashMap();
        this.transacciones = new Transacciones();
        transacciones.setConfianza(confianza);
        transacciones.setSoporte(soporte);
        if(yml)
            transacciones.agregarYML(URL);
        else
            transacciones.agregarDeArchivo(URL);
    }

    /**
     * Inicializa el objeto Apriori
     * @param soporte porcentaje comprendido entre 0 y 1
     * @param confianza porcentaje comprendido entre 0 y 1
     */
    public Apriori(double soporte, double confianza)
    {
        this.soporte = soporte;
        this.confianza = confianza;
    }

    /**
     * Obtiene los items frecuentes que ha encontrado el objeto
     * @return HashMap con los Items frecuentes
     */
    public HashMap getTodosItemsFrecuentes()
    {
        return todosItemsFrecuentes;
    }

    /**
     * Obtiene los items frecuentes de una transaccion que
     * no se ha resuelto previamente
     * @return items Frecuentes
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Map resolver1() throws FileNotFoundException, IOException
    {
        transacciones.reiniciarTransExcluidas();
        System.out.println("Calcular L1...");
        Map ItemSetL1;
        ItemSetL1 = transacciones.calcularL1();
        System.out.println("Calculado L1");
        Map candidatos = new HashMap();
        Map itemsFrecuentes = new HashMap();
        Object[] obj = ItemSetL1.entrySet().toArray();

        //agrega los items frecuentes L1 a la variable todosItemsFrecuentes
        for (int i = 0; i < obj.length; i++)
        {
            Map.Entry entry = (Map.Entry)obj[i];
            this.todosItemsFrecuentes.put(entry.getKey(), entry.getValue());
        }


         //obtiene todos los items frecuentes
        candidatos = ItemSetL1;
        do
        {
             candidatos = transacciones.GenerarCandidatos(candidatos);
             itemsFrecuentes = transacciones.getItemsFrecuentes(candidatos);
             int tam = itemsFrecuentes.size();

             if(tam > 0)
             {
                 Object[] obj2 =  itemsFrecuentes.entrySet().toArray();

                 for (int i = 0; i < obj2.length; i++)
                 {
                     Map.Entry entry = (Map.Entry) obj2[i];
                     this.todosItemsFrecuentes.put(entry.getKey(), entry.getValue());
                 }
             }

        } while ( itemsFrecuentes.size() >0 );

        //reiniciar transaccioneExcluidas
        transacciones.reiniciarTransExcluidas();
        

        return todosItemsFrecuentes;

    }

    /**
     * Obtiene los items frecuentes de una transaccion que se realizo previamente
     * @param itemsL1
     * @param todosItemsFrecuentesIn
     * @param difTransaccion
     * @return Items Frecuentes
     */
    public Map resolver1( HashMap itemsL1, HashMap todosItemsFrecuentesIn, HashMap difTransaccion)
    {


        //contar items
        //generar candidatos
        //verificar si es frecuente
        //agregar nueva items frecuentes

        Map candidatos = new HashMap();
        Map itemsFrecuentes = new HashMap();

        //agrega los items frecuentes L1 a la variable todosItemsFrecuentes
        Object[] objL1 = itemsL1.entrySet().toArray();
        for (int i = 0; i < objL1.length; i++)
        {
            Map.Entry entry = (Map.Entry)objL1[i];
            this.todosItemsFrecuentes.put(entry.getKey(), entry.getValue());
        }

        //obtiene todos los items frecuentes
        candidatos = itemsL1;
        do
        {
             candidatos = transacciones.GenerarCandidatos(candidatos, todosItemsFrecuentesIn, difTransaccion);
             itemsFrecuentes = transacciones.getItemsFrecuentes(candidatos);
             int tam = itemsFrecuentes.size();

             if(tam > 0)
             {
                 Object[] obj2 =  itemsFrecuentes.entrySet().toArray();
                 int tam2 = obj2.length;

                 for (int i = 0; i < tam2; i++)
                 {
                     Map.Entry entry = (Map.Entry) obj2[i];
                     this.todosItemsFrecuentes.put(entry.getKey(), entry.getValue());
                 }
             }

        } while ( itemsFrecuentes.size() >0 );

        return todosItemsFrecuentes;
    }

    /**
     * resuelve los items frecuentes usando la transacción resuelta previamentes
     * y que se le han añadido nuevos registros
     * @return items frecuentes
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Map resolver2() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        //obtienes los items frecuentes y no frecuentes obtenidos anteriormente
        ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("datos.dll"));
        HashMap itemNoFrecuentesIn = (HashMap)entrada.readObject();
        HashMap todosItemsFrecuentesIn = (HashMap)entrada.readObject();
        int numeroTrans = Integer.parseInt( entrada.readObject().toString() );
        HashMap transaccionIn = (HashMap)entrada.readObject();

        //obtiene los items set L1 luego se verifica que todos esten en todosItemsFrecuentesIn
        Map ItemSetL1;
        ItemSetL1 = transacciones.calcularL1();

        //se verifica que todos los itemSetsL1 esten en todosItemsFrecuentesIn
        Object[] obj1 = ItemSetL1.entrySet().toArray();
        int tam1 = obj1.length;

        boolean estanTodosEnIF = true;// todos los itemSetL1 estan en todosItemsFrecuentesIn
        for (int i = 0; i < tam1; i++)
        {
            Map.Entry obj2 = (Map.Entry) obj1[i];
            String item =  (String) obj2.getKey();
            item = item.trim();

            //recorre los items frecuentes
            Object[] obj3 = todosItemsFrecuentesIn.entrySet().toArray();
            int tam2 = obj3.length;
            int numElemento = 0;//numero de veces del elemento en todosItemsFrecuentesIn
            for (int j = 0; j < tam2; j++)
            {
                Map.Entry obj4 = (Map.Entry) obj3[j];
                String full =  (String) obj4.getKey();
                full = full.trim();
                if( /* esta al final de los elementos de la transaccion */ ( full.indexOf(", " + item) > -1 && ( full.indexOf(", " + item) + (", " + item).length()  ) == full.length() ) || /* esta en el medio de los elementos de la transaccion */( full.indexOf( ", " + item + ", ") > -1 ) || /* es el unico elemento en la transaccion */( full.indexOf( item) == 0 && (full.length() == item.length() ) ) ||/* esta al principio con varios elementos*/ (full.indexOf( item + ", ") == 0 ) )
                //if( /* es el unico elemento en la transaccion */( full.indexOf( item) == 0 && (full.length() == item.length() ) ) )
                {
                    numElemento++;
                }
            }
            if( numElemento == 0 )
            {
                estanTodosEnIF = false;
            }
            
        }

        Map difTransaccion = new HashMap();//transacciones de la diferencia con la que se leyo anteriormente
        //calcula items frecuentes recorriendo una porcion de la transaccion
        if( estanTodosEnIF )
        {
            int actSize = transacciones.transaccion.size();
            if( actSize > numeroTrans )
            {
                //int dif = actSize - numeroTrans;
                //transacciones.transaccion.

                Object[] obj5 = transacciones.transaccion.entrySet().toArray();
                int tam5 = obj5.length;
                //crea la transaccion diferencia
                for (int i = 0; i < tam5; i++)
                {

                    Map.Entry obj6 = (Map.Entry) obj5[i];
                    int key =  Integer.parseInt( obj6.getKey().toString() );
                    String value = (String) obj6.getValue();
                    
                    //if( i >= numeroTrans )
                    if( !transaccionIn.containsKey(key) )
                    {
                        difTransaccion.put(key, value);
                    }
                }
                
            }

            //se resuelve usando resolver2
            todosItemsFrecuentes = (HashMap) resolver1((HashMap) ItemSetL1,todosItemsFrecuentesIn, (HashMap)difTransaccion);
        }
        else //se resuelve usando el metodo anterior
        {
            todosItemsFrecuentes = (HashMap) resolver1();
        }
        
        return todosItemsFrecuentes;
    }

    /**
     * Lee items frecuentes guardados anteriormente
     */
    public HashMap leerItemsFrec() throws IOException, ClassNotFoundException
    {
          ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("datos.dll"));
          //String str=(String)entrada.readObject();
          HashMap result=(HashMap)entrada.readObject();
          System.out.println(result);
          entrada.close();

          return result;
    }

    /**
     * Devuelve las reglas encontradas en las transacciones
     * @return
     */
    public ArrayList<Reglas> getRelacionesFuertes()
    {
        ArrayList<Reglas> rg= this.transacciones.getRelacionFuerte(todosItemsFrecuentes);
        
        return rg;
    }

    /**
     * Cambia el valor de la confianza
     * @param confianza valor entre 0 y 1
     */
    public void setConfianza(double confianza)
    {
        this.confianza = confianza;
    }

    /**
     * Obtiene el valor de la confiaza
     * @return valor emtre cer y uno
     */
    public double getConfianza()
    {
        return confianza;
    }

    /**
     * Cambia el valor del soporte
     * @param soporte Valor entre cero y 1
     */
    public void setSoporte(double soporte)
    {
        this.soporte = soporte;
    }

    /**
     * Cambia el valor del soporte
     * @return valor entre cero y uno
     */
    public double getSoporte()
    {
        return soporte;
    }    
}
