package com.nttdata.mvn;

import org.ejml.simple.SimpleMatrix;

import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

/**
 * Main class of the application
 * @author Narciso González Calderón
 * @version 0.1-SNAPSHOT
 */
public class App {
  private static Scanner s;

  /**
   * @param text text to print in the console before the user input
   * @param min  minimum value of the input
   * @param max  maximum value of the input
   * @return the user input
   */
  public static int read(String text, int min, int max) {
    int num;
    boolean error;
    s = new Scanner(System.in);

    // Read the user input until it is correct
    do {
      System.out.print(text);
      num = s.nextInt();
      error = num < min || num > max;
      if (error)
        System.out.print("Error. ");
    } while (error);

    return num;
  }

  /**
   * @param args command line arguments (not used)
   * @throws Exception if an error occurs
   */
  public static void main(String[] args) throws Exception {
    System.out.println("Generador de matrices:\n");

    // Read the number of rows and columns of the matrix
    int height = read("Introduzca la altura (entero de 2 a 5): ", 2, 5);
    int width = read("Introduzca la anchura (entero de 2 a 5): ", 2, 5);

    // Generate the matrix with random values
    Random random = new Random();
    SimpleMatrix matrix = SimpleMatrix.random(height, width, 0, 10, random);
    String content;

    int option;
    do {
      // Print the matrix
      System.out.println("\nMatriz:");
      content = StringUtils.substringAfter(matrix.toString(), "\n");
      System.out.println(content);

      // Print menu
      System.out.println("Seleccione una de las siguientes opciones:");
      System.out.println("\t0. Salir.");
      System.out.println("\t1. Generar nueva matriz.");
      System.out.println("\t2. Calcular matriz inversa.");
      System.out.println("\t3. Generar PDF.");

      // Read the user input
      option = read("Opción: ", 0, 3);

      // Execute the selected option
      switch (option) {
        case 0:
          System.out.println("Saliendo...");
          break;
        case 1:
          height = read("Introduzca la altura (de 2 a 5): ", 2, 5);
          width = read("Introduzca la altura (de 2 a 5): ", 2, 5);
          random = new Random();
          matrix = SimpleMatrix.random(height, width, 0, 10, random);
          break;
        case 2:
          // Calculate the inverse matrix (height and width must be equal)
          if (height == width) 
            try {
              matrix = matrix.invert();
            } catch (Exception e) {
              System.out.println("Error calculando matriz inversa.");
              System.err.println(e);
            }
          else
            System.err.println("Para generar la inversa de una matriz, esta debe ser cuadrada.");
          break;
        case 3:
          // Generate the PDF
          PDF matrixPdf = new PDF("Matriz generada:\n \n" + content, "Matriz.pdf");
          matrixPdf.save();
          break;
      }
      if (option != 0) System.out.println("\n------------------------");
    } while (option != 0);

    s.close();
  }
}
