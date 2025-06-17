package com.physiotrack.backend.service;

import com.physiotrack.backend.exceptions.ObjectNotFoundException;
import com.physiotrack.backend.model.atendimento.Atendimento;
import com.physiotrack.backend.model.avaliacao.Avaliacao;
import com.physiotrack.backend.model.avaliacao.AvaliacaoRequestDTO;
import com.physiotrack.backend.model.avaliacao.AvaliacaoResponseDTO;
import com.physiotrack.backend.model.user.User;
import com.physiotrack.backend.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        avaliacao.setData(LocalDateTime.now());
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

    public byte[] gerarPdf(Long id) throws JRException, IOException {
        // Busca a avaliação
        Avaliacao avaliacao = findAvaliacaoById(id);

        // Carrega a imagem do classpath
        InputStream imageStream = getClass().getResourceAsStream("/jasper/av_V_1/imgBg1.png");
        BufferedImage image = null;
        if(imageStream != null){
          image = ImageIO.read(imageStream);
        }
        InputStream imageStreamPacientePlaceHolder = getClass().getResourceAsStream("/jasper/av_V_1/placeholder-woman.png");
        BufferedImage imagePacientePlaceHolder = null;
        if(imageStreamPacientePlaceHolder != null){
            imagePacientePlaceHolder = ImageIO.read(imageStreamPacientePlaceHolder);
        }
        LocalDateTime dataAtendimento = avaliacao.getAtendimento().getDataAtendimento();
        String dataFormatada = dataAtendimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        // Define os parâmetros
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("bgImage", image);
        parametros.put("pacienteFotoPlaceHolder", imagePacientePlaceHolder);
        parametros.put("nomePaciente", avaliacao.getAtendimento().getPaciente().getNome());
        parametros.put("nomeFisioterapeuta", avaliacao.getAtendimento().getUsuario().getPessoa().getNome());
        parametros.put("data", dataFormatada);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(avaliacao));
        //
        InputStream jrxml = getClass().getResourceAsStream("/jasper/av_V_1/AV_V_1.jrxml");
        JasperReport report = JasperCompileManager.compileReport(jrxml);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, dataSource);
//        // Carregar o template compilado
//        InputStream jasperStream = getClass().getResourceAsStream("/jasper/av_V_1/AV_V_1.jasper");
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parametros, dataSource);

        // Exportar para PDF
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
        return pdf;
    }

}