package com.example.demo;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DigitalAssetLifecycleAuditTrailApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @Before
    public void setup() throws Exception {
        // Register an admin user
        RegisterRequest admin = new RegisterRequest();
        admin.setFullName("Admin User");
        admin.setEmail("admin@example.com");
        admin.setDepartment("IT");
        admin.setPassword("admin12345");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk());

        // Login to get JWT token
        LoginRequest login = new LoginRequest();
        login.setEmail("admin@example.com");
        login.setPassword("admin12345");

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Remove quotes from token
        jwtToken = response.replace("\"", "");
    }

    @Test
    public void testRegisterAndLogin() throws Exception {
        RegisterRequest user = new RegisterRequest();
        user.setFullName("Test User");
        user.setEmail("test@example.com");
        user.setDepartment("HR");
        user.setPassword("password123");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));

        LoginRequest login = new LoginRequest();
        login.setEmail("test@example.com");
        login.setPassword("password123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testCreateAndGetAsset() throws Exception {
        Asset asset = new Asset();
        asset.setAssetTag("LAP-001");
        asset.setAssetType("LAPTOP");
        asset.setModel("Dell XPS");
        asset.setPurchaseDate(LocalDate.now().minusYears(1));

        mockMvc.perform(post("/api/assets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asset)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assetTag").value("LAP-001"));

        mockMvc.perform(get("/api/assets")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].assetTag").value("LAP-001"));
    }

    @Test
    public void testUpdateAssetStatus() throws Exception {
        Asset asset = new Asset();
        asset.setAssetTag("LAP-002");
        asset.setAssetType("LAPTOP");
        asset.setModel("MacBook");
        asset.setPurchaseDate(LocalDate.now());

        String response = mockMvc.perform(post("/api/assets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asset)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Asset created = objectMapper.readValue(response, Asset.class);

        mockMvc.perform(put("/api/assets/status/" + created.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"ASSIGNED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ASSIGNED"));
    }

    @Test
    public void testLogLifecycleEvent() throws Exception {
        Asset asset = new Asset();
        asset.setAssetTag("PRN-001");
        asset.setAssetType("PRINTER");
        asset.setModel("HP Laser");
        asset.setPurchaseDate(LocalDate.now());

        String assetResp = mockMvc.perform(post("/api/assets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asset)))
                .andReturn().getResponse().getContentAsString();

        Asset createdAsset = objectMapper.readValue(assetResp, Asset.class);

        LifecycleEvent event = new LifecycleEvent();
        event.setEventType("ASSIGNED");
        event.setEventDescription("Assigned to user");

        mockMvc.perform(post("/api/events/" + createdAsset.getId() + "/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventType").value("ASSIGNED"));

        mockMvc.perform(get("/api/events/asset/" + createdAsset.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventType").value("ASSIGNED"));
    }

    @Test
    public void testTransferRecord() throws Exception {
        Asset asset = new Asset();
        asset.setAssetTag("NET-001");
        asset.setAssetType("NETWORK DEVICE");
        asset.setModel("Cisco");
        asset.setPurchaseDate(LocalDate.now());

        String resp = mockMvc.perform(post("/api/assets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asset)))
                .andReturn().getResponse().getContentAsString();

        Asset created = objectMapper.readValue(resp, Asset.class);

        TransferRecord transfer = new TransferRecord();
        transfer.setFromDepartment("IT");
        transfer.setToDepartment("Finance");
        transfer.setTransferDate(LocalDate.now());
        User approver = new User();
        approver.setId(1L);
        transfer.setApprovedBy(approver);

        mockMvc.perform(post("/api/transfers/" + created.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/transfers/asset/" + created.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].toDepartment").value("Finance"));
    }

    @Test
    public void testDisposalRecord() throws Exception {
        Asset asset = new Asset();
        asset.setAssetTag("OLD-001");
        asset.setAssetType("DESKTOP");
        asset.setModel("Old Desktop");
        asset.setPurchaseDate(LocalDate.now().minusYears(5));

        String resp = mockMvc.perform(post("/api/assets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asset)))
                .andReturn().getResponse().getContentAsString();

        Asset created = objectMapper.readValue(resp, Asset.class);

        DisposalRecord disposal = new DisposalRecord();
        disposal.setDisposalMethod("RECYCLED");
        disposal.setDisposalDate(LocalDate.now());
        disposal.setNotes("Eco disposal");
        User approver = new User();
        approver.setId(1L);
        disposal.setApprovedBy(approver);

        mockMvc.perform(post("/api/disposals/" + created.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disposal)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/disposals")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].disposalMethod").value("RECYCLED"));
    }
}