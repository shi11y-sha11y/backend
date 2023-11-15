package com.shillyshally.externalapi.ui;


import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.shillyshally.externalapi.application.CategoryService;
import com.shillyshally.externalapi.application.dto.CategoryResponse;
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
@WebMvcTest(CategoryController.class)
@Import(RestDocsConfig.class)
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 식당_카테고리_조회_API_문서화() throws Exception {
        List<CategoryResponse> response = List.of(
                new CategoryResponse(1L, "한식"),
                new CategoryResponse(2L, "중식"),
                new CategoryResponse(3L, "일식")
        );

        given(categoryService.getAll()).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("category-get-all",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("카테고리 이름"))));
    }

}
