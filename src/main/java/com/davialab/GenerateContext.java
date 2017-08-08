package com.davialab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ioinformarics.oss.jackson.module.jsonld.JsonldContextFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mojo(name="generate-context")
public class GenerateContext extends AbstractMojo
{
    @Parameter(defaultValue = "src/main/resources/data-capture.json")
    private File dataCaptureFile;

    @Parameter(defaultValue = "src/main/resources/jarcache.json")
    private File jarCacheFile;

    public void execute() throws MojoExecutionException
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jarCache = mapper.createObjectNode();
            jarCache.put("X-Classpath", "http://schemas.solera.com/data-capture/");
            jarCache.put("Content-Location", "http://schemas.solera.com/data-capture/");
            jarCache.put("Content-Type", "application/ld+json");

            // Write cache file
            Path jarCacheFilePath = Paths.get(jarCacheFile.toURI());
            if (!Files.exists(jarCacheFilePath ))
            {
                getLog().info("Creating jar cache file: " + jarCacheFilePath);
                Files.createFile(jarCacheFilePath);
            }
            Files.write(jarCacheFilePath, mapper.writeValueAsBytes(jarCache));
            getLog().info("Jar cache file written to: " + jarCacheFilePath);

            // Write data capture file
            ObjectNode context = JsonldContextFactory.fromPackage("com.solera.global.datacapture.model.domain");
            Path dataCapturePath = Paths.get(dataCaptureFile.toURI());
            if (!Files.exists(dataCapturePath))
            {
                Files.createFile(dataCapturePath);
            }
            Files.write(dataCapturePath, mapper.writeValueAsBytes(context));
            getLog().info("Data capture file written to: " + dataCapturePath);
        }
        catch (JsonProcessingException e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Error creating data capture file. Message: " + e.getMessage(), e);
        }
    }
}
