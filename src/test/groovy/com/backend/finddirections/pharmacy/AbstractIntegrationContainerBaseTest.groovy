package com.backend.finddirections.pharmacy

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

@ActiveProfiles("intergration")
@SpringBootTest
abstract class AbstractIntegrationContainerBaseTest extends Specification {

    @Shared
    static final GenericContainer myRedis


    static {
        myRedis = new GenericContainer(DockerImageName.parse("redis:7.0"))
                .withExposedPorts(6379)

        myRedis.start()

        System.setProperty("spring.data.redis.host", myRedis.getHost())
        System.setProperty("spring.data.redis.port", myRedis.getMappedPort(6379).toString())
    }


}
