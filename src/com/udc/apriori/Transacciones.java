/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.udc.apriori;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author udc
 */
public class Transacciones {

    public Map transaccion = new HashMap();
    public ArrayList<Integer> numeroTransacciones;//variable para verificar que items pertence a una transaccion en el momento de leer un archivo yml
    public ArrayList<Integer> transaccioneExcluidas = new ArrayList<Integer>();//trasacciones excuidas para verificar el soporte
    public Map todosItemsFrecuentes = new HashMap();
    public Map itemsNoFrecuentes = new HashMap();
    public double soporte = 0.2;
    public double confianza = 0.8;
    public boolean esYml = true;
    String fullTrans = "";//guarda todas las transacciones
    public Map todosL1 = new HashMap();

    public String[] combinaciones;
    ArrayList<String[]> resCombinaciones;
    
    public Transacciones()
    {
        numeroTransacciones = new ArrayList<Integer>();
    }

    public void setSoporte(double soporte)
    {
        this.soporte = soporte;
    }

    public void setConfianza(double confianza)
    {
        this.confianza = confianza;
    }

    /**
     * elimina todos los elemento de la variable transaccioneExcluidas
     * @return true si se elimino los elementos
     */
    public void reiniciarTransExcluidas()
    {
        transaccioneExcluidas.clear();
    }

    /**
     * Agrega las transacciones que se encuentran en un archivo YML
     * @param urlArchivo Ubicacion archivo YML a deserealizar
     * @throws YamlException
     */
    public void agregarYML( String urlArchivo) throws YamlException, IOException
    {
        transaccion.clear();
        numeroTransacciones.clear();
        YamlReader reader;
        try
        {
            reader = new YamlReader(new FileReader(urlArchivo));
            while (true) 
            {
                    Map contact = (Map) reader.read();
                    if (contact == null) break;

                    agregarTransaccion( Integer.parseInt( contact.get("tid").toString() ) , (String)contact.get("item"));
            }
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(Transacciones.class.getName()).log(Level.SEVERE, null, ex);
        }
         

    }

    /**
     * Agrega las transacciones en memoria
     * @param urlArchivo
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void agregarDeArchivo( String urlArchivo) throws FileNotFoundException, IOException
    {
        esYml = false;
        transaccion.clear();
        numeroTransacciones.clear();
        
        File archivo = new File (urlArchivo);
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);

        String linea;
        int id = 1;
        while((linea = br.readLine())!=null)
        {
            //System.out.println(linea);
            numeroTransacciones.add(id);
            linea = linea.trim();
            linea = linea.replace(" ", ", ");
            transaccion.put(id, linea );
            fullTrans = fullTrans.concat("\n"+linea);
            //System.out.println(linea);
            //System.out.println(id);
            id ++;
        }


    }

    /**
     * Agrega las transacciones desde el archivo YAMl
     * @param id
     * @param idarticulo
     */
    public void agregarTransaccion( int  id, String idarticulo)
    {

            if ( existeTransaccion(id) )
            {
                transaccion.put(id, transaccion.get(id).toString() + ", " + idarticulo );
            }
            else
            {
                numeroTransacciones.add(id);
                transaccion.put(id, idarticulo );
            }
    }


    /**
     * Devuelve true si la transaccion ya fue agregada
     * @param id identificador de la trasnsacion
     * @return true si la transaccion existe si no false
     */
    private boolean existeTransaccion( int id)
    {
        for (Integer integer : numeroTransacciones)
        {
            if(integer == id) return true;
        }
        return false;
    }

    /**
     * Devuelve la transaccion guardada en memoria
     * @return transaccion
     */
    public Map getTransacion()
    {
        return transaccion;
    }

    /**
     * Calcula los 1-itemset de un archivo no YAML
     * @param urlArchivo
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Map calcularL1NoYMl( String urlArchivo) throws FileNotFoundException, IOException
    {
        Map itemsReturn = new HashMap();
        Map itemsRecorridos = new HashMap();
        int numTransacciones = transaccion.size();

        
      /*      Iterator it = transaccion.entrySet().iterator();
            while ( it.hasNext())
            {
                Map.Entry valor = (Map.Entry)it.next();

                String[] str = ( (String) valor.getValue() ).split(",");
                for (String strItem : str)
                {
                    strItem = strItem.trim();
                    if( !itemsRecorridos.containsKey(strItem) )
                    {*/
                       /* double soporteValor = getSoporte(strItem);
                        double soporteValorPorcentaje = soporteValor / numTransacciones;

                        if( soporteValorPorcentaje >= soporte)
                        {
                            itemsReturn.put(strItem, soporteValor);
                        }*/

                  /*      itemsRecorridos.put(strItem, 0.0);
                    }


                }

            }*/













            File archivo = new File(urlArchivo);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        String linea;
        int id = 1;
        while ((linea = br.readLine()) != null)
        {
            //System.out.println(linea);
            numeroTransacciones.add(id);
            linea = linea.trim();
            String strFull = linea.replace(" ", ", ");
            String[] str = strFull.split(", ");//String[] str = ( (String) valor.getValue() ).split(",");

            for (String strItem : str)
                {
                    strItem = strItem.trim();
                    if( !itemsRecorridos.containsKey(strItem) )
                    {
                       /* double soporteValor = getSoporte(strItem);
                        double soporteValorPorcentaje = soporteValor / numTransacciones;

                        if( soporteValorPorcentaje >= soporte)
                        {
                            itemsReturn.put(strItem, soporteValor);
                        }*/

                        itemsRecorridos.put(strItem, 0.0);
                    }


                }


        }


















            Object[] items = itemsRecorridos.entrySet().toArray();
            int size = items.length;


           for (int i = 0; i < size; i++)
            {
                Map.Entry obj = (Map.Entry)items[i];
                String str = (String) obj.getKey();
                str = str.trim();
                System.out.println(i);

                double soporteValor = getSoporteL1(str, urlArchivo);
                double soporteValorPorcentaje = soporteValor / numTransacciones;

                if( soporteValorPorcentaje >= soporte)
                {
                    itemsReturn.put(str, soporteValor);
                }

            }




            

            return itemsReturn;
    }

    public Map calcularL1(  )
    {
        Map itemsReturn = new HashMap();
        Map itemsRecorridos = new HashMap();
        int numTransacciones = transaccion.size();

        {//obtener soporte

            Iterator it = transaccion.entrySet().iterator();
            //obtiene todos los items que posee la transaccion
            while ( it.hasNext())
            {
                Map.Entry valor = (Map.Entry)it.next();
                //obtiene el valor del item del Hashmap y lo convierte
                //en array
                String[] str = ( (String) valor.getValue() ).split(",");
                for (String strItem : str)//recorre el array
                {
                    strItem = strItem.trim();
                    if( !itemsRecorridos.containsKey(strItem) )
                    {
                       /* double soporteValor = getSoporte(strItem);
                        double soporteValorPorcentaje = soporteValor / numTransacciones;
                        
                        if( soporteValorPorcentaje >= soporte)
                        {
                            itemsReturn.put(strItem, soporteValor);
                        }*/

                        itemsRecorridos.put(strItem, 0.0);//agrega el item nuevo encontrado
                    }
                }              
            }//fin while

            Object[] items = itemsRecorridos.entrySet().toArray();
            int size = items.length;//longitud array
            for (int i = 0; i < size; i++)
            {
                Map.Entry obj = (Map.Entry)items[i];//hashmap
                String str = (String) obj.getKey();//Key es igual a un item
                str = str.trim();//eliminan los espacios del string
                //System.out.println(i);
                double soporteValor = getSoporte(str);
                double soporteValorPorcentaje = soporteValor / numTransacciones;
                if( soporteValorPorcentaje >= soporte)
                {
                    itemsReturn.put(str, soporteValor);
                }
                else
                {
                    itemsNoFrecuentes.put(str, soporteValor);
                }
            }

           /* it = transaccion.entrySet().iterator();
            double dblReturn = 0.0;
            int cont = 0;
            while ( it.hasNext())
            {
                System.out.println(cont ++);
                Map.Entry valor = (Map.Entry)it.next();

                String strFull =  (String) valor.getValue();

                for (int i = 0; i < size; i++)
                {
                    Map.Entry obj = (Map.Entry)items[i];
                    String str = (String) obj.getKey();
                    str = str.trim();
                    */
                  //  if( /* esta al final de los elementos de la transaccion */ ( strFull.indexOf(", " + str) > -1 && ( strFull.indexOf(", " + str) + (", " + str).length()  ) == strFull.length() ) || /* esta en el medio de los elementos de la transaccion */( strFull.indexOf( ", " + str + ", ") > -1 ) || /* es el unico elemento en la transaccion */( strFull.indexOf( str) == 0 && (strFull.length() == str.length() ) ) ||/* esta al principio con varios elementos*/ (strFull.indexOf( str + ", ") == 0 ) )
                 /*   {
                        //dblReturn ++;
                        /*if( itemsReturn.containsKey(str) )
                            dblReturn = 1 + (Double) itemsReturn.get(str);
                        else
                            dblReturn = 1;

                        itemsReturn.put(str, dblReturn);
                        obj.setValue( (Double) obj.getValue() + 1.0);
                    }

                }
                
            }*/


            /*double porcSoporte = 0.0;
            for (int i = 0; i < size; i++)
            {
                Map.Entry obj = (Map.Entry)items[i];
                String key = (String) obj.getKey();
                key = key.trim();

                porcSoporte = (Double) obj.getValue() / numTransacciones;

                if( porcSoporte >= soporte) itemsReturn.put(key, obj.getValue());
             }*/
        }
        todosL1 = itemsReturn;
        return  itemsReturn;
    }

    /**
     *
     * Genera candidatos a partir de los items frecuentes
     * @param itemsFrecuentes
     * @return Devuelve los items frecuentes al combinar lk con lk items
     */
    public Map GenerarCandidatos( Map itemsFrecuentes )
    {
        Map itemsCandidatos = new HashMap();
        Iterator it = itemsFrecuentes.entrySet().iterator();
        String itemCandidato = "";
        
        Object[] arrayItemsFrecuentes = itemsFrecuentes.entrySet().toArray();

        int numItems = arrayItemsFrecuentes.length;
        for (int i = 0; i < (numItems -1); i++)
        {
            Map.Entry object = (Map.Entry)arrayItemsFrecuentes[i];
            boolean nuevoCandidato;
            String itemsFrec1 = (String) object.getKey();
            for ( int j = i + 1; j < numItems; j++ )
            {
                Map.Entry object2 = (Map.Entry)arrayItemsFrecuentes[j];
                String itemsFrec2 = (String) object2.getKey();

                {//obtienes los posibles candidatos
                    String[] arrItemsFrec1 = itemsFrec1.split(",");
                    String[] arrItemsFrec2 = itemsFrec2.split(",");
                    if( arrItemsFrec1.length == 1 && arrItemsFrec2.length ==1 && !arrItemsFrec1[0].trim().equals(arrItemsFrec2[0].trim()))
                    {
                        itemCandidato = (arrItemsFrec1[0].trim())+", "+(arrItemsFrec2[0].trim());
                        boolean candidatoRepetido = existeCandidato(itemsCandidatos, itemCandidato);
                        if( !candidatoRepetido )
                        {
                            ////
                            double soporteCand = getSoporte(itemCandidato);
                            itemsCandidatos.put(itemCandidato, soporteCand);
                        }
                    }
                    else
                    {

                        
                        for (String string1 : arrItemsFrec2)
                        {
                            nuevoCandidato = true;
                            for (String string2 : arrItemsFrec1)
                            {
                                if(string1.trim().equals(string2.trim()))
                                {
                                    nuevoCandidato = false;
                                }
                            }

                            if( nuevoCandidato)
                            {
                                itemCandidato = itemsFrec1 +", " + string1.trim();

                                boolean candidatoRepetido = existeCandidato(itemsCandidatos, itemCandidato);
                                if( !candidatoRepetido )
                                {
                                    ////
                                    double soporteCand = getSoporte(itemCandidato);
                                    itemsCandidatos.put(itemCandidato, soporteCand);
                                }

                            }
                            
                        }
                    }
                }
            }
        }

        return itemsCandidatos;
    }

    /**
     * Genera los candidatos usando los items frecuentes encontrados
     * en la transaccion anterior
     * @param itemsFrecuentes
     * @param todosItemsFrecuentesIn Items frecuentes guardados
     * @param difTransaccion Transaccion diferencia
     * @return
     */
    public Map GenerarCandidatos( Map itemsFrecuentes, HashMap todosItemsFrecuentesIn, HashMap difTransaccion )
    {
        Map itemsCandidatos = new HashMap();
        Iterator it = itemsFrecuentes.entrySet().iterator();
        String itemCandidato = "";

        Object[] arrayItemsFrecuentes = itemsFrecuentes.entrySet().toArray();

        int numItems = arrayItemsFrecuentes.length;
        for (int i = 0; i < (numItems -1); i++)
        {
            Map.Entry object = (Map.Entry)arrayItemsFrecuentes[i];
            boolean nuevoCandidato;
            String itemsFrec1 = (String) object.getKey();
            for ( int j = i + 1; j < numItems; j++ )
            {
                Map.Entry object2 = (Map.Entry)arrayItemsFrecuentes[j];
                String itemsFrec2 = (String) object2.getKey();

                {//obtienes los posibles candidatos
                    String[] arrItemsFrec1 = itemsFrec1.split(",");
                    String[] arrItemsFrec2 = itemsFrec2.split(",");
                    if( arrItemsFrec1.length == 1 && arrItemsFrec2.length ==1 && !arrItemsFrec1[0].trim().equals(arrItemsFrec2[0].trim()))
                    {
                        itemCandidato = (arrItemsFrec1[0].trim())+", "+(arrItemsFrec2[0].trim());
                        boolean candidatoRepetido = existeCandidato(itemsCandidatos, itemCandidato);
                        if( !candidatoRepetido )
                        {
                            ////
                            double soporteCand = getSoporte(itemCandidato, todosItemsFrecuentesIn, difTransaccion);
                            itemsCandidatos.put(itemCandidato, soporteCand);
                        }
                    }
                    else
                    {


                        for (String string1 : arrItemsFrec2)
                        {
                            nuevoCandidato = true;
                            for (String string2 : arrItemsFrec1)
                            {
                                if(string1.trim().equals(string2.trim()))
                                {
                                    nuevoCandidato = false;
                                }
                            }

                            if( nuevoCandidato)
                            {
                                itemCandidato = itemsFrec1 +", " + string1.trim();

                                boolean candidatoRepetido = existeCandidato(itemsCandidatos, itemCandidato);
                                if( !candidatoRepetido )
                                {
                                    ////
                                    double soporteCand = getSoporte(itemCandidato, todosItemsFrecuentesIn, difTransaccion);
                                    itemsCandidatos.put(itemCandidato, soporteCand);
                                }

                            }

                        }
                    }
                }
            }
        }

        return itemsCandidatos;
    }

    /**
     * Devuelve los items frecuentes de los items candidatos dados
     * @param itemsCadidatos
     * @return hashMap de items frecuentes
     */
    public Map getItemsFrecuentes( Map itemsCadidatos)
    {
        Map frecuentesReturn = new HashMap();
        Object[] arrayItemsFrecuentes = itemsCadidatos.entrySet().toArray();
        int numTransacciones = transaccion.size();

        for (int i = 0; i < arrayItemsFrecuentes.length; i++)
        {
            Map.Entry object = (Map.Entry) arrayItemsFrecuentes[i];

            if(  Double.parseDouble( object.getValue().toString() ) / numTransacciones >= this.soporte)
            {
                frecuentesReturn.put(object.getKey(), object.getValue());
            }

        }

        return frecuentesReturn;
    }

    /**
     * Obtiene los items frecuentes haciendo uso los items frecuentes encontrado en el ultimo analisi
     * @param itemsCadidatos
     * @param todosItemsFrecuentesIn items frecuentes leidos de una operacion anterior con el algoritmo apriori
     * @param difTransaccion transaccion con los registros nuevos de añadadido a la transaccion anterior
     * @return itemsfrecuentes de acuerdo al soporte y la confianza
     */
    public Map getItemsFrecuentes( Map itemsCadidatos, HashMap todosItemsFrecuentesIn, HashMap difTransaccion)
    {
        Map frecuentesReturn = new HashMap();
        
        return frecuentesReturn;
    }

    /**
     * Verfica si el item cadidato esta guardado
     * @param itemsCandidatos
     * @param cadena String con los items cadidatos separados por coma
     * @return
     */
    private boolean existeCandidato(Map itemsCandidatos, String cadena)
    {
        boolean existe = false;
        Object[] candidatos ;

        if( itemsCandidatos.size() > 0 )
        {
            candidatos = (Object[]) itemsCandidatos.entrySet().toArray();
        }
        else
        {
            return existe;
        }


        for (int i = 0; i < candidatos.length; i++)
        {
            Map.Entry  object = (Map.Entry)candidatos[i];
            String str = (String) object.getKey();
            String[] arrStr = str.split(",");
            String[] arrCadena = cadena.split(",");

            boolean sonIguales = arrayIgual(arrStr, arrCadena);
            if( sonIguales )
            {
                existe = true;
                i = candidatos.length + 1;
            }
            
        }

        return existe;
    }

    /**
     * Verifica si dos arrays son iguales
     * @param str1
     * @param str2
     * @return Devuelve true si los arrays son iguales si no false
     */
    private boolean arrayIgual( String[] str1, String[] str2 )
    {
        boolean esIgual = true;
        boolean poseeItem = false;

        if( str1.length == str2.length )
        {
            for (String string1 : str2)
            {
                poseeItem = false;
                for (String string2 : str1)
                {
                    if( string1.trim().equals(string2.trim()) )
                    {
                        poseeItem = true;
                    }
                }
                if( !poseeItem )
                {
                    esIgual = false;
                }

            }
        }

        return esIgual;
    }

    /**
     * Obtiene el soporte cada 1-itemset
     * @param item
     * @param urlArchivo
     * @return double compredido en entre uno y cero
     * @throws FileNotFoundException
     * @throws IOException
     */
    public double getSoporteL1(String item, String urlArchivo) throws FileNotFoundException, IOException
    {
        double dblReturn = 0.0;

        File archivo = new File(urlArchivo);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        String linea;
        int id = 1;
        while ((linea = br.readLine()) != null)
        {
            //System.out.println(linea);
            numeroTransacciones.add(id);
            linea = linea.trim();
            String strFull = linea.replace(" ", ", ");

            if ( /* esta al final de los elementos de la transaccion */(strFull.indexOf(", " + item) > -1 && (strFull.indexOf(", " + item) + (", " + item).length()) == strFull.length()) || /* esta en el medio de los elementos de la transaccion */ (strFull.indexOf(", " + item + ", ") > -1) || /* es el unico elemento en la transaccion */ (strFull.indexOf(item) == 0 && (strFull.length() == item.length())) ||/* esta al principio con varios elementos*/ (strFull.indexOf(item + ", ") == 0))
            {
                dblReturn++;
            }
            //System.out.println(linea);
            //System.out.println(id);
            id++;
        }

        return dblReturn;

    }


    /**
     * Obtiene el soporte de items candidatos
     * @param item
     * @return double de las veces que el candidato esta presenta en la transaccion
     */
    public double getSoporte( String item )
    {
        double dblReturn = 0.0;
        String[] arrItem = item.split(",");
        int arrItemLength = arrItem.length;
        if( arrItemLength == 1)
        {
            if( esYml )
            {

                Iterator it = transaccion.entrySet().iterator();
                int i = 0;
                while ( it.hasNext())
                {
                    Map.Entry valor = (Map.Entry)it.next();
                    String strFull = (String) valor.getValue();
                    int key = (Integer) valor.getKey();
                    if( !transaccioneExcluidas.contains(key) )
                    {
                        if( /* esta al final de los elementos de la transaccion */ ( strFull.indexOf(", " + item) > -1 && ( strFull.indexOf(", " + item) + (", " + item).length()  ) == strFull.length() ) || /* esta en el medio de los elementos de la transaccion */( strFull.indexOf( ", " + item + ", ") > -1 ) ||/* esta al principio con varios elementos*/ (strFull.indexOf( item + ", ") == 0 ) )
                        {
                            dblReturn ++;
                        }
                        else if( /* es el unico elemento en la transaccion */( strFull.indexOf( item) == 0 && (strFull.length() == item.length() ) ) )
                        {
                            dblReturn ++;
                            transaccioneExcluidas.add((Integer) valor.getKey());
                        }
                    }
                }
            }
            else//halla el soporte de con el archivo sin formato yml
            {
                String full = fullTrans;
                while( /* esta al final de los elementos de la transaccion */ ( full.indexOf(", " + item) > -1 && ( full.indexOf(", " + item) + (", " + item).length()  ) == full.length() ) || /* esta en el medio de los elementos de la transaccion */( full.indexOf( ", " + item + ", ") > -1 ) || /* es el unico elemento en la transaccion */( full.indexOf( item) == 0 && (full.length() == item.length() ) ) ||/* esta al principio con varios elementos*/ (full.indexOf( item + ", ") == 0 ) )                    
                {
                    dblReturn ++;
                    /* esta al final de los elementos de la transaccion */
                    if( full.indexOf(", " + item) > -1 && ( full.indexOf(", " + item) + (", " + item).length()  ) == full.length() )
                    {
                        full = full.substring( 0, full.indexOf(", " + item) );
                    }
                    
                    /* esta en el medio de los elementos de la transaccion */
                    else if ( full.indexOf( ", " + item + ", ") > -1 )
                    {
                        int inicio = full.indexOf( ", " + item + ", ");
                        String str1 = "";
                        String str2 = "";
                        str1 = full.substring( 0,  inicio/*2 + item.length()*/ );
                        str2 = full.substring( ( inicio + (", " + item + ", ").length() ) );
                        full = str1 + ", " + str2;
                        String debug = "";
                    }

                    /* es el unico elemento en la transaccion */
                    else if
                    ( full.indexOf( item) == 0 && (full.length() == item.length() ) )
                    {
                        full = full.substring(0, 0);
                    }
                    /* esta al principio con varios elementos*/
                    if(full.indexOf( item + ", ") == 0 )
                    {
                        full = full.substring(0, (item + ", ").length() );
                    }
                }

            }
        }
        else //posee mas de un elemento el candidato
        {
            //if( !transaccioneExcluidas.contains(key) )
            Object[] obj = transaccion.entrySet().toArray();
            int tam = obj.length;            
            for (int i = 0; i < tam; i++)
            {
                Map.Entry object = (Map.Entry) obj[i];
                String[] obj2 = ((String) object.getValue()).split(",");
                int key = (Integer) object.getKey();//id de la transaccion
                boolean existenTodosItem = false;
                int numItemEncontrado = 0;
                if( !transaccioneExcluidas.contains(key) )
                {
                    for (String strItem : arrItem)//items del candidato
                    {
                        boolean existenUnItem = false;
                        for (String strItem2 : obj2)//items de la transaccion
                        {
                            if (strItem.trim().equals(strItem2.trim()))
                            {
                                existenUnItem = true;
                                break;
                            }
                            /*else if( strItem.trim().compareTo(strItem2) < 0)
                            {
                            break;
                            }*/
                        }
                        //existenTodosItem = existenUnItem;
                        if (existenUnItem)
                        {
                            numItemEncontrado++;
                        }
                    }
                }
                //verificar arrayLength con strItem2
                //arrItemLength longitud del candidato
                if (numItemEncontrado == arrItemLength)
                {
                    dblReturn++;
                }
                //excluye la transaccion para posterior calculo de soporte
                //verifica que la cantidad de items de la transaccion es igual
                //a la de item del candidatos
                if( obj2.length == arrItemLength && numItemEncontrado == arrItemLength)
                {
                    transaccioneExcluidas.add(key);
                }

                if( obj2.length == (arrItemLength + 1)  && numItemEncontrado == arrItemLength)
                {
                    excluirTransPosteriores(obj2, key);
                }
            }
        }
        return dblReturn;
    }

    private void excluirTransPosteriores(String[] obj2, int key)
    {
        //recorre los items de la transaccion
        for (String strItem2 : obj2)//items de la transaccion
        {
            boolean seEncuentra;
            seEncuentra= false; //el item no aparece en los L1
            Object[] elemsL1 = todosL1.entrySet().toArray();
            int tamElemL1 = elemsL1.length;
            for (int k = 0; k < tamElemL1; k++)
            {
                Map.Entry objectL1 = (Map.Entry) elemsL1[k];
                String keyL1 = (String) objectL1.getKey();
                keyL1 = keyL1.trim();

                //verifica si son iguales
                if(strItem2.trim().equals(keyL1))
                {
                    seEncuentra = true;
                    break;
                }
            }
            //si el item no es de los L1, se excluye la transaccion
            if( seEncuentra == false
                     && tamElemL1 > 0 /*se asegura que se haya usado items L1,
             se puede presentar al ejecutar el metodo getsoporte sin ejecutar obtener L1 como
             se usa en unos ejemploes en transaccionesTest.java, si se quita esta opcion el
                     test mostrara error*/
                    )
            {
                transaccioneExcluidas.add(key);
            }
        }
    }

    /**
     * Obtiene el soporte de items candidatos haciendo uso de los items
     * frecuentes encontrado anteriormente y las nuevos registros agregados
     * @param item items a encontrar soporte
     * @param todosItemsFrecuentesIn items frecuentes encontrado en la anterior transaccion
     * @param difTransaccion nuevos registros agregados
     * @return
     */
    public double getSoporte( String item, HashMap todosItemsFrecuentesIn, HashMap difTransaccion)
    {
        double dblReturn = 0.0;
        String[] arrItem = item.split(",");
        int arrItemLength = arrItem.length;
        if( arrItemLength == 1)
        {
            if( esYml )
            {
                //se obtiene las veces que el candidato se encuentra en la transaccion diferencia
                Object[] obj2 =  difTransaccion.entrySet().toArray();
                int tam2 = obj2.length;
                for (int i = 0; i < tam2; i++)
                {
                     Map.Entry entry = (Map.Entry) obj2[i];
                     String strFull = (String) entry.getValue();
                    if( /* esta al final de los elementos de la transaccion */ ( strFull.indexOf(", " + item) > -1 && ( strFull.indexOf(", " + item) + (", " + item).length()  ) == strFull.length() ) || /* esta en el medio de los elementos de la transaccion */( strFull.indexOf( ", " + item + ", ") > -1 ) || /* es el unico elemento en la transaccion */( strFull.indexOf( item) == 0 && (strFull.length() == item.length() ) ) ||/* esta al principio con varios elementos*/ (strFull.indexOf( item + ", ") == 0 ) )
                    {
                        dblReturn ++;
                    }
                }
                //fin candidato en transaccion diferencia

                //se recorre el hashmap todosItemsFrecuentesIn para buscar
                //el soporte del candidadato
                Object[] obj3 =  todosItemsFrecuentesIn.entrySet().toArray();
                int tam3 = obj3.length;
                double sop = 0.0;
                boolean rebuscar = true;
                for (int i = 0; i < tam3; i++)
                {
                    Map.Entry entry = (Map.Entry) obj3[i];
                    String strFull = (String) entry.getKey();

                    if( item.equals(strFull))
                    {
                        dblReturn = dblReturn + sop; //se suman los soportes
                        rebuscar = false;
                    }
                }
                //fin recorrido en el hasmap todosItemsFrecuentesIn

                //se realiza de nuevo la busqueda en caso que no se 
                //encuentre el candidato en los items frecuentes calculados anterirmente
                if(rebuscar)
                {
                    dblReturn = 0.0;
                    Iterator it = transaccion.entrySet().iterator();
                    int i = 0;
                    //cambiar busqueda aqui utilizando  la transaccionDIF y todosItemsFrecuentesIn
                    while ( it.hasNext())
                    {
                        Map.Entry valor = (Map.Entry)it.next();
                        String strFull = (String) valor.getValue();
                        if( /* esta al final de los elementos de la transaccion */ ( strFull.indexOf(", " + item) > -1 && ( strFull.indexOf(", " + item) + (", " + item).length()  ) == strFull.length() ) || /* esta en el medio de los elementos de la transaccion */( strFull.indexOf( ", " + item + ", ") > -1 ) || /* es el unico elemento en la transaccion */( strFull.indexOf( item) == 0 && (strFull.length() == item.length() ) ) ||/* esta al principio con varios elementos*/ (strFull.indexOf( item + ", ") == 0 ) )
                        {
                            dblReturn ++;
                        }
                    }
                }// fin rebusqueda
            }
            else // se realiza la busqueda utilizando la variable fulltrans, la que guarda todos las transaccion en una sola cadena de texto
            {
                String full = fullTrans;
                while( /* esta al final de los elementos de la transaccion */ ( full.indexOf(", " + item) > -1 && ( full.indexOf(", " + item) + (", " + item).length()  ) == full.length() ) || /* esta en el medio de los elementos de la transaccion */( full.indexOf( ", " + item + ", ") > -1 ) || /* es el unico elemento en la transaccion */( full.indexOf( item) == 0 && (full.length() == item.length() ) ) ||/* esta al principio con varios elementos*/ (full.indexOf( item + ", ") == 0 ) )
                {
                    dblReturn ++;
                    /* esta al final de los elementos de la transaccion */
                    if( full.indexOf(", " + item) > -1 && ( full.indexOf(", " + item) + (", " + item).length()  ) == full.length() )
                    {
                        full = full.substring( 0, full.indexOf(", " + item) );
                    }

                    /* esta en el medio de los elementos de la transaccion */
                    else if ( full.indexOf( ", " + item + ", ") > -1 )
                    {
                        int inicio = full.indexOf( ", " + item + ", ");
                        String str1 = "";
                        String str2 = "";
                        str1 = full.substring( 0,  inicio/*2 + item.length()*/ );
                        str2 = full.substring( ( inicio + (", " + item + ", ").length() ) );
                        full = str1 + ", " + str2;
                        String debug = "";
                    }

                    /* es el unico elemento en la transaccion */
                    else if
                    ( full.indexOf( item) == 0 && (full.length() == item.length() ) )
                    {
                        full = full.substring(0, 0);
                    }
                    /* esta al principio con varios elementos*/
                    if(full.indexOf( item + ", ") == 0 )
                    {
                        full = full.substring(0, (item + ", ").length() );
                    }
                }

            }
        }
        else //son mas de un elemento
        {
            //se obtiene las veces que el candidato se encuentra en la transaccion diferencia
            Object[] obj2 =  difTransaccion.entrySet().toArray();
            int tam2 = obj2.length;
            for (int i = 0; i < tam2; i++)
            {
                Map.Entry object = (Map.Entry)obj2[i];
                String[] obj4  = ( (String) object.getValue() ).split(",");

                boolean existenTodosItem = false;
                int numItemEncontrado = 0;
                for (String strItem : arrItem)//items del candidato
                {
                    boolean existenUnItem = false;
                    for (String strItem2 : obj4)//items de la transacion
                    {
                        if(strItem.trim().equals(strItem2.trim()))
                        {
                            existenUnItem = true;
                            break;
                        }
                        /*else if( strItem.trim().compareTo(strItem2) < 0)
                        {
                            break;
                        }*/
                    }
                    //existenTodosItem = existenUnItem;
                    if(existenUnItem)
                    {
                        numItemEncontrado++;
                    }
                }

                if(numItemEncontrado == arrItemLength)
                {
                    dblReturn ++;
                }
            }
            //fin candidato en transaccion diferencia



            
            //se recorre el hashmap todosItemsFrecuentesIn para buscar
            //el soporte del candidadato
            Object[] obj3 =  todosItemsFrecuentesIn.entrySet().toArray();
            int tam3 = obj3.length;
            double sop = 0.0;
            boolean rebuscar = true;
            for (int i = 0; i < tam3; i++)
            {

                //
                Map.Entry entry = (Map.Entry) obj3[i];
                String strFull = (String) entry.getKey();//key guarda los items ejemplo: 3, 2
                String[] obj4  = strFull.split(",");
                int obj4Length = obj4.length;
                sop = Double.parseDouble( entry.getValue().toString() );// value es el soporte en double de los items candidatos
                boolean existenTodosItem = false;
                int numItemEncontrado = 0;
                for (String strItem : arrItem)//items del candidato
                {
                    boolean existenUnItem = false;
                    for (String strItem2 : obj4)//items de todosItemsFrecuentesIn
                    {
                        if(strItem.trim().equals(strItem2.trim()))
                        {
                            existenUnItem = true;
                            break;
                        }
                    }
                    if(existenUnItem)
                    {
                        numItemEncontrado++;
                    }
                }
                //los items candidatos concuerdan con los que guarda la
                //variable todos los items todosItemsFrecuentesIn
                if(numItemEncontrado == arrItemLength && arrItemLength == obj4Length)
                {
                    dblReturn = dblReturn + sop; //se suman los soportes
                    rebuscar = false;
                }
               /* if( item.equals(strFull))
                {
                    dblReturn = dblReturn + sop; //se suman los soportes
                    rebuscar = false;
                }
                */
            }
            //fin recorrido en el hasmap todosItemsFrecuentesIn
            


            //se realiza de nuevo la busqueda en caso que no se
            //encuentre el candidato en los items frecuentes calculados anterirmente
            if(rebuscar)
            {
                dblReturn = 0.0;
                //recorre todas las trasacciones para encontrar el soporte
                Object[] obj = transaccion.entrySet().toArray(); // recorre la transaccion
                int tam4 = obj.length;
                //cambiar busqueda aqui utilizando  la transaccionDIF y todosItemsFrecuentesIn
                for (int i = 0; i < tam4; i++)
                {
                    Map.Entry object = (Map.Entry)obj[i];
                    String[] obj4  = ( (String) object.getValue() ).split(",");

                    boolean existenTodosItem = false;
                    int numItemEncontrado = 0;
                    for (String strItem : arrItem)//items del candidato
                    {
                        boolean existenUnItem = false;
                        for (String strItem2 : obj4)//items de la transacion
                        {
                            if(strItem.trim().equals(strItem2.trim()))
                            {
                                existenUnItem = true;
                                break;
                            }
                            /*else if( strItem.trim().compareTo(strItem2) < 0)
                            {
                                break;
                            }*/
                        }
                        //existenTodosItem = existenUnItem;
                        if(existenUnItem)
                        {
                            numItemEncontrado++;
                        }
                    }

                    if(numItemEncontrado == arrItemLength)
                    {
                        dblReturn ++;
                    }

                }
                //fin recorre todas las trasacciones para encontrar el soporte

            }
        }


        return dblReturn;
    }
    /**
     * Devuelve las relaciones fuertes
     * @param items
     * @return ArrayList<Reglas> de las relaciones fuertes
     */
    public ArrayList<Reglas> getRelacionFuerte(Map items)
    {
        ArrayList<Reglas> arrReturn = new ArrayList<Reglas>();
        ArrayList<String[]> comb = new ArrayList<String[]>();

        Object[] arrItems = items.entrySet().toArray();
        int TamArrItems = arrItems.length;

        for (int i = 0; i < TamArrItems; i++)
        {
            Map.Entry object = (Map.Entry) arrItems[i];
            String strItems =  (String) object.getKey();
            String[] arrayItems = strItems.split(",");
            int tamArrayItems = arrayItems.length;
            if (arrayItems.length > 1)//se asegura que el item frecuente tenga mas de un elemento
            {
                for (int j = 0; j < tamArrayItems - 1; j++)
                {
                    comb = calcularCombinaciones(arrayItems, j + 1);//cambiar
                
                String db = "";
                //continuar

                //busca la confianza-------------------------------
                int combSize = comb.size();

                for (int k = 0; k < TamArrItems; k++)//recorre los items frecuentes para buscar el soporte
                {
                    Map.Entry object2 = (Map.Entry) arrItems[k];//items frecuentes
                    String strItems2 =  (String) object2.getKey();
                    String[] arrayItems2 = strItems2.split(",");
                    int tamArrayItems2 = arrayItems2.length;

                    int numeroOcurrencias = 0; //veces que se encuentran los elmentos en otro array

                    for (int m = 0; m < combSize; m++)
                    {
                        String[] strArrComb = comb.get(m); //obtienes una combinacion
                        int strArrCombLength = strArrComb.length;

                        numeroOcurrencias = 0;
                        for (int l = 0; l < tamArrayItems2; l++)//recorre los items frecuentes
                        {
                            String str2 = arrayItems2[l];
                            if(tamArrayItems2 == strArrCombLength)
                            {
                                //recorre items de la combinacion
                                for (int index5 = 0; index5 < strArrComb.length; index5++)
                                {
                                    String string5 = strArrComb[index5];
                                    if( string5.trim().equals(str2.trim()))
                                    {
                                        numeroOcurrencias ++;
                                        break;
                                    }

                                }
                            }
                        }

                            //se encuentra el soporte de la combinacion 
                            if( numeroOcurrencias == strArrCombLength)
                            {
                                //obtengo el valor de soporte y agregar a el arrayList de reglas
                                double soporteXY =  getSoporteEnArray(items, arrayItems);/*Double.parseDouble( arrayItems/*object2.getValue().toString() );*/
                                String[] arrSustr = getArraySustraccion(arrayItems, strArrComb);
                                
                                double soporteX = getSoporteEnArray(items, strArrComb);
                                double cofianzaLocal = soporteXY / soporteX;

                                Reglas rg = new Reglas(strArrComb, arrSustr);
                                rg.setConfianza( cofianzaLocal );
                                rg.setMinConfianza(this.confianza);
                                arrReturn.add(rg);
                                String debug = "";
                                
                            }
                            /*if( tamArrayItems == combSize)
                            {
                                String[] combArrStr = comb.get(m);
                                if( combArrStr.)
                                numeroOcurrencias++;
                            }*/
                            

                        
                        
                    }


                }
                //-------------------------------------------------


                }//llevar el for hasta el final del if
            }
        }
        return arrReturn;
    }

    /**
     * Obtiene el soporte para itemsets con mas de un elemento
     * @param items Map de los items en donde buscar
     * @param itemsBuscar String[] con los items a obtener el soporte
     * @return double numero entre uno y cero
     */
    public double getSoporteEnArray(Map items, String[] itemsBuscar)
    {
        double soporteReturn = 0.0;

        Object[] arrItems = items.entrySet().toArray();
        int TamArrItems = arrItems.length;
        int combSize = itemsBuscar.length;

        for (int i = 0; i < TamArrItems; i++)
        {
            Map.Entry object = (Map.Entry) arrItems[i];
            String strItems = (String) object.getKey();
            String[] arrayItems = strItems.split(",");
            int tamArrayItems2 = arrayItems.length;

            int numeroOcurrencias = 0; //veces que se encuentran los elmentos en otro array

            numeroOcurrencias = 0;
            for (int l = 0; l < tamArrayItems2; l++)//recorre los items frecuentes
            {
                String str2 = arrayItems[l];
                if (tamArrayItems2 == combSize)
                {
                    //recorre items de la combinacion
                    for (int index5 = 0; index5 < combSize; index5++)
                    {
                        String string5 = itemsBuscar[index5];
                        if (string5.trim().equals(str2.trim()))
                        {
                            numeroOcurrencias++;
                        }

                    }
                }
            }

            if( numeroOcurrencias == combSize )
            {
                soporte =  Double.parseDouble( object.getValue().toString() );
            }

        }
        return soporte;
    }

    /**
     * Devuelve un array que contiene los elementos del
     * arrMinuendo que no estan en el arrSustraendo
     * @param arrMinuendo
     * @param arrSustraendo
     * @return String[]
     */
    public String[] getArraySustraccion( String[] arrMinuendo, String[] arrSustraendo)
    {
        String[] arrReturn = new String[arrMinuendo.length - arrSustraendo.length];
        boolean existeElem = false;
        int index = 0;

        for (int i = 0; i < arrMinuendo.length; i++)
        {
            String string1 = arrMinuendo[i];
            existeElem = false;
            for (int j = 0; j < arrSustraendo.length; j++)
            {
                String string2 = arrSustraendo[j];
                if( string1.trim().equals(string2.trim()) )
                {
                    existeElem = true;
                }
            }

            if( !existeElem )
            {
                arrReturn[index] = string1;
                index++;
            }

        }
        
        return arrReturn;
    }
    
    /**
     * Calcula las combinaciones de un array de la forma {a, b, c, d....}
     * @param arrStr array de string
     * @param cantidad cantidad de elementos para la  realizar combinaciones
     * @return ArrayList<String[]>
     */
    public ArrayList<String[]>  calcularCombinaciones(String[] arrStr, int cantidad)
    {
        ArrayList<String[]> listReturn = new ArrayList<String[]>();
        ArrayList<Integer> indices = new ArrayList<Integer>();

        this.combinaciones = arrStr;

        int longitud = arrStr.length;
        int tope = longitud - ( cantidad - 1);

        boolean primeraInvocacion = false;
        for (int i = 0; i < tope; i++)
        {
            //String string = str[i];
            int size = indices.size();
            if( size > 0){ indices.set(0, i); }
            else{ indices.add(i); }


            if( tope < longitud)
            {
                forRecursivo( i +1, tope +1, longitud, indices, 1, listReturn);
                String debug = "";
            }
            else if( tope == longitud)
            {
                String[] arr = new String[1];
                arr[0] = this.combinaciones[i];
                listReturn.add(arr);
            }

            

        }

        return listReturn;
    }

    /**
     * For que se llama recursivamente las veces necesarias para combinar elementos
     * de los items frecuentes y obtener todas las combinaciones posibles para generar
     * las reglas fuertes.
     * @param i indice para
     * @param tope
     * @param longitud
     * @param indices
     */
    private ArrayList<String[]> forRecursivo(int i, int tope, int longitud, ArrayList<Integer> indices, int  profundidad, ArrayList<String[]> retorno)
    {
        int valorInicialdeI= i;
        boolean seHaInvocado = false;
        ArrayList<String[]> arrListReturn = retorno;
        while( i< tope)
        {
           /* if( i == valorInicialdeI )
            {
                indices.add(i);
            }
            else
            {
                indices.set(indices.size() - 1, i);
            }*/

            int tamIndices = indices.size();
            if( (profundidad + 1) == tamIndices)
            {
                indices.set(indices.size() - 1, i);
            }
            else if( (profundidad + 1) < tamIndices )
            {
                indices.set( profundidad, i);
            }
            else if( (profundidad + 1) > tamIndices )
            {
                indices.add( i);
            }

            

            if( tope < longitud )
            {
                arrListReturn = forRecursivo(i +1, tope + 1, longitud, indices, profundidad + 1, arrListReturn );
                if( !seHaInvocado )
                {
                    seHaInvocado = true;
                }
            }
            else if( tope == longitud)
            {
                String[] res = new String[indices.size()];
                int resLong = res.length;
                for (int j = 0; j < resLong; j++)
                {
                    res[j] = this.combinaciones[indices.get(j)];

                }
                arrListReturn.add(res);
                String deb = "";
                

            }

            i++;
        }

        return arrListReturn;
    }

}
