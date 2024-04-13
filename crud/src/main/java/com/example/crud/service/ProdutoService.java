package com.example.crud.service;

import com.example.crud.Model.Produto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProdutoService {

    public ResponseEntity<List<Produto>> ordenacaoQuickSort(Produto[] v, int indInicio, int indFim){
        int i = indInicio;
        int j = indFim;
        Produto pivo = v[(indInicio + indFim) / 2];

        while (i <= j){
            while (v[i].compareTo(pivo) < 0){
                i++;
            }
            while (v[j].compareTo(pivo) > 0){
                j--;
            }
            if (i <= j){
                Produto aux = v[i];
                v[i] = v[j];
                v[j] = aux;
                i++;
                j--;
            }
        }
        if (indInicio < j){
            ordenacaoQuickSort(v, indInicio, j);
        }
        if (i < indFim){
            ordenacaoQuickSort(v, i, indFim);
        }
        return ResponseEntity.status(200).body(List.of(v));
    }
}
