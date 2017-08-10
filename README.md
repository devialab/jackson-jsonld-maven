# jackson-jsonld-maven
Maven plugin for jackson-jsonld

## Usage

```
<build>
    <plugins>
        <plugin>
            <groupId>com.devialab</groupId>
            <artifactId>jackson-jsonld-maven</artifactId>
            <version>0.0.2-SNAPSHOT</version>
            <configuration>
                <outputDirectory>${project.build.outputDirectory}</outputDirectory>
                <fileName>data-capture.json</fileName>
                <packageName>com.solera.global.datacapture.model.domain</packageName>
                <contentLocation>http://schemas.solera.com/data-capture/</contentLocation>
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