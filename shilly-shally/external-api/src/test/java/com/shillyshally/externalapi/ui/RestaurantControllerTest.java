package com.shillyshally.externalapi.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.shillyshally.externalapi.application.RestaurantService;
import com.shillyshally.externalapi.application.dto.RestaurantResponse;
import com.shillyshally.externalapi.config.RestDocsConfig;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@WebMvcTest(RestaurantController.class)
@Import(RestDocsConfig.class)
class RestaurantControllerTest {

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 카테고리_구분_없이_식당_랜덤_조회_API_문서화() throws Exception {
        List<RestaurantResponse> response = List.of(
                new RestaurantResponse(1L, "알촌", 1L, "http://abc.com", "http://image.com", 1.0, 1.0),
                new RestaurantResponse(2L, "마라미방", 2L, "http://abc.com", "http://image.com", 1.0, 1.0),
                new RestaurantResponse(3L, "도스마스", 3L, "http://abc.com", "http://image.com", 1.0, 1.0)
        );

        given(restaurantService.getRandoms(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/v1/restaurants/random")
                .param("size", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-random-restaurants",
                        queryParameters(
                                parameterWithName("size").description("요청 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("식당 ID"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("식당 이름"),
                                fieldWithPath("[].categoryId").type(JsonFieldType.NUMBER).description("식당 카테고리 이름"),
                                fieldWithPath("[].informationUrl").type(JsonFieldType.STRING).description("식당 정보"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("식당 사진"),
                                fieldWithPath("[].longitude").type(JsonFieldType.NUMBER).description("식당 경도"),
                                fieldWithPath("[].latitude").type(JsonFieldType.NUMBER).description("식당 위도")
                        )
                ));
    }

    @Test
    void 카테고리별로_식당_랜덤_조회_API_문서화() throws Exception {

        List<RestaurantResponse> response = List.of(
                new RestaurantResponse(1L, "알촌", 1L, "http://abc.com", "http://image.com", 1.0, 1.0),
                new RestaurantResponse(2L, "한양식당", 1L, "http://abc.com", "http://image.com", 1.0, 1.0),
                new RestaurantResponse(3L, "찌개찌개", 1L, "http://abc.com", "http://image.com", 1.0, 1.0)
        );

        given(restaurantService.getRandoms(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/v1/restaurants/random")
                .param("size", "3")
                .param("categoryId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-random-restaurants-by-category",
                        queryParameters(
                                parameterWithName("size").description("요청 사이즈"),
                                parameterWithName("categoryId").description("카테고리 ID")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("식당 ID"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("식당 이름"),
                                fieldWithPath("[].categoryId").type(JsonFieldType.NUMBER).description("식당 카테고리 이름"),
                                fieldWithPath("[].informationUrl").type(JsonFieldType.STRING).description("식당 정보"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("식당 사진"),
                                fieldWithPath("[].longitude").type(JsonFieldType.NUMBER).description("식당 경도"),
                                fieldWithPath("[].latitude").type(JsonFieldType.NUMBER).description("식당 위도")
                        )
                ));
    }
}
