package com.mixajlenko.ispspring.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.mixajlenko.ispspring.dto.TariffsByServiceDto;
import com.mixajlenko.ispspring.service.TariffService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller
public class FileDownloadController {

    @Autowired
    TariffService tariffService;


    @SneakyThrows
    @GetMapping(
            value = "/download",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody
    byte[] getFile(@RequestParam(value = "format") String format, @RequestParam(value = "serviceName", required = false) Optional<String> serviceName, @RequestParam(value = "serviceId", required = false) Long serviceId, HttpServletResponse response) {
        StringBuilder sb;
        switch (format) {
            case "docx":
                response.setHeader("Content-Disposition", "attachment;filename=Tariffs.doc");
                break;
            case "csv":
                response.setHeader("Content-Disposition", "attachment;filename=Tariffs.csv");
                break;
            case "pdf":
                response.setHeader("Content-Disposition", "attachment;filename=Tariffs.pdf");
                break;
            case "txt":
                response.setHeader("Content-Disposition", "attachment;filename=Tariffs.txt");
                break;
            default:
        }
        response.setHeader("Content-Transfer-Encoding", "binary");
        if (serviceName.isPresent()) {
            sb = generateDownloadFileBuffer(serviceName.orElse("All tariffs"), tariffService.allTariffsBySvcId(serviceId));
        } else {
            sb = generateDownloadFileBuffer(serviceName.orElse("All tariffs"), tariffService.allTariffs());

        }
        InputStream in =
                new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));

        if (format.contains("pdf")) {
            Document document = new Document();

            ByteArrayOutputStream targetStream = new ByteArrayOutputStream();
            in.transferTo(targetStream);

            PdfWriter.getInstance(document, targetStream);

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk(sb.toString(), font);

            document.add(new Paragraph(chunk));
            document.close();
            return targetStream.toByteArray();
        }
        in.close();
        return IOUtils.toByteArray(in);
    }

    private static StringBuilder generateDownloadFileBuffer(String serviceName, List<TariffsByServiceDto> tariffs) {
        var writer = new StringBuilder();

        writer.append(serviceName);
        writer.append('\n');
        writer.append("Name");
        writer.append(",");
        writer.append("Description");
        writer.append(",");
        writer.append("Price");
        writer.append('\n');

        for (TariffsByServiceDto tariff : tariffs) {
            writer.append(tariff.getName());
            writer.append(",");
            writer.append(tariff.getDescription());
            writer.append(",");
            writer.append(tariff.getPrice());
            writer.append('\n');
        }
        return writer;
    }
}
