/**
 * Main
 */
import java.util.Random;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import Circular.CQueue;

public class Main {
    // Initializing Swap and RAM
    public static int[][] swap = new int[100][6];
    public static int[][] ram = new int[10][6];

    // | NRU | FIFO | FIFO-SC | RELÓGIO | WS-CLOCK | --> 5.000 instructions
    public static int instructionsLeft = 5000;

    public static void main(String[] args) {
        Random random = new Random();
        int i;

        // Writing to Swap
        for (i = 0; i < 100; i++) {
            swap[i][0] = i;
            swap[i][1] = i+1;
            swap[i][2] = random.nextInt(50) + 1;
            swap[i][3] = 0;
            swap[i][4] = 0;
            swap[i][5] = random.nextInt(9900) + 100;
        }

        // Writing to RAM
        int pageSelected;
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for(i = 0; i < 10; i++){
            pageSelected = random.nextInt(100);
            while(indexes.contains(pageSelected)){
                pageSelected = random.nextInt(100);
            }
            indexes.add(pageSelected);
            ram[i][0] = swap[pageSelected][0];
            ram[i][1] = swap[pageSelected][1];
            ram[i][2] = swap[pageSelected][2];
            ram[i][3] = swap[pageSelected][3];
            ram[i][4] = swap[pageSelected][4];
            ram[i][5] = swap[pageSelected][5];
        }

        // Simulating
        int instruction;
        Boolean execute = true;
        int[] aux = new int[6];
        Queue<Integer> fifo = new ArrayDeque<Integer>();
        CQueue clock = new CQueue(10);

        // Columns: 0 - Page, 1 - Instruction, 2 - Data, 3 - Access Bit R, 4 - Modified Bit M (0 - Not modified, 1 - Modified), 5 - Time
        while (execute) {
            
            if (instructionsLeft == 4000) {
                for(i = 0; i < 10; i++){
                    fifo.add(ram[i][1]);
                }
            }

            if (instructionsLeft == 3000) {
                for(i = 0; i < 10; i++){
                    fifo.add(ram[i][1]);
                }
            }
            
            if (instructionsLeft == 2000) {
                for(i = 0; i < 10; i++){
                    clock.enqueue(ram[i][1]);
                }
            }

            if (instructionsLeft == 1000) {
                for(i = 0; i < 10; i++){
                    clock.enqueue(ram[i][1]);
                }
            }

            for(i = 0; i < 10; i++){
                instruction = random.nextInt(100);{
                if(ram[i][1] == instruction){
                    ram[i][3] = 1;
                    if(random.nextInt(100) < 30){
                        ram[i][2] += 1;
                        ram[i][4] = 1;
                    }
                } else{
                        // Obs1.: O simulador deverá executar 1000 instruções para cada algoritmo de substituição de página.
                        /* Obs2.: Os algoritmos de substituição de páginas para serem implementados são; NRU, FIFO, FIFO-SC, 
                         RELÓGIO e WS-CLOCK (implementar TODOS). */
                        /* Obs5.: Cada vez que uma página que tiver o Bit M=1, e esta for retirada da memória,
                        Ela deve ser salva em SWAP com o Bit M=0. */
                        /* Obs6.: No início da execução deve ser impresso as MATRIZ RAM e a MATRIZ SWAP,
                        e ao final das 1000 instruções deve ser impresso novamente ambas as matrizes. */
                        if (instructionsLeft == 0) {
                            execute = false;
                        }
                        if(instructionsLeft == 4000 || instructionsLeft == 3000 || instructionsLeft == 2000 || instructionsLeft == 1000){
                            
                            System.out.println("\nRAM");
                            for(i = 0; i < 10; i++){
                                System.out.println(ram[i][0] + " " + ram[i][1] + " " + ram[i][2] + " " + ram[i][3] + " " + ram[i][4] + " " + ram[i][5]);
                            }
                            System.out.println("\nSWAP");
                            for(i = 0; i < 100; i++){
                                System.out.println(swap[i][0] + " " + swap[i][1] + " " + swap[i][2] + " " + swap[i][3] + " " + swap[i][4] + " " + swap[i][5]);
                            }
                        }
                        if(instructionsLeft <= 5000){
                            // Execute NRU
                            for(i = 0; i < 10; i++){
                                if(ram[i][3] == 0 && ram[i][4] == 0){
                                    aux = ram[i];
                                    ram[i] = swap[instruction];
                                    swap[instruction] = aux;
                                    break;
                                }
                                if(ram[i][3] == 0 && ram[i][4] == 1){
                                    ram[i][4] = 0;
                                    aux = ram[i];
                                    ram[i] = swap[instruction];
                                    swap[instruction] = aux;
                                    break;
                                }
                                if(ram[i][3] == 1 && ram[i][4] == 0){
                                    aux = ram[i];
                                    ram[i] = swap[instruction];
                                    swap[instruction] = aux;
                                    break;
                                }
                                if(ram[i][3] == 1 && ram[i][4] == 1){
                                    ram[i][4] = 0;
                                    aux = ram[i];
                                    ram[i] = swap[instruction];
                                    swap[instruction] = aux;
                                    break;
                                }
                            }
                        }
                        if (instructionsLeft <= 4000) {
                            // Execute FIFO
                            if (!fifo.isEmpty()) {
                                int oldestInstruction = fifo.peek();
                                for (i = 0; i < 10; i++) {
                                    if (ram[i][1] == oldestInstruction) {
                                        aux = ram[i];
                                        ram[i] = swap[instruction];
                                        swap[instruction] = aux;
                                        fifo.add(instruction);
                                        fifo.remove();
                                        break;
                                    }
                                }
                            }
                        }
                        if (instructionsLeft <= 3000) {
                            // Execute FIFO-SC
                            if (!fifo.isEmpty()) {
                                int oldestInstruction = fifo.peek();    
                                for (i = 0; i < 10; i++) {
                                    if (ram[i][1] == oldestInstruction && ram[i][3] == 0) {
                                        aux = ram[i];
                                        ram[i] = swap[instruction];
                                        swap[instruction] = aux;
                                        fifo.add(instruction);
                                        fifo.remove();
                                        break;
                                    }
                                    ram[i][3] = 0;
                                }
                            }
                        }
                        if(instructionsLeft <= 2000){
                            // Execute Relógio
                            if (!clock.isEmpty()) {
                                int oldestInstruction = clock.peek();
                                for (i = 0; i < 10; i++) {
                                    if (ram[i][1] == oldestInstruction && ram[i][3] == 0) {
                                        aux = ram[i];
                                        ram[i] = swap[instruction];
                                        swap[instruction] = aux;
                                        clock.enqueue(instruction);
                                        clock.dequeue();
                                        break;
                                    }
                                    ram[i][3] = 0;
                                }
                            }
                        }
                        if(instructionsLeft <= 1000){
                            // Execute WS-CLOCK
                            /* Obs3.: Para a implementação do algoritmo WS-Clock, sortear um número para verificar
                            o envelhecimento da página (EP). O valor de EP deve ser na faixa de 100 a 9999.  
                            Caso o EP <= T, a página ainda está no conjunto de trabalho. Caso  EP > T,
                            a página não fará parte do conjunto de trabalho. */
                            for(i = 0; i < 10; i++){
                                if(ram[i][5] > 100 && ram[i][5] <= 9999){
                                    ram[i][5] -= 100;
                                } else{
                                    ram[i][5] = 0;
                                }
                            }
                            int oldestInstruction = clock.peek();
                            for (i = 0; i < 10; i++) {
                                if (ram[i][1] == oldestInstruction && ram[i][3] == 0) {
                                    aux = ram[i];
                                    ram[i] = swap[instruction];
                                    swap[instruction] = aux;
                                    clock.enqueue(instruction);
                                    clock.dequeue();
                                    break;
                                }
                                ram[i][3] = 0;
                            }
                        }
                        instructionsLeft--;
                    }
                }
            }
            
        }

        // Obs4.: A cada 10 Instruções, o Bit R deve ser zerado para todas as páginas na memória RAM.
        for(i = 0; i < 10; i++){
            ram[i][3] = 0;
        }
    }
}