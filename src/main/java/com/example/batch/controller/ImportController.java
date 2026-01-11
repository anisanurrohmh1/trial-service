package com.example.batch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/import")
@Tag(name = "Import CSV", description = "API untuk import file CSV ke database via Spring Batch")
public class ImportController {

    private final JobLauncher jobLauncher;
    private final Job importTransactionJob;

    public ImportController(JobLauncher jobLauncher, Job importTransactionJob) {
        this.jobLauncher = jobLauncher;
        this.importTransactionJob = importTransactionJob;
    }

    @Operation(
            summary = "Import CSV Transaction",
            description = "Upload file CSV untuk diproses Spring Batch"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File berhasil dikirim ke Batch Job"),
            @ApiResponse(responseCode = "400", description = "File tidak valid"),
            @ApiResponse(responseCode = "500", description = "Error internal server")
    })
    @PostMapping(value = "/csv", consumes = "multipart/form-data")
    public ResponseEntity<String> importCsv(
            @Parameter(description = "File CSV yang akan diimport", required = true)
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        String path = System.getProperty("java.io.tmpdir") + file.getOriginalFilename();
        Files.copy(file.getInputStream(),
                Paths.get(path),
                StandardCopyOption.REPLACE_EXISTING);

        JobParameters params = new JobParametersBuilder()
                .addString("filePath", path)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(importTransactionJob, params);

        return ResponseEntity.ok("Import CSV sedang diproses");
    }
}
