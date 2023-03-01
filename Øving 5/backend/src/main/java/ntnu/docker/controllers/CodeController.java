package ntnu.docker.controllers;

import ntnu.docker.models.Code;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class CodeController {

    @PostMapping("/compile")
    public String compile(@RequestBody Code code) {
        String output = "";

        // Build the Docker command to compile the Java code
        String[] command = {
                //"docker", "run", "--rm", "-i", "java-image", "javac", "-"
                "docker", "run", "--rm", "python:latest", "python", "-c", code.getCode()
        };

        try {
            // Create a ProcessBuilder to run the Docker command
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Write the Java code to the input stream of the Docker command
            process.getOutputStream().write(code.getCode().getBytes());
            process.getOutputStream().close();

            // Read the output from the Docker command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
            }

            // Wait for the process to finish and get the exit code
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                output += "Compilation failed with exit code " + exitCode;
            }
        } catch (IOException | InterruptedException e) {
            output += "Error: " + e.getMessage();
        }
        System.out.println(output);

        return output;
    }
}
