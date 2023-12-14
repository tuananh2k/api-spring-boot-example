package com.example.helloword.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalController {
    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;

    @Autowired
    public GlobalController(JobLauncher jobLauncher, ApplicationContext applicationContext) {
        this.jobLauncher = jobLauncher;
        this.applicationContext = applicationContext;
    }

    @GetMapping("/")
    public String healthCheck() {
        return "Hello world!";
    }


    @PostMapping("/run-job/{jobName}") // Lấy tên job từ URL
    public ResponseEntity<?> runJob(@PathVariable String jobName) {
        try {
            Job job = (Job) applicationContext.getBean(jobName); // Lấy bean job bằng tên
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);

            return ResponseEntity.ok("Job is running");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Could not start the job: " + e.getMessage());
        }
    }
}
