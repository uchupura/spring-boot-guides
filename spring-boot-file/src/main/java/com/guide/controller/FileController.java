package com.guide.controller;

import com.guide.constants.ExcelConstant;
import com.guide.model.DownloadFileRequest;
import com.guide.model.UploadFileRequest;
import com.guide.model.UploadFileResponse;
import com.guide.service.ExcelWriter;
import com.guide.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = FileController.URI)
public class FileController {

    public static final String URI = "file";

    private final FileService fileService;

    @PostMapping("upload")
    public UploadFileResponse upload(@RequestParam("file") MultipartFile file) {

        String fileName = fileService.upload(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("uploads")
    public List<UploadFileResponse> uploads(@RequestParam("files") MultipartFile[] files) {

        return Arrays.asList(files)
                .stream()
                .map(file -> upload(file))
                .collect(Collectors.toList());
    }

    @PostMapping("upload-metadata")
    public UploadFileResponse uploadWidthMetadata(@ModelAttribute UploadFileRequest fileWithMeta) {

        log.info("name : {}, email : {}", fileWithMeta.getName(), fileWithMeta.getEmail());
        String fileName = fileService.upload(fileWithMeta.getFile());

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                fileWithMeta.getFile().getContentType(), fileWithMeta.getFile().getSize());
    }

    @GetMapping("download/{fileName:.+}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = fileService.download(fileName);

        // Try to determine file's content type
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("excel/{fileName:.+}")
    public void downloadExcel(@PathVariable String fileName, @RequestBody DownloadFileRequest cond, HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println(cond.toString());
        long beforeTime = System.currentTimeMillis();

        ExcelWriter writer = ExcelWriter.builder()
                .workbook(new SXSSFWorkbook(ExcelConstant.WINDOW_SIZE))
                .model(initExcelData())
                .request(request)
                .response(response)
                .build();

        writer.create();

        List<List<String>> lists = new ArrayList<>();
        for (int row = 0; row < 1048575; row++) {
//            lists.add(Arrays.asList("A", "a", "가"));
//            List<List<String>> lists = Arrays.asList(
//                    Arrays.asList("A", "a", "가"),
//                    Arrays.asList("B", "b", "나"),
//                    Arrays.asList("C", "c", "다")
//            );
            writer.createBody(Arrays.asList("A", "a", "가"));
        }
        //writer.createBody(lists);
        writer.close();
        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
        System.out.println("시간차이(m) : "+secDiffTime);
    }

    private Map<String, Object> initExcelData() {
        Map<String, Object> map = new HashMap<>();
        map.put(ExcelConstant.FILE_NAME, "default_excel");
        map.put(ExcelConstant.HEAD, Arrays.asList("ID", "NAME", "COMMENT"));
//        map.put(ExcelConstant.BODY,
//                Arrays.asList(
//                        Arrays.asList("A", "a", "가"),
//                        Arrays.asList("B", "b", "나"),
//                        Arrays.asList("C", "c", "다")
//                ));
        return map;
    }
}
