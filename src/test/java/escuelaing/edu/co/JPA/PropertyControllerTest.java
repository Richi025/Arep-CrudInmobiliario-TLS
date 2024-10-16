package escuelaing.edu.co.JPA;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PropertyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    private Property property;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();

        property = new Property("123 Main St", "A beautiful house", 250000.0, 120.5);
        property.setId(1L);
    }

    @Test
    public void testCreateProperty() throws Exception {
        when(propertyService.createProperty(any(Property.class))).thenReturn(property);

        mockMvc.perform(post("/api/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"123 Main St\",\"description\":\"A beautiful house\",\"price\":250000,\"size\":120.5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.price").value(250000.0));

        verify(propertyService, times(1)).createProperty(any(Property.class));
    }

    @Test
    public void testGetAllProperties() throws Exception {
        List<Property> properties = Arrays.asList(property);
        when(propertyService.getAllProperties()).thenReturn(properties);

        mockMvc.perform(get("/api/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("123 Main St"));

        verify(propertyService, times(1)).getAllProperties();
    }

    @Test
    public void testGetPropertyById() throws Exception {
        when(propertyService.getPropertyById(1L)).thenReturn(Optional.of(property));

        mockMvc.perform(get("/api/properties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("123 Main St"));

        verify(propertyService, times(1)).getPropertyById(1L);
    }

    @Test
    public void testUpdateProperty() throws Exception {
        Property updatedProperty = new Property("456 Main St", "Updated description", 300000.0, 150.0);
        when(propertyService.updateProperty(anyLong(), any(Property.class))).thenReturn(Optional.of(updatedProperty));

        mockMvc.perform(put("/api/properties/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"456 Main St\",\"description\":\"Updated description\",\"price\":300000,\"size\":150.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("456 Main St"))
                .andExpect(jsonPath("$.price").value(300000.0));

        verify(propertyService, times(1)).updateProperty(anyLong(), any(Property.class));
    }

    @Test
    public void testDeleteProperty() throws Exception {
        when(propertyService.deleteProperty(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/properties/1"))
                .andExpect(status().isOk());

        verify(propertyService, times(1)).deleteProperty(1L);
    }
}
