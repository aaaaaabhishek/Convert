package Convert_PDF.to.Word.Controller;

import Convert_PDF.to.Word.service.PdfToWordConversionService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

//@Controller
//public class PdfToWordController {
//
//    @PostMapping("/convert")
//    public ResponseEntity<byte[]> convertPdfToWord(@RequestParam("file") MultipartFile file) {
//        try (InputStream inputStream = file.getInputStream();
//             PDDocument document = PDDocument.load(inputStream)) {
//
//            PDFTextStripper stripper = new PDFTextStripper();
//            String text = stripper.getText(document);
//
//            XWPFDocument wordDocument = new XWPFDocument();
//            XWPFParagraph paragraph = wordDocument.createParagraph();
//            XWPFRun run = paragraph.createRun();
//            run.setText(text);
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            wordDocument.write(outputStream);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", "converted_document.docx");
//
//            return ResponseEntity
//                    .ok()
//                    .headers(headers)
//                    .body(outputStream.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//}

@Controller
@RequestMapping("/convert")
public class PdfToWordController {

    @Autowired
    private PdfToWordConversionService pdfToWordConversionService;

    @PostMapping("/pdf-to-word")
    public ResponseEntity<Resource> convertPdfToWord(@RequestParam("file") MultipartFile file) {
        byte[] wordContents = pdfToWordConversionService.convertPdfToWord(file);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(wordContents));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "converted_document.docx");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
