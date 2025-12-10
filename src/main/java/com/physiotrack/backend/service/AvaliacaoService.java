package com.physiotrack.backend.service;

import com.physiotrack.backend.exceptions.ObjectNotFoundException;
import com.physiotrack.backend.model.atendimento.Atendimento;
import com.physiotrack.backend.model.avaliacao.Avaliacao;
import com.physiotrack.backend.model.avaliacao.AvaliacaoRequestDTO;
import com.physiotrack.backend.model.user.User;
import com.physiotrack.backend.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private final AtendimentoService atendimentoService;

    private final UserService userService;

    private final ModelMapper mapper;

    public Avaliacao register (AvaliacaoRequestDTO dto, Long atendimentoId){
        User user = userService.getLoggedUser();
        Atendimento atendimento = atendimentoService.obterAtendimento(atendimentoId);
        Avaliacao avaliacao = new Avaliacao();
        mapper.map(dto, avaliacao);
        avaliacao.setAtendimento(atendimento);
        avaliacao.setData(LocalDate.now());
        insetAvaliacao(avaliacao);
        return avaliacao;
    }

    public void insetAvaliacao(Avaliacao avaliacao){
        avaliacaoRepository.save(avaliacao);
    }

    public Avaliacao findAvaliacaoById(Long id){
        return avaliacaoRepository.findById(id)
                .orElseThrow(()->  new ObjectNotFoundException("Avaliação não encontrada"));
    }

    public Avaliacao findLast(Long atendimentoId){
        return avaliacaoRepository.findLast(atendimentoId)
                .orElseThrow(()->  new ObjectNotFoundException("Avaliação não encontrada"));
    }

    public List<Avaliacao> findAll(Long pacienteId){
        return avaliacaoRepository.findAll(pacienteId);
    }

    public List<Avaliacao> findByPeriodo(Long pacienteId, LocalDate dataInicial){
        return avaliacaoRepository.findAllByPeriodoAndPaciente(pacienteId, dataInicial);
    }

    public byte[] gerarPdf(Long id) throws JRException, IOException {
        // Busca a avaliação
        Avaliacao avaliacao = findLast(id);

        // Carrega a imagem do classpath
        InputStream imageStream = getClass().getResourceAsStream("/jasper/av_V_2/imgBg1.png");
        BufferedImage image = null;
        if(imageStream != null){
          image = ImageIO.read(imageStream);
        }
        InputStream imageStreamPacientePlaceHolder = getClass().getResourceAsStream("/jasper/av_V_2/placeholder-woman.png");
        BufferedImage imagePacientePlaceHolder = null;
        if(imageStreamPacientePlaceHolder != null){
            imagePacientePlaceHolder = ImageIO.read(imageStreamPacientePlaceHolder);
        }
        LocalDateTime dataAtendimento = avaliacao.getAtendimento().getDataAtendimento();
        String dataFormatada = dataAtendimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        //
        Boolean perimetriaEmpty = Boolean.FALSE;
        Boolean lungeEmpty = Boolean.FALSE;
        Boolean quadrilEmpty = Boolean.FALSE;
        Boolean flexJoelhoEmpty = Boolean.FALSE;
        Boolean singleHopEmpty = Boolean.FALSE;
        Boolean sideHopEmpty = Boolean.FALSE;
        Boolean slbEmpty = Boolean.FALSE;
        //
        if(avaliacao.getPerimetriaMedida1D() == null){
            perimetriaEmpty = Boolean.TRUE;
        }
        if(avaliacao.getLungeD() == null){
            lungeEmpty = Boolean.TRUE;
        }
        if(avaliacao.getRotQuadInterD() == null){
            quadrilEmpty = Boolean.TRUE;
        }
        if(avaliacao.getFlexJoelhoD() == null){
            flexJoelhoEmpty = Boolean.TRUE;
        }
        if(avaliacao.getShTest1D() == null){
            singleHopEmpty = Boolean.TRUE;
        }
        if(avaliacao.getSdhTest1D() == null){
            sideHopEmpty = Boolean.TRUE;
        }
        if(avaliacao.getSlbTestD() == null){
            slbEmpty = Boolean.TRUE;
        }
        // Define os parâmetros
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("bgImage", image);
        parametros.put("pacienteFotoPlaceHolder", imagePacientePlaceHolder);
        parametros.put("nomePaciente", avaliacao.getAtendimento().getPaciente().getNome());
        parametros.put("nomeFisioterapeuta", avaliacao.getAtendimento().getUsuario().getPessoa().getNome());
        parametros.put("data", dataFormatada);
        //
        parametros.put("perimetriaEmpty", perimetriaEmpty );
        parametros.put("lungeEmpty", lungeEmpty);
        parametros.put("quadrilEmpty", quadrilEmpty);
        parametros.put("flexJoelhoEmpty", flexJoelhoEmpty);
        parametros.put("singleHopEmpty", singleHopEmpty);
        parametros.put("sideHopEmpty", sideHopEmpty);
        parametros.put("slbEmpty", slbEmpty);
        //
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(avaliacao));
        //
        InputStream jrxml = getClass().getResourceAsStream("/jasper/av_V_2/AV_V_2.jrxml");
        JasperReport report = JasperCompileManager.compileReport(jrxml);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, dataSource);
//        // Carregar o template compilado
//        InputStream jasperStream = getClass().getResourceAsStream("/jasper/av_V_1/AV_V_1.jasper");
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parametros, dataSource);

        // Exportar para PDF
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
        return pdf;
    }

    public byte[] gerarPdfs(Long pacienteId, LocalDate dataInicial) throws JRException, IOException {
        // Busca as avaliações conforme o parâmetro
        List<Avaliacao> avaliacoes;
        if (dataInicial == null) {
            avaliacoes = findAll(pacienteId);
        } else {
            avaliacoes = findByPeriodo(pacienteId, dataInicial);
        }

        if (avaliacoes.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma avaliação encontrada para os critérios informados.");
        }

        // Carrega imagens e template
        InputStream imageStream = getClass().getResourceAsStream("/jasper/av_V_2/imgBg1.png");
        BufferedImage image = (imageStream != null) ? ImageIO.read(imageStream) : null;

        InputStream imageStreamPacientePlaceHolder = getClass().getResourceAsStream("/jasper/av_V_2/placeholder-woman.png");
        BufferedImage imagePacientePlaceHolder = (imageStreamPacientePlaceHolder != null)
                ? ImageIO.read(imageStreamPacientePlaceHolder)
                : null;

        InputStream jrxml = getClass().getResourceAsStream("/jasper/av_V_2/AV_V_2.jrxml");
        JasperReport report = JasperCompileManager.compileReport(jrxml);

        List<JasperPrint> jasperPrints = new ArrayList<>();
        //
        Boolean perimetriaEmpty = Boolean.FALSE;
        Boolean lungeEmpty = Boolean.FALSE;
        Boolean quadrilEmpty = Boolean.FALSE;
        Boolean flexJoelhoEmpty = Boolean.FALSE;
        Boolean singleHopEmpty = Boolean.FALSE;
        Boolean sideHopEmpty = Boolean.FALSE;
        Boolean slbEmpty = Boolean.FALSE;
        //
        for (Avaliacao avaliacao : avaliacoes) {
            //
//            if(avaliacao.getPerimetriaMedida1D() == null){
//                perimetriaEmpty = Boolean.TRUE;
//            }
//            if(avaliacao.getLungeD() == null){
//                lungeEmpty = Boolean.TRUE;
//            }
//            if(avaliacao.getRotQuadInterD() == null){
//                quadrilEmpty = Boolean.TRUE;
//            }
//            if(avaliacao.getFlexJoelhoD() == null){
//                flexJoelhoEmpty = Boolean.TRUE;
//            }
//            if(avaliacao.getShTest1D() == null){
//                singleHopEmpty = Boolean.TRUE;
//            }
//            if(avaliacao.getSdhTest1D() == null){
//                sideHopEmpty = Boolean.TRUE;
//            }
//            if(avaliacao.getSlbTestD() == null){
//                slbEmpty = Boolean.TRUE;
//            }
            //
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("bgImage", image);
            parametros.put("pacienteFotoPlaceHolder", imagePacientePlaceHolder);
            parametros.put("nomePaciente", avaliacao.getAtendimento().getPaciente().getNome());
            parametros.put("nomeFisioterapeuta", avaliacao.getAtendimento().getUsuario().getPessoa().getNome());
            //
            parametros.put("perimetriaEmpty", perimetriaEmpty );
            parametros.put("lungeEmpty", lungeEmpty);
            parametros.put("quadrilEmpty", quadrilEmpty);
            parametros.put("flexJoelhoEmpty", flexJoelhoEmpty);
            parametros.put("singleHopEmpty", singleHopEmpty);
            parametros.put("sideHopEmpty", sideHopEmpty);
            parametros.put("slbEmpty", slbEmpty);
            //

            LocalDateTime dataAtendimento = avaliacao.getAtendimento().getDataAtendimento();
            String dataFormatada = dataAtendimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            parametros.put("data", dataFormatada);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(avaliacao));
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, dataSource);
            jasperPrints.add(jasperPrint);
        }

        // Exporta todos os relatórios em um PDF combinado
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(configuration);

        exporter.exportReport();

        // ---- Remove as páginas pares ----
        byte[] pdfBytes = outputStream.toByteArray();

        try (PDDocument document = PDDocument.load(pdfBytes);
             ByteArrayOutputStream filteredOutput = new ByteArrayOutputStream()) {

            PDDocument filteredDoc = new PDDocument();

            int totalPages = document.getNumberOfPages();
            for (int i = 0; i < totalPages; i++) {
                // i começa em 0 → página 1 é i=0 (ímpar)
                if ((i + 1) % 2 != 0) { // mantém apenas páginas ímpares
                    filteredDoc.addPage(document.getPage(i));
                }
            }

            filteredDoc.save(filteredOutput);
            filteredDoc.close();

            return filteredOutput.toByteArray();
        }
    }


}