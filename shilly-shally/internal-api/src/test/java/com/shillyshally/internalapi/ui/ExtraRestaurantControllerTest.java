package com.shillyshally.internalapi.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shillyshally.internalapi.application.ExtraRestaurantService;
import com.shillyshally.internalapi.application.dto.DefaultResultResponse;
import com.shillyshally.internalapi.application.dto.ExtraRestaurantResponse;
import com.shillyshally.internalapi.application.dto.PagingExtraRestaurantsResponse;
import com.shillyshally.internalapi.application.dto.RestaurantApprovalRequest;
import com.shillyshally.internalapi.config.RestDocsConfig;
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

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void 승인_대기중인_식당_요청_조회_API_문서화() throws Exception {
        List<ExtraRestaurantResponse> extraRestaurantResponses = List.of(
                new ExtraRestaurantResponse(1L,"알촌"),
                new ExtraRestaurantResponse(2L,"마라미방"),
                new ExtraRestaurantResponse(3L,"도스마스")
        );
        PagingExtraRestaurantsResponse response = new PagingExtraRestaurantsResponse(extraRestaurantResponses,false);

        given(extraRestaurantService.getAllUnchecked(any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/v1/extraRestaurants")
                .param("size","3")
                .param("page","1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-unchecked-extraRestaurants",
                        queryParameters(
                                parameterWithName("size").description("요청 사이즈"),
                                parameterWithName("page").description("요청 페이지")
                        ),
                        responseFields(
                                fieldWithPath("extraRestaurants[].id").type(JsonFieldType.NUMBER)
                                        .description("추가 요청 식당 ID"),
                                fieldWithPath("extraRestaurants[].name").type(JsonFieldType.STRING)
                                        .description("추가 요청 식당 이름"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 여부")
                        )
                ));
    }

    @Test
    void 식당_요청_승인_API_문서화() throws Exception {
        List<RestaurantApprovalRequest> requests = List.of(
                new RestaurantApprovalRequest(1L,"알촌",1L,"https://www.google.com/maps/place/%EC%95%8C%EC%B4%8C+%ED%95%9C%EC%96%91%EB%8C%80ERICA%EC%A0%90/data=!3m1!4b1!4m6!3m5!1s0x357b6eee5840c403:0x838ac663a17d3da0!8m2!3d37.299525!4d126.8383825!16s%2Fg%2F11c1pdbrk5?hl=ko&entry=ttu","https://lh3.googleusercontent.com/p/AF1QipOwU0cfWiibVNDDuKlXk4WQXo4GCMVd2FST77h7=s1360-w1360-h1020",37.299525,126.8383825),
                new RestaurantApprovalRequest(3L,"도스마스",2L,"https://www.google.com/maps/place/%EB%8F%84%EC%8A%A4%EB%A7%88%EC%8A%A4/data=!4m6!3m5!1s0x357b6eef7cd0cc67:0x9a357955a4542c11!8m2!3d37.2999512!4d126.8384175!16s%2Fg%2F1q5brk1kl?hl=ko&entry=ttu","https://lh3.googleusercontent.com/p/AF1QipM_ZT8Hys8hjSNSm9hGzOjxzayxlcKJ8IBJJ4qE=s1360-w1360-h1020",37.2999512,126.8384175)
        );
        DefaultResultResponse response = DefaultResultResponse.ofSuccess();

        given(extraRestaurantService.approve(any())).willReturn(response);

        ResultActions results = mockMvc.perform(put("/api/v1/extraRestaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requests))
                .characterEncoding(StandardCharsets.UTF_8)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("approve-extraRestaurants",
                        requestFields(
                                fieldWithPath("[].extraRestaurantId").type(JsonFieldType.NUMBER).description("식당 ID"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("식당 이름"),
                                fieldWithPath("[].categoryId").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("[].informationUrl").type(JsonFieldType.STRING).description("관련 정보 URL"),
                                fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("식당 이미지 URL"),
                                fieldWithPath("[].longitude").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("[].latitude").type(JsonFieldType.NUMBER).description("위도")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        )
                ));
    }
}
