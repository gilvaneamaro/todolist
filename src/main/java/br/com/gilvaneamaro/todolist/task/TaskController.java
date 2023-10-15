package br.com.gilvaneamaro.todolist.task;

import br.com.gilvaneamaro.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository repository;
    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        System.out.println("chegou controller: " + request.getAttribute("idUser"));

        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDateTime = LocalDateTime.now();
        if(currentDateTime.isAfter(taskModel.getStartAt()) || currentDateTime.isAfter(taskModel.getEndAt()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio/fim deve ser após a data atual");

        } else if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de FIM deve ser após a data INICIAL");
        }

        var task = this.repository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){

        var idUser = request.getAttribute("idUser");
        var tasks = this.repository.findByIdUser((UUID) idUser);
        return tasks;

    }
    @PutMapping("/{id}")
    public ResponseEntity update( @RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){

        var task = this.repository.findById(id).orElse(null);
        var idUser = request.getAttribute("idUser");

        if(task == null){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarz'efa não encontrada");
        }
        else if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário sem permissão para alterar essa task");
        }

        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.repository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);

    }
}
