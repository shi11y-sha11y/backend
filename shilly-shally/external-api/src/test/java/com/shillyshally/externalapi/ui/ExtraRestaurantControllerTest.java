package com.shillyshally.externalapi.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shillyshally.coredomain.extrarestaurant.ApprovalStatus;
import com.shillyshally.externalapi.application.ExtraRestaurantService;
import com.shillyshally.externalapi.application.dto.ExtraRestaurantRequest;
import com.shillyshally.externalapi.application.dto.ExtraRestaurantResponse;
import com.shillyshally.externalapi.application.dto.PagingExtraRestaurantsResponse;
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
@WebMvcTest(ExtraRestaurantController.class)
@Import(RestDocsConfig.class)
class ExtraRestaurantControllerTest {

    @MockBean
    private ExtraRestaurantService extraRestaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 식당_추가_요청_생성_API_문서화() throws Exception {
        ExtraRestaurantRequest request = new ExtraRestaurantRequest("알촌");

        ResultActions results = mockMvc.perform(post("/api/v1/extraRestaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding(StandardCharsets.UTF_8)
        );

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("create-extraRestaurants",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("추가 요청 식당 이름")
                        )
                ));
    }

    @Test
    void 식당_추가_요청_리스트_조회_API_문서화() throws Exception {
        List<ExtraRestaurantResponse> extraRestaurantResponses = List.of(
                new ExtraRestaurantResponse(1L, "알촌", ApprovalStatus.UNCHECKED),
                new ExtraRestaurantResponse(2L, "마라미방", ApprovalStatus.UNCHECKED),
                new ExtraRestaurantResponse(3L, "도스마스", ApprovalStatus.UNCHECKED)
        );
        PagingExtraRestaurantsResponse response = new PagingExtraRestaurantsResponse(extraRestaurantResponses, false);

        given(extraRestaurantService.getAll(any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/v1/extraRestaurants")
                .param("size", "3")
                .param("page", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-extraRestaurants",
                        queryParameters(
                                parameterWithName("size").description("요청 사이즈"),
                                parameterWithName("page").description("요청 페이지")
                        ),
                        responseFields(
                                fieldWithPath("extraRestaurants[].id").type(JsonFieldType.NUMBER)
                                        .description("추가 요청 식당 ID"),
                                fieldWithPath("extraRestaurants[].name").type(JsonFieldType.STRING)
                                        .description("추가 요청 식당 이름"),
                                fieldWithPath("extraRestaurants[].approvalStatus").type(JsonFieldType.STRING)
                                        .description("관리자의 등록 승인 여부"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 여부")
                        )
                ));
    }
}

