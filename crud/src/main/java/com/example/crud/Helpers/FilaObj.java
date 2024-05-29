package com.example.crud.Helpers;

public class FilaObj<T> {
    // Atributos
    private int tamanho;
    private T[] fila;

    // Construtor
    public FilaObj(int capaciade)
    {
        this.tamanho = 0;
        this.fila = (T[]) new Object[capaciade];
    }

    // Métodos

    /* Método isEmpty() - retorna true se a fila está vazia e false caso contrário */
    public boolean isEmpty()
    {
        if (this.tamanho == 0)
        {
            return true;
        }
        return false;
    }

    /* Método isFull() - retorna true se a fila está cheia e false caso contrário */
    public boolean isFull()
    {
        if (this.tamanho == this.fila.length)
        {
            return true;
        }
        return false;
    }

    /* Método insert - recebe um elemento e insere esse elemento na fila
                       no índice tamanho, e incrementa tamanho
                       Lançar IllegalStateException caso a fila esteja cheia
     */
    public void insert(T info)
    {
        if (isFull())
        {
            throw new IllegalStateException("Fila cheia");
        }
        this.fila[this.tamanho] = info;
        this.tamanho++;
    }

    /* Método peek - retorna o primeiro elemento da fila, sem removê-lo */
    public T peek()
    {
        if (isEmpty())
        {
            throw new IllegalStateException("Fila vazia");
        }
        return this.fila[0];
    }

    /* Método poll - remove e retorna o primeiro elemento da fila, se a fila não estiver
       vazia. Quando um elemento é removido, a fila "anda", e tamanho é decrementado
       Depois que a fila andar, "limpar" o ex-último elemento da fila, atribuindo null
     */
    public T poll()
    {
        if (isEmpty())
        {
            throw new IllegalStateException("Fila vazia");
        }
        T primeiro = this.fila[0];
        for (int i = 0; i < this.tamanho - 1; i++)
        {
            this.fila[i] = this.fila[i + 1];
        }
        this.fila[this.tamanho - 1] = null;
        this.tamanho--;
        return primeiro;
    }

    /* Método exibe() - exibe o conteúdo da fila */
    public void exibe()
    {
        if (isEmpty())
        {
            throw new IllegalStateException("Fila vazia");
        }
        for (int i = 0; i < this.tamanho; i++)
        {
            System.out.println(this.fila[i]);
        }
    }

    /* Usado nos testes  - complete para que fique certo */
    public int getTamanho()
    {
        return this.tamanho;
    }
}