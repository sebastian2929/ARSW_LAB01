## Escuela Colombiana de Ingeniería
### Arquitecturas de Software
### Introducción al paralelismo - hilos

### Trabajo individual o en parejas

Entrega: Martes en el transcurso del día.
Entregar: Fuentes y documento PDF con las respuestas.

**Parte I Hilos Java**

1. De acuerdo con lo revisado en las lecturas, complete las clases CountThread, para que las mismas definan el ciclo de vida de un hilo que imprima por pantalla los números entre A y B.

```
public class CountThread extends Thread{
    private final int in;
    private final int out;

    public CountThread(int in, int out){
        this.in = in;
        this.out = out;
    }

    @Override
    public void run(){
        for(int i = in; i <= out; i++){
            System.out.println(i);
        }
    }
}
```


2. Complete el método __main__ de la clase CountMainThreads para que:
	1. Cree 3 hilos de tipo CountThread, asignándole al primero el intervalo [0..99], al segundo [99..199], y al tercero [200..299].

	```
    public static void main(String a[]){
        CountThread T1 = new CountThread(0,99);
        CountThread T2 = new CountThread(100,199);
        CountThread T3 = new CountThread(200,299);

    }
    
	```

	2. Inicie los tres hilos con 'start()'.

	```
	public static void main(String a[]){
        CountThread T1 = new CountThread(0,99);
        CountThread T2 = new CountThread(100,199);
        CountThread T3 = new CountThread(200,299);

        T1.start();
        T2.start();
        T3.start();
    }
	```
	3. Ejecute y revise la salida por pantalla. 
	4. Cambie el incio con 'start()' por 'run()'. Cómo cambia la salida?, por qué?.

	```
	public static void main(String a[]){
        CountThread T1 = new CountThread(0,99);
        CountThread T2 = new CountThread(100,199);
        CountThread T3 = new CountThread(200,299);

        T1.run();
        T2.run();
        T3.run();
    }
	```

**Parte II Hilos Java**

La fórmula [BBP](https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula) (Bailey–Borwein–Plouffe formula) es un algoritmo que permite calcular el enésimo dígito de PI en base 16, con la particularidad de no necesitar calcular nos n-1 dígitos anteriores. Esta característica permite convertir el problema de calcular un número masivo de dígitos de PI (en base 16) a uno [vergonzosamente paralelo](https://en.wikipedia.org/wiki/Embarrassingly_parallel). En este repositorio encontrará la implementación, junto con un conjunto de pruebas. 

Para este ejercicio se quiere calcular, en el menor tiempo posible, y en una sola máquina (aprovechando las características multi-core de la mismas) al menos el primer millón de dígitos de PI (en base 16). Para esto

1. Cree una clase de tipo Thread que represente el ciclo de vida de un hilo que calcule una parte de los dígitos requeridos.

```
public class ThreadCalculate extends Thread {
    private final int start;
    private final int last;
    private String thread;
    private String result;

    public ThreadCalculate(String NameOfThread, int a, int b) {
        this.start = a;
        this.last = b;
        this.thread = NameOfThread;
    }

    @Override
    public void run(){
        //Start an empty array where we're going to store the process's digits
        byte[] digits;
        //Invoke the function which obtains the digits of the proccess specifying the range of numbers
        digits = PiDigits.getDigits(start, last);
        //The local variable is going to store the Hexadecimal equivalency
        this.result = PiDigits.bytesToHex(digits);
    }
}
```
2. Haga que la función PiDigits.getDigits() reciba como parámetro adicional un valor N, correspondiente al número de hilos entre los que se va a paralelizar la solución. Haga que dicha función espere hasta que los N hilos terminen de resolver el problema para combinar las respuestas y entonces retornar el resultado. Para esto, revise el método [join](https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html) del API de concurrencia de Java.

**Solución**
Para realizar este ejercicio utilizamos la clase __PiDigits__ como orquestador, enviandole al constructor el parámetro N que indica la cantidad de hilos que debe utilizar el programa y así creamos un método aparte __(Orchestor())__ el cual se encarga de crear todos los hilos y arrancarlos así obteniendo y retornando el resultado esperado.

3. Ajuste las pruebas de JUnit, considerando los casos de usar 1, 2 o 3 hilos (este último para considerar un número impar de hilos!)


**Parte III Evaluación de Desempeño**

A partir de lo anterior, implemente la siguiente secuencia de experimentos para calcular el millon de dígitos (hex) de PI, tomando los tiempos de ejecución de los mismos (asegúrese de hacerlos en la misma máquina):

1. Un solo hilo.
2. Tantos hilos como núcleos de procesamiento (haga que el programa determine esto haciendo uso del [API Runtime](https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html)).
3. Tantos hilos como el doble de núcleos de procesamiento.
4. 200 hilos.
5. 500 hilos.

Al iniciar el programa ejecute el monitor jVisualVM, y a medida que corran las pruebas, revise y anote el consumo de CPU y de memoria en cada caso. ![](img/jvisualvm.png)

Con lo anterior, y con los tiempos de ejecución dados, haga una gráfica de tiempo de solución vs. número de hilos. Analice y plantee hipótesis con su compañero para las siguientes preguntas (puede tener en cuenta lo reportado por jVisualVM):



1. Según la [ley de Amdahls](https://www.pugetsystems.com/labs/articles/Estimating-CPU-Performance-using-Amdahls-Law-619/#WhatisAmdahlsLaw?):

	![](img/ahmdahls.png), donde _S(n)_ es el mejoramiento teórico del desempeño, _P_ la fracción paralelizable del algoritmo, y _n_ el número de hilos, a mayor _n_, mayor debería ser dicha mejora. Por qué el mejor desempeño no se logra con los 500 hilos?, cómo se compara este desempeño cuando se usan 200?. 

2. Cómo se comporta la solución usando tantos hilos de procesamiento como núcleos comparado con el resultado de usar el doble de éste?.

3. De acuerdo con lo anterior, si para este problema en lugar de 500 hilos en una sola CPU se pudiera usar 1 hilo en cada una de 500 máquinas hipotéticas, la ley de Amdahls se aplicaría mejor?. Si en lugar de esto se usaran c hilos en 500/c máquinas distribuidas (siendo c es el número de núcleos de dichas máquinas), se mejoraría?. Explique su respuesta.



#### Criterios de evaluación.

1. Funcionalidad:
	- El problema fue paralelizado (el tiempo de ejecución se reduce y el uso de los núcleos aumenta), y permite parametrizar el número de hilos usados simultáneamente.

2. Diseño:
	- La signatura del método original sólo fue modificada con el parámetro original, y en el mismo debe quedar encapsulado la paralelización e inicio de la solución, y la sincronización de la finalización de la misma.
	- Las nuevas pruebas con sólo UN hilo deben ser exactamente iguales a las originales, variando sólo el parámetro adicional. Se incluyeron pruebas con hilos adicionales, y las mismas pasan.
	- Se plantea un método eficiente para combinar los resultados en el orden correcto (iterar sobre cada resultado NO sera eficiente).

3. Análisis.
	- Se deja evidencia de la realización de los experimentos.
	- Los análisis realizados son consistentes.
