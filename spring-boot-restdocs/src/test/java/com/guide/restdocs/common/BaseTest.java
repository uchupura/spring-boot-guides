package com.guide.restdocs.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

@SpringBootTest
@AutoConfigureMockMvc   // @SpringBootTest 사용 시 MockMvc 주입을 받기 위해 추가, @WebMvcTest 사용 시 필요 없음
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    public static class ConstrainedFields {
        public final ConstraintDescriptions constraintDescriptions;

        public ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        public FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". "))).optional();
        }

        public FieldDescriptor withRequiredPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }
}

/**
 * @ExtendWith({RestDocumentationExtension.class, SpringExtension.class})  // RestDocumentationResultHandler 용도
 * @ActiveProfiles("test")
 * @Disabled
 *
 * public RestDocumentationResultHandler documentationHandler;
 * @BeforeEach
 * public void setup(WebApplicationContext context, RestDocumentationContextProvider document) {
 *         this.documentationHandler = document("{method-name}",
 *                 preprocessRequest(prettyPrint()),
 *                 preprocessResponse(prettyPrint()));
 *
 *         this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
 *                 .apply(documentationConfiguration(document))
 *                 .alwaysDo(this.documentationHandler)
 *                 .build();
 * }
 */
