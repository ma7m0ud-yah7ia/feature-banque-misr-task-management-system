package banquemisr.challenge05.task.management.service.Util;

import banquemisr.challenge05.task.management.service.dto.AppUserDTO;
import banquemisr.challenge05.task.management.service.dto.TaskDTO;
import banquemisr.challenge05.task.management.service.dto.TaskRequestDTO;
import banquemisr.challenge05.task.management.service.dto.TaskResponseDTO;
import banquemisr.challenge05.task.management.service.model.AppUser;
import banquemisr.challenge05.task.management.service.model.Role;
import banquemisr.challenge05.task.management.service.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TasksMapper {

    private final ModelMapper modelMapper;

    public TasksMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TaskResponseDTO mapTaskModelToTasksResponseDTO(Task task) {
        TaskResponseDTO tasksResponseDTO = new TaskResponseDTO();
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        tasksResponseDTO.setTask(taskDTO);
        return tasksResponseDTO;
    }

    public Task mapTasksRequestDTOToTaskModel(TaskRequestDTO tasksRequestDTO) {
        return modelMapper.map(tasksRequestDTO, Task.class);
    }

    public Task mapTasksRequestDTOToTaskModel(TaskRequestDTO tasksRequestDTO, Task task) {
        task.setDescription(tasksRequestDTO.getDescription());
        task.setStatus(tasksRequestDTO.getStatus());
        task.setPriority(tasksRequestDTO.getPriority());
        task.setDueDate(tasksRequestDTO.getDueDate());
        return task;
    }


    public AppUser mapAppUserDTOToAppUser(AppUserDTO appUserDTO) {
        AppUser user = new AppUser();
        user.setUserName(appUserDTO.getUserName());

        Set<Role> roles = new HashSet<>();

        appUserDTO.getAuthorities().forEach(authority -> {
            Role role = new Role();
            roles.add(role);
        });

        user.setRole(roles);
        return user;
    }
}
