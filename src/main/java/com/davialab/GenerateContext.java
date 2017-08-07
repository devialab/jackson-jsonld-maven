package com.davialab;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ioinformarics.oss.jackson.module.jsonld.JsonldContextFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name="generate-context")
public class GenerateContext extends AbstractMojo
{
    public void execute() throws MojoExecutionException
    {
        ObjectNode context = JsonldContextFactory.fromPackage("com.solera.global.datacapture.model.domain");
        System.out.println(context);
    }
}
