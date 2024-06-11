package com.example.crud.service;

import com.example.crud.Model.Alerta;
import com.example.crud.Model.Estoque;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaResposta.ProdutoRespostaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import com.example.crud.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.ProdutoConsultaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import com.example.crud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final CategoriaRepository categoriaRepository;

    private final ProdutoRepository repository;

    private final FornecedorRepository fornecedorRepository;

    private final AlertaRepository alertaRepository;

    private final EstoqueRepository estoqueRepository;

    private final ModelMapper mapper;

    @Transactional
    public ProdutoRespostaDto criarProduto(ProdutoCriacaoDto novoProdutoDto) {
        Produto novoProduto = mapper.map(novoProdutoDto, Produto.class);

        // Validar se categoria e fornecedor existem
        var categoria = categoriaRepository.findById(novoProdutoDto.getCategoria().getId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        var fornecedor = fornecedorRepository.findById(novoProdutoDto.getFornecedor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        // Associar categoria e fornecedor ao produto
        novoProduto.setCategoria(categoria);
        novoProduto.setFornecedor(fornecedor);

        // Configurar e salvar os alertas se existirem
        if (novoProduto.getAlerta() != null) {
            for (Alerta alerta : novoProduto.getAlerta()) {
                alerta.setProduto(novoProduto);
            }
            novoProduto.getAlerta().forEach(alertaRepository::save); // Salvar alertas
        }

        novoProduto.setDataEntrada(LocalDate.now());

        // Salvar o produto (e os alertas devido ao CascadeType.ALL)
        Produto produtoSalvo = repository.save(novoProduto);

        adicionarNoEstoque(produtoSalvo, novoProdutoDto.getQtdEntrada());

        // Mapear a entidade salva para o DTO de resposta
        return mapper.map(produtoSalvo, ProdutoRespostaDto.class);
    }

    private void adicionarNoEstoque(Produto produto, int qtdEntrada) {
        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setEmpresa(produto.getEmpresa());
        estoque.setQtdDisponivel(qtdEntrada);
        estoque.setQtdDisponivel(qtdEntrada);

        estoqueRepository.save(estoque);
    }

//    public ProdutoConsultaDto criarProduto(ProdutoCriacaoDto novoProdutoDto) {
//        Produto novoProduto = modelMapper.map(novoProdutoDto, Produto.class);
////        novoProduto.setCategoria(categoriaRepository.findById(novoProdutoDto.getCategoria().getId()).get());
//        categoriaRepository.save(novoProduto.getCategoria());
//        fornecedorRepository.save(novoProduto.getFornecedor());
//        repository.save(novoProduto);
//        return modelMapper.map(novoProduto, ProdutoConsultaDto.class);
//    }

    public List<ProdutoConsultaDto> getProdutos() {
        List<Produto> lista = repository.findAll();
        return lista.stream()
                .map(produto -> mapper.map(produto, ProdutoConsultaDto.class))
                .collect(Collectors.toList());
    }

    public List<Produto> buscarPorEstoque(int qtdEstoque) {
        return repository.findByQtdEntradaGreaterThanEqual(qtdEstoque);
    }

    public Optional<ProdutoConsultaDto> listarProdutoPorId(Long id) {
        return repository.findById(id).map(produto -> mapper.map(produto, ProdutoConsultaDto.class));
    }

    public List<Produto> getProdutosPorCategoria(String categoria) {
        return repository.findByCategoriaNomeIgnoreCase(categoria);
    }

    public List<Produto> buscarPorFaixaPreco(Double precoMinimo, Double precoMaximo) {
        return repository.findByPrecoVendaProdutoBetween(precoMinimo, precoMaximo);
    }

    public Optional<Produto> adicionarEstoque(Long id, Integer quantidadeAdicional) {
        return repository.findById(id).map(produto -> {
            produto.setQtdEntrada(produto.getQtdEntrada() + quantidadeAdicional);

            repository.save(produto);
            return produto;
        });
    }

    public Optional<Produto> alterarProduto(Long id, Produto produtoAtualizado) {
        if (repository.existsById(id)) {
            produtoAtualizado.setId(id);
            repository.save(produtoAtualizado);
            return Optional.of(produtoAtualizado);
        }
        return Optional.empty();
    }

    public boolean deletarProduto(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Produto> listarProdutos() {
        return repository.findAllByOrderByNomeProdutoAsc();
    }

    public List<Produto> ordenarPorPreco() {
        return repository.findAllByOrderByPrecoVendaProdutoAsc();
    }

    public List<Produto> listarPorValidade() {
        return repository.findAllByOrderByDataValidadeAsc();
    }

    public List<Produto> listarPorDataEntrada() {
        return repository.findAllByOrderByDataEntradaAsc();
    }

    public List<Produto> listarPorEstoque() {
        return repository.findAllByOrderByQtdEntradaAsc();
    }

    public List<Produto> pesquisarProdutoPorNome(String nome) {
        return repository.findByNomeProdutoContainsIgnoreCase(nome);
    }


}
