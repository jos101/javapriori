/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udc.apriori;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author udc
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException
    {
        // TODO code application logic here
        double soporte = 20;
        double confianza = 80;
        String archivo = "";
        String archivoSalida = "";
        boolean imprimir = false;
        boolean esYMl = true;
        boolean json = false;//inidica si se debe imprimir en formato json
        boolean escribir = false;
        boolean ayuda = false;
        boolean incremental = false;//indica si la solucion es incremental

        System.out.println("--------A PRIORI------------");

        for ( int i = 0; i < args.length; i++ )
        {
            if( args[i].equals("-s") )
            {
               soporte = Double.parseDouble( args[i+1] );
            }
            if( args[i].equals("-c") )
            {
                confianza = Double.parseDouble( args[i+1]);
            }
            if( args[i].equals("-f") )
            {
                imprimir = true;
            }
            if( args[i].equals("-json") )
            {
                json = true;
            }
            if( args[i].equals("-t") )
            {
                esYMl = false;
            }
            if( args[i].equals("-o") )
            {
                archivoSalida = args[i+1];
                escribir = true;
            }
            if( args[i].equals("--help") ||  args[i].equals("?"))
            {
                ayuda = true;
            }
            //resuelve los items frecuentes usando el archivo de datos guardado
            //previamente, solucion incremental
            if(args[i].equals("-i"))
            {
                incremental = true;
            }
            //ultimo argumento de la cadena es el archivo de texto
            if( i == (args.length - 1) )
            {
                archivo = args[i];
            }                   
        }


        if(args.length != 0 && !ayuda)
        {
            System.out.println("soporte : " + soporte + "%");
            System.out.println("confianza : " + confianza + "%");
            System.out.println("archivo : " + archivo);
            System.out.println("Calculando...");

            long startTime = (long) 0;
            long estimatedTimeResolver;
            long starttimemm = 0;
            long stimatedTimemm = 0;
            Apriori apr = new Apriori(soporte/100, confianza/100, archivo, esYMl);
            System.out.println("Obteniendo items frecuentes...[1/2]");
            Map mp = new HashMap();
            if( !incremental )
            {
                startTime = System.nanoTime();
                starttimemm = System.currentTimeMillis();
                mp = apr.resolver1();
                estimatedTimeResolver = System.nanoTime() - startTime;
                stimatedTimemm = System.currentTimeMillis() - starttimemm;
            }
            else
            {
                startTime = System.nanoTime();
                mp = apr.resolver2();
                estimatedTimeResolver = System.nanoTime() - startTime;
                //System.out.println("resuelto por incremental");
            }
            System.out.println("Obteniendo Reglas...[2/2]");
            ArrayList<Reglas> listReglas = apr.getRelacionesFuertes();
            long estimatedTime = System.nanoTime() - startTime;

            //guarda la variable todosItemsFrecuentes e itemsNoFrecuentes en un archivo binario
            ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream("datos.dll"));
            salida.writeObject(apr.transacciones.itemsNoFrecuentes);
            salida.writeObject(mp);
            salida.writeObject( apr.transacciones.transaccion.size() );
            salida.writeObject( apr.transacciones.transaccion );
            salida.close();
            //fin guarda la variable todosItemsFrecuentes


            if( imprimir )
            {
                System.out.println("--------Items Frecuentes--------");
                //System.out.println(mp);
                Object[] obj = mp.entrySet().toArray();

                int tam= apr.transacciones.transaccion.size();
                System.out.println("Numero de transacciones " + tam);
                for (int i = 0; i < obj.length; i++)
                {
                    Map.Entry object = (Map.Entry)obj[i];
                    System.out.println( object.getKey() + " => "+ 100 * (Double) object.getValue() / tam + "%" );

                }
            }
            
            System.out.println("--------Relaciones Fuertes------");

            int rgSize = listReglas.size();
            for (int i = 0; i < rgSize; i++)
            {
                Reglas rg = listReglas.get(i);
                if(rg.isFrecuente())
                {
                    System.out.println( rg + "--Confianza : " +rg.getConfianza() * 100 +"%" );
                }


            }

            System.out.println("\n");
            System.out.println("\n-----Tiempo ejecucion-----");
            System.out.println("Tiempo estimado items frecuentes: " +  estimatedTimeResolver + " nano-segundos");
            System.out.println("Tiempo estimado items frecuentes: " +  stimatedTimemm + " mili-segundos");
            System.out.println("Tiempo estimado: " +  estimatedTime + " nano-segundos");


             // escribe el resultado en un fichero
             if( escribir )
             {
                 FileWriter fichero = null;
                 PrintWriter pw = null;
                 try
                 {
                     fichero = new FileWriter(archivoSalida);

                     pw = new PrintWriter(fichero);


                    if( imprimir )
                    {
                        if(!json)
                        {
                            pw.println("--------Items Frecuentes--------");
                        }
                        else
                        {
                            pw.println("{\"frecuentes\":[");
                        }
                        //System.out.println(mp);
                        Object[] obj = mp.entrySet().toArray();

                        int tam= apr.transacciones.transaccion.size();
                        //System.out.println(tam);
                        for (int i = 0; i < obj.length; i++)
                        {
                            Map.Entry object = (Map.Entry)obj[i];
                            //object.getKey() = id del item o los items
                            //object.getValue() soporte en cantidad del item o los items
                            if(!json)
                            {
                                pw.println( object.getKey() + " => "+ 100 * (Double) object.getValue() / tam + "%" );
                            }
                            else // es json
                            {
                                if( i > 0 ){ pw.println(","); }
                                pw.print( "  {\"ids\":\""+object.getKey()+"\"" + ", \"soporte\":\""+ 100 * (Double) object.getValue() / tam + "\"}" );

                            }
                            
                        }
                        if(json)
                        {
                            pw.println("],");
                        }
                    }
                    pw.println();

                    if(!json)
                    {
                        pw.println("--------Relaciones Fuertes------");
                    }
                    else
                    {
                        pw.println("\"reglas\":[");
                    }

                    rgSize = listReglas.size();
                    for (int i = 0; i < rgSize; i++)
                    {
                        Reglas rg = listReglas.get(i);
                        if(rg.isFrecuente())
                        {
                            if(!json)//no es json
                            {
                                pw.println( rg + "--Confianza : " +rg.getConfianza() * 100 +"%" );
                            }
                            else//es json
                            {
                                if( i > 0){ pw.print(",\n"); }
                                pw.print( "  {\"antecedente\":\""+rg.getStrAntecedente()+"\", \"consecuente\":\""+ rg.getStrConsecuente() +"\", \"confianza\":\"" + rg.getConfianza() * 100 + "\"}" );
                            }
                        }
                    }
                    if(json){ pw.println("\n]\n}"); }

                    if(!json)//no es json
                    {
                        pw.println("\n-----Tiempo ejecucion-----");
                        pw.println("Tiempo estimado items frecuentes: " +  estimatedTimeResolver + "nano-segundos");
                        pw.println("Tiempo estimado items frecuentes: " +  stimatedTimemm + " mili-segundos");
                        pw.println("Tiempo estimado: " +  estimatedTime + "nano-segundos");
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println("Resultados escritos en " + archivoSalida);

                 }
                 catch (Exception e)
                 {
                 }
                 finally
                 {
                     try
                     {
                         //se cierra el fichero.
                         if (null != fichero)
                         {
                             fichero.close();
                         }
                     } catch (Exception e2)
                     {
                     }
                 }

             }
            
        }
        else
        {
            System.out.println("apriori uso: apriori.jar <opciones> archivo.yml");
            System.out.println("Encuentra Items Frecuentes con el algoritmo Apriori");
            System.out.println(" ");
            System.out.println("Opciones: ");
            System.out.println("--help:\t Despliega esta ayuda \t");
            System.out.println("-c #:\t Minimo Confianza \t<Por Defecto: 80%>");
            System.out.println("-f  :\t imprimi todos los elementos frecuentes \t");
            System.out.println("-i  :\t solucion incremental, utiliza los ultimos resultados para hallar los nuevo  \t");
            System.out.println("-json  :\t salida del archivo en formato json \t");
            System.out.println("-o  :\t archivo de salida \t");
            System.out.println("-s #:\t Minimo Soporte \t<Por Defecto: 20%>");
            System.out.println("-t  :\t Lee en formato tradicional los datos en vez del YML \t");


        }

    }
}

