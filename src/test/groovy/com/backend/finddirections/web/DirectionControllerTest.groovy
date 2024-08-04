package com.backend.finddirections.web

import com.backend.finddirections.direction.service.DirectionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DirectionControllerTest extends Specification {

    private MockMvc mockMvc

    private DirectionService directionService = Mock()


    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService)).build()
    }


    def "GET /dir/{encodedId} 리다이렉트 검증"() {
        given:
        String encodedId = "EN3"
        String redirectUrl = "https://map.kakao.com/link/map/pharmacy,38.11,128.11"

        when:
        directionService.getDirectionUrlByEncodedId(encodedId) >> redirectUrl
        def resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/dir/{encodedId}", encodedId))

        then:
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl))
                .andDo(print())

    }
}