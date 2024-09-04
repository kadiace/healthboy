package com.example.healthboy;

import com.example.healthboy.user.controller.UserController;
import com.example.healthboy.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.healthboy.user.dto.ProfileDto;
import com.example.healthboy.user.dto.ProfileUpdateDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.schedule.dto.ScheduleDto;
import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.service.ScheduleService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    // Service
    @MockBean
    private UserService userService;

    @MockBean
    private ScheduleService scheduleService;

    // Mock data
    @Mock
    private User mockUser;

    @Mock
    private Profile mockProfile;

    @Mock
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockUser = mock(User.class);
        mockProfile = mock(Profile.class);
        mockRequest = mock(HttpServletRequest.class);

        // Stubbing the Profile methods to return expected values
        when(mockProfile.getFirstName()).thenReturn("John");
        when(mockProfile.getLastName()).thenReturn("Doe");
        when(mockProfile.getProfileImage()).thenReturn("image_url");

        when(mockUser.getEmail()).thenReturn("test@gmail.com");
        when(mockUser.getGithubId()).thenReturn("test token man");
        when(mockUser.getProfile()).thenReturn(mockProfile);

        when(mockRequest.getAttribute("user")).thenReturn(mockUser);
    }

    @Test
    @DisplayName("Get Profile Test")
    void testGetProfile() throws Exception {

        // Creating the expected ProfileDto
        ProfileDto expectedProfileDto = new ProfileDto(mockProfile);

        mockMvc.perform(get("/api/users")
                .requestAttr("user", mockUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(expectedProfileDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedProfileDto.getLastName()))
                .andExpect(jsonPath("$.profileImage").value(expectedProfileDto.getProfileImage()));

        verify(mockUser, times(1)).getProfile();
    }

    @Test
    @DisplayName("Get My Schedule Test")
    void testGetSchedules() throws Exception {
        // Creating and mocking the Schedule objects
        List<Schedule> mockSchedules = new ArrayList<>();
        Schedule mockSchedule1 = mock(Schedule.class);
        Schedule mockSchedule2 = mock(Schedule.class);
        mockSchedules.add(mockSchedule1);
        mockSchedules.add(mockSchedule2);

        // Stubbing the Schedule methods to return expected values
        when(mockSchedule1.getUrl()).thenReturn("schedule_url_1");
        when(mockSchedule1.getName()).thenReturn("Morning Routine");
        when(mockSchedule1.getDescription()).thenReturn("Good Morning!");
        when(mockSchedule2.getUrl()).thenReturn("schedule_url_2");
        when(mockSchedule2.getName()).thenReturn("Evening Routine");
        when(mockSchedule2.getDescription()).thenReturn("Exercise, Exercise!");

        // Mocking the schedule service method
        when(scheduleService.getMySchedules(mockProfile)).thenReturn(mockSchedules);

        // Generating expected ScheduleDto list manually
        List<ScheduleDto> expectedScheduleDtos = mockSchedules.stream().map(ScheduleDto::new).toList();

        mockMvc.perform(get("/api/users/schedules")
                .requestAttr("user", mockUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(expectedScheduleDtos.size()))
                .andExpect(jsonPath("$[0].url").value(expectedScheduleDtos.get(0).getUrl()))
                .andExpect(jsonPath("$[0].name").value(expectedScheduleDtos.get(0).getName()))
                .andExpect(jsonPath("$[0].description").value(expectedScheduleDtos.get(0).getDescription()))
                .andExpect(jsonPath("$[1].url").value(expectedScheduleDtos.get(1).getUrl()))
                .andExpect(jsonPath("$[1].name").value(expectedScheduleDtos.get(1).getName()))
                .andExpect(jsonPath("$[0].description").value(expectedScheduleDtos.get(0).getDescription()));
    }

    @Test
    @DisplayName("Update Profile Test")
    void testUpdateProfile() throws Exception {

        // Mocking the ProfileUpdateDto
        ProfileUpdateDto mockProfileUpdateDto = new ProfileUpdateDto();
        mockProfileUpdateDto.setFirstName("UpdatedFirstName");
        mockProfileUpdateDto.setLastName("UpdatedLastName");
        mockProfileUpdateDto.setProfileImage("updated_image_url");

        // Mocking the updated Profile object
        Profile updatedProfile = mock(Profile.class);
        when(updatedProfile.getFirstName()).thenReturn("UpdatedFirstName");
        when(updatedProfile.getLastName()).thenReturn("UpdatedLastName");
        when(updatedProfile.getProfileImage()).thenReturn("updated_image_url");

        // Mocking the userService updateProfile method
        when(userService.updateProfile(eq(mockProfile), any())).thenReturn(updatedProfile);

        // Creating expected ProfileDto
        ProfileDto expectedProfileDto = new ProfileDto(updatedProfile);

        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/api/users")
                .requestAttr("user", mockUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockProfileUpdateDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(expectedProfileDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedProfileDto.getLastName()))
                .andExpect(jsonPath("$.profileImage").value(expectedProfileDto.getProfileImage()));
    }

    @Test
    @DisplayName("Delete User Test")
    void testDeleteUser() throws Exception {

        String expectedResult = "User delete successfully";

        // Mock the behavior of the services
        List<Schedule> mockSchedules = new ArrayList<>();
        Schedule mockSchedule = mock(Schedule.class);
        when(mockSchedule.getUrl()).thenReturn("schedule_url");
        mockSchedules.add(mockSchedule);

        // Mocking the schedule service method
        when(scheduleService.getMySchedules(mockProfile)).thenReturn(mockSchedules);
        doNothing().when(userService).deleteUser(mockUser);
        doNothing().when(userService).deleteProfile(mockProfile);
        doNothing().when(scheduleService).deleteScheduleProfiles(mockProfile);
        doNothing().when(scheduleService).deleteScheduleWithNoProfiles(List.of("schedule_url"));

        mockMvc.perform(delete("/api/users")
                .requestAttr("user", mockUser))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));

        // Verify that the userService and scheduleService methods were called with
        // correct arguments
        verify(userService).deleteUser(mockUser);
        verify(userService).deleteProfile(mockProfile);
        verify(scheduleService).deleteScheduleProfiles(mockProfile);
        verify(scheduleService).deleteScheduleWithNoProfiles(List.of("schedule_url"));
    }

}
