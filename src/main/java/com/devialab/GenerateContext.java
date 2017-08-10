package com.devialab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ioinformarics.oss.jackson.module.jsonld.JsonldContextFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.classworlds.realm.ClassRealm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Mojo(name="generate-context")
public class GenerateContext extends AbstractMojo
{
    @Parameter(required = true)
    private File outputDirectory;

    @Parameter(required = true)
    private File fileName;

    @Parameter(required = true)
    private String packageName;

    @Parameter(required = true)
    private String contentLocation;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException
    {
        try
        {
            addProjectClasspath();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jarCache = mapper.createObjectNode();
            jarCache.put("X-Classpath", fileName.getName());
            jarCache.put("Content-Location", contentLocation);
            jarCache.put("Content-Type", "application/ld+json");

            // Write jar cache file
            Path jarCacheFilePath = Paths.get(outputDirectory.getAbsolutePath(), "jarcache.json");
            if (!Files.exists(jarCacheFilePath))
            {
                Files.createFile(jarCacheFilePath);
            }
            Files.write(jarCacheFilePath, mapper.writeValueAsBytes(jarCache));
            getLog().info("Jar cache file written to: " + jarCacheFilePath);

            // Write output file
            ObjectNode context = JsonldContextFactory.fromPackage(packageName);
            Path filePath = Paths.get(outputDirectory.getAbsolutePath(), fileName.getName());
            if (!Files.exists(filePath))
            {
                Files.createFile(filePath);
            }
            Files.write(filePath, mapper.writeValueAsBytes(context));
            getLog().info("Context file of package '"+ packageName +"' written to: " + filePath);
        }
        catch (JsonProcessingException e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (IOException e)
        {
            throw new MojoExecutionException("Error creating context file. Message: " + e.getMessage(), e);
        }
    }

    private void addProjectClasspath() throws MalformedURLException {
        final PluginDescriptor pluginDescriptor = (PluginDescriptor) getPluginContext().get("pluginDescriptor");
        final ClassRealm classRealm = pluginDescriptor.getClassRealm();
        final File classes = new File(project.getBuild().getOutputDirectory());
        classRealm.addURL(classes.toURI().toURL());
    }
}
