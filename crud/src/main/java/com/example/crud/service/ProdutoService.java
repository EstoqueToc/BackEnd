package com.example.crud.service;

import com.example.crud.Model.Alerta;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaResposta.ProdutoRespostaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import com.example.crud.repository.AlertaRepository;
import com.example.crud.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaDto.ProdutoConsultaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import com.example.crud.repository.CategoriaRepository;
import com.example.crud.repository.FornecedorRepository;
import com.example.crud.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private final ProdutoRepository repository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private ModelMapper mapper;


    @Autowired
    public ProdutoService(ProdutoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    public ProdutoRespostaDto criarProduto(ProdutoCriacaoDto novoProdutoDto) {
        Produto novoProduto = mapper.map(novoProdutoDto, Produto.class);

        // Configurar e salvar os alertas
        if (novoProduto.getAlertaEstoque() != null) {
            for (Alerta alerta : novoProduto.getAlertaEstoque()) {
                alerta.setProduto(novoProduto);
            }
        }

        // Salvar o produto (e os alertas devido ao CascadeType.ALL)
        Produto produtoSalvo = repository.save(novoProduto);

        // Mapear a entidade salva para o DTO de resposta
        return mapper.map(produtoSalvo, ProdutoRespostaDto.class);
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
                .map(produto -> modelMapper.map(produto, ProdutoConsultaDto.class))
                .collect(Collectors.toList());
    }

    public List<Produto> buscarPorEstoque(int qtdEstoque) {
        return repository.findByQtdEstoqueGreaterThanEqual(qtdEstoque);
    }

    public Optional<ProdutoConsultaDto> listarProdutoPorId(Long id) {
        return repository.findById(id).map(produto -> modelMapper.map(produto, ProdutoConsultaDto.class));
    }

    public List<Produto> getProdutosPorCategoria(String categoria) {
        return repository.findByCategoriaNomeIgnoreCase(categoria);
    }

    public List<Produto> buscarPorFaixaPreco(Double precoMinimo, Double precoMaximo) {
        return repository.findByPrecoDeVendaBetween(precoMinimo, precoMaximo);
    }

    public Optional<Produto> adicionarEstoque(Long id, Integer quantidadeAdicional) {
        return repository.findById(id).map(produto -> {
            produto.setQtdEstoque(produto.getQtdEstoque() + quantidadeAdicional);
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
        return repository.findAllByOrderByNomeAsc();
    }

    public List<Produto> ordenarPorPreco() {
        return repository.findAllByOrderByPrecoDeVendaAsc();
    }

    public List<Produto> listarPorValidade() {
        return repository.findAllByOrderByDataDeValidadeAsc();
    }

    public List<Produto> listarPorDataEntrada() {
        return repository.findAllByOrderByDataDeEntradaAsc();
    }

    public List<Produto> listarPorEstoque() {
        return repository.findAllByOrderByQtdEstoqueAsc();
    }

    public List<Produto> pesquisarProdutoPorNome(String nome) {
        return repository.findByNomeContainsIgnoreCase(nome);
    }


}
