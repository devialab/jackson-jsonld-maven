# jackson-jsonld-maven
Maven plugin for jackson-jsonld

## Usage

```
<build>
    <plugins>
        <plugin>
            <groupId>com.devialab</groupId>
            <artifactId>jackson-jsonld-maven</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <executions>
                <execution>
                    <phase>process-resources</phase>
                    <goals>
                        <goal>generate-context</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

Configuration options:

```
<configuration>
    <dataCaptureFile>src/main/resources/data-capture.json</dataCaptureFile>
    <jarCacheFile>src/main/resources/jarcache.json</jarCacheFile>
</configuration>

```