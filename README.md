# jackson-jsonld-maven
Maven plugin for jackson-jsonld

## Usage

```
<build>
    <plugins>
        <plugin>
            <groupId>com.devialab</groupId>
            <artifactId>jackson-jsonld-maven</artifactId>
            <version>0.0.3</version>
            <configuration>
                <outputDirectory>${project.build.outputDirectory}</outputDirectory>
                <fileName>context.json</fileName>
                <packageName>com.example.domain</packageName>
                <contentLocation>http://example.com/context-jsonld/</contentLocation>
            </configuration>
            <executions>
                <execution>
                    <phase>generate-test-sources</phase>
                    <goals>
                        <goal>generate-context</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```