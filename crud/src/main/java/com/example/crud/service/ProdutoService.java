package com.example.crud.service;

import com.example.crud.Model.Alerta;
import com.example.crud.Model.Estoque;
import com.example.crud.Model.Produto;
import com.example.crud.dto.consultaResposta.ProdutoRespostaDto;
import com.example.crud.dto.criacaoDto.ProdutoCriacaoDto;
import com.example.crud.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.crud.dto.consultaDto.ProdutoConsultaDto;
import com.example.crud.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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
            novoProduto.getAlerta().forEach(alertaRepository::save);
        }

        novoProduto.setDataEntrada(LocalDate.now());

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

    public void salvarProdutosEmLote(MultipartFile file) throws IOException {
        List<Produto> produtos = lerXlsx(file);
        repository.saveAll(produtos);
    }

    private List<Produto> lerXlsx(MultipartFile file) throws IOException {
        List<Produto> produtos = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);  // Pega a primeira aba do Excel
            Iterator<Row> rowIterator = sheet.iterator();

            // Ignora a primeira linha se for o cabeçalho
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Produto produto = new Produto();

                produto.setNomeProduto(getCellValue(row.getCell(0)));
                produto.setDescricaoProduto(getCellValue(row.getCell(1)));
                produto.setPrecoVendaProduto(Double.parseDouble(getCellValue(row.getCell(2))));
                produto.setQtdEntrada(Integer.parseInt(getCellValue(row.getCell(3))));

                produtos.add(produto);
            }
        }

        return produtos;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }


}
