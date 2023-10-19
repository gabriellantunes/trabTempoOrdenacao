import java.util.Random;

public class ExperimentoOrdenacao {

    public static void main(String[] args) {
        int tamanho = 100000;
        int repeticoes = 10;

        //Cenario 1
        executarExperimento(tamanho, repeticoes, "Aleatório");
        //Cenario 2
        executarExperimento(tamanho, repeticoes, "Decrescente");
        //Cenario 3
        executarExperimento(tamanho, repeticoes, "Crescente");

    }

    public static void executarExperimento(int tamanho, int repeticoes, String tipo) {
        System.out.println("Cenário: " + tipo);

        long tempoTotalMergeSort = 0;
        long tempoTotalQuickSort = 0;
        long tempoTotalBubbleSort = 0;
        long tempoTotalInsertSort = 0;
        long tempoTotalSelectionSort = 0;

        for (int i = 0; i < repeticoes; i++) {
            int[] arr = gerarArray(tamanho, tipo);

            tempoTotalMergeSort += medirTempo(arr.clone(), "MergeSort");
            tempoTotalQuickSort += medirTempo(arr.clone(), "QuickSort");
            tempoTotalBubbleSort += medirTempo(arr.clone(), "BubbleSort");
            tempoTotalInsertSort += medirTempo(arr.clone(), "InsertSort");
            tempoTotalSelectionSort += medirTempo(arr.clone(), "SelectionSort");
        }

        System.out.println("Média MergeSort: " + (tempoTotalMergeSort / repeticoes) + " ms");
        System.out.println("Média QuickSort: " + (tempoTotalQuickSort / repeticoes) + " ms");
        System.out.println("Média BubbleSort: " + (tempoTotalBubbleSort / repeticoes) + " ms");
        System.out.println("Média InsertSort: " + (tempoTotalInsertSort / repeticoes) + " ms");
        System.out.println("Média SelectionSort: " + (tempoTotalSelectionSort / repeticoes) + " ms");
    }


    public static int[] gerarArray(int tamanho, String tipo) {
        int[] arr = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            arr[i] = i;
        }

        switch (tipo) {
            case "Aleatório":
                embaralharArray(arr);
                break;
            case "Decrescente":
                inverterArray(arr);
                break;
        }

        return arr;
    }

    public static long medirTempo(int[] arr, String algoritmo) {
        long inicio = System.currentTimeMillis();

        switch (algoritmo) {
            case "MergeSort":
                mergeSort(arr);
                break;
            case "QuickSort":
                quickSort(arr, 0, arr.length - 1);
                break;
            case "BubbleSort":
                bubbleSort(arr);
                break;
            case "InsertSort":
                insertSort(arr);
                break;
            case "SelectionSort":
                selectionSort(arr);
                break;
        }

        return System.currentTimeMillis() - inicio;
    }

    public static void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        int meio = arr.length / 2;
        int[] esquerda = new int[meio];
        int[] direita = new int[arr.length - meio];

        System.arraycopy(arr, 0, esquerda, 0, meio);
        System.arraycopy(arr, meio, direita, 0, arr.length - meio);

        mergeSort(esquerda);
        mergeSort(direita);

        merge(arr, esquerda, direita);
    }

    public static void merge(int[] arr, int[] esquerda, int[] direita) {
        int i = 0, j = 0, k = 0;
        while (i < esquerda.length && j < direita.length) {
            if (esquerda[i] <= direita[j]) {
                arr[k++] = esquerda[i++];
            } else {
                arr[k++] = direita[j++];
            }
        }
        while (i < esquerda.length) {
            arr[k++] = esquerda[i++];
        }
        while (j < direita.length) {
            arr[k++] = direita[j++];
        }
    }

    public static void quickSort(int[] arr, int inicio, int fim) {
        if (inicio < fim) {
            int pivo = particionar(arr, inicio, fim);
            quickSort(arr, inicio, pivo - 1);
            quickSort(arr, pivo + 1, fim);
        }
    }

    /*public static int particionar(int[] arr, int inicio, int fim) {
        int pivo = arr[fim];
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (arr[j] < pivo) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[fim];
        arr[fim] = temp;

        return i + 1;
    }*/
    public static int particionar(int[] arr, int inicio, int fim) {
        int medio = inicio + (fim - inicio) / 2;
        int a = arr[inicio];
        int b = arr[medio];
        int c = arr[fim];
        int medianaIndex;

        if (a < b) {
            if (b < c) {
                medianaIndex = medio;
            } else if (a < c) {
                medianaIndex = fim;
            } else {
                medianaIndex = inicio;
            }
        } else {
            if (a < c) {
                medianaIndex = inicio;
            } else if (b < c) {
                medianaIndex = fim;
            } else {
                medianaIndex = medio;
            }
        }

        int temp = arr[medianaIndex];
        arr[medianaIndex] = arr[fim];
        arr[fim] = temp;

        int pivo = arr[fim];
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (arr[j] <= pivo) {
                i++;
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }

        int t = arr[i + 1];
        arr[i + 1] = arr[fim];
        arr[fim] = t;

        return i + 1;
    }


    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int chave = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > chave) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = chave;
        }
    }

    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min_idx]) {
                    min_idx = j;
                }
            }
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }

    public static void embaralharArray(int[] arr) {
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            int randomIndex = rand.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = temp;
        }
    }

    public static void inverterArray(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
    }
}