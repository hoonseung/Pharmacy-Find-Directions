package com.backend.finddirections.base62

import com.backend.finddirections.direction.service.Base62Service
import spock.lang.Specification


class Base62ServiceTest extends Specification {


    private Base62Service base62Service

    def setup() {
        base62Service = new Base62Service();
    }


    def "base62 인코딩 / 디코딩 검증"() {
        given:
        long num = 5;

        when:
        def encodedId = base62Service.encodeDirectionId(num)
        def decodedId = base62Service.decodeDirectionId(encodedId)

        then:
        num == decodedId
    }

}